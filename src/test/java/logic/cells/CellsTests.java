package logic.cells;

import logic.helpers.FakeGUI;
import logic.game.Game;
import logic.game.PlayingField;
import logic.helpers.Rotation;
import org.junit.Test;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static logic.cells.OpeningDirection.*;
import static org.junit.Assert.*;

public class CellsTests {


    /**
     * First Test Group
     *
     * @throws Exception exception
     */
    @Test
    public void Test1_isNonQuadraticBoardsPossible_True() {

        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        assertTrue(playingField.getNoOfBoardRows() != playingField.getNoOfBoardColumns());

    }

    /**
     * First Test Group
     *
     * @throws Exception exception
     */
    @Test
    public void Test1_isNonQuadraticBoardsPossible_False() {

        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        assertFalse(playingField.getNoOfBoardRows() != playingField.getNoOfBoardColumns());

    }


    /**
     * Third Test Group
     *
     * @throws Exception exception
     */
    @Test
    public void Test2_ThereAreNoUnAllowedWalls_isValid()  {

        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);

        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
//        double noOfCells = board.length * board[0].length;
//        double noOfWalls = playingField.getNoOfWalls();
//
//        double percentage = (noOfWalls * 100) / noOfCells;
        playingField.setPercentageOfAllowedWalls(10.0);


        boolean result = playingField.isValidWallNo();

        assertTrue(result);

    }

    @Test
    public void Test2_ThereAreNoUnAllowedWalls_isNotValid(){
        String puzzle = "┏┫┳╼\n" + "    \n" + "┃╽┣┓\n" + "    \n" + "┃╿■╾\n" + "┗┻━┛\n";

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        double noOfCells = board.length * board[0].length;
        double noOfWalls = playingField.getNoOfWalls();


        double percentage = (noOfWalls * 100) / noOfCells;
        playingField.setPercentageOfAllowedWalls(10.0);


        boolean result = playingField.isValidWallNo();

        assertFalse(result);

    }

    /**
     * Third Test Group
     *
     * @throws Exception exception
     */
    @Test
    public void Test3_RotateRight_StraightPipe_TOP_BOTTOM() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe straightPipe = (Pipe) board[1][0];

        Pipe result = straightPipe.rotate(Rotation.RIGHT);

        StraightPipe expected = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(OpeningDirection.LEFT, OpeningDirection.RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateLeft_StraightPipe_TOP_BOTTOM() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe straightPipe = (Pipe) board[1][0];

        Pipe result = straightPipe.rotate(Rotation.LEFT);

        StraightPipe expected = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateLeft_StraightPipe_RIGHT_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe straightPipe = (Pipe) board[3][2];

        Pipe result = straightPipe.rotate(Rotation.LEFT);

        StraightPipe expected = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateRIGHT_StraightPipe_RIGHT_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe straightPipe = (Pipe) board[3][2];

        Pipe result = straightPipe.rotate(Rotation.RIGHT);

        StraightPipe expected = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateRight_CurvePipe_BOTTOM_RIGHT()  {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][0];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateLeft_CurvePipe_BOTTOM_RIGHT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][0];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateRight_CurvePipe__TOP_RIGHT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[3][0];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateLeft_CurvePipe_TOP_RIGHT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[3][0];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateRight_CurvePipe__TOP_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[3][3];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateLeft_CurvePipe_TOP_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[3][3];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateRight_CurvePipe_BOTTOM_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[1][3];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }


    @Test
    public void Test3_RotateLeft_CurvePipe_BOTTOM_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[1][3];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }



    @Test
    public void Test3_RotateRight_T_Pipe_TOP_BOTTOM_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][1];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }

    @Test
    public void Test3_RotateLeft_T_Pipe_TOP_BOTTOM_LEFT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][1];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }

    @Test
    public void Test3_RotateRight_T_Pipe_TOP_BOTTOM_RIGHT() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[1][2];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }

    @Test
    public void Test3_RotateLeft_T_Pipe_TOP_BOTTOM_RIGHT()  {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[1][2];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }

    @Test
    public void Test3_RotateRight_T_Pipe_TOP_RIGHT_Left() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[3][1];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }

    @Test
    public void Test3_RotateLeft_T_Pipe_TOP_RIGHT_Left() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[3][1];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }


    @Test
    public void Test3_RotateRight_T_Pipe_RIGHT_Left_BOTTOM() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][2];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, LEFT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }

    @Test
    public void Test3_RotateLeft_T_Pipe_RIGHT_Left_BOTTOM() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][2];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);

    }


    @Test
    public void Test3_RotateLeftEndPipe_TOP() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[1][1];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateRightEndPipe_TOP() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[1][1];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateLeftEndPipe_Right() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[2][3];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateRightEndPipe_Right() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[2][3];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateLeftEndPipe_BOTTOM()  {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[2][1];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateRightEndPipe_BOTTOM() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[2][1];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateLeftEndPipe_Left()  {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][3];

        Pipe result = pipe.rotate(Rotation.LEFT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }

    @Test
    public void Test3_RotateRightEndPipe_Left() {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Game game = new Game(new FakeGUI(), playingField);

        Cell[][] board = playingField.getBoard();
        Pipe pipe = (Pipe) board[0][3];

        Pipe result = pipe.rotate(Rotation.RIGHT);

        Pipe expected = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP))));

        assertEquals(expected.getPipeOpenings(), result.getPipeOpenings());
        assertEquals(expected, result);
    }




}
