package me.otisdiver.otisarena.utils;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Color;

public class ColorUtils {
    private static final HashMap<ChatColor, Color>  colorsByChatColors  = new HashMap<ChatColor, Color>();
    private static final HashMap<Color, ChatColor>  chatColorsByColors  = new HashMap<Color, ChatColor>();
   
    static {
        // @formatter:off
        linkColors(ChatColor.AQUA,         Color.fromRGB(0, 230, 240));
        linkColors(ChatColor.BLUE,         Color.fromRGB(0, 200, 225));
        linkColors(ChatColor.DARK_AQUA,    Color.fromRGB(0, 200, 180));
        linkColors(ChatColor.DARK_BLUE,    Color.fromRGB(0, 0, 225));
        linkColors(ChatColor.DARK_GRAY,    Color.fromRGB(80, 80, 80));
        linkColors(ChatColor.DARK_GREEN,   Color.fromRGB(0, 150, 0));
        linkColors(ChatColor.DARK_PURPLE,  Color.fromRGB(90, 0, 150));
        linkColors(ChatColor.DARK_RED,     Color.fromRGB(200, 50, 50));
        linkColors(ChatColor.GOLD,         Color.fromRGB(255, 255, 0));
        linkColors(ChatColor.GRAY,         Color.fromRGB(180, 180, 180));
        linkColors(ChatColor.GREEN,        Color.fromRGB(0, 255, 0));
        linkColors(ChatColor.LIGHT_PURPLE, Color.fromRGB(255, 100, 255));
        linkColors(ChatColor.RED,          Color.fromRGB(255, 0, 0));
        linkColors(ChatColor.WHITE,        Color.fromRGB(255, 255, 255));
        linkColors(ChatColor.YELLOW,       Color.fromRGB(225, 220, 50));
        // @formatter:on
    }
   
    private static void linkColors(ChatColor chatColor, Color color) {
        colorsByChatColors.put(chatColor, color);
        chatColorsByColors.put(color, chatColor);
    }
   
    public static Color fromChatColor(ChatColor chatColor) {
        return fromChatColor(chatColor, Color.WHITE);
    }
   
    public static Color fromChatColor(ChatColor chatColor, Color defaultValue) {
        Color result = colorsByChatColors.get(chatColor);
        return result == null ? defaultValue : result;
    }
   
    public static ChatColor fromColor(Color color) {
        return fromColor(color, ChatColor.WHITE);
    }
   
    public static ChatColor fromColor(Color color, ChatColor defaultValue) {
        ChatColor result = chatColorsByColors.get(color);
        return result == null ? defaultValue : result;
    }
}
