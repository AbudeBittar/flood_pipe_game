package logic.game;


import com.google.gson.*;
import logic.helpers.Constants;
import logic.cells.Cell;
import logic.helpers.Helper;
import logic.helpers.Position;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import java.nio.file.Paths;

/**
 * class, that implements file inputs & outputs.
 *
 * @author Abdulrahman Al Bittar
 */
public class GameData {

    /**
     * position of water source
     */
    private final Position source;

    /**
     * overflow mode
     */
    private final boolean overflow;

    /**
     * board of integers
     */
    private final int[][] board;

    /**
     * constructor to create a game of external data
     * @param source  position of water source
     * @param overflow overflow mode
     * @param board board of integers
     */
    public GameData(Position source, boolean overflow, int[][] board) {
        this.source = source;
        this.overflow = overflow;
        this.board = Helper.transposeMatrix(board);
    }


    /**
     * ############################################## GETTER & SETTER ##############################################
     **/
    public Position getSource() {
        return source;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public int[][] getBoard() {
        return board;
    }



    /**
     * Writes a given GameData object to a JSON file at the specified file path.
     *
     * @param path      The path of the file to write the JSON data to.
     * @param gameData  The GameData object to convert to JSON and write to the file.
     * @return          True if the write was successful, false otherwise.
     * @throws Exception If any error occurs during the write process (e.g. file not found, unsupported encoding, security exception, etc.).
     */
    public static boolean toJSON(String path, GameData gameData) throws Exception {
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String jsonString = gson.toJson(gameData);
            out.write(jsonString);
            return true;
        }  catch (UnsupportedEncodingException e) {
            String msg = "Error: Unsupported encoding exception occurred while writing to file: " + path;
            System.err.println(msg);
            throw new UnsupportedEncodingException(msg);
        } catch (FileNotFoundException e) {
            String msg = "Error: File not found at the specified path: " + path;
            System.err.println(msg);
            throw new FileNotFoundException(msg);
        } catch (IOException e) {
            String msg = "Error: IOException occurred while writing to file: " + path;
            System.err.println(msg);
            throw new IOException(msg);
        } catch (SecurityException e) {
            String msg = "Error: Security exception occurred while writing to file: " + path;
            System.err.println(msg);
            throw new SecurityException(msg);
        }  catch (Exception e) {
            String msg = "Error: Exception occurred while writing to file: " + path;
            System.err.println(msg);
            throw new Exception(msg);
        }
    }


    /**
     * reads the input JSON file and creates a GameData object from it.
     *
     * @param path The path of the JSON file.
     * @return The created GameData object.
     * @throws Exception if there is any error reading the file, the JSON file is not properly formatted,
     *                   the 'source' value is not an object, the 'x' and 'y' values are not integer values,
     *                   the 'overflow' value is not a boolean, the 'board' values are not correct,
     *                   or if an unexpected error occurs.
     */
    public static GameData fromJSON(String path) throws Exception {
        assert !Helper.isNull(path);
        try {
            // create a reader
            Reader reader;
            try {
                reader = Files.newBufferedReader(Paths.get(path));
            } catch (NoSuchFileException e) {
                // Handle the exception, e.g. print a message, log the error, or show an error to the user.
                String msg = "The file could not be found at the specified path: " + path;
                System.err.println(msg);
                throw new NoSuchFileException(msg);
            }
            //create JsonObject instance
            JsonObject parser;
            try {
                parser = JsonParser.parseReader(reader).getAsJsonObject();
            } catch (JsonSyntaxException e) {
                // Handle the exception, e.g. print a message, log the error, or show an error to the user.
                String msg = "The JSON file is not properly formatted: " + e.getMessage();
                System.err.println(msg);
                throw new JsonSyntaxException(msg);
            }

            // read source
            JsonObject source;
            try {
                source = parser.get("source").getAsJsonObject();
            } catch (JsonSyntaxException e) {
                String msg = "Error: 'source' value is not an object";
                System.err.println(msg);
                throw new JsonSyntaxException(msg);
            }

            // read position of source
            int x;
            int y;
            try {
                x = source.get("x").getAsInt();
                y = source.get("y").getAsInt();
            } catch (IllegalStateException e) {
                String msg = "Error: 'x' and 'y' values are not integer values";
                System.err.println(msg);
                throw new IllegalStateException(msg);
            }

            if (Helper.isNegative(x) || x >= Constants.MAX_NUMBER_OF_ROWS || Helper.isNegative(y) || y >= Constants.MAX_NUMBER_OF_COLUMNS) {
                String msg = "Invalid source position";
                System.err.println(msg);
                throw new IllegalStateException(msg);
            }

            Position sourcePos = new Position(x, y);

            boolean overflow;
            try {
                overflow = parser.get("overflow").getAsBoolean();
            } catch (IllegalStateException e) {
                String msg = "Error: 'overflow' value is not a boolean";
                System.err.println(msg);
                throw new IllegalStateException(msg);
            }

            int[][] temp = new int[Constants.MAX_NUMBER_OF_ROWS][Constants.MAX_NUMBER_OF_COLUMNS];
            int i = 0;
            int j = 0;
            try {
                for (JsonElement row : parser.get("board").getAsJsonArray()) {
                    j = 0;
                    for (JsonElement col : row.getAsJsonArray()) {
                        temp[i][j] = col.getAsInt();
                        j++;
                    }
                    i++;
                }
            }  catch (IllegalStateException | StackOverflowError e) {
                String msg = "Error: 'board' values are not correct";
                System.err.println(msg);
                throw new IllegalStateException(msg);
            }


            int[][] board = reduceBoard(i, j, temp);

            //close reader
            reader.close();
            return new GameData(sourcePos, overflow, board);
        } catch (IOException e) {
            String msg = "Error reading file: " + e.getMessage();
            System.err.println(msg);
            throw new IOException(msg);
        } catch (Exception e) {
            String msg = "An unexpected error occurred: " + e.getMessage();
            System.err.println(msg);
            throw new Exception(msg);
        }
    }


    /**
     * Reduces the board to its correct dimensions.
     *
     * @param noOfRows the number of rows in the board
     * @param noOfCols the number of columns in the board
     * @param temp the 2D int array representing the board
     * @return the 2D int array of the reduced board, transposed to its correct orientation
     */
    private static int[][] reduceBoard(int noOfRows, int noOfCols, int[][] temp) {
        int[][] board = new int[noOfRows][noOfCols];
        for (int i = 0; i < noOfRows; i++) {
            for (int j = 0; j < noOfCols; j++) {
                board[i][j] = temp[i][j];
            }
        }
        return Helper.transposeMatrix(board);
    }


    /**
     * converts the given board of cells to a board of integers.
     *
     * @param board  given board of cells
     * @return board of integers.
     */
    public static int[][] Obj2int(Cell[][] board) {
        int[][] res = new int[board.length][board[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                res[i][j] = board[i][j].getOpeningDecimalValue();
            }
        }

        // transponierte Matrix
        return Helper.transposeMatrix(res);
    }




    /**
     * provides a representation of the playing field.
     * <p>
     * The values are to be output on the width of 5, each value is followed by a space and
     * after each row there is a line break, so that simply clear columns are created.
     *
     * @return a representation of the playing field.
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("{\n")
                .append(Helper.printTabs(1)).append("\"source\": {\n")
                .append(Helper.printTabs(2)).append("\"x\": ").append(this.source.getX()).append(",\n")
                .append(Helper.printTabs(2)).append("\"y\": ").append(this.source.getY()).append("\n")
                .append(Helper.printTabs(1)).append("},\n")
                .append(Helper.printTabs(1)).append("\"overflow\": ").append(this.overflow).append(",\n")
                .append(Helper.printTabs(1)).append("\"board\": [\n")
                .append(Helper.intMatrixToString(board, 2)).append("\n")
                .append(Helper.printTabs(1)).append("]\n}");


        return res.toString();

    }


}
