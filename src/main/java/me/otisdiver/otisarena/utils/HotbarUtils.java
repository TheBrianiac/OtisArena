package me.otisdiver.otisarena.utils;

public class HotbarUtils {
    // @formatter:off
    private static final int[][] hotbarIndexes = {
    //   0  1  2  3  4  5  6  7  8
        {            4            },
        {      2,          6      },
        {      2,    4,    6      },
        {   1,    3,    5,    7   },
        {0,    2,    4,    6,    8},
        {   1, 2, 3,    5, 6, 7   },
        {   1, 2, 3, 4, 5, 6, 7   },
        {0, 1, 2, 3,    5, 6, 7, 8},
        {0, 1, 2, 3, 4, 5, 6, 7, 8}
    };
    // @formatter:on
    
    /** Positions in the inventory to evenly distribute items. */
    public static int[] getHotbarIndexes(int items) {
        if (items < 1 || items > 9) return null;
        return hotbarIndexes[items - 1];
    }
}
