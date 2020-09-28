package ru.geekbrains.java2.lesson3;

import java.util.*;

public class ArrayListExample {

    public static void main(String[] args) {
//        arrayExample();
        arrayListExample();
//        arrayListToArray();
    }

    private static void arrayExample() {
//        Integer[] arr = new Integer[4];//+5
        int[] arr = {1, 2, 3, 4}; // [0; arr.length)
//        arr[4] = 5;
        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        int[] arrNew = new int[10];
        System.arraycopy(arr, 0, arrNew, 0, arr.length);
//        int[] arrNew = Arrays.copyOf(arr, 10);
//        arr = arrNew;
        arrNew[4] = 5;
//        System.out.println(arrNew);
        System.out.println(Arrays.toString(arrNew));
//        arrNew = null;
    }

//    private int[] addElement(int[] array, int newValue) {
//        if (!isFull(array)) {
//            for (int i = 0; i < array.length; i++) {
//                if (array[i] == 0) {
//                    array[i] = newValue;
//                    break;
//                }
//            }
//            return array;
//        } else {
//            int[] arrNew = new int[array.length * 2];
//            System.arraycopy(array, 0, arrNew, 0, array.length);
//            arrNew[array.length] = newValue;
//            return arrNew;
//        }
//    }

    private boolean isFull(int[] array) {
        for (int value : array) {
            if (value == 0) {
                return false;
            }
        }
        return true;
    }

    private static void arrayListToArray() {
        Set<Object> list1 = new HashSet<>();
        list1.add(1);
        list1.add(2);
        list1.add(3.0);

        Set<Object> list2 = new HashSet<>();
        list2.add(1);
        list2.add(3.0);
        list2.add(2);

        System.out.println("equals: " + list1.equals(list2));

//        List<String> newCollection = new ArrayList<>();
//        for (Object o : list1) {
//            newCollection.add(String.valueOf(o));
//        }

//        list.addAll(List.of(1, 2));
//        Integer[] arr = new Integer[list.size()];
//        list.toArray(arr);
//        List<Integer> arr2 = new ArrayList<>(Arrays.asList(arr));
//        List<Integer> arr3 = new ArrayList(arr);
//        arr = list.toArray(new Integer[0]);
    }

    private static void arrayListExample() {
        List<List<Integer>> data = new ArrayList<>(Integer.MAX_VALUE);
        data.add(List.of(1,3));
        data.add(List.of(2,4,8));
        for (List<Integer> datum : data) {
            System.out.println(datum);
        }

        List<String> newDataName = new ArrayList<>(5);
        newDataName.add("B");
        newDataName.add("A");
        newDataName.add("C");
        newDataName.add("E");
        newDataName.add("D");
        newDataName.add("E");
        newDataName.add("E");
        Collections.sort(newDataName);
//        newDataName.trimToSize();
        System.out.println(newDataName);
//        newDataName.add(1, "А0");
//        System.out.println(newDataName);
        newDataName.remove("E");
        newDataName.remove(2);
//        System.out.println(newDataName);
        System.out.println("contains C? " + newDataName.contains("C"));

        for (int i = 0; i < newDataName.size(); i++) {
            System.out.println(newDataName.get(i));//newDataName[i]
        }
//
//
        for (String next : newDataName) {
            System.out.println(next);
        }

//        Iterator<String> iterator = newDataName.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            System.out.println(next);
//        }


//
//        List<Integer> data = new ArrayList<>();
//        data.add(1);
//        data.add(2);
//        data.add(3);
//        data.remove(1);
//        System.out.println(data);
//
//        newDataName.sort(Comparator.naturalOrder());
//        System.out.println(newDataName);
//
////        List<String> anotherData = new ArrayList<>(newDataName);
//        List<String> anotherData = new ArrayList<>(5);
    }

}
