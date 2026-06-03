import java.util.*;

public class Solution {

    /**
     * Returns true if a path exists from src to dst in the directed graph.
     * @param edges  array of [from, to] pairs representing directed edges
     * @param src    starting node
     * @param dst    destination node
     */
    public boolean hasPath(int[][] edges, int src, int dst) {
        // Your BFS solution goes here
        // Step 1: Build the graph as an adjacency list
        Map<Integer, List<Integer>> graph =  new HashMap<>();
 
        for(int[] edge : edges)
        {
            // List<Integer> neighbors = graph.get(egde[0]);
            // if(neighbors == null)
            // {
            //     neighbors = new ArrayList<>();
            //     graph.put(egde[0],neighbors)
            // }
            // neighbors.add(edge[1]);
            graph.computeIfAbsent(edge[0], list -> new ArrayList<>()).add(edge[1]);
        }

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> visited = new HashSet<>();

        queue.add(src);
        visited.add(src);

        while(!queue.isEmpty())
        {
            int node = queue.poll();
            if(node == dst) return true;

            for(int neighbor :  graph.getOrDefault(node, new ArrayList<>())) //iterating over a empty array is safe in case a node has no outgoing edge!
            {
                if(!visited.contains(neighbor))
                {
                    visited.add(neighbor);
                    queue.add(neighbor);
                }
            }
        }
        
        return false;
    }
}
