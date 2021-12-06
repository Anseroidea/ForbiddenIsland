package graphics;

import app.ForbiddenIsland;
import app.ProgramState;
import app.ProgramStateManager;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.ResourceBundle;

public class Rules implements Initializable {
    public ImageView rulesView;
    public Button rulesButton;
    public Button pdfButton;
    public Button backButton;
    public Button nextButton;
    public Button exitButton;
    private int currentIndex;
    private BufferedImage[] pages;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rulesButton.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        pdfButton.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        backButton.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        nextButton.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        exitButton.setFont(ForbiddenIsland.getForbiddenIslandFont(30));
        backButton.setDisable(true);
        pages = new BufferedImage[9];
        for (int i = 1; i <= pages.length; i++){
            try {
                pages[i - 1] = ImageIO.read(Objects.requireNonNull(this.getClass().getClassLoader().getResourceAsStream("images/screens/rules-" + i + ".png")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        refreshView();
    }

    public void rules(ActionEvent actionEvent) {
        try {
            Desktop.getDesktop().browse(new URL("https://www.fgbradleys.com/rules/rules2/ForbiddenIsland-rules.pdf").toURI());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public void openPDF(ActionEvent actionEvent) throws IOException {
        InputStream manualAsStream = getClass().getClassLoader().getResourceAsStream("rules.pdf");

        Path tempOutput = Files.createTempFile("TempManual", ".pdf");
        tempOutput.toFile().deleteOnExit();

        Files.copy(manualAsStream, tempOutput, StandardCopyOption.REPLACE_EXISTING);

        File userManual = new File(tempOutput.toFile().getPath());
        if (userManual.exists())
        {
            Desktop.getDesktop().open(userManual);
        }
    }

    public void back(ActionEvent actionEvent) {
        currentIndex--;
        refreshButtons();
        refreshView();
    }

    public void next(ActionEvent actionEvent) {
        currentIndex++;
        refreshButtons();
        refreshView();
    }

    public void refreshButtons(){
        if (currentIndex == 0){
            backButton.setDisable(true);
        } else if (currentIndex == pages.length - 1){
            nextButton.setDisable(true);
        } else {
            backButton.setDisable(false);
            nextButton.setDisable(false);
        }
    }

    public void refreshView(){
        rulesView.setImage(SwingFXUtils.toFXImage(pages[currentIndex], null));
    }

    public void exit(ActionEvent actionEvent) {
        ProgramStateManager.goToState(ProgramState.MAINMENU);
        ForbiddenIsland.refreshDisplay();
    }
}
