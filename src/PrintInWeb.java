import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

class PrintInWeb extends Print{
    private static final String WEEKEND_TEXT_START_TOKEN_IN_WEB = "<td class=\"weekend\">";
    private static final String TEXT_END_TOKEN_IN_WEB = "</td>";
    private static final String CURRENT_DAY_TEXT_START_TOKEN_IN_WEB = "<td class=\"currentDay\">";
    private static final String OPEN_TAG_TABLE_COLUMN = "<td>";
    private static final String CLOSE_TAG_TABLE_COLUMN = "</td>";
    private static final String OPEN_TAG_TABLE_ROW = "<tr>";
    private static final String CLOSE_TAG_TABLE_ROW = "</tr>";
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;

    String print(List<DayOfWeek> weekends, DayOfWeek monthStartWithThisDate, int[][] massiveWithCalendar, LocalDate nowDay) {
        return printHeaderHTML()+
                printCalendarHeaderInWeb(weekends, monthStartWithThisDate) +
                printCalendarArrayInWeb(weekends, massiveWithCalendar, nowDay,monthStartWithThisDate)+
                printDownHTML();
    }

    String printCalendarHeaderInWeb(List<DayOfWeek> weekends, DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarHeader = new StringBuilder();
        printerCalendarHeader.append(OPEN_TAG_TABLE_ROW);
        DayOfWeek thisDay = DayOfWeek.of(firstDaySelectedMonth.getValue());
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            printerCalendarHeader.append(getWeekend(weekends, thisDay));
            thisDay = thisDay.plus(1);
        }
        printerCalendarHeader.append(CLOSE_TAG_TABLE_ROW);
        return printerCalendarHeader.toString();
    }

    @Override
    String getWeekend(List<DayOfWeek> weekends, DayOfWeek dayOfWeek) {
        if (weekends.contains(dayOfWeek)) {
            return getFormat(WEEKEND_TEXT_START_TOKEN_IN_WEB + "%4s" + TEXT_END_TOKEN_IN_WEB,getTypeOfInputCalendarHeader(dayOfWeek));
        } else {
            return getFormat(OPEN_TAG_TABLE_COLUMN+"%4s"+CLOSE_TAG_TABLE_COLUMN,getTypeOfInputCalendarHeader(dayOfWeek));

        }
    }

    @Override
    void selectionOfDay(int currentPosition, boolean currentDay, boolean weekends, StringBuilder printerCalendarArray) {
        if (Print.isCurrentDay(currentPosition, 0)) {
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

    private String printCalendarArrayInWeb(List<DayOfWeek> weekends,
                                            int[][] massiveOfCalendar, LocalDate currentDay,DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarArray = new StringBuilder();
        int nowDay = currentDay.getDayOfMonth();
        DayOfWeek thisDay = DayOfWeek.of(firstDaySelectedMonth.getValue());
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            printerCalendarArray.append(OPEN_TAG_TABLE_ROW);
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                selectionOfDay(massiveOfCalendar[i][j], isCurrentDay(massiveOfCalendar[i][j],nowDay ),
                        weekends.contains(thisDay), printerCalendarArray);
                thisDay = thisDay.plus(1);
            }
            printerCalendarArray.append(CLOSE_TAG_TABLE_ROW);
            printerCalendarArray.append("\n");
        }
        return printerCalendarArray.toString();
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