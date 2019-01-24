package callback;

import java.util.Optional;

/**
 * 接口回调Lambda编程
 *
 * @author MR.ZHANG
 * @create 2018-12-12 16:20
 */
public class CallBackLambdaTest {
    private OnClickListener listener;
    interface OnClickListener{
        void onClick(int index, double d);
    }

    /**
     *
     * @param listener OnClickListener接口
     */
    public void setOnclickListener(OnClickListener listener) {
        this.listener = listener;
    }

    public void begin() {
        Optional<OnClickListener> listener = Optional.ofNullable(this.listener);
        listener.ifPresent(x -> x.onClick(1,2.0));
    }
}
