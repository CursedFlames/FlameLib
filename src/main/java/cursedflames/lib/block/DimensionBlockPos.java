package cursedflames.lib.block;

import cursedflames.lib.INBTSavable;
import cursedflames.lib.Util;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

/**
 * Class to help with storing {@link BlockPos}es and dimension ids. Pretty self
 * explanitory really.
 */
public class DimensionBlockPos implements INBTSavable {
	private BlockPos pos;
	private int dim;

	// should only be used for readFromNBT since it's non-static
	public DimensionBlockPos() {
		this.pos = null;
		this.dim = 0;
	}

	public DimensionBlockPos(BlockPos pos, int dim) {
		this.pos = pos;
		this.dim = dim;
	}

	public DimensionBlockPos(int x, int y, int z, int dim) {
		this.pos = new BlockPos(x, y, z);
		this.dim = dim;
	}

	public DimensionBlockPos(double x, double y, double z, int dim) {
		this.pos = new BlockPos(x, y, z);
		this.dim = dim;
	}

	/**
	 * 
	 * @param loadDim
	 *            whether to load the dimension if it isn't already
	 * @return the {@link World} of this pos's dimension, or null if it could
	 *         not be loaded
	 */
	public World getWorld(boolean loadDim) {
		WorldServer world = DimensionManager.getWorld(dim);
		if (loadDim&&world==null) {
			DimensionManager.initDimension(dim);
			world = DimensionManager.getWorld(dim);
		}
		return world;
	}
	// TODO getBlockState()

	/**
	 * 
	 * @param loadDim
	 *            whether to load the dimension if it isn't already
	 * @param loadCube
	 *            whether to load the cube (chunk) if it isn't already
	 * @return the {@link TileEntity} at this pos, or null if there is none or
	 *         it couldn't be loaded
	 */
	public TileEntity getTileEntity(boolean loadDim, boolean loadCube) {
		World world = getWorld(loadDim);
		if (world==null)
			return null;
		return loadCube||world.isBlockLoaded(pos) ? world.getTileEntity(pos) : null;
	}

	public BlockPos getPos() {
		return pos;
	}

	public void setPos(BlockPos pos) {
		this.pos = pos;
	}

	public int getDim() {
		return dim;
	}

	public void setDim(int dim) {
		this.dim = dim;
	}

	// not sure if I actually need to override this
	@Override
	public boolean equals(Object o) {
		if (this==o)
			return true;
		if (!(o instanceof DimensionBlockPos))
			return false;
		DimensionBlockPos dimPos = (DimensionBlockPos) o;
		return pos.equals(dimPos.pos)&&dim==dimPos.dim;
	}

	@Override
	public int hashCode() {
		return pos.hashCode()+(dim<<24);
	}

	public NBTTagCompound writeToNBT() {
		NBTTagCompound tag = Util.blockPosToNBT(pos);
		tag.setInteger("dim", dim);
		return tag;
	}

	public DimensionBlockPos readFromNBT(NBTTagCompound tag) {
		pos = Util.blockPosFromNBT(tag);
		dim = tag.getInteger("dim");
		return this;
	}
}
