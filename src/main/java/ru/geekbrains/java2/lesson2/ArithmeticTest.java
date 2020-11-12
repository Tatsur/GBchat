package ru.geekbrains.java2.lesson2;

public class ArithmeticTest {

    public static void main(String[] args) {
        int b = 1;
        try {
            int result = divide(10, b);
            System.out.println("Result = " + result);
            int[] array = {1, 2, 3};
            for (int i = 0; i <= array.length; i++) {
                System.out.println(array[i]);
            }
        } catch (ArithmeticException | ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
//        } catch (ArithmeticException e) {
//            System.out.println("Divide by zero!");
//        } catch (ArrayIndexOutOfBoundsException e) {
//            System.out.println("Index error!");
//        } catch (RuntimeException e) {
//            System.out.println("Another error!");
//        }


        System.out.println("finish!");
    }

    private static int divide(int a, int b) {
        return a / b;
    }
}
