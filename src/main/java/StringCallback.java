import java.nio.charset.Charset;

/**
 * Created by ejin on 2018/3/27.
 */
public abstract class StringCallback extends DataCallback {

    abstract void onSuccess(String s);

    abstract void onError(int code, String error);

    @Override
    void onSuccess(byte[] bytes) {
        String s = new String(bytes, Charset.forName("UTF-8"));
        onSuccess(s);
    }
}
