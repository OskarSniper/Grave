package at.oskarsniper.listener;

import java.util.Iterator;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import at.oskarsniper.core.Grave;
import at.oskarsniper.data.Graves;

public class InventoryCloseListener implements Listener {

	/*
	 * 
	 * Check at every InventoryCloseEvent if its a grave, so we can check if it's empty or not ;-)
	 * 
	 * */
	
    @EventHandler
    public void onClose(InventoryCloseEvent e) {
    	if(Grave.worlds.contains(e.getPlayer().getWorld().getName()))
    	{
			Iterator<?> it = Grave.graves.entrySet().iterator();
		    while (it.hasNext()) {
		        @SuppressWarnings("rawtypes")
				Map.Entry pair = (Map.Entry)it.next();
		        Graves gr = (Graves) pair.getValue();
		        Location loc = new Location(e.getInventory().getLocation().getWorld(), e.getInventory().getLocation().getBlockX(), e.getInventory().getLocation().getBlockY(), e.getInventory().getLocation().getBlockZ());
		        if((gr.chestl.equals(loc) || (gr.chestr.equals(loc))))
		        {
		        	ItemStack[] items = e.getInventory().getContents();
		        	if(Grave.isInventoryEmpty(items))
		        	{
		        		Grave.removeGraves.put(gr.id, Grave.getInstance().getConfig().getInt("Grave.time.IF_OPENED_AND_EMPTY"));
		        		@SuppressWarnings("unused")
						int task;
		        		task = Bukkit.getScheduler().runTaskLater(Grave.plugin, new Runnable() {
		        			
		        			@Override
		        			public void run()
		        			{
		        				Graves gra = gr;
				        		Location gloc = gra.location;
				        		LivingEntity giant = (LivingEntity) gloc.getWorld().spawnEntity(gloc, EntityType.GIANT);
				        		giant.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 3));
				        		giant.setInvulnerable(true);
				        		giant.setFireTicks(999999);
				        		giant.setCollidable(false);
				        		giant.setAI(false);
				        		giant.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				        		gra.addEntity(giant);
		        			}
		        		}, 2 * 20).getTaskId();
		        	}
		        }
		    }
    	}
    }
}
