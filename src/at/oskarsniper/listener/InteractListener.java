package at.oskarsniper.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import at.oskarsniper.core.Grave;
import at.oskarsniper.data.Graves;
import net.md_5.bungee.api.ChatColor;

public class InteractListener implements Listener {
	@EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent e)
    {
		if(Grave.worlds.contains(e.getPlayer().getWorld().getName()))
		{
	    	if (e.getAction() == Action.RIGHT_CLICK_AIR) {
	    		/* Could be done better LOL */
	    		if(e.getItem().hasItemMeta())
	    		{
		        	if(e.getItem().getItemMeta().getDisplayName().equals("Eisenschloss"))
		        	{
		        		Iterator<?> it = Grave.graves.entrySet().iterator();
		        	    while (it.hasNext()) {
		        	        @SuppressWarnings("rawtypes")
							Map.Entry pair = (Map.Entry)it.next();
		        	        Graves gr = (Graves) pair.getValue();
		        	        if(gr.owner == e.getPlayer().getUniqueId())
		        	        {
			        	        if(!gr.ownerLocked)
			        	        {
			        	        	if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_GOT_LOCKED_WITH_IRONLOCK").isEmpty())
			        	        	{
			        	        		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_GOT_LOCKED_WITH_IRONLOCK")));
			        	        	}
			                		gr.setOwnerLocked(true);
			                		
			                		/* remove lock from inventory */
			                		
			        	        } else {
			        	        	if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_ALREADY_LOCKED_WITH_IRONLOCK").isEmpty())
			        	        	{
			        	        		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_ALREADY_LOCKED_WITH_IRONLOCK")));
			        	        	}
			        	        }
		        	        } else {
		        	        	if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_NOT_EXISTS").isEmpty())
		        	        	{
		        	        		e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_NOT_EXISTS")));
		        	        	}
		        	        }
		        	    }
		        	} else if(e.getItem().getItemMeta().getDisplayName().equals("SargSuchMaschine"))
		        	{
						Iterator<?> it = Grave.graves.entrySet().iterator();
						HashMap<UUID, Integer> gravefinder = new HashMap<UUID, Integer>();
		        	    while (it.hasNext()) {
		        	        @SuppressWarnings("rawtypes")
							Map.Entry pair = (Map.Entry)it.next();
		        	        Graves gr = (Graves) pair.getValue();
		        	        gravefinder.put(gr.id, (int) e.getPlayer().getLocation().distance(gr.location));
		        	    }
		        	    
		        	    Entry<UUID, Integer> min = null;
		        	    for (Entry<UUID, Integer> entry : gravefinder.entrySet()) {
		        	        if (min == null || min.getValue() > entry.getValue()) {
		        	            min = entry;
		        	        }
		        	    }
		        	    
		        	    Graves gr = (Graves) Grave.graves.get(min.getKey());
		        	    e.getPlayer().setCompassTarget(gr.location);
		        	} else if(e.getItem().getItemMeta().getDisplayName().equals("[P] SargSuchMaschine"))
		        	{
						Iterator<?> it = Grave.graves.entrySet().iterator();
		        	    while (it.hasNext()) {
		        	        @SuppressWarnings("rawtypes")
							Map.Entry pair = (Map.Entry)it.next();
		        	        Graves gr = (Graves) pair.getValue();
		        	        if(gr.owner.equals(e.getPlayer().getUniqueId()))
		        	        {
		        	        	e.getPlayer().setCompassTarget(gr.location);
		        	        }
		        	    }
		        	}
	    		}
	    	} else if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
	    	{
	    		if(e.getItem() != null)
	    		{
	    			if(e.getItem().hasItemMeta())
	    			{
						if(e.getItem().getItemMeta().getDisplayName().contains("Dietrich") 
								&& e.getItem().getType() == Material.TRIPWIRE_HOOK
								&& e.getClickedBlock().getType() == Material.CHEST
								&& e.getClickedBlock().hasMetadata("grave"))
						{
							if(Grave.graves.containsKey(UUID.fromString(e.getClickedBlock().getMetadata("uuid").get(0).asString())))
							{
								Graves gr = Grave.graves.get(UUID.fromString(e.getClickedBlock().getMetadata("uuid").get(0).asString()));
								if((gr.locked) && (!gr.ownerLocked))
								{
				    				Random r = new Random();
				    				float chance = r.nextFloat();
				    				if(chance <= 0.33f)
				    				{
				    					gr.setLocked(false);
				    					if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_UNLOCKED").isEmpty())
				    					{
				    						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_UNLOCKED")));
				    					}
				    					
				    					/* Opened Sound and Particles */
				    					if(gr.opened == 0)
				    					{
				    						e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 0.25f, 1f);
				    						List<Location> articles = new ArrayList<Location>();
				    						articles.add(gr.chestl);
				    						articles.add(gr.chestr);
				    						for(int i = 0; i < articles.size(); i++)
				    						{
				    							Location loc = (Location) articles.get(i);
					    						for (double t = 0; t < 40 * Math.PI; t += 0.39) 
					    						{
					    							e.getPlayer().getWorld().playEffect(loc, Effect.LARGE_SMOKE, 4);
					    						}
				    						}
				    						articles.clear();
				    					} else {
				    						e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 0.25f, 1f);
				    					}
				    					
				    					
				    					Grave.removeGraves.put(gr.id, Grave.getInstance().getConfig().getInt("Grave.time.IF_OPENED_BUT_NOT_EMPTY"));
				    				} else {
				    					if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_OPENED_NOT_SUCCESSFULLY_WITH_LOCK_PICK").isEmpty())
				    					{
				    						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_OPENED_NOT_SUCCESSFULLY_WITH_LOCK_PICK")));
				    					}
				                		/* TODO: Remove item after successfull opening */
				                		
				    					e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.BLOCK_ANVIL_LAND, 0.25f, 1f);
				    				}
								} else if(gr.ownerLocked)
								{
									if(gr.owner.equals(e.getPlayer().getUniqueId()))
									{
					    				Random r = new Random();
					    				float chance = r.nextFloat();
					    				if(chance <= 0.33f)
					    				{
					    					gr.setLocked(false);
					    					if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_UNLOCKED").isEmpty())
					    					{
					    						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_SUCCESSFULLY_UNLOCKED")));
					    					}
					    					
					    					/* Opened Sound and Particles */
					    					if(gr.opened == 0)
					    					{
					    						e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, 0.25f, 1f);
					    						List<Location> articles = new ArrayList<Location>();
					    						articles.add(gr.chestl);
					    						articles.add(gr.chestr);
					    						for(int i = 0; i < articles.size(); i++)
					    						{
					    							Location loc = (Location) articles.get(i);
						    						for (double t = 0; t < 40 * Math.PI; t += 0.39) 
						    						{
						    							e.getPlayer().getWorld().playEffect(loc, Effect.LARGE_SMOKE, 4);
						    						}
					    						}
					    						articles.clear();
					    					} else {
					    						e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.BLOCK_CHEST_OPEN, 0.25f, 1f);
					    					}
					    					
					    					Grave.removeGraves.put(gr.id, Grave.getInstance().getConfig().getInt("Grave.time.IF_OPENED_BUT_NOT_EMPTY"));
					    				} else {
					    					if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_OPENED_NOT_SUCCESSFULLY_WITH_LOCK_PICK").isEmpty())
					    					{
					    						e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_OPENED_NOT_SUCCESSFULLY_WITH_LOCK_PICK")));
					    					}
					    					/* TODO: Remove item after successfull opening */

					    					e.getPlayer().playSound(e.getPlayer().getEyeLocation(), Sound.BLOCK_ANVIL_LAND, 0.25f, 1f);
					    				}
									} else {
										if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_LOCK_IS_UNUSUAL").isEmpty())
										{
											e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_LOCK_IS_UNUSUAL")));
										}
									}
								} else {
									if(!Grave.getInstance().getConfig().getString("Grave.message.GRAVE_ALREADY_OPENED_WITH_LOCK_PICK").isEmpty())
									{
										e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('$', Grave.getInstance().getConfig().getString("Grave.message.GRAVE_ALREADY_OPENED_WITH_LOCK_PICK")));
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
