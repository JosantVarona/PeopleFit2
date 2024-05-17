package dam.JosantVarona.test;

import dam.JosantVarona.Model.Connection.ConnectProperties;
import dam.JosantVarona.Utils.XMLManager;

public class LoadConnect {
    public static void main(String[] args) {
        ConnectProperties c = XMLManager.readXML(new ConnectProperties(),"connection.xml");
        System.out.println(c);
    }
}
