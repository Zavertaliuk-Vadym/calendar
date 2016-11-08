public class Main {
    public static void main(String[] args) {
        Print calendar =null;
        calendar= new PrintInConsole();
        calendar.print();
    }
//    private static LocalDate getDate(String[] args) {
//        LocalDate today = LocalDate.now();
//        if (args.length > 0) {
//            try {
//                int year = Integer.parseInt(args[0]);
//                int month = Integer.parseInt(args[1]);
//                int day = Integer.parseInt(args[2]);
//
//                return LocalDate.of(year, Month.of(month), day);
//            } catch (Exception e) {
//                System.out.println("Pas specific date in arguments using following format: YYYY MM DD");
//                System.exit(1);
//            }
//        }
//        return today;
//    }
}
