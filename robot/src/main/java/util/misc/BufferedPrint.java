package util.misc;

public class BufferedPrint {
    private int i = 0;
    
    public void bufferedPrint(String input) {
        bufferedPrint(input, 50);
    }

    public void bufferedPrint(String input, int itr) {
       if(i % itr == 0) {
           System.out.println(input);
       } 
       i++;
    }
}