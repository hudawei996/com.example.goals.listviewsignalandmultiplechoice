package com.example.goals.cameraselfdefine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

import com.tbruyelle.rxpermissions.Permission;

import rx.functions.Action1;

/**
 * User: PAPA
 * Date: 2017-06-03
 * Description:
 */
public class PermissionAction implements Action1<Permission> {

    private Context mContext;
    private static final String PACKAGE_URL_SCHEME = "package:"; // 方案
    private ApplyPermissionResultListener mResultListener;

    public PermissionAction(Context context, ApplyPermissionResultListener listener) {
        mContext = context;
        mResultListener = listener;
    }

    @Override
    public void call(Permission permission) {
        if (permission.granted) {
            // `permission.name` is granted !
            if (mResultListener != null)
                mResultListener.applyResult(true, permission.name);

        } else if (permission.shouldShowRequestPermissionRationale) {
            // Denied permission without ask never again
            showMissingPermissionDialog(permission);
        } else {
            // Denied permission with ask never again
            // Need to go to the settings
            showMissingPermissionDialog(permission);
        }

    }


    // 显示缺失权限提示
    private void showMissingPermissionDialog(final Permission permission) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setCancelable(false);
        builder.setTitle(R.string.help);
        builder.setMessage(R.string.string_help_text);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.quit, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (mResultListener != null)
                    mResultListener.applyResult(false, permission.name);
            }
        });

        builder.setPositiveButton(R.string.settings, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startAppSettings();
            }
        });

        builder.show();
    }


    // 启动应用的设置
    private void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mContext.getPackageName()));
        mContext.startActivity(intent);
    }


    public interface ApplyPermissionResultListener {
        public void applyResult(boolean hasPermission, String permissionName);
    }
}
