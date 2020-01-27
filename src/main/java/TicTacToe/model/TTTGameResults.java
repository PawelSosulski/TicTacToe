package TicTacToe.model;

public class TTTGameResults {

    private int firstWin;

    private int secondWin;

    private int drawGame;

    public TTTGameResults(int firstWin, int secondWin, int drawGame) {
        this.firstWin = firstWin;
        this.secondWin = secondWin;
        this.drawGame = drawGame;
    }

    public void update(String whichWin) {
        switch (whichWin.toLowerCase()) {
            case "first":
                firstWin++;
                break;
            case "second":
                secondWin++;
                break;
            case "draw":
                drawGame++;
                break;
            default:
                break;
        }
    }

    public int getFirstWin() {
        return firstWin;
    }

    public int getSecondWin() {
        return secondWin;
    }

    public int getDrawGame() {
        return drawGame;
    }
}