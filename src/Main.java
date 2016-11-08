public class Main {
    public static void main(String[] args) {
        Print calendar = null;
        calendar = new PrintInConsole();
        calendar.print(true);//'true' - in console
        calendar = new PrintInWeb();
        calendar.print(false);
    }

}
