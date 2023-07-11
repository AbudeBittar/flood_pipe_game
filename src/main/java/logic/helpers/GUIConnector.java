package logic.helpers;

/**
 * Interface that defines all the Methods the GUI need.
 *
 * @author Abdulrahman Al Bittar
 */
public interface GUIConnector {


    /**
     * rotates the cell at given position in the given rotation direction
     *
     * @param rotation given rotation direction
     * @param currentPosition given position
     */
    void rotateInBackend(Rotation rotation, Position currentPosition);

    /**
     *  creates new game
     * @param gui GUI
     * @param noOfRows number of board rows
     * @param noOfCols number of board columns
     * @param overflow overflow mode
     * @param percentOfWalls percentage of allowed wall
     *
     */
    void createNewGame(GUIConnector gui, int noOfRows, int noOfCols, boolean overflow, double percentOfWalls);


    /**
     * set the activation of overflow mode
     * @param newOverflow boolean value for overflow
     */
    void setNewOverflowMode(boolean newOverflow);

    /**
     * changes the size of the board
     * @param rowCount new number of board rows
     * @param colCount new number of board columns
     */
    void changeSizeOfBoard(int rowCount, int colCount);

    /**
     * shuffle the cell in the board randomly.
     */
    void mixRandomlyCurrentBoard();

    /**
     * removes the water source.
     */
    void resetWaterSource();

    /**
     * increments the win clicks of rotation
     */
    void incrementWinClicks();

}
