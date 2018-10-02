package cursedflames.lib;

import cursedflames.lib.network.PacketHandler;
import cursedflames.lib.util.UsernameCache;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

//TODO13 separate CLib mod stuff and lib stuff
@Mod(useMetadata = true, modid = CursedLib.MODID)
@Mod.EventBusSubscriber
public class CursedLib {
	public static final String MODID = "cursedlib";

	@Mod.EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(UsernameCache.class);
	}

	@Mod.EventHandler
	public static void init(FMLInitializationEvent event) {
		PacketHandler.registerMessages();
	}
}
