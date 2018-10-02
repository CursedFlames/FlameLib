package cursedflames.lib.gui;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

//just a copy-pasted modified minecraft slider with some feature stolen from the forge slider, because java doesn't have multiple inheritance
public class GuiSlider extends GuiBetterButton {
	private List<IGuiSliderListener> listeners = new ArrayList<IGuiSliderListener>();

	public void addListener(IGuiSliderListener listener) {
		listeners.add(listener);
	}

	private void guiSliderUpdated() {
		for (IGuiSliderListener listener : listeners) {
			listener.sliderActionPerformed(this);
		}
	}

	private double sliderPosition = 1.0D;
	public boolean isMouseDown;
	private final String name;
	private final double min;
	private final double max;
	private final boolean showDec;
	private final boolean drawStr;
	private final boolean snapToInt;

	public GuiSlider(int idIn, int x, int y, int width, int height, String nameIn, double minIn,
			double maxIn, double defaultValue, boolean showDec, boolean drawStr,
			boolean snapToInt) {
		super(idIn, x, y, width, height, "");
		this.name = nameIn;
		this.min = minIn;
		this.max = maxIn;
		this.sliderPosition = (defaultValue-minIn)/(maxIn-minIn);
		this.showDec = showDec;
		this.drawStr = drawStr;
		this.snapToInt = snapToInt;
		this.displayString = this.getDisplayString();
	}

	public GuiSlider(int idIn, int x, int y, int width, int height, String nameIn, double minIn,
			double maxIn, double defaultValue, boolean showDec, boolean drawStr, boolean snapToInt,
			IGuiSliderListener listener) {
		this(idIn, x, y, width, height, nameIn, minIn, maxIn, defaultValue, showDec, drawStr,
				snapToInt);
		addListener(listener);
	}

	/**
	 * Gets the value of the slider.
	 * 
	 * @return A value that will under normal circumstances be between the
	 *         slider's {@link #min} and {@link #max} values, unless it was
	 *         manually set out of that range.
	 */
	public double getSliderValue() {
		return this.min+(this.max-this.min)*this.sliderPosition;
	}

	public int getSliderValueInt() {
		return (int) Math.floor(getSliderValue());
	}

	/**
	 * Sets the slider's value, optionally notifying the associated
	 * {@linkplain GuiPageButtonList.GuiResponder responder} of the change.
	 */
	public void setSliderValue(double value) {
		this.sliderPosition = (value-this.min)/(this.max-this.min);
		if (this.sliderPosition>1.0F) {
			this.sliderPosition = 1.0F;
		} else if (this.sliderPosition<0.0F) {
			this.sliderPosition = 0.0F;
		}
		this.displayString = this.getDisplayString();
		this.guiSliderUpdated();
	}

//	public void increment() {
//		this.setSliderValue((double) (this.getSliderValue()+Math.pow(0.1, incrAmount)));
//	}
//
//	public void decrement() {
//		this.setSliderValue((double) (this.getSliderValue()-Math.pow(0.1, incrAmount)));
//	}

	/**
	 * Gets the slider's position.
	 * 
	 * @return The position of the slider, which will under normal circumstances
	 *         be between 0 and 1, unless it was manually set out of that range.
	 */
	public double getSliderPosition() {
		return this.sliderPosition;
	}

	// WTF is this past self
	private String getDisplayString() {
		if (!drawStr)
			return "";
		String val = showDec ? String.valueOf(getSliderValue())
				: String.valueOf((int) Math.floor(getSliderValue()));
		return I18n.format(this.name)+val;
		// return val;
	}

	/**
	 * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over
	 * this button and 2 if it IS hovering over this button.
	 */
	protected int getHoverState(boolean mouseOver) {
		return 0;
	}

	/**
	 * Fired when the mouse button is dragged. Equivalent of
	 * MouseListener.mouseDragged(MouseEvent e).
	 */
	protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
		if (this.visible) {
			if (this.isMouseDown) {
				this.sliderPosition = (double) (mouseX-(this.x+4))/(double) (this.width-8);

				if (this.sliderPosition<0.0F) {
					this.sliderPosition = 0.0F;
				}

				if (this.sliderPosition>1.0F) {
					this.sliderPosition = 1.0F;
				}
				if (snapToInt) {
					snapToInt();
				}
				this.displayString = this.getDisplayString();
				this.guiSliderUpdated();
			}

			GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
			this.drawTexturedModalRect(this.x+(int) (this.sliderPosition*(double) (this.width-8)),
					this.y, 0, 66, 4, this.height/2);
			this.drawTexturedModalRect(this.x+(int) (this.sliderPosition*(double) (this.width-8))+4,
					this.y, 196, 66, 4, this.height/2);
			this.drawTexturedModalRect(this.x+(int) (this.sliderPosition*(double) (this.width-8)),
					this.y+this.height/2, 0, 86-this.height/2, 4, this.height/2);
			this.drawTexturedModalRect(this.x+(int) (this.sliderPosition*(double) (this.width-8))+4,
					this.y+this.height/2, 196, 86-this.height/2, 4, this.height/2);
		}
	}

	/**
	 * Sets the position of the slider and notifies the associated
	 * {@linkplain GuiPageButtonList.GuiResponder responder} of the change
	 */
	public void setSliderPosition(double position) {
		this.sliderPosition = position;
		if (snapToInt) {
			snapToInt();
		} else {
			this.displayString = this.getDisplayString();
			this.guiSliderUpdated();
		}
	}

	private void snapToInt() {
		// snap to nearest value
		sliderPosition += 0.5F/(max-min);
		setSliderValue(getSliderValueInt());
	}

	/**
	 * Returns true if the mouse has been pressed on this control. Equivalent of
	 * MouseListener.mousePressed(MouseEvent e).
	 */
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
		if (super.mousePressed(mc, mouseX, mouseY)) {
			this.sliderPosition = (double) (mouseX-(this.x+4))/(double) (this.width-8);

			if (this.sliderPosition<0.0F) {
				this.sliderPosition = 0.0F;
			}

			if (this.sliderPosition>1.0F) {
				this.sliderPosition = 1.0F;
			}

			if (snapToInt) {
				snapToInt();
			}

			this.displayString = this.getDisplayString();
			this.guiSliderUpdated();
			this.isMouseDown = true;
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Fired when the mouse button is released. Equivalent of
	 * MouseListener.mouseReleased(MouseEvent e).
	 */
	public void mouseReleased(int mouseX, int mouseY) {
		this.isMouseDown = false;
	}
}
