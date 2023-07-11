package logic.ai;

import logic.cells.Cell;
import logic.cells.CurvePipe;
import logic.cells.EndPipe;
import logic.cells.OpeningDirection;
import logic.cells.Pipe;
import logic.cells.StraightPipe;
import logic.cells.T_Pipe;
import logic.cells.Wall;
import logic.helpers.Constants;
import logic.helpers.Helper;
import logic.helpers.Position;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static logic.cells.OpeningDirection.*;

public class CellGenerator {


    /**
     * creates a solved board using rule based reasoning og AI.
     *
     * @param noOfRows number of board rows
     * @param noOfColumns number of board columns
     * @param overflow percentage of allowed walls
     *
     * @return  a solved board.
     */
    public static Cell[][] generateSolvedBoard(int noOfRows, int noOfColumns, boolean overflow) {
        assert validateBoardSize(noOfRows, noOfColumns);

        int lastRow = noOfRows - 1;
        int lastColumn = noOfColumns - 1;

        Cell[][] board = new Cell[noOfRows][noOfColumns];

        if (!overflow) {
            board[0][0] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
            for (int i = 0; i < noOfRows; i++) {
                for (int j = 0; j < noOfColumns; j++) {
                    // Erste Reihe, Zwischenspalten
                    if (i == 0 && j > 0 && j < lastColumn) {
                        Pipe prevPipe = (Pipe) board[0][j - 1]; // To the left
                        if (prevPipe != null) {
                            if (prevPipe.isOpenFromRight()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(TOP)), LEFT);
                                board[i][j] = cell;
                            }
                        } else {
                            Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                            board[i][j] = cell;
                        }

                    }
                    // Erste Reihe, letzte Zelle
                    if (i == 0 && j == lastColumn) {
                        Pipe prevPipe = (Pipe) board[i][j - 1]; // To the left
                        if (prevPipe != null) {
                            if (prevPipe.isOpenFromRight()) {
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                                board[i][j] = cell;
                            }
                        }

                    }
                    // ZwischenReihen, Erste Spalte
                    if (i != 0 && i != lastRow && j == 0) {
                        Pipe abovePipe = (Pipe) board[i - 1][0];
                        if (abovePipe != null) {
                            if (abovePipe.isOpenFromBottom()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT)), TOP);
                                board[i][j] = cell;
                            }
                        } else {
                            Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                            board[i][j] = cell;
                        }

                    }
                    // letzte Reihe, erste Spalte
                    if (i == lastRow && j == 0) {
                        Pipe abovePipe = (Pipe) board[i - 1][j];
                        if (abovePipe != null) {
                            if (abovePipe.isOpenFromBottom()) {
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                board[i][j] = cell;
                            } else { //  above closed
                                board[i][j] = null;
                            }
                        } else { // above is a wall
                            // make above an end pipe
                            board[i - 1][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM))));
                            Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                            board[i][j] = cell;
                        }

                    }
                    // ZwischenReihen, letzte Spalte
                    if (i != 0 && i != lastRow && j == lastColumn) {
                        Pipe abovePipe = (Pipe) board[i - 1][j];
                        Pipe prevPipe = (Pipe) board[i][j - 1]; // To the left
                        if (prevPipe != null && abovePipe != null) { // prev and above are not walls
                            if (prevPipe.isOpenFromRight() && abovePipe.isOpenFromBottom()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(RIGHT)), LEFT, TOP);
                                board[i][j] = cell;
                            } else if (abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above opened, prev closed
                                Cell cell = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above closed, prev opened
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above closed, prev closed
                                board[i][j] = null;
                            }
                        } else if (abovePipe == null) { // above is a wall
                            if (prevPipe != null) { // prev is not a wall
                                // make above an end pipe
                                board[i - 1][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM)))); // TODO: Check if this is right
                                if (prevPipe.isOpenFromRight()) { // prev opened
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(RIGHT)), LEFT, TOP);
                                    board[i][j] = cell;
                                } else { // prev closed
                                    board[i][j] = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                }
                            }
                        } else { // prev is a wall
                            if (abovePipe.isOpenFromBottom()) { // above opened
                                Cell cell = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                board[i][j] = cell;
                            } else {
                                board[i][j] = null;
                            }
                        }
                    }
                    // letzte Reihe, letzte Spalte
                    if (i == lastRow && j == lastColumn) {
                        Pipe abovePipe = (Pipe) board[i - 1][j];
                        Pipe prevPipe = (Pipe) board[i][j - 1];
                        if (prevPipe != null && abovePipe != null) { // preve and above are not a wall
                            if (abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above opened, prev opened
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                board[i][j] = cell;
                            } else if (abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above opened, prev closed
                                Cell cell = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP))));
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above closed, prev opened
                                Cell cell = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT))));
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above closed, prev closed
                                board[i][j] = null;
                            }
                        } else if (abovePipe == null) { // above is a wall
                            if (prevPipe != null) { // prev is not a wall
                                if (prevPipe.isOpenFromRight()) { // prev opened
                                    Cell cell = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT))));
                                    board[i][j] = cell;
                                } else { // prev closed
                                    board[i][j] = null;
                                }
                            }
                        } else { // prev is a wall
                            if (abovePipe.isOpenFromBottom()) { // above opened
                                Cell cell = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP))));
                                board[i][j] = cell;
                            } else {
                                board[i][j] = null;
                            }
                        }
                    }
                    // letzte Reihe, Zwischenspalten
                    if (i == lastRow && j != 0 && j != lastColumn) {
                        Pipe abovePipe = (Pipe) board[i - 1][j];
                        Pipe prevPipe = (Pipe) board[i][j - 1]; // To the left
                        if (prevPipe != null && abovePipe != null) { // prev and above are not walls
                            if (prevPipe.isOpenFromRight() && abovePipe.isOpenFromBottom()) {  // above opened, prev opened
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(BOTTOM)), LEFT, TOP);
                                board[i][j] = cell;
                            } else if (abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above opened, prev closed
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above closed, prev opened
                                Cell cell = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above closed, prev closed
                                board[i][j] = null;
                            }
                        } else if (abovePipe == null) { // above is a wall
                            if (prevPipe != null) { // prev is not a wall
                                if (prevPipe.isOpenFromRight()) { // prev opened
                                    Cell cell = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                                    board[i][j] = cell;
                                } else { // prev closed
                                    board[i][j] = null;
                                }
                            }
                        } else { // prev is a wall
                            if (abovePipe.isOpenFromBottom()) { // above opened
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                board[i][j] = cell;
                            } else {
                                board[i][j] = null;
                            }
                        }
                    }
                    // the rest cells | the inner cells
                    if (i != 0 && i < lastRow && j != 0 && j < lastColumn) {
                        Pipe abovePipe = (Pipe) board[i - 1][j];
                        Pipe prevPipe = (Pipe) board[i][j - 1];

                        if (prevPipe != null && abovePipe != null) { // preve and above are not a wall
                            if (abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above opened, prev opened
                                if (abovePipe.isOpenFromLeft() && prevPipe.isOpenFromTop()) { // to avoid circles
                                    board[i][j] = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, BOTTOM)))); // this way are no circles possible
                                }
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP, LEFT);
                                board[i][j] = cell;
                            } else if (abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above opened, prev closed
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT)), TOP);
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above closed, prev opened
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(TOP)), LEFT);
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above closed, prev closed
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT, TOP)), RIGHT); // um zu versuchen, weitere Zellen zu generieren
                                board[i][j] = cell;
                            }
                        } else if (abovePipe == null) { // above is a wall
                            if (prevPipe != null) { // prev is not a wall
                                board[i - 1][j] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM)))); // TODO: Check if this is right
                                if (prevPipe.isOpenFromRight()) { // prev opened
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP, LEFT);
                                    board[i][j] = cell;
                                } else { // prev closed or wall
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP, RIGHT);
                                    board[i][j] = cell;
                                }
                            }
                        } else { // prev is a wall
                            Cell aboveOfPrev = board[i - 1][j - 1];
                            if (aboveOfPrev == null) {
                                // make aboveOfPrev an end pipe to avoid walls and to add an end pipe at positions, where no other pipes are allowed.
                                board[i - 1][j - 1] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM))));
                                Cell cellForPrev = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP)))); // um zu versuchen, weitere Zellen zu generieren
                                board[i][j - 1] = cellForPrev;
                                if (abovePipe.isOpenFromBottom()) { // above opened
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP, LEFT);
                                    board[i][j] = cell;
                                } else { // above closed
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(TOP)), LEFT);
                                    board[i][j] = cell;
                                }
                            } else { // above of prev not a wall -> no blocker
                                board[i][j - 1] = new EndPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT))));
                                if (abovePipe.isOpenFromBottom()) {
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP, LEFT);
                                    board[i][j] = cell;
                                } else {
                                    Cell cell = new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM, LEFT)))); // um zu versuchen, weitere Zellen zu generieren
                                    board[i][j] = cell;
                                }
                            }
                        }
                    }
                }
            }


        } else { // Overflow == True
            board[0][0] = getRandomCellWithRandomOpeningDirections();
            for (int i = 0; i < noOfRows; i++) {
                for (int j = 0; j < noOfColumns; j++) {
                    // no need to check if returned position ist equal to null, because there is no null value while overflow mode is activated.
                    Position posAbove = Position.getAbove(new Position(i, j), noOfRows, noOfColumns, true);
                    Pipe abovePipe;
                    if (posAbove != null) {
                        abovePipe = (Pipe) board[posAbove.getX()][posAbove.getY()];

                    } else {
                        throw new IllegalStateException("No Such Position, while overflow mode is activated.");
                    }

                    Position posPrev = Position.getPrev(new Position(i, j), noOfRows, noOfColumns, true);
                    Pipe prevPipe = (Pipe) board[posPrev.getX()][posPrev.getY()];


                    //erste Reihe, zwischenSpalten
                    if (i == 0 && j != 0 && j != lastColumn) {
                        if (prevPipe != null) {
                            if (prevPipe.isOpenFromRight()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, LEFT);
                                board[i][j] = cell;
                            } else {
                                Cell cell = CellGenerator.getRandomCellWithUnwantedOpeningDirections(LEFT);
                                board[i][j] = cell;
                            }
                        }
                    }
                    // Erste Reihe, letzte Spalte
                    if (i == 0 && j == lastColumn) {
                        Position posNext = Position.getNext(new Position(i, j), noOfRows, noOfColumns, true);
                        Pipe nextPipe = (Pipe) board[posNext.getX()][posNext.getY()];

                        if (nextPipe != null && prevPipe != null) { // prev and next are not walls
                            if (prevPipe.isOpenFromRight() && nextPipe.isOpenFromLeft()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, LEFT, RIGHT);
                                board[i][j] = cell;
                            } else if (nextPipe.isOpenFromLeft() && !prevPipe.isOpenFromRight()) { // next opened, prev closed
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT)), RIGHT);
                                board[i][j] = cell;
                            } else if (!nextPipe.isOpenFromLeft() && prevPipe.isOpenFromRight()) { // next closed, prev opened
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(RIGHT)), LEFT);
                                board[i][j] = cell;
                            } else if (!nextPipe.isOpenFromLeft() && !prevPipe.isOpenFromRight()) { // next closed, prev closed
                                board[i][j] = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                            }
                        } else if (nextPipe == null) { // next is a wall
                            if (prevPipe != null) { // prev is not a wall
                                if (prevPipe.isOpenFromRight()) { // prev opened
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(RIGHT)), LEFT);
                                    board[i][j] = cell;
                                } else { // prev closed
                                    Cell cell = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                    board[i][j] = cell;
                                }
                            }
                        } else { // prev is a wall
                            if (nextPipe.isOpenFromLeft()) { // next opened
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT)), RIGHT);
                                board[i][j] = cell;
                            } else {
                                Cell cell = new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                board[i][j] = cell;
                            }
                        }
                    }
                    // Zwischenreihen, erste Spalte.
                    if (i != 0 && i != lastRow && j == 0) {
                        if (abovePipe != null) {
                            if (abovePipe.isOpenFromBottom()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP);
                                board[i][j] = cell;
                            } else {
                                Cell cell = CellGenerator.getRandomCellWithUnwantedOpeningDirections(TOP);
                                board[i][j] = cell;
                            }
                        }
                    }

                    // Zwischenreihen, zwischenSpalten
                    if (i != 0 && i != lastRow && j != 0 && j != lastColumn) {
                        if (abovePipe != null && prevPipe != null) { // prev and next are not walls
                            if (prevPipe.isOpenFromRight() && abovePipe.isOpenFromBottom()) {
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(null, TOP, LEFT);
                                board[i][j] = cell;
                            } else if (abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above opened, prev closed
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT)), TOP);
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && prevPipe.isOpenFromRight()) { // above closed, prev opened
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(TOP)), LEFT);
                                board[i][j] = cell;
                            } else if (!abovePipe.isOpenFromBottom() && !prevPipe.isOpenFromRight()) { // above closed, prev closed
                                board[i][j] = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));
                            }
                        } else if (abovePipe == null) { // above is a wall
                            if (prevPipe != null) { // prev is not a wall
                                if (prevPipe.isOpenFromRight()) { // prev opened
                                    Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(TOP)), LEFT);
                                    board[i][j] = cell;
                                } else { // prev closed
                                    Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));
                                    board[i][j] = cell;
                                }
                            }
                        } else { // prev is a wall
                            if (abovePipe.isOpenFromBottom()) { // above opened
                                Cell cell = CellGenerator.getRandomCellWithSpecificOpeningDirections(new ArrayList<>(List.of(LEFT)), TOP);
                                board[i][j] = cell;
                            } else {
                                Cell cell = new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));
                                board[i][j] = cell;
                            }
                        }
                    }
                    // Zwischenreihen, letzte Spalte.
                    if (i != 0 && i != lastRow && j == lastColumn) {
                        Position posNext = Position.getNext(new Position(i, j), noOfRows, noOfColumns, true);
                        Pipe nextPipe = (Pipe) board[posNext.getX()][posNext.getY()];
                        ArrayList<OpeningDirection> unwanted = getUnwantedOpeningDirectionsForLastColumn(prevPipe, abovePipe, nextPipe);
                        if (unwanted.size() == 0) { // prev, above, next are opened
                            board[i][j] = new T_Pipe(EnumSet.copyOf(List.of(TOP, RIGHT, LEFT)));
                        } else {
                            if (unwanted.size() == 1) {
                                ArrayList<OpeningDirection> wanted = getWantedOpeningDirectionsForLastColumn(unwanted); // wanted.size() == 2
                                if ((wanted.contains(RIGHT) && wanted.contains(LEFT)) || (wanted.contains(TOP) && wanted.contains(BOTTOM))) {
                                    board[i][j] = new StraightPipe(EnumSet.copyOf(wanted));
                                } else {
                                    board[i][j] = new CurvePipe(EnumSet.copyOf(wanted));
                                }
                            } else {
                                Cell cell = closeLastRowOrColumn(unwanted);
                                board[i][j] = cell;
                            }
                        }
                    }

                    // letzte Reihe, Erste Spalte
                    // Hier sollte es vermieden werden, 4 Öffnungsrichtungen an der letzten Zelle in der letzten Reihe zu gestalten.
                    if (i == lastRow && j == 0) {
                        Position posLower = Position.getLower(new Position(i, j), noOfRows, noOfColumns, true);
                        Pipe lowerPipe = (Pipe) board[posLower.getX()][posLower.getY()];

                        Position posAboveOfPrev = Position.getAboveOfPrev(new Position(i, j), noOfRows, noOfColumns);
                        Pipe aboveOfPrevPipe = (Pipe) board[posAboveOfPrev.getX()][posAboveOfPrev.getY()];

                        ArrayList<OpeningDirection> unwanted = getUnwantedOpeningDirectionsToAvoidBlocker(abovePipe, lowerPipe, aboveOfPrevPipe);
                        Cell cell = avoidBlocker(unwanted);
                        board[i][j] = cell;
                    }

                    // letzte Reihe, Zwischenspalten
                    if (i == lastRow && j != 0 && j != lastColumn) {
                        Position posLower = Position.getLower(new Position(i, j), noOfRows, noOfColumns, true);
                        Pipe lowerPipe = (Pipe) board[posLower.getX()][posLower.getY()];
                        ArrayList<OpeningDirection> unwanted = getUnwantedOpeningDirectionsForLastRow(prevPipe, abovePipe, lowerPipe);
                        if (unwanted.size() == 0) { // prev, above, lower are opened
                            board[i][j] = new T_Pipe(EnumSet.copyOf(List.of(TOP, BOTTOM, LEFT)));
                        } else {
                            if (unwanted.size() == 1) {
                                ArrayList<OpeningDirection> wanted = getWantedOpeningDirectionsForLastRow(unwanted); // wanted.size() == 2
                                if ((wanted.contains(RIGHT) && wanted.contains(LEFT)) || (wanted.contains(TOP) && wanted.contains(BOTTOM))) {
                                    board[i][j] = new StraightPipe(EnumSet.copyOf(wanted));
                                } else {
                                    board[i][j] = new CurvePipe(EnumSet.copyOf(wanted));
                                }

                            } else {
                                Cell cell = closeLastRowOrColumn(unwanted);
                                board[i][j] = cell;
                            }
                        }
                    }
                    // letzte Reihe, letzte Spalte
                    if (i == lastRow && j == lastColumn) {
                        Position posLower = Position.getLower(new Position(i, j), noOfRows, noOfColumns, true);
                        Pipe lowerPipe = (Pipe) board[posLower.getX()][posLower.getY()];
                        Position posNext = Position.getNext(new Position(i, j), noOfRows, noOfColumns, true);
                        Pipe nextPipe = (Pipe) board[posNext.getX()][posNext.getY()];

                        ArrayList<OpeningDirection> unwanted = getUnwantedOpeningDirectionsToCloseTheBoard(abovePipe, lowerPipe, prevPipe, nextPipe);
                        Cell cell = closeBoard(unwanted);
                        board[i][j] = cell;
                    }
                }
            }
        }

        // convert Nulls To Walls
        convertNullsToWalls(board);

        return board;

    }

    /**
     * ############################ Unwanted means 'not allowed' in this context ############################
     * ############################   wanted means   'allowed'   in this context ############################
     */


    /**
     * get the wanted opening directions depending on use cases can appear at last column.
     *
     * @param unwanted list of unwanted opening directions
     * @return list of wanted opening directions
     */
    private static ArrayList<OpeningDirection> getWantedOpeningDirectionsForLastColumn(ArrayList<OpeningDirection> unwanted) {
        ArrayList<OpeningDirection> wanted = new ArrayList<>(); // TOP, RIGHT, LEFT
        if (!unwanted.contains(TOP)) {
            wanted.add(TOP);
        }
        if (!unwanted.contains(LEFT)) {
            wanted.add(LEFT);
        }
        if (!unwanted.contains(RIGHT)) {
            wanted.add(RIGHT);
        }
        return wanted;
    }


    /**
     * get the wanted opening directions depending on use cases can appear at last raw.
     *
     * @param unwanted list of unwanted opening directions
     * @return list of wanted opening directions
     */
    private static ArrayList<OpeningDirection> getWantedOpeningDirectionsForLastRow(ArrayList<OpeningDirection> unwanted) {
        ArrayList<OpeningDirection> wanted = new ArrayList<>();
        if (!unwanted.contains(TOP)) {
            wanted.add(TOP);
        }
        if (!unwanted.contains(LEFT)) {
            wanted.add(LEFT);
        }
        if (!unwanted.contains(BOTTOM)) {
            wanted.add(BOTTOM);
        }

        return wanted;
    }


    /**
     * validates board size
     *
     * @param noOfRows  number of board rows
     * @param noOfColumns number of board columns
     * @return true if the board size is valid. else false.
     */
    static boolean validateBoardSize(int noOfRows, int noOfColumns) {
        return Helper.inRange(noOfRows, Constants.MIN_NUMBER_OF_ROWS, Constants.MAX_NUMBER_OF_ROWS)
                && Helper.inRange(noOfColumns, Constants.MIN_NUMBER_OF_COLUMNS, Constants.MAX_NUMBER_OF_COLUMNS);
    }


    /**
     * get the unwanted opening directions depending on use cases can appear at last row.
     * @param prevPipe neighbour pipe to the left of current pipe
     * @param abovePipe neighbour pipe to the top of current pipe
     * @param lowerPipe neighbour pipe to the bottom of current pipe
     *
     * @return list of unwanted opening directions
     */
    static ArrayList<OpeningDirection> getUnwantedOpeningDirectionsForLastRow(Pipe prevPipe, Pipe abovePipe, Pipe lowerPipe) {
        ArrayList<OpeningDirection> unwanted = new ArrayList<>();
        if (prevPipe == null || !prevPipe.isOpenFromRight()) {
            unwanted.add(LEFT);
        }
        if (abovePipe == null || !abovePipe.isOpenFromBottom()) {
            unwanted.add(TOP);
        }
        if (lowerPipe == null || !lowerPipe.isOpenFromTop()) {
            unwanted.add(BOTTOM);
        }

        return unwanted;
    }

    /**
     * calculates the all possibilities can appear at the cell at last row and last column.
     *
     * @param unwanted not allowed opening directions.
     * @return a cell deepening on the current situation.
     */
    static Cell closeBoard(ArrayList<OpeningDirection> unwanted) {

        switch (unwanted.size()) { // all neighbours are closed
            case 4 -> { // all pipes are closed.
                return null;
            }
            case 3 -> { // 3 neighbours arw closed, one is opened -> endPipe
                ArrayList<OpeningDirection> wanted = getWantedOpeningDirections(unwanted); // wanted size = 1
                return new EndPipe(EnumSet.copyOf(wanted));
            }
            case 2 -> {
                ArrayList<OpeningDirection> wanted = getWantedOpeningDirections(unwanted); // wanted size = 2
                Cell cell = getRandomCellWithSpecificOpeningDirections(unwanted, wanted.get(0), wanted.get(1));
                return cell;

            }
            case 1 -> {
                ArrayList<OpeningDirection> wanted = getWantedOpeningDirections(unwanted);
                return new T_Pipe(EnumSet.copyOf(wanted));
            }
        }

        return null;
    }

    /**
     * calculates allowed opening directions depending on the given list of not allowed opening directions.
     *
     * @param unwanted list of not allowed opening directions.
     * @return list of allowed opening directions.
     */
    static ArrayList<OpeningDirection> getWantedOpeningDirections(ArrayList<OpeningDirection> unwanted) {
        ArrayList<OpeningDirection> wanted = new ArrayList<>();

        if (!unwanted.contains(TOP)) {
            wanted.add(TOP);
        }

        if (!unwanted.contains(BOTTOM)) {
            wanted.add(BOTTOM);
        }

        if (!unwanted.contains(LEFT)) {
            wanted.add(LEFT);
        }

        if (!unwanted.contains(RIGHT)) {
            wanted.add(RIGHT);
        }

        return wanted;
    }


    /**
     * calculates the all possibilities can appear at the cells at last row or last column.
     *
     * @param unwanted not allowed opening directions.
     * @return a cell deepening on the current situation.
     */
    static Cell closeLastRowOrColumn(ArrayList<OpeningDirection> unwanted) { // While overflow mode is activated.
        switch (unwanted.size()) {
            case 3 -> { // this means all neighbours are not connected to the current pipe
                return null;
            }
            case 2 -> {
                return CellGenerator.getRandomCellWithUnwantedOpeningDirections(unwanted.get(0), unwanted.get(1));
            }
            case 1 -> {
                return CellGenerator.getRandomCellWithUnwantedOpeningDirections(unwanted.get(0));
            }
        }

        return null;
    }


    /**
     * determinate the cell that fit to the cell at last row, first column if the overflow is activated.
     *
     * checks ig the cell at last row, last column has 3 connected opening directions.
     * if this is the case, it returns a pipe without opening direction to the left.
     *
     * @param unwanted not allowed opening directions.
     * @return cell that avoids a blocker that fit to the cell at last row, first column if the overflow is activated.
     */
    static Cell avoidBlocker(ArrayList<OpeningDirection> unwanted) {
        if (unwanted == null) {
            unwanted = new ArrayList<>();
        }
        // Unwanted can be TOP, BOTTOM or LEFT
        switch (unwanted.size()) {
            case 3 -> {
                return new EndPipe(EnumSet.copyOf(List.of(RIGHT)));
            }
            case 2 -> {
                if (unwanted.contains(LEFT)) { // aboveOfPrevPipe opened
                    if (unwanted.contains(TOP)) {
                        return new CurvePipe(EnumSet.copyOf(List.of(RIGHT, BOTTOM)));
                    }
                    if (unwanted.contains(BOTTOM)) {
                        return new CurvePipe(EnumSet.copyOf(List.of(RIGHT, TOP)));
                    }
                } else { // aboveOfPrevPipe closed -> !unwanted.contains(LEFT)
                    if (unwanted.contains(TOP) && unwanted.contains(BOTTOM)) {
//                        return getRandomCellWithSpecificOpeningDirections(unwanted, RIGHT);
                        return new StraightPipe(EnumSet.copyOf(List.of(RIGHT, LEFT)));
                    }
                }
            }

            case 1 -> {
                if (unwanted.contains(LEFT)) { // aboveOfPrevPipe opened
                    return getRandomCellWithSpecificOpeningDirections(unwanted, TOP, BOTTOM);
                } else if (unwanted.contains(TOP)) {
                    return getRandomCellWithSpecificOpeningDirections(unwanted, BOTTOM); // because left is not a must
                } else if (unwanted.contains(BOTTOM)) {
                    return getRandomCellWithSpecificOpeningDirections(unwanted, TOP); // because left is not a must
                }
            }
            case 0 -> { // all directions are required, but left is optional
                return getRandomCellWithSpecificOpeningDirections(null, TOP, BOTTOM);
            }

        }

        return null;
    }


    /**
     * get the not wanted opening directions depending on use cases can appear at the cell located at last column, last row.
     *  it is used to avoid a blocker to fit to the cell at last row, first column if the overflow is activated.
     *
     * @param abovePipe  neighbour pipe to the top of current pipe
     * @param lowerPipe  neighbour pipe to the bottom of current pipe
     * @param aboveOfPrevPipe  neighbour pipe to the top of the neighbour pipe located to the left of current pipe
     *                         (above of last cell at last column, last row)
     * @return list of not wanted opening directions depending on use cases can appear at the cell located at last column, last row.
     */
    static ArrayList<OpeningDirection> getUnwantedOpeningDirectionsToAvoidBlocker(Pipe abovePipe, Pipe lowerPipe, Pipe aboveOfPrevPipe) {
        ArrayList<OpeningDirection> list = new ArrayList<>();
        if (abovePipe == null || !abovePipe.isOpenFromBottom()) {
            list.add(TOP);
        }
        if (lowerPipe == null || !lowerPipe.isOpenFromTop()) {
            list.add(BOTTOM);
        }
        if (aboveOfPrevPipe != null && aboveOfPrevPipe.isOpenFromBottom()) { // prev is last cell at Board[lastRow][lastColumn]
            list.add(LEFT); // Keine Öffnung links, so wird das Sperren vermieden.
        }

        return list;
    }


    /**
     * get the unwanted opening directions depending on use cases can appear at last column.
     * @param prevPipe neighbour pipe to the left of current pipe
     * @param abovePipe neighbour pipe to the top of current pipe
     * @param nextPipe neighbour pipe to the right of current pipe
     *
     * @return list of unwanted opening directions
     */
    static ArrayList<OpeningDirection> getUnwantedOpeningDirectionsForLastColumn(Pipe prevPipe, Pipe abovePipe, Pipe nextPipe) {
        ArrayList<OpeningDirection> list = new ArrayList<>();
        if (prevPipe == null || !prevPipe.isOpenFromRight()) {
            list.add(LEFT);
        }
        if (abovePipe == null || !abovePipe.isOpenFromBottom()) {
            list.add(TOP);
        }
        if (nextPipe == null || !nextPipe.isOpenFromLeft()) {
            list.add(RIGHT);
        }

        return list;
    }

    /**
     * get the unwanted opening directions depending on use cases can appear at last column last row (last cell in the loop).
     * @param prevPipe neighbour pipe to the left of current pipe
     * @param abovePipe neighbour pipe to the top of current pipe
     * @param nextPipe neighbour pipe to the right of current pipe
     *
     * @return list of unwanted opening directions
     */
    static ArrayList<OpeningDirection> getUnwantedOpeningDirectionsToCloseTheBoard(Pipe abovePipe, Pipe lowerPipe, Pipe prevPipe, Pipe nextPipe) {
        ArrayList<OpeningDirection> list = new ArrayList<>();
        if (abovePipe == null || !abovePipe.isOpenFromBottom()) {
            list.add(TOP);
        }
        if (lowerPipe == null || !lowerPipe.isOpenFromTop()) {
            list.add(BOTTOM);
        }
        if (prevPipe == null || !prevPipe.isOpenFromRight()) {
            list.add(LEFT);
        }
        if (nextPipe == null || !nextPipe.isOpenFromLeft()) {
            list.add(RIGHT);
        }


        return list;
    }


    /**
     * determinate a cell randomly depending on the wanted and unwanted opening directions.
     * it calculates all possibilities for each scenario.
     * if there is multiple options, it returns one cell randomly.
     * if there is only one option, it returns the cell fits to that option.
     * if there is no options, it returns null.
     *
     * @param unwanted list of unwanted opening directions
     * @param wanted list of wanted opening directions
     * @return cell, that fits to the scenario the method called in.
     */
    static Cell getRandomCellWithSpecificOpeningDirections(ArrayList<OpeningDirection> unwanted, OpeningDirection... wanted) {
        if (unwanted == null) {
            unwanted = new ArrayList<>();
        }

        switch (wanted.length) {
            case 1 -> {

                if (wanted[0] == TOP) {
                    switch (Helper.generateRandomNo(1, 3)) { // select a pipe type randomly
                        case 1 -> { // Straight pipe
                            if (!unwanted.contains(BOTTOM)) {
                                return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                            } else { // try to get another pipe type
                                return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);

                            }
                        }
                        case 2 -> { // Curve pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                switch (Helper.generateRandomNo(1, 2)) {
                                    case 1 -> {
                                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                    }
                                    case 2 -> {
                                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                    }
                                }
                            } else { // there are unwanted opening directions
                                if (unwanted.contains(RIGHT) && !unwanted.contains(LEFT)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                } else if (unwanted.contains(LEFT) && !unwanted.contains(RIGHT)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                } else { // try to get another pipe type
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                        case 3 -> { // T- pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                switch (Helper.generateRandomNo(1, 3)) {
                                    case 1 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, BOTTOM))));
                                    }
                                    case 2 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, BOTTOM))));
                                    }
                                    case 3 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, LEFT))));
                                    }
                                }
                            } else { // There is unwanted opining direction
                                if (unwanted.contains(RIGHT) && !unwanted.contains(LEFT) && !unwanted.contains(BOTTOM)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, BOTTOM))));
                                } else if (unwanted.contains(LEFT) && !unwanted.contains(RIGHT) && !unwanted.contains(BOTTOM)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, BOTTOM))));
                                } else if (unwanted.contains(BOTTOM) && !unwanted.contains(RIGHT) && !unwanted.contains(LEFT)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, LEFT))));
                                } else {
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                    }
                }
                if (wanted[0] == RIGHT) {
                    switch (Helper.generateRandomNo(1, 3)) { // select a pipe type randomly
                        case 1 -> { // Straight pipe
                            if (!unwanted.contains(LEFT)) {
                                return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT))));
                            } else { // try to get another pipe type
                                return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                            }
                        }
                        case 2 -> { // Curve pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                if (Helper.generateRandomNo(1, 2) == 1) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP))));
                                } else {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                                }
                            } else {
                                if (unwanted.contains(TOP) && !unwanted.contains(BOTTOM)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                                } else if (unwanted.contains(BOTTOM) && !unwanted.contains(TOP)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP))));
                                } else { // try to get another pipe type
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                        case 3 -> { // T- pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                switch (Helper.generateRandomNo(1, 3)) {
                                    case 1 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, BOTTOM))));
                                    }
                                    case 2 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, LEFT))));
                                    }
                                    case 3 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM, LEFT))));
                                    }
                                }
                            } else { // There is unwanted opining direction
                                if (unwanted.contains(TOP) && !unwanted.contains(LEFT) && !unwanted.contains(BOTTOM)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT, BOTTOM))));
                                } else if (unwanted.contains(LEFT) && !unwanted.contains(TOP) && !unwanted.contains(BOTTOM)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, BOTTOM))));
                                } else if (unwanted.contains(BOTTOM) && !unwanted.contains(TOP) && !unwanted.contains(LEFT)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, LEFT))));
                                } else {
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                    }
                }
                if (wanted[0] == BOTTOM) {
                    switch (Helper.generateRandomNo(1, 3)) { // select a pipe type randomly
                        case 1 -> { // Straight pipe
                            if (!unwanted.contains(TOP)) {
                                return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP))));
                            } else { // try to get another pipe type
                                return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                            }
                        }
                        case 2 -> { // Curve pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                switch (Helper.generateRandomNo(1, 2)) {
                                    case 1 -> {
                                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));
                                    }
                                    case 2 -> {
                                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));
                                    }

                                }
                            } else {
                                if (unwanted.contains(LEFT) && !unwanted.contains(RIGHT)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT))));
                                } else if (unwanted.contains(RIGHT) && !unwanted.contains(LEFT)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));
                                } else { // try to get another pipe type
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                        case 3 -> { // T- pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                switch (Helper.generateRandomNo(1, 3)) {
                                    case 1 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, LEFT))));
                                    }
                                    case 2 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, RIGHT))));
                                    }
                                    case 3 -> {
                                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT, LEFT))));
                                    }
                                }
                            } else { // There is unwanted opining direction
                                if (unwanted.contains(TOP) && !unwanted.contains(LEFT) && !unwanted.contains(RIGHT)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT, LEFT))));
                                }
                                if (unwanted.contains(LEFT) && !unwanted.contains(RIGHT) && !unwanted.contains(TOP)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, RIGHT))));
                                }
                                if (unwanted.contains(RIGHT) && !unwanted.contains(LEFT) && !unwanted.contains(TOP)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, LEFT))));
                                } else {
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                    }
                }
                if (wanted[0] == LEFT) {
                    switch (Helper.generateRandomNo(1, 3)) { // select a pipe type randomly
                        case 1 -> { // Straight pipe
                            if (!unwanted.contains(RIGHT)) {
                                return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                            } else { // try to get another pipe type
                                return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                            }
                        }
                        case 2 -> { // Curve pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                switch (Helper.generateRandomNo(1, 2)) {
                                    case 1 -> {
                                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP))));
                                    }
                                    case 2 -> {
                                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                                    }

                                }
                            } else {
                                if (unwanted.contains(TOP) && !unwanted.contains(BOTTOM)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                                } else if (unwanted.contains(BOTTOM) && !unwanted.contains(TOP)) {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP))));
                                } else { // try to get another pipe type
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                        case 3 -> { // T- pipe
                            if (!Helper.validCollection(unwanted)) { // No unwanted opening directions
                                int range = Helper.generateRandomNo(1, 3);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, BOTTOM)))
                                    );
                                } else if (range == 2) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, RIGHT))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM))));
                                }
                            } else { // There is unwanted opining direction
                                if (unwanted.contains(BOTTOM) && !unwanted.contains(RIGHT) && !unwanted.contains(TOP)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, TOP))));
                                }
                                if (unwanted.contains(TOP) && !unwanted.contains(BOTTOM) && !unwanted.contains(RIGHT)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM, RIGHT))));
                                }
                                if (unwanted.contains(RIGHT) && !unwanted.contains(TOP) && !unwanted.contains(LEFT)) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, BOTTOM))));
                                } else {
                                    return getRandomCellWithSpecificOpeningDirections(unwanted, wanted);
                                }
                            }
                        }
                    }
                }
            }
            case 2 -> { // tow wanted opening directions
                /**
                 * Hier habe ich die Potenzmenge der Menge { TOP, RIGHT, BOTTOM, LEFT }
                 * ohne Duplikate gebildet, um alle Fälle zu berücksichtigen.
                 *
                 * p.s. Die Reihenfolge der Tupels werden ignoriert, da den gleichen Fall doppelt zu überprüfen ist zu vermeiden.
                 *
                 * Die zu berücksichtigende Fälle sind : {
                 *                      TOP    x RIGHT
                 *                      TOP    x LEFT
                 *                      TOp    x BOTTOM
                 *                      BOTTOM x LEFT
                 *                      BOTTOM x RIGHT
                 *                      LEFT   x RIGHT
                 *                  }
                 *
                 */
                ArrayList<OpeningDirection> wantedList = getListOfEnumValues(wanted);
                if (unwanted.size() == 2) {
                    return getRandomCellWith2UnwantedAnd2WantedOpeningDirections(unwanted, wantedList);
                }
                if (wantedList.contains(TOP) && wantedList.contains(RIGHT)) {
                    int range02 = Helper.generateRandomNo(1, 2);
                    if (Helper.validCollection(unwanted)) { // unwanted opening directions are available
                        if (unwanted.contains(BOTTOM) && !unwanted.contains(LEFT)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, RIGHT))));
                                }
                            }
                        } else if (unwanted.contains(LEFT) && !unwanted.contains(BOTTOM)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, RIGHT))));
                                }
                            }
                        }
                    } else { // unwanted ist egal (T & Curve pipes are allowed)
                        switch (range02) { // select a pipe type randomly
                            case 1 -> { // Curve pipe
                                return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                            }
                            case 2 -> { // T- pipe
                                int range = Helper.generateRandomNo(1, 2);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, BOTTOM))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, LEFT))));
                                }
                            }
                        }
                    }
                }
                if (wantedList.contains(TOP) && wantedList.contains(LEFT)) {
                    int range02 = Helper.generateRandomNo(1, 2);
                    if (Helper.validCollection(unwanted)) { // unwanted opening directions are available
                        if (unwanted.contains(BOTTOM) && !unwanted.contains(RIGHT)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, RIGHT))));
                                }
                            }
                        } else if (unwanted.contains(RIGHT) && !unwanted.contains(BOTTOM)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, LEFT))));
                                }
                            }
                        }
                    } else { // unwanted ist egal (T & Curve pipes are allowed)
                        switch (range02) { // select a pipe type randomly
                            case 1 -> { // Curve pipe
                                return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                            }
                            case 2 -> { // T- pipe
                                int range = Helper.generateRandomNo(1, 2);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT, BOTTOM))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, LEFT))));
                                }
                            }
                        }
                    }
                }
                if (wantedList.contains(TOP) && wantedList.contains(BOTTOM)) {
                    int range02 = Helper.generateRandomNo(1, 2);
                    if (Helper.validCollection(unwanted)) {
                        if (unwanted.contains(RIGHT) && !unwanted.contains(LEFT)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Straight pipe
                                    return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, BOTTOM))));
                                }
                            }
                        } else if (unwanted.contains(LEFT) && !unwanted.contains(RIGHT)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Straight pipe
                                    return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, RIGHT))));
                                }
                            }

                        }
                    } else { // unwanted ist egal (T & Curve pipes are allowed)
                        switch (range02) { // select a pipe type randomly
                            case 1 -> { // Straight pipe
                                return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                            }
                            case 2 -> { // T- pipe
                                int range = Helper.generateRandomNo(1, 2);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, BOTTOM))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, BOTTOM))));
                                }
                            }
                        }
                    }
                }
                if (wantedList.contains(LEFT) && wantedList.contains(BOTTOM)) {
                    int range02 = Helper.generateRandomNo(1, 2);
                    if (Helper.validCollection(unwanted)) { // unwanted opening directions are available
                        if (unwanted.contains(RIGHT) && !unwanted.contains(TOP)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, BOTTOM))));
                                }
                            }
                        } else if (unwanted.contains(TOP) && !unwanted.contains(RIGHT)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT, RIGHT))));
                                }
                            }
                        }
                    } else { // unwanted ist egal (T & Curve pipes are allowed)
                        switch (range02) { // select a pipe type randomly
                            case 1 -> { // Curve pipe
                                return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                            }
                            case 2 -> { // T- pipe
                                int range = Helper.generateRandomNo(1, 2);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, BOTTOM))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT, BOTTOM))));
                                }
                            }
                        }
                    }
                }
                if (wantedList.contains(RIGHT) && wantedList.contains(BOTTOM)) {
                    int range02 = Helper.generateRandomNo(1, 2);
                    if (Helper.validCollection(unwanted)) { // unwanted opening directions are available
                        if (unwanted.contains(TOP) && !unwanted.contains(LEFT)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM))));
                                }
                            }

                        } else if (unwanted.contains(LEFT) && !unwanted.contains(TOP)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Curve pipe
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, TOP, RIGHT))));
                                }
                            }

                        }
                    } else { // unwanted ist egal (T & Curve pipes are allowed)

                        switch (range02) { // select a pipe type randomly
                            case 1 -> { // Curve pipe
                                return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                            }
                            case 2 -> { // T- pipe
                                int range = Helper.generateRandomNo(1, 2);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, BOTTOM))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT, BOTTOM))));
                                }
                            }
                        }
                    }
                }
                if (wantedList.contains(RIGHT) && wantedList.contains(LEFT)) {
                    int range02 = Helper.generateRandomNo(1, 2);
                    if (Helper.validCollection(unwanted)) { // unwanted opening directions are available
                        if (unwanted.contains(TOP) && !unwanted.contains(BOTTOM)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Straight pipe
                                    return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM))));
                                }
                            }
                        } else if (unwanted.contains(BOTTOM) && !unwanted.contains(TOP)) {
                            switch (range02) { // select a pipe type randomly
                                case 1 -> { // Straight pipe
                                    return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT))));
                                }
                                case 2 -> { // T- pipe
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, TOP, RIGHT))));
                                }
                            }
                        }
                    } else { // unwanted ist egal (T & Curve pipes are allowed)
                        switch (range02) { // select a pipe type randomly
                            case 1 -> { // Straight pipe
                                return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, LEFT))));
                            }
                            case 2 -> { // T- pipe
                                int range = Helper.generateRandomNo(1, 2);
                                if (range == 1) {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT, BOTTOM))));
                                } else {
                                    return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, TOP, LEFT))));
                                }
                            }
                        }
                    }
                }
            }
            case 3 -> { // just T-Pipe is allowed
                return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(wanted))));
            }

        }

        return null;
    }

    /**
     * returns a cell randomly, in the scenario, that the cell that call this method has 2 unwanted and 3 wanted opening directions.
     *
     * @param unwanted list of unwanted opening directions
     * @param wanted list of wanted opening directions
     * @return a cell randomly, in the scenario, that the cell that call this method has 2 unwanted and 3 wanted opening directions.
     */
    private static Cell getRandomCellWith2UnwantedAnd2WantedOpeningDirections(ArrayList<OpeningDirection> unwanted, ArrayList<OpeningDirection> wanted) {
        assert unwanted.size() == 2;
        assert wanted.size() == 2;

        if ((wanted.contains(TOP) && wanted.contains(BOTTOM)) || (wanted.contains(RIGHT) && wanted.contains(LEFT))) {
            return new StraightPipe(EnumSet.copyOf(wanted));
        } else {
            return new CurvePipe(EnumSet.copyOf(wanted));
        }
    }

    /**
     * creates a cell that has 2 known unwanted opening directions.
     *
     * @param unwanted list of unwanted opening directions
     * @return a cell that has 2 known unwanted opening directions.
     */
    static Cell getRandomCellWithUnwantedOpeningDirections(OpeningDirection... unwanted) {
        assert unwanted.length <= 2;

        ArrayList<OpeningDirection> unwantedList = getListOfEnumValues(unwanted);

        switch (unwantedList.size()) {
            case 1 -> {
                if (unwantedList.contains(TOP)) {
                    int randomNo13 = Helper.generateRandomNo(1, 3);
                    switch (randomNo13) {
                        case 1 -> {
                            return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                        }
                        case 2 -> {
                            int randomNo12 = Helper.generateRandomNo(1, 2);
                            switch (randomNo12) {
                                case 1 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                                }
                                case 2 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));
                                }
                            }
                        }
                        case 3 -> {
                            return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT, LEFT))));
                        }
                    }
                } else if (unwantedList.contains(RIGHT)) {
                    int randomNo13 = Helper.generateRandomNo(1, 3);
                    switch (randomNo13) {
                        case 1 -> {
                            return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                        }
                        case 2 -> {
                            int randomNo12 = Helper.generateRandomNo(1, 2);
                            switch (randomNo12) {
                                case 1 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                }
                                case 2 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));
                                }
                            }
                        }
                        case 3 -> {
                            return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, LEFT))));
                        }
                    }
                } else if (unwantedList.contains(BOTTOM)) {
                    int randomNo13 = Helper.generateRandomNo(1, 3);
                    switch (randomNo13) {
                        case 1 -> {
                            return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                        }
                        case 2 -> {
                            int randomNo12 = Helper.generateRandomNo(1, 2);
                            switch (randomNo12) {
                                case 1 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                }
                                case 2 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                                }
                            }
                        }
                        case 3 -> {
                            return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, LEFT))));
                        }
                    }
                } else { // LEFT
                    int randomNo13 = Helper.generateRandomNo(1, 3);
                    switch (randomNo13) {
                        case 1 -> {
                            return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                        }
                        case 2 -> {
                            int randomNo12 = Helper.generateRandomNo(1, 2);
                            switch (randomNo12) {
                                case 1 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                                }
                                case 2 -> {
                                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                                }

                            }
                        }
                        case 3 -> {
                            return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, RIGHT))));
                        }


                    }
                }
            }
            case 2 -> {
                if (unwantedList.contains(TOP) && unwantedList.contains(RIGHT)) {
                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, BOTTOM))));
                }
                if (unwantedList.contains(TOP) && unwantedList.contains(BOTTOM)) {
                    return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                }
                if (unwantedList.contains(TOP) && unwantedList.contains(LEFT)) {
                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                }
                if (unwantedList.contains(RIGHT) && unwantedList.contains(BOTTOM)) {
                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                }
                if (unwantedList.contains(RIGHT) && unwantedList.contains(LEFT)) {
                    return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                }
                if (unwantedList.contains(BOTTOM) && unwantedList.contains(LEFT)) {
                    return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                }

            }
        }
        return null;
    }


    /**
     * create a cell with random opening direction. there is no specific wanted or unwanted opening directions.
     *
     * @return random cell with random opening directions
     */
    static Cell getRandomCellWithRandomOpeningDirections() {
        int randomNo13 = Helper.generateRandomNo(1, 3);

        switch (randomNo13) {
            case 1 -> {
                int randomNo12 = Helper.generateRandomNo(1, 2);
                switch (randomNo12) {
                    case 1 -> {
                        return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(LEFT, RIGHT))));
                    }
                    case 2 -> {
                        return new StraightPipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM))));
                    }
                }
            }
            case 2 -> {
                int randomNo14 = Helper.generateRandomNo(1, 4);
                switch (randomNo14) {
                    case 1 -> {
                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT))));
                    }
                    case 2 -> {
                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, LEFT))));
                    }
                    case 3 -> {
                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(RIGHT, BOTTOM))));
                    }
                    case 4 -> {
                        return new CurvePipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, LEFT))));
                    }
                }
            }
            case 3 -> {
                int randomNo14 = Helper.generateRandomNo(1, 4);
                switch (randomNo14) {
                    case 1 -> {
                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, LEFT))));
                    }
                    case 2 -> {
                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, BOTTOM, RIGHT))));
                    }
                    case 3 -> {
                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(TOP, RIGHT, LEFT))));
                    }
                    case 4 -> {
                        return new T_Pipe(EnumSet.copyOf(new ArrayList<>(List.of(BOTTOM, RIGHT, LEFT))));
                    }
                }
            }
        }

        return null;
    }

    /**
     * converts all nulls in the board to walls
     * @param board board that have walls instead of nulls.
     */
    static void convertNullsToWalls(Cell[][] board) {
        // convert Nulls To Walls
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new Wall();
                }
            }
        }
    }


    /**
     * converts the given array to a list.
     *
     * @param wanted array of wanted opening direction
     * @return list of given wanted opening direction
     */
    private static ArrayList<OpeningDirection> getListOfEnumValues(OpeningDirection... wanted) {
        ArrayList<OpeningDirection> list = new ArrayList<>();
        for (OpeningDirection od : wanted) {
            list.add(od);
        }

        return list;
    }
}
