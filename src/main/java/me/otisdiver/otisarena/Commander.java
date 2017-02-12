package me.otisdiver.otisarena;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.otisdiver.otisarena.task.EndGame;
import me.otisdiver.otisarena.task.StartGame;

public class Commander implements CommandExecutor {
    
    private static final String commandName = "game";
    private static final String subcommandRestart = "restart";
    private static final String subcommandKillAll = "stop";
    private static final String returnMessage = ChatColor.GREEN + "Success!";
    
    private OtisArena main;
    
    /** Handles plugin commands.
     * 
     * @param main instance of JavaPlugin
     */
    public Commander(OtisArena main) {
        this.main = main;
    }
    
    /* new EndGame(main, false).runSync();
       Bukkit.getServer().shutdown();
     */
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        // make sure the command matches: /game <one argument>
        if (!cmd.equalsIgnoreCase(commandName)) return false;
        if (args.length != 1) return false;
        
        // find the subcommand (/game <restart/stop>)
        String arg0 = args[0].toLowerCase();
        boolean kill = arg0.equals(subcommandKillAll);
        if (!arg0.equals(subcommandRestart) && !kill) return false;
        
        // stop the game
        StartGame.cancelEndTasks();
        new EndGame(main, !kill).runSync();
        
        // for /game stop, stop the server
        if (kill) Bukkit.getServer().shutdown();
        
        // finish
        sender.sendMessage(returnMessage);
        return true;
    }

}
