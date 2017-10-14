package com.cpxiao.ballpksquare.mode;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/11.
 */

public class Line extends BaseSprite {

    private Paint mPaint = new Paint();

    protected Line(Build build) {
        super(build);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        //        super.onDraw(canvas, paint);

        mPaint.setColor(getColorBg());
        mPaint.setAlpha((int) (255 * Math.sin((float) getFrame() / 10)));
        canvas.drawRect(getSpriteRectF(), mPaint);
    }

    public static class Build extends BaseSprite.Build {

        public Line build() {
            return new Line(this);
        }
    }
}
