public class MaxList {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
        int start = -1000000000;
        for (int i = 0; i < m.length; i++) {
            if (m[i] > start) {
                start = m[i];
            }
        }
        return start;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
       System.out.println(max(numbers));     
    }
}