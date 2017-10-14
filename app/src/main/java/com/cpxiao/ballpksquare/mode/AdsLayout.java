package com.cpxiao.ballpksquare.mode;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/14.
 */

public class AdsLayout extends BaseSprite {

    protected AdsLayout(Build build) {
        super(build);

    }


    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);

        paint.setColor(getColorBg());
        canvas.drawRect(getSpriteRectF(), paint);

    }

    public static class Build extends BaseSprite.Build {
        public AdsLayout build() {
            return new AdsLayout(this);
        }
    }
}
