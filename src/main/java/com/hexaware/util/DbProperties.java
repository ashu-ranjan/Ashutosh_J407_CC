package com.hexaware.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DbProperties {
    private static Properties properties = new Properties();

    static {
        try {

            InputStream in = DbProperties.class.getClassLoader().getResourceAsStream("AshutoshJ407.properties");


            if (in == null) {
                throw new RuntimeException("Resource file not found!");
            }
            properties.load(in);


        } catch (IOException e) {
            throw new RuntimeException("Database Connection Failed!", e);
        }
    }

    public static String getDriver() {
        return properties.getProperty("db.driver");
    }

    public static String getUrl() {
        return properties.getProperty("db.url");
    }

    public static String getUser() {
        return properties.getProperty("db.user");
    }

    public static String getPassword() {
        return properties.getProperty("db.password");
    }

    public static Properties getProperties() {
        Properties connProperties = new Properties();
        connProperties.put("user", getUser());
        connProperties.put("password", getPassword());
        return connProperties;
    }
}
