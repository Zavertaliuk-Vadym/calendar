public class Main {
    public static void main(String[] args) {
        Print calendar =null;
        calendar= new PrintInConsole();
        calendar.print();
        Print print = new PrintInWeb();
        print.printInWeb();
    }

}
