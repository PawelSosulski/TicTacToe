package TicTacToe.exception;

public class FieldTakenException extends RuntimeException {
    public FieldTakenException(String message) {
        super(message);
    }

    public FieldTakenException() {
    }

    public FieldTakenException(int x, int y) {
        super (String.format("%d %d is taken!",x,y));
    }
}
