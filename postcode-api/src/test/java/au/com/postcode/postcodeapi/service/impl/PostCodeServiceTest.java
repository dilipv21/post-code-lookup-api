package au.com.postcode.postcodeapi.service.impl;


import au.com.postcode.postcodeapi.model.PostCode;
import au.com.postcode.postcodeapi.repository.PostCodeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostCodeServiceTest {

    @Mock
    public PostCodeRepository repository;

    @InjectMocks
    public PostCodeService service;

    @Test
    public void persistPostalDetails_addEntry() {

        PostCode request = PostCode.builder()
                .postCode("3000")
                .suburb("Melbourne")
                .build();

        boolean isSuccess = service.persistPostalDetails(request);
        service.persistPostalDetails(request);
        assertTrue(isSuccess);
    }


    @Test
    public void persistPostalDetails_exception() {

        PostCode request = PostCode.builder()
                .postCode("3000")
                .suburb("Melbourne")
                .build();

        when(repository.save(request)).thenThrow(Exception.class);
        boolean isSuccess = service.persistPostalDetails(request);
        assertFalse(isSuccess);

    }

    //TODO: Apply same to rest of the Methods.

    @Test
    public void getSuburbDetails() {

    }

    @Test
    public void getPostalCodeDetails() {
    }

}