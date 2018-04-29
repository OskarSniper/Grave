package at.oskarsniper.listener;

import java.util.LinkedHashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.block.DoubleChest;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Stairs;
import org.bukkit.metadata.FixedMetadataValue;

import at.oskarsniper.core.Grave;
import at.oskarsniper.data.Graves;

public class DeathListener implements Listener {
	
	/*
	 * 
	 * Creates graves if you die, max graves by player aren't set yet!
	 * 
	 */
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onDeath(PlayerDeathEvent e)
	{
		if(Grave.worlds.contains(e.getEntity().getPlayer().getWorld().getName()))
		{
			Player p = e.getEntity().getPlayer();
			World w = e.getEntity().getPlayer().getWorld();
			Location deathMatic = new Location(w, p.getLocation().getBlockX(), p.getLocation().getBlockY(), p.getLocation().getBlockZ());
			
			/* Check if player's inventory is empty! */
			if(!Grave.isInventoryEmpty(p))
			{		
				/* Save all blocks for restore after chest despawn */
				LinkedHashMap<Location, BlockState> allB = new LinkedHashMap<Location, BlockState>();
				
				UUID uuid = UUID.randomUUID();
				
				/* Create grave */
				Graves grave = new Graves();
				grave.setID(uuid);
				grave.setOwner(p.getUniqueId());
				grave.setEXP(p.getTotalExperience());
				grave.setLocked(true);
				grave.setOwnerLocked(false);
				//
				//grave.setCreationTimestamp(System.currentTimeMillis() / 1000);
				
				/* Load schematic */
				// Basic block
				Location basis = deathMatic.getBlock().getLocation();
				grave.setLocation(basis);
				basis.setY(basis.getY()-1);
				while(basis.getBlock().getType().equals(Material.AIR) 
						|| basis.getBlock().getType().equals(Material.DEAD_BUSH)
						|| basis.getBlock().getType().equals(Material.LEAVES)
						|| basis.getBlock().getType().equals(Material.POWERED_RAIL)
						|| basis.getBlock().getType().equals(Material.DETECTOR_RAIL)
						|| basis.getBlock().getType().equals(Material.ACTIVATOR_RAIL)
						|| basis.getBlock().getType().equals(Material.RAILS)
						|| basis.getBlock().getType().equals(Material.WEB)
						|| basis.getBlock().getType().equals(Material.LONG_GRASS)
						|| basis.getBlock().getType().equals(Material.YELLOW_FLOWER)
						|| basis.getBlock().getType().equals(Material.RED_ROSE)
						|| basis.getBlock().getType().equals(Material.BROWN_MUSHROOM)
						|| basis.getBlock().getType().equals(Material.RED_MUSHROOM)
						|| basis.getBlock().getType().equals(Material.STEP)
						|| basis.getBlock().getType().equals(Material.TORCH)
						|| basis.getBlock().getType().equals(Material.FIRE)
						|| basis.getBlock().getType().equals(Material.CROPS)
						|| basis.getBlock().getType().equals(Material.SIGN_POST)
						|| basis.getBlock().getType().equals(Material.WOODEN_DOOR)
						|| basis.getBlock().getType().equals(Material.LADDER)
						|| basis.getBlock().getType().equals(Material.LEVER)
						|| basis.getBlock().getType().equals(Material.STONE_PLATE)
						|| basis.getBlock().getType().equals(Material.WOOD_PLATE)
						|| basis.getBlock().getType().equals(Material.REDSTONE_TORCH_ON)
						|| basis.getBlock().getType().equals(Material.REDSTONE_TORCH_OFF)
						|| basis.getBlock().getType().equals(Material.SNOW)
						|| basis.getBlock().getType().equals(Material.CACTUS)
						|| basis.getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)
						|| basis.getBlock().getType().equals(Material.FENCE)
						|| basis.getBlock().getType().equals(Material.PUMPKIN)
						|| basis.getBlock().getType().equals(Material.JACK_O_LANTERN)
						|| basis.getBlock().getType().equals(Material.CAKE_BLOCK)
						|| basis.getBlock().getType().equals(Material.DIODE_BLOCK_OFF)
						|| basis.getBlock().getType().equals(Material.DIODE_BLOCK_ON)
						|| basis.getBlock().getType().equals(Material.STAINED_GLASS)
						|| basis.getBlock().getType().equals(Material.TRAP_DOOR)
						|| basis.getBlock().getType().equals(Material.THIN_GLASS)
						|| basis.getBlock().getType().equals(Material.IRON_FENCE)
						|| basis.getBlock().getType().equals(Material.MELON_BLOCK)
						|| basis.getBlock().getType().equals(Material.VINE)
						|| basis.getBlock().getType().equals(Material.FENCE_GATE)
						|| basis.getBlock().getType().equals(Material.NETHER_FENCE)
						|| basis.getBlock().getType().equals(Material.ENCHANTMENT_TABLE)
						|| basis.getBlock().getType().equals(Material.BREWING_STAND)
						|| basis.getBlock().getType().equals(Material.ENDER_PORTAL_FRAME)
						|| basis.getBlock().getType().equals(Material.DRAGON_EGG)
						|| basis.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)
						|| basis.getBlock().getType().equals(Material.REDSTONE_LAMP_ON)
						|| basis.getBlock().getType().equals(Material.WOOD_STEP)
						|| basis.getBlock().getType().equals(Material.TRIPWIRE_HOOK)
						|| basis.getBlock().getType().equals(Material.TRIPWIRE)
						|| basis.getBlock().getType().equals(Material.FLOWER_POT)
						|| basis.getBlock().getType().equals(Material.COBBLE_WALL)
						|| basis.getBlock().getType().equals(Material.WOOD_BUTTON)
						|| basis.getBlock().getType().equals(Material.SKULL)
						|| basis.getBlock().getType().equals(Material.ANVIL)
						|| basis.getBlock().getType().equals(Material.GOLD_PLATE)
						|| basis.getBlock().getType().equals(Material.IRON_PLATE)
						|| basis.getBlock().getType().equals(Material.REDSTONE_COMPARATOR_ON)
						|| basis.getBlock().getType().equals(Material.REDSTONE_COMPARATOR_OFF)
						|| basis.getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)
						|| basis.getBlock().getType().equals(Material.LEAVES_2)
						|| basis.getBlock().getType().equals(Material.HAY_BLOCK)
						|| basis.getBlock().getType().equals(Material.CARPET)
						|| basis.getBlock().getType().equals(Material.DOUBLE_PLANT))
				{
					basis.setY(basis.getY()-1);
				}
				basis.setY(basis.getY()+1);
				while(basis.getBlock().getType().equals(Material.WATER)
						|| (basis.getBlock().getType().equals(Material.STATIONARY_WATER)) 
						|| (basis.getBlock().getType().equals(Material.LAVA)) 
						|| (basis.getBlock().getType().equals(Material.STATIONARY_LAVA)))
				{
					basis.setY(basis.getY() + 1);
				}
				
				Block basic_block = basis.getBlock();
				
				basic_block.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(basic_block.getLocation(), basic_block.getState());
				basic_block.setType(Material.DOUBLE_STEP);
				
				// South Block
				Block slap_front = basic_block.getRelative(BlockFace.SOUTH);
				slap_front.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(slap_front.getLocation(), slap_front.getState());
				slap_front.setType(Material.SMOOTH_STAIRS);
				
				// East Block
				Block slap_left = basic_block.getRelative(BlockFace.EAST);
				slap_left.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(slap_left.getLocation(), slap_left.getState());
				slap_left.setType(Material.SMOOTH_STAIRS);
				Stairs stairs_left = (Stairs) slap_left.getState().getData();
				stairs_left.setFacingDirection(BlockFace.WEST);
				slap_left.setData(stairs_left.getData(), true);
				
				// West Block
				Block slap_right = basic_block.getRelative(BlockFace.WEST);
				slap_right.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(slap_right.getLocation(), slap_right.getState());
				slap_right.setType(Material.SMOOTH_STAIRS);
				Stairs stairs_right = (Stairs) slap_right.getState().getData();
				stairs_right.setFacingDirection(BlockFace.EAST);
				slap_right.setData(stairs_right.getData(), true);
				
				// North Block
				Block slap_back = basic_block.getRelative(BlockFace.NORTH);
				slap_back.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(slap_back.getLocation(), slap_back.getState());
				slap_back.setType(Material.SMOOTH_STAIRS);
				Stairs stairs_back = (Stairs) slap_back.getState().getData();
				stairs_back.setFacingDirection(BlockFace.SOUTH);
				slap_back.setData(stairs_back.getData(), true);
				
				// SignHolder
				Block signHolder = basic_block.getRelative(BlockFace.UP);
				signHolder.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(signHolder.getLocation(), signHolder.getState());
				signHolder.setType(Material.COBBLE_WALL);
				
				// Sign
				Block sign = signHolder.getRelative(BlockFace.SOUTH);
				sign.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(sign.getLocation(), sign.getState());
				sign.setType(Material.WALL_SIGN);
				sign.setData((byte) 3);
				
				Sign s = (Sign) sign.getState();
				s.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(s.getLocation(), s);
				
				s.setLine(0, "Ruhe in Frieden");
				s.setLine(1, "---------------");
				s.setLine(2, p.getDisplayName());
				s.setLine(3, "---------------");
				s.update();
				
				// Basic fence
				Block basic_fence = signHolder.getRelative(BlockFace.UP);
				basic_fence.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(basic_fence.getLocation(), basic_fence.getState());
				basic_fence.setType(Material.DARK_OAK_FENCE);
				
				// second fence
				Block second_fence = basic_fence.getRelative(BlockFace.UP);
				second_fence.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(second_fence.getLocation(), second_fence.getState());
				second_fence.setType(Material.DARK_OAK_FENCE);
				
				// left fence
				Block left_fence = second_fence.getRelative(BlockFace.EAST);
				left_fence.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(left_fence.getLocation(), left_fence.getState());
				left_fence.setType(Material.DARK_OAK_FENCE);
				
				// right fence
				Block right_fence = second_fence.getRelative(BlockFace.WEST);
				right_fence.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(right_fence.getLocation(), right_fence.getState());
				right_fence.setType(Material.DARK_OAK_FENCE);
				
				// last fence
				Block last_fence = second_fence.getRelative(BlockFace.UP);
				last_fence.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				allB.put(last_fence.getLocation(), last_fence.getState());
				last_fence.setType(Material.DARK_OAK_FENCE);
				
				/* Creating grave */
				/* Check if chest if in air */
				basis.setY(basis.getY() - 4);
				while((basis.getBlock().getType().equals(Material.AIR)) 
						|| (basis.getBlock().getType().equals(Material.WATER)) 
						|| (basis.getBlock().getType().equals(Material.STATIONARY_WATER)) 
						|| (basis.getBlock().getType().equals(Material.LAVA)) 
						|| (basis.getBlock().getType().equals(Material.STATIONARY_LAVA))
						|| basis.getBlock().getType().equals(Material.DEAD_BUSH)
						|| basis.getBlock().getType().equals(Material.LEAVES)
						|| basis.getBlock().getType().equals(Material.POWERED_RAIL)
						|| basis.getBlock().getType().equals(Material.DETECTOR_RAIL)
						|| basis.getBlock().getType().equals(Material.ACTIVATOR_RAIL)
						|| basis.getBlock().getType().equals(Material.RAILS)
						|| basis.getBlock().getType().equals(Material.WEB)
						|| basis.getBlock().getType().equals(Material.LONG_GRASS)
						|| basis.getBlock().getType().equals(Material.YELLOW_FLOWER)
						|| basis.getBlock().getType().equals(Material.RED_ROSE)
						|| basis.getBlock().getType().equals(Material.BROWN_MUSHROOM)
						|| basis.getBlock().getType().equals(Material.RED_MUSHROOM)
						|| basis.getBlock().getType().equals(Material.STEP)
						|| basis.getBlock().getType().equals(Material.TORCH)
						|| basis.getBlock().getType().equals(Material.FIRE)
						|| basis.getBlock().getType().equals(Material.CROPS)
						|| basis.getBlock().getType().equals(Material.SIGN_POST)
						|| basis.getBlock().getType().equals(Material.WOODEN_DOOR)
						|| basis.getBlock().getType().equals(Material.LADDER)
						|| basis.getBlock().getType().equals(Material.LEVER)
						|| basis.getBlock().getType().equals(Material.STONE_PLATE)
						|| basis.getBlock().getType().equals(Material.WOOD_PLATE)
						|| basis.getBlock().getType().equals(Material.REDSTONE_TORCH_ON)
						|| basis.getBlock().getType().equals(Material.REDSTONE_TORCH_OFF)
						|| basis.getBlock().getType().equals(Material.SNOW)
						|| basis.getBlock().getType().equals(Material.CACTUS)
						|| basis.getBlock().getType().equals(Material.SUGAR_CANE_BLOCK)
						|| basis.getBlock().getType().equals(Material.FENCE)
						|| basis.getBlock().getType().equals(Material.PUMPKIN)
						|| basis.getBlock().getType().equals(Material.JACK_O_LANTERN)
						|| basis.getBlock().getType().equals(Material.CAKE_BLOCK)
						|| basis.getBlock().getType().equals(Material.DIODE_BLOCK_OFF)
						|| basis.getBlock().getType().equals(Material.DIODE_BLOCK_ON)
						|| basis.getBlock().getType().equals(Material.STAINED_GLASS)
						|| basis.getBlock().getType().equals(Material.TRAP_DOOR)
						|| basis.getBlock().getType().equals(Material.THIN_GLASS)
						|| basis.getBlock().getType().equals(Material.IRON_FENCE)
						|| basis.getBlock().getType().equals(Material.MELON_BLOCK)
						|| basis.getBlock().getType().equals(Material.VINE)
						|| basis.getBlock().getType().equals(Material.FENCE_GATE)
						|| basis.getBlock().getType().equals(Material.NETHER_FENCE)
						|| basis.getBlock().getType().equals(Material.ENCHANTMENT_TABLE)
						|| basis.getBlock().getType().equals(Material.BREWING_STAND)
						|| basis.getBlock().getType().equals(Material.ENDER_PORTAL_FRAME)
						|| basis.getBlock().getType().equals(Material.DRAGON_EGG)
						|| basis.getBlock().getType().equals(Material.REDSTONE_LAMP_OFF)
						|| basis.getBlock().getType().equals(Material.REDSTONE_LAMP_ON)
						|| basis.getBlock().getType().equals(Material.WOOD_STEP)
						|| basis.getBlock().getType().equals(Material.TRIPWIRE_HOOK)
						|| basis.getBlock().getType().equals(Material.TRIPWIRE)
						|| basis.getBlock().getType().equals(Material.FLOWER_POT)
						|| basis.getBlock().getType().equals(Material.COBBLE_WALL)
						|| basis.getBlock().getType().equals(Material.WOOD_BUTTON)
						|| basis.getBlock().getType().equals(Material.SKULL)
						|| basis.getBlock().getType().equals(Material.ANVIL)
						|| basis.getBlock().getType().equals(Material.GOLD_PLATE)
						|| basis.getBlock().getType().equals(Material.IRON_PLATE)
						|| basis.getBlock().getType().equals(Material.REDSTONE_COMPARATOR_ON)
						|| basis.getBlock().getType().equals(Material.REDSTONE_COMPARATOR_OFF)
						|| basis.getBlock().getType().equals(Material.DAYLIGHT_DETECTOR)
						|| basis.getBlock().getType().equals(Material.LEAVES_2)
						|| basis.getBlock().getType().equals(Material.HAY_BLOCK)
						|| basis.getBlock().getType().equals(Material.CARPET)
						|| basis.getBlock().getType().equals(Material.DOUBLE_PLANT))
				{
					basis.setY(basis.getBlockY()-1);
				}
				basis.setY(basis.getBlockY()+1);
				
				/* Create chest */
				Block c1 = basis.getBlock();
				allB.put(c1.getLocation(), c1.getState());
				c1.setType(Material.CHEST);
				
				basis.setZ(basis.getBlockZ()-1);
				Block c2 = basis.getBlock();
				allB.put(c2.getLocation(), c2.getState());
				c2.setType(Material.CHEST);
				Block cBlock = w.getBlockAt(basis);
				Chest chest = (Chest) cBlock.getState();
				
				/* Set both chest locations */
				grave.setChestLeft(cBlock.getLocation());
				basis.setZ(basis.getBlockZ()+1);
				Block cBlock2 = w.getBlockAt(basis);
				grave.setChestRight(cBlock2.getLocation());
				
				/* LChest */
				chest.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				chest.setMetadata("uuid", new FixedMetadataValue(Grave.plugin, uuid));
				/* RChest */
				Chest rChest = (Chest) cBlock2.getState();
				rChest.setMetadata("grave", new FixedMetadataValue(Grave.plugin, true));
				rChest.setMetadata("uuid", new FixedMetadataValue(Grave.plugin, uuid));
				
				
				InventoryHolder ciH = chest.getInventory().getHolder();
				
				/* Fill chest with player's items */
				if (ciH instanceof DoubleChest) {
					DoubleChest c = (DoubleChest) ciH;
					ItemStack[] pInv = p.getInventory().getContents();
					ItemStack[] temp_cInv = new ItemStack[pInv.length];
					for(int i = 0; i < p.getInventory().getSize(); i++) { temp_cInv[i] = pInv[i]; }
					
					for(int i = 0; i < temp_cInv.length; i++)
					{
						if(temp_cInv[i] != null)
						{
							c.getInventory().addItem(temp_cInv[i]);
						}
					}
					
					e.getDrops().clear();
					p.getInventory().clear();
					e.setDroppedExp(0);
				}
				
				
				/* Set all replaced blocks into hashmap!*/
				grave.setBlocks(allB);
				
				/* Put grave into hashmap for later use */
				Grave.graves.put(uuid, grave);
				
				/* Grave is automatically disappering in 'MAX_TIME_ALIVE' minutes!*/
				Grave.removeGraves.put(uuid, Grave.getInstance().getConfig().getInt("Grave.time.MAX_TIME_ALIVE"));
				/* Done grave system */
			}
		}
	}
}
