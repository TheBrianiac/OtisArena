package me.otisdiver.otisarena.utils;

public class HotbarUtils {

    private static final int[] n9 = {0, 1, 2, 3, 4, 5, 6, 7, 8};
    private static final int[] n8 = {0, 1, 2, 3, 5, 6, 7, 8};
    private static final int[] n7 = {1, 2, 3, 4, 5, 6, 7};
    private static final int[] n6 = {1, 2, 3, 5, 6, 7};
    private static final int[] n5 = {0, 2, 4, 6, 8};
    private static final int[] n4 = {1, 3, 5, 7};
    private static final int[] n3 = {2, 4, 6};
    private static final int[] n2 = {2, 6};
    private static final int[] n1 = {4};
   
    
    /** Positions in the inventory to evenly distribute items. */
    public static int[] getHotbarIndexes(int items) {
        switch(items) {
            case 9:
                return n9;
            case 8:
                return n8;
            case 7:
                return n7;
            case 6:
                return n6;
            case 5:
                return n5;
            case 4:
                return n4;
            case 3:
                return n3;
            case 2:
                return n2;
            case 1:
                return n1;
            default:
                return null;
        }
    }
    
}
