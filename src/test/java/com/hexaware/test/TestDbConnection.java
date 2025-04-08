package com.hexaware.test;

import com.hexaware.service.DbConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.*;

public class TestDbConnection {

    @Test
    void databaseConnection(){
        DbConnection dbConn = new DbConnection();

        Connection conn = dbConn.connection();
        assertNotNull(conn, "Connection is null. DB not connected.");
        System.out.println("Database connection test passed.");


    }

}
