package BOJ;

import java.util.ArrayList;
import java.util.Scanner;

/*
Reviewed : 2023-08-01
*/

public class BOJ_1068_DFS_1 {
    // curNode 자식들의 leaf node 개수 저장 (curNode 본인 포함)
    static int dfs (int curNode, int[] leafCnt, ArrayList<Integer> graph[]) {
        if (graph[curNode].size() == 0) {
            return leafCnt[curNode] = 1;
        } else {
            for (int s = 0; s < graph[curNode].size(); ++s) {
                leafCnt[curNode] += dfs(graph[curNode].get(s), leafCnt, graph);
            }
            return leafCnt[curNode];
        }
    }

    public static void main(String[] args) {
        // (1) 변수 및 Input =========================================
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int rootNode = 0;
        int deleteTargetNode = 0;
        int answer = 0;

        // parentNodeOf[n] : n의 부모 노드 저장, graph[x] : x의 자식 노드 저장
        ArrayList<Integer> graph[] = new ArrayList[N];
        int[] parentNodeOf = new int[N];
        int[] leafCnt = new int[N];

        for (int n = 0; n < N; ++n) {
            graph[n] = new ArrayList();
        }

        for (int n = 0; n < N; ++n) {
            int parent = sc.nextInt();
            parentNodeOf[n] = parent;

            if (parent == -1) {
                rootNode = n;
            } else {
                graph[parent].add(n);
            }
        }
        deleteTargetNode = sc.nextInt();

        // (2) DFS =========================================
        dfs(rootNode, leafCnt, graph);
        answer = leafCnt[rootNode];

        // (3) 삭제 =========================================
        // root 노드인 경우
        if (parentNodeOf[deleteTargetNode] == -1) {
            answer = 0;
        } else {
            // 삭제 노드의 leaf 개수 차감
            answer -= leafCnt[deleteTargetNode];
            leafCnt[parentNodeOf[deleteTargetNode]] -= leafCnt[deleteTargetNode];

            // 삭제 노드의 부모 노드가 leaf가 되는 경우
            if (leafCnt[parentNodeOf[deleteTargetNode]] == 0) {
                leafCnt[parentNodeOf[deleteTargetNode]] = 1;
                answer++;
            }
        }
        System.out.println(answer);
        return;
    }
}
