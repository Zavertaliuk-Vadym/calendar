import java.time.DayOfWeek;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;


class PrintInConsole {
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String GREEN_TEXT_START_TOKEN = (char) 27 + "[36m";
    private static final String EXT_END_TOKEN = (char) 27 + "[0m";
    private static final String RED_TEXT_START_TOKEN = (char) 27 + "[31m";

    String printCalendarInConsole(List<DayOfWeek> weekends, DayOfWeek monthStartWithThisDate, int[][] massiveWithCalendar, int nowDay) {
        return printCalendarHeader(weekends, monthStartWithThisDate) +
                printCalendarArray(weekends, massiveWithCalendar, nowDay);
    }

    public static String printCalendarArray(List<DayOfWeek> weekends,
                                            int[][] massiveOfCalendar, int currentDay) {
        StringBuilder printerCalendarArray = new StringBuilder();
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                selectionOfDay(massiveOfCalendar[i][j], isCurrentDay(massiveOfCalendar[i][j],currentDay ),
                        weekends.contains(DayOfWeek.of(j+1)), printerCalendarArray);
            }
            printerCalendarArray.append("\n");
        }
        return printerCalendarArray.toString();
    }

    private static boolean isCurrentDay(int day, int currentDay) {
        return day == currentDay;
    }

    private static void selectionOfDay(int currentPosition, boolean currentDay, boolean weekends, StringBuilder printerCalendarArray) {
        if (currentPosition == 0) {
            printerCalendarArray.append(getFormat("%4s", ""));
            return;
        }
        if (currentDay)
            printerCalendarArray.append(getFormat(currentPosition, GREEN_TEXT_START_TOKEN + "%4d" + EXT_END_TOKEN));
        else if (weekends)
            printerCalendarArray.append(getFormat(currentPosition, RED_TEXT_START_TOKEN + "%4d" + EXT_END_TOKEN));
        else {
            printerCalendarArray.append(getFormat(currentPosition, "%4d"));
        }
    }

    private static String getFormat(int i, String format) {
        return String.format(format, i);
    }

    public static String printCalendarHeader(List<DayOfWeek> weekends, DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarHeader = new StringBuilder();
        for (int i = firstDaySelectedMonth.getValue(); i < DAYS_IN_WEEK+firstDaySelectedMonth.getValue(); i++) {
            selectionWeekends(weekends, printerCalendarHeader, i);
        }
        printerCalendarHeader.append("\n");
        return printerCalendarHeader.toString();
    }

    private static void selectionWeekends(List<DayOfWeek> weekends, StringBuilder printerCalendarHeader, int currentPosition) {
        if (weekends.contains(DayOfWeek.of(currentPosition))) {
            printerCalendarHeader.append(getFormat(RED_TEXT_START_TOKEN + "%4s" + EXT_END_TOKEN, getTypeOfInputCalendarHeader(currentPosition)));
        } else {
            printerCalendarHeader.append(getFormat("%4s", getTypeOfInputCalendarHeader(currentPosition)));

        }
    }

    private static String getFormat(String format, String typeOfInputCalendarHeader) {
        return String.format(format, typeOfInputCalendarHeader);
    }

    static String getTypeOfInputCalendarHeader(int i) {
        return WeekFields.of(Locale.ENGLISH)
                .getFirstDayOfWeek()
                .plus(i)
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toUpperCase();
    }
}
