package at.oskarsniper.listener;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import at.oskarsniper.core.Grave;

public class ExplosionListener implements Listener {

	/*
	 * 
	 * Block TNT or other explosives on grave!
	 * 
	 * */
	
	private ItemStack[] saveChest;
	private String[] lines;
	
	@EventHandler
	public void onExplosive(EntityExplodeEvent e)
	{
		if(Grave.worlds.contains(e.getEntity().getWorld().getName()))
		{
			Map<Location, BlockState> blocks = new HashMap<Location, BlockState>();
			for(Block b : e.blockList())
			{
				if(b.hasMetadata("grave"))
				{
					if(b.getType().equals(Material.CHEST))
					{
						Chest c = (Chest) b.getState();
						ItemStack[] cI = c.getInventory().getContents();
						if(c.getInventory().getSize() >= 53)
						{
							saveChest = new ItemStack[c.getInventory().getSize()];
							for(int i = 0; i < c.getInventory().getSize(); i++) { saveChest[i] = cI[i]; }
						}
						c.getInventory().clear();
					} else if(b.getType().equals(Material.WALL_SIGN))
					{
						Block sb = b;
						Sign sbs = (Sign) sb.getState();
						lines = sbs.getLines();
					}
					blocks.put(b.getLocation(), b.getState());
					b.setType(Material.AIR);
				}
			}
			Grave.plugin.getServer().getScheduler().scheduleSyncDelayedTask(Grave.plugin, new Runnable() {
	
				@SuppressWarnings("deprecation")
				@Override
				public void run() {
					ItemStack[] tmp = saveChest;
					for(BlockState state : blocks.values()) {
						state.update(true);
						if(state.getBlock().getType().equals(Material.COBBLE_WALL))
						{
							Block signblock = state.getBlock().getRelative(BlockFace.SOUTH);
							signblock.setType(Material.WALL_SIGN);
							signblock.setData((byte) 3);
							
							Sign sign = (Sign) signblock.getState();
							for(int i = 0; i < lines.length; i++)
							{
								sign.setLine(i, lines[i].toString());
							}
							sign.update();
						}
					}
					for (Entry<Location, BlockState> entry : blocks.entrySet())
					{
						BlockState bs = entry.getValue();
					    if(bs.getBlock().getType().equals(Material.CHEST))
					    {
					    	Chest c = (Chest) bs;
					    	for(ItemStack is : tmp)
					    	{
					    		if(is != null)
					    		{
					    			c.getInventory().addItem(is);
					    		}
					    	}
					    }
					}
				}
				
			}, 20L);
		}
	}
}
