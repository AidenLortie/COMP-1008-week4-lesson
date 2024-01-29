module com.github.aidenlortie.comp1008week4lesson {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.github.aidenlortie.comp1008week4lesson to javafx.fxml;
    exports com.github.aidenlortie.comp1008week4lesson;
}