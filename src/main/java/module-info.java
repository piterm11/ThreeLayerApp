module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.rmi;
    requires java.persistence;
    requires org.jetbrains.annotations;

    opens com.client to javafx.fxml;
    exports com.client;
}