import org.junit.Test;
import pojo.Book;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Lambda实战
 *
 * @author MR.ZHANG
 * @create 2018-07-24 9:41
 */
public class LambdaMain {
    /**
     * index?itemId=123&userId=wekser&type=1&token=3242343&key=index
     */
    @Test
    public void test1() {
    String queryString = "itemId=123&userId=wekser&type=1&token=3242343&key=index";
        Map<String, String> map = Stream.of(queryString.split("&")).map(str -> str.split("="))
                .collect(Collectors.toMap(s -> s[0], s -> s[1]));
        System.out.println("-----------wesker-----------map值=" + map + "," + "当前类=LambdaMain.test1()");
        /**
         * {itemId=123, type=1, userId=wekser, key=index, token=3242343}
         */
    }
    @Test
    public void test2() {
        //List<Book> -> List<Integer>
        List<Integer> list = books().stream().map(Book::getId).collect(Collectors.toList());
        System.out.println("-----------wesker-----------list值=" + list + "," + "当前类=LambdaMain.test2()");
        /**
         *[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14]
         */
        String s = books().stream().map(book -> book.getId() + "").collect(Collectors.joining(",","(",")"));
        System.out.println("-----------wesker-----------s值=" + s + "," + "当前类=LambdaMain.test2()");
        /**
         * (1,2,3,4,5,6,7,8,9,10,11,12,13,14)
         */
    }

    /**
     * 去重
     */
    @Test
    public void test3() {
        //List<String> types = books().stream().map(Book::getType).distinct().collect(Collectors.toList());
        Set<String> types = books().stream().map(Book::getType).collect(Collectors.toSet());
        System.out.println("-----------wesker-----------types值=" + types + "," + "当前类=LambdaMain.test3()");
    }

    /**
     * 按价格排序，升序
     */
    @Test
    public void test4() {
        List<Book> list = books().stream().sorted(Comparator.comparingDouble(Book::getPrice)).collect(Collectors.toList());
        list.forEach(System.out::println);
    }
    /**
     * 按价格排序，降序
     */
    @Test
    public void test5() {
        List<Book> list = books().stream().sorted(Comparator.comparingDouble(Book::getPrice).reversed()).collect(Collectors.toList());
        list.forEach(System.out::println);
    }
    /**
     * 按价格排序，升序,如果价格一致，按日期升序排序
     */
    @Test
    public void test6() {
        List<Book> list = books().stream().sorted(Comparator.comparingDouble(Book::getPrice).thenComparing(Book::getPublishDate)).collect(Collectors.toList());
        list.forEach(System.out::println);
    }
    @Test
    public void test7() {
        Map<Integer, Book> bookMap = books().stream().collect(Collectors.toMap(Book::getId, book -> book));
        System.out.println("-----------wesker-----------bookMap值=" + bookMap + "," + "当前类=LambdaMain.test7()");
    }

    /**
     * 统计书的平均价格
     */
    @Test
    public void test8() {
        Double avePrice = books().stream().collect(Collectors.averagingDouble(Book::getPrice));
        System.out.println("-----------wesker-----------avePrice值=" + avePrice + "," + "当前类=LambdaMain.test8()");
    }

    /**
     * 找到最XX的书
     */
    @Test
    public void test9() {
        Optional<Book> max = books().stream().max(Comparator.comparing(Book::getPrice));
        Book maxBook = max.get();
        System.out.println("-----------wesker-----------maxBook=" + maxBook + "," + "当前类=LambdaMain.test9()");
        Optional<Book> min = books().stream().min(Comparator.comparing(Book::getPrice));
        Book minBook = min.get();
        System.out.println("-----------wesker-----------minBook=" + minBook + "," + "当前类=LambdaMain.test9()");
        Optional<Book> maxDate = books().stream().max(Comparator.comparing(Book::getPublishDate));
        Book maxDateBook = maxDate.get();
        System.out.println("-----------wesker-----------maxDateBook=" + maxDateBook + "," + "当前类=LambdaMain.test9()");
        Optional<Book> minDate = books().stream().min(Comparator.comparing(Book::getPublishDate));
        Book minDateBook = minDate.get();
        System.out.println("-----------wesker-----------minDateBook=" + minDateBook + "," + "当前类=LambdaMain.test9()");
    }

    /**
     * 统计分组
     */
    @Test
    public void test10() {
        //按书类型分组
        Map<String, List<Book>> map = books().stream().collect(Collectors.groupingBy(Book::getType));
        map.keySet().forEach(key -> {
            System.out.println(key);
            System.out.println(map.get(key));
            System.out.println("------------------");
        });
        //按类型分组统计每种类型数量
        Map<String, Long> map1 = books().stream().collect(Collectors.groupingBy(Book::getType, Collectors.counting()));
        map1.keySet().forEach(key -> System.out.println("\"" + key + "\"" + " 类型的数量是 : " + map1.get(key)));
        //按类型分组统计每种类型价格总和
        Map<String, Double> collect = books().stream().collect(Collectors.groupingBy(Book::getType, Collectors.summingDouble(Book::getPrice)));
        collect.keySet().forEach(key -> System.out.println("\"" + key + "\"" + " 类型的价格总和是 : " + collect.get(key)));
        //按类型分组统计每种类型平均价格
        Map<String, Double> aveMap = books().stream().collect(Collectors.groupingBy(Book::getType, Collectors.averagingDouble(Book::getPrice)));
        aveMap.keySet().forEach(key -> System.out.println("\"" + key + "\"" + " 类型的平均价格是 : " + aveMap.get(key)));
        //按类型分组统计每种类型最贵价格
        Map<String, Optional<Book>> maxPriceMap = books().stream().collect(Collectors.groupingBy(Book::getType, Collectors.maxBy(Comparator.comparing(Book::getPrice))));
        maxPriceMap.keySet().forEach(key -> System.out.println("\"" + key + "\"" + " 类型的最贵价格是 : " + maxPriceMap.get(key).get()));
        //按类型分组统计每种类型出版时间最晚的
        Map<String, Optional<Book>> maxDate = books().stream().collect(Collectors.groupingBy(Book::getType, Collectors.maxBy(Comparator.comparing(Book::getPublishDate))));
        maxDate.keySet().forEach(key -> System.out.println("\"" + key + "\"" + " 类型的出版时间最晚的是 : " + maxDate.get(key).get()));
    }

    /**
     * 过滤
     */
    @Test
    public void test11() {
        //取出价格大于XX的书并按出版时间升序排序
        List<Book> bookList = books().stream().filter(book -> book.getPrice() >= 40).sorted(Comparator.comparing(Book::getPublishDate)).collect(Collectors.toList());
        bookList.forEach(System.out::println);
    }
    private List<Book> books() {
        List<Book> books = new ArrayList<>();
        books.add(new Book(1,"Java",15d,"编程语言",LocalDate.parse("2018-01-01")));
        books.add(new Book(2,"Tomcat",31d,"服务器",LocalDate.parse("2015-05-05")));
        books.add(new Book(3,"Disruptor",51d,"框架",LocalDate.parse("2016-01-03")));
        books.add(new Book(4,"Jetty",17d,"服务器",LocalDate.parse("2017-05-06")));
        books.add(new Book(5,"ActiveMQ",19d,"框架",LocalDate.parse("2013-01-23")));
        books.add(new Book(6,"MongoDB",41d,"数据库",LocalDate.parse("2011-05-16")));
        books.add(new Book(7,"Mysql",61d,"数据库",LocalDate.parse("1994-06-08")));
        books.add(new Book(8,"SpringMVC",10d,"框架",LocalDate.parse("2011-06-15")));
        books.add(new Book(9,"Mybatis",31d,"框架",LocalDate.parse("2009-09-08")));
        books.add(new Book(10,"BootStrap",45d,"框架",LocalDate.parse("2010-11-12")));
        books.add(new Book(11,"Html",76d,"编程语言",LocalDate.parse("2016-12-13")));
        books.add(new Book(12,"设计模式",28d,"其他",LocalDate.parse("2017-10-18")));
        books.add(new Book(13,"敏捷开发",97,"其他",LocalDate.parse("2018-12-25")));
        books.add(new Book(14,"JavaScript",97d,"编程语言",LocalDate.parse("2018-01-20")));
        return books;
    }
}
