module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.almasb.fxgl.all;
    requires org.slf4j;

    opens org.example.demo to javafx.fxml;
    exports org.example.demo;
    exports org.example.demo.UIController;
    opens org.example.demo.UIController to javafx.fxml;
}