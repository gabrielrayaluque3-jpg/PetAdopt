package es.iesfranciscodelosrios.petadopt.dataAccess;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "connection")
public class ConectionProperties implements Serializable {
    private  String server;
    private String port;
    private String dataBase;
    private String user;
    private String password;

    public ConectionProperties() {
    }

    public ConectionProperties (String server, String port, String dataBase, String password, String user) {
        this.server = server;
        this.port = port;
        this.dataBase = dataBase;
        this.password = password;
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getURL(){
        return "jdbc:mysql://"+this.server+":"+this.port+"/"+this.dataBase;
    }
}

