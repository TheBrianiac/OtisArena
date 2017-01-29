package me.otisdiver.otisarena;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import me.otisdiver.otisarena.task.EndGame;

public class Commander implements CommandExecutor {

    private OtisArena main;
    
    /** Handles plugin commands.
     * 
     * @param main instance of JavaPlugin
     */
    public Commander(OtisArena main) {
        this.main = main;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (cmd.equalsIgnoreCase("endgame")) {
            if (!sender.hasPermission("gameadmin")) return false;
            if (args.length != 1) return false;
            
            String arg = args[0].toLowerCase();
            if (arg.equals("no")) {
                new EndGame(main, false);
                Bukkit.getServer().shutdown();
            }
            else if (args.equals("yes")) {
                new EndGame(main, true);
            }
            
            else return false;
            return true;
        }
        return false;
    }

}
