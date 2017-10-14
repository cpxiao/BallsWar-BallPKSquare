package com.cpxiao.ballpksquare.mode.enemy;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/9.
 */

public class BallFood extends BaseSprite {

    private static Paint mPaint = new Paint();

    static {
        mPaint.setAntiAlias(true);
    }

    protected BallFood(Build build) {
        super(build);
    }


    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, mPaint);
        mPaint.setColor(getColorBg());
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(getCenterX(), getCenterY(), 0.5F * getWidth(), mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(0.1F * getWidth());
        canvas.drawCircle(getCenterX(), getCenterY()
                , (float) (0.7F * getWidth() + 0.05F * getWidth() * Math.sin((float) getFrame() / 10))
                , mPaint);
    }


    public static class Build extends BaseSprite.Build {

        public BallFood build() {
            return new BallFood(this);
        }
    }
}
