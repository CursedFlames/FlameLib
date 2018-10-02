package cursedflames.lib.block;

import javax.annotation.Nullable;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

// Thanks McJty - something something MIT license something something
// https://opensource.org/licenses/MIT
abstract public class GenericContainer extends Container {
	protected GenericTileEntity te;

	public GenericContainer(IInventory playerInventory, GenericTileEntity te) {
		this.te = te;

		// This container references items out of our own inventory (the 9 slots
		// we hold ourselves)
		// as well as the slots from the player inventory so that the user can
		// transfer items between
		// both inventories. The two calls below make sure that slots are
		// defined for both inventories.
		addOwnSlots();
		addPlayerSlots(playerInventory);
	}

	protected void addPlayerSlots(IInventory playerInventory) {
		// Slots for the main inventory
		for (int row = 0; row<3; ++row) {
			for (int col = 0; col<9; ++col) {
				int x = 9+col*18;
				int y = row*18+70;
				this.addSlotToContainer(new Slot(playerInventory, col+row*9+10, x, y));
			}
		}

		// Slots for the hotbar
		for (int row = 0; row<9; ++row) {
			int x = 9+row*18;
			int y = 58+70;
			this.addSlotToContainer(new Slot(playerInventory, row, x, y));
		}
	}

	protected void addOwnSlots() {
		IItemHandler itemHandler = this.te
				.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		int x = 9;
		int y = 6;

		// Add our own slot
		addSlotToContainer(new SlotItemHandler(itemHandler, 0, x, y));
	}

	// Magical copy-pasted shift-click code
	// TODO make this shift stacks into same-type stacks before empty stacks
	@Nullable
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = this.inventorySlots.get(index);

		if (slot!=null&&slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index==0) {
				if (!this.mergeItemStack(itemstack1, 1, this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
				return null;
			}

			if (itemstack1.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return te.canInteractWith(playerIn);
	}
}
