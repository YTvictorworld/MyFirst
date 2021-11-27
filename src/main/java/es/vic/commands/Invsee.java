package es.vic.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Invsee implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String alias, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            //SOLO JUGADOR ///////////////////////////////////////
            if(!player.isOp()) return false;
            if(player.getServer().getPlayer(args[0]) == null){
                player.sendMessage("Jugador desconocido");
                player.setArrowsInBody(100000);
                return false;
            }else{
                player.openInventory(player.getServer().getPlayer(args[0]).getInventory());
                player.sendMessage("Inventario de "+player.getServer().getPlayer(args[0]).getName());
                return true;
            }
            /////////////////////////////////////////////////
        }
        else{

            //SOLO CONSOLA///////////////////////////////////////
            System.out.println("Es una consola o un bloque de comandos, no se puede hacer esto desde consola");
            return false;
            ////////////////////////////////////////
        }

        //LAS DOS COSAS
    }
    
}