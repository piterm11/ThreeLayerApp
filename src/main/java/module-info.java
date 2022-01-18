module com.client {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires java.rmi;
    requires java.persistence;
    requires org.jetbrains.annotations;
    requires java.sql;
    requires mysql.connector.java;
    requires org.hibernate.commons.annotations;
    requires org.hibernate.orm.core;

    opens com.entities to org.hibernate.orm.core, javafx.base;
    opens com.client to javafx.fxml;

    exports com.client;
    exports com.test;
    exports com.api;
    exports com.entities;
    exports com.exceptions;
}