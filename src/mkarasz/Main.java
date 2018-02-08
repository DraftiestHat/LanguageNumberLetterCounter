package mkarasz;

import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.text.RuleBasedNumberFormat;
import com.ibm.icu.util.ULocale;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        userInteractNumbers();
    }

    public static void checkNumbers(ULocale l)
    {
        System.out.println("starting " + l.getBaseName());
        NumberFormat formatter = new RuleBasedNumberFormat(l, RuleBasedNumberFormat.SPELLOUT);
        long time = System.currentTimeMillis();
        long total = 0;
        for (long i = -400000000; i < 400000000; i++) {
            String s = formatter.format(i);
            if(s.length() == i) {
                System.out.println(s + " == " + i);
            }

            if(i % 10000000 == 0) {
                total += (System.currentTimeMillis() - time);
                time = System.currentTimeMillis();
            }
        }

        System.out.println("Total was " + total + " i.e. " + formatter.format(total));
    }

    public static void userInteractNumbers() throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("What is your locale? En(glish), Fr(ench), Ge(rman), or It(alian)");
        ULocale l = null;
        while(true) {
            String bS = scanner.next();
            byte[] b = bS.getBytes();
            switch (b[0]) {
                case 'E':
                case 'e':
                    l = ULocale.ENGLISH;
                    break;
                case 'F':
                case 'f':
                    l = ULocale.FRENCH;
                    break;
                case 'G':
                case 'g':
                    l = ULocale.GERMAN;
                    break;
                case 'I':
                case 'i':
                    l = ULocale.ITALIAN;
                    break;
                default:
                    System.out.println("Didn't recognize \"" + b + "\", please try again.");
            }
            if(l != null) {
                break;
            }
        }

        long number = 0;
        System.out.println("Enter your number.");
        while(true) {
            String b = scanner.next();
            try {
                number = Long.parseLong(b.toString());
                break;
            } catch (NumberFormatException nfe) {
                System.out.println("The number wasn't parsed correctly. Got \"" + b + "\". Try again.");
            }
        }

        LinkedHashMap<Long, String> list = new LinkedHashMap<Long, String>();
        NumberFormat format = new RuleBasedNumberFormat(l, RuleBasedNumberFormat.SPELLOUT);
        while(true) {
            if(list.containsKey(number)) {
                break;
            }
            String s = format.format(number);
            list.put(number,s);
            System.out.println(number + ", " + s);
            number = s.length();
        }
    }
}
