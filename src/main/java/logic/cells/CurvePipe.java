package logic.cells;

import logic.helpers.Rotation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static logic.cells.OpeningDirection.*;


/**
 * Class that represents a curve pipe.
 *
 * @author Abdulrahman Al Bittar
 */
public class CurvePipe extends Pipe {

    /**
     * constructor that creates a curve pipe with specific opening directions.
     *
     * @param openingDirections specific opening directions.
     */
    public CurvePipe(EnumSet<OpeningDirection> openingDirections) {
        super(openingDirections);
        if (openingDirections.size() != 2) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }

    /**
     * copy constructor
     *
     * @param pipe other pipe
     */
    public CurvePipe(Pipe pipe) {
        super(pipe);

        if (pipe.openingDirections.size() != 2) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }


    @Override
    public Pipe rotate(Rotation rotation) {
        if (rotation == Rotation.LEFT) {
            if (this.isOpenFromLeft() && this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT)));
                return this;
            } else if (this.isOpenFromLeft() && this.isOpenFromBottom()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT)));
                return this;
            }  else if (this.isOpenFromBottom() && this.isOpenFromRight()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT)));
                return this;
            } else if (this.isOpenFromRight() && this.isOpenFromTop()){
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT)));
                return this;
            } else {
                throw new IllegalArgumentException("No such opening directions");
            }
        } else { // rotation to the right
            if (this.isOpenFromLeft() && this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT)));
                return this;
            } else if (this.isOpenFromLeft() && this.isOpenFromBottom()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT)));
                return this;
            }  else if (this.isOpenFromBottom() && this.isOpenFromRight()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT)));
                return this;
            } else if (this.isOpenFromRight() && this.isOpenFromTop()){
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM)));
                return this;
            } else {
                throw new IllegalArgumentException("No such opening directions");
            }
        }



    }

    @Override
    public String getOpeningBinaryValue() {
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(RIGHT)) {
            return "00000011";
        }
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(LEFT)) {
            return "00001001";
        }
        if (this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(RIGHT)) {
            return "00000110";
        }
        if (this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(LEFT)) {
            return "00001100";
        }

        System.out.println("CurvePipe opening directions " + this.getPipeOpenings());
        throw new IllegalStateException("NO integer value for such opening directions");
    }



    @Override
    public String toString() {
        String str = "";
        if (this.getPipeOpenings().contains(RIGHT) && this.getPipeOpenings().contains(BOTTOM)) {
            str = str + '\u250F';
        } else if (this.getPipeOpenings().contains(LEFT) && this.getPipeOpenings().contains(BOTTOM)) {
            str = str + '\u2513';
        }  else if (this.getPipeOpenings().contains(RIGHT) && this.getPipeOpenings().contains(TOP)) {
            str = str + '\u2517';
        } else if (this.getPipeOpenings().contains(LEFT) && this.getPipeOpenings().contains(TOP)){
            str = str + '\u251B';
        }

        return str;
    }

}
