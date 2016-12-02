package com.tt.core.util;

import java.util.UUID;

public class UuidUtil {
    
    /**
     * Utility class
     */
    private UuidUtil() {}
    
    

    public static String newId() {
        UUID uuid = UUID.randomUUID();
        String randomUUIDString = uuid.toString();
        
        return randomUUIDString;
    }

}
