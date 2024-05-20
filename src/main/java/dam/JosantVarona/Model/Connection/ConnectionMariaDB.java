package dam.JosantVarona.Model.Connection;

import dam.JosantVarona.Utils.XMLManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionMariaDB {
    private final static String FILE="connection.xml";
    private static ConnectionMariaDB _instance;
    private static Connection conn;

    private ConnectionMariaDB(){
        // Read the connection properties from an XML file and cast it to ConnectProperties
        ConnectProperties properties = (ConnectProperties) XMLManager.readXML(new ConnectProperties(),FILE);

        try {
            // Attempt to establish a connection to the database using the URL, user, and password from properties
            conn = DriverManager.getConnection(properties.getURL(),properties.getUser(),properties.getPassword());
        } catch (SQLException e) {
            // If an SQLException occurs, print the stack trace for debugging purposes
            e.printStackTrace();
            // Set the connection object to null, indicating the connection failed
            conn=null;
        }
    }

    public static Connection getConnection(){
        // If null, create a new instance of ConnectionMariaDB
        if(_instance==null){
            _instance = new ConnectionMariaDB();
        }
        return conn;
    }
    public static void closeConnection() {
        // Check if the connection object is not null
        if (conn != null) {
            try {
                // Attempt to close the connection
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
