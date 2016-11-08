import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Calendar {
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private YearMonth month;
    private LocalDate today;
    private DayOfWeek dayOfWeek;
    private List<DayOfWeek> weekends;
    private Locale locale;
    private int[][] massiveWithCalendar = new int[MAX_WEEKS_IN_MONTH][DAYS_IN_WEEK];

    Calendar() {
        this(LocalDate.now());
    }

    Calendar(LocalDate today) {
        this(YearMonth.now(), today);
    }

   Calendar(YearMonth month, LocalDate today) {
        this.month = month;
        this.today = today;
        dayOfWeek=DayOfWeek.THURSDAY;
        weekends= new ArrayList<>();
        weekends.add(DayOfWeek.THURSDAY);
        weekends.add(DayOfWeek.SUNDAY);
        locale=Locale.getDefault();
    }
    void print(){
        PrintInConsole printInConsole =  new PrintInConsole();
        FillMassiveOfCalendar.fillInCalendarArray(massiveWithCalendar, today,dayOfWeek);
        System.out.println(printInConsole.printCalendarInConsole(weekends, dayOfWeek, massiveWithCalendar, today));
    }

    void printInWeb(){
        PrintInWeb printInWeb = new PrintInWeb();
        int monthLength = today.lengthOfMonth();
        int firstDayInMonth = today.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek().getValue();
        FillMassiveOfCalendar.fillInCalendarArray(massiveWithCalendar, today,dayOfWeek);
        try (PrintWriter printWriter = new PrintWriter("text3.html")) {
            printWriter.println(printInWeb.printCalendarInWeb(weekends, dayOfWeek, massiveWithCalendar, today));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static LocalDate getDate(String[] args) {
        LocalDate today = LocalDate.now();
        if (args.length > 0) {
            try {
                int year = Integer.parseInt(args[0]);
                int month = Integer.parseInt(args[1]);
                int day = Integer.parseInt(args[2]);

                return LocalDate.of(year, Month.of(month), day);
            } catch (Exception e) {
                System.out.println("Pas specific date in arguments using following format: YYYY MM DD");
                System.exit(1);
            }
        }
        return today;
    }
}