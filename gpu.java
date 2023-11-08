import java.util.*;

public class GPUSort {

   public static long getMinCost(int[] cost, int[] compatible1, int[] compatible2, int min_compatible) {
      int n = cost.length;
      long minCost = 0;

      // Create a list of (cost, index) pairs to sort the GPUs by cost
      List<int[]> gpuList = new ArrayList<>();
      for (int i = 0; i < n; i++) {
         gpuList.add(new int[] {cost[i], i});
      }
      
      // Sort the GPUs by cost in ascending order
      Collections.sort(gpuList, (a, b) -> Integer.compare(a[0], b[0]));

      // separate them to three price list, one for each cluster, another for common
      ArrayList<Integer> price1 = new ArrayList<>();
      ArrayList<Integer> price2 = new ArrayList<>();
      ArrayList<Integer> common = new ArrayList<>();
      for (int i = 0; i < n; i++) {
         int index = gpuList.get(i)[1];
         int price = gpuList.get(i)[0];
         if ((compatible1[index] == 1) && (compatible2[index] == 1)) {
            common.add(price);
         } else if (compatible1[index] == 1) {
            price1.add(price);
         } else if (compatible2[index] == 1) {
            price2.add(price);
         }
      }

      // not enought GPU to fill
      if (price1.size() + common.size() < min_compatible || price2.size() + common.size() < min_compatible) {
         return -1;
      }

      // use common first
      int size = price1.size() > price2.size() ? price2.size() : price1.size();
      int residue = min_compatible - size;
      if (residue > 0) {
         for (int i = 0; i < residue; i++) {
            minCost += common.get(i);
         }
      }

      int leftGPU = min_compatible - residue;
      // for the uncommon GPU, need to check if the total of single price is more or less than the next common.
      for (int i = 0; i < leftGPU; i++) {
         int singleTotalCost = price1.get(i) + price2.get(i);
         // if there is common, and the common cost is less
         if ((residue + i) < common.size() && singleTotalCost > common.get(residue + i)) {
            minCost += common.get(residue + i); 
         } else {
            minCost += singleTotalCost;
         }
      }

      return minCost;
   }

   public static void main(String[] args) {
      test1();
      test2();
      test3();

  }

   private static void test1() {
      int[] cost = {2, 4, 6, 5};
      int[] compatible1 = {1, 1, 1, 0};
      int[] compatible2 = {0, 0, 1, 1};
      int min_compatible = 2;

      long result = getMinCost(cost, compatible1, compatible2, min_compatible);
      System.out.println(result);  // Output should be 13
   }
   private static void test2() {
      int[] cost = {2, 4, 6, 5, 6};
      int[] compatible1 = {1, 1, 1, 1, 0};
      int[] compatible2 = {0, 0, 0, 1, 0};
      int min_compatible = 2;
      long result = getMinCost(cost, compatible1, compatible2, min_compatible);
      System.out.println(result);  // Output should be -1
   }
   private static void test3() {
      int[] cost = {2, 4, 6, 5};
      int[] compatible1 = {1, 1, 1, 1};
      int[] compatible2 = {0, 0, 1, 1};
      int min_compatible = 2;
      long result = getMinCost(cost, compatible1, compatible2, min_compatible);
      System.out.println(result);  // Output should be 11
   }

}







