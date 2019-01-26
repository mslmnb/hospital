package com.epam.hospital.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private final static String DRIVER_NAME_PROPERTY_KEY = "database.driverName";
    private final static String URL_PROPERTY_KEY = "database.url";
    private final static String USER_NAME_PROPERTY_KEY = "database.userName";
    private final static String PASSWORD_PROPERTY_KEY = "database.password";
    private final static String MAX_CONN_PROPERTY_KEY = "database.maxConn";


    public static Properties getProperties(String propertiesFileName) throws IOException {

        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader()
                                                      .getResourceAsStream(propertiesFileName);
        properties.load(inputStream);
        return properties;
    }

    public static String getDriverName(Properties properties) {
        return (String) properties.get(DRIVER_NAME_PROPERTY_KEY);
    }

    public static String getUrl(Properties properties) {
        return (String) properties.get(URL_PROPERTY_KEY);
    }

    public static String getUserName(Properties properties) {
        return (String) properties.get(USER_NAME_PROPERTY_KEY);
    }

    public static String getPassword(Properties properties) {
        return (String) properties.get(PASSWORD_PROPERTY_KEY);
    }

    public static int getMaxConn(Properties properties) {
        return Integer.parseInt((String) properties.get(MAX_CONN_PROPERTY_KEY));
    }
}
