package TicTacToe.consoleView;


import TicTacToe.model.BoardModel;

import static TicTacToe.model.TicTacToe.BOARD_SIZE;


public class ConsoleTTTView implements TicTacToeView {
    @Override
    public void printBoard(BoardModel boardModel) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(boardModel.getFieldStatus(j, i));
                if (j<BOARD_SIZE-1) {
                    System.out.print("|");
                }
            }
            System.out.println();
            if (i<BOARD_SIZE-1)
            System.out.println("-+-+-");
        }
        System.out.println();
    }
}
