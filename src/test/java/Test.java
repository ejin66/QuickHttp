import com.ejin.quickhttp.ArrayModelCallback;
import com.ejin.quickhttp.ModelCallback;
import com.ejin.quickhttp.QuickClient;
import com.ejin.quickhttp.SimpleCallback;

import java.util.List;

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
        QuickClient client = new QuickClient.Builder().enableLog(true).setTemplate(DemoBean.class).build();

        ArrayModelCallback callback1 = new ArrayModelCallback<DemoBeanDesc>() {
            @Override
            public void onSuccess(List<DemoBeanDesc> list) {
                System.out.println(list);
            }

            @Override
            public void onError(int code, String error) {
                System.err.println(error);
            }
        };

        client.get("http://www.wanandroid.com/hotkey/json", callback1);

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

        SimpleCallback callback2 = new SimpleCallback() {
            @Override
            public void onSuccess() {
                System.out.println("on success");
            }

            @Override
            public void onError(int code, String error) {
                System.err.println(error);
            }
        };


//        QuickClient.getDefault().get("https://www.baidu.com", callback);
        QuickClient.getDefault().get("http://www.wanandroid.com/hotkey/json", callback2);
//        QuickClient.getDefault().post("http://www.wanandroid.com/hotkey/json", callback);
//        QuickClient.getDefault().put("http://www.wanandroid.com/hotkey/json", callback);
//        QuickClient.getDefault().delete("http://www.wanandroid.com/hotkey/json", callback);

//        QuickClient.getDefault().sync().get("http://www.wanandroid.com/hotkey/json").string();
//        QuickClient.getDefault().sync().post("http://www.wanandroid.com/hotkey/json", null).string();
//        QuickClient.getDefault().sync().put("http://www.wanandroid.com/hotkey/json", null).string();
//        QuickClient.getDefault().sync().delete("http://www.wanandroid.com/hotkey/json", null).string();
    }

}
