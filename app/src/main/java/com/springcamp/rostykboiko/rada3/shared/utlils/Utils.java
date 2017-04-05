package com.springcamp.rostykboiko.rada3.shared.utlils;

public class Utils {

    public static int longToInt(long longNumber) {
        int intNumber = (int)longNumber;
        if ((long)intNumber != longNumber) {
            throw new IllegalArgumentException(longNumber + " cannot be cast to int without changing its value.");
        }
        return intNumber;
    }
}
