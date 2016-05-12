package com.hm.zhihuclient.frag_setup;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import com.hm.zhihuclient.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ShortCutUtils {

    private static String AUTHORITY = null;

    /**
     * 在桌面添加快捷方式
     *
     * @param icon 快捷方式图标
     * @param name 快捷方式名称
     * @param uri  快捷方式的intent Uri
     */

    public static void addShortcut(Context context, Bitmap icon, String name, Uri uri) {

        Intent intentAddShortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
//		intentAddShortcut.setAction(action)
        intentAddShortcut.putExtra("duplicate", false);
        // 添加名称

        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);

        // 添加图标
        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        // 设置Launcher的Uri数据

        Intent intentLauncher = new Intent(Intent.ACTION_VIEW);
        intentAddShortcut.setClass(context, MainActivity.class);
        // Intent intentLauncher=AppNotificationInit.createShort;
        // intentLauncher.setAction(Intent.ACTION_VIEW);
        // intentLauncher.addCategory(Intent.CATEGORY_LAUNCHER);
        // if (intentLauncher == null) {
        // intentLauncher = new Intent();
        // }
        intentLauncher.setData(uri);
        // intentLauncher.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 添加快捷方式的启动方法

        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intentLauncher);
        // AppNotificationInit init = new AppNotificationInit();
        // init.creatShortCutWebIntent(uri, name, icon);
        context.sendBroadcast(intentAddShortcut);

    }

    public static void deleWebShortCut(Context context, String name, Uri uri) {
        Intent intentAddShortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        // 添加名称

        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        // 设置Launcher的Uri数据

        Intent intentLauncher = new Intent(Intent.ACTION_VIEW);
        // intentLauncher.addCategory(Intent.CATEGORY_LAUNCHER);
        // if (intentLauncher == null) {
        // intentLauncher = new Intent(Intent.ACTION_VIEW);
        // }

        intentLauncher.setData(uri);

        // 添加快捷方式的启动方法

        intentAddShortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, intentLauncher);
        // AppNotificationInit init = new AppNotificationInit();
        context.sendBroadcast(intentAddShortcut);

    }

    private static Intent createShortcutIntent(Context context, String packageName, String activityName) {
        // Intent intent = AppNotificationInit.createShort;
        // intent.setAction(Intent.ACTION_MAIN);
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setClassName(context, packageName);

        ComponentName comp = new ComponentName(packageName,
                activityName);
        intent.setComponent(comp);
        return intent;
    }



    public static void delShortcut(Context context, String packageName, String appName, boolean isInstall, String activityName) {
        // Logger.e("delShortcut", "delShortcut");
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, createShortcutIntent(context, packageName, activityName));
        context.sendBroadcast(shortcut);
    }

    public static void createShortcut(Context context, Bitmap icon, String packageName, String appName, String activityName) {
        // Logger.e("createShortcut", "createShortcut");
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);
        shortcut.putExtra("duplicate", false);// 设置是否重复创建

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);

        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT, createShortcutIntent(context, packageName, activityName));
        // AppNotificationInit init = new AppNotificationInit();
        context.sendBroadcast(shortcut);
        // AppNotificationInit.creatShortCut(icon, packageName, appName,
        // isInstall, activityName);
    }
    public static<T>  void  createShortcut(Context context, int resDrawable, String appName,Class<T> activity) {
        Intent addShortcutIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // 不允许重复创建
        addShortcutIntent.putExtra("duplicate", false);// 经测试不是根据快捷方式的名字判断重复的
        // 应该是根据快链的Intent来判断是否重复的,即Intent.EXTRA_SHORTCUT_INTENT字段的value
        // 但是名称不同时，虽然有的手机系统会显示Toast提示重复，仍然会建立快链
        // 屏幕上没有空间时会提示
        // 注意：重复创建的行为MIUI和三星手机上不太一样，小米上似乎不能重复创建快捷方式

        // 名字
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, appName);

        // 图标
        addShortcutIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,
                Intent.ShortcutIconResource.fromContext(context,
                        resDrawable));

        // 设置关联程序
        Intent launcherIntent = new Intent(Intent.ACTION_MAIN);
        launcherIntent.setClass(context,activity);
        launcherIntent.addCategory(Intent.CATEGORY_LAUNCHER);

        addShortcutIntent
                .putExtra(Intent.EXTRA_SHORTCUT_INTENT, launcherIntent);

        // 发送广播
        context.sendBroadcast(addShortcutIntent);
    }

    // /**
    // * 判断是否存在快捷方式
    // * */
    public static boolean hasShortCut(Context context, String shortcutName) {
        String url = null;
        if (android.os.Build.VERSION.SDK_INT < 8) {
            url = "content://com.android.launcher.settings/favorites?notify=true";
        } else {
            url = "content://com.android.launcher2.settings/favorites?notify=true";
        }
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = resolver.query(Uri.parse(url), null, "title=?", new String[]{shortcutName}, null);

        if (cursor != null && cursor.moveToFirst()) {
            cursor.close();
            return true;
        }
        return false;
    }

    public static boolean getLocalGame(Context ctx, String name) {
        List<String> pkgs = new ArrayList<String>();

        List<PackageInfo> packageInfoList = getAllInstallPackages(ctx);
        for (int i = 0; i < packageInfoList.size(); i++) {
            PackageInfo packageInfo = packageInfoList.get(i);
            // 过滤掉系统程序
            if (!isSystemPackage(packageInfo.applicationInfo) && packageInfo.packageName.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取所有已经安装的APK
     *
     * @param ctx
     * @return
     */
    public static List<PackageInfo> getAllInstallPackages(Context ctx) {
        PackageManager pm = ctx.getPackageManager();
        List<PackageInfo> allPackageInfos = pm.getInstalledPackages(0);
        return allPackageInfos;
    }

    /**
     * 是否系统包
     *
     * @return
     */
    public static boolean isSystemPackage(ApplicationInfo info) {
        return (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    // private Bitmap getShortCutBitmap() {
    // Bitmap output = Bitmap.createBitmap(250, 250, Config.ARGB_8888);
    // Canvas mCanvas = new Canvas(output);
    // final int color = 0xff424242;
    // final Paint paint = new Paint();
    // paint.setAntiAlias(true);
    // mCanvas.drawARGB(0, 0, 0, 0);
    // paint.setColor(color);
    // Paint backPaint = new Paint();
    // backPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
    // // backPaint.setColor(Color.argb(160, 255, 255, 255));
    // backPaint.setColor(Color.rgb(121, 133, 140));
    //
    // // List<ApplicationItem> packageInfoList = getInstallGameListData();
    // // List<ApplicationItem> packageInfoList = installGameList;
    // // List<PackageInfo> packageInfoList =
    // // AndroidPackageUtils.getAllInstallPackages(this);
    //
    // if (installGameList == null || installGameList.size() == 0) {
    // mCanvas.drawBitmap(BitmapUtils.readBitmap(this, R.drawable.shortcut),
    // null, new RectF(0, 0, 250, 250), paint);
    // return output;
    // }
    // mCanvas.drawRoundRect(new RectF(0, 0, 250, 250), 8, 8, backPaint);
    // // for(int i = z; i < z + 4 && i < packageInfoList.size(); i++){
    // // //
    // //
    // mCanvas.drawBitmap(BitmapUtils.drawable2Bitmap(packageInfoList.get(i).applicationInfo.loadIcon(this.getApplicationContext().getPackageManager())),
    // // null, rect[i-z], paint);
    // // //
    // //
    // mCanvas.drawBitmap(BitmapUtils.drawable2Bitmap(packageInfoList.get(i).applicationInfo.loadIcon(this.getApplicationContext().getPackageManager())),
    // // null, rect[i-z], paint);
    // //
    // mCanvas.drawBitmap(BitmapUtils.drawable2Bitmap(AndroidPackageUtils.getInstallAPKInfo(this,
    // // packageInfoList.get(i).applicationInfo.packageName)), null,
    // // rect[i-z], paint);
    // // }
    // for (int i = 0; i < 4 && i < installGameList.size(); i++) {
    // mCanvas.drawBitmap(BitmapUtils.drawable2Bitmap(AndroidPackageUtils.getInstallAPKInfo(this,
    // installGameList.get(i).applicationInfo.appPackageName)), null, rect[i],
    // paint);
    // }
    // return output;
    // }

    /**
     * 判断快捷方式是否存在
     *
     * @param context
     * @param title
     * @return
     */
    public static boolean isShortCutExist(Context context, String title) {

        boolean isInstallShortcut = false;

        if (null == context || TextUtils.isEmpty(title))
            return isInstallShortcut;

        if (TextUtils.isEmpty(AUTHORITY))
            AUTHORITY = getAuthorityFromPermission(context);

        final ContentResolver cr = context.getContentResolver();

        if (!TextUtils.isEmpty(AUTHORITY)) {
            try {
                final Uri CONTENT_URI = Uri.parse(AUTHORITY);

                Cursor c = cr.query(CONTENT_URI, new String[]{"title", "iconResource"}, "title=?", new String[]{title}, null);

                // XXX表示应用名称。
                if (c != null && c.getCount() > 0) {
                    isInstallShortcut = true;
                }
                if (null != c && !c.isClosed())
                    c.close();
            } catch (Exception e) {
                // TODO: handle exception
                Log.e("isShortCutExist", e.getMessage());
            }

        }
        return isInstallShortcut;

    }

    public static String getCurrentLauncherPackageName(Context context) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        ResolveInfo res = context.getPackageManager().resolveActivity(intent, 0);
        if (res == null || res.activityInfo == null) {
            // should not happen. A home is always installed, isn't it?
            return "";
        }
        if (res.activityInfo.packageName.equals("android")) {
            return "";
        } else {
            return res.activityInfo.packageName;
        }
    }

    public static String getAuthorityFromPermissionDefault(Context context) {

        return getThirdAuthorityFromPermission(context, "com.android.launcher.permission.READ_SETTINGS");
    }

    public static String getThirdAuthorityFromPermission(Context context, String permission) {
        if (TextUtils.isEmpty(permission)) {
            return "";
        }

        try {
            List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
            if (packs == null) {
                return "";
            }
            for (PackageInfo pack : packs) {
                ProviderInfo[] providers = pack.providers;
                if (providers != null) {
                    for (ProviderInfo provider : providers) {
                        if (permission.equals(provider.readPermission) || permission.equals(provider.writePermission)) {
                            if (!TextUtils.isEmpty(provider.authority)// 精准匹配launcher.settings，再一次验证
                                    && (provider.authority).contains(".launcher.settings"))
                                return provider.authority;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getAuthorityFromPermission(Context context) {
        // 获取默认
        String authority = getAuthorityFromPermissionDefault(context);
        // 获取特殊第三方
        if (authority == null || authority.trim().equals("")) {
            String packageName = getCurrentLauncherPackageName(context);
            packageName += ".permission.READ_SETTINGS";
            authority = getThirdAuthorityFromPermission(context, packageName);
        }
        // 还是获取不到，直接写死
        if (TextUtils.isEmpty(authority)) {
            int sdkInt = android.os.Build.VERSION.SDK_INT;
            if (sdkInt < 8) { // Android 2.1.x(API 7)以及以下的
                authority = "com.android.launcher.settings";
            } else if (sdkInt < 19) {// Android 4.4以下
                authority = "com.android.launcher2.settings";
            } else {// 4.4以及以上
                authority = "com.android.launcher3.settings";
            }
        }
        authority = "content://" + authority + "/favorites?notify=true";
        return authority;

    }
}
