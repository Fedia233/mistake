import java.util.Iterator;

public class main {
    public static void main(String[] args) {
        Range<Integer> range = Range.of(1, 10);
        Iterator<Integer> iter = range.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }
}
