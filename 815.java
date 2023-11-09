// 815. Bus Routes

// // The code is solving the problem of finding the least number of buses needed to travel from a
// source bus stop to a target bus stop. The input is an array of bus routes, where each route is
// represented by an array of bus stops. The code uses a breadth-first search (BFS) algorithm to
// find the shortest path from the source to the target. It builds a graph representation of the bus
// routes using a HashMap, where each bus stop is mapped to the routes that pass through it. Then,
// it performs a BFS starting from the source bus stop, exploring all possible routes and stops
// until it reaches the target bus stop. The code returns the minimum number of buses needed to
// reach the target, or -1 if it is not possible.

// You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.

// For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
// You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.

// Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.

 

// Example 1:

// Input: routes = [[1,2,7],[3,6,7]], source = 1, target = 6
// Output: 2
// Explanation: The best strategy is take the first bus to the bus stop 7, then take the second bus to the bus stop 6.
// Example 2:

// Input: routes = [[7,12],[4,5,15],[6],[15,19],[9,12,13]], source = 15, target = 12
// Output: -1

import java.util.*;

class Solution {
    public int numBusesToDestination(int[][] routes, int source, int target) {
        // sort 
        // find the target bus, check the bus stop,
        // find the sourse bus, check the bus stop, 
        if (source == target) {
            return 0;
        }

        HashMap<Integer, ArrayList<Integer>> adjList = new HashMap<>();
        // build the map
        for (int r = 0; r < routes.length; r++) {
            for (int stop : routes[r]) {
                ArrayList<Integer> route = adjList.getOrDefault(stop, new ArrayList<>());
                route.add(r);
                adjList.put(stop, route);             
            }
        }

        // BFS
        // find the source bus
        Queue<Integer> q = new LinkedList<>();
        Set<Integer> seen = new HashSet<Integer>();
        // add all the source to the q
        for (int route : adjList.get(source)) {
            q.add(route);
            seen.add(route);
        }

        //BFS
        int busCount = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                int route = q.poll();

                // iterate over the bus stop in this route
                for (int stop : routes[route]) {
                    if (stop == target) {
                        return busCount;
                    }

                    // iterate over the next possible routes from the current stop
                    for (int nextRoute : adjList.get(stop)) {
                        if (!seen.contains(nextRoute)) {
                            seen.add(nextRoute);
                            q.add(nextRoute);
                        }
                    }
                }
            }
            busCount++;
        }
        return -1;
    }
}


class Solution {
    List<List<Integer>> adjList = new ArrayList();

    // Iterate over each pair of routes and add an edge between them if there's a common stop.
    void createGraph(int[][] routes) {
        for (int i = 0; i < routes.length; i++) {
            for (int j = i + 1; j < routes.length; j++) {
                if (haveCommonNode(routes[i], routes[j])) {
                    adjList.get(i).add(j);
                    adjList.get(j).add(i);
                }
            }
        }
    }

    // Returns true if the provided routes have a common stop, false otherwise.
    boolean haveCommonNode(int[] route1, int[] route2) {
        int i = 0, j = 0;
        while (i < route1.length && j < route2.length) {
            if (route1[i] == route2[j]) {
                return true;
            }

            if (route1[i] < route2[j]) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }

    // Add all the routes in the queue that have the source as one of the stops.
    void addStartingNodes(Queue<Integer> q, int[][] routes, int source) {
        for (int i = 0; i < routes.length; i++) {
            if (isStopExist(routes[i], source)) {
                q.add(i);
            }
        }
    }

    // Returns true if the given stop is present in the route, false otherwise.
    boolean isStopExist(int[] route, int stop) {
        for (int j = 0; j < route.length; j++) {
            if (route[j] == stop) {
                return true;
            }
        }
        return false;
    }

    public int numBusesToDestination(int[][] routes, int source, int target) {
        if (source == target) {
            return 0;
        }

        for (int i = 0; i < routes.length; ++i) {
            Arrays.sort(routes[i]);
            adjList.add(new ArrayList());
        }

        createGraph(routes);

        Queue<Integer> q = new LinkedList<>();
        addStartingNodes(q, routes, source);

        Set<Integer> vis = new HashSet<Integer>(routes.length);
        int busCount = 1;
        while (!q.isEmpty()) {
            int sz = q.size();

            while (sz-- > 0) {
                int node = q.remove();

                // Return busCount, if the stop target is present in the current route.
                if (isStopExist(routes[node], target)) {
                    return busCount;
                }

                // Add the adjacent routes.
                for (int adj : adjList.get(node)) {
                    if (!vis.contains(adj)) {
                        vis.add(adj);
                        q.add(adj);
                    }
                }
            }

            busCount++;
        }

        return -1;
    }
};