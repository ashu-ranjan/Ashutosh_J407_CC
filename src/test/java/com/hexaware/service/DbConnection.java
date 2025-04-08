package com.hexaware.service;

import com.hexaware.exception.DBConnectionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbConnection {

    private static final String PROPERTIES_FILE = "src/main/resources/AshutoshJ407.properties";

    public Connection connection() throws DBConnectionException {

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream(PROPERTIES_FILE)){

            properties.load(fileInputStream);

            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");

            return DriverManager.getConnection(url, user, password);

        } catch (IOException e){
            System.out.println("Cannot load properties file! : " + e.getMessage());
        } catch (SQLException e){
            System.out.println("Database Connection Failed! : " + e.getMessage());
        }
        return null;
    }
}
