package me.listenzz.modal;

import android.graphics.Point;
import android.os.Build;
import android.util.DisplayMetrics;

import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

public class TranslucentModalHostShadowNode extends LayoutShadowNode {

    @Override
    public void addChildAt(ReactShadowNodeImpl child, int i) {
        super.addChildAt(child, i);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getWindowDisplayMetrics();
            child.setStyleWidth(screenDisplayMetrics.widthPixels);
            child.setStyleHeight(screenDisplayMetrics.heightPixels);
        } else {
            Point modalSize = TranslucentModalHostHelper.getModalHostSize(getThemedContext());
            child.setStyleWidth(modalSize.x);
            child.setStyleHeight(modalSize.y);
        }
    }

}
