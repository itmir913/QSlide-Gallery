package com.tistory.itmir.qslidegallery;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.lge.app.floating.FloatableActivity;
import com.lge.app.floating.FloatingWindow;

public class QSlideGalleryActivity extends FloatableActivity {
    private final int REQ_CODE_PICK_PICTURE = 1;

    private ImageView mImageView;
    private Button mFloatingButton;
    private CheckBox mHideTitle;

//    private Bitmap mBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qslide_fullscreen_gallery);
        mHideTitle = (CheckBox) findViewById(R.id.mHideTitle);
        setContentViewForFloatingMode(R.layout.activity_qslide_floating_gallery);
    }

    View.OnTouchListener mFloatingListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            boolean isInFloatingMode = isInFloatingMode();

            if (event.getAction() == MotionEvent.ACTION_UP) {
                // QSlide
                if (isInFloatingMode) {
                    getFloatingWindow().close(true);
                    Intent mIntent = new Intent(getApplicationContext(), QSlideGalleryActivity.class);
                    mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(mIntent);
                }
            }

            return true;
        }
    };

    /**
     * http://stackoverflow.com/questions/10776438/crop-an-image-when-selected-from-gallery-in-android
     */
    public void mChooseButton(View v) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        pickImageIntent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        pickImageIntent.putExtra("crop", "true");
//        pickImageIntent.putExtra("outputX", 200);
//        pickImageIntent.putExtra("outputY", 200);
//        pickImageIntent.putExtra("aspectX", 1);
//        pickImageIntent.putExtra("aspectY", 1);
        pickImageIntent.putExtra("scale", true);
        pickImageIntent.putExtra(MediaStore.EXTRA_OUTPUT, Tools.getTempUri());
        pickImageIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(pickImageIntent, REQ_CODE_PICK_PICTURE);
    }

    public void mQSlideButton(View v) {
        if (!Tools.isExists()) {
            Toast.makeText(getApplicationContext(), R.string.error_not_choosing_photo, Toast.LENGTH_SHORT).show();
        } else {
            FloatingWindow.LayoutParams mParams = new FloatingWindow.LayoutParams(this);
//            mParams.resizeOption = FloatingWindow.ResizeOption.PROPORTIONAL;
            mParams.useOverlappingTitle = mHideTitle.isChecked();
            mParams.minWidthWeight = 0.3f;
            mParams.minHeightWeight = 0.2f;
            switchToFloatingMode(mParams);

//            boolean useOverlay = true;
//            boolean useOverlappingTitle = mHideTitle.isChecked();
//            boolean isResizable = true;

            // switch to the floating window mode
//            switchToFloatingMode(useOverlay, useOverlappingTitle, isResizable, null);
        }
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent mData) {
        if (requestCode == REQ_CODE_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                mQSlideButton(null);
            }
        }
    }*/

    // This is called when the floating window becomes visible to the user.
    @Override
    public void onAttachedToFloatingWindow(FloatingWindow mFloatingWindow) {
        /* all resources should be reinitialized once again
         * if you set new layout for the floating mode setContentViewForFloatingMode()*/
        mImageView = (ImageView) findViewById(R.id.mImageView);
        mFloatingButton = (Button) findViewById(R.id.mFloatingButton);

        Uri mUri = Tools.getTempUri();
        if (mUri != null)
            mImageView.setImageURI(mUri);
//        if (mBitmap != null)
//            mImageView.setImageBitmap(mBitmap);
        mFloatingButton.setOnTouchListener(mFloatingListener);

        FloatingWindow.LayoutParams mParams = mFloatingWindow.getLayoutParams();
        mParams.minWidthWeight = 0.3f;
        mParams.minHeightWeight = 0.2f;
        mFloatingWindow.updateLayoutParams(mParams);

        // set an onUpdateListener to limit the width of the floating window
//        mFloatingWindow.setOnUpdateListener(new FloatingWindow.DefaultOnUpdateListener() {
//            public void onResizeFinished(FloatingWindow mWindow, int width, int height) {
//                if (width > 1000)
//                    mWindow.setSize(1000, height);
//            }
//        });
    }

    // This is called when the floating window is closed.
    @Override
    public boolean onDetachedFromFloatingWindow(FloatingWindow w, boolean isReturningToFullScreen) {
        // set the last position of the floating window when the window is closing
//        Intent intent = getIntent();
//        intent.putExtra("posX", w.getLayoutParams().x);
//        intent.putExtra("posY", w.getLayoutParams().y);

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!isSwitchingToFloatingMode()) {
            // release application specific resources here
            // only when application isn't switching to floating window mode
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (!isSwitchingToFloatingMode()) {
            // release application specific resources here
            // only when application isn't switching to floating window mode
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!isSwitchingToFloatingMode()) {
            // release application specific resources here
            // only when application isn't switching to floating window mode
        }
    }

}
