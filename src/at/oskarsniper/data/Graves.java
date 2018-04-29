package at.oskarsniper.data;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.inventory.ItemStack;

public class Graves {

	/*
	 * 
	 * This contains all information that's been locked on every generated grave!
	 * 
	 * */
	
	
	public UUID id;
	public Location location;
	public Location chestl;
	public Location chestr;
	public UUID owner;
	public int exp;
	public boolean locked;
	public boolean ownerLocked;
	public int opened = 0;
	public LinkedHashMap<Location, BlockState> blocks;
	public long creation_timestamp;
	public ItemStack[] chestItems;
	public Entity entity;
	
	public void setID(UUID i)
	{
		id = i;
	}
	
	public void setLocation(Location loc)
	{
		location = loc;
	}
	
	public void setChestLeft(Location loc)
	{
		chestl = loc;
	}
	
	public void setChestRight(Location loc)
	{
		chestr = loc;
	}
	
	public void setOwner(UUID uuid)
	{
		owner = uuid;
	}
	
	public void setEXP(int i)
	{
		exp = i;
	}
	
	public void setLocked(boolean status)
	{
		locked = status;
	}
	
	public void setOwnerLocked(boolean status)
	{
		ownerLocked = status;
	}
	
	public void countOpened()
	{
		opened = opened + 1;
	}
	
	public void setBlocks(LinkedHashMap<Location, BlockState> block)
	{
		blocks = block;
	}
	
	public void setCreationTimestamp(long time)
	{
		creation_timestamp = time;
	}
	
	public void setItemStack(ItemStack[] items)
	{
		chestItems = items;
	}
	
	public void addEntity(Entity e)
	{
		entity = e;
	}
	
}
