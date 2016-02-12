package com.automaticvolumecontrol;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

/**
 * Created by nbp184 on 2016/02/09.
 */
public class HighlightButton extends Button {

    private boolean highlighted;

    public HighlightButton(Context context) {
        super(context);
        init(null, 0);
    }

    public HighlightButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public HighlightButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyle) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.HighlightButton, defStyle, 0);
        highlighted = a.getBoolean(R.styleable.HighlightButton_highlighted, false);
        a.recycle();
        doHighlight();
    }

    private void doHighlight() {
        if(highlighted) {
            setBackgroundResource(R.drawable.ic_solid_circle_accent_36dp);
        } else {
            setBackgroundResource(R.drawable.ic_solid_circle_transparent_36dp);
        }
        invalidate();
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
        doHighlight();
    }

    public void switchHighlighted() {
        highlighted = !highlighted;
        doHighlight();
    }
}
