package ru.geekbrains.java3.homework.lesson6;

import java.util.Arrays;

public class Task2 {

    public int[] arrayOp(int[] array){
        int last4 = 0;
        boolean changed = false;
        for (int i = 0; i < array.length; i++) {
            if(array[i] == 4) {
                changed = true;
                last4 = i;
            }
        }
        if(!changed) throw new RuntimeException();
        else return Arrays.copyOfRange(array,last4+1,array.length);
    }
}
