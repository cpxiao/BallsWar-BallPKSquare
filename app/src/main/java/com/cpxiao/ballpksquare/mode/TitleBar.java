package com.cpxiao.ballpksquare.mode;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/14.
 */

public class TitleBar extends BaseSprite {
    private int mScore;

    protected TitleBar(Build build) {
        super(build);
    }

    public void setScore(int score) {
        mScore = score;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);

        paint.setColor(getColorBg());
        canvas.drawRect(getSpriteRectF(), paint);

        paint.setColor(getColorText());
        canvas.drawText("" + mScore, getCenterX(), getCenterY() + 0.4F * paint.getTextSize(), paint);
    }

    public static class Build extends BaseSprite.Build {
        public TitleBar build() {
            return new TitleBar(this);
        }
    }
}
