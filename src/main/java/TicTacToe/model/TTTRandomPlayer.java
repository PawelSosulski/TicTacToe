package TicTacToe.model;

import java.util.Random;

public class TTTRandomPlayer implements TTTPlayer {


    @Override
    public TTTPosition getMarkPosition(BoardModel board) {
        Random random = new Random();
        while (true) {
            int newValue = random.nextInt(9) + 1;
            TTTPosition newPosition = getPositionWithMagic(newValue);
            if (!checkIfTaken(board,newPosition)) {
                return  newPosition;
            }
        }
    }

    @Override
    public void update(FieldState o, TicTacToe game) {

    }


    private boolean checkIfTaken(BoardModel board,TTTPosition position) {
        if (board.getFieldStatus(position.getX(),position.getY())==FieldState.EMPTY){
            return false;
        }
        return true;
    }

}
