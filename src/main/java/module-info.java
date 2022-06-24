module com.example.friendsbookimp {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.friendsbookimp to javafx.fxml;
    exports com.example.friendsbookimp;
}