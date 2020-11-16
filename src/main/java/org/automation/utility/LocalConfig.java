package org.automation.utility;


import org.automation.utility.PropertyUtil;
public class LocalConfig {

        public final static String WEATHER_HOST;
        public final static String CITY;
        public final static String APP_KEY;
        public final static String NDTV;
        public final static String TEMP_VAR;
        public final static String CHROME;


        static {
            try{
                PropertyUtil.getInstance().load("localconfig.properties");
                WEATHER_HOST=PropertyUtil.getInstance().getValue("WEATHER_HOST");
                CITY=PropertyUtil.getInstance().getValue("CITY");
                APP_KEY=PropertyUtil.getInstance().getValue("APP_KEY");
                NDTV=PropertyUtil.getInstance().getValue("NDTV");
                TEMP_VAR=PropertyUtil.getInstance().getValue("TEMP_VAR");
                CHROME=PropertyUtil.getInstance().getValue("CHROME");

            }catch (Exception e){
                e.printStackTrace();
                throw new RuntimeException();
            }
        }

    }


