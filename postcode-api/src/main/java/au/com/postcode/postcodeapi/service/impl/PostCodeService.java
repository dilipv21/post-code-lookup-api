package au.com.postcode.postcodeapi.service.impl;

import au.com.postcode.postcodeapi.model.ModelHelper;
import au.com.postcode.postcodeapi.model.PostCode;
import au.com.postcode.postcodeapi.repository.PostCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class PostCodeService {

    public static final String UNABLE_TO_LOCATE = "Unable to locate";
    private final RedisTemplate redisTemplate;
    private final PostCodeRepository repository;


    @Autowired
    public PostCodeService(final RedisTemplate redisTemplate, final PostCodeRepository repository) {
        this.redisTemplate = redisTemplate;
        this.repository = repository;
    }

    /**
     * Gets the Suburb Details from Redis Cache/MongoDB.
     *
     * @param postcode - Postal Code.
     * @return - Suburb Name.
     */
    public String getSuburbDetails(final Integer postcode) {
        //Redis Approach -> getCacheEntry(postcode)
        PostCode data = repository.findByPostCodeEquals(String.valueOf(postcode));
        log.debug("Found an entry in  {}", ModelHelper.toJson(data));
        return data != null ? data.getSuburb() : UNABLE_TO_LOCATE;
    }

    /**
     * Gets the PostCode from Redis Cache/MongoDB.
     *
     * @param suburb - Suburb Name.
     * @return - Postal Code.
     */
    public String getPostalCodeDetails(final String suburb) {
        //Redis Approach -> getCacheEntry(suburb)
        PostCode data = repository.findBySuburbIgnoreCase(suburb);
        log.debug("Found an entry in  {}", ModelHelper.toJson(data));
        return data != null ? data.getPostCode() : "Unable to locate";
    }


    /**
     * Persists the Entry into Redis Cache/Mongo DB.
     *
     * @param postCode {@link PostCode}
     * @return - {@link Boolean} - True on Successful save of record, else returns false.
     */
    public boolean persistPostalDetails(final PostCode postCode) {

        try {

            String existingRecord = getSuburbDetails(Integer.valueOf(postCode.getPostCode()));

            if (!UNABLE_TO_LOCATE.equals(existingRecord)) {
                log.warn("An Entry exist for the given Postcode {} ", existingRecord);
            }
            repository.save(postCode);
            log.debug("An entry added into DB");
            /* Redis Approach!!
            redisTemplate.opsForHash().put("suburb", postCode.getSuburb(), String.valueOf(postCode.getPostCode()));
            redisTemplate.opsForHash().put("postCode", String.valueOf(postCode.getPostCode()), postCode.getSuburb());
            redisTemplate.opsForHash().put("requestDetails", String.valueOf(new Date()),
            ModelHelper.toJson(postCode));
            */
        } catch (Exception e) {
            log.error("Failed to persist postal code details, =", e);
            return false;
        }

        return true;
    }

    /**
     * Redis Cache Lookup.
     */
    private String getCacheEntry(final Integer postcode) {
        String suburb = (String) redisTemplate.opsForHash().get("postCode", String.valueOf(postcode));
        return Optional.ofNullable(suburb).orElse(UNABLE_TO_LOCATE);
    }


}
