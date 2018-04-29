package at.oskarsniper.listener;

import java.util.UUID;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;

import at.oskarsniper.core.Grave;
import at.oskarsniper.data.Graves;
import net.md_5.bungee.api.ChatColor;

public class InventoryOpenListener implements Listener{
	
	/*
	 * 
	 * EventHandler for InventoryOpening, checking if double chest is a grave.
	 * 
	 */
	
    @EventHandler
    public void onInventoryOpenEvent(InventoryOpenEvent e) {
    	if(Grave.worlds.contains(e.getPlayer().getWorld().getName()))
    	{
	        if (e.getInventory().getHolder() instanceof DoubleChest) {
	        	DoubleChest c = (DoubleChest) e.getInventory().getHolder();
	    		Block cBlock = c.getWorld().getBlockAt(c.getLocation());
	    		Chest chest = (Chest) cBlock.getState();
	        	/* Check if double chest is a grave */
	    		if(chest.getBlock().hasMetadata("grave"))
	    		{
		        	if(chest.getBlock().getMetadata("grave").get(0).asBoolean())
		        	{
		        		/* Check if grave is in hashmap */
		        		if(Grave.graves.containsKey(UUID.fromString(chest.getBlock().getMetadata("uuid").get(0).asString())))
		        		{
		        			/* Retrieve from hashmap */
			        		if(Grave.graves.get(UUID.fromString(chest.getBlock().getMetadata("uuid").get(0).asString())).id.equals(UUID.fromString(chest.getBlock().getMetadata("uuid").get(0).asString())))
			        		{
			        			/* Load data */
			        			Graves gr = Grave.graves.get(UUID.fromString(chest.getBlock().getMetadata("uuid").get(0).asString()));
					    		Player p = (Player) e.getPlayer();
					    		if((gr.locked) && (!gr.ownerLocked))
					    		{
					    			if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_IS_LOCKED").isEmpty())
					    			{
					    				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_IS_LOCKED")));
					    			}
					    			e.setCancelled(true);
					    		} else if(gr.ownerLocked && gr.locked)
					    		{
					    			if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_IS_LOCKED_WITH_IRONLOCK").isEmpty())
					    			{
					    				e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_IS_LOCKED_WITH_IRONLOCK")));
					    			}
					    			e.setCancelled(true);
					    		} else 
					    		{
					    			if(gr.ownerLocked)
					    			{
					    				if(gr.owner.equals(p.getPlayer().getUniqueId()))
					    				{
					    					if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_OPENED").isEmpty())
					    					{
					    						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_OPENED")));
					    					}
					    					if(gr.exp > 0)
							    			{
							    				p.setTotalExperience(p.getTotalExperience() + gr.exp);
							    				gr.exp = 0;
							    			}
							    			gr.countOpened();
					    				} else {
					    					e.setCancelled(true);
					    				}
					    			} else {
					    				if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_OPENED").isEmpty())
					    				{
					    					e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_OPENED")));
					    				}
						    			if(gr.exp > 0)
						    			{
						    				p.setTotalExperience(p.getTotalExperience() + gr.exp);
						    				gr.exp = 0;
						    			}
						    			gr.countOpened();
					    			}
					    		}
			        		}
			        	}
		        	}
	    		}
	        }
    	}
    }
}
