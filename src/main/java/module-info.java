module happy.test.javafxte {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.almasb.fxgl.all;

    opens happy.test.javafxte to javafx.fxml;
    exports happy.test.javafxte;
}