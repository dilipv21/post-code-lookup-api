package au.com.postcode.postcodeapi.controller;


import au.com.postcode.postcodeapi.model.PostCode;
import au.com.postcode.postcodeapi.service.impl.PostCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.metrics.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/postcode")
@Slf4j
public class PostCodeController {

    public static final int POST_CODE_START = 0200;
    public static final int POST_CODE_END = 7999;
    private final PostCodeService postCodeService;

    public PostCodeController(final PostCodeService postCodeService) {
        this.postCodeService = postCodeService;
    }


    /**
     * Validates the PostCode provided in the Request Path, Retrieves Data from DB.
     *
     * @param postCode - Australia Postal Code, Expected between 02000 to 7999
     * @return - {@link HttpStatus} OK on successful retrieval of record, BAD REQUEST on Validation Failure,
     * INTERNAL ERROR on Exceptions.
     */
    @GetMapping(path = "/code/{pc}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Timed(value = "findSuburb_byPostCode_http_request")
    public ResponseEntity<String> retrieveSuburb(@PathVariable("pc") final Integer postCode) {

        Optional<Integer> code = Optional.of(postCode)
                .filter(zipCode -> zipCode >= POST_CODE_START && zipCode <= POST_CODE_END);

        if (code.isPresent()) {
            return new ResponseEntity<>(postCodeService.getSuburbDetails(postCode), HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid Postal Code, Expected Value between 0200 to 7999",
                HttpStatus.BAD_REQUEST);

    }


    /**
     * Validates the PostCode provided in the Request Path, Retrieves Data from DB.
     *
     * @param suburb - SubUrb Details.
     * @return - {@link HttpStatus} OK on successful retrieval of record, BAD REQUEST on Validation Failure,
     * INTERNAL ERROR on Exceptions.
     */
    @GetMapping(path = "/suburb/{sb}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Timed(value = "findPostCode_bySuburb_http_request")
    public ResponseEntity<String> retrievePostal(@PathVariable("sb") final String suburb) {
        return new ResponseEntity<>(postCodeService.getPostalCodeDetails(suburb), HttpStatus.OK);
    }


    /**
     * Saves the Suburb Information along with Postal Code details into DB.
     *
     * @param postCode - {@link PostCode}
     * @return - {@link HttpStatus} OK on successful retrieval of record, BAD REQUEST on Validation Failure,
     * INTERNAL ERROR on Exceptions.
     */
    @PostMapping(path = "/saveSuburbDetails", consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @Timed(value = "savePostCodeDetails_http_request")
    public ResponseEntity<String> saveSuburbDetails(@RequestBody @Valid final PostCode postCode) {

        if (postCodeService.persistPostalDetails(postCode)) {
            return new ResponseEntity<>("Saved Postal Code Details", HttpStatus.OK);
        }


        return new ResponseEntity<>("Unable to Save Postal Code Details", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
