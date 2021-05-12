package me.scruffyboy13.Economy.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {
	
	public String host;
    public int port;
    public String database;
    public String username;
    public String password;
    public Connection connection;

    public MySQL(String host, int port, String database, String username, String password) {
    	this.host = host;
    	this.port = port;
    	this.database = database;
    	this.username = username;
    	this.password = password;
    }
    
    public void connect() throws ClassNotFoundException, SQLException {
        if (!isConnected()) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true&useSSL=false", username, password);
        }
    }

    public void disconnect() throws SQLException {
        if (isConnected()) {
        	connection.close();
        }
    }
    
    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public Connection getConnection() {
        return connection;
    }
}
