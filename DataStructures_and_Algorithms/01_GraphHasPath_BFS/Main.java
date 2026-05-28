public class Main {

    static int passed = 0;
    static int failed = 0;

    public static void main(String[] args) {
        Solution sol = new Solution();

        // --------------------------------------------------
        // Test 1: Postoji direktan put
        // --------------------------------------------------
        int[][] edges1 = {{0,1},{1,2},{2,3}};
        check("Test 1 — postoji put 0->1->2->3",
              sol.hasPath(edges1, 0, 3), true);

        // --------------------------------------------------
        // Test 2: Nema puta (suprotni smer)
        // --------------------------------------------------
        int[][] edges2 = {{0,1},{1,2},{2,3}};
        check("Test 2 — nema puta 3->0 (usmereni graf)",
              sol.hasPath(edges2, 3, 0), false);

        // --------------------------------------------------
        // Test 3: Vise puteva do istog cilja
        // --------------------------------------------------
        int[][] edges3 = {{0,1},{0,2},{1,3},{2,3}};
        check("Test 3 — dva puta do 3 (0->1->3 i 0->2->3)",
              sol.hasPath(edges3, 0, 3), true);

        // --------------------------------------------------
        // Test 4: Src == Dst
        // --------------------------------------------------
        int[][] edges4 = {{0,1},{1,2}};
        check("Test 4 — src == dst (0 == 0)",
              sol.hasPath(edges4, 0, 0), true);

        // --------------------------------------------------
        // Test 5: Prazan graf
        // --------------------------------------------------
        int[][] edges5 = {};
        check("Test 5 — prazan graf, src != dst",
              sol.hasPath(edges5, 0, 1), false);

        // --------------------------------------------------
        // Test 6: Izolovani cvor (nema izlaznih grana)
        // --------------------------------------------------
        int[][] edges6 = {{0,1},{1,2}};
        check("Test 6 — cvor 3 je izolovan",
              sol.hasPath(edges6, 0, 3), false);

        // --------------------------------------------------
        System.out.println("==================");
        System.out.println("Proslo:  " + passed);
        System.out.println("Palo:    " + failed);
        System.out.println("==================");
        if (failed == 0) System.out.println("Svi testovi prosli!");
        else             System.out.println("Ima gresaka — proveri!");
    }

    static void check(String name, boolean actual, boolean expected) {
        if (actual == expected) {
            System.out.println("PASS  " + name);
            passed++;
        } else {
            System.out.println("FAIL  " + name);
            System.out.println("      Ocekivano: " + expected + "  Dobijeno: " + actual);
            failed++;
        }
    }
}
