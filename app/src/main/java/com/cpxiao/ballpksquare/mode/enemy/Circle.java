package com.cpxiao.ballpksquare.mode.enemy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/11.
 */

public class Circle extends BaseSprite {

    protected Circle(Build build) {
        super(build);
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        if (getLife() <= 0) {
            return;
        }
        paint.setColor(getColorBg());
        canvas.drawCircle(getCenterX(), getCenterY(), 0.5F * getWidth(), paint);
        //        canvas.drawRect(getSpriteRectF(), paint);

        paint.setColor(Color.WHITE);
        paint.setTextSize(0.4F * getWidth());
        canvas.drawText("" + getLife(), getCenterX(), getCenterY() + 0.4F * paint.getTextSize(), paint);
    }

    public static class Build extends BaseSprite.Build {

        public Circle build() {
            return new Circle(this);
        }
    }
}
