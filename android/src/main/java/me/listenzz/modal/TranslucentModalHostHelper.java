package me.listenzz.modal;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.facebook.infer.annotation.Assertions;
import java.lang.reflect.Method;
import static android.view.View.NO_ID;

public class TranslucentModalHostHelper {
    private static final Point MIN_POINT = new Point();
    private static final Point MAX_POINT = new Point();
    private static final Point SIZE_POINT = new Point();
    private static final String NAVIGATION = "navigationBarBackground";

    /**
     * To get the size of the screen, we use information from the WindowManager and
     * default Display. We don't use DisplayMetricsHolder, or Display#getSize() because
     * they return values that include the status bar. We only want the values of what
     * will actually be shown on screen.
     * We use Display#getSize() to determine if the screen is in portrait or landscape.
     * We don't use getRotation because the 'natural' rotation will be portrait on phones
     * and landscape on tablets.
     * This should only be called on the native modules/shadow nodes thread.
     */
    public static Point getModalHostSize(Context context, Activity activity) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = Assertions.assertNotNull(wm).getDefaultDisplay();
        // getCurrentSizeRange will return the min and max width and height that the window can be
        display.getCurrentSizeRange(MIN_POINT, MAX_POINT);
        // getSize will return the dimensions of the screen in its current orientation
        display.getSize(SIZE_POINT);

        int[] attrs = {android.R.attr.windowFullscreen};
        Resources.Theme theme = context.getTheme();
        TypedArray ta = theme.obtainStyledAttributes(attrs);
        boolean windowFullscreen = ta.getBoolean(0, false);

        // We need to add the status bar height to the height if we have a fullscreen window,
        // because Display.getCurrentSizeRange doesn't include it.
        Resources resources = context.getResources();
        int statusBarId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int statusBarHeight = 0;
        if ((windowFullscreen || Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) && statusBarId > 0) {
            statusBarHeight = (int) resources.getDimension(statusBarId);
        }

        int navigationBarHeight = 0;
        if (hasNavigationBar(context) && !navigationGestureEnabled(activity)) {
            navigationBarHeight = getNavigationHeight(context);
        }

        if (SIZE_POINT.x < SIZE_POINT.y) {
            // If we are vertical the width value comes from min width and height comes from max height
            return new Point(MIN_POINT.x, MAX_POINT.y + statusBarHeight + navigationBarHeight);
        } else {
            // If we are horizontal the width value comes from max width and height comes from min height
            return new Point(MAX_POINT.x, MIN_POINT.y + statusBarHeight + navigationBarHeight);
        }
    }

    // 判断是否存在NavigationBar
    private static boolean hasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            //反射获取SystemProperties类，并调用它的get方法
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hasNavigationBar;
    }

    // 全面屏判断
    private static boolean navigationGestureEnabled(@NonNull Activity activity) {
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId() != NO_ID && NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static int getNavigationHeight(Context activity) {
        if (activity == null) {
            return 0;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        int height = 0;
        if (resourceId > 0) {
            //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }
}
