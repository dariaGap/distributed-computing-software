module software.architecture.design {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    requires com.google.gson;
    requires jdk.httpserver;

    opens beauty_app;
    //opens business_logic;
    opens controllers;
    //opens database;
    //opens service;
   // opens view;
}