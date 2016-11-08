import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;

class PrintInWeb {
    private static final String WEEKEND_TEXT_START_TOKEN_IN_WEB = "<td class=\"weekend\">";
    private static final String TEXT_END_TOKEN_IN_WEB = "</td>";
    private static final String CURRENT_DAY_TEXT_START_TOKEN_IN_WEB = "<td class=\"currentDay\">";
    private static final String OPEN_TAG_TABLE_COLUMN = "<td>";
    private static final String CLOSE_TAG_TABLE_COLUMN = "</td>";
    private static final String OPEN_TAG_TABLE_ROW = "<tr>";
    private static final String CLOSE_TAG_TABLE_ROW = "</tr>";
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;

    static String printCalendarInWeb(List<DayOfWeek> weekends, DayOfWeek monthStartWithThisDate, int[][] massiveWithCalendar, LocalDate nowDay) {
        return printHeaderHTML()+
                printCalendarHeaderInWeb(weekends, monthStartWithThisDate) +
                printCalendarArrayInWeb(weekends, massiveWithCalendar, nowDay,monthStartWithThisDate)+
                printDownHTML();
    }

    private static String printCalendarHeaderInWeb(List<DayOfWeek> weekends, DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarHeader = new StringBuilder();
        printerCalendarHeader.append(OPEN_TAG_TABLE_ROW);
        DayOfWeek thisDay = DayOfWeek.of(firstDaySelectedMonth.getValue());
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            selectionWeekends(weekends, printerCalendarHeader, thisDay);
            thisDay = thisDay.plus(1);
        }
        printerCalendarHeader.append(CLOSE_TAG_TABLE_ROW);
        return printerCalendarHeader.toString();
    }

    private static String printCalendarArrayInWeb(List<DayOfWeek> weekends,
                                            int[][] massiveOfCalendar, LocalDate currentDay,DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarArray = new StringBuilder();
        int thisDay = firstDaySelectedMonth.getValue();
        int nowDay = currentDay.getDayOfMonth();
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            printerCalendarArray.append(OPEN_TAG_TABLE_ROW);
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                selectionOfDay(massiveOfCalendar[i][j], isCurrentDay(massiveOfCalendar[i][j],nowDay ),
                        weekends.contains(DayOfWeek.of(j+1).minus(thisDay)), printerCalendarArray);
            }
            printerCalendarArray.append(CLOSE_TAG_TABLE_ROW);
            printerCalendarArray.append("\n");
        }
        return printerCalendarArray.toString();
    }

    private static void selectionOfDay(int currentPosition, boolean currentDay, boolean weekends, StringBuilder printerCalendarArray) {
        if (currentPosition == 0) {
            printerCalendarArray.append(getFormat(OPEN_TAG_TABLE_COLUMN+"%4s"+CLOSE_TAG_TABLE_COLUMN, "    "));
            return;
        }
        if (currentDay)
            printerCalendarArray.append(getFormat(currentPosition, CURRENT_DAY_TEXT_START_TOKEN_IN_WEB + "%4d" + TEXT_END_TOKEN_IN_WEB));
        else if (weekends)
            printerCalendarArray.append(getFormat(currentPosition, WEEKEND_TEXT_START_TOKEN_IN_WEB + "%4d" + TEXT_END_TOKEN_IN_WEB));
        else {
            printerCalendarArray.append(getFormat(currentPosition, OPEN_TAG_TABLE_COLUMN+"%4s"+CLOSE_TAG_TABLE_COLUMN));
        }
    }

    private static void selectionWeekends(List<DayOfWeek> weekends, StringBuilder printerCalendarHeader, DayOfWeek currentPosition) {

        if (weekends.contains(currentPosition)) {
            printerCalendarHeader.append(getFormat(WEEKEND_TEXT_START_TOKEN_IN_WEB + "%4s" + TEXT_END_TOKEN_IN_WEB, getTypeOfInputCalendarHeader(currentPosition)));
        } else {
            printerCalendarHeader.append(getFormat(OPEN_TAG_TABLE_COLUMN+"%4s"+CLOSE_TAG_TABLE_COLUMN, getTypeOfInputCalendarHeader(currentPosition)));

        }
    }

    private static boolean isCurrentDay(int day, int currentDay) {
        return day == currentDay;
    }

    private static String getFormat(int i, String format) {
        return String.format(format, i);
    }

    private static String getFormat(String format, String typeOfInputCalendarHeader) {
        return String.format(format, typeOfInputCalendarHeader);
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

    private static String getTypeOfInputCalendarHeader(DayOfWeek dayOfWeek) {
        return dayOfWeek
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toUpperCase();
    }
}