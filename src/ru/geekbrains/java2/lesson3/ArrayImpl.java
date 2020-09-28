package ru.geekbrains.java2.lesson3;

import java.util.Arrays;

public class ArrayImpl {

    public static void main(String[] args) {
        ArrayImpl array = new ArrayImpl(5);
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.add(5);
        array.add(6);
        for (int i = 0; i < array.size(); i++) {
            System.out.println(array.get(i));
        }
    }

    protected int[] data;
    protected int size = 0;

    public ArrayImpl(int initialCapacity) {
        this.data = new int[initialCapacity];
    }

    public void add(int value) {
        checkAndGrow();
        data[size++] = value;//001[005] --> [005(1), 006(2), 007(3)]
    }

    private void checkAndGrow() {
        if (data.length == size) {
            data = Arrays.copyOf(data, data.length * 2);
//            int[] arrNew = new int[data.length * 2];
//            System.arraycopy(data, 0, arrNew, 0, data.length);
//            data = null;
//            data = arrNew;
        }
    }

    public int get(int index) {
        return data[index];
    }

    public int remove(int index) {
        int removedValue = data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = 0;
        size--;
        return removedValue;
    }

    public int indexOf(int value) {
        for (int i = 0; i < size; i++) {
            if (data[i] == value) {
                return i;
            }
        }
        return -1;
    }

    public int size() {
        return this.size;
    }
}