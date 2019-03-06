package com.leothon.cogito.Http;

public interface UploadProgressListener {
    void onRequestProgress(long bytesWritten, long contentLength);
}
