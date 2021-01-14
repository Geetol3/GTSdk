package com.gtdev5.geetolsdk.mylibrary.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by cheng
 * PackageName APP_Lock
 * 2018/1/20 13:56
 *          手机拿权限
 */

public class MobileInfoUtils {

    /**
     * 小米
     */
    private static final String xiaomi_Setting = "com.miui.securitycenter";
    private static final String xiaomi_Activity = "com.miui.permcenter.autostart.AutoStartManagementActivity";

    /**
     * 乐视
     */
    private static final String leshi_Activity = "com.letv.android.permissionautoboot";

    /**
     * 三星
     */
    private static final String sanxing_Setting = "com.samsung.android.sm_cn";
    private static final String sanxing_Activity = "com.samsung.android.sm.ui.ram.AutoRunActivity";

    /**
     * 华为
     */
    private static final String huawei_setting = "com.huawei.systemmanager";
    private static final String huawei_Activity = "com.huawei.systemmanager.optimize.process.ProtectActivity";

    /**
     * Vivo
     */
    private static final String vivo_Activity = "com.iqoo.secure/.safeguard.PurviewTabActivity";

    /**
     * 魅族
     */
    private static final String meizu_Activity = "com.meizu.safe/.permission.PermissionMainActivity";

    /**
     * OPPO
     */
    private static final String oppo_Activity = "com.oppo.safe/.permission.startup.StartupAppListActivity";

    /**
     * 360
     */
    private static final String sanliuling_Activity = "com.yulong.android.coolsafe\", \".ui.activity.autorun.AutoRunListActivity";

    public static String getMobileType(){
        return Build.MANUFACTURER;
    }

    public static ComponentName jumpStartInterface(){
        ComponentName componentName = null;
        switch (getMobileType()){
            case "Xiaomi":
                componentName = new ComponentName(xiaomi_Setting,xiaomi_Activity);
                break;
            case "samsung":
                componentName = new ComponentName(sanxing_Setting,sanxing_Setting);
                break;
            case "HUAWEI":
                componentName = new ComponentName(huawei_setting,huawei_Activity);
                break;
            case "vivo":
                componentName = ComponentName.unflattenFromString(vivo_Activity);
                break;
            case "Meizu":
                componentName = ComponentName.unflattenFromString(meizu_Activity);
                break;
            case "OPPO":
                componentName = ComponentName.unflattenFromString(oppo_Activity);
                break;
            case "ulong":
                componentName = ComponentName.unflattenFromString(sanliuling_Activity);
                break;
            default:
                break;
        }
        return componentName;
    }

    public static int backTtype(Context context){
        ComponentName mComponentName = new ComponentName("com.gtdev5.app_lock","com.gtdev5.app_lock.receiver.BootBroadcastReceiver");
        return context.getPackageManager().getComponentEnabledSetting(mComponentName);
    }

        public static Intent jumpStartInterface(Context context) {
        Intent intent = new Intent();
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Log.e("HLQ_Struggle", "******************当前手机型号为：" + getMobileType());
            ComponentName componentName = null;
            if (getMobileType().equals("Xiaomi")) { // 红米Note4测试通过
                componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
            } else if (getMobileType().equals("Letv")) { // 乐视2测试通过
                intent.setAction("com.letv.android.permissionautoboot");
            } else if (getMobileType().equals("samsung")) { // 三星Note5测试通过
                componentName = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity");
            } else if (getMobileType().equals("HUAWEI")) { // 华为测试通过
                componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
            } else if (getMobileType().equals("vivo")) { // VIVO测试通过
                //componentName = ComponentName.unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");  闪退
                componentName = ComponentName.unflattenFromString("com.vivo.permissionmanager/com.vivo.permissionmanager.activity.PurviewTabActivity");
            } else if (getMobileType().equals("Meizu")) { //万恶的魅族
                //componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");
                componentName = ComponentName.unflattenFromString("com.meizu.safe/com.meizu.safe.permission.SmartBGActivity");
            } else if (getMobileType().equals("OPPO")) { // OPPO R8205测试通过
                componentName = ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
            } else if (getMobileType().equals("ulong")) { // 360手机 未测试
                componentName = new ComponentName("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity");
            } else {
                // 将用户引导到系统设置页面
                if (Build.VERSION.SDK_INT >= 9) {
                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
                } else if (Build.VERSION.SDK_INT <= 8) {
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                    intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
                }
            }
            intent.setComponent(componentName);
        } catch (Exception e) {//抛出异常就直接打开设置页面
            intent = new Intent(Settings.ACTION_SETTINGS);
        }
        return intent;
    }

//    public static void jumpStartInterface(Context context) {
//        Intent intent = new Intent();
//        try {
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            Log.e("HLQ_Struggle", "******************当前手机型号为：" + getMobileType());
//            ComponentName componentName = null;
//            if (getMobileType().equals("Xiaomi")) { // 红米Note4测试通过
//                componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.autostart.AutoStartManagementActivity");
//            } else if (getMobileType().equals("Letv")) { // 乐视2测试通过
//                intent.setAction("com.letv.android.permissionautoboot");
//            } else if (getMobileType().equals("samsung")) { // 三星Note5测试通过
//                componentName = new ComponentName("com.samsung.android.sm_cn", "com.samsung.android.sm.ui.ram.AutoRunActivity");
//            } else if (getMobileType().equals("HUAWEI")) { // 华为测试通过
//                componentName = new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.optimize.process.ProtectActivity");
//            } else if (getMobileType().equals("vivo")) { // VIVO测试通过
//                componentName = ComponentName.unflattenFromString("com.iqoo.secure/.safeguard.PurviewTabActivity");
//            } else if (getMobileType().equals("Meizu")) { //万恶的魅族
//                componentName = ComponentName.unflattenFromString("com.meizu.safe/.permission.PermissionMainActivity");
//            } else if (getMobileType().equals("OPPO")) { // OPPO R8205测试通过
//                componentName = ComponentName.unflattenFromString("com.oppo.safe/.permission.startup.StartupAppListActivity");
//            } else if (getMobileType().equals("ulong")) { // 360手机 未测试
//                componentName = new ComponentName("com.yulong.android.coolsafe", ".ui.activity.autorun.AutoRunListActivity");
//            } else {
//                // 将用户引导到系统设置页面
//                if (Build.VERSION.SDK_INT >= 9) {
//                    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
//                    intent.setData(Uri.fromParts("package", context.getPackageName(), null));
//                } else if (Build.VERSION.SDK_INT <= 8) {
//                    intent.setAction(Intent.ACTION_VIEW);
//                    intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
//                    intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
//                }
//            }
//            intent.setComponent(componentName);
//            context.startActivity(intent);
//        } catch (Exception e) {//抛出异常就直接打开设置页面
//            intent = new Intent(Settings.ACTION_SETTINGS);
//            context.startActivity(intent);
//        }
//    }

    public static boolean getMoblie(){
        switch (getMobileType()){
            case "Xiaomi":
            case "Letv":
            case "samsung":
            case "HUAWEI":
            case "vivo":
            case "Meizu":
            case "OPPO":
            case "ulong":
                return true;
            default:
                break;
        }
        return false;
    }
}
