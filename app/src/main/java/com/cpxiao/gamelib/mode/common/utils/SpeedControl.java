package com.cpxiao.gamelib.mode.common.utils;

/**
 * 在物理学中，速度和速率是两个不同的概念。
 * 1）速度(Velocity)是矢量，具有大小和方向；速率(Speed)则纯粹指物体运动的快慢，是标量，没有方向。
 * 2）弧度 (Radian),一个完整的圆的弧度是2π；度 (角) Degree (angle)
 *
 * @author cpxiao on 2017/10/10.
 */

public final class SpeedControl {
    /**
     * 根据角度计算速度X
     */
    public static float getVelocityXByDegrees(float speed, float degrees) {
        return (float) (speed * Math.cos(Math.toRadians(degrees)));
    }

    /**
     * 根据角度计算速度Y
     */
    public static float getVelocityYByDegrees(float speed, float degrees) {
        return -(float) (speed * Math.sin(Math.toRadians(degrees)));
    }

    /**
     * 根据弧度计算速度X
     */
    public static float getVelocityXByRadians(float speed, float radians) {
        return (float) (speed * Math.cos(radians));
    }

    /**
     * 根据弧度计算速度Y
     */
    public static float getVelocityYByRadians(float speed, float radians) {
        return -(float) (speed * Math.sin(radians));
    }

    /**
     * 根据速度X和速度Y计算速度的角度
     */
    public static float getVelocityDegree(float velocityX, float velocityY) {
        return 0;
    }

    /**
     * 根据速度X和速度Y计算速度的弧度
     */
    public static float getVelocityRadians(float velocityX, float velocityY) {
        return 0;
    }

    /**
     * 根据速度X和速度Y计算速率
     */
    public static float getSpeed(float velocityX, float velocityY) {
        return (float) Math.sqrt(Math.pow(velocityX, 2) + Math.pow(velocityY, 2));
    }
}
