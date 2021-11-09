import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class
ForbiddenIsland extends Application {

    private static final Stage primaryStage = new Stage();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
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
        AnchorPane ap = FXMLLoader.load(Objects.requireNonNull(ForbiddenIsland.class.getResource("/fxml/player.fxml")));
        Scene s = new Scene(ap, 1920, 1080);
        primaryStage.setScene(s);
        primaryStage.setFullScreen(true);
        primaryStage.show();
    }
}

