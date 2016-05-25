/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.webcore.client.lang;

/**
 * @author wu
 * 
 */
public class Size {

	private int width;

	private int height;

	private Size(int w, int h) {
		this.width = w;
		this.height = h;
	}

	/**
	 * @param w
	 * @param h
	 * @return
	 */
	public static Size valueOf(int w, int h) {
		// TODO Auto-generated method stub
		return new Size(w, h);
	}

	public Size minus(Size size) {
		return Size.valueOf(this.width - size.width, this.height - size.height);
	}

	public Size plus(Size size) {
		return Size.valueOf(this.width + size.width, this.height + size.height);
	}

	public Size divide(double zoom) {
		return divide(zoom, zoom);
	}

	public Size divide(double zx, double zy) {
		return Size.valueOf((int) ((double) this.width / zx), (int) ((double) this.height / zy));
	}

	public Size multiple(double zx, double zy) {
		return Size.valueOf((int) (this.width * zx), (int) (this.height * zy));

	}

	public Size multiple(double zoom) {
		return this.multiple(zoom, zoom);
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @return the widget
	 */
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Size)) {
			return false;
		}
		Size s2 = (Size) obj;
		return s2.height == this.height && s2.width == this.width;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Size,width:" + this.width + ",height:" + this.height + "";
	}

}
