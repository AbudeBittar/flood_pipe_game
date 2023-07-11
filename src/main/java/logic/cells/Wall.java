package logic.cells;

import java.util.ArrayList;

/**
 * Class, that represents a single piece of a wall.
 *
 * @author Abdulrahman Al Bittar
 */
public class Wall extends Cell {

    /**
     * constructor
     */
    public Wall() {

    }


    @Override
    public ArrayList<OpeningDirection> getPipeOpenings() {
        return new ArrayList<>();
    }

    @Override
    public boolean isOpenFromTop() {
        return false;
    }

    @Override
    public boolean isOpenFromRight() {
        return false;
    }

    @Override
    public boolean isOpenFromBottom() {
        return false;
    }

    @Override
    public boolean isOpenFromLeft() {
        return false;
    }

    @Override
    public int getOpeningDecimalValue() {
        return 0;
    }

    @Override
    public String getOpeningBinaryValue() {
        return "00000000";
    }

    @Override
    public String toString() {
        String str = "";

        return str + '\u25A0';
    }
}
