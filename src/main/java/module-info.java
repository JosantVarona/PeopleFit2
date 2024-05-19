module dam.JosantVarona {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.xml.bind;
    requires java.sql;
    requires javafx.media;

    opens dam.JosantVarona to javafx.fxml;
    opens dam.JosantVarona.Model.Connection to java.xml.bind;

    exports dam.JosantVarona;
    exports dam.JosantVarona.View;
    opens dam.JosantVarona.View to javafx.fxml;

}
