package callback;

/**
 * @author MR.ZHANG
 * @create 2018-12-12 16:24
 */
public class Main {




    public static void main(String[] args){
        CallBackLambdaTest test = new CallBackLambdaTest();
        test.setOnclickListener((index,d) -> System.out.println(index+"--"+d));
        test.begin();
    }
}
