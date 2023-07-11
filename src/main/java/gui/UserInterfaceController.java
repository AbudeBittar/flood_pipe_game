package gui;

import com.google.gson.JsonSyntaxException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import logic.game.Game;
import logic.game.GameData;
import logic.cells.Cell;
import logic.cells.*;
import logic.helpers.Helper;
import logic.helpers.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.NoSuchFileException;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import static logic.cells.OpeningDirection.*;

/**
 * Main class for the user interface, that controls the view UserInterface.fxml
 *
 * @author Abdulrahman Al Bittar
 */
public class UserInterfaceController implements Initializable {

    /**
     * ################### FXML GUI Components ###################
     */
    @FXML
    private RadioMenuItem radioMenuItemEditorMode;
    @FXML
    private Button btnSaveEditorMode;
    @FXML
    private Slider sliderNoOfRows;
    @FXML
    private Slider sliderNoOfColumns;
    @FXML
    private CheckBox chkBoxOverflow;
    @FXML
    private Button btnNewGame;
    @FXML
    private Button btnGameLoad;
    @FXML
    private Button btnExitGame;
    @FXML
    private MenuBar mnuSettings;
    @FXML
    private AnchorPane anckorBtnsAtStart;
    @FXML
    private HBox hBOxBoardSetting;
    @FXML
    private GridPane grdPnStartGame;
    @FXML
    private HBox hBoxEditorModus;
    @FXML
    private ImageView editorModusImageCurvePipe;
    @FXML
    private ImageView editorModusImageStraightPipe;
    @FXML
    private ImageView editorModusImageT_Pipe;
    @FXML
    private ImageView editorModusImageEndPipe;
    @FXML
    private ImageView editorModusImageWall;
    @FXML
    private ImageView editorModusImageWaterSource;
    @FXML
    private Button btnDeleteBoardEditorMode;
    @FXML
    private Slider editorModeSliderNoOfRows;
    @FXML
    private Slider editorModeSliderNoOfColumns;
    @FXML
    private CheckBox editorModeChkBoxOverflow;
    @FXML
    private Button btnApplyNewEditorSettings;

    @FXML
    private Slider sliderPercentOfWalls;
    @FXML
    private Button btnMixBoardEditorMode;
    @FXML
    private Slider sliderAnimationSpeed;
    @FXML
    private BorderPane mainBorderPane;
    @FXML
    private HBox hBoxResult;
    @FXML
    private Label lblResult;


    /**
     * ################### Class Attributes ###################
     */


    /**
     * GUI reference
     */
    private static JavaFxGUI gui;


    /**
     * Main root in the GUI.
     */
    private static GridPane gridpane;


    /**
     * number of board rows
     */
    private static int noOfRows;

    /**
     * number of board columns
     */
    private static int noOfCols;

    /**
     * percentage of allowed walls
     */
    private static double percentOfWalls;

    /**
     * is overflow mode activated or not
     */
    private static boolean overflow;

    /**
     * The GUI board
     */
    private static ImageView[][] imgViewsPlayingField;


    /**
     * Game reference
     */
    private static Game game;


    /**
     * configures the things should be done while initializing.
     *
     * @param url            probably not used
     * @param resourceBundle probably not used
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gui = new JavaFxGUI(gridpane, btnSaveEditorMode, sliderNoOfRows, sliderNoOfColumns, chkBoxOverflow,
                btnNewGame, btnGameLoad, btnExitGame, mnuSettings, anckorBtnsAtStart, hBOxBoardSetting,
                grdPnStartGame, hBoxEditorModus, editorModusImageCurvePipe, editorModusImageStraightPipe,
                editorModusImageT_Pipe, editorModusImageEndPipe, editorModusImageWall,
                editorModusImageWaterSource, btnDeleteBoardEditorMode, editorModeSliderNoOfRows,
                editorModeSliderNoOfColumns, editorModeChkBoxOverflow, btnApplyNewEditorSettings,
                sliderPercentOfWalls, btnMixBoardEditorMode, sliderAnimationSpeed,
                mainBorderPane, hBoxResult, lblResult);

        initWidgets();
        initButtons();


    }

    /**
     * initializes the GUI widgets
     */
    private void initWidgets() {
        mnuSettings.setVisible(false);
        mnuSettings.managedProperty().bind(mnuSettings.visibleProperty());
        mnuSettings.setPrefSize(0.0, 0.0);

        hBoxEditorModus.setVisible(false);
        hBoxEditorModus.managedProperty().bind(hBoxEditorModus.visibleProperty());

        btnSaveEditorMode.setVisible(false);
        btnSaveEditorMode.managedProperty().bind(btnSaveEditorMode.visibleProperty());

        btnDeleteBoardEditorMode.setVisible(false);
        btnDeleteBoardEditorMode.managedProperty().bind(btnDeleteBoardEditorMode.visibleProperty());

        btnMixBoardEditorMode.setVisible(false);
        btnMixBoardEditorMode.managedProperty().bind(btnMixBoardEditorMode.visibleProperty());

        lblResult.setVisible(false);
        lblResult.managedProperty().bind(lblResult.visibleProperty());


        sliderAnimationSpeed.setTooltip(new Tooltip(Constants.ANIMATION_SPEED_SLIDER_TOOL_TIP));
    }


    /**
     * initializes the GUI buttons
     */
    private void initButtons() {
        btnNewGame.setTooltip(new Tooltip(Constants.NEW_GAME_BTN_TOOL_TIP));
        btnGameLoad.setTooltip(new Tooltip(Constants.LOAD_GAME_BTN_TOOL_TIP));
        btnExitGame.setTooltip(new Tooltip(Constants.EXIT_GAME_BTN_TOOL_TIP));

        btnSaveEditorMode.setTooltip(new Tooltip(Constants.SAVE_EDITED_BOARD_BTN_TOOL_TIP));
        btnDeleteBoardEditorMode.setTooltip(new Tooltip(Constants.DELETE_EDITOR_BOARD_BTN_TOOL_TIP));
        btnMixBoardEditorMode.setTooltip(new Tooltip(Constants.MIX_EDITOR_BOARD_BTN_TOOL_TIP));

        btnApplyNewEditorSettings.setTooltip(new Tooltip(Constants.APPLAY_NEW_EDITOR_SETTINGS_BTN_TOOL_TIP));
    }


    /**
     * Helper method to create a new game with specific configurations
     *
     * @param noOfRows       number of board rows
     * @param noOfCols       number of board columns
     * @param overflow       is overflow mode activated or not
     * @param percentOfWalls percentage of allowed walls
     */
    private void createGame(int noOfRows, int noOfCols, boolean overflow, double percentOfWalls) {

        // adding grid pane dynamically based on number of rows and columns specified by user.
        gridpane = createGridPaneForPlayingFieldDynamically(mainBorderPane, noOfRows, noOfCols);

        // initializing the imageview
        imgViewsPlayingField = initImageViews(gridpane);

        // creating new game
        gui.createNewGame(gui, noOfRows, noOfCols, overflow, percentOfWalls);

        game = gui.getGame();

        gui.initializePlayingField(imgViewsPlayingField);

        gui.setAnimationDuration(getAnimationDuration());

        turnOnGameMode();


    }

    /**
     * gets the animation duration configured by user.
     *
     * @return animation duration configured by user.
     */
    private Duration getAnimationDuration() {
        return sliderAnimationSpeed.getValue() == 0.0 ? Duration.ZERO : Duration.millis(sliderAnimationSpeed.getValue());
    }

    /**
     * turns on the game mode.
     */
    private void turnOnGameMode() {
        anckorBtnsAtStart.setVisible(false);
        anckorBtnsAtStart.managedProperty().bind(anckorBtnsAtStart.visibleProperty());

        hBOxBoardSetting.setDisable(false);
        hBOxBoardSetting.managedProperty().bind(hBOxBoardSetting.visibleProperty());

        grdPnStartGame.setVisible(false);
        grdPnStartGame.managedProperty().bind(grdPnStartGame.visibleProperty());

        hBoxResult.setDisable(false);
        hBoxResult.managedProperty().bind(hBoxResult.visibleProperty());

        mnuSettings.setVisible(true);
        mnuSettings.managedProperty().bind(mnuSettings.visibleProperty());

        lblResult.setVisible(true);
        lblResult.managedProperty().bind(lblResult.visibleProperty());
        lblResult.setText(Constants.LBL_RESULT_STR + " (" + game.getNoOfClicks() + ")");
        gui.animateResultLabel(lblResult);

    }

    /**
     * Creates a 2d array of imageViews corresponding to the gridPane.
     * Each imageView becomes a child of the gridPane and fills a cell.
     * For proper resizing it is bound to the gridPanes width and height.
     *
     * @return an array of imageviews added to the gridPane
     */
    public ImageView[][] initImageViews(GridPane grdPn) {
        int colCount = grdPn.getColumnCount(); //Spaltenanzahl
        int rowCount = grdPn.getRowCount(); // Zeilenanzahl

        ImageView[][] imageViews = new ImageView[colCount][rowCount];

        int cellWidth = (int) grdPn.getWidth() / colCount;
        int cellHeight = (int) grdPn.getHeight() / rowCount;

        // bind each Imageview to a cell of the grid pane
        for (int x = 0; x < colCount; x++) {
            for (int y = 0; y < rowCount; y++) {
                //creates an empty imageview
                imageViews[x][y] = new ImageView();

                //image has to fit a cell
                imageViews[x][y].setPreserveRatio(true);
                imageViews[x][y].setFitWidth(cellWidth);
                imageViews[x][y].setFitHeight(cellHeight);
                imageViews[x][y].setFitWidth(Double.MAX_VALUE);
                imageViews[x][y].setFitHeight(Double.MAX_VALUE);
                imageViews[x][y].setSmooth(true);

                GridPane.setRowIndex(imageViews[x][y], x);
                GridPane.setColumnIndex(imageViews[x][y], y);

                //add the imageview to the cell
                grdPn.add(imageViews[x][y], x, y);

                //the image shall resize when the cell resizes
                imageViews[x][y].fitWidthProperty().bind(grdPn.widthProperty().divide(colCount));
                imageViews[x][y].fitHeightProperty().bind(grdPn.heightProperty().divide(rowCount));

                handleOnDragOver(imageViews[x][y], new Position(y, x));
                // By manipulating the board in the backend, the position is reversed -> x is y and y is x.. (JavaFX 🤔)
                handleOnDragDropped(imageViews[x][y], new Position(y, x));

            }
        }

        return imageViews;
    }

    /**
     * Helper method to configure the gui to load new game from JSON file.
     *
     * @param game the new game instance.
     */
    private void loadGame(Game game) {
        int rowCount = game.getBoard().length;
        int colCount = game.getBoard()[0].length;

        gridpane = createGridPaneForPlayingFieldDynamically(mainBorderPane, rowCount, colCount);

        // initializing the imageview
        imgViewsPlayingField = initImageViews(gridpane);

        // creating new game
        gui.loadGame(game, imgViewsPlayingField);

        gui.setAnimationDuration(getAnimationDuration());

        turnOnGameMode();
    }

    /**
     * handles the on drage over listener to determinate which image should be accepted on which cell.
     *
     * @param iv              the image view that has the cell.
     * @param currentPosition current position the dragging mouse stands above.
     */
    public void handleOnDragOver(ImageView iv, Position currentPosition) {
        iv.addEventHandler(DragEvent.DRAG_OVER, event -> {

            if (event.getGestureSource() != iv && event.getDragboard().hasImage()) {
                Cell currentCell = game.getBoard()[currentPosition.getX()][currentPosition.getY()];
                Dragboard db = event.getDragboard();
                // water source must be occupied just on pipes, not on walls.
                if (Objects.equals(db.getString(), Constants.WATER_SOURCE_STR) && game.getPlayingField().isWall(currentCell)) {
                    event.acceptTransferModes(TransferMode.NONE);
                } else {
                    event.acceptTransferModes(TransferMode.ANY);
                }

            }

            event.consume();
        });
    }


    /**
     * handles the on drage dropped listener to determinate which image is accepted.
     *
     * @param iv              the image view that has the cell.
     * @param currentPosition current position the image dropped into.
     */
    public void handleOnDragDropped(ImageView iv, Position currentPosition) {
        iv.addEventHandler(DragEvent.DRAG_DROPPED,
                event -> {
                    Dragboard db = event.getDragboard();
                    boolean success = false;

                    if (db.hasImage()) {
                        System.out.println(db.getString() + " was dropped at position [" + currentPosition.getX() + "][" + currentPosition.getY() + "].");
                        String cellTyp = db.getString();

                        // Water source image should not be dropped, it should be animated.
                        if (!cellTyp.equals(Constants.WATER_SOURCE_STR)) {
                            iv.setImage(db.getImage());
                            iv.setRotate(0.0);
                        }

                        // changing the cell in the backend.
                        switch (cellTyp) {
                            case Constants.CURVE_PIPE_STR -> {
                                game.setCellAtSpecificIndex(new CurvePipe(EnumSet.copyOf(List.of(TOP, LEFT))), currentPosition);
                            }
                            case Constants.T_PIPE_STR -> {
                                game.setCellAtSpecificIndex(new T_Pipe(EnumSet.copyOf(List.of(TOP, LEFT, BOTTOM))), currentPosition);
                            }
                            case Constants.STRAIGHT_PIPE_STR -> {
                                game.setCellAtSpecificIndex(new StraightPipe(EnumSet.copyOf(List.of(LEFT, RIGHT))), currentPosition);
                            }
                            case Constants.END_PIPE_STR -> {
                                game.setCellAtSpecificIndex(new EndPipe(EnumSet.copyOf(List.of(TOP))), currentPosition);
                            }
                            case Constants.WALL_STR -> {
                                if (game.getSource() != null && game.getSource().equals(currentPosition)) {
                                    game.setSource(null);
                                    gui.resetWaterAnimation(currentPosition);

                                }
                                game.setCellAtSpecificIndex(new Wall(), currentPosition);
                            }
                            case Constants.WATER_SOURCE_STR -> {
                                Position oldWaterSource = game.getSource();
                                if (oldWaterSource != null) {
                                    gui.resetWaterAnimation(oldWaterSource);
                                }
                                game.setSource(currentPosition);
                                gui.setEffectToWaterSource(iv);

                            }
                        }

                        gui.checkIfBoardIsSolved();
                        gui.updateBoardOnTheGUI(true);
                        success = true;

                        Helper.printBoard(game.getBoard());
                    }

                    event.setDropCompleted(success);
                    event.consume();
                });
    }


    /**
     * create a grid pane after run time.
     *
     * @param mainBorderPane the root
     * @param rowCount       number of rows
     * @param colCount       number of columns
     * @return
     */
    public GridPane createGridPaneForPlayingFieldDynamically(BorderPane mainBorderPane, int rowCount, int colCount) {
        GridPane gridpane = new GridPane();
        gridpane.setId("grdPnBoard");

        for (int x = 0; x < colCount; x++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setMinWidth(2);
            gridpane.getColumnConstraints().add(col);
        }
        for (int y = 0; y < rowCount; y++) {
            RowConstraints row = new RowConstraints();
            row.setMinHeight(2);
            gridpane.getRowConstraints().add(row);
        }

        mainBorderPane.setCenter(gridpane);

        gridpane.setGridLinesVisible(false);


        return gridpane;
    }

    /**
     * saves current game states to a json file
     */
    private void saveGameToJSONFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Pfad des Spiels sichern");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Eine JSON-Datei wählen", "*.json"));

        File savedFile = fileChooser.showSaveDialog(null);
        String path = (savedFile != null) ? savedFile.getPath() : null;


        if (savedFile != null) {
            GameData game = new GameData(UserInterfaceController.game.getSource(),
                    UserInterfaceController.game.isOverflow(),
                    Helper.transposeMatrix(GameData.Obj2int(UserInterfaceController.game.getBoard())));
            try {
                boolean result = GameData.toJSON(path, game);
                if (result) {
                    System.out.println("Game states were saved in the following path " + path);

                    Alert alertInfo = new Alert(Alert.AlertType.INFORMATION, Constants.GAME_STATES_SUCCESS);
                    alertInfo.setTitle("Daten gespeichert");

                    ButtonType ok = new ButtonType("In Ordnung!", ButtonBar.ButtonData.OK_DONE);

                    alertInfo.getButtonTypes().setAll(ok);

                    alertInfo.showAndWait().ifPresent((type -> {
                        if (type == ok) {
                            System.out.println("Data saved successfully");
                        } else {
                            System.err.println("While saving the game states an unexpected Button was clicked");
                            GUIAlert.displayException(ExceptionCode.E7);
                        }
                    }));
                }
            } catch (UnsupportedEncodingException e) {
                System.err.println("Error: Unsupported encoding exception occurred while writing to file: " + path);
                GUIAlert.displayException(ExceptionCode.E9, path);
            } catch (FileNotFoundException e) {
                System.err.println("Error: File not found at the specified path: " + path);
                GUIAlert.displayException(ExceptionCode.E4, path);
            } catch (SecurityException | IOException e) {
                System.err.println("Error: IO- or Security exception occurred while writing to file: " + path);
                GUIAlert.displayException(ExceptionCode.E10, path);
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                GUIAlert.displayException(ExceptionCode.E11);
            }
        } else {
            System.err.println("Error: File not found at the specified path: " + path);
            GUIAlert.displayException(ExceptionCode.E4, "");
        }
    }


    /**
     *
     * ################################### Listener Methods ###################################
     *
     */


    /**
     * Listener fot crating a new game
     */
    @FXML
    public void onNewGame() {
        noOfRows = (int) Math.round(sliderNoOfRows.getValue());
        noOfCols = (int) Math.round(sliderNoOfColumns.getValue());
        overflow = chkBoxOverflow.isSelected();
        percentOfWalls = sliderPercentOfWalls.getValue();

        createGame(noOfRows, noOfCols, overflow, percentOfWalls);

    }


    /**
     * Listener fot exiting from the game.
     */
    @FXML
    private void onExitGame() {
        GUIAlert.showExitDialog();
    }

    /**
     * Listener fot loading game from external json file.
     */
    @FXML
    private void onLoadGame() {

        File currentDirectory = null;
        try {
            currentDirectory = new File(UserInterfaceController.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        } catch (URISyntaxException ex) {
            System.err.println("Unexpected Error thrown while uploading the game states... ");
            GUIAlert.displayException(ExceptionCode.E8);
        }

        FileChooser fileChooser = new FileChooser();
        if (currentDirectory != null) {
            fileChooser.setInitialDirectory(currentDirectory.getParentFile().getParentFile().getParentFile());
        }
        fileChooser.setTitle("Spiel von einer Json-Datei laden");
        fileChooser.getExtensionFilters().setAll(new FileChooser.ExtensionFilter("Eine JSON-Datei wählen", "*.json"));

        File selectedFile = fileChooser.showOpenDialog(null);

        GameData gameData;
        if (selectedFile != null) {
            String path = selectedFile.getPath();
            System.out.println("Selected file path: " + path);
            try {
                gameData = GameData.fromJSON(path);
                System.out.println(gameData);
                UserInterfaceController.game = new Game(gui, gameData);
                loadGame(game);
            } catch (NoSuchFileException | AssertionError e) {
                System.err.println("The file could not be found at the specified path: " + path);
                GUIAlert.displayException(ExceptionCode.E4, "");
            } catch (JsonSyntaxException e) {
                System.err.println("The JSON file is not properly formatted: " + e.getMessage());
                GUIAlert.displayException(ExceptionCode.E1, path);
            } catch (IllegalStateException | StackOverflowError e) {
                System.err.println("Error: json objects are not correct. they have illegal states (values)");
                GUIAlert.displayException(ExceptionCode.E2);
            } catch (IOException e) {
                String msg = "Error reading file: " + e.getMessage();
                System.err.println(msg);
                GUIAlert.displayException(ExceptionCode.E3);
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                GUIAlert.displayException(ExceptionCode.E5);
            }

        }


    }


    /**
     * Listener fot exiting from the game.
     */
    @FXML
    private void navigationBarGameHandler(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();
        switch (item.getId()) {
            // create new game, while there is currently a game already.
            case "menuItemNewGame" -> {
                GUIAlert.showDialogOfCreatingNewGame();
            }
            // save current game states to a json file
            case "menuItemSaveStates" -> {
                saveGameToJSONFile();
            }
            // exit game
            case "menuItemExit" -> {
                onExitGame();
            }
        }
    }


    /**
     * Listener dor activating the editor mode.
     *
     * @param actionEvent action event.
     */
    @FXML
    private void navigationBarModeHandler(ActionEvent actionEvent) {
        MenuItem item = (MenuItem) actionEvent.getSource();

        if ("radioMenuItemEditorMode".equals(item.getId())) {
            hBoxEditorModus.setVisible(true);
            hBoxEditorModus.managedProperty().bind(hBoxEditorModus.visibleProperty());

            btnSaveEditorMode.setVisible(true);
            btnSaveEditorMode.managedProperty().bind(btnSaveEditorMode.visibleProperty());

            btnDeleteBoardEditorMode.setVisible(true);
            btnDeleteBoardEditorMode.managedProperty().bind(btnDeleteBoardEditorMode.visibleProperty());

            lblResult.setVisible(false);
            lblResult.managedProperty().bind(lblResult.visibleProperty());

            btnMixBoardEditorMode.setVisible(true);
            btnMixBoardEditorMode.managedProperty().bind(btnMixBoardEditorMode.visibleProperty());


            gui.setIsEditorMode(true);

        }
    }


    /**
     * listener for applying user configuration in the editor mode.
     */
    @FXML
    private void applyNewEditorSettings() {
        int rowCount = (int) Math.round(editorModeSliderNoOfRows.getValue());
        int colCount = (int) Math.round(editorModeSliderNoOfColumns.getValue());
        boolean newOverflow = editorModeChkBoxOverflow.isSelected();

        if (rowCount != noOfRows || colCount != noOfCols) {
            gui.changeSizeOfBoard(rowCount, colCount);
            // adding grid pane dynamically based on number of rows and columns specified by user.
            gridpane = createGridPaneForPlayingFieldDynamically(mainBorderPane, rowCount, colCount);


            // initializing the imageview
            imgViewsPlayingField = initImageViews(gridpane);


            gui.initializePlayingField(imgViewsPlayingField);
        }
        if (newOverflow != overflow) {
            gui.setNewOverflowMode(newOverflow);
        }

    }


    /**
     * set listener to drag detected of curve pipe.
     *
     * @param mouseEvent mouse event
     */
    @FXML
    private void setOnDragDetectedCurvePipe(MouseEvent mouseEvent) {
        Dragboard db = editorModusImageCurvePipe.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(JavaFxGUI.getImages()[0]);
        content.putString(Constants.CURVE_PIPE_STR);
        db.setContent(content);

        mouseEvent.consume();
    }

    /**
     * set listener to drag detected of straight pipe.
     *
     * @param mouseEvent mouse event
     */
    @FXML
    private void setOnDragDetectedStraightPipe(MouseEvent mouseEvent) {
        Dragboard db = editorModusImageStraightPipe.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putImage(JavaFxGUI.getImages()[2]);
        content.putString(Constants.STRAIGHT_PIPE_STR);
        db.setContent(content);

        mouseEvent.consume();
    }


    /**
     * set listener to drag detected of t-cross pipe.
     *
     * @param mouseEvent mouse event
     */
    @FXML
    private void setOnDragDetectedT_Pipe(MouseEvent mouseEvent) {
        Dragboard db = editorModusImageT_Pipe.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(Constants.T_PIPE_STR);
        content.putImage(JavaFxGUI.getImages()[4]);
        db.setContent(content);

        mouseEvent.consume();
    }

    /**
     * set listener to drag detected of end pipe.
     *
     * @param mouseEvent mouse event
     */
    @FXML
    private void setOnDragDetectedEndPipe(MouseEvent mouseEvent) {
        Dragboard db = editorModusImageEndPipe.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(Constants.END_PIPE_STR);
        content.putImage(JavaFxGUI.getImages()[6]);
        db.setContent(content);

        mouseEvent.consume();
    }


    /**
     * set listener to drag detected of wall.
     *
     * @param mouseEvent mouse event
     */
    @FXML
    private void setOnDragDetectedWall(MouseEvent mouseEvent) {
        Dragboard db = editorModusImageWall.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(Constants.WALL_STR);
        content.putImage(JavaFxGUI.getImages()[8]);
        db.setContent(content);

        mouseEvent.consume();
    }


    /**
     * set listener to drag detected of water source.
     *
     * @param mouseEvent mouse event
     */
    @FXML
    private void setOnDragDetectedWaterSource(MouseEvent mouseEvent) {
        Dragboard db = editorModusImageWaterSource.startDragAndDrop(TransferMode.ANY);

        /* legt einen String im Clipboard ab*/
        ClipboardContent content = new ClipboardContent();
        content.putString(Constants.WATER_SOURCE_STR);
        content.putImage(JavaFxGUI.getImages()[9]);
        db.setContent(content);

        mouseEvent.consume();

    }


    /**
     * Listener to delete current playing board and replace all cell with walls
     */
    @FXML
    private void deleteCurrentBoardByEditorMode() {
        Alert stateLossingWarning = new Alert(Alert.AlertType.WARNING, Constants.WARNING_MSG_FOR_LOSSING_DATA);
        stateLossingWarning.setTitle(Constants.ASK_TO_DELETE_BOARD);

        ButtonType yes = new ButtonType(Constants.YES_DIALOG_BTN, ButtonBar.ButtonData.YES);
        ButtonType no = new ButtonType(Constants.NO_DIALOG_BTN, ButtonBar.ButtonData.NO);

        stateLossingWarning.getButtonTypes().setAll(yes, no);

        stateLossingWarning.showAndWait().ifPresent((type -> {
            if (type == yes) {
                System.out.println("the board was deleted by the user ");
                gui.initializePlayingFieldForEditorMode(imgViewsPlayingField);
                gui.resetWaterSource();
            } else if (type == no) {
                System.out.println("Deleting the board was declined from user ");
            } else {
                System.err.println("While deleting the board an unexpected Button was clicked");
                GUIAlert.displayException(ExceptionCode.E7);
            }
        }));
    }

    /**
     * Listener to save current playing board and turning on the game mode.
     */
    @FXML
    private void SaveCurrentBoardByEditorMode() {
        if (gui.isThereWaterSource()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, Constants.ASK_TO_GO_TO_GAME_MODE);
            alert.setTitle(Constants.ASK_TO_SAVE_CURRENT_BOARD_AT_EDITOR_MODE);

            ButtonType yes = new ButtonType(Constants.YES_DIALOG_BTN, ButtonBar.ButtonData.YES);
            ButtonType no = new ButtonType(Constants.NO_DIALOG_BTN, ButtonBar.ButtonData.NO);

            alert.getButtonTypes().setAll(yes, no);

            alert.showAndWait().ifPresent((type -> {
                if (type == yes) {
                    System.out.println("Turning-on from editor mode to game mode was approved by the user ");
                    hBoxEditorModus.setVisible(false);
                    hBoxEditorModus.managedProperty().bind(hBoxEditorModus.visibleProperty());

                    btnSaveEditorMode.setVisible(false);
                    btnSaveEditorMode.managedProperty().bind(btnSaveEditorMode.visibleProperty());

                    btnDeleteBoardEditorMode.setVisible(false);
                    btnDeleteBoardEditorMode.managedProperty().bind(btnDeleteBoardEditorMode.visibleProperty());

                    btnMixBoardEditorMode.setVisible(false);
                    btnMixBoardEditorMode.managedProperty().bind(btnMixBoardEditorMode.visibleProperty());

                    gui.setIsEditorMode(false);
                    radioMenuItemEditorMode.setSelected(false);

                } else if (type == no) {
                    System.out.println("Saving of the edited Board was declined from user ");
                } else {
                    System.err.println("While Saving of the edited Board an unexpected Button was clicked");
                    GUIAlert.displayException(ExceptionCode.E7);
                }
            }));
        } else {
            String msg = "Setzen Sie eine Quelle, bevor Sie das Feld Speichern";
            Alert warning = new Alert(Alert.AlertType.WARNING, msg);
            warning.setTitle("Es gibt keine Wasserquelle");
            ButtonType ok = new ButtonType("In Ordnung", ButtonBar.ButtonData.OK_DONE);
            warning.getButtonTypes().setAll(ok);

            warning.showAndWait().ifPresent((type -> {
                if (type == ok) {
                    System.out.println("Water source would be set by the user ");
                } else {
                    System.err.println("While Saving of the edited Board an unexpected Button was clicked");
                    GUIAlert.displayException(ExceptionCode.E7);
                }
            }));

        }
    }


    /**
     * shuffles the cells of the board randomly
     */
    @FXML
    private void mixCurrentBoardByEditorMode() {
        gui.mixRandomlyCurrentBoard();

    }

    /**
     * set the duration of animation for the filling of pipes.
     */
    @FXML
    private void setAnimationSpeed() {
        gui.setAnimationDuration(getAnimationDuration());
    }


}


