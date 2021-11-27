package es.vic;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import es.vic.commands.Invsee;
import es.vic.commands.oa;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.md_5.bungee.api.ChatColor;


public class Plvic extends JavaPlugin implements Listener {

    DiscordWebhook webhook = new DiscordWebhook("https://discord.com/api/webhooks/893238212779204638/dRW8DWWtFREs1RfYnAYCVwYTiYs4XqwbaVqjOQRA88EUobyFuzwCALrYQudyW_lWboDW");
    File configFile;
    //////////////////////////////PRACTICA//////////////////////////////////////
    Inventory inv = Bukkit.createInventory(null, 9, format("&c&lMenu&f"));
    public HashMap<UUID, Integer> time = new HashMap<UUID, Integer>();

    @Override
    public void onEnable() {

        File configFile = new File(this.getDataFolder(), "config.yml");
        webhook.setContent("BotInciado Mendiante WebHooks");
        try {
            webhook.execute();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("[myFirstPlugin] El plugin se ha iniciado correctamente");
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("invsee").setExecutor(new Invsee());
        this.getCommand("oa").setExecutor(new oa());

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player playerOnline : Bukkit.getServer().getOnlinePlayers()) {
                    if(playerOnline.isOp()) return;
                    if (time.getOrDefault(playerOnline.getUniqueId(), 30) <= 0)
                        return;
                    time.put(playerOnline.getUniqueId(), time.getOrDefault(playerOnline.getUniqueId(), 30) - 1);
                    playerOnline.setPlayerListHeader(format("TIEMPO"+time.get(playerOnline.getUniqueId())));
                    if (time.getOrDefault(playerOnline.getUniqueId(), 30) <= 0) {
                        playerOnline.kickPlayer(format("&c&lSe acabo tu tiempo"));
                    }
                }
            }
        }, 20, 20);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(format(event.getPlayer().getDisplayName() + "&5&lA entrado un puto"));
        Location spawn = new Location(event.getPlayer().getWorld(), 999, 100, -999);
        event.getPlayer().teleport(spawn);
        event.getPlayer().sendMessage(format("&2&lHijodePuta te jodes"));
    }

    @EventHandler
    public void DeathPlayer(PlayerDeathEvent event) {
        event.setDeathMessage(format("&8&lᒥ&c☠&8&lᒧ &7" + event.getEntity().getDisplayName()));
        if (event.getEntity().getMaxHealth() <= 2) {
            event.getEntity().setGameMode(GameMode.SPECTATOR);
            return;
        }

        event.getEntity().setMaxHealth(event.getEntity().getMaxHealth() - 2);
    }

    @EventHandler
    public void Interaction(PlayerInteractEntityEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.BUCKET) {
            if (event.getRightClicked().getType() == EntityType.PIG) {
                event.getPlayer().getInventory().getItemInMainHand().setType(Material.MILK_BUCKET);
            }
        }
    }

    @EventHandler
    public void chat(AsyncPlayerChatEvent event) throws IOException {
        // String chat = "&7 Usuario &f"+event.getPlayer().getDisplayName()+" &8»
        // &7"+event.getMessage();
        
        if (event.getPlayer().isOp()) {
            event.setFormat(
                    format("&c&lADMIN &f" + event.getPlayer().getDisplayName() + " &8» &c" + event.getMessage()));
                    webhook.setContent(event.getMessage());
                    webhook.setAvatarUrl("https://crafatar.com/renders/head/"+event.getPlayer().getUniqueId().toString());
                    webhook.setUsername(event.getPlayer().getName());
                    webhook.execute();
                    
                    
        } else {
            event.setFormat(
                    format("&7 Usuario &f" + event.getPlayer().getDisplayName() + " &8» &7" + event.getMessage()));
        }

        if (event.getMessage().startsWith("sc") && event.getPlayer().hasPermission("staffchat.send")) {
            event.setCancelled(true);
            for (Player playerOnline : event.getPlayer().getServer().getOnlinePlayers()) {
                if (playerOnline.hasPermission("staffchat.read")) {
                    playerOnline.sendMessage(format(
                            "&c&l[ AC ] &f" + event.getPlayer().getDisplayName() + " &8» &5" + event.getMessage()));

                }
            }

        }

        if (event.getMessage().startsWith("inv")) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {

                    inv.setItem(1, new ItemStack(Material.AXOLOTL_BUCKET, 1));

                    event.getPlayer().openInventory(inv);

                }
            });

        }

        if (event.getMessage().startsWith("INVENTARIO")) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {
                    Inventory inv = Bukkit.createInventory(null, 9,
                            format("&c&lInventario de&f " + event.getPlayer().getDisplayName()));
                    event.getPlayer().openInventory(inv);

                }
            });

        }

        if (event.getMessage().equalsIgnoreCase("invsee")) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openInventory(event.getPlayer().getServer().getPlayer("NexxusYT").getInventory());

                }
            });

        }

        if (event.getMessage().startsWith("CRAFTEO")) {
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(this, new Runnable() {
                @Override
                public void run() {
                    event.getPlayer().openWorkbench(null, true);

                }
            });

        }

    }

    private static final Pattern pattern = Pattern.compile("(?<!\\\\)(#[a-fA-F0-9]{6})");

    public static String format(String message) {
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, "" + ChatColor.of(color));
        }
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    @EventHandler
    public void EventBlock(BlockDropItemEvent event) {
        event.setCancelled(true);
        for (Item item : event.getItems()) {
            if (item.getItemStack().getType() == Material.RAW_IRON)
                event.getPlayer().getInventory().addItem(new ItemStack(Material.IRON_INGOT));
            else
                event.getPlayer().getInventory().addItem(item.getItemStack());
        }
    }

    @EventHandler
    public void ClickInventory(InventoryClickEvent event) {

        if (event.getClickedInventory() == inv) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void TimeUser(PlayerJoinEvent e) {
        if(e.getPlayer().isOp()) return;
        if (time.get(e.getPlayer().getUniqueId()) == 0) {
            e.getPlayer().kickPlayer(format("&c&lTiempo Limite Exedido"));
            return;
        }
    }

    @EventHandler
    public void MovePlayer(PlayerMoveEvent e) {
        if(e.getPlayer().getLocation().add(0, -1, 0).getBlock().getType() == Material.CAVE_AIR) return;
        if(e.getPlayer().getLocation().add(0, -1, 0).getBlock().getType() == Material.VOID_AIR) return;
        if(e.getPlayer().getLocation().add(0, -1, 0).getBlock().getType() == Material.AIR) return;
        if(e.getPlayer().getGameMode() == GameMode.SPECTATOR) return; 

        e.getPlayer().getLocation().add(0, -1, 0).getBlock().setType(Material.DIAMOND_ORE);
    }

}
