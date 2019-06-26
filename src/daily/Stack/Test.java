package daily.Stack;

import java.util.LinkedList;

public class Test {
    public void test() {
        LinkedList<Integer>  test = new LinkedList<>();

        test.add(4);
        test.add(5);
        test.add(2);
        test.add(6);
        test.add(3);

        System.out.println(test.pop());
    }
}
