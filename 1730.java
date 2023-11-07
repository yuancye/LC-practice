// 1730. Shortest Path to Get Food

// You are starving and you want to eat food as quickly as possible. You want to find the shortest path to arrive at any food cell.

// You are given an m x n character matrix, grid, of these different types of cells:

// '*' is your location. There is exactly one '*' cell.
// '#' is a food cell. There may be multiple food cells.
// 'O' is free space, and you can travel through these cells.
// 'X' is an obstacle, and you cannot travel through these cells.
// You can travel to any adjacent cell north, east, south, or west of your current location if there is not an obstacle.

// Return the length of the shortest path for you to reach any food cell. If there is no path for you to reach food, return -1.

import java.util.LinkedList;
import java.util.Queue;

class Solution {
    int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
    public int getFood(char[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        boolean[][] visited = new boolean[m][n];
        // save the location, x,y coordinates
        Queue<int[]> q = new LinkedList<>();
        q.add(findStart(grid));
        
        int step = 0;

        while(!q.isEmpty()) {
            // bfs
            int len =  q.size();
            for (int i = 0; i < len; i++) {
                int[] pos = q.poll();
                int x = pos[0];
                int y = pos[1];
                // if it is the target?
                if (grid[x][y] == '#') {
                    return step;
                }
                // check other directions
                for (int[] dir : dirs) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    if (isValid(grid, newX, newY) && !visited[newX][newY]) {
                        visited[newX][newY] = true;
                        q.offer(new int[]{newX, newY});
                    }
                }
            }
            step++;           
        }
        return -1;
    }

    public int[] findStart(char[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '*') {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 0};
    }

    public boolean isValid(char[][] grid, int x, int y) {
        return x >=0 && x< grid.length && y >=0 && y < grid[0].length && grid[x][y] != 'X';
    }
}
