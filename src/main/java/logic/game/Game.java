package logic.game;

import logic.helpers.GUIConnector;
import logic.cells.*;
import logic.helpers.Helper;
import logic.helpers.Position;

import java.util.*;



/**
 * class, that represents the game.
 *
 * @author Abdulrahman Al Bittar
 */
public class Game {

    /**
     * Number of rotation clicks
     */
    private static int noOfClicks;


    /**
     * position of water source
     */
    private Position source;

    /**
     * overflow mode
     */
    private boolean overflow;

    /**
     * backend board
     */
    private Cell[][] board;


    /**
     * GUI
     */
    private final GUIConnector gui;

    /**
     * Saves the state of the current game
     */
    private final PlayingField playingField;

    /**
     * already connected pipes to the water source
     */
    private Set<Position> alreadyConnectedPipes = new HashSet<>();


    /**
     * Test Constructor.
     *
     * @param gui          GUI
     * @param playingField playing field to configure the board as waned.
     */
    public Game(GUIConnector gui, PlayingField playingField) {
        assert !Helper.isNull(gui);
        assert !Helper.isNull(playingField);

        this.gui = gui;
        this.playingField = playingField;

        noOfClicks = 0;
    }


    /**
     * Constructor to use for external data
     *
     * @param gui      GUI
     * @param gameData data to create new game from external data.
     */
    public Game(GUIConnector gui, GameData gameData) {
        assert !Helper.isNull(gui);
        assert !Helper.isNull(gameData);

        this.gui = gui;
        this.playingField = new PlayingField(gameData.getSource(), gameData.isOverflow(), Helper.transposeMatrix(gameData.getBoard()));
        this.source = playingField.getSource();
        this.board = this.playingField.getBoard();


        noOfClicks = 0;
    }


    /**
     * main constructor to create the game
     *
     * @param gui            GUI
     * @param noOfRows       number of board rows
     * @param noOfColumns    number of board columns
     * @param overflow       overflow mode
     * @param percentOfWalls percentage of allowed walls
     */
    public Game(GUIConnector gui, int noOfRows, int noOfColumns, boolean overflow, double percentOfWalls) {
        this.overflow = overflow;
        this.gui = gui;
        this.playingField = new PlayingField();
        this.board = playingField.getBoard();

        playingField.setPercentageOfAllowedWalls(percentOfWalls);

        initializeBoard(noOfRows, noOfColumns);
        noOfClicks = 0;

    }

    /**
     * ############################################## GETTER & SETTER ##############################################
     **/

    public PlayingField getPlayingField() {
        return playingField;
    }

    public Position getSource() {
        return source;
    }

    public void setSource(Position source) {
        this.source = source;
    }

    public boolean isOverflow() {
        return overflow;
    }

    public void setOverflow(boolean overflow) {
        this.overflow = overflow;
    }

    public Cell[][] getBoard() {
        if (Helper.isNull(this.board)) {
            return null;
        } else {
            Cell[][] currentBoard = new Cell[board.length][board[0].length];
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    Cell cell = board[i][j];
                    if (playingField.isPipe(cell)) {
                        Pipe pipe = (Pipe) board[i][j];
                        if (playingField.isStraightPipe(cell)) {
                            currentBoard[i][j] = new StraightPipe(pipe);
                        }
                        if (playingField.isCurvePipe(pipe)) {
                            currentBoard[i][j] = new CurvePipe(pipe);
                        }
                        if (playingField.isT_Pipe(pipe)) {
                            currentBoard[i][j] = new T_Pipe(pipe);
                        }
                        if (playingField.isEndPipe(pipe)) {
                            currentBoard[i][j] = new EndPipe(pipe);
                        }
                    } else if (playingField.isWall(cell)) {
                        currentBoard[i][j] = new Wall();
                    }
                }
            }

            return currentBoard;
        }

    }

    public void setBoard(Cell[][] board) {
        this.board = board;
    }


    public int getNoOfClicks() {
        return noOfClicks;
    }

    public void setNoOfClicks(int noOfClicks) {
        Game.noOfClicks = noOfClicks;
    }

    public boolean isGameFinished() {
        return isSolvedBoard(this.board, this.source, this.overflow);

    }


    /** ############################################## Logic Methods ############################################## **/


    /**
     *
     */

    /**
     * fills the board with a solved playing field.
     *
     * @param noOfRows    number of board rows
     * @param noOfColumns number of board columns
     */
    private void initializeBoard(int noOfRows, int noOfColumns) {
        do {
            this.board = playingField.createSolvedBoard(noOfRows, noOfColumns, this.overflow);
            playingField.setPosOfWaterSourceRandomly();
        } while (!playingField.isValidWallNo() || !isSolvedBoard(playingField.getBoard(), playingField.getSource(), this.overflow));


        System.out.println("Solved Board before random rotating: ");
        System.out.println("-----------------------------------------");
        Helper.printBoard(this.board);
        System.out.println("-----------------------------------------\n");
        this.board = playingField.rotateBoardCellsRandomly(this.board);


        this.source = playingField.getSource();

        this.alreadyConnectedPipes = new HashSet<>();
        List<Position> connectedPipes = getConnectedPipes(this.source, this.board, this.overflow);
        for (Position pipe : connectedPipes) {
            alreadyConnectedPipes.add(pipe);
        }

    }

    /**
     * checks if the given board is solved
     *
     * @param board       the given board
     * @param waterSource position of water source
     * @param overflow    overflow mode
     * @return true, if the given board is solved.
     */
    private boolean isSolvedBoard(Cell[][] board, Position waterSource, boolean overflow) {
       return checkAllConnections(board, overflow) && checkAllPipesAreConnectedToWaterSource(board, waterSource);
    }


    /**
     * creates a sorted list of all connected pipes.
     *
     * @param waterSource position of water source
     * @param board       the board
     * @param overflow    overflow mode
     * @return sorted list of all connected pipes.
     */
    public List<Position> getSortedConnectedPipes(Position waterSource, Cell[][] board, boolean overflow) {
        List<Position> connectedPipes = getConnectedPipes(waterSource, board, overflow);

        // this filter because already connected pipe before rotating should not be animated again.
        connectedPipes.removeIf(pipe -> alreadyConnectedPipes.contains(pipe));


        alreadyConnectedPipes = new HashSet<>();

        for (Position pipe : getConnectedPipes(waterSource, board, overflow)) {
            alreadyConnectedPipes.add(pipe);
        }

        return connectedPipes;
    }

    /**
     * creates a  list of all connected pipes.
     *
     * @param waterSource position of water source
     * @param board       the board
     * @param overflow    overflow mode
     * @return list of all connected pipes.
     */
    public List<Position> getConnectedPipes(Position waterSource, Cell[][] board, boolean overflow) {
        if (waterSource == null) {
            // while editor mode, if there is no water source
            return new ArrayList<>();
        }
        List<Position> connectedPipes = new ArrayList<>();
        boolean[][] visited = new boolean[board.length][board[0].length];
        initVisited(visited);

        dfs(waterSource, board, visited, connectedPipes, overflow);
        return connectedPipes;
    }


    /**
     * fills the given boolean matrix with nulls.
     *
     * @param visited boolean matrix
     */
    private void initVisited(boolean[][] visited) {
        for (int i = 0; i < visited.length; i++) {
            Arrays.fill(visited[i], false);
        }
    }

    /**
     * This method implements a Depth First Search algorithm to find all the connected pipes
     * starting from the source. It takes in the current position, the board, the visited array,
     * the connected pipes list, and the overflow flag as input. It adds the current position to
     * the connected pipes list and marks it as visited. Then it checks for all 4 adjacent cells
     * (top, right, bottom, left) and calls itself recursively for those cells which are connected
     * to the current cell.
     *
     * @param currentPosition The current position to start the search from
     * @param board           The game board
     * @param visited         A 2D boolean array to track if a cell has been visited
     * @param connectedPipes  A list to store all the connected pipes
     * @param overflow        A flag to indicate if overflow is allowed
     */
    private void dfs(Position currentPosition, Cell[][] board, boolean[][] visited, List<Position> connectedPipes, boolean overflow) {
        // If the current position is null, stop
        if (currentPosition == null) {
            return;
        }
        // Add the current position to the connected pipes list
        connectedPipes.add(currentPosition);

        // Mark the current position as visited
        visited[currentPosition.getX()][currentPosition.getY()] = true;

        // Get the positions for the cells above, next to, lower than, and previous to the current position
        Position above = Position.getAbove(currentPosition, board.length, board[0].length, overflow);
        Position next = Position.getNext(currentPosition, board.length, board[0].length, overflow);
        Position lower = Position.getLower(currentPosition, board.length, board[0].length, overflow);
        Position prev = Position.getPrev(currentPosition, board.length, board[0].length, overflow);

        // Get the current cell
        Cell currentCell = board[currentPosition.getX()][currentPosition.getY()];

        // Check if the current cell is open from the top and if the cell above it is connected and not visited
        if (currentCell.isOpenFromTop() && above != null && !visited[above.getX()][above.getY()] && board[above.getX()][above.getY()].isOpenFromBottom()) {
            // Call dfs recursively for the cell above the current cell
            dfs(above, board, visited, connectedPipes, overflow);
        }

        // Check if the current cell is open from the right and if the cell next to it is connected and not visited
        if (currentCell.isOpenFromRight() && next != null && !visited[next.getX()][next.getY()] && board[next.getX()][next.getY()].isOpenFromLeft()) {
            // Call dfs recursively for the cell next to the current cell
            dfs(next, board, visited, connectedPipes, overflow);
        }

        // Check if the current cell is open from the bottom and if the cell below it is connected and not visited
        if (currentCell.isOpenFromBottom() && lower != null && !visited[lower.getX()][lower.getY()] && board[lower.getX()][lower.getY()].isOpenFromTop()) {
            // Call dfs recursively for the cell below to the current cell
            dfs(lower, board, visited, connectedPipes, overflow);
        }

        // Check if the current cell is open from the bottom and if the cell previous it is connected and not visited
        if (currentCell.isOpenFromLeft() && prev != null && !visited[prev.getX()][prev.getY()] && board[prev.getX()][prev.getY()].isOpenFromRight()) {
            // Call dfs recursively for the cell previous to the current cell
            dfs(prev, board, visited, connectedPipes, overflow);
        }
    }


    /**
     * checks that every pipe in the board is connected from all their opening directions.
     * This means that there is no unconnected opening direction in the whole board.
     *
     * @param board    the board
     * @param overflow overflow mode
     * @return true, if every pipe in the board is connected from all their opening directions. else false
     */
    public boolean checkAllConnections(Cell[][] board, boolean overflow) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                if (playingField.isPipe(board[i][j])) {
                    // check top opening
                    if (board[i][j].isOpenFromTop()) {
                        Position above = Position.getAbove(new Position(i, j), board.length, board[0].length, overflow);
                        if (above == null) return false;
                        if (!board[above.getX()][above.getY()].isOpenFromBottom()) return false;
                    }
                    // check right opening
                    if (board[i][j].isOpenFromRight()) {
                        Position next = Position.getNext(new Position(i, j), board.length, board[0].length, overflow);
                        if (next == null) return false;
                        if (!board[next.getX()][next.getY()].isOpenFromLeft()) return false;
                    }
                    // check bottom opening
                    if (board[i][j].isOpenFromBottom()) {
                        Position lower = Position.getLower(new Position(i, j), board.length, board[0].length, overflow);
                        if (lower == null) return false;
                        if (!board[lower.getX()][lower.getY()].isOpenFromTop()) return false;
                    }
                    // check left opening
                    if (board[i][j].isOpenFromLeft()) {
                        Position prev = Position.getPrev(new Position(i, j), board.length, board[0].length, overflow);
                        if (prev == null) return false;
                        if (!board[prev.getX()][prev.getY()].isOpenFromRight()) return false;
                    }
                }

            }
        }
        return true;
    }

    /**
     * check if all pipes are connected to the water source.
     * this method tells that there are no circles (unreachable pipes) in the board.
     *
     * @param board       the board
     * @param waterSource position of water source
     * @return true, if all pipes are connected to the water source.
     */
    private boolean checkAllPipesAreConnectedToWaterSource(Cell[][] board, Position waterSource) {
        int totalCells = board.length * board[0].length - playingField.getNoOfWalls();
        List<Position> connectedPipes;
        connectedPipes = getConnectedPipes(waterSource, board, overflow);

        return connectedPipes != null && connectedPipes.size() == totalCells;
    }


    /**
     * This method checks if the specified position is connected to the water source.
     * It does so by checking if the position exists in the list of connected pipes returned by the getConnectedPipes method.
     *
     * @param toCheck the position to check if it is connected to the water source
     * @param waterSource the position of the water source
     * @param board the current state of the playing field
     * @param overflow whether to allow wrapping or not
     * @return true if the position is connected to the water source, false otherwise
     */
    public boolean isConnectedToWaterSource(Position toCheck, Position waterSource, Cell[][] board, boolean overflow) {
        if (toCheck == null || waterSource == null) {
            return false;
        }

        return getConnectedPipes(waterSource, board, overflow).contains(toCheck);
    }

    /**
     * This method sets the cell at a specific index in the board matrix.
     * It first checks if the index is valid (i.e. within the bounds of the board matrix)
     * and then sets the cell at the specified position.
     *
     * @param newCell the new cell to set at the specified position
     * @param position the position at which to set the new cell
     */
    public void setCellAtSpecificIndex(Cell newCell, Position position) {
        assert position.getX() >= 0 && position.getX() < this.board.length;
        assert position.getY() >= 0 && position.getY() < this.board[0].length;

        this.board[position.getX()][position.getY()] = newCell;
    }

    /**
     * resets the board (make it containing only walls).
     */
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = new Wall();
            }
        }
    }

    /**
     * resets the water source
     */
    public void resetWaterSource() {
        this.source = null;
    }



}
