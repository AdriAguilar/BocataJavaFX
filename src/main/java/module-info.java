module com.example.bocatajavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.bocatajavafx to javafx.fxml;
    exports com.example.bocatajavafx;
    exports com.example.bocatajavafx.controllers;
    opens com.example.bocatajavafx.controllers to javafx.fxml;
}