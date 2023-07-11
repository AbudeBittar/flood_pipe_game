package logic.cells;

import logic.helpers.Helper;

/**
 * Enum, that represents the opining directions.
 *
 * @author Abdulrahman Al Bittar
 */
public enum OpeningDirection {

    TOP(0),
    RIGHT(1),
    BOTTOM(2),
    LEFT(3);


    /**
     * Direction ID
     */
    private final int directionID;

    /**
     * Constructor.
     *
     * @param directionID Orientation ID of the pipe.
     */
    OpeningDirection(int directionID) {
        this.directionID = directionID;
    }


    /** ############################################## GETTER & SETTER ############################################## **/

    private int getDirectionID() {

        return this.directionID;
    }



}
