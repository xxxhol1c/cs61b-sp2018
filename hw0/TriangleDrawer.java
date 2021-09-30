public class TriangleDrawer {
   
   public static void drawTriangle(int N) {
        int row = 0;
        while (row < N) {
            row = row + 1;
            int col = 0;
            while (col < row) {
                System.out.print('*');
                col = col +1;
            }
            System.out.println();
        }
    }
   
   public static void main(String[] args) {
        int num = Integer.parseInt(args[0]);
        drawTriangle(num);
   }
}