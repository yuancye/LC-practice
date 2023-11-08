// 1293. Shortest Path in a Grid with Obstacles Elimination

// You are given an m x n integer matrix grid where each cell is either 0 (empty) or 1 (obstacle). You can move up, down, left, or right from and to an empty cell in one step.

// Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m - 1, n - 1) given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    int k;
    int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, { 0, -1}};

    public int shortestPath(int[][] grid, int k) {
       // move from current pos, until k == 0 if it is reach the end, record the steps. 
       // don't want to go back, how to record if the cell has been visited? -1
       // how to distinguish if it is an obstacle?
       // bfs?
        int m = grid.length;
        int n = grid[0].length;
        if (k >= m + n - 2) {
            return m + n - 2;
        }
        Queue<int[]> q = new LinkedList<>();
        boolean[][][] seen = new boolean[m][n][k+1];

        q.add(new int[]{0, 0, k, 0});
   

        while (!q.isEmpty()) {
            int[] pos = q.poll();
            int x = pos[0];
            int y = pos[1];
            int obstacle = pos[2];
            int curStep = pos[3];

            if (x == m-1 && y == n-1 && obstacle >= 0) {
                return curStep;
            }

            for (int[] dir : dirs) {
                obstacle = pos[2];
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (isValid(grid, newX, newY)) {
                    obstacle -= grid[newX][newY];
                    int[] curState = new int[]{newX, newY, obstacle, curStep + 1};
                    if (obstacle >= 0 && !seen[newX][newY][obstacle]) {    
                        seen[newX][newY][obstacle] = true;                   
                        q.offer(curState);
                    }
                }
            }
        }

        return -1;
    }

    public boolean isValid(int[][] grid, int x, int y) {
        return x >=0 && x < grid.length && y >= 0 && y < grid[0].length;
    }
}