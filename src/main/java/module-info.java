module com.example.bocatajavafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires java.naming;
    requires org.hibernate.orm.core;
    requires jbcrypt;


    opens com.example.bocatajavafx to javafx.fxml, org.hibernate.orm.core;
    exports com.example.bocatajavafx;
    exports com.example.bocatajavafx.controllers;
    opens com.example.bocatajavafx.controllers to javafx.fxml, org.hibernate.orm.core;
    exports com.example.bocatajavafx.dao;
    opens com.example.bocatajavafx.dao to javafx.fxml, org.hibernate.orm.core;
    exports com.example.bocatajavafx.models;
    opens com.example.bocatajavafx.models to javafx.fxml, org.hibernate.orm.core;
    exports com.example.bocatajavafx.services;
    opens com.example.bocatajavafx.services to javafx.fxml, org.hibernate.orm.core;
    exports com.example.bocatajavafx.util;
    opens com.example.bocatajavafx.util to javafx.fxml, org.hibernate.orm.core;
}