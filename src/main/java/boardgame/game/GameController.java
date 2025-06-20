package boardgame.game;

import boardgame.model.BoardGameState;
import boardgame.model.Figure;
import boardgame.model.Position;
import common.TwoPhaseMoveState;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.tinylog.Logger;

public class GameController {

    @FXML
    private GridPane grid;

    @FXML
    private TextField numberOfMovesField;

    private BoardGameState gameState = null;

    private Position selectedFrom = null;

    @FXML
    public void initialize() {
        initializeCells();
        gameState = new BoardGameState(new Figure(), new Figure());
    }

    public void initializeCells() {
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

    public StackPane createCell(int row, int column) {
        StackPane cell = new StackPane();
        Label label = new Label(((Integer) BoardGameState.table[row][column]).toString());
        cell.getChildren().add(label);
        cell.setOnMouseClicked(this::handleMouseClick);
        return cell;
    }

    @FXML
    private void handleMouseClick(MouseEvent event) {
        Position clickedPosition = getClickedPosition(event);
        if (selectedFrom == null) {
            handleFirstClick(clickedPosition);
        } else {
            handleSecondClick(clickedPosition);
        }
    }

    private Position getClickedPosition(MouseEvent event) {
        StackPane cell = (StackPane) event.getSource();
        int row = GridPane.getRowIndex(cell);
        int column = GridPane.getColumnIndex(cell);
        return new Position(row, column);
    }

    private void handleFirstClick(Position position) {
        if (gameState.isLegalToMoveFrom(position)) {
            selectedFrom = position;
            Logger.info("Moving from {}", position);
        } else {
            Logger.warn("Can't move from {}", position);
        }
    }

    private void handleSecondClick(Position position) {
        TwoPhaseMoveState.TwoPhaseMove<Position> move = new TwoPhaseMoveState.TwoPhaseMove<>(selectedFrom, position);
        if (gameState.isLegalMove(move)) {
            gameState.makeMove(move);
            Logger.info("Move made: {}", move);
            updateBoard();
        } else {
            Logger.warn("Illegal move: {}", move);
        }
        selectedFrom = null;
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


}


