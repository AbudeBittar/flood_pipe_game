package logic.game;

import logic.cells.Cell;
import logic.game.Game;
import logic.game.PlayingField;
import logic.helpers.FakeGUI;
import logic.helpers.Position;
import org.junit.Test;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class GameTest {


    @Test
    public void Test_IsPipeConnectedTOTheWaterSource_NoOverFlow() throws Exception {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """; // ╾


        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 3));

        Cell[][] board = playingField.getBoard();
        assertTrue(game.isConnectedToWaterSource(new Position(0, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 3), game.getSource(), board, playingField.isAbleToOverflow()));


        assertFalse(game.isConnectedToWaterSource(new Position(0, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(0, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(2, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(game.getSource(), new Position(2, 3), board, playingField.isAbleToOverflow()));

    }

    @Test
    public void Test_IsPipeConnectedTOTheWaterSource_WithOverFlow() throws Exception {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """; // ╾


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 3));

        Cell[][] board = playingField.getBoard();
        assertTrue(game.isConnectedToWaterSource(new Position(0, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 3), game.getSource(), board, playingField.isAbleToOverflow()));


        assertFalse(game.isConnectedToWaterSource(new Position(0, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(0, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(2, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(2, 3), game.getSource() , board, playingField.isAbleToOverflow()));

    }


    @Test
    public void Test_IsPipeConnectedTOTheWaterSource_NoOverFlow_ComplexBoard_1() throws Exception {
        String puzzle = """
                ┏┳┓╿
                ┗┻┛┃
                ╾┳━┫
                ╾┫┏┫
                ╾┫┃┃
                ■┗┛╽
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(5, 2));

        Cell[][] board = playingField.getBoard();
        assertTrue(game.isConnectedToWaterSource(new Position(0, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 3), game.getSource(), board, playingField.isAbleToOverflow()));


        assertFalse(game.isConnectedToWaterSource(new Position(0, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(0, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(0, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(5, 0), game.getSource() , board, playingField.isAbleToOverflow()));

    }

    @Test
    public void Test_IsPipeConnectedTOTheWaterSource_WithOverFlow_ComplexBoard_1() throws Exception {
        String puzzle = """
                ┏┳┓╿
                ┗┻┛┃
                ╾┳━┫
                ╾┫┏┫
                ╾┫┃┃
                ■┗┛╽
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(5, 2));

        Cell[][] board = playingField.getBoard();
        assertTrue(game.isConnectedToWaterSource(new Position(0, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 3), game.getSource(), board, playingField.isAbleToOverflow()));


        assertFalse(game.isConnectedToWaterSource(new Position(0, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(0, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(0, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(1, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertFalse(game.isConnectedToWaterSource(new Position(5, 0), game.getSource() , board, playingField.isAbleToOverflow()));

    }


    @Test
    public void Test_IsPipeConnectedTOTheWaterSource_WithOverFlow_ComplexBoard_2() throws Exception {
        String puzzle = """
                ┛┣┳┳┓┣
                ┳┫┃━┣┻
                ┃┣┻┛┣┓
                ┗┛┏━┛┃
                ━┓┃┏━┻
                ┳┫┗┛■┏
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();

//        assertFalse(game.isConnectedToWaterSource(new Position(0, 0), game.getSource(), board, playingField.isAbleToOverflow()));

//        assertTrue(game.isConnectedToWaterSource(new Position(1, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 5), game.getSource(), board, playingField.isAbleToOverflow()));


        assertTrue(game.isConnectedToWaterSource(new Position(0, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(1, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 2), game.getSource(), board, playingField.isAbleToOverflow()));



        assertTrue(game.isConnectedToWaterSource(new Position(1, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(2, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(3, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 5), game.getSource(), board, playingField.isAbleToOverflow()));


        assertTrue(game.isConnectedToWaterSource(new Position(4, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(5, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 1), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(5, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 3), game.getSource(), board, playingField.isAbleToOverflow()));



        // because it is a wall
        assertFalse(game.isConnectedToWaterSource(new Position(5, 4), game.getSource(), board, playingField.isAbleToOverflow()));

    }


    @Test
    public void Test_IsPipeConnectedTOTheWaterSource_WithOverFlow_ComplexBoard_3() throws Exception {
        String puzzle = """
                ┫┣┫┗┳━
                ┛┣┫┏┛┏
                ┓┗┻┛┏┻
                ┛┏━┳┛┏
                ┓┣┓┗┓┗
                ┃┃┃┏┛■
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();


        assertTrue(game.isConnectedToWaterSource(new Position(0, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(0, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(1, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(1, 5), game.getSource(), board, playingField.isAbleToOverflow()));


        assertTrue(game.isConnectedToWaterSource(new Position(2, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(2, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(3, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(3, 5), game.getSource(), board, playingField.isAbleToOverflow()));


        assertTrue(game.isConnectedToWaterSource(new Position(4, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 4), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(4, 5), game.getSource(), board, playingField.isAbleToOverflow()));

        assertTrue(game.isConnectedToWaterSource(new Position(5, 0), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 1), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 2), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 3), game.getSource(), board, playingField.isAbleToOverflow()));
        assertTrue(game.isConnectedToWaterSource(new Position(5, 4), game.getSource(), board, playingField.isAbleToOverflow()));




        // because it is a wall
        assertFalse(game.isConnectedToWaterSource(new Position(5, 5), game.getSource(), board, playingField.isAbleToOverflow()));

    }


    @Test
    public void Test_CheckAllConnections_WithOverFlow_ComplexBoard_1() {
        String puzzle = """
                ━┫┃┏┓┗┻━━━━┛┃┗━
                ━┛┃┗┛┏━┳━━━━┛┏━
                ┳┳┛┏━┛┏┻┓┏━━┓┣━
                ┣┛┏┫┏━┻━┫┣┳┓┗┛■
                ┫┏┛┗┫┏┳┓┗┻┻┫┏━━
                ┣┛┏┳┛┗┻┻┓┏┓┗┻┳┓
                ┃┏┛┗┳┓┏┓┣┛┗━┳┛┃
                ┛┣┳┓┣┛┗┻┛┏┓┏┛┏┻
                ━┻┫┗┻┳━━━┻┛┣┓┣━
                ━┓┣┓┏┻┳━━┳━┻┫┃┏
                ━┫┃┗┛┏┛┏┳┛┏┓┗┛┗
                ┏┛┣━┳┛┏┫┗┳┫┃┏┓■
                ┫┏┛┏┛┏┛┣━┫┣┫┃┗━
                ┃┣┓┣┓┗┓┗┳┻┫┗┛┏┓
                ┗┫┃┗┛┏┫■┗━┛┏┓┃╽
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();



        assertTrue(game.checkAllConnections(board, playingField.isAbleToOverflow()));


    }


    @Test
    public void Test_CheckAllConnections_WithOverFlow_ComplexBoard_2() {
        String puzzle = """
                ━┓┃┏┫┏┛┃┃┃┃┏┳━━
                ┳┻┛┗┛┣┓┗┫┗┛┣┻━━
                ┃┏┓┏┳┛┣┓┣┓┏┫┏┳┓
                ┫┃┗┻┛┏┛┗┫┃┗┻┛┗┻
                ┃┃┏┓┏┛┏┳┫┗━┳┳┓■
                ┫┗┛┣┻┓┗┛┗┳┓┃┗┻━
                ┗━┳┫┏┛┏━┓┣┛┗━━┓
                ━━┛┣┫┏┻┳┛┗┓┏━┓┗
                ┳━━┛┗┛┏┛┏━┛┗━┛┏
                ┗┓┏━┳━┫┏┛┏━━┓┏┛
                ━┛┗┳┛┏┛┃┏┫┏┓┣┻━
                ━━┳┻┳┛┏┛┣┛┗┻┻━━
                ━━┛┏┻┳┫┏┛┏┳━┳┓┏
                ━━┳┛┏┛┗┛┏┛┗━┫┗┻
                ━━┫■┃■┏┓┃┏┓■┗━━
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();



        assertTrue(game.checkAllConnections(board, playingField.isAbleToOverflow()));


    }

    @Test
    public void Test_CheckAllConnections_WithOverFlow_ComplexBoard_3() {
        String puzzle = """
                ┓┣━┫┃┗┛┏━┛┣┻━┛┣
                ┻┛┏┫┣┳━┛┏┓┗┳┳━┻
                ┏┳┛┣┛┣┳┳┻┻━┫┃┏┓
                ┃┣┳┫┏┛┣┫┏┳┳┛┗┫┃
                ┛┃┣┛┃┏┻┛┗┫┣━┳┛┗
                ┏┛┃┏┻┛┏┓┏┛┃┏┛┏┓
                ┗┳┛┃┏━┛┃┗┓┣┛┏┛┃
                ┓┃┏┻┻━┓┣━┻┛┏┫┏┻
                ┃┗┫┏┓┏┻┛┏┓┏┫┃┃■
                ┛┏┛┣┫┃┏┓┃┗┻┛┃┗━
                ┓┗━┻┫┣┫┃┗┓┏┳┻━━
                ┫┏┳┳┻┛┃┃┏┫┃┗┳━━
                ┃┃┣┫┏┳┻┻┛┗┻━┫┏┓
                ┃┃┣┛┃┗━┓┏━┳━┛┣┛
                ┻┫┗┓┃┏┓┗┛┏┫┏━┫┏
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();



        assertTrue(game.checkAllConnections(board, playingField.isAbleToOverflow()));


    }


    @Test
    public void Test_CheckAllConnections_WithOverFlow_ComplexBoard_4() {
        String puzzle = """
                ━┛┏━┻┛┃┣━━━┻┫┃┣
                ┳━┫┏┓┏┫┣┓┏┳┳┛┗┻
                ┛┏┫┣┫┃┗┛┗┛┃┃┏┓┏
                ┳┫┃┗┛┃┏━┓┏┛┗┫┃┗
                ┛┗┛┏━┫┣┳┛┃┏━┛┃┏
                ┏┓┏┻━┛┣┫┏┻┛┏┓┣┛
                ┫┣┫┏┳┓┗┛┃┏┳┫┃┗━
                ┃┗┫┗┫┃┏┳┫┗┛┃┣━┓
                ┃┏┫┏┛┃┣┛┣┓┏┛┣┓┃
                ┫┗┛┗┓┃┗━┻┻┻┓┗┫┗
                ┃┏━┓┃┣┳┳┳━┳┛┏┻┓
                ┫┃┏┛┗┻┫┗┛┏┛┏┛┏┻
                ┻┫┣━┓┏┫┏┳┫┏┛┏┻━
                ┳┫┃┏┻┻┛┃┃┣┛┏┫┏━
                ┻┫┗┛┏┓┏┫┗┛■┃┃┃┏
                """;


        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();



        assertTrue(game.checkAllConnections(board, playingField.isAbleToOverflow()));

    }



    @Test
    public void Test_CheckAllConnections_WithOverFlow_ComplexBoard_5() {
        String puzzle = """
                ━┛┏━┻┛┃┣━━━┻┫┃┣
                ┳━┫┏┓┏┫┣┓┏┳┳┛┗┻
                ┛┏┫┣┫┃┗┛┗┛┃┃┏┓┏
                ┳┫┃┗┛┃┏━┓┏┛┗┫┃┗
                ┛┗┛┏━┫┣┳┛┃┏━┛┃┏
                ┏┓┏┻━┛┣┫┏┻┛┏┓┣┛
                ┫┣┫┏┳┓■┛┃┏┳┫┃┗━
                ┃┗┫┗┫┃┏┳┫┗┛┃┣━┓
                ┃┏┫┏┛┃┣┛┣┓┏┛┣┓┃
                ┫┗┛┗┓┃┗━┻┻┻┓┗┫┗
                ┃┏━┓┃┣┳┳┳━┳┛┏┻┓
                ┫┃┏┛┗┻┫┗┛┏┛┏┛┏┻
                ┻┫┣━┓┏┫┏┳┫┏┛┏┻━
                ┳┫┃┏┻┻┛┃┃┣┛┏┫┏━
                ┻┫┗┛┏┓┏┫┗┛■┃┃┃┏
                """;
        // ┫┣┫┏┳┓■┛┃┏┳┫┃┗━  >>>>>> is not connected!!

        PlayingField playingField = new PlayingField(puzzle, Boolean.TRUE);

        Game game = new Game(new FakeGUI(), playingField);
        game.setSource(new Position(3, 2));

        Cell[][] board = playingField.getBoard();



        assertFalse(game.checkAllConnections(board, playingField.isAbleToOverflow()));

    }






}
