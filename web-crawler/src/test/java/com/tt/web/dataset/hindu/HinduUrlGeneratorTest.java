package com.tt.web.dataset.hindu;

import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HinduUrlGeneratorTest {
    
    private static final Logger log = LoggerFactory.getLogger(HinduUrlGeneratorTest.class);
    
    private DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    
    private Date startDate;
    private Date endDate;

    private HinduUrlGenerator urlGenerator;

    /* --- Setup --- */

    @Before
    public void setUp() throws ParseException {
        startDate = dateFormat.parse("18-08-2009");
        endDate = dateFormat.parse("19-08-2009");
        
        urlGenerator = new HinduUrlGenerator(startDate, endDate);

    }

    @After
    public void tearDown() {
        urlGenerator = null; 
    }


    /* --- Tests --- */

    @Test
    public void test_isNextUrlPresent() {
        boolean isNextUrlPresent = urlGenerator.isNextUrlPresent();
        Assert.assertTrue(isNextUrlPresent);
    }

    @Test
    public void test_getNextURL() {
        boolean isNextUrlPresent = urlGenerator.isNextUrlPresent();
        while(isNextUrlPresent) {
        
            URL nextUrl = urlGenerator.getNextURL();
            log.info("Next URL : {}", nextUrl);
            
            isNextUrlPresent = urlGenerator.isNextUrlPresent();
        }

        Assert.assertFalse(isNextUrlPresent);
    }

}
