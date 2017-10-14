package com.cpxiao.ballpksquare.mode.extra;

/**
 * @author cpxiao on 2017/10/9.
 */

public final class EnemyExtra {
    public static final int COUNT_X = 7;

    public static final int TYPE_NONE = 0;
    public static final int TYPE_SQUARE = 1;
    public static final int TYPE_CIRCLE = 2;
    public static final int TYPE_FOOD_BALL = 3;

    private static int[][] enemyArray0 = {
            {0, 0, 0, 1, 0, 0, 0},
            {0, 0, 1, 3, 1, 0, 0},
            {2, 0, 0, 1, 0, 0, 2},
    };
    private static int[][] enemyArray1 = {
            {0, 0, 0, 1, 0, 0, 0},
            {0, 3, 0, 0, 0, 3, 0},
            {0, 0, 2, 1, 2, 0, 0},
    };
    private static int[][] enemyArray2 = {
            {1, 0, 0, 3, 0, 0, 1},
            {0, 0, 2, 1, 2, 0, 0},
            {0, 0, 0, 1, 0, 0, 0},
    };
    private static int[][] enemyArray3 = {
            {1, 0, 0, 1, 0, 0, 1},
            {0, 3, 0, 0, 0, 3, 0},
            {0, 1, 1, 1, 1, 1, 0},
    };
    private static int[][] enemyArray4 = {
            {1, 0, 0, 1, 0, 0, 1},
            {0, 1, 3, 2, 3, 1, 0},
            {0, 0, 1, 1, 1, 0, 0},
    };
    private static int[][] enemyArray5 = {
            {0, 1, 1, 1, 1, 1, 1},
            {0, 3, 0, 0, 0, 3, 1},
            {0, 1, 1, 1, 1, 1, 1},
    };
    private static int[][] enemyArray6 = {
            {1, 1, 1, 1, 1, 1, 0},
            {1, 3, 0, 0, 0, 3, 0},
            {1, 1, 1, 1, 1, 1, 0},
    };

    private static int[][] enemyArray7 = {
            {1, 1, 1, 1, 1, 1, 1},
            {1, 3, 0, 3, 0, 3, 1},
            {1, 0, 1, 1, 1, 0, 1},
    };
    private static int[][] enemyArray8 = {
            {1, 0, 3, 1, 3, 0, 1},
            {0, 1, 1, 1, 1, 1, 0},
            {0, 1, 0, 2, 0, 1, 0},
    };
    private static int[][] enemyArray9 = {
            {1, 0, 0, 1, 0, 0, 1},
            {0, 0, 2, 3, 2, 0, 0},
            {1, 0, 0, 1, 0, 0, 1},
    };

    private static int[][] enemyArray10 = {
            {1, 0, 0, 1, 0, 0, 1},
            {3, 1, 1, 2, 1, 1, 3},
            {1, 0, 1, 1, 1, 0, 1},
    };
    private static int[][] enemyArray11 = {
            {3, 1, 1, 2, 1, 1, 3},
    };
    private static int[][] enemyArray12 = {
            {1, 0, 1, 1, 1, 0, 1},
    };
    private static int[][] enemyArray13 = {
            {1, 1, 1, 1, 1, 1, 1},
    };
    private static int[][] enemyArray14 = {
            {1, 1, 2, 1, 2, 1, 1},
    };
    private static int[][] enemyArray15 = {
            {2, 1, 1, 3, 1, 1, 2},
    };

    private static final int[][][] enemyStore = {
            enemyArray0, enemyArray1, enemyArray2, enemyArray3, enemyArray4,
            enemyArray5, enemyArray6, enemyArray7, enemyArray8, enemyArray9,
            enemyArray10, enemyArray11, enemyArray12, enemyArray13, enemyArray14,
            enemyArray15,};

    public static int[][] getEnemyArray() {
        return enemyStore[(int) (Math.random() * enemyStore.length)];
    }
}
