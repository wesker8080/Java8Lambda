import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Optonal使用示例
 *
 * @author MR.ZHANG
 * @create 2018-09-13 9:42
 * 背景,有如下需求
 *     String isocode = user.getAddress().getCountry().getIsocode().toUpperCase();
 *     上面的语句每一个get都有可能导致 NullPointerException
 *     如果要确保不触发异常
 *     if (user != null) {
 *         Address address = user.getAddress();
 *         if (address != null) {
 *             Country country = address.getCountry();
 *             if (country != null) {
 *                 String isocode = country.getIsocode();
 *                 if (isocode != null) {
 *                     isocode = isocode.toUpperCase();
 *                 }
 *             }
 *         }
 *     }
 * 这个过程，是比较痛苦的，每一个都去判null
 * ******************************Optional类很好解决了这个问题**************************
 *
 * Optional 是 Java 语言的有益补充 —— 它旨在减少代码中的 NullPointerExceptions，虽然还不能完全消除这些异常。
 * 它也是精心设计，自然融入 Java 8 函数式支持的功能。
 * 总的来说，这个简单而强大的类有助于创建简单、可读性更强、比对应程序错误更少的程序
 * 在使用 Optional 的时候需要考虑一些事情，以决定什么时候怎样使用它。
 * 重要的一点是 Optional 不是 Serializable。因此，它不应该用作类的字段。
 *
 * 如果你需要序列化的对象包含 Optional 值，Jackson 库支持把 Optional 当作普通对象。
 * 也就是说，Jackson 会把空对象看作 null，而有值的对象则把其值看作对应域的值。
 *
 * Optional 主要用作返回类型。在获取到这个类型的实例后，
 * 如果它有值，你可以取得这个值，否则可以进行一些替代行为。
 */
public class OptionalTest {

    //创建Optional实例empty,of,ofNullable
    @Test
    public void test1() {
        User user = new User();
        //空的Optional实例
        Optional<Object> empty = Optional.empty();
        //如果user是null，of方法会报NullPointerException,所以要确定传入对象不为null的情况下使用
        Optional<User> optional = Optional.of(user);
        System.out.println("创建Optional实例------》Optional.of 存在实例？ : " + optional.get());
        //如果user为null，返回null
        //user = null;
        Optional<User> optional1 = Optional.ofNullable(user);
        System.out.println("创建Optional实例------》Optional.ofNullable 存在实例？ : " + optional1.isPresent());
    }

    //获取Optional里的值
    @Test
    public void test2() {
        User user = new User("wesker","22");
        Optional<User> user1 = Optional.ofNullable(user);
        //isPresent执行检查，判断对象是否存在，存在返回true
        boolean isExits = user1.isPresent();
        System.out.println("获取Optional里的值------》user.isPresent " + isExits);
        //ifPresent是执行检查，接受一个Consumer(消费者) 参数，如果对象不是空的，就对执行传入的 Lambda 表达式
        user1.ifPresent(x -> System.out.println("获取Optional里的值------》name : " + x.getName()));
    }
    //返回默认值
    @Test
    public void test3() {
        User user = null;
        User user2 = new User("wesker", "22");
        //如果有值则返回该值，否则返回传递给它的参数值.
        // 如果对象的初始值不是 null，那么默认值会被忽略
        User result = Optional.ofNullable(user).orElse(user2);
        System.out.println("返回默认值------》orElse : " + result);
        //这个方法会在有值的时候返回值，
        // 如果没有值，它会执行作为参数传入的 Supplier(供应者) 函数式接口，这个接口没有入参，返回T类型对象
        // 并将返回其执行结果
        User user1 = Optional.ofNullable(user).orElseGet(() -> user2);
        System.out.println("返回默认值------》orElseGet : " + user1);
        //********orElse和orElseGet的区别
        System.out.println("--------orElse执行对象为空---------");
        User result1 = Optional.ofNullable(user).orElse(createUser());
        System.out.println("--------orElseGet执行对象为空---------");
        User user11 = Optional.ofNullable(user).orElseGet(this::createUser);
        //由上可知当对象为空而返回默认对象时，没有区别
        System.out.println("--------orElse执行对象不为空---------");
        User result11 = Optional.ofNullable(user2).orElse(createUser());
        System.out.println("--------orElseGet执行对象不为空---------");
        User user111 = Optional.ofNullable(user2).orElseGet(this::createUser);
        //当对象为不为空，两个方法都会返回非空值，
        // 不过，orElse() 方法仍然创建了 User 对象。与之相反，orElseGet() 方法不创建 User 对象
        //在执行较密集的调用时，比如调用 Web 服务或数据查询，这个差异会对性能产生重大影响
    }
    User createUser() {
        System.out.println("createUser");
        return new User("wesker","33");
    }

    //返回异常
    @Test
    public void test4() {
        User user = new User();
        //orElseThrow会在对象为空的时候抛出异常，而不是返回备选的值
        //这个方法可以决定抛出什么样的异常
        Optional.ofNullable(user).orElseThrow(IllegalArgumentException::new);
    }

    //转换值
    @Test
    public void test5() {
        User user = new User();
        String username = Optional.ofNullable(user).map(u -> u.getName()).orElse("zhangqing");
        System.out.println("转换值------》map : " + username);
        //flatMap需要函数作为参数，并对值调用这个函数，然后直接返回结果。
        // 所以getEmail里返回值用Optional.ofNullable判断email是否存在
        user.setEmail("wesker8080@foxmail.com");
        String email = Optional.ofNullable(user).flatMap(u -> u.getEmail()).orElse("default");
        System.out.println("转换值------》flatMap : " + email);
    }
    //过滤值
    @Test
    public void test6() {
        User user = new User("wesker","22");
        //filter() 接受一个 Predicate 参数，返回测试结果为 true 的值。
        // 如果测试结果为 false，会返回一个空的 Optional。
        Optional<User> user1 = Optional.ofNullable(user).filter(u -> u.getName() != null && u.getName().contains("e"));
        if (user1.isPresent()) {
            System.out.println("过滤值------》filter : " + user1.get());
        }
    }

    //Optional 类的链式方法
    @Test
    public void test7() {
        //首先，重构类，使其 getter 方法返回 Optional 引用：
        User user = new User();
        Address address = new Address();
        address.setCountry(new Country("wesker","china"));
        user.setAddress(address);

        String result = Optional.ofNullable(user).
                flatMap(User::getAddress)
                .flatMap(Address::getCountry)
                .map(Country::getLanuage)
                .orElse("default");
        System.out.println("Optional 类的链式方法------》" + result);
        List<User> users = new ArrayList<>();
    }
    public static void main(String[] args){
        //创建Optional实例
        //test1();
        //获取Optional里的值
        //test2();
        //返回默认值
        //test3();
        //返回异常
        //test4();
        //转换值
        //test5();
        //过滤值
        //test6();
        //Optional 类的链式方法
        //test7();
    }
    class User {
        private String name;
        private String sex;
        private String email;
        private Address address;
        private String phone;

        public User() {

        }
        public User(String name, String sex) {
            this.name = name;
            this.sex = sex;
        }
        public void setAddress(Address address) {
            this.address = address;
        }
        public Optional<String> getPhone() {
            return Optional.ofNullable(phone);
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }
        public Optional<String> getEmail() {
            return Optional.ofNullable(email);
        }
        public void setEmail(String email) {
            this.email = email;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    '}';
        }
    }
    class Address {
        private Country country;

        public Optional<Country> getCountry() {
            return Optional.ofNullable(country);
        }
        public void setCountry(Country country) {
            this.country = country;
        }
    }
    class Country {
        private String name;
        private String lanuage;

        public Country(String name, String lanuage) {
            this.name = name;
            this.lanuage = lanuage;
        }

        public String getLanuage() {
            return lanuage;
        }

        public void setLanuage(String lanuage) {
            this.lanuage = lanuage;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
