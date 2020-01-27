package TicTacToe.model;

public enum FieldState {
    EMPTY(" "),
    X("X"),
    O("O");

    private String symbol;

    FieldState(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }



}
