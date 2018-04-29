package at.oskarsniper.data;

import java.util.LinkedHashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;

import at.oskarsniper.core.Grave;

public class Terrain {

	/*
	 * 
	 * Terrain restore tool ;-)
	 * 
	 * */
	
	public static void restore(LinkedHashMap<Location, BlockState> block)
	{
		@SuppressWarnings("unused")
		int task;
		task = Bukkit.getScheduler().runTaskLater(Grave.plugin, new Runnable() {
			
			public void run()
			{
		        for(Map.Entry<Location, BlockState> set : block.entrySet()) {
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
		}, 2 * 20).getTaskId();
	}
}
