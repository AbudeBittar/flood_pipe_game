package logic.cells;

import java.util.ArrayList;

/**
 * Class, that represents a single cell,
 *  this is the parent  class of the whole Pipes and Walls.
 *
 *
 * @author Abdulrahman Al Bittar
 */
public abstract class Cell {


    /**
     * standard constructor.
     */
    public Cell() {

    }


    /**
     * get the opening directions of this cell.
     *
     * @return a list of the opening directions of this cell.
     */
    public abstract ArrayList<OpeningDirection> getPipeOpenings();

    /**
     * determinates if the current cell is open from top
     *
     * @return true,  if the current cell is open from top. else false.
     */
    public abstract boolean isOpenFromTop();

    /**
     * determinates if the current cell is open from right
     *
     * @return true,  if the current cell is open from right. else false.
     */
    public abstract boolean isOpenFromRight();

    /**
     * determinates if the current cell is open bottom top
     *
     * @return true,  if the current cell is open from bottom. else false.
     */
    public abstract boolean isOpenFromBottom();

    /**
     * determinates if the current cell is open from left
     *
     * @return true,  if the current cell is open from left. else false.
     */
    public abstract boolean isOpenFromLeft();

    /**
     * Determines the decimal value of pipe opening directions.
     *
     * The value cas be calculated as following:
     * UP      RIGHT        BOTTOM      LEFT
     * 1         2            4          8
     *
     * @return an integral value, that determines all pipe opening directions.
     */
    public abstract int getOpeningDecimalValue();


    /**
     * Determines the binary representation of above described integer value.
     *
     * @return String value containing the binary representation of above described integer value.
     */
    public abstract String getOpeningBinaryValue();





}
