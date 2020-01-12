package by.babanin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RemoveElementsList {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c", "d"));
        list.add(1, "x");
        list.add(3, "z");

        for (int i = 0; i < list.size(); i++) {
            list.remove(i);
        }
        System.out.println(list);
    }
}
