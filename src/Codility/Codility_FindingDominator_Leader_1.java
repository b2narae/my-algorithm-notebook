package Codility;

public class Codility_FindingDominator_Leader_1 {
    public int solution(int[] A) {
        int answer = -1;
        int candidate = -1;
        int size = 0;
        int count = 0;

        // 값을 비교하고 빼는 목적에는 stack이 적합
        for (int i = 0; i < A.length; ++i) {
            if (size == 0) {
                candidate = A[i];
                size++;
            } else {
                if (candidate == A[i]) {
                    size++;
                } else {
                    size--;
                }
            }
        }

        // count
        // 실제 dominator가 없는 경우, candidate의 값은?
        // (1) 짝수 배열 : 마지막 pair의 값 중 먼저 나온 값
        // (2) 홀수 배열 : 마지막 인덱스의 값
        for (int i = 0; i < A.length; ++i) {
            if (A[i] == candidate) {
                count++;
                answer = i;
            }
        }

        // first index
        if (count > A.length / 2) {
            return answer;
        } else {
            return -1;
        }
    }
}
