import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by ejin on 2018/3/27.
 */
abstract class BaseCallback implements Callback {

    private QuickClient client;
    private boolean enableLog = false;
    private Class templateClass;
    private int successCode = 0;

    BaseCallback set(QuickClient client) {
        this.client = client;
        this.enableLog = client.enableLog;
        this.templateClass = client.templateClass;
        this.successCode = client.successCode;
        return this;
    }

    boolean enableLog() {
        return enableLog;
    }

    Class getTemplateClass() {
        return templateClass;
    }

    int getSuccessCode() {
        return successCode;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        client.remove(call);
        client = null;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        client.remove(call);
        client = null;
    }
}
