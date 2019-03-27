package com.epam.hospital.dao;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectionPool {
    private static ConnectionPool instance;
    private final String DRIVER_NAME;
    private ArrayList<Connection> freeConnections = new ArrayList<>();
    private String url;
    private String user;
    private String password;
    private int maxConn;

    private ConnectionPool(String DRIVER_NAME, String url,
                           String user, String password, int maxConn) {
        this.DRIVER_NAME = DRIVER_NAME;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        loadDrivers();
    }
    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class
                    .forName(DRIVER_NAME).newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            // logger "Can't register JDBC driver "
        }
    }

    public static synchronized ConnectionPool getInstance(String DRIVER_NAME, String URL,
                                                           String user, String password, int maxConn){
        if (instance == null) {
            instance = new ConnectionPool(DRIVER_NAME, URL, user, password, maxConn);
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        Connection con;
        if (!freeConnections.isEmpty()) {
            con = (Connection) freeConnections.get(freeConnections.size() - 1);
            freeConnections.remove(con);
            try {
                if (con.isClosed()) {
                    con = getConnection();
                }
            } catch (Exception e) {
                con = getConnection();
            }
        } else {
            con = newConnection();
        }
        return con;
    }

    private Connection newConnection() {
        Connection con;
        try {
            if (user == null) {
                con = DriverManager.getConnection(url);
            } else {
                con = DriverManager.getConnection(url,
                        user, password);
            }
        } catch (SQLException e) {
            con = null;
        }
        return con;
    }

    public synchronized void freeConnection(Connection con) {
        // Put the connection at the end of the List
        if ((con != null) && (freeConnections.size() <= maxConn)){
            freeConnections.add(con);
        }
    }

    public synchronized void release() {
        Iterator allConnections = freeConnections.iterator();
        while (allConnections.hasNext()) {
            Connection con=(Connection) allConnections.next();
            try {
                con.close();
                // "Closed connection for pool „
            } catch (SQLException e) {
                // "Can't close connection for pool „
            }
        }
        freeConnections.clear();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        release();
   }
}
