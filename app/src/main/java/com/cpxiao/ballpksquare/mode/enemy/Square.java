package com.cpxiao.ballpksquare.mode.enemy;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/9.
 */

public class Square extends BaseSprite {


    protected Square(Build build) {
        super(build);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        if (getLife() <= 0) {
            return;
        }
        paint.setColor(getColorBg());
        canvas.drawRect(getSpriteRectF(), paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(0.4F * getWidth());
        canvas.drawText("" + getLife(), getCenterX(), getCenterY() + 0.4F * paint.getTextSize(), paint);
    }

    public static class Build extends BaseSprite.Build {

        public Square build() {
            return new Square(this);
        }
    }
}
