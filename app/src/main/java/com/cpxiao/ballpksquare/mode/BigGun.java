package com.cpxiao.ballpksquare.mode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;

/**
 * @author cpxiao on 2017/10/09.
 */

public class BigGun extends BaseSprite {
    private int colorMuzzle;

    private float degree = 90;
    private static final float ANGLE_WIDTH = 12;

    private Path mPath = new Path();

    private float muzzleBigR;
    private float muzzleSmallR;
    private float bigR;
    private float smallR;
    private RectF mBigRectF = new RectF();
    private RectF mSmallRectF = new RectF();

    private float mMuzzleX, mMuzzleY;

    protected BigGun(Build build) {
        super(build);
        colorMuzzle = build.colorMuzzle;

        initRect();
    }

    public float getMuzzleX() {
        return mMuzzleX;
    }

    public float getMuzzleY() {
        return mMuzzleY;
    }

    public float getDegrees() {
        return degree;
    }

    private void initRect() {
        smallR = 0.22F * getWidth();
        bigR = smallR + 0.015F * getWidth();
        muzzleSmallR = bigR + 0.012F * getWidth();
        muzzleBigR = muzzleSmallR + 0.03F * getWidth();

        mBigRectF.left = getCenterX() - bigR;
        mBigRectF.right = mBigRectF.left + 2 * bigR;
        mBigRectF.top = getY() + getHeight() - bigR;
        mBigRectF.bottom = mBigRectF.top + 2 * bigR;

        mSmallRectF.left = getCenterX() - smallR;
        mSmallRectF.right = mSmallRectF.left + 2 * smallR;
        mSmallRectF.top = getY() + getHeight() - smallR;
        mSmallRectF.bottom = mSmallRectF.top + 2 * smallR;

    }


    private Path getPath() {
        mPath.reset();
        float startAngle = degree - 0.5F * ANGLE_WIDTH;
        float endAngle = degree + 0.5F * ANGLE_WIDTH;
        float startRadians = (float) Math.toRadians(startAngle);
        float endRadians = (float) Math.toRadians(endAngle);
        mPath.moveTo((float) (getCenterX() + Math.cos(startRadians) * muzzleBigR)
                , (float) (getY() + getHeight() - Math.sin(startRadians) * muzzleBigR));
        mPath.lineTo((float) (getCenterX() + Math.cos(endRadians) * muzzleBigR)
                , (float) (getY() + getHeight() - Math.sin(endRadians) * muzzleBigR));
        mPath.lineTo((float) (getCenterX() + Math.cos(endRadians) * muzzleSmallR)
                , (float) (getY() + getHeight() - Math.sin(endRadians) * muzzleSmallR));
        mPath.lineTo((float) (getCenterX() + Math.cos(startRadians) * muzzleSmallR)
                , (float) (getY() + getHeight() - Math.sin(startRadians) * muzzleSmallR));
        return mPath;
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);

        paint.setColor(colorMuzzle);
        canvas.drawArc(mBigRectF, 0, -180, true, paint);

        paint.setColor(getColorBg());
        canvas.drawArc(mSmallRectF, 0, -180, true, paint);

        paint.setColor(colorMuzzle);
        canvas.drawPath(getPath(), paint);

        degree = (float) (90 + 75 * Math.sin((float) getFrame() / 20));

        mMuzzleX = (float) (getCenterX() + Math.cos(Math.toRadians(degree)) * muzzleBigR);
        mMuzzleY = (float) (getY()+getHeight()  - Math.sin(Math.toRadians(degree)) * muzzleBigR);


    }

    public static class Build extends BaseSprite.Build {
        private int colorMuzzle;

        public Build setColorMuzzle(int colorMuzzle) {
            this.colorMuzzle = colorMuzzle;
            return this;
        }

        public BigGun build() {
            return new BigGun(this);
        }
    }
}
