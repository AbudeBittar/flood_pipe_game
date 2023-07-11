package logic.helpers;


import logic.game.PlayingField;
import logic.cells.*;

import java.util.*;

/**
 * class that defines all helper methods.
 *
 * Note: Helper classes are object-oriented. For more infos ->
 * @see <a href="https://en.wikipedia.org/wiki/Helper_class"></a>
 *
 * @author Abdulrahman Al Bittar
 */
public class Helper {

    private static final Random RANDOM = new Random();

    private Helper(){

    }

    /**
     * determines, if the given number is negative.
     *
     * @param num given number
     * @return true, if the given number is negative.
     */
    public static boolean isNegative(int num) {
        return num < 0;
    }

    /**
     * determines, if the given object is equal to null.
     *
     * @param obj given object
     * @return true,  if the given object is equal to null.
     */
    public static boolean isNull(Object obj){
        return obj == null;
    }


    /**
     * checks if the given number is in range between tow given numbers
     * @param toCheck to check number
     * @param firstNo first number
     * @param secondNo second number
     * @return true, if to check number is in the range.
     */
    public static boolean inRange(int toCheck, int firstNo, int secondNo) {
        return (toCheck >= firstNo && toCheck <= secondNo);
    }


    /**
     * generates a random integer between the specified minimum and maximum values, inclusive.
     *
     * The Math.random() method is used to generate a random decimal between 0 and 1, which is then multiplied by the range
     * (the difference between the maximum and minimum values, plus one) and added to the minimum value to give the final random number.
     *
     * @param min the minimum value
     * @param max the maximum value
     * @return the final random number
     */
    public static int generateRandomNo(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        return RANDOM.nextInt((max - min) + 1) + min;
    }


    /**
     * checks the validation of the given collection ( not null and not empty)
     *
     * @param c given collection
     * @return true, if the given collection is valid
     */
    public static boolean validCollection(Collection<?> c) {
        return c != null && !c.isEmpty();
    }


    /**
     * transpose the given int matrix. this means the rows will be replaced with the columns and the columns with the rows.
     *
     * @param matrix given matrix of integers
     * @return transposed matrix
     */
    public static int[][] transposeMatrix(int[][] matrix) {
        assert !isNull(matrix);

        int row = matrix[0].length;
        int column = matrix.length;
        int[][] transpose = new int[column][row];

        for(int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                transpose[j][i] = matrix[i][j];
            }
        }

        return transpose;
    }


    /**
     * repeats tabs by the given number. it used to print string json pretty.
     *
     * @param noOfTabs number of repeats
     * @return string of tabs.
     */
    public static String printTabs(int noOfTabs){
        return "\t".repeat(noOfTabs);
    }

    /**
     * converts the given matrix to a string, with adding tabs to print pretty
     *
     * @param matrix given matrix
     * @param noOfTabs number of repeats
     * @return string representation for the given matrix.
     */
    public static String intMatrixToString(int[][] matrix, int noOfTabs) {
        StringBuilder str = new StringBuilder();

        for (int[] victor : matrix) {
            str.append(printTabs(noOfTabs)).append("[");
            for (int j = 0; j < victor.length; j++) {
                if (j == victor.length - 1) {
                    str.append(victor[j]);
                } else {
                    str.append(victor[j]).append(", ");
                }
            }
            str.append("],\n");
        }

        return str.substring(0, str.length() - 2); // to remove last comma

    }


    /**
     * fills zeros to the right of the given string binary number.
     *
     * @param binary given string binary number
     * @return string binary number with 8 digits.
     */
    public static String fillZerosRight(String binary) {
        int length = 8 - binary.length();
        String res = "";
        for (int i = 0; i < length; i++) {
            res +="0";
        }

        res += binary;

        return res;

    }


    /**
     * converts the given integer number to a binary number represented as string.
     *
     * @param n given integer number
     * @return  binary number represented as string.
     */
    public static String intToBinary(int n) {
        return Integer.toBinaryString(n);
    }


    /**
     * creates and prints out string representation of the drawing box board.
     * @param board given board.
     */
    public static void printBoard(Cell[][] board){
        PlayingField playingField = new PlayingField();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Cell cell = board[i][j];
                if (playingField.isStraightPipe(cell)) {
                    Pipe pipe = (StraightPipe) cell;
                    System.out.print(pipe.toString());
                }
                if (playingField.isCurvePipe(cell)) {
                    Pipe pipe = (CurvePipe) cell;
                    System.out.print(pipe.toString());
                }
                if (playingField.isT_Pipe(cell)) {
                    Pipe pipe = (T_Pipe) cell;
                    System.out.print(pipe.toString());
                }
                if (playingField.isEndPipe(cell)) {
                    Pipe pipe = (EndPipe) cell;
                    System.out.print(pipe.toString());
                }
                if (playingField.isWall(cell)) {
                    Wall wall = (Wall) cell;
                    System.out.print(wall.toString());
                }
            }
            System.out.println();
        }
    }
}
