package logic.cells;

import logic.helpers.Rotation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static logic.cells.OpeningDirection.*;


/**
 * Class that represents an end pipe.
 *
 * @author Abdulrahman Al Bittar
 */
public class EndPipe extends Pipe {

    /**
     * constructor that creates an end pipe with one specific opening direction.
     *
     * @param openingDirections specific opening directions.
     */
    public EndPipe(EnumSet<OpeningDirection> openingDirections) {
        super(openingDirections);
        if (openingDirections.size() != 1) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }

    /**
     * copy constructor
     *
     * @param pipe other pipe
     */
    public EndPipe(Pipe pipe) {
        super(pipe);

        if (pipe.openingDirections.size() != 1) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }


    @Override
    public Pipe rotate(Rotation rotation) {
        if (rotation == Rotation.LEFT) {
            if (this.isOpenFromRight()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP)));
                return this;
            } else if (this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(LEFT)));
                return this;
            }  else if (this.isOpenFromBottom()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(RIGHT)));
                return this;
            } else {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM)));
                return this;
            }
        } else { // right
            if (this.isOpenFromRight()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM)));
                return this;
            } else if (this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(RIGHT)));
                return this;
            }  else if (this.isOpenFromBottom()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(LEFT)));
                return this;
            } else {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP)));
                return this;
            }
        }

    }


    @Override
    public int getOpeningDecimalValue(){
        if (this.openingDirections.contains(TOP)) {
            return 1;
        }
        if (this.openingDirections.contains(RIGHT)) {
            return 2;
        }
        if (this.openingDirections.contains(LEFT)) {
            return 8;
        }
        if (this.openingDirections.contains(BOTTOM)) {
            return 4;
        }

        throw new IllegalStateException("NO integer value for such opening directions");
    }

    @Override
    public String getOpeningBinaryValue() {
        if (this.openingDirections.contains(TOP)) {
            return "00000001";
        }
        if (this.openingDirections.contains(RIGHT)) {
            return "00000010";
        }
        if (this.openingDirections.contains(LEFT)) {
            return "00001000";
        }
        if (this.openingDirections.contains(BOTTOM)) {
            return "00000100";
        }

        throw new IllegalStateException("NO integer value for such opening directions");
    }
    @Override
    public String toString() {
        String str = "";
        if (this.getPipeOpenings().contains(LEFT)) {
            str = str + '\u257C';
        } else if (this.getPipeOpenings().contains(TOP)) {
            str = str + '\u257D';
        }  else if (this.getPipeOpenings().contains(RIGHT)) {
            str = str + '\u257E';
        } else if (this.getPipeOpenings().contains(BOTTOM)) {
            str = str + '\u257F';
        }

        if (str.equals("")) {
            throw new IllegalArgumentException("No such opening directions | EndPipe.class");
        }

        return str;
    }
}
