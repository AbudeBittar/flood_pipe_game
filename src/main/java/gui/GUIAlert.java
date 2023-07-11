package gui;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.IOException;


/**
 * Class that represents the displaying of errors to the user, to be informed about each error if occurs.
 * <p>
 * it calls methods from another classes, depending on user reactions.
 *
 * @author Abdulrahman Al Bittar
 */
public class GUIAlert {

    private GUIAlert(){}

    /**
     * alert height to adjust the size for all error messages.
     */
    private static final double ALERT_HEIGHT = 350;


    /**
     * creates an error dialog to display a message for the user on the GUI.
     *
     * @param code the exception code, to map the corresponding message from the string constants class.
     * @param info additional information that should be passed tho the User (HERE ALWAYS PATH OF A FILE).
     *             it is passed with the 3 dots operator to be optional.
     */
    public static void displayException(ExceptionCode code, String... info) {
        // JAVAFX Alert
        Alert exceptionAlert = new Alert(Alert.AlertType.ERROR);
        exceptionAlert.setTitle(Constants.EXCEPTION_DIALOG_TITLE);

        ButtonType ok = new ButtonType(Constants.YES_DIALOG_BTN, ButtonBar.ButtonData.OK_DONE);
        exceptionAlert.getButtonTypes().setAll(ok);

        switch (code) {
            case E1 -> { // JsonSyntaxException
                exceptionAlert.setContentText(Constants.E1 + showErrorCode(code.getExceptionID()));
            }
            case E2 -> { // IllegalStateException
                exceptionAlert.setContentText(Constants.E2 + showErrorCode(code.getExceptionID()));
            }
            case E3 -> { // IOException
                exceptionAlert.setContentText(Constants.E3 + showErrorCode(code.getExceptionID()));
            }
            case E4 -> { // NoSuchFileException
                exceptionAlert.setContentText(Constants.E4 + " " + info[0] + showErrorCode(code.getExceptionID()));
            }
            case E5 -> { // Exception
                exceptionAlert.setContentText(Constants.E5 + showErrorCode(code.getExceptionID()));
            }
            case E6 -> { //IOException  Error while loading a new Game
                exceptionAlert.setContentText(Constants.E6 + showErrorCode(code.getExceptionID()));
            }
            case E7 -> { // An unexpected Button was clicked
                exceptionAlert.setContentText(Constants.E7 + showErrorCode(code.getExceptionID()));
            }
            case E8 -> { // An unexpected error on load game
                exceptionAlert.setContentText(Constants.E8 + showErrorCode(code.getExceptionID()));
            }
            case E9 -> { // An unexpected error on load game
                exceptionAlert.setContentText(Constants.E9 + showErrorCode(code.getExceptionID()));
            }
            case E10 -> { // JsonSyntaxException
                exceptionAlert.setContentText(Constants.E10 + " " + info[0] + showErrorCode(code.getExceptionID()));
            }
            case E11 -> { // JsonSyntaxException
                exceptionAlert.setContentText(Constants.E11 + showErrorCode(code.getExceptionID()));
            }
            case E12 -> { // JsonSyntaxException
                exceptionAlert.setContentText(Constants.E12 + showErrorCode(code.getExceptionID()));
            }
        }

        // Increase the height to view the entire message
        exceptionAlert.getDialogPane().setMinHeight(ALERT_HEIGHT);

        exceptionAlert.showAndWait().ifPresent((type -> {
            if (type == ok) {
                System.err.println(exceptionAlert.getContentText());
            } else {
                System.err.println("While showing the error an unexpected Button was clicked");
            }
        }));

        exceptionAlert.show();
    }

    /**
     * represents the exception code number as a string.
     *
     * @param codeNr the exception code number
     * @return string representation of the exception code number
     */
    private static String showErrorCode(int codeNr) {

        return Constants.ERROR_CODE + codeNr + ".";
    }


    /**
     * shows the user an informational dialog, that the board is solved, when he draws the board itself in the editor modus
     */
    public static void showBoardSolvedDialog() {
        Alert winAlert = new Alert(Alert.AlertType.INFORMATION, Constants.BOARD_IS_SOLVED);

        winAlert.setTitle(Constants.HINT_TITLE);

        ButtonType ok = new ButtonType("Ok, Danke", ButtonBar.ButtonData.OK_DONE);

        winAlert.getButtonTypes().setAll(ok);

        winAlert.showAndWait().ifPresent((type -> {
            if (type == ok) {
                System.out.println("Solved Board was drawn from user");
            } else {
                System.err.println("While creating a new game an unexpected Button was clicked");
                GUIAlert.displayException(ExceptionCode.E7);
            }
        }));
    }


    /**
     * shows the user an informational dialog, that he won the gam, and how many clicks he needed to win.
     * The User has 2 options, either starting a new game or quiting the game (whole application).
     */
    public static void showWinDialog(int clicks) {
        String msg = "Du hast " + clicks + " Klicks gebraucht, um zu gewinnen";
        Alert winAlert = new Alert(Alert.AlertType.INFORMATION, msg);

        winAlert.setTitle(Constants.WIN_MSG);

        ButtonType yes = new ButtonType(Constants.NEW_GAME_BTN_DIALOG, ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(Constants.QUIT_GAME_BTN_DIALOG, ButtonBar.ButtonData.NO);

        winAlert.getButtonTypes().setAll(yes, no);

        winAlert.showAndWait().ifPresent((type -> {
            if (type == yes) {
                System.out.println("new Game was asked by user");
                try {
                    JavaFxGUI.restartGame();
                } catch (IOException e) {
                    System.err.println("Error while creating a new Game: " + e.getMessage());
                    GUIAlert.displayException(ExceptionCode.E6);
                }
            } else if (type == no) {
                Platform.exit();
                System.out.println("new Game was asked by user");
            } else {
                System.err.println("While creating a new game an unexpected Button was clicked");
                GUIAlert.displayException(ExceptionCode.E7);
            }
        }));
    }


    /**
     * dialog to approve exiting the game
     */
    public static void showExitDialog() {

        Alert stateLosingWarning = new Alert(Alert.AlertType.WARNING, Constants.WARNING_MSG_FOR_LOSSING_DATA);
        stateLosingWarning.setTitle(Constants.ASK_TO_QUITING_THE_GAME);

        ButtonType yes = new ButtonType(Constants.YES_DIALOG_BTN, ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(Constants.NO_DIALOG_BTN, ButtonBar.ButtonData.NO);

        stateLosingWarning.getButtonTypes().setAll(yes, no);

        stateLosingWarning.showAndWait().ifPresent((type -> {
            if (type == yes) {
                System.out.println("Quitting the game was accepted from user ");
                Platform.exit();
            } else if (type == no) {
                System.out.println("Quitting the game was declined from user ");
            } else {
                System.err.println("While quitting the game an unexpected Button was clicked");
                GUIAlert.displayException(ExceptionCode.E7);
            }
        }));
    }

    /**
     * dialog to approve creating new game, while now there is a game (currently).
     */
    public static void showDialogOfCreatingNewGame() {
        Alert stateLosingWarning = new Alert(Alert.AlertType.WARNING, Constants.WARNING_MSG_FOR_LOSSING_DATA);
        stateLosingWarning.setTitle(Constants.ASK_TO_START_NEW_GAME);

        ButtonType yes = new ButtonType(Constants.YES_DIALOG_BTN, ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(Constants.NO_DIALOG_BTN, ButtonBar.ButtonData.NO);

        stateLosingWarning.getButtonTypes().setAll(yes, no);

        stateLosingWarning.showAndWait().ifPresent((type -> {
            if (type == yes) {
                System.out.println("Creating a new game was approved from user ");
                try {
                    JavaFxGUI.restartGame();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (type == no) {
                System.out.println("Creating a new game was declined from user ");
            } else {
                System.err.println("While quitting the game an unexpected Button was clicked");
                GUIAlert.displayException(ExceptionCode.E7);
            }
        }));

    }

}
