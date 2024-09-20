module org.example.tableview {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tableview to javafx.fxml;
    exports org.example.tableview;
}