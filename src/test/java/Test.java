import java.util.List;

/**
 * Created by ejin on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
        QuickClient client = new QuickClient.Builder().setTemplate(DemoBean.class).enableLog(true).Build();
//        client.download("http://testplc.dataserver.cn/uploadfile/inosaAPP/0000100017/inosa.apk");
//        DemoBean<List<DemoBeanDesc>>
//        Class t = Utils.getType(callback.getClass());
//        System.out.println(t.getSimpleName());
//        Object tag = new Object();
//        client.get(tag,"http://www.wanandroid.com/banner/json", new ArrayModelCallback<DemoBeanDesc>() {
//            @Override
//            void onSuccess(List<DemoBeanDesc> list) {
////                System.out.println(list);
//            }
//
//            @Override
//            void onError(int code, String error) {
//                System.out.println("error: " + code + ", " + error);
//            }
//        });
//        client.cancel(tag);
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
