package TicTacToe.model;


import java.util.ArrayList;
import java.util.Random;

public class CompLogic implements TTTPlayer {
    private Random random = new Random();
    private TicTacToe game;
    private FieldState[][] compLogicBoard;
    private FieldState actualFieldState;
    private ArrayList<TTTPosition> cornerPosition = new ArrayList<>();
    private ArrayList<TTTPosition> edgePosition = new ArrayList<>();

    public CompLogic(TicTacToe game) {
        this.game = game;
        compLogicBoard = new FieldState[game.getBoard().length][game.getBoard().length];
        cornerPosition.add(new TTTPosition(0, 0));
        cornerPosition.add(new TTTPosition(2, 0));
        cornerPosition.add(new TTTPosition(0, 2));
        cornerPosition.add(new TTTPosition(2, 2));
        edgePosition.add(new TTTPosition(1, 0));
        edgePosition.add(new TTTPosition(0, 1));
        edgePosition.add(new TTTPosition(2, 1));
        edgePosition.add(new TTTPosition(1, 2));
    }

    private void copyBoard() {
        for (int i = 0; i < compLogicBoard.length; i++) {
            for (int j = 0; j < compLogicBoard[i].length; j++) {
                compLogicBoard[i][j] = game.getFieldStatus(j, i);
            }
        }

    }

    public void update(FieldState state, TicTacToe game) {
        actualFieldState = state;
        this.game = game;
        copyBoard();
    }

    private Boolean isMovesLeft() {
        for (int i = 0; i < compLogicBoard.length; i++)
            for (int j = 0; j < compLogicBoard[i].length; j++)
                if (compLogicBoard[i][j] == FieldState.EMPTY)
                    return true;
        return false;
    }


    private int evaluate() {
        // Checking for Rows for X or O victory.
        for (int row = 0; row < compLogicBoard.length; row++) {
            if (compLogicBoard[row][0] == compLogicBoard[row][1] &&
                    compLogicBoard[row][1] == compLogicBoard[row][2]) {
                if (compLogicBoard[row][0] == actualFieldState)
                    return +10;
                else if (compLogicBoard[row][0] != FieldState.EMPTY)
                    return -10;
            }
        }
        // Checking for Columns for X or O victory.
        for (int col = 0; col < compLogicBoard.length; col++) {
            if (compLogicBoard[0][col] == compLogicBoard[1][col] &&
                    compLogicBoard[1][col] == compLogicBoard[2][col]) {
                if (compLogicBoard[0][col] == actualFieldState)
                    return +10;
                else if (compLogicBoard[0][col] != FieldState.EMPTY)
                    return -10;
            }
        }
        // Checking for Diagonals for X or O victory.
        if (compLogicBoard[0][0] == compLogicBoard[1][1] && compLogicBoard[1][1] == compLogicBoard[2][2]) {
            if (compLogicBoard[0][0] == actualFieldState)
                return +10;
            else if (compLogicBoard[0][0] != FieldState.EMPTY)
                return -10;
        }
        if (compLogicBoard[0][2] == compLogicBoard[1][1] && compLogicBoard[1][1] == compLogicBoard[2][0]) {
            if (compLogicBoard[0][2] == actualFieldState)
                return +10;
            else if (compLogicBoard[0][2] != FieldState.EMPTY)
                return -10;
        }
        // Else if none of them have won then return 0
        return 0;
    }


    private int miniMax(int depth, boolean isMax) {
        int score = evaluate();

        // If Maximizer has won the game return his/her
        // evaluated score
        if (score == 10)
            return score;

        // If Minimizer has won the game return his/her
        // evaluated score
        if (score == -10)
            return score;

        // If there are no more moves and no winner then
        // it is a tie
        if (isMovesLeft() == false)
            return 0;

        // If this maximizer's move
        if (isMax) {
            int best = -1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (compLogicBoard[i][j] == FieldState.EMPTY) {
                        // Make the move
                        compLogicBoard[i][j] = actualFieldState;

                        // Call minimax recursively and choose
                        // the maximum value
                        best = Math.max(best, miniMax(depth + 1, !isMax));

                        // Undo the move
                        compLogicBoard[i][j] = FieldState.EMPTY;
                    }
                }
            }
            return best;
        }
        // If this minimizer's move
        else {
            int best = 1000;

            // Traverse all cells
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    // Check if cell is empty
                    if (compLogicBoard[i][j] == FieldState.EMPTY) {
                        // Make the move
                        if (actualFieldState == FieldState.X)
                            compLogicBoard[i][j] = FieldState.O;
                        else
                            compLogicBoard[i][j] = FieldState.X;

                        // Call minimax recursively and choose
                        // the minimum value
                        best = Math.min(best, miniMax(depth + 1, !isMax));

                        // Undo the move
                        compLogicBoard[i][j] = FieldState.EMPTY;
                    }
                }
            }
            return best;
        }
    }


    // This will return the best possible move for the player
    private TTTPosition findBestMove() {
        int bestVal = -1000;
        TTTPosition bestMove = new TTTPosition(-1, -1);

        // Traverse all cells, evaluate minimax function for
        // all empty cells. And return the cell with optimal
        // value.
        for (int i = 0; i < compLogicBoard.length; i++) {
            for (int j = 0; j < compLogicBoard[i].length; j++) {
                // Check if cell is empty
                if (compLogicBoard[i][j] == FieldState.EMPTY) {
                    // Make the move
                    compLogicBoard[i][j] = actualFieldState;

                    // compute evaluation function for this
                    // move.
                    int moveVal = miniMax(0, false);

                    // Undo the move
                    compLogicBoard[i][j] = FieldState.EMPTY;

                    // If the value of the current move is
                    // more than the best value, then update
                    // best/
                    if (moveVal > bestVal) {
                        bestMove.setX(j);
                        bestMove.setY(i);
                        bestVal = moveVal;
                    }
                }
            }
        }

//        System.out.printf("\nThe value of the best move is %d. The move: x: %d y: %d\n",
//                bestVal, bestMove.getX(), bestMove.getY());

        if (bestMove.getX() == -1) {
            bestMove = randomShoot();
        }

        return bestMove;
    }


    private int countEmptyFields() {
        int count = 0;
        for (int i = 0; i < compLogicBoard.length; i++) {
            for (int j = 0; j < compLogicBoard.length; j++) {
                if (compLogicBoard[i][j] == FieldState.EMPTY)
                    count++;
            }
        }
        return count;
    }

    private TTTPosition randomShoot() {
        TTTPosition move = new TTTPosition(-1, -1);
        do {
            move.setX(random.nextInt(3));
            move.setY(random.nextInt(3));
        } while (compLogicBoard[move.getY()][move.getX()] != FieldState.EMPTY);
        return move;
    }

    @Override
    public TTTPosition getMarkPosition(BoardModel board) {
        int countOfEmptyField = countEmptyFields();
        int randomNumber;
        switch (countOfEmptyField) {
            case 9:
                randomNumber = random.nextInt(7);
                if (randomNumber == 6)
                    return edgePosition.get(random.nextInt(edgePosition.size()));
                else if (randomNumber < 3)
                    return cornerPosition.get(random.nextInt(cornerPosition.size()));
                else
                    return new TTTPosition(1, 1);
            case 8:
                randomNumber = random.nextInt(7);
                if (randomNumber > 4) {
                    return randomShoot();
                } else
                    return findBestMove();
            default:
                return findBestMove();
        }
    }


}
