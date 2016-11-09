import calendars.CustomCalendar;
import calendars.DefaultCalendar;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws RuntimeException {
        System.out.println("Default calendar - 1, custom calendar - 2");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i) {
            case 1: {
                DefaultCalendar defaultCalendar = new DefaultCalendar();
                defaultCalendar.checkYourChange();
                break;
            }
            case 2: {
                CustomCalendar customCalendar = new CustomCalendar();
                customCalendar.checkYourChange();
                break;
            }
        }
    }
}
