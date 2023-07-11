package logic.helpers;


import logic.helpers.GUIConnector;
import logic.helpers.Position;
import logic.helpers.Rotation;

/**
 * Fake user interface, that have nothing into.
 * Only for Testing purposes
 *
 * @author Abdulrahman Al Bittar
 */

public class FakeGUI implements GUIConnector {


    @Override
    public void rotateInBackend(Rotation rotation, Position currentPosition) {

    }

    @Override
    public void createNewGame(GUIConnector gui, int noOfRows, int noOfCols, boolean overflow, double percentOfWalls) {

    }



    @Override
    public void setNewOverflowMode(boolean newOverflow) {

    }

    @Override
    public void changeSizeOfBoard(int rowCount, int colCount) {

    }

    @Override
    public void mixRandomlyCurrentBoard() {

    }

    @Override
    public void resetWaterSource() {

    }



    @Override
    public void incrementWinClicks() {

    }


}
