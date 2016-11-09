import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import print.Print;
import print.PrintInConsole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

/**
 * Created by employee on 11/2/16.
 */

public class CalendarTest {
    private static final int DAYS_IN_WEEK = 7;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static final String RED_TEXT_START_TOKEN = (char) 27 + "[31m";
    private static final String GREEN_TEXT_START_TOKEN = (char) 27 + "[32m";
    private static final String EXT_END_TOKEN = (char) 27 + "[0m";

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));


    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
        System.setErr(null);
    }

    @Test
    public void ColorOfCurrentDay() throws IOException {
        StringBuilder expected = new StringBuilder();
        StringBuilder expected1 = new StringBuilder();
        LocalDate today = LocalDate.now();
        int weekStartWithThisDayInt = 0;
        Print print = new PrintInConsole();
        List<Integer> weekends = new ArrayList<>();
        weekends.add(DayOfWeek.MONDAY.minus(1).getValue());
        int dayNow = 9;
        int[][] a = new int[6][7];
        int counter = 0;
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                a[i][j] = counter;
            }
        }
        for (int i = 0; i < MAX_WEEKS_IN_MONTH; i++) {
            for (int j = 0; j < DAYS_IN_WEEK; j++) {
                if (a[i][j] == 0) {
                    expected.append("    ");
                    continue;
                }
                if (a[i][j] == dayNow)
                    expected.append(String.format(GREEN_TEXT_START_TOKEN + "%4d" + EXT_END_TOKEN, a[i][j]));
                else if (weekends.contains(j))
                    expected.append(String.format(RED_TEXT_START_TOKEN + "%4d" + EXT_END_TOKEN, a[i][j]));
                else {
                    expected.append(String.format("%4d", a[i][j]));
                }
            }
            expected.append("\n");
        }
        expected.append("\n");

        System.out.println(print.print());

        assertThat(outContent.toString(), containsString(String.format(" Present day : %10s\n", today)));
//        assertThat(expected.toString(), equalTo(outContent.toString()));
    }


    @Test
    public void CalendarHeader() {
        Print print = new PrintInConsole();
        StringBuilder expected = new StringBuilder();
        List<Integer> weekends = new ArrayList<>();
        weekends.add(DayOfWeek.SUNDAY.getValue());
        weekends.add(DayOfWeek.SATURDAY.getValue());
        for (int i = 1; i <= DAYS_IN_WEEK; i++) {
            if (weekends.contains(i)) {
                expected.append(String.format(RED_TEXT_START_TOKEN + "%4s" + EXT_END_TOKEN, WeekFields.of(Locale.ENGLISH)
                        .getFirstDayOfWeek()
                        .plus(i)
                        .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        .toUpperCase()));
            } else {
                expected.append(String.format("%4s", WeekFields.of(Locale.ENGLISH)
                        .getFirstDayOfWeek()
                        .plus(i)
                        .getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
                        .toUpperCase()));

            }
        }
        expected.append("\n\n");

        System.out.println(print.printCalendarHeader());


        assertThat(outContent.toString(), equalTo(expected.toString()));
    }
}