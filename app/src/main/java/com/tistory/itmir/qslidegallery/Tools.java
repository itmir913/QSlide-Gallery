package com.tistory.itmir.qslidegallery;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

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

        return mFile;
    }

    public static boolean isExists() {
        return getFile().exists();
    }
}
