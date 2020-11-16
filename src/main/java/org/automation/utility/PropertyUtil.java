package org.automation.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyUtil {

    private static PropertyUtil prop;
    private Properties property = new Properties();

    public PropertyUtil() {


    }

    public static synchronized PropertyUtil getInstance() {
        if (prop == null) {
            prop = new PropertyUtil();


        }
        return prop;

    }

    public  void load(String fileName){

        InputStream inputStream =this.getClass().getClassLoader().getResourceAsStream(fileName);

        try {
            this.property.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getValue(String key){
        return this.property.getProperty(key);
    }
}