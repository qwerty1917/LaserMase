import java.util.ArrayList;

/**
 * Created by hyeongminpark on 14. 12. 18..
 */
public class main {
    public static void main(String[] args){
        ArrayList<Integer> myList = new ArrayList<Integer>();
        myList.add(1);
        myList.add(2);

        for(int i : myList){
            System.out.println(i);
        }

        String myString = "aaa" + 12 + "bbb";

        System.out.println(myString);
    }
}
