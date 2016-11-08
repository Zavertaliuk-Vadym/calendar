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
    private int[][] massiveOfCalendar = new int[MAX_WEEKS_IN_MONTH][DAYS_IN_WEEK];

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
        massiveOfCalendar = FillMassiveOfCalendar.fillInCalendarArray(massiveOfCalendar, today, dayOfWeek);
    }

    void print(boolean bool) {
        if (bool) {
            System.out.println(printCalendarHeader() + printCalendarArray());
        } else {
            try (PrintWriter printWriter = new PrintWriter("calendar.html")) {
                printWriter.println(printHeaderHTML() + printCalendarHeader() + printCalendarArray() + printDownHTML());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    private String printCalendarHeader() {
        StringBuilder days = new StringBuilder();
        int firstDay = dayOfWeek.getValue();
        DayOfWeek thisDay = DayOfWeek.of(firstDay);
        days.append(startOfStringHeader());                                                ///!!!!!
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            days.append(getWeekend(weekends, thisDay));
            thisDay = thisDay.plus(1);
        }
        days.append(endOfStringHeader());
        return days.toString();
    }

    abstract String endOfStringHeader();

    abstract String startOfStringHeader();

    abstract String endOfStringBody();

    abstract String startOfStringBody();

    abstract String getWeekend(List<DayOfWeek> weekends, DayOfWeek dayOfWeek);

    abstract String selectionOfDay(int currentPosition, boolean currentDay, boolean weekends);

    private String printCalendarArray() {
        StringBuilder printerCalendarArray = new StringBuilder();
        DayOfWeek thisDay = DayOfWeek.of(dayOfWeek.getValue());
        int nowDay = today.getDayOfMonth();
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            printerCalendarArray.append(startOfStringBody());
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                printerCalendarArray.append(selectionOfDay(massiveOfCalendar[i][j], isCurrentDay(massiveOfCalendar[i][j], nowDay),
                        weekends.contains(thisDay)));
                thisDay = thisDay.plus(1);
            }
            printerCalendarArray.append(endOfStringBody());
        }
        return printerCalendarArray.toString();
    }

    static boolean isCurrentDay(int day, int currentDay) {
        return day == currentDay;
    }

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


    private static String printHeaderHTML() {

        return "<Html>\n" +
                "<head>\n" +
                "<style>\n" +
                "       td.weekend{\n" +
                "           color: red;\n" +
                "       }\n" +
                "       td.currentDay{\n" +
                "           color: green;\n" +
                "       }\n" +
                "       td{\n" +
                "           padding:5px;\n" +
                "       }\n" +
                "   </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "<table>";
    }

    private static String printDownHTML() {

        return "</table>\n" +
                "</body>\n" +
                "</Html>";
    }
}