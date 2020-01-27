package TicTacToe.model;

import TicTacToe.exception.FieldTakenException;
import TicTacToe.model.*;
import TicTacToe.consoleView.ConsoleTTTView;

public class MainTicTacToeGame {
   /* public static void main(String[] args) {
        TTTPlayer playerX = new TTTScannerPlayer();//X
        //TTTPlayer playerO = new TTTScannerPlayer();//O
        TTTPlayer playerO = new TTTRandomPlayer();

        TicTacToe game = new TicTacToe(new ConsoleTTTView());
        TTTPosition position;
        do {
            if (game.isPlayerXTurn()) {
                System.out.println("Ruch gracza X");
                position = playerX.getMarkPosition(game);
            } else {
                System.out.println("Ruch gracza O");
                position = playerO.getMarkPosition(game);
            }
            try {
                game.putMark(position.getX(), position.getY());
                game.refreshView();
            } catch (FieldTakenException field) {
                System.out.println("To pole jest zajęte");
            }
        } while (game.checkGameResult() == TTTResult.PENDING);

    }*/
}
