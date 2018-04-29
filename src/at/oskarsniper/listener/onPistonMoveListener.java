package at.oskarsniper.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPistonRetractEvent;

import at.oskarsniper.core.Grave;


public class onPistonMoveListener implements Listener {

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPistonExtend(BlockPistonExtendEvent e)
	{
		if(Grave.worlds.contains(e.getBlock().getWorld().getName()))
		{
			Block piston = e.getBlock();
			Block ext = null;
	        switch(piston.getData()) {
	            case 0:
	            	ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,-1,0));
	            break;
	            case 1:
	                ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,1,0));
	            break;
	            case 2:
	            	ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,0,-1));
	            break;
	            case 3:
	                ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,0,1));
	            break;
	            case 4:
	            	ext = piston.getWorld().getBlockAt(piston.getLocation().add(-1,0,0));
	            break;
	            case 5:
	            	ext = piston.getWorld().getBlockAt(piston.getLocation().add(1,0,0));
	            break;
	        }
	        
			Block block = piston.getRelative(e.getDirection());
			if(block.hasMetadata("grave") || ext.hasMetadata("grave"))
			{
				e.setCancelled(true);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPistonRetract(BlockPistonRetractEvent e)
	{
		if(Grave.worlds.contains(e.getBlock().getWorld().getName()))
		{
			Block piston = e.getBlock();
			Block ext = null;
	        switch(piston.getData()) {
		        case 8:
		        	ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,-2,0));
		        break;
		        case 9:
		            ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,2,0));
		        break;
		        case 10:
		        	ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,0,-2));
		        break;
		        case 11:
		            ext = piston.getWorld().getBlockAt(piston.getLocation().add(0,0,2));
		        break;
		        case 12:
		        	ext = piston.getWorld().getBlockAt(piston.getLocation().add(-2,0,0));
		        break;
		        case 13:
		        	ext = piston.getWorld().getBlockAt(piston.getLocation().add(2,0,0));
		        break;
	        }
	        
			if(e.isSticky())
			{
				if(e.getBlock().hasMetadata("grave") || ext.hasMetadata("grave"))
				{
					e.setCancelled(true);
				}
			}
		}
	}
}
