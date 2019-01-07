package com.epam.hospital.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
    private static final String POSTGRES_PROPERTIES_FILE = "db/postgres.properties";

    public static Properties getDefaultProperties() throws IOException {
        return getPostgresProperties();
    }

    public static Properties getPostgresProperties() throws IOException {

        Properties properties = new Properties();
        InputStream inputStream = new PropertiesUtil().getClass()
                                                      .getClassLoader()
                                                      .getResourceAsStream(POSTGRES_PROPERTIES_FILE);
        properties.load(inputStream);
        return properties;
    }

    public static String getDriverName(Properties properties) {
        return (String) properties.get("database.driverName");
    }

    public static String getUrl(Properties properties) {
        return (String) properties.get("database.url");
    }

    public static String getUserName(Properties properties) {
        return (String) properties.get("database.userName");
    }

    public static String getPassword(Properties properties) {
        return (String) properties.get("database.password");
    }

    public static int getMaxConn(Properties properties) {
        return Integer.parseInt((String) properties.get("database.maxConn"));
    }

    public static void main(String[] args) {
        try {
            Properties properties=getPostgresProperties();
            String s = getDriverName(properties);
            int i = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
