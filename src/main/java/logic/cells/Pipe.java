package logic.cells;

import logic.helpers.Rotation;

import java.util.*;

import static logic.cells.OpeningDirection.*;


/**
 * Class, that represents a single Pipe,
 *  this is the parent  class of the whole Pipes.
 *
 * @author Abdulrahman Al Bittar
 */
public abstract class Pipe extends Cell {


    /**
     * Set of all opining Directions of the Pipe
     */
    protected EnumSet<OpeningDirection> openingDirections;



    /**
     * Constructor.
     *
     * @param openingDirections Set of all opining Directions of the Pipe
     */
    public Pipe(EnumSet<OpeningDirection> openingDirections) {
        this.openingDirections = openingDirections;
    }


    /**
     * Copy constructor.
     */
    public Pipe(Pipe pipe) {
        this.openingDirections = pipe.openingDirections;
    }




    /**
     * Set of all opining Directions of the Pipe
     *
     */
    @Override
    public ArrayList<OpeningDirection> getPipeOpenings() {
        return new ArrayList<>(this.openingDirections);
    }

    /**
     * Set of all opining Directions of the Pipe
     *
     */
    private void setPipeOpenings(EnumSet<OpeningDirection> openingDirections) {
        this.openingDirections = openingDirections;
    }


    public boolean isOpenFromTop() {
        return this.openingDirections != null && !this.openingDirections.isEmpty() && this.openingDirections.contains(TOP);
    }

    public boolean isOpenFromRight() {
        return this.openingDirections != null && !this.openingDirections.isEmpty() && this.openingDirections.contains(RIGHT);
    }

    public boolean isOpenFromBottom() {
        return this.openingDirections != null && !this.openingDirections.isEmpty() && this.openingDirections.contains(BOTTOM);
    }

    public boolean isOpenFromLeft() {
        return this.openingDirections != null && !this.openingDirections.isEmpty() && this.openingDirections.contains(LEFT);
    }

    @Override
    public int getOpeningDecimalValue(){
        return this.binaryToInteger(this.getOpeningBinaryValue());
    }




    private int binaryToInteger(String binary) {
        char[] numbers = binary.toCharArray();
        int result = 0;
        for(int i=numbers.length - 1; i>=0; i--)
            if(numbers[i]=='1')
                result += Math.pow(2, (numbers.length-i - 1));
        return result;
    }





    /**
     * Rotates this pipe 90° into the given rotation direction.
     *
     * @param rotationDirection the given rotation direction.
     *
     * @return this pipe after rotating.
     */
    public abstract Pipe rotate(Rotation rotationDirection);



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pipe)) return false;
        Pipe pipe = (Pipe) o;
        return Objects.equals(openingDirections, pipe.openingDirections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openingDirections);
    }

    @Override
    public abstract String toString();


}
