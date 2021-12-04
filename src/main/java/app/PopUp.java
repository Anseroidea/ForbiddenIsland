package app;

import graphics.Poppable;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

public enum PopUp {

    DISCARD,
    FLOOD;

    private Pane p;

    private Poppable controller;

    public void setController(Poppable controller) {
        this.controller = controller;
    }

    public Scene getScene(){
        return new Scene(p, 1920, 1080);
    }
    public Pane getPane(){
        return p;
    }
    public void setPane(Pane a){
        p = a;
    }

    public Object getController() {
        return controller;
    }

    public void load(){
        controller.initializePopUp();
        Stage popup = new Stage();
        popup.setScene(new Scene(p));
        popup.initModality(Modality.APPLICATION_MODAL);
        popup.setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Close Confirmation");
            alert.setHeaderText("Closing this dialog will close the entire application. Are you sure you want to quit?");
            alert.initOwner(popup);
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Platform.exit();
            }
            event.consume();
        });
        popup.showAndWait();
    }
}
