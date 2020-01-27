package TicTacToe.model;

import TicTacToe.model.FieldState;

public interface BoardModel {

    FieldState getFieldStatus(int x,int y);
}
