package es.vic.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class oa implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // SOLO JUGADOR ///////////////////////////////////////
            for (Player playerOnline : player.getServer().getOnlinePlayers()) {
                player.sendMessage(playerOnline.getName());
            }
            return true;
            /////////////////////////////////////////////////
        } else {

            // SOLO CONSOLA///////////////////////////////////////
            System.out.println("Es una consola o un bloque de comandos, no se puede hacer esto desde consola");
            return false;
            ////////////////////////////////////////
        }
    }
}
