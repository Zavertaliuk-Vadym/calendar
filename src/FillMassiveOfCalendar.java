import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

class FillMassiveOfCalendar {
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;

    static int[][] fillInCalendarArray(int[][] a, LocalDate localDate, DayOfWeek dayOfWeek) {

        int firstDayOfFirstWeekCurrentMonth = localDate.with(TemporalAdjusters.firstDayOfMonth()).getDayOfWeek().getValue();
        int firstDay = DayOfWeek.of(firstDayOfFirstWeekCurrentMonth).minus(dayOfWeek.getValue()).plus(1).getValue();
        int dayInMonth = localDate.lengthOfMonth();
        int number = 1;
        for (int i = firstDay; i <= DAYS_IN_WEEK; i++) {
            a[0][i - 1] = number;
            number++;
        }
        for (int i = 1; i < MAX_WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                a[i][j] = number;
                number++;
                if (number == dayInMonth + 1)
                    return a;
            }
        }
        return a;
    }
}
