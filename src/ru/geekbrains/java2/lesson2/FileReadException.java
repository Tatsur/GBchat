package ru.geekbrains.java2.lesson2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FileReadException {

    public static void main(String[] args) {
//        FileInputStream fis = null;
//        try {
        try (FileInputStream fis = new FileInputStream("test_lesson2.txt")) {
//            String text = readFile("test1.txt");
//            fis = new FileInputStream("test1.txt");
            byte[] bytes = fis.readAllBytes();
            System.out.println(new String(bytes));
//            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("Файл не был найден!");
        } catch (IOException e) {
            System.out.println("Файл поврежден!");
        } /*finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    private static String readFile(String fileName) throws FileNotFoundException, IOException {
        FileInputStream fis;
        fis = new FileInputStream(fileName);
        byte[] bytes = fis.readAllBytes();
        return new String(bytes);
    }
}