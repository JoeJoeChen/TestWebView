package com.example.testwebview.common;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;

import com.example.testwebview.R;

public class Utils
{
    /**
     * 为程序创建桌面快捷方式
     */
    public static void addShortcut(Activity activity)
    {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); // 不允许重复创建

        // 指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        // 注意: ComponentName的第二个参数必须加上点号(.)，否则快捷方式无法启动相应程序
        ComponentName comp = new ComponentName(activity.getPackageName(), "."
                + activity.getLocalClassName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                new Intent(Intent.ACTION_MAIN).setComponent(comp));

        // 快捷方式的图标
        ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity,
                R.drawable.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * 删除程序的快捷方式
     */
    public static void delShortcut(Activity activity)
    {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        // 快捷方式的名称
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));

        // 指定当前的Activity为快捷方式启动的对象: 如 com.everest.video.VideoPlayer
        // 注意: ComponentName的第二个参数必须是完整的类名（包名+类名），否则无法删除快捷方式
        String appClass = activity.getPackageName() + "." + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                new Intent(Intent.ACTION_MAIN).setComponent(comp));

        activity.sendBroadcast(shortcut);
    }

}