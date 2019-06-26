package daily.Stack.HanoiTower;

/**
 * 递归解决汉诺塔问题
 */
public class RecursionHanoiTower {
    public void hanoiTower(int n) {
        recursion(n, 'A', 'B', 'C');
    }

    private void recursion(int n, char src, char pass, char dest) {
        if (n == 1) {
            System.out.println("Move " + n + "th plate from " + src + " to " + dest);
            return;
        }

        // 把n-1个盘从src移动到pass
        recursion(n-1, src, dest, pass);

        // 把第n个盘从src移动到dest
        System.out.println("Move " + n + "th plate from " + src + " to " + dest);

        // 把n-1个盘从pass移动到dest
        recursion(n-1, pass, src, dest);
    }
}
