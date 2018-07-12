package com.example.goals.cameraselfdefine;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions.RxPermissions;

public class TakePhotoActivity extends Activity implements
        SurfaceHolder.Callback, OnClickListener {

    private SurfaceView mySurfaceView = null;
    private SurfaceHolder mySurfaceHolder = null;
    private ScannerView topView = null; //自定义顶层view

    private Camera myCamera = null;
    private Camera.Parameters myParameters;

    private TextView takeTxt;
    private TextView cancelTxt;

    private boolean isPreviewing = false;
    private Bitmap bm;
    private static final String IMG_PATH = "SHZQ";
    private File file;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        int flag = WindowManager.LayoutParams.FLAG_FULLSCREEN;
        Window myWindow = this.getWindow();
        myWindow.setFlags(flag, flag);

        setContentView(R.layout.my_camera_activity);
        mySurfaceView = findViewById(R.id.sv_camera);
        mySurfaceView.setZOrderOnTop(false);
        mySurfaceHolder = mySurfaceView.getHolder();
        mySurfaceHolder.setFormat(PixelFormat.TRANSLUCENT);
        mySurfaceHolder.addCallback(this);
        mySurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        topView = findViewById(R.id.top_view);
        takeTxt = findViewById(R.id.txt_take);
        cancelTxt = findViewById(R.id.txt_cancel);
        takeTxt.setClickable(false);
        cancelTxt.setClickable(false);
        takeTxt.setOnClickListener(this);
        cancelTxt.setOnClickListener(this);

        topView.draw(new Canvas());
        //topView.setTextStrTop("行驶证首页");
        //topView.setTextStrBottom("行驶证首页");
        topView.setScannerType(1);

        checkPermission(this,
                new PermissionAction.ApplyPermissionResultListener() {
                    @Override
                    public void applyResult(boolean hasPermission, String permissionName) {
                        if (hasPermission) {
                            initCamera();
                        }
                    }
                },
                Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);

    }

    public static void checkPermission(Activity activity, final PermissionAction
            .ApplyPermissionResultListener listener, final String... permissions) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        rxPermissions.requestEach(permissions).subscribe(new PermissionAction(activity, listener));
    }

    public static boolean hasPermission(Activity activity, final String permission) {
        RxPermissions rxPermissions = new RxPermissions(activity);
        return rxPermissions.isGranted(permission);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (myCamera == null) {
            initCamera();
        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (myCamera != null) {
            isPreviewing = false;
            takeTxt.setClickable(false);
            cancelTxt.setClickable(false);
            myCamera.release(); // release the camera for other applications
            myCamera = null;
        }
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        if (myCamera != null) {
            myCamera.release();
            myCamera = null;
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        try {
            if (myCamera == null) {
                return;
            }
            myCamera.setPreviewDisplay(mySurfaceHolder);
            myCamera.startPreview();
            isPreviewing = true;
            takeTxt.setClickable(true);
            cancelTxt.setClickable(true);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        if (myCamera != null) {
            myCamera.stopPreview();
            myCamera.release();
            myCamera = null;
        }

    }

    ShutterCallback myShutterCallback = new ShutterCallback() {

        public void onShutter() {
            // TODO Auto-generated method stub

        }
    };
    PictureCallback myRawCallback = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub

        }
    };
    PictureCallback myjpegCalback = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            if (data != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
//              myCamera.stopPreview();
//              myCamera.release();
//              myCamera = null;
                isPreviewing = false;
                takeTxt.setText("确定");

                // 截取图片
                /*Bitmap sizeBitmap = Bitmap.createScaledBitmap(bitmap,
                        topView.getViewWidth(), topView.getViewHeight(), true);
                bm = Bitmap.createBitmap(sizeBitmap, topView.getRectLeft(),
                        topView.getRectTop(),
                        topView.getRectRight() - topView.getRectLeft(),
                        topView.getRectBottom() - topView.getRectTop());*/

                //不截取图片
                int width = getScreenWidth(TakePhotoActivity.this);
                int height = getScreenHeight(TakePhotoActivity.this);

                Bitmap sizeBitmap = Bitmap.createScaledBitmap(
                        bitmap,
                        height,
                        width,
                        false);

                // 旋转将要保存的图片（默认会自动旋转90度）
                Matrix matrix = new Matrix();
                matrix.postRotate(90);

                bm = Bitmap.createBitmap(
                        sizeBitmap,
                        0,
                        0,
                        height,
                        width,
                        matrix,
                        true);
            }
        }
    };

    /**
     * 获得屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获得屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    // 初始化摄像头
    public void initCamera() {
        if (myCamera == null) {
            myCamera = getCameraInstance();
        }
        if (myCamera != null) {
            myParameters = myCamera.getParameters();
            myParameters.setPictureFormat(PixelFormat.JPEG);
            myParameters.set("rotation", 90);
            if (getCameraFocusable() != null) {
                myParameters.setFocusMode(getCameraFocusable());
            }
//          myParameters.setPreviewSize(1280, 720);
//          myParameters.setPictureSize(2048, 1152); // 1280, 720

            myCamera.setDisplayOrientation(90);
            myCamera.setParameters(myParameters);
        } else {
            Toast.makeText(this, "相机错误！", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View v) {
        if (v == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.txt_take:
                //if (CommonUtils.isFastDoubleClick()) return;
                if (isPreviewing) {
                    // 拍照
                    myCamera.takePicture(myShutterCallback, myRawCallback, myjpegCalback);
                } else {
                    // 保存图片
                    storePhoto();
                }
                break;
            case R.id.txt_cancel:
                //if (CommonUtils.isFastDoubleClick()) return;
                if (isPreviewing) {
                    finish();
                    // 退出相机
                } else {
                    if (myCamera == null) {
                        initCamera();
                    }
                    // 重新拍照
                    try {
                        myCamera.setPreviewDisplay(mySurfaceHolder);
                        myCamera.startPreview();
                        isPreviewing = true;
                        takeTxt.setText("拍照");
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }

    }

    // 保存图片
    private void storePhoto() {
        File file = getOutputMediaFile();
        this.file = file;
        this.uri = Uri.fromFile(file);
        if (file != null && bm != null) {
            FileOutputStream fout;
            try {
                fout = new FileOutputStream(file);
                BufferedOutputStream bos = new BufferedOutputStream(fout);
                // Bitmap mBitmap = Bitmap.createScaledBitmap(bm,
                // topView.getViewWidth(), topView.getViewHeight(),
                // false);
                bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                // bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                bos.flush();
                bos.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //返回数据
            Intent intent = new Intent();
            intent.setData(uri);
            Bundle bundle = new Bundle();
            intent.putExtras(bundle);
            setResult(0x1001, intent);
        }
        Toast.makeText(this, "保存图片并退出", Toast.LENGTH_SHORT).show();
        finish();
    }

    private String getCameraFocusable() {
        List<String> focusModes = myParameters.getSupportedFocusModes();
        if (focusModes
                .contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
            return Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;
        } else if (focusModes.contains(Camera.Parameters.FOCUS_MODE_AUTO)) {
            return Camera.Parameters.FOCUS_MODE_AUTO;
        }
        return null;
    }

    /**
     * A safe way to get an instance of the Camera object.
     */
    public Camera getCameraInstance() {
        Camera c = null;
        try {
            if (getCameraId() >= 0) {
                c = Camera.open(getCameraId()); // attempt to get a Camera
                // instance
            } else {
                return null;
            }
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            Log.e("getCameraInstance", e.toString());
        }
        return c; // returns null if camera is unavailable
    }

    private int getCameraId() {
        if (!checkCameraHardware(this)) {
            return -1;
        }
        int cNum = Camera.getNumberOfCameras();
        int defaultCameraId = -1;
        CameraInfo cameraInfo = new CameraInfo();
        for (int i = 0; i < cNum; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == CameraInfo.CAMERA_FACING_BACK) {
                defaultCameraId = i;
            }
        }
        return defaultCameraId;
    }

    /**
     * Check if this device has a camera
     */
    private boolean checkCameraHardware(Context context) {
        if (context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Create a File for saving an image or video
     */
    private File getOutputMediaFile() {
        File mediaStorageDir = null;
        if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED)) {
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    IMG_PATH);
            if (!mediaStorageDir.exists()) {
                if (!mediaStorageDir.mkdirs()) {
                    return null;
                }
            }
        } else {
            Toast.makeText(this, "没有sd卡", Toast.LENGTH_SHORT).show();
            return null;
        }
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + timeStamp + ".jpg");

        return mediaFile;
    }

}
