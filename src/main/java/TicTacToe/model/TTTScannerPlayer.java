package TicTacToe.model;

import java.util.Scanner;

public class TTTScannerPlayer implements TTTPlayer {
    @Override
    public TTTPosition getMarkPosition(BoardModel board) {
        Scanner scanner = new Scanner(System.in);
        int position;
        do {
            System.out.println("Graczu, podaj pozycję od 1 do 9");
            try {
                String input = scanner.next();
                position = Integer.valueOf(input);
                if (position > 0 && position < 10)
                    return getPositionWithMagic(position);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            System.out.println("Źle!");
        } while (true);

    }

    @Override
    public void update(FieldState o, TicTacToe game) {

    }


}
