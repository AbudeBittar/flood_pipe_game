package logic.ai;

import logic.ai.CellGenerator;
import logic.cells.Cell;
import logic.cells.CurvePipe;
import logic.cells.OpeningDirection;
import logic.cells.Pipe;
import logic.cells.StraightPipe;
import logic.cells.T_Pipe;
import logic.helpers.Helper;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static logic.cells.OpeningDirection.*;
import static org.junit.Assert.*;

public class CellGeneratorTest {



    /**
     *
     * @param board
     * @return
     */
    private String getBoardAsString(Cell[][] board){
        String str = "";
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Cell cell = board[i][j];
                str += cell.toString();
            }
            str +='\n';
        }

        return str;
    }



    @Test(expected = AssertionError.class)
    public void ErrorBoard1x1() {
        Cell[][] board = CellGenerator.generateSolvedBoard(1,1, Boolean.FALSE);
    }

    @Test(expected = AssertionError.class)
    public void ErrorBoard1x2() {
        Cell[][] board = CellGenerator.generateSolvedBoard(1,2, Boolean.FALSE);
    }

    @Test(expected = AssertionError.class)
    public void ErrorBoard2x1() {
        Cell[][] board = CellGenerator.generateSolvedBoard(2,1, Boolean.FALSE);
    }

    @Test(expected = AssertionError.class)
    public void ErrorBoard0x0() {
        Cell[][] board = CellGenerator.generateSolvedBoard(0,0, Boolean.FALSE);
    }

    @Test(expected = AssertionError.class)
    public void ErrorBoard3x16() {
        Cell[][] board = CellGenerator.generateSolvedBoard(3,16, Boolean.FALSE);
    }

    @Test(expected = AssertionError.class)
    public void ErrorBoard16x3() {
        Cell[][] board = CellGenerator.generateSolvedBoard(16,3, Boolean.FALSE);
    }

    @Test
    public void QuadraticBoard2x2_NoOverflow() {
        // there is no other use case, because board[0][0] is always equal to '┏' while overflow is deactivated.
        String expectedPuzzle = """
                ┏┓
                ┗┛
                """;

        Cell[][] board = CellGenerator.generateSolvedBoard(2,2, Boolean.FALSE);
        Helper.printBoard(board);

        assertEquals("Quadratic Board 2x2, overflow mode is deactivated", expectedPuzzle, getBoardAsString(board));

    }

    @Test
    public void QuadraticBoard3x3_NoOverflow() {
        /**
         *  ┏┳┓     ┏┓╿     ┏━┓     ┏━┓     ┏┳┓
         *  ┗┫┃     ┣┛┃     ┣━┫     ┃┏┛     ┃┃┃
         *  ■┗┛     ┗━┛     ┗━┛     ┗┛■     ┗┛╽
         *
         */

        Cell[][] board = CellGenerator.generateSolvedBoard(3,3, Boolean.FALSE);
        Helper.printBoard(board);

    }

    @Test
    public void QuadraticBoard15x15_NoOverflow() {
        for (int i= 0; i < 10; i++){
            System.out.println("Test Nr. " + (i + 1));
            Cell[][] board;
            board = CellGenerator.generateSolvedBoard(15,15, Boolean.FALSE);
            Helper.printBoard(board);
            System.out.println("----------------------------------------------------");
        }

    }


    @Test
    public void QuadraticBoard2x2_WithOverflow() {
        Cell[][] board = CellGenerator.generateSolvedBoard(2,2, Boolean.TRUE);
        Helper.printBoard(board);

    }

    @Test
    public void NonQuadraticBoard2x3_WithOverflow() {
        Cell[][] board = CellGenerator.generateSolvedBoard(2,3, Boolean.TRUE);
        Helper.printBoard(board);

    }


    @Test
    public void NonQuadraticBoard5x10_WithOverflow() {

        for (int i= 0; i < 100; i++) {
            System.out.println("Test Nr. " + (i + 1));
            Cell[][] board;
            board = CellGenerator.generateSolvedBoard(5,10, Boolean.TRUE);
            Helper.printBoard(board);
            System.out.println("----------------------------------------------------");
        }

    }

    @Test
    public void QuadraticBoard8x8_WithOverflow() {

        for (int i= 0; i < 10; i++) {
            System.out.println("Test Nr. " + (i + 1));
            Cell[][] board;
            board = CellGenerator.generateSolvedBoard(8,8, Boolean.TRUE);
            Helper.printBoard(board);
            System.out.println();
            System.out.println(board[0][7].getPipeOpenings());
            System.out.println("----------------------------------------------------");
        }

    }

    @Test
    public void QuadraticBoard3x3_WithOverflow() {

        for (int i= 0; i < 10; i++) {
            System.out.println("Test Nr. " + (i + 1));
            Cell[][] board;
            board = CellGenerator.generateSolvedBoard(3,3, Boolean.TRUE);
            Helper.printBoard(board);
            System.out.println();
            System.out.println(board[0][2].getPipeOpenings());
            System.out.println("----------------------------------------------------");
        }

    }


    @Test
    public void NonQuadraticBoard15x15_WithOverflow() {

        for (int i= 0; i < 100; i++) {
            System.out.println("Test Nr. " + (i + 1));
            Cell[][] board;
            board = CellGenerator.generateSolvedBoard(15,15, Boolean.TRUE);
            Helper.printBoard(board);
            System.out.println("----------------------------------------------------");
        }

    }

    @Test
    public void TestValidateBoardSize() {
        assertTrue(CellGenerator.validateBoardSize(2,2));
        assertTrue(CellGenerator.validateBoardSize(3,2));
        assertTrue(CellGenerator.validateBoardSize(3,3));
        assertTrue(CellGenerator.validateBoardSize(4,4));
        assertTrue(CellGenerator.validateBoardSize(14,14));
        assertTrue(CellGenerator.validateBoardSize(15,15));

        assertFalse(CellGenerator.validateBoardSize(1,1));
        assertFalse(CellGenerator.validateBoardSize(4,16));
        assertFalse(CellGenerator.validateBoardSize(16,3));
        assertFalse(CellGenerator.validateBoardSize(19,19));

    }

    @Test
    public void TestGetUnwantedOpeningDirectionsForLastRow_AllUnWanted() {
        Pipe prevPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT)));  // wanted because it is opened to the right
        Pipe abovePipe = new StraightPipe(EnumSet.copyOf(List.of(TOP, BOTTOM))); // wanted because it is opened to the bottom
        Pipe lowerPipe = new StraightPipe(EnumSet.copyOf(List.of(TOP, BOTTOM))); // wanted because it is opened to the top


        OpeningDirection[] expectedUnwanted = new OpeningDirection[]{};
        ArrayList<OpeningDirection> unwanted = CellGenerator.getUnwantedOpeningDirectionsForLastRow(prevPipe, abovePipe, lowerPipe);
        OpeningDirection[] actualUnwanted = unwanted.toArray(new OpeningDirection[0]);

        assertArrayEquals(expectedUnwanted, actualUnwanted);



    }

    @Test
    public void TestGetUnwantedOpeningDirectionsForLastRow_And_CloseLastRowOrColumn_LEFT_TOP_AreUnwanted() {
        Pipe prevPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT)));  // wanted because it is opened to the right
        Pipe abovePipe = new StraightPipe(EnumSet.copyOf(List.of(TOP, BOTTOM))); // wanted because it is opened to the bottom
        Pipe lowerPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))); // unwanted because it is not opened to the top


        OpeningDirection[] expectedUnwanted = new OpeningDirection[]{BOTTOM};
        ArrayList<OpeningDirection> unwanted = CellGenerator.getUnwantedOpeningDirectionsForLastRow(prevPipe, abovePipe, lowerPipe);
        OpeningDirection[] actualUnwanted = unwanted.toArray(new OpeningDirection[0]);

        assertArrayEquals(expectedUnwanted, actualUnwanted);

        Cell pipe = CellGenerator.closeLastRowOrColumn(unwanted);
        System.out.println(pipe.toString());

        ArrayList<Cell> possiblePipes = new ArrayList<>();
        possiblePipes.add(new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))));
        possiblePipes.add(new T_Pipe(EnumSet.copyOf(List.of(LEFT, RIGHT, TOP))));
        possiblePipes.add(new CurvePipe(EnumSet.copyOf(List.of(RIGHT, TOP))));
        possiblePipes.add(new CurvePipe(EnumSet.copyOf(List.of(LEFT, TOP))));

        assertTrue(possiblePipes.contains(pipe));

    }

    @Test
    public void TestGetUnwantedOpeningDirectionsForLastRow_And_CloseLastRowOrColumn_LEFT_isUnwanted() {
        Pipe prevPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT)));  // wanted because it is opened to the right
        Pipe abovePipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))); // unwanted because it is not opened to the bottom
        Pipe lowerPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))); // unwanted because it is not opened to the top


        OpeningDirection[] expectedUnwanted = new OpeningDirection[]{TOP, BOTTOM};
        ArrayList<OpeningDirection> unwanted = CellGenerator.getUnwantedOpeningDirectionsForLastRow(prevPipe, abovePipe, lowerPipe);
        OpeningDirection[] actualUnwanted = unwanted.toArray(new OpeningDirection[0]);

        assertArrayEquals(expectedUnwanted, actualUnwanted);

        Cell pipe = CellGenerator.closeLastRowOrColumn(unwanted);
        System.out.println(pipe.toString());

        ArrayList<Cell> possiblePipes = new ArrayList<>();
        possiblePipes.add(new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))));

        assertTrue(possiblePipes.contains(pipe));

    }

    @Test
    public void TestGetUnwantedOpeningDirectionsForLastRow_And_CloseLastRowOrColumn_TOP_isUnwanted() {
        Pipe prevPipe = new StraightPipe(EnumSet.copyOf(List.of(TOP, BOTTOM)));  // unwanted because it is opened to the right
        Pipe abovePipe = new StraightPipe(EnumSet.copyOf(List.of(TOP, BOTTOM))); // wanted because it is opened to the bottom
        Pipe lowerPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))); // unwanted because it is not opened to the top


        OpeningDirection[] expectedUnwanted = new OpeningDirection[]{LEFT, BOTTOM};
        ArrayList<OpeningDirection> unwanted = CellGenerator.getUnwantedOpeningDirectionsForLastRow(prevPipe, abovePipe, lowerPipe);
        OpeningDirection[] actualUnwanted = unwanted.toArray(new OpeningDirection[0]);

        assertArrayEquals(expectedUnwanted, actualUnwanted);

        for (int i = 0; i < 100; i++) {
            Cell pipe = CellGenerator.closeLastRowOrColumn(unwanted);
            System.out.println(pipe.toString());
        }

        Cell pipe = CellGenerator.closeLastRowOrColumn(unwanted);
        System.out.println(pipe.toString());

        ArrayList<Cell> possiblePipes = new ArrayList<>();
        possiblePipes.add(new CurvePipe(EnumSet.copyOf(List.of(TOP, RIGHT))));

        assertTrue(possiblePipes.contains(pipe));

    }


    @Test
    public void TestGetUnwantedOpeningDirectionsForLastRow_And_CloseLastRowOrColumn_AllAreUnwanted() {
        Pipe prevPipe = new StraightPipe(EnumSet.copyOf(List.of(TOP, BOTTOM)));  // unwanted because it is not opened to the right
        Pipe abovePipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))); // unwanted because it is not opened to the bottom
        Pipe lowerPipe = new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))); // unwanted because it is not opened to the top


        OpeningDirection[] expectedUnwanted = new OpeningDirection[]{LEFT, TOP, BOTTOM};
        ArrayList<OpeningDirection> unwanted = CellGenerator.getUnwantedOpeningDirectionsForLastRow(prevPipe, abovePipe, lowerPipe);
        OpeningDirection[] actualUnwanted = unwanted.toArray(new OpeningDirection[0]);

        assertArrayEquals(expectedUnwanted, actualUnwanted);


        // all neighbours are closed to the current pipe -> current pipe is equal to null
        Cell pipe = CellGenerator.closeLastRowOrColumn(unwanted);


        assertNull(pipe);

    }




}
