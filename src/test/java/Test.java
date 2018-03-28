import java.util.List;

/**
 * Created by ejin on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
        QuickClient client = new QuickClient.Builder().setTemplate(DemoBean.class).enableLog(true).Build();
//        String data = client.sync().get("http://www.wanandroid.com/banner/json");
//        System.out.println(data);
        client.get("https://www.baidu.com/", new StringCallback() {
            @Override
            void onSuccess(String s) {

            }

            @Override
            void onError(int code, String error) {

            }
        });
    }

}
