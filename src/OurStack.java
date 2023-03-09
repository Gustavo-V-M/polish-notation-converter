
public class OurStack<T> 
{

    private final int DEFAULT_SIZE = 128;
    private int count = -1;
    private int size;
    private T data[];

    OurStack(){
        this.size = DEFAULT_SIZE;
        data = (T[]) new Object[size];
    }
    public OurStack(int size)
    {
        this.size = size;
        data = (T[]) new Object[size];
    }

    public void push(T val)
    {
        if (count < size){
            count++;
            data[count] = val;
        }
        else{
            throw new StackOverflowError(); 
        }
    }

    public T pop()
    {
        T top;
        if (count >= 0){
            top = data[count];
            data[count] = null;
            count--;
            return top;
        }
        return null;
        

        

    }

    public T top()
    {
        if (count >= 0) return data[count];
        
        return null;

    }
    public int getSize(){

        return this.size;
    }
    public void setSize(int size){
        this.size = size;
    }

    public int getCount(){
        
        return this.count+1;
    }

    public boolean isEmpty(){
        
        return (count == -1);
    }

    public void clear(){

        for (int i = 0; count >= 0; i++){
            this.pop();
        }
    }

}
