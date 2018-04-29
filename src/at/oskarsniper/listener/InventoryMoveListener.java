package at.oskarsniper.listener;

import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Dropper;
import org.bukkit.block.Hopper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import at.oskarsniper.core.Grave;

public class InventoryMoveListener implements Listener {
	
    /*
     * 
     * Blocks annoying dropper or hopper or minecart Hopper, griefing is prevented by that!
     * 
     */
    @EventHandler
    public void onInventoryMove(InventoryMoveItemEvent e) {
    	if(Grave.worlds.contains(e.getDestination().getLocation().getWorld().getName()) || Grave.worlds.contains(e.getSource().getLocation().getWorld().getName()))
    	{
	    	Inventory dInv = e.getDestination();
	        Inventory sInv = e.getSource();
	       
	        if(dInv.getHolder() instanceof Hopper 
	        		|| dInv.getHolder() instanceof Dropper 
	        		|| sInv.getHolder() instanceof Hopper 
	        		|| sInv.getHolder() instanceof Dropper
	        		|| dInv.getType().equals(InventoryType.HOPPER)
	        		|| sInv.getType().equals(InventoryType.HOPPER)) {
	        	if (sInv.getHolder() instanceof DoubleChest) {
	        		DoubleChest c = (DoubleChest) sInv.getHolder();
		    		Block cBlock = c.getWorld().getBlockAt(c.getLocation());
		    		Chest chest = (Chest) cBlock.getState();
		    		/* Only block dropper or hopper if chest is defined as grave */
		        	if(chest.getBlock().getMetadata("grave").get(0).asBoolean())
		        	{
		        		e.setCancelled(true);
		        	}
	        	}
	        	if(dInv.getHolder() instanceof DoubleChest)
	        	{
	        		DoubleChest c = (DoubleChest) dInv.getHolder();
	        		Block cBlock = c.getWorld().getBlockAt(c.getLocation());
	        		Chest chest = (Chest) cBlock.getState();
		    		/* Only block dropper or hopper if chest is defined as grave */
		        	if(chest.getBlock().getMetadata("grave").get(0).asBoolean())
		        	{
		        		e.setCancelled(true);
		        	}
	        	}
	        }
    	}
    }
}
