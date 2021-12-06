package app;

import board.BoardGame;
import graphics.BoardStateGraphicsInitializer;
import graphics.Lose;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class
ForbiddenIsland extends Application {

    private static Stage primaryStage = new Stage();

    private static BoardGame board;

    private static Lose lose;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void setBoardGame(int difficulty, int numPlayers, boolean manualFlooding) {
        board = new BoardGame(difficulty, numPlayers, manualFlooding);
    }

    public static void refreshDisplay() {
        System.out.println(ProgramStateManager.getCurrentState().name());
        primaryStage.setScene(ProgramStateManager.getCurrentState().getScene());
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static Font getForbiddenIslandFont(int size) {
        return Font.loadFont(ForbiddenIsland.class.getClassLoader().getResourceAsStream("fonts/BlackRose.ttf"), size);
    }

    public static Lose getLose() {
        return lose;
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ForbiddenIsland.primaryStage = primaryStage;
        primaryStage.setTitle("Forbidden Island");
        for (ProgramState ps : ProgramState.values()){
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ForbiddenIsland.class.getResource("/fxml/" + ps.name().toLowerCase() + ".fxml")));
                AnchorPane ap = loader.load();
                ps.setPane(ap);
                if (ps.name().equalsIgnoreCase("BOARD")){
                    BoardStateGraphicsInitializer.setBg(loader.getController());
                    FXMLLoader loader1 = new FXMLLoader(Objects.requireNonNull(ForbiddenIsland.class.getResource("/fxml/player.fxml")));
                    AnchorPane ap1 = loader1.load();
                    BoardStateGraphicsInitializer.setPg(loader1.getController());
                    StackPane s = new StackPane(ap, ap1);
                    ps.setPane(s);
                } else if (ps.name().equalsIgnoreCase("LOSE")){
                    lose = loader.getController();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        for (PopUp p : PopUp.values()){
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ForbiddenIsland.class.getResource("/fxml/" + p.name().toLowerCase() + ".fxml")));
                AnchorPane ap = loader.load();
                p.setPane(ap);
                p.setController(loader.getController());
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        ProgramStateManager.goToState(ProgramState.MAINMENU);
        Scene s = new Scene(ProgramStateManager.getCurrentState().getPane(), 1920, 1080);
        primaryStage.setScene(s);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static BoardGame getBoard(){
        return board;
    }


    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}

