import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * java8时间API测试用例，方便以后直接使用
 *
 * ●Instant——它代表的是时间戳
 *
 * ●LocalDate——不包含具体时间的日期，比如2014-01-14。它可以用来存储生日，周年纪念日，入职日期等。
 *
 * ●LocalTime——它代表的是不含日期的时间
 *
 * ●LocalDateTime——它包含了日期及时间，不过还是没有偏移信息或者说时区。
 *
 * ●ZonedDateTime——这是一个包含时区的完整的日期时间，偏移量是以UTC/格林威治时间为基准的。
 * @author MR.ZHANG
 * @create 2018-11-29 9:33
 */
public class Java8Time {
    private static void getToday() {
        LocalDate now = LocalDate.now();
        System.out.println("now is : " + now.toString());
        //now is : 2018-11-29
    }
    private static void getNowTime() {
        LocalTime now = LocalTime.now();
        System.out.println("now is : " + now.toString());
        //now is : 09:53:01.208
    }
    private static void getLocalDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("now is : " + now.toString());
        //2018-11-29T10:29:34.498
    }
    private static void getYear() {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        System.out.println("now year is : " + year);
        //now year is : 2018
    }
    private static void getMonth() {
        LocalDate now = LocalDate.now();
        Month month = now.getMonth();
        System.out.println("now month is : " + month);
        //now month is : NOVEMBER
        int monthValue = now.getMonthValue();
        System.out.println("now monthValue is : " + monthValue);
        //now monthValue is : 11
    }
    private static void getDayOfMonth() {
        LocalDate now = LocalDate.now();
        int dayOfMonth = now.getDayOfMonth();
        System.out.println("now dayOfMonth is : " + dayOfMonth);
        //now dayOfMonth is : 29
    }
    private static void getDayOfWeek() {
        LocalDate now = LocalDate.now();
        DayOfWeek dayOfWeek = now.getDayOfWeek();
        System.out.println("now dayOfWeek is : " + dayOfWeek);
        //now dayOfWeek is : THURSDAY
    }
    private static void getDayOfYear() {
        LocalDate now = LocalDate.now();
        int dayOfYear = now.getDayOfYear();
        System.out.println("now dayOfYear is : " + dayOfYear);
        //now dayOfYear is : 333
    }
    private static void getCustomDate() {
        LocalDate date = LocalDate.of(1994, 5, 16);
        System.out.println("getCustomDate : " + date);
        LocalDateTime date1 = LocalDateTime.of(2019, 12, 23,9,58,0);
        long milli = date1.toInstant(ZoneOffset.of("+8")).toEpochMilli();
        System.out.println("getCustomDate milli: " + milli);
        LocalDate now = LocalDate.now();
        LocalDateTime date2 = LocalDateTime.of(now.getYear(), now.getMonth(), 1,0,0,0);

        System.out.println(date2.toString()+"");

    }
    private static void dateIsEqual() {
        LocalDate date = LocalDate.of(1994, 5, 16);
        LocalDate now = LocalDate.now();
        System.out.println("dateIsEqual : " + now.equals(date));
    }
    private static void monthDay() {
        MonthDay of = MonthDay.of(5, 16);
        LocalDate date = LocalDate.now();
        MonthDay from = MonthDay.from(date);
        System.out.println("of : " + of);
        System.out.println("from : " + from);
        //of : --05-16
        //from : --11-29
    }
    private static void plusHours() {
        LocalTime now = LocalTime.now();
        System.out.println("now : " + now);
        //now : 10:04:11.772
        LocalTime time = now.plusHours(2);
        System.out.println("time : " + time);
        //time : 12:04:11.772
    }
    private static void plusDays() {
        LocalDate now = LocalDate.now();
        System.out.println("now : " + now);
        LocalDate date = now.plusDays(3);
        System.out.println("date : " + date);
        //一星期后的时间，当然也可以增加年
        LocalDate weekDay = now.plus(1, ChronoUnit.WEEKS);
        System.out.println("weekDay : " + weekDay);
    }
    private static void getUtcTime() {
        long clock = Clock.systemUTC().millis();
        System.out.println("clock : " + clock);
    }
    private static void getDefaultZone() {
        ZoneId zone = Clock.systemDefaultZone().getZone();
        System.out.println("zone : " + zone);
        //zone : Asia/Shanghai
    }
    private static void isBeforeOrAfter() {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(1994, 5, 16);
        boolean after = now.isAfter(date);
        System.out.println("after : " + after);
        //after : true
    }
    private static void getZoneDateTime() {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println("now : " + now);
        //now : 2018-11-29T10:45:38.441+08:00[Asia/Shanghai]
    }
    private static void localDateTime2ZoneDateTime() {
        LocalDateTime now = LocalDateTime.now();
        System.out.println("before change : " + now);
        //America/Chicago
        ZoneId zoneId = ZoneId.of(ZoneId.SHORT_IDS.get("PLT"));

        ZonedDateTime of = ZonedDateTime.of(now, zoneId);
        System.out.println("after change : " + of);
    }
    private static void yearMonth() {
        YearMonth of = YearMonth.of(2018, 2);
        //当月有多少天，可用于判断闰月
        int i = of.lengthOfMonth();
        System.out.println("this month has " + i + " days");
    }
    private static void isLeapYear() {
        LocalDate now = LocalDate.now();
        boolean leapYear = now.isLeapYear();
        System.out.println("is leapYear : " + leapYear);
    }
    private static void countDaysOrMonths() {
        LocalDate now = LocalDate.now();
        LocalDate date = LocalDate.of(2018, Month.SEPTEMBER, 5);
        Period period = Period.between(date, now);
        System.out.printf("日期%s和日期%s相差%d天", date, now, period.getDays());
        System.out.println();
        //日期2018-11-29和日期2018-09-05相差24天
    }
    private static void countTimesOrDay() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = now.plusMinutes(30);
        Duration period = Duration.between(now, date);
        System.out.printf("日期%s和日期%s相差%d分", date, now, period.toMinutes());
        System.out.println();
        //日期2019-01-21T16:55:58.753和日期2019-01-21T16:25:58.753相差30分
    }
    private static void getTimestamp() {
        Instant now = Instant.now();
        System.out.println("now : " + now);
        //now : 2018-11-29T03:38:38.793Z
    }
    private static void parseTime() {
        String date = "2018-11-23 12:13:45";
        try {
            DateTimeFormatter pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            //LocalDate parse = LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
            LocalDateTime parse = LocalDateTime.parse(date, pattern);

            System.out.println("parse : " + parse);
        } catch (Exception e) {
            System.out.println("parse error: " + e.getMessage());

        }
    }
    private static void customParseTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("YYYY_MM_dd hh:mm:ss");
        String format = pattern.format(now);
        System.out.println("format : " + format);
    }
    private static void getInstantTime() {
        Instant now = Instant.now();
        System.out.println("now:"+now);
    }
    private static void getCustomInstantTime() {
        Instant now = Instant.now();
        //转为北京时间 +8小时
        Instant instant = now.plusMillis(TimeUnit.HOURS.toMillis(8));
        System.out.println("instant:"+instant);
    }

    public static void dateConvertToLocalDateTime(Date date) {
        LocalDateTime dateTime = date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
        System.out.println("dateTime : " + dateTime);
    }
    public static void getWeek() {
        LocalDate localDate = LocalDate.now();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        System.out.println("dayOfWeek : " + dayOfWeek);
    }
    /**
     * 描述 获取今天星期几
     *
     * @param
     * @return String
     * @author ***
     * @date 2018/11/26 10:53
     */
    public static String getDayOfTheWeek() {
        String[][] strArray = {{"MONDAY", "一"}, {"TUESDAY", "二"}, {"WEDNESDAY", "三"}, {"THURSDAY", "四"}, {"FRIDAY", "五"}, {"SATURDAY", "六"}, {"SUNDAY", "日"}};

        LocalDate currentDate = LocalDate.now();
        String k = String.valueOf(currentDate.getDayOfWeek());
        //获取行数
        for (int i = 0; i < strArray.length; i++) {
            if (k.equals(strArray[i][0])) {
                k = strArray[i][1];
                break;
            }
        }
        System.out.println("getDayOfTheWeek : " + "星期" + k);
        return "星期" + k;
    }

    public static void localDateTimeConvertToDate(LocalDateTime localDateTime) {
        Date date = Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
        System.out.println("date : " + date);
    }
    public static void main(String[] args){
        getToday();
        getNowTime();
        getLocalDateTime();
        getZoneDateTime();
        getYear();
        getMonth();
        getDayOfMonth();
        getDayOfWeek();
        getDayOfYear();
        //获取某个特定的日期
        getCustomDate();
        //判断两个日期是否相等
        dateIsEqual();
        //MonthDay类
        monthDay();
        //增加时间操作,可增加时 分 秒 和 任意时间
        plusHours();
        //增加日期操作，可增加年，月，日，星期 和 任意时间
        plusDays();
        //用来代替System.currentTimeMillis()
        getUtcTime();
        //获取当前时区
        getDefaultZone();
        //判断某个日期在另一个日期的前面还是后面
        isBeforeOrAfter();
        //将本地时间转换成另一个时区中的对应时间
        localDateTime2ZoneDateTime();
        yearMonth();
        //检查闰年
        isLeapYear();
        //两个日期之间的天数，月数
        countDaysOrMonths();
        //获取当前时间戳
        getTimestamp();
        //时间格式转化
        parseTime();
        //使用自定义的格式器来解析日期转成字符串
        customParseTime();
        //获取0时区时间,与北京时区相差8小时
        getInstantTime();
        //获取指定时区时间
        getCustomInstantTime();
        //将java.util.Date 转换为java8 的java.time.LocalDateTime,默认时区为东8区
        dateConvertToLocalDateTime(new Date());
        //将java8 的 java.time.LocalDateTime 转换为 java.util.Date，默认时区为东8区
        localDateTimeConvertToDate(LocalDateTime.now());
        // 两个日期之前的分钟差或秒差
        countTimesOrDay();
        // 获取星期(英文)
        getWeek();
        // 获取星期（中文）
        getDayOfTheWeek();

        // 毫秒转日期
        systemToDate();
    }

    private static void systemToDate() {
        long l = System.currentTimeMillis();
        Instant timestamp = Instant.ofEpochMilli(l);
        ZonedDateTime losAngelesTime = timestamp.atZone(ZoneId.of("Asia/Shanghai"));
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("YYYY-MM-dd hh:mm:ss");
        LocalDateTime localDateTime = losAngelesTime.toLocalDateTime();
        String format = pattern.format(localDateTime);
        System.out.println(format);
    }
}
