package com.cpxiao.ballpksquare.views;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import com.cpxiao.R;
import com.cpxiao.ballpksquare.OnGameListener;
import com.cpxiao.ballpksquare.mode.AdsLayout;
import com.cpxiao.ballpksquare.mode.Ball;
import com.cpxiao.ballpksquare.mode.BigGun;
import com.cpxiao.ballpksquare.mode.TitleBar;
import com.cpxiao.ballpksquare.mode.enemy.Circle;
import com.cpxiao.ballpksquare.mode.enemy.Square;
import com.cpxiao.ballpksquare.mode.enemy.BallFood;
import com.cpxiao.ballpksquare.mode.extra.EnemyExtra;
import com.cpxiao.gamelib.mode.common.Sprite;
import com.cpxiao.gamelib.mode.common.utils.SpeedControl;
import com.cpxiao.gamelib.mode.common.utils.SpriteControl;

import java.util.concurrent.ConcurrentLinkedQueue;

import hugo.weaving.DebugLog;


/**
 * @author cpxiao on 2017/10/09.
 */

public class ShooterGameView extends BaseGameView {

    private int mScore;
    public int mBallCount = 3;
    private ConcurrentLinkedQueue<Ball> mBallQueue = new ConcurrentLinkedQueue<>();
    private TitleBar mTitleBar;
    private AdsLayout mAdsLayout;
    private BigGun mBigGun;
    private RectF mMovingRangeRectF;
    private boolean createFlag = false;

    private float mEnemyVelocityY;
    private float mEnemyVelocityYDefault;

    private float createEnemyArrayFlag;
    private float createEnemyArrayMaxY;

    private boolean isGameOverShown = false;
    private OnGameListener mOnGameListener;


    public ShooterGameView(Context context) {
        super(context);
    }

    public ShooterGameView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ShooterGameView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void initWidget() {
        Context context = getContext();
        int gameViewColorBg = ContextCompat.getColor(context, R.color.gameViewColorBg);
        int titleBarColorBg = ContextCompat.getColor(context, R.color.titleBarColorBg);

        setBgColor(gameViewColorBg);
        mEnemyVelocityYDefault = 0.0001F * mViewHeight;
        mEnemyVelocityY = mEnemyVelocityYDefault;

        float titleBarH = 50 * Resources.getSystem().getDisplayMetrics().density;
        float adsLayoutH = 100 * Resources.getSystem().getDisplayMetrics().density;
        float bigGunH = 80 * Resources.getSystem().getDisplayMetrics().density;

        mTitleBar = (TitleBar) new TitleBar.Build()
                .setColorBg(titleBarColorBg)
                .setColorText(Color.WHITE)
                .setWidth(mViewWidth)
                .setHeight(titleBarH)
                .build();

        mAdsLayout = (AdsLayout) new AdsLayout.Build()
                .setColorBg(titleBarColorBg)
                .setWidth(mViewWidth)
                .setHeight(adsLayoutH)
                .setY(mViewHeight - adsLayoutH)
                .build();

        mBigGun = (BigGun) new BigGun.Build()
                .setColorMuzzle(ContextCompat.getColor(context, R.color.bigGun))
                .setColorBg(ContextCompat.getColor(context, R.color.bigGunBg))
                .setColorText(ContextCompat.getColor(context, R.color.bigGunText))
                .setWidth(mViewWidth)
                .setHeight(bigGunH)
                .setY(mViewHeight - adsLayoutH - bigGunH)
                .build();

        mMovingRangeRectF = new RectF(0, titleBarH, mViewWidth, mViewHeight);

    }

    private void drawBulletText(BigGun bigGun, Canvas canvas, Paint paint) {

        //        canvas.drawCircle(bigGun.getCenterX() - 0.2F * bigGun.getHeight(), bigGun.getY(), 0.06F * bigGun.getHeight(), paint);
        canvas.drawText("" + mBallCount, bigGun.getCenterX(), bigGun.getCenterY(), paint);

    }

    @Override
    public void drawCache() {

        super.drawCache();


        if (mBigGun != null) {
            if (createFlag) {
                mBigGun.onDraw(mCanvasCache, mPaint);
            } else {
                mBigGun.draw(mCanvasCache, mPaint);
            }
            if (mBallQueue.isEmpty()) {
                mPaint.setColor(Color.WHITE);
                drawBulletText(mBigGun, mCanvasCache, mPaint);
            } else {
                mPaint.setColor(Color.GRAY);
                drawBulletText(mBigGun, mCanvasCache, mPaint);
            }
        }
        for (Sprite sprite : mBallQueue) {
            sprite.draw(mCanvasCache, mPaint);
        }

        mTitleBar.draw(mCanvasCache, mPaint);
        mAdsLayout.draw(mCanvasCache, mPaint);

        if (!isGameOverShown && checkGameOver()) {
            isGameOverShown = true;
            //Game Over
            if (mOnGameListener != null) {
                mOnGameListener.onGameOver(mScore);
            }
        }
    }

    private synchronized boolean checkGameOver() {
        for (Sprite sprite : mSpriteQueue) {
            if (SpriteControl.isTwoRectFSpriteCollided(sprite, mAdsLayout)
                    || SpriteControl.isTwoRectFSpriteCollided(sprite, mBigGun)) {

                return true;
            }
        }
        return false;
    }


    @Override
    protected void timingLogic() {
        super.timingLogic();
        if (createEnemyArrayFlag > createEnemyArrayMaxY) {
            mEnemyVelocityY += 0.2F * mEnemyVelocityYDefault;
            createEnemyArray();
            createEnemyArrayFlag = 0;
        }
        createEnemyArrayFlag += mEnemyVelocityY;

        if (mBigGun != null) {
            for (Ball ball : mBallQueue) {
                if (ball.isDestroyed()) {
                    mBallQueue.remove(ball);
                }

                if (SpriteControl.isTwoRectFSpriteCollided(ball, mAdsLayout)) {
                    ball.destroy();
                }

            }
            if (createFlag) {
                createBallQueue(mBallCount, mBigGun);
            }
        }

        for (Sprite sprite : mSpriteQueue) {
            if (SpriteControl.isTwoRectFSpriteCollided(sprite, mAdsLayout)) {
                sprite.destroy();
            }
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            if (mBallQueue.isEmpty()) {
                createFlag = true;
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * create square
     */
    @DebugLog
    private synchronized void createEnemyArray() {
        int[][] squareArray = EnemyExtra.getEnemyArray();
        int life = (int) (mBallCount * (1 + Math.random()));

        if (squareArray != null) {
            int countX = EnemyExtra.COUNT_X, countY;
            countY = squareArray.length;
            if (squareArray[0] != null) {
                countX = squareArray[0].length;
            }
            float rectWH = Math.min(mViewWidth, mViewHeight) / countX;
            float startY = -rectWH * squareArray.length + (mTitleBar == null ? 0 : mTitleBar.getHeight());
            createEnemyArrayMaxY = rectWH * (squareArray.length + 1);

            float circleWH = 0.9F * rectWH;
            float squareWH = 0.95F * rectWH;
            float ballFoodWH = 0.24F * rectWH;

            int color = ContextCompat.getColor(getContext(), R.color.enemyColor);
            for (int x = 0; x < countX; x++) {
                for (int y = 0; y < countY; y++) {
                    int type = squareArray[y][x];
                    if (type == EnemyExtra.TYPE_NONE) {
                        continue;
                    }
                    float cX = rectWH * (0.5F + x);
                    float cY = startY + rectWH * (0.5F + y);
                    if (type == EnemyExtra.TYPE_CIRCLE) {
                        Circle circle = (Circle) new Circle.Build()
                                .setColorBg(color)
                                .setLife(life)
                                .setWidth(circleWH)
                                .setHeight(circleWH)
                                .centerTo(cX, cY)
                                .setVelocityY(mEnemyVelocityY)
                                .build();
                        mSpriteQueue.add(circle);
                    } else if (type == EnemyExtra.TYPE_SQUARE) {
                        Square square = (Square) new Square.Build()
                                .setColorBg(color)
                                .setLife(life)
                                .setWidth(squareWH)
                                .setHeight(squareWH)
                                .centerTo(cX, cY)
                                .setVelocityY(mEnemyVelocityY)
                                .build();
                        mSpriteQueue.add(square);
                    } else if (type == EnemyExtra.TYPE_FOOD_BALL) {
                        BallFood ball = (BallFood) new BallFood.Build()
                                .setColorBg(Color.WHITE)
                                .setWidth(ballFoodWH)
                                .setHeight(ballFoodWH)
                                .centerTo(cX, cY)
                                .setVelocityY(mEnemyVelocityY)
                                .build();
                        mSpriteQueue.add(ball);
                    }
                }
            }
        }
    }


    private void createBallQueue(int count, BigGun bigGun) {
        if (mFrame % 5 != 0) {
            return;
        }
        if (mBallQueue.size() < count) {
            createBall(bigGun);
        } else {
            createFlag = false;
        }
    }

    private void createBall(BigGun bigGun) {
        if (bigGun == null) {
            return;
        }
        float angle = bigGun.getDegrees();
        float speed = 0.02F * mViewWidth;
        float speedX = SpeedControl.getVelocityXByDegrees(speed, angle);
        float speedY = SpeedControl.getVelocityYByDegrees(speed, angle);
        if (DEBUG) {
            Log.d(TAG, "createBallQueue: speedX = " + speedX + ", speedY = " + speedY);
        }
        float ballWH = 0.03F * mViewWidth;
        int color = Color.WHITE;
        Ball ball = (Ball) new Ball.Build()
                .setColorBg(color)
                .setWidth(ballWH)
                .setHeight(ballWH)
                .centerTo(bigGun.getMuzzleX(), bigGun.getMuzzleY())
                .setVelocityX(speedX)
                .setVelocityY(speedY)
                .setMovingRangeRectF(mMovingRangeRectF)
                .build();
        ball.setSpriteQueue(mSpriteQueue);
        ball.setShooterGameView(this);
        mBallQueue.add(ball);
    }

    public void addScore() {
        mScore++;
        if (mTitleBar != null) {
            mTitleBar.setScore(mScore);
        }

    }


    public void setOnGameListener(OnGameListener onGameListener) {
        mOnGameListener = onGameListener;
    }

    public void restart() {
        createEnemyArrayFlag = 0;
        createEnemyArrayMaxY = 0;
        mEnemyVelocityY = mEnemyVelocityYDefault;
        isGameOverShown = false;
        mScore = 0;
        if (mTitleBar != null) {
            mTitleBar.setScore(mScore);
        }
        mSpriteQueue.clear();
        mBallCount = 3;
        mBallQueue.clear();
        postInvalidate();
    }
}
