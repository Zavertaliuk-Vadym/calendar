import java.time.DayOfWeek;
import java.util.List;

class PrintInConsole extends Print {
    private static final String GREEN_TEXT_START_TOKEN = (char) 27 + "[36m";
    private static final String EXT_END_TOKEN = (char) 27 + "[0m";
    private static final String RED_TEXT_START_TOKEN = (char) 27 + "[31m";
    @Override
    String getWeekend(List<DayOfWeek> weekends, DayOfWeek dayOfWeek) {
            if (weekends.contains(dayOfWeek)) {
                return getFormat(RED_TEXT_START_TOKEN + "%4s" + EXT_END_TOKEN, getTypeOfInputCalendarHeader(dayOfWeek));
            } else {
                return getFormat("%4s", getTypeOfInputCalendarHeader(dayOfWeek));

            }
    }

    @Override
    void selectionOfDay(int currentPosition, boolean currentDay, boolean weekends, StringBuilder printerCalendarArray) {
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
}
