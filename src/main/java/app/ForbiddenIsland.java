package app;

import board.BoardGame;
import board.WaterLevel;
import graphics.BoardGraphicsInitializer;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
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
                ps.setAnchorPane(ap);
                if (ps.name().equals("BOARD")){
                    BoardGraphicsInitializer.setBg(loader.getController());
                }
            } catch (Exception e){

            }
        }
        ProgramStateManager.goToState(ProgramState.INPUT);
        AnchorPane ap = ProgramStateManager.getCurrentState().getAnchorPane();
        Scene s = new Scene(ap, 1920, 1080);
        primaryStage.setScene(s);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }

    public static BoardGame getBoard(){
        return board;
    }
}

