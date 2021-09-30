public class triangle {
    
    public static void main(String[] args) {
        int row = 0;
        int size = 5;
        while (row < size) {
            row = row + 1;
            int col = 0;
            while (col < row) {
                System.out.print('*');
                col = col +1;
            }
            System.out.println();
        }
    }
}