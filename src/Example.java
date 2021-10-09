import java.util.Arrays;
import java.util.Stack;

public class Example {
    public static void printBoard(int[][] board) {
        System.out.println("====Printing====");
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                System.out.printf("%3d", board[i][j]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        int[][] board = new int[5][5];
        int r1 = 0, r2 = 4, c1 = 0, c2 = 4;
        int cnt = 0;
        Stack<Integer> st[] = new Stack[25];

        // st 초기화
        for (int i = 0; i < 25; ++i) {
            st[i] = new Stack();
        }
        // board 채우기
        for (int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                board[i][j] = cnt++;
            }
        }

        // 2. stack 채우기
        for (int i = r1; i < r2; ++i) {
            for (int j = c1; j < c2; ++j) {
                st[i + j].add(board[i][j]);
            }
        }
        System.out.println("\n<before>");
        printBoard(board);

        // 3. stack 비우기
        for (int i = r1; i < r2; ++i) {
            for (int j = c1; j < c2; ++j) {
                board[i][j] = st[i + j].pop();
            }
        }
        System.out.println("\n<after>");
        printBoard(board);
    }
}
