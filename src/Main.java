import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Month;

public class Main {
    public static void main(String[] args) {
        Print calendar = null;
        calendar = new PrintInConsole();
        calendar.setToday(LocalDate.of(1996, Month.JULY, 4));
        calendar.print(true);//'true' - in console
        Print calendar1 = new PrintInWeb();
        File file = new File("text.html");
        try(PrintWriter printWriter = new PrintWriter(file);) {
            printWriter.print(calendar.print(false));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println(calendar.print(false));
        calendar1.setToday(LocalDate.of(2016, Month.NOVEMBER, 8));
        calendar1.print(false);
    }
}
