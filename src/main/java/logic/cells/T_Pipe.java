package logic.cells;

import logic.helpers.Rotation;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static logic.cells.OpeningDirection.*;


/**
 * Class that represents a T-pipe.
 *
 * @author Abdulrahman Al Bittar
 */
public class T_Pipe extends Pipe {

    /**
     * copy constructor
     *
     * @param pipe other pipe
     */
    public T_Pipe(Pipe pipe) {
        super(pipe);

        if (pipe.openingDirections.size() != 3) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }


    /**
     * constructor that creates a t-cross pipe with specific opening directions.
     *
     * @param openingDirections specific opening directions.
     */
    public T_Pipe(EnumSet<OpeningDirection> openingDirections) {
        super(openingDirections);

        if (openingDirections.size() != 3) {
            throw new IllegalArgumentException("No such opening directions");
        }
    }


    @Override
    public Pipe rotate(Rotation rotation) {
        if (rotation == Rotation.LEFT) {
            if (this.isOpenFromLeft() && this.isOpenFromBottom() && this.isOpenFromRight()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT, TOP)));
                return this;
            } else if (this.isOpenFromRight() && this.isOpenFromBottom() && this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, TOP)));
                return this;
            }  else if (this.isOpenFromLeft() && this.isOpenFromRight() && this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, LEFT)));
                return this;
            } else if (this.isOpenFromLeft() && this.isOpenFromTop() && this.isOpenFromBottom()){
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT, BOTTOM)));
                return this;
            } else {
                throw new IllegalArgumentException("No such opening directions");
            }
        } else { // rotation to the right
            if (this.isOpenFromLeft() && this.isOpenFromBottom() && this.isOpenFromRight()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT, TOP)));
                return this;
            } else if (this.isOpenFromRight() && this.isOpenFromBottom() && this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM)));
                return this;
            }  else if (this.isOpenFromLeft() && this.isOpenFromRight() && this.isOpenFromTop()) {
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, RIGHT)));
                return this;
            } else if (this.isOpenFromLeft() && this.isOpenFromTop() && this.isOpenFromBottom()){
                this.openingDirections = EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT, TOP)));
                return this;
            } else {
                throw new IllegalArgumentException("No such opening directions");
            }
        }


    }



    @Override
    public int getOpeningDecimalValue(){
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(LEFT)) {
            return 13;
        }
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(RIGHT)) {
            return 7;
        }
        if (this.openingDirections.contains(LEFT) && this.openingDirections.contains(RIGHT) && this.openingDirections.contains(TOP)) {
            return 11;
        }
        if (this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(LEFT) && this.openingDirections.contains(RIGHT)) {
            return 14;
        }

        throw new IllegalStateException("NO integer value for such opening directions");
    }

    @Override
    public String getOpeningBinaryValue() {
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(LEFT)) {
            return "00001101";
        }
        if (this.openingDirections.contains(TOP) && this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(RIGHT)) {
            return "00000111";
        }
        if (this.openingDirections.contains(LEFT) && this.openingDirections.contains(RIGHT) && this.openingDirections.contains(TOP)) {
            return "00001011";
        }
        if (this.openingDirections.contains(BOTTOM) && this.openingDirections.contains(LEFT) && this.openingDirections.contains(RIGHT)) {
            return "00001110";
        }

        throw new IllegalStateException("NO integer value for such opening directions");
    }


    @Override
    public String toString() {
        String str = "";
        if (this.getPipeOpenings().contains(RIGHT) && this.getPipeOpenings().contains(BOTTOM) && this.getPipeOpenings().contains(TOP)) {
            str = str + '\u2523';
        } else if (this.getPipeOpenings().contains(LEFT) && this.getPipeOpenings().contains(BOTTOM) && this.getPipeOpenings().contains(TOP)) {
            str = str + '\u252B';
        }  else if (this.getPipeOpenings().contains(RIGHT) && this.getPipeOpenings().contains(BOTTOM) && this.getPipeOpenings().contains(LEFT)) {
            str = str + '\u2533';
        } else if (this.getPipeOpenings().contains(LEFT) && this.getPipeOpenings().contains(TOP) && this.getPipeOpenings().contains(RIGHT)){
            str = str + '\u253B';
        }


        return str;
    }
}
