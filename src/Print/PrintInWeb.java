package Print;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class PrintInWeb extends Print {
    private static final String WEEKEND_TEXT_START_TOKEN_IN_WEB = "<td class=\"weekend\">";
    private static final String OPEN_TAG_TABLE_COLUMN = "<td>";
    private static final String TEXT_END_TOKEN_IN_WEB = "</td>";
    private static final String CURRENT_DAY_TEXT_START_TOKEN_IN_WEB = "<td class=\"currentDay\">";
    private static final String OPEN_TAG_TABLE_ROW = "<tr>";
    private static final String CLOSE_TAG_TABLE_ROW = "</tr>";

    @Override
    String printHeader(LocalDate today) {
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
                String.format("  Present day : %10s\n", today) +
                "<table>";
    }

    @Override
    String printFooter() {
        return "</table>\n" +
                "</body>\n" +
                "</Html>";
    }

    @Override
    String startOfStringHeader() {
        return OPEN_TAG_TABLE_ROW;
    }

    @Override
    String startOfStringBody() {
        return CLOSE_TAG_TABLE_ROW;
    }

    @Override
    String endOfStringBody() {
        return OPEN_TAG_TABLE_ROW;
    }

    @Override
    String endOfStringHeader() {
        return CLOSE_TAG_TABLE_ROW;
    }

    @Override
    String getWeekend(List<DayOfWeek> weekends, DayOfWeek dayOfWeek) {
        if (weekends.contains(dayOfWeek)) {
            return getFormat(WEEKEND_TEXT_START_TOKEN_IN_WEB + "%4s" + TEXT_END_TOKEN_IN_WEB, getTypeOfInputCalendarHeader(dayOfWeek));
        } else {
            return getFormat(OPEN_TAG_TABLE_COLUMN + "%4s" + TEXT_END_TOKEN_IN_WEB, getTypeOfInputCalendarHeader(dayOfWeek));
        }
    }

    @Override
    String selectionOfDay(int currentPosition, boolean currentDay, boolean weekends) {
        if (isCurrentDay(currentPosition, 0)) {
            return getFormat(OPEN_TAG_TABLE_COLUMN + "%4s" + TEXT_END_TOKEN_IN_WEB, "    ");
        }
        if (currentDay)
            return getFormat(currentPosition, CURRENT_DAY_TEXT_START_TOKEN_IN_WEB + "%4d" + TEXT_END_TOKEN_IN_WEB);
        else if (weekends)
            return getFormat(currentPosition, WEEKEND_TEXT_START_TOKEN_IN_WEB + "%4d" + TEXT_END_TOKEN_IN_WEB);
        else {
            return getFormat(currentPosition, OPEN_TAG_TABLE_COLUMN + "%4s" + TEXT_END_TOKEN_IN_WEB);
        }

    }
}