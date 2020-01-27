package TicTacToe;

import TicTacToe.exception.FieldTakenException;
import TicTacToe.model.FieldState;
import TicTacToe.model.TTTResult;
import TicTacToe.model.TicTacToe;
import TicTacToe.consoleView.TicTacToeView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

import static TicTacToe.model.TicTacToe.BOARD_SIZE;
import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {
    private TicTacToe game;
    private TicTacToeView view;

    @BeforeEach
    public void setup() {
        //given
        //todo
        view = mock(TicTacToeView.class);


        game = new TicTacToe(view);
    }

    @Test
    public void atBeginningFieldAreEmpty() {
        //when
        FieldState state = game.getFieldStatus(0, 0);

        //then
        assertEquals(FieldState.EMPTY, state);

    }


    @Test
    public void atBeginningAllFieldsAreEmpty() {
        FieldState states;

        //when
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                states = game.getFieldStatus(i, j);

                //then
                assertEquals(FieldState.EMPTY, states);

            }
        }
    }

    @Test
    public void firstActionOnBoardIsX() {

        //when
        game.putMark(0, 0);

        //then
        assertEquals(FieldState.X, game.getFieldStatus(0, 0));

    }

    @Test
    public void secondActionOnBoardIsO() {

        //when
        game.putMark(0, 0);
        game.putMark(0, 1);

        //then
        assertEquals(FieldState.O, game.getFieldStatus(0, 1));

    }

    @Test
    public void putAgainInTheSameFieldThrowsException() {
        //given
        game.putMark(0, 0);


        // when and then
        assertThrows(FieldTakenException.class, () -> game.putMark(0, 0));

    }

    /*@Test
    public void dummnyBoardTest(){
        game.putMark(0,0);
        game.putMark(1,1);
        game.putMark(2,2);
        game.refreshView();
    }*/

    @Test
    public void putMarkTooMuchOnTopShouldThrow() {
        //when then
    assertThrows(IllegalArgumentException.class,()->game.putMark(0,-1));
    }

    @Test
    public void putMarkTooMuchOnLeftShouldThrow() {
        //when then
        assertThrows(IllegalArgumentException.class,()->game.putMark(-1,0));
    }

    @Test
    public void putMarkTooMuchOnRightShouldThrow() {
        //when then
        assertThrows(IllegalArgumentException.class,()->game.putMark(3,0));
    }

    @Test
    public void putMarkTooMuchOnBottonShouldThrow() {
        //when then
        assertThrows(IllegalArgumentException.class,()->game.putMark(0,3));
    }

    @Test
    void shouldInformIfGameIsWonByPlayer1Row() {
        //given
        game.putMark(0, 0);
        game.putMark(1, 0);
        game.putMark(0, 1);
        game.putMark(2, 0);
        game.putMark(0, 2);
        //game simulation PlayerX Won
        assertEquals(TTTResult.PLAYER_X_WIN, game.checkGameResult());
    }
    @Test
    void shouldInformIfGameIsWonByPlayer2Row() {
        //given
        game.putMark(1, 0);
        game.putMark(0, 0);
        game.putMark(1, 1);
        game.putMark(2, 0);
        game.putMark(1, 2);
        //game simulation PlayerX Won
        assertEquals(TTTResult.PLAYER_X_WIN, game.checkGameResult());
    }
    @Test
    void shouldInformIfGameIsWonByPlayer3Row() {
        //given
        game.putMark(2, 0);
        game.putMark(0, 0);
        game.putMark(2, 1);
        game.putMark(1, 0);
        game.putMark(2, 2);
        //game simulation PlayerX Won
        assertEquals(TTTResult.PLAYER_X_WIN, game.checkGameResult());
    }
@Test
    void shouldInformIfGameIsWonByPlayerDiagonaly() {
        //given
        game.putMark(0, 0);
        game.putMark(1, 0);
        game.putMark(1, 1);
        game.putMark(2, 0);
        game.putMark(2, 2);
        //game simulation PlayerX Won
        assertEquals(TTTResult.PLAYER_X_WIN, game.checkGameResult());
    }

    @Test
    public void gameresultIsPendingAtStart(){
        //when
        TTTResult result = game.checkGameResult();

        //then
        assertEquals(TTTResult.PENDING,result);
    }

    @Test
    public void gameresultIsPendingDuringGame(){
        //when
        game.putMark(0,0);
        game.putMark(0,1);
        game.putMark(0,2);
        TTTResult result = game.checkGameResult();

        //then
        assertEquals(TTTResult.PENDING,result);
    }

    @Test
    public void gameresultIsDrawWhenIsDraw(){
        //when
        game.putMark(1, 1);
        game.putMark(0, 0);
        game.putMark(0, 1);
        game.putMark(0, 2);
        game.putMark(1, 0);
        game.putMark(1, 2);
        game.putMark(2, 0);
        game.putMark(2, 1);
        game.putMark(2, 2);

        //then
        TTTResult result = game.checkGameResult();

        //then
        assertEquals(TTTResult.DRAW,result);
    }



}
