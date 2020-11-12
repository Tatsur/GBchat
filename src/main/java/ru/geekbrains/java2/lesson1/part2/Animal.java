package ru.geekbrains.java2.lesson1.part2;

public abstract class Animal /*implements AnimalInterface*/ {

    protected String name;

    protected static String staticValue = "5";

    public static String getStaticValue() {
        return staticValue;
    }

    public Animal(String name) {
        this.name = name;
    }

    public Animal() {
        name = "default name";
    }

    public void animalInfo() {
        System.out.println("Animal name is " + name);
    }

    public void jump() {
        System.out.println("Animal jumped");
    }

    public String getName() {
        return name;
    }

    public abstract void voice();

}
