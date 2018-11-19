import com.ejin.quickhttp.ModelCallback;
import com.ejin.quickhttp.QuickClient;

/**
 * Created by ejin on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
//        QuickClient client = new QuickClient.Builder()
//                .enableLog(true)
//                .build();

//        StringCallback callback = new StringCallback() {
//            @Override
//            public void onSuccess(String s) {
//                System.out.println(s);
//            }
//
//            @Override
//            public void onError(int code, String error) {
//                System.err.println(error);
//            }
//        };

        ModelCallback callback = new ModelCallback<DemoBean>() {

            @Override
            public void onSuccess(DemoBean demoBean) {
                System.out.println(demoBean);
            }

            @Override
            public void onError(int code, String error) {
                System.err.println(error);
            }
        };

        QuickClient.getDefault().get("http://www.wanandroid.com/hotkey/json", callback);
        QuickClient.getDefault().post("http://www.wanandroid.com/hotkey/json", callback);
        QuickClient.getDefault().put("http://www.wanandroid.com/hotkey/json", callback);
        QuickClient.getDefault().delete("http://www.wanandroid.com/hotkey/json", callback);

//        QuickClient.getDefault().sync().get("http://www.wanandroid.com/hotkey/json").string();
//        QuickClient.getDefault().sync().post("http://www.wanandroid.com/hotkey/json", null).string();
//        QuickClient.getDefault().sync().put("http://www.wanandroid.com/hotkey/json", null).string();
//        QuickClient.getDefault().sync().delete("http://www.wanandroid.com/hotkey/json", null).string();
    }

}
