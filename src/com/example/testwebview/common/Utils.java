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
     * Ϊ���򴴽������ݷ�ʽ
     */
    public static void addShortcut(Activity activity)
    {
        Intent shortcut = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");

        // ��ݷ�ʽ������
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));
        shortcut.putExtra("duplicate", false); // �������ظ�����

        // ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer
        // ע��: ComponentName�ĵڶ�������������ϵ��(.)�������ݷ�ʽ�޷�������Ӧ����
        ComponentName comp = new ComponentName(activity.getPackageName(), "."
                + activity.getLocalClassName());
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                new Intent(Intent.ACTION_MAIN).setComponent(comp));

        // ��ݷ�ʽ��ͼ��
        ShortcutIconResource iconRes = Intent.ShortcutIconResource.fromContext(activity,
                R.drawable.ic_launcher);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, iconRes);

        activity.sendBroadcast(shortcut);
    }

    /**
     * ɾ������Ŀ�ݷ�ʽ
     */
    public static void delShortcut(Activity activity)
    {
        Intent shortcut = new Intent("com.android.launcher.action.UNINSTALL_SHORTCUT");

        // ��ݷ�ʽ������
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_NAME, activity.getString(R.string.app_name));

        // ָ����ǰ��ActivityΪ��ݷ�ʽ�����Ķ���: �� com.everest.video.VideoPlayer
        // ע��: ComponentName�ĵڶ�����������������������������+�������������޷�ɾ����ݷ�ʽ
        String appClass = activity.getPackageName() + "." + activity.getLocalClassName();
        ComponentName comp = new ComponentName(activity.getPackageName(), appClass);
        shortcut.putExtra(Intent.EXTRA_SHORTCUT_INTENT,
                new Intent(Intent.ACTION_MAIN).setComponent(comp));

        activity.sendBroadcast(shortcut);
    }

}