package at.oskarsniper.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import at.oskarsniper.data.Configuration;
import at.oskarsniper.data.Graves;
import at.oskarsniper.data.Terrain;
import at.oskarsniper.listener.*;

/*
 * 
 * Author: OskarSniper
 * 
 * */

public class Grave extends JavaPlugin {
	
	public static Plugin plugin;
	public static Grave core;
	public static ArrayList<String> worlds;
	
	/* Contains all active graves */
	public static HashMap<UUID, Graves> graves = new HashMap<UUID, Graves>();
	
	/* Remove graves */
	public static int Scheduler;
	public static HashMap<UUID, Integer> removeGraves = new HashMap<UUID, Integer>();
	private static int cooldown;
	
	/*
	 * #----------------------------------------------------------#
	 * #                                                          #
	 * #                  TO DO / FIX ME Board                    #
	 * #                                                          #
	 * #----------------------------------------------------------#
	 * 
	 * TODO: Change cmd ;-)
	 * TODO: Remove used items
	 * FIXME: Levelpoints
	 * 
	 * 
	 */
	
	@Override
	public void onEnable()
	{
		plugin = this;
		core = this;
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] " + ChatColor.AQUA + "# PLUGIN BOOTUP CHECK #");
		Configuration.loadConfiguration();
		int used = 0;
		int unused = 0;
		for(String str : Grave.getInstance().getConfig().getKeys(true))
		{
			if(str.contains("Grave.message."))
			{
				if(Grave.getInstance().getConfig().getString(str).isEmpty())
				{
					unused++;
				} else {
					used++;
				}
			}
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] --- Message Settings ---");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> Unused: '" + unused + "'!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> Used  : '" + used + "'!");
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] ---  World Settings  ---");
		worlds = (ArrayList<String>) Grave.getInstance().getConfig().getStringList("Grave.worlds");
		for(World w : Bukkit.getWorlds())
		{
			if(worlds.contains(w.getName()))
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> is" + ChatColor.GREEN + " working" + ChatColor.GRAY + " @ '" + w.getName() + "'!");
			} else {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> is " + ChatColor.RED + "NOT" + ChatColor.GRAY + " working @ '" + w.getName() + "'!");
			}
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] " + ChatColor.YELLOW + "* PLUGIN INFO * " + ChatColor.GRAY + " >> Worlds can be added in 'config.yml'!");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] ---  Time  Settings  ---");
		for(String str : Grave.getInstance().getConfig().getKeys(true))
		{
			if(str.contains("Grave.time."))
			{
				Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> " + str.replace("Grave.time.", "") + " = \"" + Grave.getInstance().getConfig().getInt(str) + "\"");
			}
		}
		cooldown = Grave.getInstance().getConfig().getInt("Grave.time.MAX_TIME_ALIVE");
		
		Bukkit.getServer().getPluginManager().registerEvents(new BlockBreakListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DeathListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InteractListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryMoveListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryOpenListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryCloseListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new ExplosionListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new onPistonMoveListener(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new DamageListener(), this);
		this.getCommand("grave").setExecutor(new cmd());
		
		Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] " + ChatColor.AQUA + "# PLUGIN BOOTUP CHECK END #");
		
		Scheduler = Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
			
			@Override
			public void run() {
				for(Entry<UUID, Integer> entry : removeGraves.entrySet())
				{
					if(removeGraves.get(entry.getKey()) >= 1)
					{
						for(int x = 1; x <= cooldown; x++)
						{
							if(removeGraves.get(entry.getKey()) == x)
							{
								removeGraves.put(entry.getKey(), entry.getValue()-1);
							}
						}
					} else {
						removeGraves.remove(entry.getKey());
						Terrain.restore(graves.get(entry.getKey()).blocks);
						graves.get(entry.getKey()).entity.remove();
						graves.remove(entry.getKey());
					}
				}
			}
		}, 20L, 20L);
	}
	
	@Override
	public void onDisable()
	{
		// Remove all existing graves before reload or server restart fully done!
		for(Entry<UUID, Graves> gr : graves.entrySet())
		{
	        for(Map.Entry<Location, BlockState> set : gr.getValue().blocks.entrySet()) {
	        	BlockState state = set.getValue();
	        	state.update(true, false);
	        	state.removeMetadata("grave", Grave.core);
	        	if(state.hasMetadata("uuid")) { state.removeMetadata("uuid", Grave.core); }
	        	if(state.getBlock().getType().equals(Material.CHEST))
	        	{
	        		Chest c = (Chest) state.getBlock().getState();
	        		c.getInventory().clear();
	        	}
	        	if(state.getBlock().getType().equals(Material.WALL_SIGN))
	        	{
	        		state.getBlock().setType(Material.AIR);
	        	}
	        	
	        	Block n_loc = state.getBlock();
	        	Location nc_loc = n_loc.getLocation();
	        	nc_loc.setY(nc_loc.getBlockY() - 1);
	        	if(nc_loc.getBlock().getType().equals(Material.AIR))
	        	{
	        		n_loc.getLocation().getBlock().setType(Material.AIR);
	        	}
	        }
		}
		graves.clear();
	}
	
	public static Plugin getInstance() {
		return plugin;
	}
	
	public static boolean isInventoryEmpty(Player p){
		for(ItemStack item : p.getInventory().getContents())
		{
		    if(item != null)
		      return false;
		}
		return true;
	}
	
	public static boolean isInventoryEmpty(ItemStack[] items){
		for(ItemStack item : items)
		{
		    if(item != null)
		      return false;
		}
		return true;
	}
}
