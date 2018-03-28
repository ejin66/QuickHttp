/**
 * Created by ejin on 2018/3/27.
 */
public interface ProgressCallback {

    void onSuccess();

    void onError(int code, String error);

    /**
     * @param progress 0-100
     */
    void onProgress(int progress);

}
