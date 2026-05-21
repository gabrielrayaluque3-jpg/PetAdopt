package es.iesfranciscodelosrios.petadopt.dataAccess;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionBD {
    private static final String FILE = "connection.xml";
    private static Connection con;
    private static ConnectionBD _instance;

    private ConnectionBD() {
        ConectionProperties properites = XMLManager.readXML(new ConectionProperties(), FILE);
        try{
            con = DriverManager.getConnection(properites.getURL(),properites.getUser(),properites.getPassword());

        }catch (SQLException e){
            e.printStackTrace();
            con = null;
        }
    }

    public static Connection getConnection(){
        if (_instance==null){
            _instance=new ConnectionBD();
        }
        return  con;
    }
}

