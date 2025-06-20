package boardgame.game;

import boardgame.model.BoardGameState;
import boardgame.model.Figure;
import boardgame.model.Position;
import common.TwoPhaseMoveState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.tinylog.Logger;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameController {

    public String playerName;

    @FXML
    private GridPane grid;

    @FXML
    private TextField numberOfMovesField;

    private int moveCount;

    private BoardGameState gameState = null;

    private StackPane selectedFrom = null;

    @FXML
    private void initialize() {
        initializeCells();
        gameState = new BoardGameState(new Figure(), new Figure());
        moveCount = 0;
        numberOfMovesField.setText("0");
    }

    private void initializeCells() {
        grid.getChildren().clear();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                StackPane cell = createCell(i, j);
                grid.add(cell, j, i);
            }
        }
        addFigureToCell(new Position(0, 0), "/figure1.png");
        addFigureToCell(new Position(0, 0), "/figure2.png");
        Logger.info("Table cells are initialized");
    }

    private StackPane createCell(int row, int column) {
        StackPane cell = new StackPane();
        cell.getStyleClass().add("cell");
        Label label;
        if (row == 7 && column == 7) {
            label = new Label("*");
        } else {
            label = new Label(((Integer) BoardGameState.table[row][column]).toString());
        }
        cell.getChildren().add(label);
        cell.setOnMouseClicked(this::handleMouseClick);
        return cell;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        StackPane clickedCell = (StackPane) event.getSource();
        if (selectedFrom == null) {
            handleFirstClick(clickedCell);
        } else {
            handleSecondClick(clickedCell);
        }
    }

    private Position getClickedPosition(StackPane cell) {
        int row = GridPane.getRowIndex(cell);
        int column = GridPane.getColumnIndex(cell);
        return new Position(row, column);
    }

    private void handleFirstClick(StackPane cell) {
        Position position = getClickedPosition(cell);
        if (gameState.isLegalToMoveFrom(position)) {
            cell.getStyleClass().add("selected");
            selectedFrom = cell;
            Logger.info("Moving from {}", position);
        } else {
            Logger.error("Can't move from {}", position);
        }
    }

    private void handleSecondClick(StackPane cell) {
        Position position = getClickedPosition(cell);
        TwoPhaseMoveState.TwoPhaseMove<Position> move = new TwoPhaseMoveState.TwoPhaseMove<>(getClickedPosition(selectedFrom), position);
        if (gameState.isLegalMove(move)) {
            gameState.makeMove(move);
            Logger.info("Move made: {}", move);
            moveCount++;
            numberOfMovesField.setText(String.valueOf(moveCount));
            updateBoard();
            handleSolved();
            if (isOnForbiddenCell(gameState.getFigure1().position()) || isOnForbiddenCell(gameState.getFigure2().position())) {
                showGameLostAlert();
            }
        } else {
            Logger.error("Illegal move: {}", move);
        }
        selectedFrom.getStyleClass().remove("selected");
        selectedFrom = null;
    }

    private boolean isOnForbiddenCell(Position pos) {
        return BoardGameState.table[pos.row()][pos.column()] == 0;
    }

    private void updateBoard() {
        for (var node : grid.getChildren()) {
            if (node instanceof StackPane cell) {
                cell.getChildren().removeIf(child -> child instanceof ImageView);
            }
        }

        addFigureToCell(gameState.getFigure1().position(), "/figure1.png");
        addFigureToCell(gameState.getFigure2().position(), "/figure2.png");
    }

    private void addFigureToCell(Position position, String imagePath) {
        ImageView figureImg = new ImageView(new Image(getClass().getResourceAsStream(imagePath)));
        figureImg.setFitWidth(75);
        figureImg.setFitHeight(75);

        for (var node : grid.getChildren()) {
            if (node instanceof StackPane cell) {
                int row = GridPane.getRowIndex(cell);
                int column = GridPane.getColumnIndex(cell);

                if (row == position.row() && column == position.column()) {
                    cell.getChildren().add(figureImg);
                    break;
                }
            }
        }
    }

    private void handleSolved(){
        if (gameState.isSolved()){
            Platform.runLater(this::showSolvedAlert);
        }
    }

    private void showSolvedAlert() {
        saveGameStateToJson(true);
        final var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over");
        alert.setContentText(String.format("Congratulations %s, you have solved the puzzle in %d moves!", playerName, moveCount));
        alert.showAndWait();
        initialize();
    }

    private void showGameLostAlert() {
        saveGameStateToJson(false);
        final var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Game Over");
        alert.setContentText("You stepped on zero!");
        alert.showAndWait();
        initialize();
    }

    private void saveGameStateToJson(boolean solved) {
        ObjectMapper mapper = new ObjectMapper();
        GameSaveData saveData = new GameSaveData(playerName, moveCount, solved);

        File file = new File("game_save.json");
        List<GameSaveData> list;

        try {
            if (file.exists() && file.length() > 0) {
                list = mapper.readValue(file, new TypeReference<List<GameSaveData>>() {});
            } else {
                list = new ArrayList<>();
            }

            list.add(saveData);

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, list);

        } catch (IOException e) {
            Logger.error("Error saving game state: {}", e.getMessage());
        }
    }
}


