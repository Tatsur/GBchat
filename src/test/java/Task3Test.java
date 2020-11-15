import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.geekbrains.java3.homework.lesson6.Task3;

public class Task3Test {
    private static Task3 task3;
    @BeforeClass
    public static void init(){
        System.out.println("init task3");
        task3 = new Task3();
    }
    @Test
    public void MainTest1(){
        Assert.assertTrue(task3.arrayCheck(new int[]{1,4,1,4,4,1,1}));
    }
    @Test
    public void MainTest2(){
        Assert.assertTrue(task3.arrayCheck(new int[]{1,1,1,1,1,1,1}));
    }
    @Test
    public void MainTest3(){
        Assert.assertFalse(task3.arrayCheck(new int[]{1,4,4,0,1,1,0}));
    }
}
