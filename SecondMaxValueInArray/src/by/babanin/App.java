package by.babanin;

public class App {
    public static void main(String[] args) {
        int[] arr = {1, 3, 1, 8, -6, 2};
        int result = arr[0];
        int max = arr[0];
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > max) {
                result = max;
                max = arr[i];
            }
        }
        System.out.println(result);
    }
}
