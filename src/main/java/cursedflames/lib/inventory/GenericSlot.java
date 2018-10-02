package cursedflames.lib.inventory;

import java.util.function.Predicate;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class GenericSlot extends Slot {

	Predicate<ItemStack> isValid;

	public GenericSlot(IInventory inventoryIn, int index, int xPosition, int yPosition,
			Predicate<ItemStack> isValid) {
		super(inventoryIn, index, xPosition, yPosition);
		this.isValid = isValid;
	}

	public GenericSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		this(inventoryIn, index, xPosition, yPosition, i -> true);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return isValid.test(stack);
	}
}
