module dam.JosantVarona {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;

    opens dam.JosantVarona to javafx.fxml;
    exports dam.JosantVarona;
}
