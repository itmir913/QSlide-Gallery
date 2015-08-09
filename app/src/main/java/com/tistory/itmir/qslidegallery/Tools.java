package com.tistory.itmir.qslidegallery;

import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;

/**
 * Created by whdghks913 on 2015-06-21.
 */
public class Tools {
    public static Uri getTempUri() {
        return Uri.fromFile(getFile());
    }

    public static File getFile() {
        String mPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QSlideGallery/";
        String mFilePath = mPath + "QSlidePhoto.jpg";

        File mFolder = new File(mPath);
        if (!mFolder.exists()) mFolder.mkdirs();

        File mFile = new File(mFilePath);

        File mNoMediaFile = new File(mPath + ".nomedia");
        if (!mNoMediaFile.exists())
            try {
                mNoMediaFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        return mFile;
    }

    public static boolean isExists() {
        return getFile().exists();
    }
}
