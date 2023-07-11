package logic.game;


import logic.ai.CellGenerator;
import logic.cells.*;
import logic.helpers.Helper;
import logic.helpers.Position;
import logic.helpers.Rotation;

import java.util.*;

import static logic.cells.OpeningDirection.*;

/**
 * class, that represents the playing Field (game board).
 *
 * @author Abdulrahman Al Bittar
 */
public class PlayingField {


    /**
     * ############################################## Attributes ##############################################
     **/

    /**
     * is Overflow mode activated.
     */

    private boolean overflow;

    /**
     * the board.
     */
    private Cell[][] board;


    /**
     * percentage Of Allowed Walls
     */
    private double percentageOfAllowedWalls;


    /**
     * position of water source
     */
    private Position source;


    /**
     * ############################################## Constructors ##############################################
     **/

    /**
     * Constructor to create a board og drawing box board.
     *
     * @param strBoard the board represented as a string.
     */
    public PlayingField(String strBoard, boolean overflow) {
        assert !Helper.isNull(strBoard);
        this.overflow = overflow;

        String[] strings = strBoard.split("\n");
        this.board = new Cell[strings.length][strings[0].length()];

        for (int i = 0; i < strings.length; i++) {
            for (int j = 0; j < strings[0].length(); j++) {
                char toCheck = strings[i].charAt(j);
                switch (toCheck) {
                    case '\u2501' -> board[i][j] = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));  // ━
                    case '\u2503' -> board[i][j] = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));  // ┃

                    case '\u250F' -> board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));   // ┏
                    case '\u2513' -> board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));    // ┓
                    case '\u2517' -> board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));      // ┗
                    case '\u251B' -> board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));       // ┛

                    case '\u2523' -> board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, BOTTOM)))); // ┣
                    case '\u252B' -> board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, LEFT))));  // ┫
                    case '\u2533' -> board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM))));// ┳
                    case '\u253B' -> board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, LEFT))));   // ┻

                    // 2 Möglichkeiten, um Tests der Dozenten auch parsen zu können.
                    case '\u257C', '\u2578' -> board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT))));    // ╼ && ╸
                    case '\u257E', '\u257A' -> board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT))));   // ╾ && ╺
                    case '\u257D', '\u2579' -> board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP))));     // ╽ && ╹
                    case '\u257F', '\u257B' -> board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM))));  // ╿ && ╻

                    case '\u25A0', ' '      -> board[i][j] = new Wall(); // ■ || ' '

                    default       -> throw new IllegalStateException("Unexpected value: " + toCheck);

                }
            }
        }

        setPosOfWaterSourceRandomly();

    }


    /**
     * constructor to create a board from external data.
     *
     * @param source position of water source
     * @param overflow overflow mode
     * @param board board
     */
    public PlayingField(Position source, boolean overflow, int[][] board) {

        this.source = source;
        this.overflow = overflow;
        String[][] binaryBoard = convertIntegerCellsTOBinary(board);

        this.board = new Cell[binaryBoard.length][binaryBoard[0].length];

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                switch (binaryBoard[i][j]) {
                    //The last 4 bits represents the opening direction in this order LEFT, BOTTOM, RIGHT, TOP
                    // the first 4 bits are in the actual case always 0000. Thy are here in case we want to scale the Game in the future.
                    case "00001010" -> this.board[i][j] = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));  // ━
                    case "00000101" -> this.board[i][j] = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));  // ┃

                    case "00000110" -> this.board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));   // ┏
                    case "00001100" -> this.board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));    // ┓
                    case "00000011" -> this.board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));      // ┗
                    case "00001001" -> this.board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));       // ┛

                    case "00000111" -> this.board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, BOTTOM)))); // ┣
                    case "00001101" -> this.board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, LEFT))));  // ┫
                    case "00001110" -> this.board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM))));// ┳
                    case "00001011" -> this.board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, LEFT))));   // ┻


                    case "00001000" -> this.board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT))));               // ╼ && ╸
                    case "00000010" -> this.board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT))));              // ╾ && ╺
                    case "00000001" -> this.board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP))));                // ╽ && ╹
                    case "00000100" -> this.board[i][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM))));             // ╿ && ╻

                    case "00000000" -> this.board[i][j] = new Wall();

                    default         -> throw new IllegalStateException("Unexpected value: " + board[i][j]);                          // ■


                }
            }
        }

    }


    /**
     * constructor to use the methods here from another classes.
     */
    public PlayingField() {

    }



    /**
     * ############################################## GETTER & SETTER ##############################################
     **/


    public Cell[][] getBoard() {
        Cell[][] res = this.board;
        return res;
    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }


    public boolean isAbleToOverflow() {
        boolean res = this.overflow;
        return res;
    }

    private double getPercentageOfAllowedWalls() {
        double res = percentageOfAllowedWalls;
        return res;
    }

    private void setIsAbleToOverflow(boolean isAbleToOverflow) {
        this.overflow = isAbleToOverflow;
    }

    public boolean getIsAbleToOverflow() {
        boolean res = this.overflow;
        return res;
    }

    public void setPercentageOfAllowedWalls(double percentageOfAllowedWalls) {
        assert percentageOfAllowedWalls <= 10;
        assert percentageOfAllowedWalls >= 0;


        this.percentageOfAllowedWalls = percentageOfAllowedWalls;
    }

    /**
     * Determines the number of rows the playing field has.
     *
     * @return the number of rows the playing field has
     */
    public int getNoOfBoardRows() {
        return this.board.length;
    }

    /**
     * Determines the number of columns the playing field has
     *
     * @return the number of columns the playing field has
     */
    public int getNoOfBoardColumns() {
        return this.board[0].length;
    }

    /**
     * sets a pipe as a water source at the given position.
     *
     * @param position the Position of the Pipe
     */
    private void setWaterSource(Position position) {
        this.source = position;
    }


    public Position getSource() {
        return this.source;
    }




    /** ############################################## Logic Methods ############################################## **/




    /**
     * creates a solved board depending on user configurations.
     *
     * @param noOfRows  number of board rows
     * @param noOfColumns number of board rows
     * @param overflow overflow mode
     *
     * @return solved board
     */
    public Cell[][] createSolvedBoard(int noOfRows, int noOfColumns, boolean overflow) {
        this.board = new Cell[noOfRows][noOfColumns];

        this.board = CellGenerator.generateSolvedBoard(noOfRows, noOfColumns, overflow);

        return Arrays.copyOf(board, board.length);

    }


    /**
     * converts the given int board to a given board
     *
     * @param board given int board
     * @return binary board
     */
    private String[][] convertIntegerCellsTOBinary(int[][] board) {
        String[][] res = new String[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                String binary = Helper.intToBinary(board[i][j]);
                binary = Helper.fillZerosRight(binary);
                res[i][j] = binary;
            }
        }
        return res;
    }


    /**
     * checks if the number of walls is valid
     *
     * @return true, if the number of walls is valid
     */
    public boolean isValidWallNo() {
        return getNoOfWalls() <= getNoOfAllowedNoOfWalls();
    }

    /**
     * gets the number of walls in the board.
     *
     * @return number of walls in the board.
     */
    private double getNoOfAllowedNoOfWalls() {
        if(percentageOfAllowedWalls == 0.0) {
            return 0;
        } else {
            int noOfCells = getNoOfBoardRows() * getNoOfBoardColumns();
            return (noOfCells * percentageOfAllowedWalls) / 100;
        }
    }


    /**
     * sets a position for water source randomly
     */
    public void setPosOfWaterSourceRandomly() {
        Random random = new Random();
        int randomX;
        int randomY;
        Cell cell;

        do {
            randomX = random.nextInt(getNoOfBoardRows());
            randomY = random.nextInt(getNoOfBoardColumns());
            cell = this.board[randomX][randomY];
        } while (isWall(cell));

        Position randomPosition = new Position(randomX, randomY);

        setWaterSource(randomPosition);
    }

    /**
     * Rotates pipes of given board randomly.
     *
     * @param board given board
     *
     * @return board that their pipes are shuffled
     */
    public Cell[][] rotateBoardCellsRandomly(Cell[][] board) {
        for (int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++) {
                if (isPipe(board[i][j])) {
                    Rotation rotation = getRandomRotation();
                    int rotationCounter = Helper.generateRandomNo(1, 4);

                    // rotate n times
                    for (int counter = 0; counter < rotationCounter; counter++) {
                        board[i][j] = ((Pipe) board[i][j]).rotate(rotation);
                    }

                }
            }
        }

        return board;
    }


    /**
     * get a random rotation direction (right or left).
     *
     * @return a random rotation direction (right or left).
     */
    private Rotation getRandomRotation(){
        int rotationID = Helper.generateRandomNo(1, 2);

        return rotationID == 1 ?  Rotation.LEFT : Rotation.RIGHT;
    }


    /**
     * Determines the number of walls the playing field has
     *
     * @return Determines the number of walls the playing field has
     */
    public int getNoOfWalls() {
        int res = 0;
        for (Cell[] row : board) {
            for (Cell col : row) {
                if (isWall(col)) {
                    res++;
                }
            }
        }

        return res;
    }


    /**
     * check if the given cell is a wall.
     *
     * @param cell given cell
     * @return true, if the given cell is a wall.
     */
    public boolean isWall(Cell cell) {
        return (cell instanceof Wall);
    }

    /**
     * check if the given cell is a wall.
     *
     * @param cell given cell
     * @return true, if the given cell is a wall.
     */
    public boolean isPipe(Cell cell) {
        return (cell instanceof Pipe);
    }

    /**
     * check if the given cell is a straight pipe.
     *
     * @param cell given cell
     * @return true, if the given cell is a straight pipe.
     */
    public boolean isStraightPipe(Cell cell) {
        return (cell instanceof StraightPipe);
    }

    /**
     * check if the given cell is a curve pipe.
     *
     * @param cell given cell
     * @return true, if the given cell is a curve pipe.
     */
    public boolean isCurvePipe(Cell cell) {
        return (cell instanceof CurvePipe);
    }

    /**
     * check if the given cell is a t-cross pipe.
     *
     * @param cell given cell
     * @return true, if the given cell is a t-cross pipe.
     */
    public boolean isT_Pipe(Cell cell) {
        return (cell instanceof T_Pipe);
    }

    /**
     * check if the given cell is an end pipe.
     *
     * @param cell given cell
     * @return true, if the given cell is an end pipe.
     */
    public boolean isEndPipe(Cell cell) {
        return (cell instanceof EndPipe);
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
                .append(Helper.intMatrixToString(GameData.Obj2int(this.board), 2)).append("\n")
                .append(Helper.printTabs(1)).append("]\n}");


        return res.toString();

    }



}
