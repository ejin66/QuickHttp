import okhttp3.internal.platform.Platform;

/**
 * Created by ejin on 2018/3/28.
 */
class Log {

    private static final int WARN = 5;
    private static final int INFO = 6;

    static void d(String msg) {
//        System.out.println(pack(msg));
        Platform.get().log(INFO, pack(msg), null);
    }

    static void i(String msg) {
//        System.out.println(pack(msg));
        Platform.get().log(INFO, pack(msg), null);
    }

    static void e(String msg) {
//        System.err.println(pack(msg));
        Platform.get().log(WARN, pack(msg), null);
    }

    private static String pack(String msg) {
        if (msg == null || "".equals(msg)) {
            msg = "print empty message";
        }

        return "------------------------------------------------------------------------------------------------------------------------------------\n"
                + msg
                + "\n------------------------------------------------------------------------------------------------------------------------------------\n";
    }
}
