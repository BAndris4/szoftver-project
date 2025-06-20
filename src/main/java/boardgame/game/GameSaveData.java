package boardgame.game;

public class GameSaveData {
    public String playerName;
    public int moveCount;
    public boolean solved;

    public GameSaveData() {
    }

    public GameSaveData(String playerName, int moveCount, boolean solved) {
        this.playerName = playerName;
        this.moveCount = moveCount;
        this.solved = solved;
    }
}
