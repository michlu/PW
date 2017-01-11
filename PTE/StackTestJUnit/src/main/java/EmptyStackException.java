/**
 * @author Michlu
 * @sience 2017-01-10
 */
public class EmptyStackException extends Exception{
    private static final long serialVersionUID = 1L;

    public EmptyStackException() {
        System.out.println("Stack is Empty");
    }
}