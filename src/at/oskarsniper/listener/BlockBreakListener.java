package at.oskarsniper.listener;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import at.oskarsniper.core.Grave;

public class BlockBreakListener implements Listener {
	
    /*
     * 
     * Blocks block breaking events on graves! 
     * 
     */
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) 
    {
    	if(Grave.worlds.contains(e.getBlock().getWorld().getName()))
    	{
    		Block cBlock = e.getBlock();
    		/* Check if block is a grave */
    		if(cBlock.hasMetadata("grave"))
    		{
    			e.setCancelled(true);
    		}	
    	}
    }
}
