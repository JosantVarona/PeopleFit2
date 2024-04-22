package dam.JosantVarona.test;

import dam.JosantVarona.Model.Connection.ConnectProperties;
import dam.JosantVarona.Utils.XMLManager;

public class TestSave {
    public static void main(String[] args) {
        ConnectProperties c = new ConnectProperties("localhost","3306","proyect3","root","root");
        XMLManager.writeXML(c,"connection.xml");
    }
}
