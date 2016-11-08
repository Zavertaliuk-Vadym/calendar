import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

abstract class Print {
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String GREEN_TEXT_START_TOKEN = (char) 27 + "[36m";
    private static final String EXT_END_TOKEN = (char) 27 + "[0m";
    private static final String RED_TEXT_START_TOKEN = (char) 27 + "[31m";
    private static final String OPEN_TAG_TABLE_ROW = "<tr>";
    private static final String CLOSE_TAG_TABLE_ROW = "</tr>";

    private YearMonth month;
    private LocalDate today;
    private DayOfWeek dayOfWeek;
    private List<DayOfWeek> weekends;
    private Locale locale;
    private int[][] massiveWithCalendar = new int[MAX_WEEKS_IN_MONTH][DAYS_IN_WEEK];

    Print() {
        this(LocalDate.now());
    }

    private Print(LocalDate today) {
        this(YearMonth.now(), today);
    }

    private Print(YearMonth month, LocalDate today) {
        this.month = month;
        this.today = today;
        dayOfWeek = DayOfWeek.SUNDAY;
        weekends = new ArrayList<>();
        weekends.add(DayOfWeek.SATURDAY);
        weekends.add(DayOfWeek.SUNDAY);
        locale = Locale.getDefault();
        massiveWithCalendar = FillMassiveOfCalendar.fillInCalendarArray(massiveWithCalendar, today, dayOfWeek);
    }

    void print() {
        System.out.println(print(weekends, dayOfWeek, massiveWithCalendar, today));
    }

    void printInWeb() {
        PrintInWeb printInWeb = new PrintInWeb();
        try (PrintWriter printWriter = new PrintWriter("text3.html")) {
            printWriter.println(printInWeb.print(weekends, dayOfWeek, massiveWithCalendar, today));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String print(List<DayOfWeek> weekends, DayOfWeek monthStartWithThisDate, int[][] massiveWithCalendar, LocalDate nowDay) {
        return printCalendarHeader(weekends, monthStartWithThisDate) +
                printCalendarArray(weekends, massiveWithCalendar, nowDay, monthStartWithThisDate);

    }

    private String printCalendarHeader(List<DayOfWeek> weekends, DayOfWeek firstDaySelectedMonth) {
        StringBuilder days = new StringBuilder();
        int firstDay = firstDaySelectedMonth.getValue();
        DayOfWeek thisDay = DayOfWeek.of(firstDay);
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            days.append(getWeekend(weekends, thisDay));
            thisDay = thisDay.plus(1);
        }
        days.append("\n");
        return days.toString();
    }

    abstract String getWeekend(List<DayOfWeek> weekends, DayOfWeek dayOfWeek);

    private String printCalendarArray(List<DayOfWeek> weekends, int[][] massiveOfCalendar,
                                      LocalDate currentDay, DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarArray = new StringBuilder();
        DayOfWeek thisDay = DayOfWeek.of(firstDaySelectedMonth.getValue());
        int nowDay = currentDay.getDayOfMonth();
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                selectionOfDay(massiveOfCalendar[i][j], isCurrentDay(massiveOfCalendar[i][j], nowDay),
                        weekends.contains(thisDay), printerCalendarArray);
                thisDay = thisDay.plus(1);
            }
            printerCalendarArray.append("\n");
        }
        return printerCalendarArray.toString();
    }

    static boolean isCurrentDay(int day, int currentDay) {
        return day == currentDay;
    }

    abstract void selectionOfDay(int currentPosition, boolean currentDay, boolean weekends, StringBuilder printerCalendarArray);

    static String getFormat(int i, String format) {
        return String.format(format, i);
    }

    static String getFormat(String format, String typeOfInputCalendarHeader) {
        return String.format(format, typeOfInputCalendarHeader);
    }

    static String getTypeOfInputCalendarHeader(DayOfWeek dayOfWeek) {
        return dayOfWeek
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toUpperCase();
    }
}