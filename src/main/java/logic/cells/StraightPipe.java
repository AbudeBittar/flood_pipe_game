package logic.cells;

import logic.helpers.Rotation;

import java.util.*;

import static logic.cells.OpeningDirection.*;


/**
 * Class that represents a straight pipe.
 *
 * @author Abdulrahman Al Bittar
 */
public class StraightPipe extends Pipe {



    /**
     * copy constructor
     *
     * @param pipe other pipe
     */
    public StraightPipe(Pipe pipe) {
        super(pipe);

        if (pipe.openingDirections.size() != 2) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }

    /**
     * constructor that creates a straight pipe with specific opening directions.
     *
     * @param openingDirections specific opening directions.
     */
    public StraightPipe(EnumSet<OpeningDirection> openingDirections) {
        super(openingDirections);

        if (openingDirections.size() != 2) {
            throw new IllegalArgumentException("No such opening directions");
        }

    }


    @Override
    public Pipe rotate(Rotation rotation) {
        // rotation direction does not matter. result is anyway the same.
        if (this.isOpenFromLeft() && this.isOpenFromRight()) {
            this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP)));
            return this;
        } else if (this.isOpenFromTop() && this.isOpenFromBottom()) {
            this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT)));
            return this;
        } else {
            throw new IllegalArgumentException("No such opening directions");
        }


    }



    @Override
    public int getOpeningDecimalValue(){
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(BOTTOM)) {
            return 5;
        }
        if (this.openingDirections.contains(RIGHT) && this.openingDirections.contains(LEFT)) {
            return 10;
        }

        throw new IllegalStateException("NO integer value for such opening directions");
    }

    @Override
    public String getOpeningBinaryValue() {
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(BOTTOM)) {
            return "00000101";
        }
        if (this.openingDirections.contains(RIGHT) && this.openingDirections.contains(LEFT)) {
            return "00001010";
        }

        throw new IllegalStateException("NO binary value for such opening directions");
    }



    @Override
    public String toString() {
        String str = "";
        if (this.getPipeOpenings().contains(LEFT) && this.getPipeOpenings().contains(RIGHT)) {
            str = str + '\u2501';
        } else if (this.getPipeOpenings().contains(TOP) && this.getPipeOpenings().contains(BOTTOM)) {
            str = str + '\u2503';
        }

//        if (str.equals("")) {
//            throw new IllegalArgumentException("No such opening directions | StraightPipe.class");
//        }

        return str;
    }
}
