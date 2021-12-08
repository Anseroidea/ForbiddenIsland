package app;

import card.Card;
import card.FloodCard;
import card.TreasureCard;
import graphics.*;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import player.Player;

import java.util.List;
import java.util.Optional;
import java.util.Queue;
import java.util.Stack;

public enum PopUp {

    DISCARD,
    HELICOPTER,
    RELOCATE,
    DRAW,
    USEHAND,
    DISCARDPILE,
    USE;

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
        if (this == HELICOPTER || this == RELOCATE || this == DISCARD){
            System.out.println(this.name() + " POPUP CANNOT BE LOADED FROM DEFAULT LOAD");
        } else {
            controller.initializePopUp();
            Stage popup = new Stage();
            if (this != DRAW) {
                popup.setScene(new Scene(p));
            } else {
                popup.setScene(new Scene(p, 1, 1));
            }
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            if (this != USEHAND){
                popup.setOnCloseRequest(event -> {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Close Confirmation");
                    alert.setHeaderText("Closing this dialog will close the entire application. Are you sure you want to quit?");
                    alert.initOwner(popup);
                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.get() == ButtonType.OK){
                        Platform.exit();
                        System.exit(0);
                    }
                    event.consume();
                });
            } else {
                popup.setOnCloseRequest(event -> {
                    event.consume();
                    controller.close();
                });
            }
            if (this == DRAW){
                popup.show();
                DrawPopUp drawPopUp = (DrawPopUp) controller;
                drawPopUp.drawNext();
                popup.close();
                popup.getScene().setRoot(new AnchorPane());
                popup.setScene(new Scene(p));
                popup.showAndWait();
                BoardStateGraphicsInitializer.refreshDisplay();
            } else {
                popup.showAndWait();
            }

        }
    }

    public List<Player> loadHelicopter(List<Player> p){
        if (this == HELICOPTER){
            HelicopterPopUp controller1 = (HelicopterPopUp) controller;
            controller1.initializePopUp(p);
            Stage popup = new Stage();
            popup.setScene(new Scene(this.p));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.setOnCloseRequest(event -> {
                event.consume();
                controller1.close();
            });
            popup.showAndWait();
            return controller1.selectedPlayers();
        } else {
            System.out.println("NONHELICOPTER POPUPS CANNOT BE LOADED FROM THIS METHOD");
            return null;
        }
    }

    public void loadRelocate(Player pl){
        if (this == RELOCATE) {
            RelocatePopUp controller1 = (RelocatePopUp) controller;
            controller1.initializePopUp(pl);
            Stage popup = new Stage();
            popup.setScene(new Scene(p));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.setOnCloseRequest(event -> {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Close Confirmation");
                alert.setHeaderText("Closing this dialog will close the entire application. Are you sure you want to quit?");
                alert.initOwner(popup);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    Platform.exit();
                    System.exit(0);
                }
                event.consume();
            });
            popup.showAndWait();
        } else {
            System.out.println("NONRELOCATE POPUPS CANNOT BE LOADED FROM THIS METHOD");
        }
    }

    public void loadUse(Player pl, TreasureCard c){
        if (this == USE) {
            UsePopUp controller1 = (UsePopUp) controller;
            controller1.initializePopUp(pl, c);
            Stage popup = new Stage();
            popup.setScene(new Scene(p));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.setResizable(false);
            popup.setOnCloseRequest(event -> {
                event.consume();
                controller1.close();
            });
            popup.showAndWait();
        } else {
            System.out.println("NONUSE POPUPS CANNOT BE LOADED FROM THIS METHOD");
        }
    }

    public void loadDiscardPile(Stack<? extends Card> cards){
        if (this == DISCARDPILE) {
            DiscardPilePopUp controller1 = (DiscardPilePopUp) controller;
            controller1.initializePopUp(cards);
            Stage popup = new Stage();
            popup.setScene(new Scene(p));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.setResizable(false);
            popup.setOnCloseRequest(event -> {
                event.consume();
                controller1.close();
            });
            popup.showAndWait();
        } else {
            System.out.println("NONDISCARDPILE POPUPS CANNOT BE LOADED FROM THIS METHOD");
        }
    }

    public void loadDiscard(Player pl) {
        if (this == DISCARD) {
            DiscardPopUp controller1 = (DiscardPopUp) controller;
            controller1.initializePopUp(pl);
            Stage popup = new Stage();
            popup.setScene(new Scene(p));
            popup.initModality(Modality.APPLICATION_MODAL);
            popup.setResizable(false);
            popup.setResizable(false);
            popup.setOnCloseRequest(event -> {
                event.consume();
                controller1.close();
            });
            popup.showAndWait();
        } else {
            System.out.println("NONDISCARD POPUPS CANNOT BE LOADED FROM THIS METHOD");
        }
    }
}
