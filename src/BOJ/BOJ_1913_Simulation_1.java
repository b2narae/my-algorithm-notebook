/*
Reviewed : 2023-08-02
백준 : 1913 달팽이 문제
- 방법 1 : 중앙에서 바깥으로 채우는 방법
- 방법 2 : 바깥에서 중앙으로 채우는 방법

방법 2의 풀이가 더 간략하고 좋음. 본 파일은 방법 1의 풀이
! 주의 사항 : 출력이 많으므로, StringBuilder나 BuffereWriter를 사용해야 함
*/
package BOJ;

import java.io.*;

public class BOJ_1913_Simulation_1 {
    static int[][] direction = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}}; // 상 우 하 좌

    public static void main(String[] args) throws IOException {
        // (1) 변수 및 Input =========================================
        int size, targetNum;                                          // 정사각형 한 변의 길이, 출력 숫자
        int currentRow, currentCol, currentNum = 1, currentStep = 1;  // 탐색용 행, 열, 채우는 숫자, 한 변의 길이
        int[][] board;                                                // 정사각형 보드 저장
        int[] answer = new int[2];                                    // 정답 출력, [0] : row, [1] : col

        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        size = Integer.parseInt(br.readLine());
        targetNum = Integer.parseInt(br.readLine());

        board = new int[size][size];

        // (2) 풀이 =========================================
        currentRow = currentCol = (size / 2);
        if (currentNum == targetNum) {
            answer[0] = currentRow + 1;
            answer[1] = currentCol + 1;
        }
        board[currentRow][currentCol] = currentNum++;

        int currentDirection = 0;                                     // 방향 지정용
        loop : while (true) {
            for (int i = 0; i < 2; ++i) {                             // currentStep 크기만큼 (상, 우), (하, 좌) 방향으로 이동
                for (int step = 1; step <= currentStep; ++step) {
                    currentRow += direction[currentDirection][0];
                    currentCol += direction[currentDirection][1];

                    // 범위를 벗어나면
                    if (currentRow < 0 || currentCol < 0 || currentRow >= size || currentCol >= size) {
                        break loop;
                    }

                    // 타겟 숫자에 도착한 경우
                    if (currentNum == targetNum) {
                        answer[0] = currentRow + 1;
                        answer[1] = currentCol + 1;
                    }
                    // 값 저장
                    board[currentRow][currentCol] = currentNum++;
                }
                currentDirection = (currentDirection + 1 >= 4) ? 0 : currentDirection + 1;
            }
            currentStep++; // 한 칸 씩 늘리기
        }

        // (3) 정답 출력 부분 =========================================
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                sb.append(board[i][j]).append(" ");
            }
            sb.append("\n");
        }
        sb.append(answer[0]).append(" ").append(answer[1]);
        System.out.println(sb.toString());
        return;
    }
}
