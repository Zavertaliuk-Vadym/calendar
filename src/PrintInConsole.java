import java.time.DayOfWeek;
import java.util.List;

class PrintInConsole extends Print {

    private static final String GREEN_TEXT_START_TOKEN = (char) 27 + "[36m";
    private static final String EXT_END_TOKEN = (char) 27 + "[0m";
    private static final String RED_TEXT_START_TOKEN = (char) 27 + "[31m";

    @Override
    String printFooter() {
        return null;
    }

    @Override
    String printHeader() {
        return null;
    }

    @Override
    String startOfStringHeader() {
        return "";
    }

    @Override
    String startOfStringBody() {
        return "";
    }

    @Override
    String endOfStringHeader() {
        return "\n";
    }

    @Override
    String endOfStringBody() {
        return "\n";
    }

    @Override
    String getWeekend(List<DayOfWeek> weekends, DayOfWeek dayOfWeek) {
        if (weekends.contains(dayOfWeek)) {
            return getFormat(RED_TEXT_START_TOKEN + "%4s" + EXT_END_TOKEN, getTypeOfInputCalendarHeader(dayOfWeek));
        } else {
            return getFormat("%4s", getTypeOfInputCalendarHeader(dayOfWeek));
        }
    }


    @Override
    String selectionOfDay(int currentPosition, boolean currentDay, boolean weekends) {
        if (currentPosition == 0) {
            return getFormat("%4s", "");
        }
        if (currentDay)
            return getFormat(currentPosition, GREEN_TEXT_START_TOKEN + "%4d" + EXT_END_TOKEN);
        else if (weekends)
            return getFormat(currentPosition, RED_TEXT_START_TOKEN + "%4d" + EXT_END_TOKEN);
        else {
            return getFormat(currentPosition, "%4d");
        }
    }
}
