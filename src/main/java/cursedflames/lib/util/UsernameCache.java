package cursedflames.lib.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import cursedflames.lib.CursedLib;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

public class UsernameCache extends WorldSavedData {
	private static final String DATA_NAME = CursedLib.MODID+"_UsernameCache";
	private static boolean enabled = false;

	private Map<UUID, String> usernames = new HashMap<>();

	public UsernameCache() {
		super(DATA_NAME);
	}

	public UsernameCache(String s) {
		super(s);
	}

	public static void setEnabled() {
		enabled = true;
	}

	public static UsernameCache get(World world) {
		MapStorage storage = world.getMapStorage();
		UsernameCache instance = (UsernameCache) storage.getOrLoadData(UsernameCache.class,
				DATA_NAME);
		if (instance==null) {
			instance = new UsernameCache();
			storage.setData(DATA_NAME, instance);
		}
		return instance;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		NBTTagCompound usernamesNBT = nbt.getCompoundTag("usernames");
		for (int i = 0; usernamesNBT.hasKey(String.valueOf(i)); i++) {
			NBTTagCompound entry = usernamesNBT.getCompoundTag(String.valueOf(i));
			if (entry.hasUniqueId("id")&&entry.hasKey("name")) {
				UUID id = entry.getUniqueId("id");
				String name = entry.getString("name");
				usernames.put(id, name);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		NBTTagCompound usernamesNBT = new NBTTagCompound();
		int i = 0;
		for (UUID id : usernames.keySet()) {
			NBTTagCompound entry = new NBTTagCompound();
			String name = usernames.get(id);
			entry.setUniqueId("id", id);
			entry.setString("name", name);
			usernamesNBT.setTag(String.valueOf(i), entry);
			i++;
		}
		compound.setTag("usernames", usernamesNBT);
		return compound;
	}

	public String getCachedName(UUID id) {
		return usernames.get(id);
	}

	@SubscribeEvent
	public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
		if (enabled&&!event.player.world.isRemote) {
			UsernameCache instance = get(event.player.world);
			String name = event.player.getName();
			UUID id = event.player.getUniqueID();
			if (name!=instance.usernames.get(id)) {
				instance.usernames.put(id, name);
				instance.markDirty();
			}
		}
	}
}
