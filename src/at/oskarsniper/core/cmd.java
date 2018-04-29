package at.oskarsniper.core;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import at.oskarsniper.data.Graves;

public class cmd implements CommandExecutor {
	
	@SuppressWarnings("deprecation")
	@Override
	// TODO: Only temporary ;-) Need to be removed afterwards!
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player) {
			if(args.length == 0)
			{
				sender.sendMessage(ChatColor.DARK_GRAY + "              «=»=«=» " + ChatColor.AQUA + "Grave - Help " + ChatColor.DARK_GRAY + "«=»=«=»");
				sender.sendMessage(ChatColor.YELLOW + ">> Version [" + Grave.getInstance().getDescription().getVersion() + "] made by OskarSniper");
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.DARK_AQUA + "/grave schloss " + ChatColor.GRAY + "- With this item you can lock graves!");
				sender.sendMessage(ChatColor.DARK_AQUA + "/grave dietrich " + ChatColor.GRAY + "- Needed for grave to open!");
				sender.sendMessage(ChatColor.DARK_AQUA + "/grave pfinder " + ChatColor.GRAY + "- Personal grave finder!");
				sender.sendMessage(ChatColor.DARK_AQUA + "/grave finder " + ChatColor.GRAY + "- Search for nearest grave!");
				sender.sendMessage(" ");
				sender.sendMessage(ChatColor.YELLOW + ">> Admintools");
				sender.sendMessage(ChatColor.DARK_AQUA + "/grave check message " + ChatColor.GRAY + "- Shows all messages with color!");
			} else if(args[0].equalsIgnoreCase("schloss"))
			{
				CraftPlayer p = (CraftPlayer) sender;
				ItemStack iss = new ItemStack(Material.IRON_BLOCK);
				ItemMeta meta = iss.getItemMeta();
				meta.setDisplayName("Eisenschloss");
				//meta.setLore(Arrays.asList("Durch das Eisenschloss kannst nur noch du die Kiste öffnen."));
				iss.setItemMeta(meta);
				p.getInventory().addItem(iss);
				return true;
			} else if(args[0].equalsIgnoreCase("dietrich")) { 
				CraftPlayer p = (CraftPlayer) sender;
				ItemStack iss = new ItemStack(Material.TRIPWIRE_HOOK);
				ItemMeta meta = iss.getItemMeta();
				meta.setDisplayName("Dietrich");
				//meta.setLore(Arrays.asList("Mit dem Dietrich kannst du andere Kisten öffnen, außer diese wurden mit einem Eisenschloss versehen!"));
				iss.setItemMeta(meta);
				p.getInventory().addItem(iss);
				return true;
			} else if(args[0].equalsIgnoreCase("pfinder")) {
				CraftPlayer p = (CraftPlayer) sender;
				ItemStack iss = new ItemStack(Material.COMPASS);
				ItemMeta meta = iss.getItemMeta();
				meta.setDisplayName("[P] SargSuchMaschine");
				//meta.setLore(Arrays.asList("Ach die gute alte SargSuchMaschine ;-) Damit kannst du nur deine eigenen Särge finden. Die Ältesten werden immer bevorzugt ;-)"));
				iss.setItemMeta(meta);
				p.getInventory().addItem(iss);
				return true;
			} else if(args[0].equalsIgnoreCase("finder")) {
				CraftPlayer p = (CraftPlayer) sender;
				ItemStack iss = new ItemStack(Material.COMPASS);
				ItemMeta meta = iss.getItemMeta();
				meta.setDisplayName("SargSuchMaschine");
				//meta.setLore(Arrays.asList("Ayayay, damit kannst du alle Särge finden, die nähesten werden hier bevorzugt!"));
				iss.setItemMeta(meta);
				p.getInventory().addItem(iss);
        	    return true;
			} else if(args[0].equalsIgnoreCase("check")) {
				if(args[1].length() == 0)
				{
					((Player) sender).getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> Use /grave check message!");
				} else if(args[1].equalsIgnoreCase("message"))
				{
					for(String str : Grave.getInstance().getConfig().getKeys(true))
					{
						if(str.contains("Grave.message."))
						{
							if(Grave.getInstance().getConfig().getString(str).isEmpty())
							{
								sender.sendMessage(ChatColor.YELLOW + ">> " + str.replace("Grave.message.", "") + ChatColor.RESET + " \n  -> \"\"");
								sender.sendMessage("");
							} else {
								sender.sendMessage(ChatColor.YELLOW + ">> " + str.replace("Grave.message.", "") + ChatColor.RESET + " \n  -> \"" + ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString(str)) + ChatColor.RESET + "\"");
								sender.sendMessage("");
							}
						}
					}
				} else {
					((Player) sender).getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> Use /grave check message!");
				}
				//Location loc = ((Player) sender).getPlayer().getLocation();
				//e.getPlayer().getWorld().playEffect(e.getPlayer().getLocation(), Effect.LARGE_SMOKE, 2003);
			} else if(args[0].equalsIgnoreCase("reload"))
			{
				((Player) sender).getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> Reloading config file!");
				Grave.getInstance().reloadConfig();
			} else if(args[0].equalsIgnoreCase("info"))
			{
				// TODO: Not finished right now, only for debug!
				if(args[1].length() == 0)
				{
					((Player) sender).getPlayer().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> Use /grave info <username>!");
				} else if(args[1].length() > 0)
				{
					HashMap<UUID, Graves> foundGraves = new HashMap<UUID, Graves>();
					Iterator<?> it = Grave.graves.entrySet().iterator();
				    while (it.hasNext()) {
				        @SuppressWarnings("rawtypes")
						Map.Entry pair = (Map.Entry)it.next();
				        Graves gr = (Graves) pair.getValue();
				        if(gr.owner.equals(Bukkit.getOfflinePlayer(args[1]).getUniqueId()))
				        {
				        	foundGraves.put(gr.id, gr);
				        }
				    }
				    
				    Player p = (Player) Bukkit.getOfflinePlayer(args[1]);
				    
				    sender.sendMessage(ChatColor.DARK_GRAY + "              «=»=«=» " + ChatColor.AQUA + "Grave - Info " + ChatColor.DARK_GRAY + "«=»=«=»");
				    sender.sendMessage("");
				    sender.sendMessage(ChatColor.AQUA + ">> " + p.getName() + " [" + p.getUniqueId() + "]");
				    sender.sendMessage(" Active graves: " + foundGraves.size());
				    if(foundGraves.size() == 1)
				    {
				    	sender.sendMessage("Only 1 grave, info instant!");
				    } else if(foundGraves.size() > 1)
				    {
				    	sender.sendMessage("Found more than 1 grave, give list of all graves!");
				    } else {
				    	sender.sendMessage("No grave here ;-)");
				    }
				}
			}
		}
		return false;
	}
}