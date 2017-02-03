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
            // not enough args, stop
            if (args.length != 1) return false;
            
            // start a new game or shut the server down based on args
            String arg = args[0].toLowerCase();
            if (arg.equals("no")) {
                new EndGame(main, false);
                Bukkit.getServer().shutdown();
            }
            else if (arg.equals("yes")) {
                new EndGame(main, true);
            }
            // not a valid option, stop
            else {
                return false;
            }
            
            // haven't stopped yet -> success!
            return true;
        }
        // not one of our commands, stop
        return false;
    }

}
