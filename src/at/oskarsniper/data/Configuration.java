package at.oskarsniper.data;

import java.io.File;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import at.oskarsniper.core.Grave;

public class Configuration {
	
	public static File f = new File("plugins/Grave", "config.yml");
	public static FileConfiguration cfg;
	
	public static void loadConfiguration()
	{
		if((!Grave.getInstance().getDataFolder().exists()) || (!f.exists())){
			Grave.getInstance().getDataFolder().mkdirs();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GRAY + "[" + ChatColor.DARK_AQUA + "Grave" + ChatColor.GRAY + "] >> File " + ChatColor.RED + "config.yml" + ChatColor.GRAY + " not found. " + ChatColor.GRAY + "Freshly new file generated!");
			Grave.getInstance().getConfig().options().header(
			  "#-------------------- Global Settings --------------------#\n"
			+ "#                                                         #\n"
			+ "# worlds           = Plugin is only active there!         #\n"
			+ "#                                                         #\n"
			+ "#--------------------- Time Settings ---------------------#\n"
			+ "#                                                         #\n"
			+ "# MAX_TIME_ALIVE          = 30 minutes in seconds         #\n"
			+ "# IF_OPENED_BUT_NOT_EMPTY = 10 minutes in seconds         #\n"
			+ "# IF_OPENED_AND_EMPTY     = 1 minute in seconds           #\n"
			+ "#                                                         #\n"
			+ "#-------------------- Message Settings -------------------#\n"
			+ "#                                                         #\n"
			+ "#           !Set color with $ instead of &!               #\n"
			+ "# G_ = GRAVE_                                             #\n"
			+ "# G_IS_LOCKED = Grave is closed :P                        #\n"
			+ "# G_IF_CLOSED_WITH_IRONLOCK = Grave is closed with lock   #\n"
			+ "# G_SUCCESSFULLY_OPENED = Grave opened successfully       #\n"
			+ "# G_GOT_IRONLOCK = Grave is now locked with iron lock     #\n"
			+ "# G_ALREADY_LOCKED_WITH_IRONLOCK = Grave locked with lock #\n"
			+ "# G_NOT_EXISTS = Player has no active grave               #\n"
			+ "# G_SUCCESSFULLY_UNLOCKED = Grave is now opened           #\n"
			+ "# G_ALREADY_OPENED_WITH_LOCK_PICK = Already opened        #\n"
			+ "# G_OPENED_NOT_SUCCESSFULLY_WITH_LOCk_PICK = Not opened   #\n"
			+ "# G_LOCK_IS_UNUSUAL = Grave only unlocked by owner        #\n"
			+ "#                                                         #\n"
			+ "#---------------------------------------------------------#\n");
			
			
				ArrayList<String> worlds = new ArrayList<String>();
				worlds.add("exampleWorld1");
				worlds.add("exampleWorld2");
				Grave.getInstance().getConfig().addDefault("Grave.worlds", worlds);
				Grave.getInstance().getConfig().addDefault("Grave.time.MAX_TIME_ALIVE", 1800);
				Grave.getInstance().getConfig().addDefault("Grave.time.IF_OPENED_BUT_NOT_EMPTY", 600);
				Grave.getInstance().getConfig().addDefault("Grave.time.IF_OPENED_AND_EMPTY", 60);
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_IS_LOCKED", "Dieser Sarg ist verschlossen.");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_IS_LOCKED_WITH_IRONLOCK", "Der Sarg wurde mit einem robusten Eisenschloss versehen.");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_SUCCESSFULLY_OPENED", "Du hast den Sarg erfolgreich geöffnet.");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_GOT_LOCKED_WITH_IRONLOCK", "Du hast deinen Sarg mit einem robusten Eisenschloss versehen.");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_ALREADY_LOCKED_WITH_IRONLOCK", "Du hast deinen Sarg bereits abgesichert.");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_NOT_EXISTS", "Du besitzt keinen Sarg!");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_SUCCESSFULLY_UNLOCKED", "Du hast den Sarg erfolgreich aufgebrochen!");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_ALREADY_OPENED_WITH_LOCK_PICK", "Das Schloss wurde breits geknackt!");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_OPENED_NOT_SUCCESSFULLY_WITH_LOCK_PICK", "Der Dietrich ist abgebrochen!");
				Grave.getInstance().getConfig().addDefault("Grave.message.GRAVE_LOCK_IS_UNUSUAL", "Das Schloss ist aber ungewöhnlich!");
				
				Grave.getInstance().getConfig().options().copyDefaults(true);
				Grave.getInstance().getConfig().options().copyHeader(true);
				saveConfiguration();
		}
		cfg = YamlConfiguration.loadConfiguration(f);
	}
	
	public static void saveConfiguration(){
		Grave.getInstance().saveConfig();
	}

}
