/**
 * @author Michlu
 * @sience 2017-01-10
 */
import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Before;

public class StackTest {
    private Stack s;

    @Before
    public void setUp(){
        s = new Stack();
    }

    @Test
    public void givenNewStack_WhenCheckedForEmptiness_ThenTrueIsReturned(){

        assertTrue(s.isEmpty());
    }

    @Test
    public void givenNotEmptyStack_WhenCheckingForEmptiness_ThenFalseIsReturned() throws FullStackException {

        s.push(42);
        assertFalse(s.isEmpty());
    }

    @Test(expected= EmptyStackException.class)
    public void givenEmptyStack_WhenPopping_ThenExceptionIsThrow() throws EmptyStackException {

        s.pop();
    }

    @Test
    public void givenNotEmptyStack_WhenPopping_ThenValueIsReturned() throws EmptyStackException, FullStackException {
        s.push(42);

        assertEquals(42, s.pop());
    }

    @Test
    public void givenStack_WhenPopping_ThenLastAddedValueIsReturned() throws EmptyStackException, FullStackException {
        s.push(42);
        s.push(15);

        assertEquals(15, s.pop());
    }

    @Test
    public void givenNotEmptyStack_WhenPopping_ThenStackBecomesEmpty() throws EmptyStackException, FullStackException {
        s.push(42);
        s.pop();

        assertTrue(s.isEmpty());
    }

    @Test
    public void givenStackWithTwoElements_WhenPoppingSingleItem_ThenStackIsStillNotEmpty() throws EmptyStackException, FullStackException {
        s.push(15);
        s.push(42);

        s.pop();

        assertFalse(s.isEmpty());
    }

    @Test
    public void givenStackWithTwoElements_WhenPoppingSecondItem_ThenFirstAddedValueIsEmpty() throws EmptyStackException, FullStackException {
        s.push(15);
        s.push(42);

        s.pop();

        assertEquals(15, s.pop());
    }
    @Test // clear
    public void givenStackWithTwoElements_whenClear_thenStackIsEmpty() throws FullStackException {
        s.push(15);
        s.push(42);

        s.clear();

        assertTrue(s.isEmpty());
    }

    @Test //getSize
    public void givenStackWithTwoElements_whenGetSize_ThenSizeEqualsTwo() throws FullStackException {
        s.push(15);
        s.push(42);

        assertEquals(2, s.getSize());
    }

    @Test //getSize
    public void givenStackWithTwoElementsAndPoppingTwoElements_whenGetSize_thenSizeEqualsZero() throws FullStackException, EmptyStackException {
        s.push(15);
        s.push(42);

        s.pop();
        s.pop();

        assertEquals(0, s.getSize());
    }

    @Test(expected= FullStackException.class)
    public void givenStackWithTwoElements_whenPushNextElement_ThenExceptionIsThrow() throws FullStackException {
        s.push(15);
        s.push(42);

        s.push(12);
    }

    @Test //testSetSizeStack
    public void givenStackWithTwoElements_whenSetSizeThreeAndPushElement_thenSizeStackIsThree() throws FullStackException, EmptyStackException {
        s.push(15);
        s.push(42);

        s.setSizeStack(3);
        s.push(12);

        assertEquals(3, s.getSize());
        assertEquals(12, s.pop());
        assertEquals(42, s.pop());
    }

    @Test // Peek
    public void givenNotEmptyStack_whenPeek_thenGiveValueHighestElementOnStack() throws FullStackException {
        s.push(15);
        assertEquals(15, s.peek());
        s.push(55);
        assertEquals(55, s.peek());
    }

    @Test
    public void givenStackWithTwoElements_whenSearch_thenReturnPositionValueOfElement() throws FullStackException {
        s.push(15);
        s.push(42);

        assertEquals(0, s.search(15));
        assertEquals(1, s.search(42));
        assertEquals(-1, s.search(17));
    }
}
