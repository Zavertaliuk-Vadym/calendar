package calendars;

import Print.Print;
import Print.PrintInConsole;
import Print.PrintInWeb;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class DefaultCalendar {
    public void checkYourChange() {
        Print calendar;
        System.out.println("Where you wont to print default calendar?\n 1 - Console. 2 - Web.");
        Scanner scanner = new Scanner(System.in);
        int i = scanner.nextInt();
        switch (i) {
            case 1: {
                calendar = new PrintInConsole();
                System.out.println(calendar.print());
                break;
            }
            case 2: {
                calendar = new PrintInWeb();
                try (PrintWriter printWriter = new PrintWriter("calendar.html")) {
                    printWriter.append(calendar.print());
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            }
        }

    }
}
