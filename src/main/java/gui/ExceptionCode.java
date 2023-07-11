package gui;


/**
 * Enum, that represents all  error code to inform the user about errors could be thrown.
 *
 *
 *
 * @author Abdulrahman Al Bittar
 */
public enum ExceptionCode {

    E1(1),
    E2(2),
    E3(3),
    E4(4),
    E5(5),
    E6(6),
    E7(7),
    E8(8),
    E9(9),
    E10(10),
    E11(11),
    E12(12);



    /**
     * Exception ID
     */
    private final int exceptionID;


    /**
     * Constructor.
     *
     * @param exceptionID Exception ID .
     */
    private ExceptionCode(int exceptionID) {
        this.exceptionID = exceptionID;
    }



    /** ############################################## GETTER & SETTER ############################################## **/

    public int getExceptionID() {

        return this.exceptionID;
    }

}
