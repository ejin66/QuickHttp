import com.ejin.quickhttp.Code;
import com.ejin.quickhttp.Data;
import com.ejin.quickhttp.Error;

/**
 * Created by ejin on 2018/3/27.
 */
public class DemoBean {

    @Code
    public int errorCode;
    @Error
    public String errorMsg;
    @Data
    public String data;

    @Override
    public String toString() {
        return "DemoBean{" +
                "errorCode=" + errorCode +
                ", errorMsg='" + errorMsg + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
