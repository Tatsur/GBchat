package ru.geekbrains.java3.homework.lesson6;

public class Task3 {
    public boolean arrayCheck(int[] array){
        for (int i : array) {
            if(i!=1 && i!=4){
                return false;
            }
        }
        return true;
    }
}
