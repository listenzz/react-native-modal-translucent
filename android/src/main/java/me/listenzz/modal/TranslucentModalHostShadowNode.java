package me.listenzz.modal;
import android.util.DisplayMetrics;

import com.facebook.react.uimanager.DisplayMetricsHolder;
import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;


public class TranslucentModalHostShadowNode extends LayoutShadowNode {

    @Override
    public void addChildAt(ReactShadowNodeImpl child, int i) {
        super.addChildAt(child, i);
        DisplayMetrics screenDisplayMetrics = DisplayMetricsHolder.getWindowDisplayMetrics();
        child.setStyleWidth(screenDisplayMetrics.widthPixels);
        child.setStyleHeight(screenDisplayMetrics.heightPixels);
    }

}
