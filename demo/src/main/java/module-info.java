module hr.tvz.pejkunovic.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.slf4j;


    opens hr.tvz.pejkunovic.demo to javafx.fxml;
    exports hr.tvz.pejkunovic.demo;
}