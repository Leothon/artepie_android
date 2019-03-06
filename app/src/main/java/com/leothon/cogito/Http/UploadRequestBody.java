package com.leothon.cogito.Http;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;


public class UploadRequestBody extends RequestBody {
    private File mFile;
    private UploadProgressListener mUploadListener;

    private String mMediaType;
    private int mEachBufferSize = 1024;
    public UploadRequestBody(File file, String mMediaType,UploadProgressListener uploadListener) {
        this.mFile = file;
        this.mMediaType = mMediaType;
        mUploadListener = uploadListener;
    }

    public UploadRequestBody(final File file, String mediaType, int eachBufferSize, final UploadProgressListener listener) {
        mFile = file;
        mMediaType = mediaType;
        mEachBufferSize = eachBufferSize;
        mUploadListener = listener; }

    @Override
    public MediaType contentType() {
        return MediaType.parse(mMediaType);
    }
    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        long fileLength = mFile.length();
        byte[] buffer = new byte[mEachBufferSize];
        FileInputStream in = new FileInputStream(mFile);
        long uploaded = 0;
        try {
            int read;
            Handler handler = new Handler(Looper.getMainLooper());
            while ((read = in.read(buffer)) != -1) {
                // update progress on UI thread
                handler.post(new ProgressUpdater(uploaded, fileLength));
                uploaded += read;
                sink.write(buffer, 0, read);
            }
        } finally {
            in.close();
        }


    }


    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;
        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }
        @Override
        public void run() {
            mUploadListener.onRequestProgress(mUploaded,mTotal);
        }
    }

}
