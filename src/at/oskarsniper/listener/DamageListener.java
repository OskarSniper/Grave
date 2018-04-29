package at.oskarsniper.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageByEntityEvent e)
	{
		if(e.getEntity().hasMetadata("grave"))
		{
			e.setCancelled(true);
		}
	}

}
