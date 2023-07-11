package logic.helpers;

import java.util.Objects;

/**
 * class that defines the positions on the playing field.
 *
 * @author Abdulrahman Al Bittar
 */
public class Position implements Comparable<Position> {

    /**
     * Row
     */
    private int x;

    /**
     * Column
     */
    private int y;

    /**
     * Constructor.
     *
     * @param x Row
     * @param y Column
     */
    public Position(int x, int y) {
        assert !Helper.isNegative(x);
        assert !Helper.isNegative(y);

        this.x = x;
        this.y = y;
    }

    public static Position reverse(Position position) {
        return new Position(position.getY(), position.getX());
    }

    /**
     * ############################################## GETTER & SETTER ##############################################
     **/
    public int getX() {
        int row = this.x;
        return row;
    }

    public int getY() {
        int col = this.y;
        return col;
    }

    private void setX(int x) {
        assert Helper.isNegative(x);

        this.x = x;
    }

    private void setY(int y) {
        assert Helper.isNegative(y);

        this.y = y;
    }


    /** ############################################## Logic Methods ############################################## **/



    /**
     * determinate the above cell of precious cell.
     *
     * @param position position of current cell
     * @param noOfRows number of rows
     * @param noOfColumns number of columns
     * @return the above cell of precious cell.
     */
    public static Position getAboveOfPrev(Position position, int noOfRows, int noOfColumns) {
        assert !Helper.isNegative(noOfRows);
        assert !Helper.isNegative(noOfColumns);
        assert Helper.inRange(position.getX(), 0, noOfRows - 1);
        assert Helper.inRange(position.getY(), 0, noOfColumns - 1);

        Position prev = Position.getPrev(position, noOfRows, noOfColumns, true);
        assert prev != null;
        return Position.getAbove(prev, noOfRows, noOfColumns, true);
    }


    /**
     * determinate the neighbour cell to the left of current given position .
     * @param position current given position
     * @param noOfRows number of rows
     * @param noOfColumns number of columns
     * @param overFlow overflow mode
     * @return neighbour cell to the left of current given position
     */
    public static Position getPrev(Position position, int noOfRows, int noOfColumns, boolean overFlow) {
        assert !Helper.isNegative(noOfRows);
        assert !Helper.isNegative(noOfColumns);
        assert Helper.inRange(position.getX(), 0, noOfRows - 1);
        assert Helper.inRange(position.getY(), 0, noOfColumns - 1);


        int lastColumn = noOfColumns - 1;
        int i = position.getX();
        int j = position.getY();


        if (j == 0) {
            if (overFlow) {
                return new Position(i, lastColumn);
            } else{
                return null;
            }
        }

        return new Position(i, j - 1);

    }


    /**
     * determinate the neighbour cell to the right of current given position .
     * @param position current given position
     * @param noOfRows number of rows
     * @param noOfColumns number of columns
     * @param overFlow overflow mode
     * @return neighbour cell to the right of current given position
     */
    public static Position getNext(Position position, int noOfRows, int noOfColumns, boolean overFlow) {
        assert !Helper.isNegative(noOfRows);
        assert !Helper.isNegative(noOfColumns);
        assert Helper.inRange(position.getX(), 0, noOfRows - 1);
        assert Helper.inRange(position.getY(), 0, noOfColumns - 1);


        int lastColumn = noOfColumns - 1;
        int i = position.getX();
        int j = position.getY();


        if (j == lastColumn) {
            if (overFlow) {
                return new Position(i, 0);
            } else {
                return null;
            }
        }

        return new Position(i, j + 1);
    }


    /**
     * determinate the neighbour cell to the top of current given position .
     * @param position current given position
     * @param noOfRows number of rows
     * @param noOfColumns number of columns
     * @param overFlow overflow mode
     * @return neighbour cell to the top of current given position
     */
    public static Position getAbove(Position position, int noOfRows, int noOfColumns, boolean overFlow) {
        assert !Helper.isNull(position);
        assert !Helper.isNegative(noOfRows);
        assert !Helper.isNegative(noOfColumns);
        assert Helper.inRange(position.getX(), 0, noOfRows - 1);
        assert Helper.inRange(position.getY(), 0, noOfColumns - 1);

        int lastRow = noOfRows - 1;
        int i = position.getX();
        int j = position.getY();

        if (i == 0) {
            if (overFlow) {
                return new Position(lastRow, j);
            } else {
                return null;
            }
        }

        return new Position(i - 1, j);

    }


    /**
     * determinate the neighbour cell to the bottom of current given position .
     * @param position current given position
     * @param noOfRows number of rows
     * @param noOfColumns number of columns
     * @param overFlow overflow mode
     * @return neighbour cell to the bottom of current given position
     */
    public static Position getLower(Position position, int noOfRows, int noOfColumns, boolean overFlow) {
        assert !Helper.isNegative(noOfRows);
        assert !Helper.isNegative(noOfColumns);
        assert Helper.inRange(position.getX(), 0, noOfRows - 1);
        assert Helper.inRange(position.getY(), 0, noOfColumns - 1);

        int lastRow = noOfRows - 1;
        int i = position.getX();
        int j = position.getY();

        if (i == lastRow) {
            if (overFlow) {
                return new Position(0, j);
            } else {
                return null;
            }
        }

        return new Position(i + 1, j);
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (Helper.isNull(obj) || getClass() != obj.getClass()) {
            return false;
        }

        Position position = (Position) obj;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("Position{")
                .append("x=").append(x)
                .append(", y=").append(y)
                .append('}').toString();
        return str.toString();
//        return "";
    }

    @Override
    public int compareTo(Position position) {
        if (position.getX() == this.x - 1 && position.getY() == this.y) {       // 41 assuming position is 51
            return 1; // top
        } else if (position.getX() == this.x && position.getY() == this.y + 1) { // 02 assuming position is 01
            return 2; // right
        } else if (position.getX() == this.x + 1 && position.getY() == this.y) { // 11 assuming position is 01
            return 3; // bottom
        } else if (position.getX() == this.x && position.getY() == this.y - 1) { // 00 assuming position is 01
            return 4; // left
        } else {
            return 0; // must be treated separately
        }
    }
}
