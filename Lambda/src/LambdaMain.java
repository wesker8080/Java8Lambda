import org.junit.Test;
import pojo.Book;

import java.io.*;
import java.security.MessageDigest;
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
    public void t() {
        List<Map<String, String>> data = new ArrayList();
        String queryString = "itemId=123&userId=wekser&type=1&token=3242343&key=index,itemId=123&userId=wekser&type=1&token=3242343&key=index,";
        List<String> collect = Stream.of(queryString.split(",")).collect(Collectors.toList());
        collect.forEach(x -> {
            Map<String, String> collect1 = Stream.of(x.split("&")).map(str -> str.split("="))
                    .collect(Collectors.toMap(s -> s[0], s -> s[1]));
            data.add(collect1);
        });
        data.forEach(System.out::println);

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
    @Test
    public void testSortFiles() {
        File file = new File("d:\\test");
        long time = 350;
        File[] files = file.listFiles();
        List<File> fileList = Arrays.asList(files);
        //fileList.forEach(x -> System.out.println(x.getName()));
        List<File> collect = fileList.stream().filter(x -> {
            String time1 = x.getName().substring(0, x.getName().length() - 4);
            //System.out.println(time1);
            Long aLong = Long.valueOf(time1);
            if (Math.abs(time - aLong) < 100) {
                return true;
            }
            return false;
        }).collect(Collectors.toList());
        collect.forEach(y -> System.out.println(y.getName()));
        //fileList.stream().sorted(Comparator.comparing(f -> f.getName()));
    }

    @Test
    public void  getMD5() {
        //用于加密的字符
        File file = new File("d:\\test\\xqy.jpg");

        char[] md5String = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        FileInputStream fileInputStream = null;
        try {
            MessageDigest MD5 = MessageDigest.getInstance("MD5");
            fileInputStream = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                MD5.update(buffer, 0, length);
            }
            // 把密文转换成十六进制的字符串形式
            byte[] md = MD5.digest();
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {   //  i = 0
                byte byte0 = md[i];  //95
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
            //返回经过加密后的字符串
            System.out.println(str);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            try {
                if (fileInputStream != null){
                    fileInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public final static String MD5(String s, String charsetName)
    {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f' };
        try {
            byte[] strTemp = s.getBytes(charsetName);
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(strTemp);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
    @Test
    public void testWriteFile() {
        FileOutputStream fileOutputStream = null;
        BufferedWriter bufferedWriter = null;
        String dir = "D:\\";
        String DATA_FILE = "info.json";
        String info = "[{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"0\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"1\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"2\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"3\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"4\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"5\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"6\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"7\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"8\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"9\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"10\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"11\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"12\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"13\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"14\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"15\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"16\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"17\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"18\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"19\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"20\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"21\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"22\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"23\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"24\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"25\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"26\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"27\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"28\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"29\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"30\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"31\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"32\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"33\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"34\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"35\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"36\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"37\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"38\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"39\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"40\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"41\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"42\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"43\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"44\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"45\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"46\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"47\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"48\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"49\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"50\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"51\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"52\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"53\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"54\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"55\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"56\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"57\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"58\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"59\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"60\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"61\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"62\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"63\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"64\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"65\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"66\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"67\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"68\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"69\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"70\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"71\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"72\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"73\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"74\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"75\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"76\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"77\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"78\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"79\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"80\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"81\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"82\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"83\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"84\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"85\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"86\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"87\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"88\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"89\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"90\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"91\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"92\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"93\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"94\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"95\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"96\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"97\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"98\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"99\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"100\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"101\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"102\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"103\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"104\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"105\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"106\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"107\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"108\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"109\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"110\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"111\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"112\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"113\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"114\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"115\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"116\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"117\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"118\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"119\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"120\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"121\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"122\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"123\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"124\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"125\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"126\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"127\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"128\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"129\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"130\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"131\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"132\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"133\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"134\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"135\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"136\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"137\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"138\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"139\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"140\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"141\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"142\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"143\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"144\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"145\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"146\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"147\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"148\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"149\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"150\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"151\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"152\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"153\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"154\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"155\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"156\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"157\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"158\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"159\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"160\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"161\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"162\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"163\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"164\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"165\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"166\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"167\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"168\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"169\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"170\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"171\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"172\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"173\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"174\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"175\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"176\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"177\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"178\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"179\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"180\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"181\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"182\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"183\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"184\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"185\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"186\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"187\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"188\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"189\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"190\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"191\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"192\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"193\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"194\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"195\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"196\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"197\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"198\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"199\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"200\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"201\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"202\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"203\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"204\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"205\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"206\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"207\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"208\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"209\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"210\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"211\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"212\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"213\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"214\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"215\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"216\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"217\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"218\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"219\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"220\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"221\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"222\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"223\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"224\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"225\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"226\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"227\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"228\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"229\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"230\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"231\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"232\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"233\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"234\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"235\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"236\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"237\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"238\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"239\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"240\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"241\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"242\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"243\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"244\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"245\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"246\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"247\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"248\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"249\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"250\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"251\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"252\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"253\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"254\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"255\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"256\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"257\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"258\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"259\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"260\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"261\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"262\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"263\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"264\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"265\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"266\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"267\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"268\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"269\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"270\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"271\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"272\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"273\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"274\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"275\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"276\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"277\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"278\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"279\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"280\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"281\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"282\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"283\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"284\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"285\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"286\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"287\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"288\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"289\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"290\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"291\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"292\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"293\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"294\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"295\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"296\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"297\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"298\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"299\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"300\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"301\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"302\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"303\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"304\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"305\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"306\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"307\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"308\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"309\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"310\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"311\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"312\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"313\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"314\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"315\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"316\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"317\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"318\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"319\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"320\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"321\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"322\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"323\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"324\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"325\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"326\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"327\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"328\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"329\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"330\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"331\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"332\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"333\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"334\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"335\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"336\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"337\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"338\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"339\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"340\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"341\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"342\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"343\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"344\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"345\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"346\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"347\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"348\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"349\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"350\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"351\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"352\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"353\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"354\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"355\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"356\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"357\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"358\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"359\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"360\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"361\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"362\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"363\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"364\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"365\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"366\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"367\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"368\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"369\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"370\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"371\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"372\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"373\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"374\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"375\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"376\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"377\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"378\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"379\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"380\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"381\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"382\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"383\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"384\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"385\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"386\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"387\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"388\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"389\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"390\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"391\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"392\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"393\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"394\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"395\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"396\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"397\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"398\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"399\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"400\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"401\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"402\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"403\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"404\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"405\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"406\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"407\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"408\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"409\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"410\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"411\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"412\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"413\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"414\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"415\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"416\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"417\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"418\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"419\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"420\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"421\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"422\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"423\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"424\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"425\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"426\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"427\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"428\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"429\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"430\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"431\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"432\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"433\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"434\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"435\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"436\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"437\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"438\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"439\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"440\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"441\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"442\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"443\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"444\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"445\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"446\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"447\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"448\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"449\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"450\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"451\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"452\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"453\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"454\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"455\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"456\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"457\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"458\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"459\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"460\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"461\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"462\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"463\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"464\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"465\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"466\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"467\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"468\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"469\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"470\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"471\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"472\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"473\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"474\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"475\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"476\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"477\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"478\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"479\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"480\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"481\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"482\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"483\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"484\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"485\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"486\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"487\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"488\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"489\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"490\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"491\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"492\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"493\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"494\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"495\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"496\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"497\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"498\"},{\"data\":{\"fileName\":\"sss\",\"keyRvalue\":\"dfs\",\"devCode\":\"aaa\",\"type\":\"1\"},\"type\":\"499\"}]";
        File jsonFileDir = new File(dir);
        if (!jsonFileDir.exists()) {
            jsonFileDir.mkdir();
        }
        try {
            // 这里的第二个参数代表追加还是覆盖，true为追加，false为覆盖
            fileOutputStream = new FileOutputStream(jsonFileDir + DATA_FILE, false);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            bufferedWriter.write(info);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedWriter != null) {
                    bufferedWriter.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Test
    public void testReadFileByLines() {
        File file = new File("D:\\info.json");
        BufferedReader reader = null;
        try {
            System.out.println("以行为单位读取文件内容，一次读一整行：");
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                // 显示行号
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }/**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
    @Test
    public void readFileByChars() {
        File file = new File("D:\\info.json");
        Reader reader = null;
        /*try {
            System.out.println("以字符为单位读取文件内容，一次读一个字节：");
            // 一次读一个字符
            reader = new InputStreamReader(new FileInputStream(file));
            int tempchar;
            while ((tempchar = reader.read()) != -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) tempchar) != '\r') {
                    System.out.print((char) tempchar);
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            System.out.println("以字符为单位读取文件内容，一次读多个字节：");
            // 一次读多个字符
            char[] tempchars = new char[30];
            int charread = 0;
            reader = new InputStreamReader(new FileInputStream(file));
            // 读入多个字符到字符数组中，charread为一次读取字符数
            while ((charread = reader.read(tempchars)) != -1) {
                // 同样屏蔽掉\r不显示
                if ((charread == tempchars.length)
                        && (tempchars[tempchars.length - 1] != '\r')) {
                    System.out.print(tempchars);
                } else {
                    for (int i = 0; i < charread; i++) {
                        if (tempchars[i] == '\r') {
                            continue;
                        } else {
                            System.out.print(tempchars[i]);
                        }
                    }
                }
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    }
}
