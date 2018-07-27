import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * JAVA8流操作
 *Stream分为 源source，中间操作，终止操作
 *
 * 流的源可以是一个数组、一个集合、一个生成器方法，一个I/O通道等等。
 *
 * 一个流可以有零个和或者多个中间操作，每一个中间操作都会返回一个新的流，供下一个操作使用。一个流只会有一个终止操作
 *
 * Stream只有遇到终止操作，它的源才开始执行遍历操作
 * @author MR.ZHANG
 * @create 2018-07-23 9:27
 */

/**
 * 中间操作
 *
 * 过滤 filter
 * 去重 distinct
 * 排序 sorted
 * 截取 limit、skip
 * 转换 map/flatMap
 * 其他 peek
 */

/**
 * 终止操作
 *
 * 循环 forEach
 * 计算 min、max、count、 average
 * 匹配 anyMatch、 allMatch、 noneMatch、 findFirst、 findAny
 * 汇聚 reduce
 * 收集器 toArray collect
 */
public class StreamTest {

    /**
     * 创建Stream
     */
    static void test1() throws IOException {
        //数组 常见
        String[] arr = {"1","2","3","4","5"};
        Stream<String> arr1 = Stream.of(arr);
        arr1.forEach(System.out :: print);
        System.out.println();
        //集合 常见
        List<String> list = Arrays.asList("1", "2", "3", "4", "5");
        Stream<String> stream = list.stream();
        stream.forEach(System.out :: print);
        System.out.println();
        //Stream.generate
        Stream<Integer> generate = Stream.generate(() -> 1);
        generate.limit(10).forEach(System.out :: print);
        System.out.println();
        //Stream.iterate
        Stream<Integer> iterate = Stream.iterate(0, x -> x + 1);
        iterate.limit(10).forEach(System.out::print);
        System.out.println();
        //Others API
        String string = "wesker123";
        IntStream chars = string.chars();
        chars.forEach(System.out::print);
        //读取文件内容并打印到控制台上
        //Files.lines(Paths.get("F:\\WorkSpace\\Lambda\\src\\LambdaTest.java")).forEach(System.out::println);
    }


    /**
     * filter
     */
    static void test2() {
        Arrays.asList(1,2,3,4,5).stream().filter(x -> x%2 == 0).forEach(System.out::println);
    }

    /**
     * sum
     */
    static void test3() {
        int sum = Arrays.asList(1, 2, 3, 4, 5,6).stream().filter(x -> x % 2 == 0).mapToInt(x -> x).sum();
        System.out.println("-----------wesker-----------sum值=" + sum + "," + "当前类=StreamTest.test3()");
    }

    /**
     * max min
     */
    static void test4() {
        Integer max = Arrays.asList(1, 2, 3, 4, 5).stream().max(Comparator.comparingInt(x -> x)).get();
        System.out.println("-----------wesker-----------max=" + max + "," + "当前类=StreamTest.test4()");
        Integer min = Arrays.asList(1, 2, 3, 4, 5).stream().min(Comparator.comparingInt(x -> x)).get();
        System.out.println("-----------wesker-----------min=" + min + "," + "当前类=StreamTest.test4()");
    }

    /**
     * findFirst findAny
     */
    static void test5() {
        Optional<Integer> optional = Arrays.asList(1, 2, 3, 4, 5,6).stream().filter(x -> x % 2 == 0).findAny();
        Integer integer = optional.get();
        System.out.println("-----------wesker-----------integer值=" + integer + "," + "当前类=StreamTest.test5()");
        //升序
        Arrays.asList(4, 2, 5, 1, 3, 6).stream().filter(x -> x % 2 == 0).sorted().forEach(System.out::println);
        Arrays.asList("admin","com","java","wesker","w").stream().sorted(Comparator.comparingInt(String::length)).forEach(System.out::println);
        //降序,关键代码sorted((x,y) -> y-x)
        Optional<Integer> optional1 = Arrays.asList(1, 2, 3, 4, 5, 6).stream().filter(x -> x % 2 == 0).sorted((x,y) -> y-x).findFirst();
        Integer integer1 = optional1.get();
        System.out.println("-----------wesker-----------integer1值=" + integer1 + "," + "当前类=StreamTest.test5()");
        Arrays.asList("admin","com","java","wesker","w").stream().sorted((x,y) -> y.length() - x.length()).forEach(System.out::println);
    }

    /**
     * 转换为list
     */
    static void test6() {
        List<Integer> list = Stream.iterate(1, x -> x + 1).limit(50).filter(x -> x % 2 == 0).collect(Collectors.toList());
        System.out.println("-----------wesker-----------list值=" + list + "," + "当前类=StreamTest.test6()");
    }

    /**
     *转换为set
     */
    static void test7() {
        Set<Integer> set = Arrays.asList(1, 1, 2, 2, 3, 3, 4, 4, 5, 5).stream().collect(Collectors.toSet());
        System.out.println("-----------wesker-----------set值=" + set + "," + "当前类=StreamTest.test8()");
    }
    /**
     *distinct 去重
     */
    static void test8() {
        Arrays.asList(1,1,2,2,3,3,4,4,5,5).stream().distinct().forEach(System.out::print);
    }

    /**
     * skip 忽略
     */
    static void test9() {
        //先取前10个忽略前5个再取剩余前3个
        Stream.iterate(10,x -> x - 1).limit(10).sorted(Comparator.comparingInt(x -> x)).skip(5).limit(3).forEach(System.out::print);
    }

    /**
     * map 转换
     */
    static void test10() {
        String s = "1,2,3,4,5";
        int sum = Stream.of(s.split(",")).mapToInt(Integer::valueOf).sum();
        System.out.println("-----------wesker-----------sum值=" + sum + "," + "当前类=StreamTest.test10()");
        String s1 = "wesker_lisa_luke";
        //构造方法引用
        Stream.of(s1.split("_")).map(User::new).forEach(System.out::println);
        //静态方法引用
        Stream.of(s1.split("_")).map(Person::build).forEach(System.out::println);
    }

    /**
     * peek 调用Consumer去消费
     */
    static void test11() {
        String s = "1,2,3,4,5";
        int sum = Stream.of(s.split(",")).peek(System.out::println).mapToInt(Integer::valueOf).sum();
        System.out.println("-----------wesker-----------sum值=" + sum + "," + "当前类=StreamTest.test11()");
    }
    static void test12() {

        //并行
        //设置并发线程数
       System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism","2");

        Stream.iterate(1,x -> x + 1).limit(200).parallel().peek(x -> {
            System.out.println(Thread.currentThread().getName());
        }).parallel().max(Integer::compareTo);
        //串行
        /*Stream.iterate(1,x -> x + 1).limit(200).parallel().peek(x -> {
            System.out.println(Thread.currentThread().getName());
        }).sequential().max(Integer::compareTo);*/
    }
    public static void main(String[] args) throws IOException {
        //test1();
        //test2();
        //test3();
        //test4();
        //test5();
        //test6();
        //test7();
        //test8();
        //test9();
        //test10();
        //test11();
        test12();

    }
}
class User{
    String name;
    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                '}';
    }
}
class Person {
    String name;
    public static Person build(String name) {
        Person person = new Person();
        person.setName(name);
        return person;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
}
