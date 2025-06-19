package boardgame.game;

import boardgame.model.BoardGameState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class GameController {

    @FXML
    private GridPane grid;

    @FXML
    private TextField numberOfMovesField;

    @FXML
    public void initialize() {
        initializeCells();
    }

    public void initializeCells() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane cell = createCell(i, j);
                grid.add(cell, i, j);
            }
        }
    }

    public StackPane createCell(int row, int column) {
        StackPane cell = new StackPane();
        Label label = new Label(((Integer) BoardGameState.table[row][column]).toString());
        if (row == 0 && column == 0) {
            ImageView figure1Img = new ImageView(new Image(getClass().getResourceAsStream("/figure1.png")));
            ImageView figure2Img = new ImageView(new Image(getClass().getResourceAsStream("/figure2.png")));

            figure1Img.setFitHeight(75);
            figure1Img.setFitWidth(75);
            figure2Img.setFitHeight(75);
            figure2Img.setFitWidth(75);

            cell.getChildren().add(figure1Img);
            cell.getChildren().add(figure2Img);
        }
        cell.getChildren().add(label);
        return cell;
    }
}


