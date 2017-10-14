package com.cpxiao.ballpksquare.mode.core;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.cpxiao.gamelib.mode.common.Sprite;

/**
 * @author cpxiao on 2017/10/11.
 */

public class BaseSprite extends Sprite {

    private int colorBg;
    private int colorText;

    public int getColorBg() {
        return colorBg;
    }

    public int getColorText() {
        return colorText;
    }

    protected BaseSprite(Build build) {
        super(build);
        colorBg = build.colorBg;
        colorText = build.colorText;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        //        super.onDraw(canvas, paint);
    }

    public static class Build extends Sprite.Build {
        private int colorBg;
        private int colorText;

        public Build setColorBg(int color) {
            this.colorBg = color;
            return this;
        }

        public Build setColorText(int color) {
            this.colorText = color;
            return this;
        }

        public BaseSprite build() {
            return new BaseSprite(this);
        }
    }
}
