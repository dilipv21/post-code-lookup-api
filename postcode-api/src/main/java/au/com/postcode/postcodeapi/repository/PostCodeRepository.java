package au.com.postcode.postcodeapi.repository;

import au.com.postcode.postcodeapi.model.PostCode;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostCodeRepository extends MongoRepository<PostCode, String> {

    PostCode findByPostCodeEquals(String postCode);
    PostCode findBySuburbIgnoreCase(String suburb);
}
