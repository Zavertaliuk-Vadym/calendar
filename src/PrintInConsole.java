import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;


class PrintInConsole {
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String GREEN_TEXT_START_TOKEN = (char) 27 + "[36m";
    private static final String EXT_END_TOKEN = (char) 27 + "[0m";
    private static final String RED_TEXT_START_TOKEN = (char) 27 + "[31m";

    String printCalendarInConsole(List<DayOfWeek> weekends, DayOfWeek monthStartWithThisDate, int[][] massiveWithCalendar, LocalDate nowDay) {
        return printCalendarHeader(weekends, monthStartWithThisDate) +
                printCalendarArray(weekends, massiveWithCalendar, nowDay,monthStartWithThisDate);
    }

    private String printCalendarHeader(List<DayOfWeek> weekends, DayOfWeek firstDaySelectedMonth) {
        StringBuilder days = new StringBuilder();
        int firstDay=firstDaySelectedMonth.getValue();
        DayOfWeek thisDay = DayOfWeek.of(firstDay);
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            days.append(selectionWeekends(weekends, thisDay));
            thisDay = thisDay.plus(1);
        }
        days.append("\n");
        return days.toString();
    }

    private String selectionWeekends(List<DayOfWeek> weekends, DayOfWeek dayOfWeek) {
        if (weekends.contains(dayOfWeek)) {
            return getFormat(RED_TEXT_START_TOKEN + "%4s" + EXT_END_TOKEN, getTypeOfInputCalendarHeader(dayOfWeek));
        } else {
            return getFormat("%4s", getTypeOfInputCalendarHeader(dayOfWeek));

        }
    }

    private String printCalendarArray(List<DayOfWeek> weekends, int[][] massiveOfCalendar,
                                      LocalDate currentDay,DayOfWeek firstDaySelectedMonth) {
        StringBuilder printerCalendarArray = new StringBuilder();
        int thisDay = firstDaySelectedMonth.getValue();
        int nowDay = currentDay.getDayOfMonth();
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                selectionOfDay(massiveOfCalendar[i][j], isCurrentDay(massiveOfCalendar[i][j],nowDay),
                        weekends.contains(DayOfWeek.of(j+1).minus(thisDay)), printerCalendarArray);
            }
            printerCalendarArray.append("\n");
        }
        return printerCalendarArray.toString();
    }

    private boolean isCurrentDay(int day, int currentDay) {
        return day == currentDay;
    }

    private void selectionOfDay(int currentPosition, boolean currentDay, boolean weekends, StringBuilder printerCalendarArray) {
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

    private String getFormat(int i, String format) {
        return String.format(format, i);
    }

    private String getFormat(String format, String typeOfInputCalendarHeader) {
        return String.format(format, typeOfInputCalendarHeader);
    }

    private String getTypeOfInputCalendarHeader(DayOfWeek dayOfWeek) {
        return dayOfWeek
                .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                .toUpperCase();
    }
}
