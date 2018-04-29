package at.oskarsniper.listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import at.oskarsniper.core.Grave;

public class BlockPlaceListener implements Listener {

	/*
	 * 
	 * Block placing Eisenschloss or Dietrich in world ;-)
	 * 
	 * */
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent e)
	{
    	if(Grave.worlds.contains(e.getBlock().getWorld().getName()))
    	{
			if(e.getItemInHand().hasItemMeta())
			{
				if(e.getItemInHand().getItemMeta().getDisplayName().equals("Eisenschloss") && e.getItemInHand().getType().equals(Material.IRON_BLOCK))
				{
					e.setCancelled(true);
				} else if(e.getItemInHand().getItemMeta().getDisplayName().equals("Dietrich") && e.getItemInHand().getType().equals(Material.TRIPWIRE_HOOK))
				{
					e.setCancelled(true);
				}
			}
    	}
	}
}
