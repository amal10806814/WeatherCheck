package org.automation.utility;

public class CommonHelpers {

   static public int getNumericValue(String str) {
        String digits = str.replaceAll("[^0-9.]", "");
        return Integer.parseInt(digits);
    }
}