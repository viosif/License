package com.fortech.utils;

import java.util.Random;

/**
 * Created by iosifvarga on 29.06.2017.
 */
public class Utils {

    public static String generateLicenseKey() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 23) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());

            if (salt.length() == 5 || salt.length() == 11 || salt.length() == 17)
                salt.append("-");
            else
                salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }

}
