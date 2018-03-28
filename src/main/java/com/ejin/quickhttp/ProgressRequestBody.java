package com.ejin.quickhttp;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.*;

import java.io.IOException;

/**
 * Created by ejin on 2018/3/27.
 */
public class ProgressRequestBody extends RequestBody {

    private RequestBody requestBody;
    private ProgressCallback callback;
    private BufferedSink bufferedSink;

    public ProgressRequestBody(RequestBody body, ProgressCallback callback) {
        this.requestBody = body;
        this.callback = callback;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            bufferedSink = Okio.buffer(sink(sink));
        }
        requestBody.writeTo(bufferedSink);
        bufferedSink.flush();
    }

    private Sink sink(BufferedSink sink) {
        return new ForwardingSink(sink) {
            long bytesWritten = 0L;
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    contentLength = contentLength();
                }

                bytesWritten += byteCount;
                int progress = (int) (100 * bytesWritten / (float) contentLength);
                callback.onProgress(progress);
            }
        };
    }
}
