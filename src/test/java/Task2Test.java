import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.geekbrains.java3.homework.lesson6.Task2;

import java.util.Arrays;

public class Task2Test {
    private static Task2 task2;
    private static int[] testArray;

    @BeforeClass
    public static void init(){
        System.out.println("init task2");
        task2 = new Task2();
        testArray = new int[]{1,2,4,4,2,3,4,1,7};
        System.out.println(Arrays.toString(testArray));
    }

    @Test
    public void testMainOp1(){
        Assert.assertArrayEquals(new int[]{1,7},task2.arrayOp(testArray));
    }
    @Test
    public void testMainOp2(){
        Assert.assertArrayEquals(new int[]{1,1,7},task2.arrayOp(new int[]{1,2,4,4,2,4,1,1,7}));
    }
    @Test
    public void testMainOp3(){
        Assert.assertArrayEquals(new int[]{},task2.arrayOp(new int[]{1,2,4,4,2,4,1,1,4}));
    }

    @Test(expected = RuntimeException.class)
    public void testRuntimeException(){
        task2.arrayOp(new int[]{1,2,1,1,2,3,1,1,7});
    }
}

