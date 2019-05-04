package me.listenzz.modal;

import android.graphics.Point;

import com.facebook.react.uimanager.LayoutShadowNode;
import com.facebook.react.uimanager.ReactShadowNodeImpl;

public class TranslucentModalHostShadowNode extends LayoutShadowNode {

    @Override
    public void addChildAt(ReactShadowNodeImpl child, int i) {
        super.addChildAt(child, i);
        Point modalSize = TranslucentModalHostHelper.getModalHostSize(getThemedContext());
        child.setStyleWidth(modalSize.x);
        child.setStyleHeight(modalSize.y);
    }

}
