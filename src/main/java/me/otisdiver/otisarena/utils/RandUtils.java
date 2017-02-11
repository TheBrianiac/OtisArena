package me.otisdiver.otisarena.utils;

import java.util.Random;

public class RandUtils {

    private static Random randomSet = new Random();
    
    /** Get a random integer less than a given value.
     * 
     * @param max the maximum (exclusive) value of the number
     * @return a pseudo randomly generated number above 0
     */
    public static int rand(int max) {
        return randomSet.nextInt(max);
    }
    
    /** Get a boolean with a % chance of being true
     * 
     * @param percent chance of returning true
     * @return true % of the time
     */
    public static boolean percentChance(int percent) {
        if (percent == 0) return false;
        return (randomSet.nextInt(101) <= percent);
    }
}
