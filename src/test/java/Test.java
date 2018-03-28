import com.ejin.quickhttp.QuickClient;

/**
 * Created by ejin on 2018/3/26.
 */
public class Test {

    public static void main(String[] args) {
        QuickClient client = new QuickClient.Builder()
                .connectTimeout(5000)
                .setTemplate(DemoBean.class)
                .enableLog(true)
                .build();
    }

}
