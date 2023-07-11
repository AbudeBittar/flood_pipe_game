package logic.game;

import logic.cells.Cell;
import logic.game.GameData;
import logic.game.PlayingField;
import logic.helpers.Helper;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class GameDataTest {

    @Test
    public void test_toJSON_Own_Board() throws Exception {
        String puzzle = """
                ┏┫┳╼
                ┃╽┣┓
                ┃╿■╾
                ┗┻━┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Cell[][] board = playingField.getBoard();

        GameData state = new GameData(playingField.getSource(), playingField.getIsAbleToOverflow(), GameData.Obj2int(board));
        String path = "./src/main/resources/JSON/States.json";

        boolean json = GameData.toJSON(path, state);


        assertTrue(json);

    }

    @Test
    public void test_toJSON_University_Board() throws Exception {
        String puzzle = """
                ┛┏┗┫┓
                ━┃┣┳┓
                ╿┫┫┃┛
                ┏┏┓┓━
                ┏╽┓┫┛
                """;

        PlayingField playingField = new PlayingField(puzzle, Boolean.FALSE);
        Cell[][] board = playingField.getBoard();

        GameData state = new GameData(playingField.getSource(), playingField.getIsAbleToOverflow(), Helper.transposeMatrix(GameData.Obj2int(board)));
        String path = "./src/main/resources/JSON/State Of the university.json";

        boolean json = GameData.toJSON(path, state);


        assertTrue(json);

    }



    @Test
    public void test_fromJSON_Own_Board() throws Exception {

        String path = "./src/main/resources/JSON/States.json";


        GameData json = GameData.fromJSON(path);


        assert json != null;
        System.out.println("JSON like it was in the file: ");
        System.out.println(json);

        System.out.println("JSON object after converting it to a game");
        System.out.println();
        PlayingField playingField = new PlayingField(json.getSource(), json.isOverflow(), Helper.transposeMatrix(json.getBoard()));
        Cell[][] board = playingField.getBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Cell cell = board[i][j];
                System.out.print(cell.toString());
            }
            System.out.println();
        }

    }


    @Test
    public void test_fromJSON_University_Board() throws Exception {

        String path = "./src/main/resources/JSON/State Of the university.json";


        GameData json = GameData.fromJSON(path);

        assert json != null;
        System.out.println("JSON like it was in the file: ");
        System.out.println(json);

        System.out.println("JSON object after converting it to a game");
        System.out.println();
        PlayingField playingField = new PlayingField(json.getSource(), json.isOverflow(), Helper.transposeMatrix(json.getBoard()));
        Cell[][] board = playingField.getBoard();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Cell cell = board[i][j];
                System.out.print(cell.toString());
            }
            System.out.println();
        }

    }


}
