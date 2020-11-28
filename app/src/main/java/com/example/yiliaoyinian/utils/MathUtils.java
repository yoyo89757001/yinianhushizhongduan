package com.example.yiliaoyinian.utils;

public class MathUtils {

    /**
     * 计算BAC夹角
     *
     * @param Ax 点A X
     * @param Ay 点A Y
     * @param Bx 点B X
     * @param By 点B Y
     * @param Cx 点C X
     * @param Cy 点C y
     * @return BAC夹角
     */
    public static double angleBAC(
            double Ax, double Ay, double Bx, double By, double Cx, double Cy) {
        if ((Ax != Bx || Ay != By) && (Ax != Cx || Ay != Cy)) {
            double ABx = Bx - Ax;
            double ABy = By - Ay;
            double ACx = Cx - Ax;
            double ACy = Cy - Ay;
            double AB_AC = ABx * ACx + ABy * ACy;
            double mAB = Math.sqrt(Math.pow(ABx, 2) + Math.pow(ABy, 2));
            double mAC = Math.sqrt(Math.pow(ACx, 2) + Math.pow(ACy, 2));
            double cosA = AB_AC / (mAB * mAC);
            return Math.toDegrees(Math.acos(cosA));
        }
        return 0;
    }

    /**
     * 求圆上某一点
     *
     * @param centerX      圆心X
     * @param centerY      圆心Y
     * @param radius       半径
     * @param angle        角度
     * @param output       结果输出
     * @param outputOffset 输出的偏移量
     */
    public static void circlePoint(
            double centerX, double centerY, double radius, double angle,
            double[] output, int outputOffset) {
        output[outputOffset] = centerX + radius * Math.cos(angle * Math.PI / 180);
        output[outputOffset + 1] = centerY + radius * Math.sin(angle * Math.PI / 180);
    }

    /**
     * 获取象限
     *
     * @param x X轴
     * @param y Y轴
     * @return 象限
     */
    public static int getQuadrant(double x, double y) {
        if (x > 0) {
            if (y > 0) {
                return 1;
            } else {
                return 4;
            }
        } else {
            if (y > 0) {
                return 2;
            } else {
                return 3;
            }
        }
    }

    /**
     * 计算AB的距离
     *
     * @param Ax 点A X
     * @param Ay 点A Y
     * @param Bx 点B X
     * @param By 点B Y
     * @return AB两点距离
     */
    public static double distanceAB(double Ax, double Ay, double Bx, double By) {
        return Math.sqrt(Math.pow(Ax - Bx, 2) + Math.pow(Ay - By, 2));
    }
}
