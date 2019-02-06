package com.xyz.fig.shape;

import java.awt.Graphics2D;

/**
 * The Class Diamond.
 */
public class Diamond extends ShapeImpl {

    /** The w. */
    private final float w;

    /** The h. */
    private final float h;

    /**
     * Instantiates a new diamond.
     *
     * @param x
     *            the x
     * @param y
     *            the y
     * @param w
     *            the w
     * @param h
     *            the h
     */
    public Diamond(final float x, final float y, final float w, final float h) {
        super(x, y);
        this.w = w;
        this.h = h;
    }

    /**
     * Gets the w.
     *
     * @return the w
     */
    public float getW() {
        return w;
    }

    /**
     * Gets the h.
     *
     * @return the h
     */
    public float getH() {
        return h;
    }

    /* (non-Javadoc)
     * @see com.xyz.fig.shape.ShapeImpl#draw(java.awt.Graphics2D)
     */
    @Override
    public void draw(final Graphics2D f) {
        throw new RuntimeException("Not implemented");
    }
}
