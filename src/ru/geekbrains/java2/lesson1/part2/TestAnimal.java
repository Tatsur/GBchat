package ru.geekbrains.java2.lesson1.part2;

public class TestAnimal {

    public static void main(String[] args) {
//        Animal animal = new Animal("оно");

        System.out.println(Color.valueOf("BLACK"));
        System.out.println(Color.valueOf("BLACK").ordinal());
//
        for (Color value : Color.values()) {
            System.out.println(value.getRussianColor());
        }


        Cat.CatAttribute catAttribute = new Cat.CatAttribute();
        Cat cat = new Cat("Бааааааарсик", Color.WHITE, catAttribute);
        Animal dog = new Dog("Boris", "black", "ovcharka");

        var value = switch (cat.getColor()) {
            case WHITE, RED -> "dsgs";
            default -> throw new IllegalStateException("Unexpected value: " + cat.getColor());
        };

//        new Animal("name");

//        cat.animalInfo();
        dog.animalInfo();

        System.out.println(Animal.getStaticValue());
        System.out.println(Dog.getStaticValue());
        Cat.CatAttribute catAttribute1 = ((Cat) cat).getCatAttribute();
        System.out.println("instanceof: " + (cat instanceof Animal));
        System.out.println("instanceof: " + (cat instanceof Cat));
//        System.out.println("instanceof: " + (cat instanceof Dog));
        System.out.println(Cat.class.cast(cat).getCatAttribute());
        System.out.println(Animal.class.cast(cat).getName());
        System.out.println(Dog.class.cast(cat).getName());


//        animal.animalInfo();
//
//        cat.catInfo();
//        cat.animalInfo();

//
//        dog.dogInfo();
//        dog.animalInfo();
//        dog.jump();

//        infoAndJump(animal);
//        infoAndJump(cat);
//        infoAndJump(dog);
    }

    private static void infoAndJump(Animal animal) {
        animal.animalInfo();
        animal.jump();
//        animal.voice();
        System.out.println();
    }
}
