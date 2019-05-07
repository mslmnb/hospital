package com.epam.hospital.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * The class of utilities for work with properties files
 */

public class PropertiesUtil {
    private static final String DRIVER_NAME_PROPERTY_KEY = "database.driverName";
    private static final String URL_PROPERTY_KEY = "database.url";
    private static final String USER_NAME_PROPERTY_KEY = "database.userName";
    private static final String PASSWORD_PROPERTY_KEY = "database.password";
    private static final String MAX_CONN_PROPERTY_KEY = "database.maxConn";

    /**
     * Gets {@code Properties} from specified properties file.
     * @param propertiesFileName the properties file name.
     *                           Properties file should be in resource directory.
     *                           If the file is in the subdirectory of resource directory that
     *                           in the file name also there has to be a subdirectory name.
     *                           For example "db/db.properties"
     *
     * @return {@code Properties}
     * @throws IOException if an error occurred when reading from the input stream.
     */
    public static Properties getProperties(String propertiesFileName) throws IOException {

        Properties properties = new Properties();
        InputStream inputStream = PropertiesUtil.class.getClassLoader()
                                                      .getResourceAsStream(propertiesFileName);
        properties.load(inputStream);
        return properties;
    }

    /**
     * Gets the driver name for database connection from specified {@code Properties}
     * @param properties {@code Properties}
     * @return the driver name for database connection
     */
    public static String getDriverName(Properties properties) {
        return (String) properties.get(DRIVER_NAME_PROPERTY_KEY);
    }

    /**
     * Gets the URL for database connection from specified {@code Properties}
     * @param properties {@code Properties}
     * @return the URL for database connection
     */
    public static String getUrl(Properties properties) {
        return (String) properties.get(URL_PROPERTY_KEY);
    }

    /**
     * Gets the user name for database connection from specified {@code Properties}
     * @param properties {@code Properties}
     * @return the user name for database connection
     */
    public static String getUserName(Properties properties) {
        return (String) properties.get(USER_NAME_PROPERTY_KEY);
    }

    /**
     * Gets the user password for database connection from specified {@code Properties}
     * @param properties {@code Properties}
     * @return the password for database connection
     */
    public static String getPassword(Properties properties) {
        return (String) properties.get(PASSWORD_PROPERTY_KEY);
    }

    /**
     * Gets the maximum number of connections for a connection pool from specified {@code Properties}
     * @param properties {@code Properties}
     * @return the maximum number of connections for a connection pool
     */
    public static int getMaxConn(Properties properties) {
        return Integer.parseInt((String) properties.get(MAX_CONN_PROPERTY_KEY));
    }
}
