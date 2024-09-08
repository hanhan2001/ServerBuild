package me.xiaoying.serverbuild.gui;

import org.bukkit.inventory.ItemStack;

public class Component {
    private final ItemStack itemStack;
    private int x;
    private int y;
    private Runnable click;
    private Runnable hover;
    private boolean close = true;

    public Component(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * Get component's ItemStack
     *
     * @return
     */
    public ItemStack getItemStack() {
        return this.itemStack;
    }

    /**
     * Set click event of click component
     *
     * @param runnable Runnable
     * @return Component
     */
    public Component setClick(Runnable runnable) {
        this.click = runnable;
        return this;
    }

    /**
     * Call click event of component
     */
    public void onClick() {
        if (this.click == null)
            return;

        this.click.run();
    }

    /**
     * Set hover event of component
     *
     * @param runnable Runnable
     * @return Component
     */
    public Component setHover(Runnable runnable) {
        this.hover = runnable;
        return this;
    }

    /**
     * Call hover event of component
     */
    public void onHover() {
        if (this.hover == null)
            return;

        this.hover.run();
    }

    /**
     * Set component's x-axis
     *
     * @param x x-axis
     * @return Component
     */
    public Component setX(int x) {
        this.x = x;
        return this;
    }

    /**
     * Get component's x-axis
     *
     * @return Component's x-axis
     */
    public int getX() {
        return this.x;
    }

    /**
     * Set component's y-axis
     *
     * @param y y-axis
     * @return Component
     */
    public Component setY(int y) {
        this.y = y;
        return this;
    }

    /**
     * Get component's y-axis
     *
     * @return y-axis
     */
    public int getY() {
        return this.y;
    }

    /**
     * Get gui needed close when clicked component
     *
     * @return Gui needed close
     */
    public boolean needClose() {
        return this.close;
    }

    /**
     * Set gui needed close when click this component
     *
     * @param needClose boolean
     * @return Component
     */
    public Component setClose(boolean needClose) {
        this.close = needClose;
        return this;
    }
}