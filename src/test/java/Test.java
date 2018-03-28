import com.ejin.quickhttp.ModelCallback;
import com.ejin.quickhttp.QuickClient;

/**
 * Created by ejin on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
        QuickClient client = new QuickClient.Builder()
                .enableLog(true)
                .build();

        client.get("http://www.wanandroid.com/hotkey/json", new ModelCallback<Test>() {
            @Override
            public void onSuccess(Test test) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

}
