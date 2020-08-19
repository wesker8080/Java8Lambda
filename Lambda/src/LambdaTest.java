import java.util.ArrayList;
import java.util.List;
import java.util.function.*;

interface UserMapper {
    void insert(Integer i);
}
interface OrderMapper {
    int insert(Integer i,String s);
    //int insert(int i, String s, double d);
}
public class LambdaTest{
    private static final String WESKER = "wesker";
    static void test1() {
        Runnable runnable = () -> System.out.println("runnable lambda type");
        new Thread(runnable).start();
    }
    static void test2() {
        UserMapper userMapper = i -> System.out.println("有参无返回值 : " + i);
        userMapper.insert(5);
    }
    static void test3() {
        OrderMapper orderMapper = (x,y) -> (x + Integer.valueOf(y));
        int insert = orderMapper.insert(5, "6");
        System.out.println("有参有返回值 : " + insert);
    }

    /**
     * 一输入一输出
     */
    static void test4() {
        Function<String,Integer> function = String::length;
        Integer size = function.apply(WESKER);
        System.out.println("-----------wesker-----------size值=" + size + "," + "当前类=LambdaTest.test4()");
    }
    /**
     * 两输入一输出
     */
    static void test5() {
        BiFunction<String,String,Integer> biFunction = (x,y) -> x.length() + y.length();
        Integer size = biFunction.apply(WESKER,WESKER);
        System.out.println("-----------wesker-----------size值=" + size + "," + "当前类=LambdaTest.test5()");
    }

    /**
     * 消费一个值
     */
    static void test6() {
        Consumer<String> consumer = System.out :: println;
        consumer.accept(WESKER);
    }

    /**
     * 静态方法引用,Supplier没有入参 返回String类型
     */
    static String put() {
        return WESKER;
    }
    static void consume(String s) {
        System.out.println("-----------wesker-----------s值=" + s + "," + "当前类=LambdaTest.consume()");
    }
    static String function(String s) {
        String s1 = s.toUpperCase();
        System.out.println("-----------wesker-----------s值=" + s1 + "," + "当前类=LambdaTest.function()");
        return s1;
    }
    static Integer biFunction(String s1, String s2) {
        Integer i = s1.length() + s2.length();
        System.out.println("-----------wesker-----------i值=" + i + "," + "当前类=LambdaTest.biFunction()");
        return i;
    }
    static void test7() {
        //Supplier< T>接口没有入参，返回一个T类型的对象
        Supplier<String> supplier = LambdaTest :: put;
        String s = supplier.get();
        System.out.println("-----------wesker-----------s值=" + s + "," + "当前类=LambdaTest.test7()");
        Consumer<String> consumer = LambdaTest::consume;
        consumer.accept(WESKER);
        Function<String,String> function = LambdaTest::function;
        function.apply(WESKER);
        BiFunction<String,String,Integer> biFunction = LambdaTest::biFunction;
        biFunction.apply(WESKER,WESKER);
    }

    /**
     * 实例方法引用
     * @param s
     */
    public void consum(String s) {
        System.out.println("-----------wesker-----------s值=" + s + "," + "当前类=LambdaTest.consum()");
    }
    static void test8 () {
        Consumer<String> consumer = new LambdaTest() :: consum;
        consumer.accept(WESKER);
    }

    /**
     * 对象方法引用
     * 条件:抽象方法的第一个参数类型刚好是实例方法的类型，抽象方法剩余的参数恰好可以当作实例方法的参数
     */
    static void test9() {
        //Consumer<Too> consumer = (Too too) -> too.too();
        Consumer<Too> consumer = Too::too;
        consumer.accept(new Too());
        BiConsumer<Too2,String> consumer1 = Too2 :: too2;
        consumer1.accept(new Too2(),WESKER);
    }

    /**
     * 构造方法引用
     */
    static void test10() {
        /**
         * 无参
         */
        Supplier<List<String>> supplier = ArrayList::new;
        Supplier<Student> supplier1 = Student::new;
        supplier1.get();
        /**
         * 有参
         */
        Consumer<Integer> consumer = Student :: new;
        consumer.accept(6);
        Function<String,Student> function = Student::new;
        function.apply(WESKER);
    }
    public static void main(String[] args) throws Exception {
        //test1();
        test2();
        //test3()
        //test4();
        //test5();
        //test6();
        //test7();
        //test8();
        //test9();
        //test10();
    }
}
class Too {
    public void too() {
        System.out.println("-----------wesker-----------当前类=Too.too()");
    }
}
class Too2 {
    public void too2(String s) {
        System.out.println("-----------wesker-----------s值=" + s + "," + "当前类=Too2.too2()");
    }
}
class Student {
    public Student() {
        System.out.println("-----------wesker-------"+ "当前类=Student.Student()");
    }
    public Student(Integer i) {
        System.out.println("-----------wesker-----------i值=" + i + "," + "当前类=Student.Student()");
    }
    public Student(String s) {
        System.out.println("-----------wesker-----------s值=" + s + "," + "当前类=Student.Student()");
    }
}
