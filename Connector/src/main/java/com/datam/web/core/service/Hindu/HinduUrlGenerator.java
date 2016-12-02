package com.datam.web.core.service.Hindu;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tt.core.util.Verify;
import com.tt.web.UrlGenerator;

public class HinduUrlGenerator implements UrlGenerator {

    private static final Logger log = LoggerFactory.getLogger(HinduUrlGenerator.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String BASE_AJAX_URL =
            "http://www.thehindu.com/template/1-0-1/widget/archive/archiveWebDayRest.jsp?d=";

    private final Calendar calendar;

    private final Date startDate;
    private final Date endDate;

    
    /* --- Constructors --- */
    
    public HinduUrlGenerator(Date startDate, Date endDate) {
        super();
        
        // Sanity checks
        Verify.notNull(startDate, endDate);
        
        this.startDate = startDate;
        this.endDate = endDate;

        // initialise calendar
        this.calendar = new GregorianCalendar();
        this.calendar.setTime(this.startDate);
    }

    
    /* --- Implementations --- */
    
    @Override
    public URL getNextURL() {

        // get date and increment
        String dateStr = getDateAndIncrementDay();
        if (dateStr == null)
            return null;

        String urlString = BASE_AJAX_URL + dateStr;
        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
        }

        return null;
    }

    @Override
    public boolean isNextUrlPresent() {
        return calendar.getTime().before(endDate);
    }
    
    
    
    /* --- Private methods --- */
    
    private String getDateAndIncrementDay() {

        Date result = calendar.getTime();
        if (result.before(endDate)) {
            // increase the day by one
            calendar.add(Calendar.DAY_OF_MONTH, 1);
            return new SimpleDateFormat(DATE_FORMAT).format(result);
        } else {
            return null;
        }
    }

    
}
