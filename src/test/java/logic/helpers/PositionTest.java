package logic.helpers;

import logic.game.Game;
import logic.game.PlayingField;
import org.junit.Test;

import static logic.helpers.Constants.board_2x2;
import static logic.helpers.Constants.board_4x5;
import static org.junit.Assert.assertEquals;

public class PositionTest {





    /**
     * ########################################### without overflow ###########################################
     */

    @Test
    public void Test_No_Overflow_Pos_00() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_00");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(0, 0);
        Position expectedAbove = null;
        Position expectedNext = new Position(0, 1);
        Position expectedLower = new Position(1, 0);
        Position expectedPrev = null;


        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_No_Overflow_Pos_01() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_01");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(0, 1);
        Position expectedAbove = null;
        Position expectedNext = new Position(0, 2);
        Position expectedLower = new Position(1, 1);
        Position expectedPrev = new Position(0, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }

    @Test
    public void Test_No_Overflow_Pos_04() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_04");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(0, 4);
        Position expectedAbove = null;
        Position expectedNext = null;
        Position expectedLower = new Position(1, 4);
        Position expectedPrev = new Position(0, 3);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_No_Overflow_Pos_10() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_10");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(1, 0);
        Position expectedAbove = new Position(0, 0);
        ;
        Position expectedNext = new Position(1, 1);
        ;
        Position expectedLower = new Position(2, 0);
        Position expectedPrev = null;

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_No_Overflow_Pos_11() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_11");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(1, 1);
        Position expectedAbove = new Position(0, 1);
        ;
        Position expectedNext = new Position(1, 2);
        ;
        Position expectedLower = new Position(2, 1);
        Position expectedPrev = new Position(1, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_No_Overflow_Pos_14() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_14");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(1, 4);
        Position expectedAbove = new Position(0, 4);
        Position expectedNext = null;
        Position expectedLower = new Position(2, 4);
        Position expectedPrev = new Position(1, 3);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_No_Overflow_Pos_30() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_30");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(3, 0);
        Position expectedAbove = new Position(2, 0);
        Position expectedNext = new Position(3, 1);
        Position expectedLower = null;
        Position expectedPrev = null;

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));


        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_No_Overflow_Pos_33() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_33");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(3, 3);
        Position expectedAbove = new Position(2, 3);
        Position expectedNext = new Position(3, 4);
        Position expectedLower = null;
        Position expectedPrev = new Position(3, 2);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");
    }


    @Test
    public void Test_No_Overflow_Pos_34() throws Exception {
        System.out.println("Executing test name: Test_No_Overflow_Pos_34");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(3, 4);
        Position expectedAbove = new Position(2, 4);
        Position expectedNext = null;
        Position expectedLower = null;
        Position expectedPrev = new Position(3, 3);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.FALSE));

        System.out.println("____________________________________________________\n");
    }


    /**
     * ########################################### with overflow ###########################################
     */

    @Test
    public void Test_With_Overflow_Pos_00() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_00");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5


        Position toCheck = new Position(0, 0);
        Position expectedAbove = new Position(3, 0);
        Position expectedNext = new Position(0, 1);
        Position expectedLower = new Position(1, 0);
        Position expectedPrev = new Position(0, 4);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));

        System.out.println("____________________________________________________\n");
    }


    @Test
    public void Test_With_Overflow_Pos_01() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_01");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(0, 1);
        Position expectedAbove = new Position(3, 1);
        Position expectedNext = new Position(0, 2);
        Position expectedLower = new Position(1, 1);
        Position expectedPrev = new Position(0, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));

        System.out.println("____________________________________________________\n");
    }


    @Test
    public void Test_With_Overflow_Pos_04() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_04");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(0, 4);
        Position expectedAbove = new Position(3, 4);
        Position expectedNext = new Position(0, 0);
        Position expectedLower = new Position(1, 4);
        Position expectedPrev = new Position(0, 3);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_With_Overflow_Pos_10() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_10");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(1, 0);
        Position expectedAbove = new Position(0, 0);
        Position expectedNext = new Position(1, 1);
        Position expectedLower = new Position(2, 0);
        Position expectedPrev = new Position(1, 4);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }

    @Test
    public void Test_With_Overflow_Pos_11() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_11");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(1, 1);
        Position expectedAbove = new Position(0, 1);
        Position expectedNext = new Position(1, 2);
        Position expectedLower = new Position(2, 1);
        Position expectedPrev = new Position(1, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_With_Overflow_Pos_14() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_14");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(1, 4);
        Position expectedAbove = new Position(0, 4);
        Position expectedNext = new Position(1, 0);
        Position expectedLower = new Position(2, 4);
        Position expectedPrev = new Position(1, 3);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }

    @Test
    public void Test_With_Overflow_Pos_30() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_30");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(3, 0);
        Position expectedAbove = new Position(2, 0);
        Position expectedNext = new Position(3, 1);
        Position expectedLower = new Position(0, 0);
        Position expectedPrev = new Position(3, 4);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }

    @Test
    public void Test_With_Overflow_Pos_33() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_33");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(3, 3);
        Position expectedAbove = new Position(2, 3);
        Position expectedNext = new Position(3, 4);
        Position expectedLower = new Position(0, 3);
        Position expectedPrev = new Position(3, 2);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_With_Overflow_Pos_34() throws Exception {
        System.out.println("Executing test name: Test_With_Overflow_Pos_34");
        PlayingField playingField = new PlayingField(board_4x5, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 4
        int noOfColumns = playingField.getNoOfBoardColumns(); // 5

        Position toCheck = new Position(3, 4);
        Position expectedAbove = new Position(2, 4);
        Position expectedNext = new Position(3, 0);
        Position expectedLower = new Position(0, 4);
        Position expectedPrev = new Position(3, 3);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 4, 5, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 4, 5, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_Quadratic_Board_No_Overflow_Pos_00() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_No_Overflow_Pos_00");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(0, 0);
        Position expectedAbove = null;
        Position expectedNext = new Position(0, 1);
        Position expectedLower = new Position(1, 0);
        Position expectedPrev = null;

        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.FALSE));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_Quadratic_Board_No_Overflow_Pos_11() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_No_Overflow_Pos_11");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(1, 1);
        Position expectedAbove = new Position(0, 1);
        Position expectedNext = null;
        Position expectedLower = null;
        Position expectedPrev = new Position(1, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.FALSE));


        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_Quadratic_Board_No_Overflow_Pos_10() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_No_Overflow_Pos_10");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(1, 0);
        Position expectedAbove = new Position(0, 0);
        Position expectedNext = new Position(1, 1);
        Position expectedLower = null;
        Position expectedPrev = null;

        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.FALSE));

        System.out.println("____________________________________________________\n");


    }


    @Test
    public void Test_Quadratic_Board_No_Overflow_Pos_01() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_No_Overflow_Pos_01");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(0, 1);
        Position expectedAbove = null;
        Position expectedNext = null;
        Position expectedLower = new Position(1, 1);
        Position expectedPrev = new Position(0, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.FALSE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.FALSE));

        System.out.println("____________________________________________________\n");


    }

    @Test
    public void Test_Quadratic_Board_With_Overflow_Pos_00() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_With_Overflow_Pos_00");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(0, 0);
        Position expectedAbove = new Position(1, 0);
        Position expectedNext = new Position(0, 1);
        Position expectedLower = new Position(1, 0);
        Position expectedPrev =  new Position(0, 1);
        Position expectedAboveOfPrev =  new Position(1, 1);


        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedAboveOfPrev, Position.getAboveOfPrev(toCheck, 2, 2));


        System.out.println("____________________________________________________\n");

    }

    @Test
    public void Test_Quadratic_Board_With_Overflow_Pos_01() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_With_Overflow_Pos_01");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(0, 1);
        Position expectedAbove = new Position(1, 1);
        Position expectedNext = new Position(0, 0);
        Position expectedLower = new Position(1, 1);
        Position expectedPrev =  new Position(0, 0);


        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.TRUE));


        System.out.println("____________________________________________________\n");

    }

    @Test
    public void Test_Quadratic_Board_With_Overflow_Pos_10() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_With_Overflow_Pos_10");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(1, 0);
        Position expectedAbove = new Position(0, 0);
        Position expectedNext = new Position(1, 1);
        Position expectedLower = new Position(0, 0);
        Position expectedPrev = new Position(1, 1);
        Position expectedAboveOfPrev =  new Position(0, 1);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedAboveOfPrev, Position.getAboveOfPrev(toCheck, 2, 2));

        System.out.println("____________________________________________________\n");

    }


    @Test
    public void Test_Quadratic_Board_With_Overflow_Pos_11() throws Exception {
        System.out.println("Executing test name: Test_Quadratic_Board_With_Overflow_Pos_11");
        PlayingField playingField = new PlayingField(board_2x2, Boolean.TRUE);
        Game game = new Game(new FakeGUI(), playingField);
        int noOfRows = playingField.getNoOfBoardRows(); // 2
        int noOfColumns = playingField.getNoOfBoardColumns(); // 2


        Position toCheck = new Position(1, 1);
        Position expectedAbove = new Position(0, 1);
        Position expectedNext = new Position(1, 0);
        Position expectedLower = new Position(0, 1);
        Position expectedPrev = new Position(1, 0);

        assertEquals(expectedAbove, Position.getAbove(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedNext, Position.getNext(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedLower, Position.getLower(toCheck, 2, 2, Boolean.TRUE));
        assertEquals(expectedPrev, Position.getPrev(toCheck, 2, 2, Boolean.TRUE));

        System.out.println("____________________________________________________\n");

    }


}
