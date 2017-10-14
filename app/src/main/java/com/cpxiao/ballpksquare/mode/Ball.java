package com.cpxiao.ballpksquare.mode;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.cpxiao.ballpksquare.mode.core.BaseSprite;
import com.cpxiao.ballpksquare.mode.enemy.Circle;
import com.cpxiao.ballpksquare.mode.enemy.Square;
import com.cpxiao.ballpksquare.mode.enemy.BallFood;
import com.cpxiao.ballpksquare.views.ShooterGameView;
import com.cpxiao.gamelib.mode.common.Sprite;
import com.cpxiao.gamelib.mode.common.utils.SpriteControl;

import java.util.concurrent.ConcurrentLinkedQueue;

import hugo.weaving.DebugLog;

/**
 * @author cpxiao on 2017/10/9.
 */

public class Ball extends BaseSprite {

    protected ShooterGameView mShooterGameView;
    protected ConcurrentLinkedQueue<Sprite> mSpriteQueue;

    protected Ball(Build build) {
        super(build);
    }

    public void setShooterGameView(ShooterGameView shooterGameView) {
        mShooterGameView = shooterGameView;
    }

    public void setSpriteQueue(ConcurrentLinkedQueue<Sprite> spriteQueue) {
        mSpriteQueue = spriteQueue;
    }

    @Override
    protected void beforeDraw(Canvas canvas, Paint paint) {
        super.beforeDraw(canvas, paint);

        //判断移动范围, 到达边界后反弹
        RectF movingRangeRectF = getMovingRangeRectF();
        if (movingRangeRectF != null) {
            RectF rectF = getSpriteRectF();
            if (rectF.left <= movingRangeRectF.left || rectF.right >= movingRangeRectF.right) {
                setVelocityX(-getVelocityX());
            }
            if (rectF.top <= movingRangeRectF.top || rectF.bottom >= movingRangeRectF.bottom) {
                setVelocityY(-getVelocityY());
            }
        }

        checkCollided(this);

        /*防止Y方向速度太小*/
        float velocityY = getVelocityY();
        if (Math.abs(velocityY) < 1) {
            setVelocityY(velocityY * 5);
        }
    }

    @Override
    public void onDraw(Canvas canvas, Paint paint) {
        super.onDraw(canvas, paint);
        paint.setColor(getColorBg());
        canvas.drawCircle(getCenterX(), getCenterY(), 0.5F * getWidth(), paint);
    }

    @DebugLog
    private synchronized void checkCollided(Sprite ball) {
        if (mSpriteQueue == null || ball.isDestroyed()) {
            return;
        }
        for (Sprite sprite : mSpriteQueue) {
            if (sprite.isDestroyed()) {
                continue;
            }
            if (sprite instanceof BallFood) {
                checkBallFood(ball, sprite);
            } else if (sprite instanceof Circle) {
                checkCircle(ball, sprite);
            } else if (sprite instanceof Square) {
                checkSquare(ball, sprite);
            }
        }
    }

    private synchronized void checkBallFood(Sprite ball, Sprite sprite) {
        if (SpriteControl.isTwoCircleSpriteCollided(ball, sprite)) {
            if (mShooterGameView != null) {
                mShooterGameView.mBallCount++;
                sprite.destroy();
            }
        }
    }

    private synchronized void checkCircle(Sprite ball, Sprite circle) {
        if (SpriteControl.isTwoCircleSpriteCollided(ball, circle)) {
            //reset ball speed, 注意向量角度
            float startAngle = SpriteControl.getAngleByVector(-ball.getVelocityX(), ball.getVelocityY());
            float baseAngle = SpriteControl.getAngleByVector(ball.getCenterX() - circle.getCenterX()
                    , -(ball.getCenterY() - circle.getCenterY()));
            float endAngle = (720 + 2 * baseAngle - startAngle) % 360;

            float delta = Math.abs(startAngle - baseAngle);
            if (delta >= 90 && delta <= 270) {
                return;
            }
            circle.deleteLife(1);
            addScore();

            float speed = (float) Math.sqrt(Math.pow(ball.getVelocityX(), 2) + Math.pow(ball.getVelocityY(), 2));

            float speedX = (float) (speed * Math.cos(Math.toRadians(endAngle)));
            float speedY = -(float) (speed * Math.sin(Math.toRadians(endAngle)));
            ball.setVelocityX(speedX);
            ball.setVelocityY(speedY);
        }
    }

    private synchronized void checkSquare(Sprite ball, Sprite sprite) {
        if (SpriteControl.isTwoRectFSpriteCollided(ball, sprite)) {
            sprite.deleteLife(1);
            addScore();

            float ballCX = ball.getCenterX();
            float ballCY = ball.getCenterY();
            //处于上下位置, 反弹，velocityY改变
            if (ballCX > sprite.getX() && ballCX < sprite.getX() + sprite.getWidth()) {
                if (ball.getCenterY() < sprite.getCenterY()) {
                    ball.setY(sprite.getY() - ball.getHeight());
                } else {
                    ball.setY(sprite.getY() + sprite.getHeight());
                }
                ball.setVelocityY(-ball.getVelocityY());
            }
            //处于左右位置, 反弹，velocityX改变
            else if (ballCY > sprite.getY() && ballCY < sprite.getY() + sprite.getHeight()) {
                if (ball.getCenterX() <= sprite.getCenterX()) {
                    ball.setX(sprite.getX() - ball.getWidth());
                } else {
                    ball.setX(sprite.getX() + sprite.getWidth());
                }
                ball.setVelocityX(-ball.getVelocityX());
            }
        }
    }

    private void addScore() {
        if (mShooterGameView != null) {
            mShooterGameView.addScore();
        }
    }

    public static class Build extends BaseSprite.Build {


        public Ball build() {
            return new Ball(this);
        }
    }
}
