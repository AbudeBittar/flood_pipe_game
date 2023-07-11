package gui;


/**
 * Class, that represents all string constants for the GUI,
 *
 *
 *
 * @author Abdulrahman Al Bittar
 */
public final class Constants {

    // Pipes
    public static final String CURVE_PIPE_STR        = "CurvePipe";
    public static final String T_PIPE_STR            = "T_Pipe";
    public static final String STRAIGHT_PIPE_STR     = "StraightPipe";
    public static final String END_PIPE_STR          = "EndPipe";
    public static final String WALL_STR              = "Wall";
    public static final String WATER_SOURCE_STR      = "WaterSource";


    // Tool tips to use on hover
    public static final String NEW_GAME_BTN_TOOL_TIP      = "Ein neues Spiel mit aktuellen Einstellungen starten";
    public static final String LOAD_GAME_BTN_TOOL_TIP     = "Ein gespeichertes Spiel laden";
    public static final String EXIT_GAME_BTN_TOOL_TIP     = "Das Programm ohne Speichern der Spielzustände beenden";

    public static final String SAVE_EDITED_BOARD_BTN_TOOL_TIP     = "Das aktuelle editierte Feld speichern und in den Spielmodus wechseln";
    public static final String DELETE_EDITOR_BOARD_BTN_TOOL_TIP   = "Das aktuelle Feld löschen und alle Zellen mit Mauerstücken ersetzen";
    public static final String MIX_EDITOR_BOARD_BTN_TOOL_TIP      = "Die Röhren des aktuellen Feldes mit zufälligem Anzahl von Drehungen mixen";
    public static final String ANIMATION_SPEED_SLIDER_TOOL_TIP    = "Die Zeit stellt die Geschwindigkeit der Animation von 0 bis 1 Sekunde pro Röhre";

    public static final String APPLAY_NEW_EDITOR_SETTINGS_BTN_TOOL_TIP   = "Bei Kürzung der Zeilen- bzw. Spaltenanzahl entsteht Datenverlust";



    public static final String WARNING_MSG_FOR_LOSSING_DATA = "Ungespeicherte Spielzustände gehen bei dieser Aktion verloren";

    static final String ASK_TO_DELETE_BOARD                 = "Möchten Sie das aktuelle Feld wirklich löschen?";
    static final String ASK_TO_QUITING_THE_GAME = "Möchten Sie das Spiel wirklich beenden?";
    static final String ASK_TO_START_NEW_GAME = "Möchten Sie ein neues Spiel starten?";
    static final String ASK_TO_GO_TO_GAME_MODE = "Möchten Sie in den Spielmodus wechseln?";
    static final String ASK_TO_SAVE_CURRENT_BOARD_AT_EDITOR_MODE = "Möchten Sie das aktuelle Feld wirklich speichern?";






    // EXCEPTIONS
    public static final String E1  = "Die JSON-Datei ist nicht korrekt formatiert";
    public static final String E2  = "Unzulässiger Zustandsfehler. Die Objekte der Json-Datei enthalten unzulässige Zustände.";
    public static final String E3  = "Fehler beim Lesen der Datei. Die Leseberichtigung bitte überprüfen. ";
    public static final String E4  = "Die Datei konnte unter dem angegebenen Pfad nicht gefunden werden: ";
    public static final String E5  = "Es ist ein unerwarteter Fehler aufgetreten";
    public static final String E6  = "Beim Starten eines neuen Spiels ist ein Fehler aufgetreten: ";
    public static final String E7  = "Ein unerwarteter Button wurde angeklickt";
    public static final String E8  = "Ein unerwarteter Fehler ist beim Hochladen der Spielstände aufgetreten. ";
    public static final String E9  = "Beim Schreiben in eine Datei ist eine nicht unterstützte Kodierungsfehler aufgetreten";
    public static final String E10 = "Fehler beim Schreiben in die Datei. Die Schreibberichtigung bitte überprüfen. ";
    public static final String E11 = "Ein unerwarteter Fehler ist beim Speichern der Spielstände aufgetreten. ";
    public static final String E12 = "beim Neustart des Spiels wurde ein Input-Output-Fehler ausgelöst";

    static final String EXCEPTION_DIALOG_TITLE = "Leider ist ein Fehler aufgetreten";
    static final String ERROR_CODE             = " - Fehler Code E";




    // other gui constants
    public static final String LBL_RESULT_STR      = "Benötigte Drehungen bis jetzt: ";

    static final String WIN_MSG = "Glückwunsch, Du hast gewonnen";
    static final String GAME_STATES_SUCCESS = "Die Spielzustände wurden erfolgreich gespeichert.";

    static final String BOARD_IS_SOLVED = "das aktuelle Feld ist gelöst";



    static final String NEW_GAME_BTN_DIALOG = "Neues Spiel";
    static final String QUIT_GAME_BTN_DIALOG = "Spiel beenden";
    static final String HINT_TITLE = "Hinweis";

    static final String YES_DIALOG_BTN = "Ja, bitte.";
    static final String NO_DIALOG_BTN = "Nein, danke :)";
}
