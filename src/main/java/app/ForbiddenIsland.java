package app;

import board.BoardGame;
import graphics.BoardStateGraphicsInitializer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class
ForbiddenIsland extends Application {

    private static Stage primaryStage = new Stage();

    private static BoardGame board;

    public static void main(String[] args) {
        Application.launch(args);
    }

    public static void setBoardGame(int difficulty, int numPlayers) {
        board = new BoardGame(difficulty, numPlayers);
    }

    public static void refreshDisplay() {
        System.out.println(ProgramStateManager.getCurrentState().name());
        primaryStage.setScene(ProgramStateManager.getCurrentState().getScene());
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ForbiddenIsland.primaryStage = primaryStage;
        primaryStage.setTitle("Forbidden Island");
        /*
        GridPane gp = new GridPane();
        List<ArrayList<TileFloodState>> tileStates = new ArrayList<>();
        for (int i = 0; i < 6; i++){
            tileStates.add(new ArrayList<>());
            for (int j = 0; j < 6; j++){
                int finalI = i;
                int finalJ = j;
                Label l = new Label();
                l.setPrefSize(100, 100);
                if (j > 0 && j < 5 && i > 0 && i < 5 || j == 0 && (i == 2 || i == 3) || j == 5 && (i == 2 || i == 3) || i == 0 && (j == 2 || j == 3) || i == 5 && (j == 2 || j == 3)){
                    tileStates.get(finalI).add(TileFloodState.LAND);
                    l.setText("Landed");
                    Color color = TileFloodState.LAND.getColor();
                    l.setStyle("-fx-background-color: rgb(" + color.getRed() * 255 + "," + color.getGreen() * 255 + "," + color.getBlue() * 255 + ");");
                    l.setOnMouseClicked((event) -> {
                        TileFloodState prevValue = tileStates.get(finalI).get(finalJ);
                        l.setText(prevValue.next() + "");
                        Color c = prevValue.next().getColor();
                        l.setStyle("-fx-background-color: rgb(" + c.getRed() * 255 + "," + c.getGreen() * 255 + "," + c.getBlue() * 255 + ");");
                        tileStates.get(finalI).set(finalJ, prevValue.next());
                        System.out.println(tileStates);
                    });

                } else {
                    tileStates.get(finalI).add(TileFloodState.WATER);
                    Color color = TileFloodState.WATER.getColor();
                    l.setStyle("-fx-background-color: rgb(" + color.getRed() * 255 + "," + color.getGreen() * 255 + "," + color.getBlue() * 255 + ");");
                }
                gp.add(l, finalJ, finalI);
            }
        }
        gp.setGridLinesVisible(true);
        Scene s = new Scene(gp, 600, 400);
        */
        for (ProgramState ps : ProgramState.values()){
            try {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(ForbiddenIsland.class.getResource("/fxml/" + ps.name().toLowerCase() + ".fxml")));
                AnchorPane ap = loader.load();
                ps.setPane(ap);
                if (ps.name().equalsIgnoreCase("BOARD")){
                    BoardStateGraphicsInitializer.setBg(loader.getController());
                    FXMLLoader loader1 = new FXMLLoader(Objects.requireNonNull(ForbiddenIsland.class.getResource("/fxml/player.fxml")));
                    AnchorPane ap1 = loader1.load();
                    System.out.println("3247897329432");
                    BoardStateGraphicsInitializer.setPg(loader1.getController());
                    StackPane s = new StackPane(ap, ap1);
                    ps.setPane(s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        ProgramStateManager.goToState(ProgramState.INPUT);
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

