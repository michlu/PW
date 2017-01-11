/**
 * @author Michlu
 * @sience 2017-01-10
 */
public class Stack {
    private int sizeStack = 2;
    private int size;
    private int[] values = new int[sizeStack];

    public boolean isEmpty(){
        return size == 0;
    }

    public boolean isFull(){
        return size == sizeStack;
    }

    public void push(int i) throws FullStackException {
        if(isFull()){
                throw new FullStackException();
        }
        values[size++] = i;
    }

    public int pop() throws EmptyStackException {
        if(isEmpty()){
                throw new EmptyStackException();
        }
        return values[--size];
    }

    public void clear() {
        size = 0;
    }

    public int getSize() {
        return size;
    }

    public void setSizeStack(int capacity){
        sizeStack = capacity;
        int[] copy = new int[capacity];
        for(int i=0;i<values.length;i++) {
            copy[i]=values[i];
        }
        values = new int[capacity];
        for(int i=0;i<copy.length;i++) {
            values[i]=copy[i];
        }
    }
    // podaje element na szczycie stosu
    public int peek(){
        int s = size;
        return values[s-1];
    }

    // podaje pozycje obiektu na stosie, albo -1 jezeli takiego nie ma
    public int search(int val){
        int result = -1;
        for(int i=0;i<values.length;i++) {
           if(val==values[i])
               result = i;
        }
        return result;
    }
}

