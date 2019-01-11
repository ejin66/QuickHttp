import com.ejin.quickhttp.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.Call;
import okhttp3.Dns;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ejin on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
//mvn clean deploy -P release
//        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new EnumTypeAdapterFactory()).create();
//
//        TBean tBean = new TBean(TEnum.FIRST, TEnum.SECOND);
//
//        System.out.println(gson.toJson(tBean));
//
//        String t = "{\"t1\":5,\"t2\":\"second2\"}";
//        System.out.println(gson.fromJson(t, TBean.class));

        QuickClient client = new QuickClient.Builder()
                .enableLog(true)
                .build();

        //https://elevtest.inovance.com:6004
        client.get("https://www.baidu.com.com", new StringCallback() {
            @Override
            public void onSuccess(String s) {

            }

            @Override
            public void onError(int code, String error) {

            }
        });

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
//        QuickClient client = new QuickClient.Builder().enableLog(true).setTemplate(DemoBean.class).build();
//
//        ArrayModelCallback callback1 = new ArrayModelCallback<DemoBeanDesc>() {
//            @Override
//            public void onSuccess(List<DemoBeanDesc> list) {
//                System.out.println(list);
//            }
//
//            @Override
//            public void onError(int code, String error) {
//                System.err.println(error);
//            }
//        };
//
//        client.get("http://www.wanandroid.com/hotkey/json", callback1);
//
//        ModelCallback callback = new ModelCallback<DemoBean>() {
//
//            @Override
//            public void onSuccess(DemoBean demoBean) {
//                System.out.println(demoBean);
//            }
//
//            @Override
//            public void onError(int code, String error) {
//                System.err.println(error);
//            }
//        };
//
//        SimpleCallback callback2 = new SimpleCallback() {
//            @Override
//            public void onSuccess() {
//                System.out.println("on success");
//            }
//
//            @Override
//            public void onError(int code, String error) {
//                System.err.println(error);
//            }
//        };


//        QuickClient.getDefault().get("https://www.baidu.com", callback);
//        QuickClient.getDefault().get("http://www.wanandroid.com/hotkey/json", callback2);
//        QuickClient.getDefault().post("http://www.wanandroid.com/hotkey/json", callback);
//        QuickClient.getDefault().put("http://www.wanandroid.com/hotkey/json", callback);
//        QuickClient.getDefault().delete("http://www.wanandroid.com/hotkey/json", callback);

//        QuickClient.getDefault().sync().get("http://www.wanandroid.com/hotkey/json").string();
//        QuickClient.getDefault().sync().post("http://www.wanandroid.com/hotkey/json", null).string();
//        QuickClient.getDefault().sync().put("http://www.wanandroid.com/hotkey/json", null).string();
//        QuickClient.getDefault().sync().delete("http://www.wanandroid.com/hotkey/json", null).string();
    }

}
