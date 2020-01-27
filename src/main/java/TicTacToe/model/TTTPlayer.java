package TicTacToe.model;

public interface TTTPlayer {
    TTTPosition getMarkPosition(BoardModel board);

    default   TTTPosition getPositionWithMagic(int position) {
        int x = (position - 1) % 3;
        int y = (position - 1) / 3;
        return new TTTPosition(x, y);
    }

    void update(FieldState o, TicTacToe game);

}
