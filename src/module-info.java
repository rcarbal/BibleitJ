module BibleitJ {
    requires javafx.fxml;
    requires javafx.controls;
    requires log4j;
    requires jackson.mapper.asl;
    requires jackson.core.asl;
    requires httpclient;
    requires httpcore;

    opens mainlayout;

}