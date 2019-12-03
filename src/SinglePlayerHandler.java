public class SinglePlayerHandler {
    public static void main(String[] args) {
        Board playerBoard = new Board();
        Board computerBoard = new Board();

        printBoardIndexing();




    }

    public static void printBoardIndexing() {
        System.out.println("\tA\tB\tC\tD\tE\tF\tG\tH\tI\tJ");
        for(int i = 1; i <= 10; i++) {
            String tabs = i + "";
            for(int j = 0; j < 10; j++) {
                tabs += "\t";
            }
            System.out.println(tabs);
        }
    }
}
