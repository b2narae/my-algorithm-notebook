package BOJ;
/*
(1) (left, mid) + (mid + 1, right)
(2) (left, mid - 1) + (mid, right)

(1) is better than (2)
ex) left = 2, right = 3, mid = 2
 */


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_7469_SegmentTree_1 {
    static int N, M;
    static int array[];
    static int sortedArray[][];
    static int tree[];

    static int findTreeSize () {
        int size = 1;

        while (size < N)
            size *= 2;

        return 2 * size + 1;
    }

    static void makingSegmentTree() {
        int treeSize = findTreeSize();
        tree = new int [treeSize]; // tree size 세팅 완료
        initialSaving(0, N - 1, 1);
    }

    static int initialSaving(int leftBoundOfNode, int rightBoundOfNode, int treeIdx) {
        // (1) 종료 조건
        if (leftBoundOfNode == rightBoundOfNode) {	// 종료 조건
            tree[treeIdx] = array[leftBoundOfNode];
            return tree[treeIdx];
        }

        // (2) (1)의 종료 조건에 해당될 때까지 쪼갠다.
        int mid = (leftBoundOfNode + rightBoundOfNode) / 2;
        int leftChild = initialSaving (leftBoundOfNode, mid, treeIdx * 2); // left, mid -1로 잡으면 에러
        int rightChild = initialSaving (mid + 1, rightBoundOfNode, treeIdx * 2 + 1);

        tree[treeIdx] = (leftChild < rightChild) ? leftChild : rightChild;
        return tree[treeIdx];
    }

    static int findSmallestValue(int leftIdxOfTargetArray, int rightIdxOfTargetArray, int leftBoundOfTreeNode, int rightBoundOfTreeNode, int treeIdx) {
        // (1) 현재 Tree Node의 범위에 포함되지 못하는 경우
        if (rightIdxOfTargetArray < leftBoundOfTreeNode || rightBoundOfTreeNode < leftIdxOfTargetArray) {
            return (1 << 30);
        }
        // (2) 현재 Tree Node의 범위에 포함되는 경우
        else if (leftIdxOfTargetArray <= leftBoundOfTreeNode & rightBoundOfTreeNode <= rightIdxOfTargetArray) {
            return tree[treeIdx];
        }
        // (3) (2)에 해당될 때까지 쪼갠다.
        else {
            int leftChild = findSmallestValue(leftIdxOfTargetArray, rightIdxOfTargetArray, leftBoundOfTreeNode, (leftBoundOfTreeNode + rightBoundOfTreeNode) / 2, 2 * treeIdx);
            int rightChild = findSmallestValue(leftIdxOfTargetArray, rightIdxOfTargetArray, (leftBoundOfTreeNode + rightBoundOfTreeNode) / 2 + 1, rightBoundOfTreeNode, 2 * treeIdx + 1);
            return leftChild < rightChild ? leftChild : rightChild;
        }
    }

    static int getKthValue(int start, int end, int kth, int target) {
        int left = 0, right = N - 1, mid = 0;
        int ret = 0;

        // 정렬된 sortedArray 에서 최소값 (target) 의 인덱스 찾기
        while (left <= right) {
            mid = (left + right) / 2;
            if (sortedArray[mid][0] < target) {
                left = mid + 1;
            }
            else if (sortedArray[mid][0] > target) {
                right = mid - 1;
            }
            else {
                break;
            }
        }

        // 최소값 (target)의 sortedArray 인덱스부터 탐색하면서, 기존 인덱스가 start, end 사이인지 확인
        for (int i = mid; i < N; ++i) {
            if (start <= sortedArray[i][1] && sortedArray[i][1] <= end) {
                kth--;

                if (kth == 0) {
                    ret = sortedArray[i][0];
                    break;
                }
            }
        }

        return ret;
    }

    public static void main(String[] args) throws IOException {
        int i, j, k;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); // 입력용
        StringTokenizer st = new StringTokenizer(br.readLine());// parsing 용
        StringBuilder sb = new StringBuilder();  // 출력용

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        array = new int[N];
        sortedArray = new int[N][2];

        st = new StringTokenizer(br.readLine());
        for (int n = 0; n < N; ++n) {
            array[n] = Integer.parseInt(st.nextToken());
            sortedArray[n][0] = array[n];				// value
            sortedArray[n][1] = n;						// index
        }

        Arrays.sort(sortedArray, (int x[], int y[]) -> x[0] - y[0]); // 2차원 배열 sorting

        makingSegmentTree();

        for (int m = 0; m < M; ++m) {
            st = new StringTokenizer(br.readLine());

            i = Integer.parseInt(st.nextToken()) - 1;
            j = Integer.parseInt(st.nextToken()) - 1;
            k = Integer.parseInt(st.nextToken());

            int smallestInTheArray = findSmallestValue(i, j, 0, N - 1, 1);
            int KthValue = getKthValue(i, j, k, smallestInTheArray);
            sb.append(KthValue).append("\n");
        }
        System.out.println(sb.toString());
        return;
    }
}
