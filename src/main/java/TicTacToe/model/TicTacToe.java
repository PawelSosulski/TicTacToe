package TicTacToe.model;

import TicTacToe.exception.FieldTakenException;
import TicTacToe.consoleView.TicTacToeView;

import java.util.ArrayList;
import java.util.List;

public class TicTacToe implements BoardModel {
    private boolean isPlayerXTurn = true;
    public final static int BOARD_SIZE = 3;
    private FieldState[][] board;
    private TicTacToeView view;


    public TicTacToe(TicTacToeView view) {
        this.board = new FieldState[BOARD_SIZE][BOARD_SIZE];
        this.view = view;
        initBoard();
    }


    @Override
    public FieldState getFieldStatus(int x, int y) {
        return board[y][x];
    }

    private void initBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = FieldState.EMPTY;
            }
        }
    }


    public void putMark(int x, int y) throws FieldTakenException, IllegalArgumentException {
        if (x >= BOARD_SIZE || y >= BOARD_SIZE || x < 0 || y < 0)
            throw new IllegalArgumentException();

        if (getFieldStatus(x, y) != FieldState.EMPTY)
            throw new FieldTakenException(x, y);

        if (isPlayerXTurn) {
            board[y][x] = FieldState.X;
        } else {
            board[y][x] = FieldState.O;
        }
        prepareNextStep();
    }

    public FieldState[][] getBoard() {
        return board;
    }

    private void prepareNextStep() {
        isPlayerXTurn = !isPlayerXTurn;
    }


    public void refreshView() {
        view.printBoard(this);
    }


    public TTTResult checkGameResult() {

        if (isPlayerXWin()) {
            return TTTResult.PLAYER_X_WIN;
        } else if (isPlayerOWin()) {
            return TTTResult.PLAYER_O_WIN;
        } else {
            boolean isAnyEmpty = false;

            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    if (getFieldStatus(i, j) == FieldState.EMPTY) {
                        isAnyEmpty = true;
                    }
                }
            }
            if (isAnyEmpty)
                return TTTResult.PENDING;
            else
                return TTTResult.DRAW;
        }
    }

    private boolean isPlayerXWin() {
        FieldState checkedField = FieldState.X;
        return isPlayerWin(checkedField);
    }

    private boolean isPlayerOWin() {
        FieldState checkedField = FieldState.O;
        return isPlayerWin(checkedField);
    }


    private boolean isPlayerWin(FieldState state) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (getFieldStatus(i, 0) == state
                    && getFieldStatus(i, 0) == getFieldStatus(i, 1)
                    && getFieldStatus(i, 1) == getFieldStatus(i, 2))
                return true;
            if (getFieldStatus(0, i) == state
                    && getFieldStatus(0, i) == getFieldStatus(1, i)
                    && getFieldStatus(1, i) == getFieldStatus(2, i))
                return true;
        }
        if (getFieldStatus(0, 0) == state
                && getFieldStatus(0, 0) == getFieldStatus(1, 1)
                && getFieldStatus(1, 1) == getFieldStatus(2, 2))
            return true;
        if (getFieldStatus(2, 0) == state
                && getFieldStatus(2, 0) == getFieldStatus(1, 1)
                && getFieldStatus(1, 1) == getFieldStatus(0, 2))
            return true;

        return false;
    }

    public List<TTTPosition> checkWinningCoordinates() {
        List<TTTPosition> positionList = new ArrayList<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (getFieldStatus(i, 0) == getFieldStatus(i, 1)
                    && getFieldStatus(i, 1) == getFieldStatus(i, 2)) {
                positionList.add(new TTTPosition(i, 0));
                positionList.add(new TTTPosition(i, 1));
                positionList.add(new TTTPosition(i, 2));
                return positionList;
            }
            if (getFieldStatus(0, i) == getFieldStatus(1, i)
                    && getFieldStatus(1, i) == getFieldStatus(2, i)) {
                positionList.add(new TTTPosition(0, i));
                positionList.add(new TTTPosition(1, i));
                positionList.add(new TTTPosition(2, i));
                return positionList;
            }
        }
        if (getFieldStatus(0, 0) == getFieldStatus(1, 1)
                && getFieldStatus(1, 1) == getFieldStatus(2, 2)) {
            positionList.add(new TTTPosition(0,0));
            positionList.add(new TTTPosition(1,1));
            positionList.add(new TTTPosition(2,2));
            return positionList;
        }

        if (getFieldStatus(2, 0) == getFieldStatus(1, 1)
                && getFieldStatus(1, 1) == getFieldStatus(0, 2)) {
            positionList.add(new TTTPosition(2,0));
            positionList.add(new TTTPosition(1,1));
            positionList.add(new TTTPosition(0,2));
            return positionList;
        }


        return positionList;
    }


    public boolean isPlayerXTurn() {
        return isPlayerXTurn;
    }

}