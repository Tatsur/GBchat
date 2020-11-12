package ru.geekbrains.java2.lesson2;

public class Calendar {

    enum Month {
        JANUARY,
        FEBRARY,
        JUNE,
        DECEMBER,
        SEPTEMBER,
    }

    public int getNumberOfDays(Month month) throws Exception {
        switch (month) {
            case JANUARY:
                return 31;
            case FEBRARY:
                return 28;
            case JUNE:
                return 30;
            case DECEMBER:
                return 31;
            default:
                throw new Exception("Test");
//                throw new IllegalArgumentException("Test");
//                RuntimeException e = new IllegalArgumentException();
//                throw e;
        }
    }

    public static void main(String[] args) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                System.out.println(t.getName());
                System.out.println(e.getMessage());
                System.out.println("My UncaughtExceptionHandler!");
                e.printStackTrace();
            }
        });

        Calendar calendar = new Calendar();
        System.out.println(calendar.getNumberOfDays(Month.SEPTEMBER));
    }
}
