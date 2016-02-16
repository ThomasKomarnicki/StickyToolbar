package com.example.tdk10.toolbar.behavior;

/**
 * Created by tdk10 on 2/15/2016.
 */
public class MathUtils {
    static int constrain(int amount, int low, int high) {
        return amount < low ? low : (amount > high ? high : amount);
    }

    static float constrain(float amount, float low, float high) {
        return amount < low ? low : (amount > high ? high : amount);
    }
}
