package calendars;

import print.Print;
import print.PrintInConsole;
import print.PrintInWeb;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomCalendar {
    public void checkYourChange() {
        Print calendar;
        Scanner scanner = new Scanner(System.in);
        Scanner scanner1 = new Scanner(System.in);
        List<DayOfWeek> dayOfWeeks = new ArrayList<>();
        System.out.println("Select this information for custom calendar");
        System.out.println("Select your year:");
        int year = scanner.nextInt();
        System.out.println("Select your month:");
        int month = scanner.nextInt();
        System.out.println("Select your day:");
        int day = scanner.nextInt();
        System.out.println("Select your first day in week of calendar:");
        int firstDay = scanner.nextInt();
        DayOfWeek firstDayOfWeek = DayOfWeek.of(firstDay);
        System.out.println("Select your weekend in week of calendar:");
        String weekend = scanner1.nextLine();
        List<DayOfWeek> weekList = add(weekend, dayOfWeeks);
        System.out.println("Where you wont to print custom calendar?\n 1 - Console. 2 - Web.");
        int i = scanner.nextInt();
        getTypeCalendar(year, month, day, firstDayOfWeek, weekList, i);
    }

    private void getTypeCalendar(int year, int month, int day, DayOfWeek firstDayOfWeek, List<DayOfWeek> weekList, int i) {
        Print calendar;
        switch (i) {
            case 1: {
                calendar = new PrintInConsole();
                calendar.setToday(LocalDate.of(year, month, day));
                calendar.setDayOfWeek(firstDayOfWeek);
                calendar.setWeekends(weekList);
                System.out.println(calendar.print());
                break;
            }
            case 2: {
                calendar = new PrintInWeb();
                try (PrintWriter printWriter = new PrintWriter("calendar.html")) {
                    calendar.setToday(LocalDate.of(year, month, day));
                    calendar.setDayOfWeek(firstDayOfWeek);
                    calendar.setWeekends(weekList);
                    printWriter.append(calendar.print());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private static List<DayOfWeek> add(String s, List<DayOfWeek> dayOfWeeks) {
        String[] numbersArray = s.split("[, ]");
        for (String number : numbersArray) {
            if (!number.trim().isEmpty()) {
                DayOfWeek weekends = DayOfWeek.of(Integer.parseInt(number.trim()));
                dayOfWeeks.add(weekends);
            }
        }
        return dayOfWeeks;
    }
}
