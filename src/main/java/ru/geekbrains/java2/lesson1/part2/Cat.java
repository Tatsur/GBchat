package ru.geekbrains.java2.lesson1.part2;

public class Cat extends Animal {

    private Color color = Color.RED;
    private CatAttribute catAttribute;

    public static class CatAttribute {
        private String meal;
        private Color colorEyes;
        private int weight;
    }

    public Cat(String name, Color color, CatAttribute catAttribute) {
        super(name);
        this.color = color;
        this.catAttribute = catAttribute;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public void animalInfo() {
        super.animalInfo();
        System.out.println("Cat name is " + super.getName() + "; color - " + color);
    }

    @Override
    public void voice() {
        System.out.println("Мяу!");
    }

    public CatAttribute getCatAttribute() {
        return catAttribute;
    }
}
