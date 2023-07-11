package gui;

import javafx.animation.*;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import logic.helpers.GUIConnector;
import logic.game.Game;
import logic.cells.Cell;
import logic.cells.*;
import logic.helpers.Helper;
import logic.helpers.Position;
import logic.helpers.Rotation;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class is responsible for changing the gui when the logic deems it
 * necessary. Created by the gui and then passed as a parameter into the logic.
 *
 * @author Abdulrahman Al Bittar
 */
public class JavaFxGUI implements GUIConnector {


    /**
     * ################### FXML GUI Components ###################
     */

    @FXML
    private  RadioMenuItem radioMenuItemEditorMode;
    @FXML
    private final Button btnSaveEditorMode;
    @FXML
    private final Slider sliderNoOfRows;
    @FXML
    private final Slider sliderNoOfColumns;
    @FXML
    private final CheckBox chkBoxOverflow;
    @FXML
    private final Button btnNewGame;
    @FXML
    private final Button btnGameLoad;
    @FXML
    private final Button btnExitGame;
    @FXML
    private final MenuBar mnuSettings;
    @FXML
    private final AnchorPane anckorBtnsAtStart;
    @FXML
    private final HBox hBOxBoardSetting;
    @FXML
    private final GridPane grdPnStartGame;
    @FXML
    private final HBox hBoxEditorModus;
    @FXML
    private final ImageView editorModusImageCurvePipe;
    @FXML
    private final ImageView editorModusImageStraightPipe;
    @FXML
    private final ImageView editorModusImageT_Pipe;
    @FXML
    private final ImageView editorModusImageEndPipe;
    @FXML
    private final ImageView editorModusImageWall;
    @FXML
    private final ImageView editorModusImageWaterSource;
    @FXML
    private final Button btnDeleteBoardEditorMode;
    @FXML
    private final Slider editorModeSliderNoOfRows;
    @FXML
    private final Slider editorModeSliderNoOfColumns;
    @FXML
    private final CheckBox editorModeChkBoxOverflow;
    @FXML
    private final Button btnApplyNewEditorSettings;

    @FXML
    private final Slider sliderPercentOfWalls;
    @FXML
    private final Button btnMixBoardEditorMode;
    @FXML
    private final Slider sliderAnimationSpeed;
    @FXML
    private final BorderPane mainBorderPane;
    @FXML
    private final HBox hBoxResult;
    @FXML
    private final Label lblResult;

    /**
     * Grid pane to represents the whole view. It is the root component of the GUI.
     */
    @FXML
    private final GridPane gridpane;

    /**
     * Constructor that gets all GUI Components from the Controller to be able to be manipulated also hier.
     */
    public JavaFxGUI(
        GridPane gridpane,
        Button btnSaveEditorMode,
        Slider sliderNoOfRows,
        Slider sliderNoOfColumns,
        CheckBox chkBoxOverflow,
        Button btnNewGame,
        Button btnGameLoad,
        Button btnExitGame,
        MenuBar mnuSettings,
        AnchorPane anckorBtnsAtStart,
        HBox hBOxBoardSetting,
        GridPane grdPnStartGame,
        HBox hBoxEditorModus,
        ImageView editorModusImageCurvePipe,
        ImageView editorModusImageStraightPipe,
        ImageView editorModusImageT_Pipe,
        ImageView editorModusImageEndPipe,
        ImageView editorModusImageWall,
        ImageView editorModusImageWaterSource,
        Button btnDeleteBoardEditorMode,
        Slider editorModeSliderNoOfRows,
        Slider editorModeSliderNoOfColumns,
        CheckBox editorModeChkBoxOverflow,
        Button btnApplyNewEditorSettings,
        Slider sliderPercentOfWalls,
        Button btnMixBoardEditorMode,
        Slider sliderAnimationSpeed,
        BorderPane mainBorderPane,
        HBox hBoxResult,
        Label lblResult
    ) {
        this.gridpane = gridpane;
        this.btnSaveEditorMode = btnSaveEditorMode;
        this.sliderNoOfRows = sliderNoOfRows;
        this.sliderNoOfColumns = sliderNoOfColumns;
        this.chkBoxOverflow = chkBoxOverflow;
        this.btnNewGame = btnNewGame;
        this.btnGameLoad = btnGameLoad;
        this.btnExitGame = btnExitGame;
        this.mnuSettings = mnuSettings;
        this.anckorBtnsAtStart = anckorBtnsAtStart;
        this.hBOxBoardSetting = hBOxBoardSetting;
        this.grdPnStartGame = grdPnStartGame;
        this.hBoxEditorModus = hBoxEditorModus;
        this.editorModusImageCurvePipe = editorModusImageCurvePipe;
        this.editorModusImageStraightPipe = editorModusImageStraightPipe;
        this.editorModusImageT_Pipe = editorModusImageT_Pipe;
        this.editorModusImageEndPipe = editorModusImageEndPipe;
        this.editorModusImageWall = editorModusImageWall;
        this.editorModusImageWaterSource = editorModusImageWaterSource;
        this.btnDeleteBoardEditorMode = btnDeleteBoardEditorMode;
        this.editorModeSliderNoOfRows = editorModeSliderNoOfRows;
        this.editorModeSliderNoOfColumns = editorModeSliderNoOfColumns;
        this.editorModeChkBoxOverflow = editorModeChkBoxOverflow;
        this.btnApplyNewEditorSettings = btnApplyNewEditorSettings;
        this.sliderPercentOfWalls = sliderPercentOfWalls;
        this.btnMixBoardEditorMode = btnMixBoardEditorMode;
        this.sliderAnimationSpeed = sliderAnimationSpeed;
        this.mainBorderPane = mainBorderPane;
        this.hBoxResult = hBoxResult;
        this.lblResult = lblResult;
    }


    /**
     * Referent to the logic.
     */
    private static Game game;

    /**
     * The water source animation, to animate the cell that represents the water source
     */
    private final Animation waterSourceAnimation = new Timeline();

    /**
     * the 2d-array of type ImageView to represents the game board on the GUI view.
     */
    private static ImageView[][] imgViewsPlayingField;

    /**
     * Pictures for All cells ( pipes, wall and water source) in 2 colors for the pipes.
     */
    private static final Image[] IMAGES = new Image[]
            {
                    new Image("img/curve_blue.png"),       // 0
                    new Image("img/curve_red.png"),        // 1

                    new Image("img/straight_blue.png"),    // 2
                    new Image("img/straight_red.png"),     // 3

                    new Image("img/t_blue.png"),           // 4
                    new Image("img/t_red.png"),            // 5

                    new Image("img/end_blue.png"),         // 6
                    new Image("img/end_red.png"),          // 7

                    new Image("img/wall.png"),             // 8
                    new Image("img/source.png"),           // 9

            };


    /**
     * Duration of the water filling animation. The user can set up it from the view.
     */
    private static Duration animationDuration;

    /**
     * boolean value to determinate if the editor mode is activated or not.
     */
    private static boolean isEditorMode = false;


    /**
     * getter for all images in the game.
     *
     * @return an array of all images in the game.
     */
    public static Image[] getImages() {
        return IMAGES;
    }

    /**
     * creates a new game using the given parameters.
     *
     * @param gui            GUI
     * @param noOfRows       number of board rows
     * @param noOfCols       number of board columns
     * @param overflow       overflow mode
     * @param percentOfWalls percentage of allowed walls
     */
    @Override
    public void createNewGame(GUIConnector gui, int noOfRows, int noOfCols, boolean overflow, double percentOfWalls) {
        game = new Game(gui, noOfRows, noOfCols, overflow, percentOfWalls);
    }


    /**
     * initializes the playing field with images and adds event handler for the rotation.
     *
     * @param imgViewsPlayerField Image view, that represents the playing field in the GUI.
     */
    public void initializePlayingField(ImageView[][] imgViewsPlayerField) {
        assert imgViewsPlayerField != null;
        assert game.getBoard() != null;
        assert imgViewsPlayerField.length == game.getBoard().length;
        assert imgViewsPlayerField[0].length == game.getBoard()[0].length;

        JavaFxGUI.imgViewsPlayingField = imgViewsPlayerField;
        Position posOfWaterSource = initializeWaterSourcePosition();
        Cell[][] board = game.getBoard();
        Helper.printBoard(board);
        initializeBoardImages(posOfWaterSource, board);

    }

    /**
     * iterates the board and sets the corresponding image at the corresponding position.
     *
     * @param posOfWaterSource  Position of  Water Source
     * @param board playing board
     */
    private void initializeBoardImages(Position posOfWaterSource, Cell[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Position currentPosition = new Position(i, j);
                String cell = board[i][j].getOpeningBinaryValue();
                ImageView iv = JavaFxGUI.imgViewsPlayingField[j][i];
                boolean isConnected = game.isConnectedToWaterSource(currentPosition, posOfWaterSource, board, game.isOverflow());
                setSuitableImage(iv, cell, isConnected, false);
                addEventHandlerForRotation(iv, currentPosition);

            }
        }
    }

    /**
     * initialize Water Source Position
     *
     * @return Position of initialized Water Source
     */
    private Position initializeWaterSourcePosition() {
        Position posOfWaterSource = game.getSource();
        // wenn Zellen weg sind bei Verkürzung der Zeilen- bzw. Spaltenanzahl im Editormodus
        if (posOfWaterSource != null && (posOfWaterSource.getX() >= game.getBoard().length
            || posOfWaterSource.getY() >= game.getBoard()[0].length)) {
            resetWaterSource();
        }
        posOfWaterSource = game.getSource();
        if (posOfWaterSource != null) {
            // if there is a source, animate that please.
            Position reversPosOfWaterSource = Position.reverse(posOfWaterSource);
            ImageView waterSource = JavaFxGUI.imgViewsPlayingField[reversPosOfWaterSource.getX()][reversPosOfWaterSource.getY()];
            setEffectToWaterSource(waterSource);
        }
        return posOfWaterSource;
    }

    /**
     * adds an event handler to the image view, to rotate it on mouse click.
     *
     * @param iv              image view
     * @param currentPosition position, where the image view was clicked.
     */
    public void addEventHandlerForRotation(ImageView iv, Position currentPosition) {
        iv.addEventHandler(MouseEvent.MOUSE_CLICKED, rotateAndAnimate(currentPosition));
    }

    private EventHandler<MouseEvent> rotateAndAnimate(Position currentPosition) {
        return mouseEvent -> {
            handleRotationOnMouseClick(mouseEvent, currentPosition);
            animatePipeFilling();
        };
    }

    /**
     * handles the rotation process.
     *
     * @param mouseEvent      the mouse event
     * @param currentPosition position, where the image view was clicked.
     */
    public void handleRotationOnMouseClick(MouseEvent mouseEvent, Position currentPosition) {

        if (mouseEvent.getButton() == MouseButton.PRIMARY) { // left click
            rotate(Rotation.LEFT, currentPosition, imgViewsPlayingField);
            System.out.println("Mouse left-clicked at board[" + currentPosition.getX() + " , " + currentPosition.getY() + "]");

            Helper.printBoard(game.getBoard());
            updateResultLabelHandler();


        }
        if (mouseEvent.getButton() == MouseButton.SECONDARY) { // right click
            rotate(Rotation.RIGHT, currentPosition, imgViewsPlayingField);
            System.out.println("Mouse right-clicked at board[" + currentPosition.getX() + " , " + currentPosition.getY() + "]");
            Helper.printBoard(game.getBoard());
            updateResultLabelHandler();

        }


        checkIfBoardIsSolved();

    }

    /**
     * checks if the board is solved to display a winner dialog at game mode and informational dialog at editor mode.
     */
    public void checkIfBoardIsSolved() {
        if (game.isGameFinished()) {
            // if editor mode
            if (isEditorMode) {
                GUIAlert.showBoardSolvedDialog();
            } else { // game mode
                GUIAlert.showWinDialog(game.getNoOfClicks());
            }
        }
    }

    /**
     * Helper Method, to handle rotation in a correct sequence.
     *
     * @param rotation             rotation direction.
     * @param currentPosition      current position of clicked image view.
     * @param imgViewsPlayingField the GUI board.
     */
    private void rotate(Rotation rotation, Position currentPosition, ImageView[][] imgViewsPlayingField) {
        Position reverse = Position.reverse(currentPosition);
        int i = reverse.getX();
        int j = reverse.getY();
        double actualRotationDegree = imgViewsPlayingField[i][j].getRotate();

        if (rotation == Rotation.LEFT) {
            rotate(rotation, currentPosition, imgViewsPlayingField[i], j, actualRotationDegree - 90);
        }
        if (rotation == Rotation.RIGHT) {
            rotate(rotation, currentPosition, imgViewsPlayingField[i], j, actualRotationDegree + 90);
        }
    }

    private void rotate(Rotation rotation,
        Position currentPosition,
        ImageView[] imgViewsPlayingField,
        int j,
        double actualRotationDegree) {
        this.rotateInBackend(rotation, currentPosition);
        imgViewsPlayingField[j].setRotate(actualRotationDegree);
        incrementWinClicks();
        Helper.printBoard(game.getBoard());
    }

    /**
     * restarts the game (the whole application).
     *
     * @throws IOException
     */
    public static void restartGame() throws IOException {
        Parent root;
        try {
            // Parsing the GUI
            FXMLLoader fxmlLoader = new FXMLLoader(ApplicationMain.class.getResource("UserInterface.fxml"));
            root = fxmlLoader.load();
        } catch (IOException e) {
            GUIAlert.displayException(ExceptionCode.E12);
            throw new IOException(e.getMessage());
        }

        // closing current stage
        Stage currentStage = ApplicationMain.getPrimaryStage();
        currentStage.close();

        // opening a new stage
        Scene scene = new Scene(root, 1200, 900);
        Stage newStage = new Stage();

        ApplicationMain.setPrimaryStage(newStage);

        // Stage display settings
        newStage.setResizable(true);
        newStage.setMinHeight(800);
        newStage.setMinWidth(800);
        newStage.setTitle("Flood Pipe Game");
        Image icon = new Image("img/icon.png");
        newStage.getIcons().add(icon);
        newStage.setScene(scene);

        // resetting the clicks counter
        game.setNoOfClicks(0);

        // showing the programm window.
        newStage.show();
    }

    /**
     * updates the result ( number of clicks)
     */
    private void updateResultLabelHandler() {
        lblResult.setText(Constants.LBL_RESULT_STR + "(" + game.getNoOfClicks() + ")");
    }

    /**
     * updates the  imageview to show, which pipes are either connected or disconnected
     *
     * @param isRotated must the image view be rotated or not.
     */
    public void updateBoardOnTheGUI(boolean isRotated) {
        Cell[][] board = game.getBoard();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Position currentPosition = new Position(i, j);
                String cell = board[i][j].getOpeningBinaryValue();
                ImageView iv = imgViewsPlayingField[j][i];
                boolean isConnected = game.isConnectedToWaterSource(currentPosition, game.getSource(), board, game.isOverflow());
                setSuitableImage(iv, cell, isConnected, isRotated);
            }
        }
    }


    /**
     * sets the corresponding image to the given image view.
     *
     * @param iv          the given image view
     * @param cell        the board cell represented as binary number.
     * @param isConnected is the pipe connected or not.
     * @param isRotated   must the image view be rotated or not.
     */
    private void setSuitableImage(ImageView iv, String cell, boolean isConnected, boolean isRotated) {
        switch (cell) {
            case "00001010" -> {                         // ━
                if (isConnected) {
                    iv.setImage(IMAGES[2]);
                } else {
                    iv.setImage(IMAGES[3]);
                }
            }
            case "00000101" -> {                         // ┃
                if (isConnected) {
                    iv.setImage(IMAGES[2]);
                } else {
                    iv.setImage(IMAGES[3]);
                }
                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 90);
                }
            }
            case "00001101" -> {                        // ┫
                if (isConnected) {
                    iv.setImage(IMAGES[4]);
                } else {
                    iv.setImage(IMAGES[5]);
                }
            }
            case "00000111" -> {                       // ┣
                if (isConnected) {
                    iv.setImage(IMAGES[4]);
                } else {
                    iv.setImage(IMAGES[5]);
                }
                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 180);
                }
            }
            case "00001011" -> {                      // ┻
                if (isConnected) {
                    iv.setImage(IMAGES[4]);
                } else {
                    iv.setImage(IMAGES[5]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 90);
                }
            }
            case "00001110" -> {                     // ┳
                if (isConnected) {
                    iv.setImage(IMAGES[4]);
                } else {
                    iv.setImage(IMAGES[5]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() - 90);
                }

            }
            case "00000011" -> {                    // ┗
                if (isConnected) {
                    iv.setImage(IMAGES[0]);
                } else {
                    iv.setImage(IMAGES[1]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 90);

                }
            }
            case "00001001" -> {                    // ┛
                if (isConnected) {
                    iv.setImage(IMAGES[0]);
                } else {
                    iv.setImage(IMAGES[1]);
                }
            }
            case "00000110" -> {                   // ┏
                if (isConnected) {
                    iv.setImage(IMAGES[0]);
                } else {
                    iv.setImage(IMAGES[1]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 180);
                }

            }
            case "00001100" -> {                   // ┓
                if (isConnected) {
                    iv.setImage(IMAGES[0]);
                } else {
                    iv.setImage(IMAGES[1]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() - 90);
                }

            }
            case "00000001" -> {                   // ╽ && ╹
                if (isConnected) {
                    iv.setImage(IMAGES[6]);
                } else {
                    iv.setImage(IMAGES[7]);
                }

            }
            case "00000010" -> {                  // ╾ && ╺
                if (isConnected) {
                    iv.setImage(IMAGES[6]);
                } else {
                    iv.setImage(IMAGES[7]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 90);

                }

            }
            case "00000100" -> {                  // ╿ && ╻
                if (isConnected) {
                    iv.setImage(IMAGES[6]);
                } else {
                    iv.setImage(IMAGES[7]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() + 180);
                }

            }
            case "00001000" -> {                  // ╼ && ╸
                if (isConnected) {
                    iv.setImage(IMAGES[6]);
                } else {
                    iv.setImage(IMAGES[7]);
                }

                if (!isRotated) {
                    iv.setRotate(iv.getRotate() - 90);
                }

            }
            case "00000000" -> {
                iv.setImage(IMAGES[8]);
            }

        }
    }

    /**
     * set an effect to the given image view, that has the water source.
     *
     * @param imageView the image view, that has the water source.
     */
    public void setEffectToWaterSource(ImageView imageView) {
        assert imageView != null;

        //Create an inner shadow effect (Border)
        InnerShadow innerShadow = new InnerShadow();
        innerShadow.setRadius(15);
        innerShadow.setColor(Color.BLUE);

        imageView.setEffect(innerShadow);
    }


    /**
     * determinate, that there is a water source.
     *
     * @return true, if there is a water source, else false.
     */
    public boolean isThereWaterSource() {
        return game != null && game.getSource() != null;
    }


    /**
     * increments the counter of rotation clicks
     */
    public void incrementWinClicks() {
        game.setNoOfClicks(game.getNoOfClicks() + 1);
    }


    /**
     * initialize the board at editor mode.
     *
     * @param imgViewsPlayingField the GUI board.
     */
    public void initializePlayingFieldForEditorMode(ImageView[][] imgViewsPlayingField) {
        resetWaterAnimation(game.getSource());
        game.resetBoard();
        for (ImageView[] imageViews : imgViewsPlayingField) {
            for (int j = 0; j < imageViews.length; j++) {
                imageViews[j].setImage(IMAGES[8]); // Walls
            }
        }
    }


    /**
     * rests the water source animation, if the deleted at editor mode
     *
     * @param source position of water source.
     */
    public void resetWaterAnimation(Position source) {
        waterSourceAnimation.stop();
        ImageView waterSource = imgViewsPlayingField[source.getY()][source.getX()];
        waterSource.setEffect(null);
    }


    /**
     * Animates the pipe water filling.
     */
    private void animatePipeFilling() {
        // Get the list of pipes that need to be filled
        List<Position> pipesToFill = game.getSortedConnectedPipes(game.getSource(), game.getBoard(), game.isOverflow());

        // If there are no pipes to fill, immediately update the board on the GUI
        if (pipesToFill.isEmpty()) {
            updateBoardOnTheGUI(true);
        } else { // there is pipes to animate...
            /**
             * Why AtomicInteger?
             *
             * An AtomicInteger is used instead of a normal int in this code because the AtomicInteger class provides
             * a convenient way of performing atomic operations on a single integer value, in which multiple
             * threads can access and update the value concurrently, without the need for locks or other synchronization mechanisms.
             *
             * Since in this method, there is a possibility of multiple threads accessing and updating the
             * remainingPipes count, an AtomicInteger is used to ensure that the count remains correct,
             * even in a multi-threaded environment.
             */
            // Track the number of remaining pipes to fill
            AtomicInteger remainingPipes = new AtomicInteger(pipesToFill.size());

            // Create a timeline for the animation
            Timeline fillTimeline = new Timeline();

            // Add a keyframe to the timeline that updates the GUI
            fillTimeline.getKeyFrames().add(new KeyFrame(animationDuration, event -> {
                if (!pipesToFill.isEmpty()) {
                    // Get the next pipe to fill
                    Position nextPipe = pipesToFill.remove(0);
                    Position reverse = Position.reverse(nextPipe);
                    ImageView pipeImageView = imgViewsPlayingField[reverse.getX()][reverse.getY()];

                    // Get the binary value of the opening directions of the next pipe
                    String cell = game.getBoard()[nextPipe.getX()][nextPipe.getY()].getOpeningBinaryValue();
                    // Set the suitable image for the pipe
                    setSuitableImage(pipeImageView, cell, true, true);

                    // Decrement the number of remaining pipes
                    remainingPipes.getAndDecrement();

                    // If there are no more remaining pipes, stop the animation and update the board on the GUI
                    if (remainingPipes.get() == 0) {
                        fillTimeline.stop();
                        updateBoardOnTheGUI(true);
                    }
                }
            }));
            fillTimeline.setCycleCount(pipesToFill.size());
            fillTimeline.play();
        }
    }


    /**
     * Animates the result label to make it prettier.
     *
     * @param lblResult the label, that displays the result.
     */
    public void animateResultLabel(Label lblResult) {

        // create a new scale transition that scales the label up and down
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(1), lblResult);
        scaleTransition.setFromX(1);
        scaleTransition.setFromY(1);
        scaleTransition.setToX(1.2);
        scaleTransition.setToY(1.2);
        scaleTransition.setAutoReverse(true);
        scaleTransition.setCycleCount(Animation.INDEFINITE);

        // create a new fade transition object, which takes the label and a duration as parameters.
        // It then sets the start and end values for the animation, so that the label will fade in.
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), lblResult);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        // play both transitions at the same time
        scaleTransition.play();
        fadeTransition.play();
    }


    /**
     * resets the rotation of the given 2d array of ImageView. Is used when the user deletes the current board at editor mode.
     */
    private void resetImageviewRotation() {
        for (ImageView[] imageViews : imgViewsPlayingField) {
            for (int j = 0; j < imgViewsPlayingField[0].length; j++) {
                ImageView iv = imageViews[j];
                iv.setRotate(0);

            }
        }
    }

    /**
     * set the Animation duration, that the user want.
     *
     * @param animationDuration animation duration.
     */
    public void setAnimationDuration(Duration animationDuration) {
        JavaFxGUI.animationDuration = animationDuration;
    }

    /**
     * get the current game instance.
     *
     * @return the current game instance.
     */
    public Game getGame() {
        return game;
    }


    /**
     * creates a new game after game loading
     *
     * @param game the new game, that should be created
     * @param imgViewsPlayingField the GUI board
     */
    public void loadGame(Game game, ImageView[][] imgViewsPlayingField) {
        JavaFxGUI.game = game;
        initializePlayingField(imgViewsPlayingField);
    }

    /**
     * de- and activates the editor mode.
     *
     * @param bool boolean value to de- or activates the editor mode.
     */
    public void setIsEditorMode(boolean bool) {
        isEditorMode = bool;
    }


    @Override
    public void changeSizeOfBoard(int rowCount, int colCount) {
        Cell[][] currentBoard = game.getBoard();
        Helper.printBoard(currentBoard);
        Cell[][] newBoard = new Cell[rowCount][colCount];
        int row = Math.min(rowCount, currentBoard.length);
        int col = Math.min(colCount, currentBoard[0].length);

        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                Cell cell = currentBoard[i][j];
                if (game.getPlayingField().isPipe(cell)) {
                    Pipe pipe = (Pipe) currentBoard[i][j];
                    if (game.getPlayingField().isStraightPipe(cell)) {
                        newBoard[i][j] = new StraightPipe(pipe);
                    }
                    if (game.getPlayingField().isCurvePipe(pipe)) {
                        newBoard[i][j] = new CurvePipe(pipe);
                    }
                    if (game.getPlayingField().isT_Pipe(pipe)) {
                        newBoard[i][j] = new T_Pipe(pipe);
                    }
                    if (game.getPlayingField().isEndPipe(pipe)) {
                        newBoard[i][j] = new EndPipe(pipe);
                    }
                } else if (game.getPlayingField().isWall(cell)) {
                    newBoard[i][j] = new Wall();
                }
            }
        }

        if (rowCount > currentBoard.length || colCount > currentBoard[0].length) {
            for (int i = 0; i < rowCount; i++) {
                for (int j = 0; j < colCount; j++) {
                    if (newBoard[i][j] == null) {
                        newBoard[i][j] = new Wall();
                    }
                }
            }
        }

        game.setBoard(newBoard);
        Helper.printBoard(newBoard);
    }

    @Override
    public void setNewOverflowMode(boolean newOverflow) {
        game.setOverflow(newOverflow);
    }

    @Override
    public void mixRandomlyCurrentBoard() {
        game.setBoard(game.getPlayingField().rotateBoardCellsRandomly(game.getBoard()));
        resetImageviewRotation();
        updateBoardOnTheGUI(false);

        System.out.println("Mixed Board: ");
        Helper.printBoard(game.getBoard());
    }

    @Override
    public void resetWaterSource() {
        game.resetWaterSource();
    }

    @Override
    public void rotateInBackend(Rotation rotation, Position currentPosition) {
        int i = currentPosition.getX();
        int j = currentPosition.getY();
        if (rotation == Rotation.LEFT) {
            rotateAtPosition(i, j, Rotation.LEFT);
        }
        if (rotation == Rotation.RIGHT) {
            rotateAtPosition(i, j, Rotation.RIGHT);
        }
    }

    private static void rotateAtPosition(int i, int j, Rotation direction) {
        Cell cell = game.getBoard()[i][j];
        if (cell instanceof Pipe) {
            Pipe newPipe = ((Pipe) cell).rotate(direction);
            game.setCellAtSpecificIndex(newPipe, new Position(i, j));

            Helper.printBoard(game.getBoard());
        }
    }

}
