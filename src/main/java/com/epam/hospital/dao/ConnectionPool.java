package com.epam.hospital.dao;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * The basic service for managing of connection's pool
 */

public class ConnectionPool {
    private static final Logger LOG = Logger.getLogger(ConnectionPool.class);
    private static ConnectionPool instance;
    private final String driverName;
    private final List<Connection> freeConnections = new ArrayList();
    private final String url;
    private final String user;
    private final String password;
    private final int maxConn;

    /* Prevent the ConnectionPool class from being instantiated. */
    private ConnectionPool(String driverName, String url,
                           String user, String password, int maxConn) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        loadDrivers();
    }

    /**
     * Loads the jdbc-driver
     */
    private void loadDrivers() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            LOG.error("Not found JDBC driver. " + e.getMessage());
        }
    }

    /**
     * Returns the instance of the ConnectionPool class
     * @param url a database url
     * @param user the database user on whose behalf the connection is being  made
     * @param password the user's password
     * @param maxConn the size of the connection's pool
     * @return the instance of the ConnectionPool class
     */
    public static synchronized ConnectionPool getInstance(String driverName, String url,
                                                           String user, String password, int maxConn){
        if (instance == null) {
            instance = new ConnectionPool(driverName, url, user, password, maxConn);
        }
        return instance;
    }

    /**
     * Retrieves a Connection to the database.
     * @return a Connection from connection's pool if pool is not empty
     *    or a new Connection.
     */
    public synchronized Connection getConnection() {
        Connection con;
        if (!freeConnections.isEmpty()) {
            con = freeConnections.get(freeConnections.size() - 1);
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

    /**
     * Creates a new connection to the database.
     * @return a Connection or null if a database access error occurs
     */
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

    /**
     * Puts a specified connection into the connection's pool
     * @param con a Connection to the database.
     */
    public synchronized void freeConnection(Connection con) {
        if ((con != null) && (freeConnections.size() <= maxConn)){
            freeConnections.add(con);
        }
    }

    /**
     * Closes all connections in the connection's pool and cleans the pool.
     */
    public synchronized void release() {
        Iterator allConnections = freeConnections.iterator();
        while (allConnections.hasNext()) {
            Connection con=(Connection) allConnections.next();
            try {
                con.close();
            } catch (SQLException e) {
                LOG.error("Can't close connection for pool.");
            }
        }
        freeConnections.clear();
    }
}
