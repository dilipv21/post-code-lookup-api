package au.com.postcode.postcodeapi.service;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import static org.junit.Assert.*;

public class PostCodeServiceTest {

    @Test
    public void getSuburbDetails() {

        Jedis jedis = new Jedis();

        System.out.println("->" + jedis.hgetAll("postCode"));
    }

    @Test
    public void persistPostalDetails() {
    }
}