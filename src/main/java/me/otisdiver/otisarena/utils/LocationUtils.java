package me.otisdiver.otisarena.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class LocationUtils {

    /** Calculates the pythagorean distance between two points.
     * 
     * @param locA the first location value
     * @param locB the second location value
     * @return the distance between the two locations
     */
    public static double calculateDistance(Location locA, Location locB) {
        double dx = locA.getX() - locB.getX();
        double dy = locA.getY() - locB.getY();
        double dz = locA.getZ() - locB.getZ();
        
        return Math.sqrt(dx * dx + dy * dy + dz * dz);
    }
    
    /** Checks whether two players are within (inclusive) a distance of each other.
     * 
     * @param a the first player
     * @param b the second player
     * @param distance the maximum distance at which true should be returned
     * @return whether Players 'a' and 'b' are within 'distance' of each other
     */
    public static boolean withinDistance(Player a, Player b, double distance) {
        return calculateDistance(a.getLocation(), b.getLocation()) <= distance;
    }
    
}
