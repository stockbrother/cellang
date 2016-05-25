/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.webframework.client.lang;

/**
 * @author wu
 * 
 */
public class Rectangle {

	private Point topLeft;

	private Size size;

	public Rectangle(Point topLeft, Point bottomRight) {
		this(topLeft, Size.valueOf(bottomRight.getX() - topLeft.getX(), bottomRight.getY() - topLeft.getY()));
	}

	public Rectangle(Point topLeft, Size size) {
		this.topLeft = topLeft;
		this.size = size;
	}

	public Rectangle plus(Point point) {
		return new Rectangle(this.topLeft.plus(point), this.size);
	}

	public int getTopY() {// y
		return this.topLeft.getY();
	}

	public int getLeftX() {
		return this.topLeft.getX();
	}

	public int getBottomY() {
		return this.getBottomLeft().getY();
	}

	public int getRightX() {
		return this.getBottomRight().getX();
	}

	public Point getBottomRight() {
		return this.topLeft.add(size.getWidth(), size.getHeight());
	}

	public Point getBottomLeft() {
		return this.topLeft.add(0, size.getHeight());
	}

	public Point getCenter() {
		return this.topLeft.add(size.getWidth() / 2, size.getHeight() / 2);//
	}

	/**
	 * @return the topLeft
	 */
	public Point getTopLeft() {
		return topLeft;
	}

	public Point getTopRight() {
		return Point.valueOf(this.topLeft.getX() + this.size.getWidth(), this.topLeft.getY());
	}

	/**
	 * @return the size
	 */
	public Size getSize() {
		return size;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Rectangle)) {
			return false;
		}
		Rectangle r2 = (Rectangle) obj;
		return r2.topLeft.equals(this.topLeft) && this.size.equals(r2.size);
	}

	public boolean contains(Point p) {
		return p.minus(this.topLeft).isFirstArea(true) && this.getBottomRight().minus(p).isFirstArea(true);
	}

	public boolean contains(Rectangle rect) {
		return rect.topLeft.minus(this.topLeft).isFirstArea(true)
				&& this.getBottomRight().minus(rect.getBottomRight()).isFirstArea(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Rectangle,topLeft:" + this.topLeft + ",size:" + this.size;
	}

	public Point getShortestPointTo(Point point2) {
		int x = point2.getX();
		int y = point2.getY();
		Point tl = this.getTopLeft();
		Point br = this.getBottomRight();
		if (x < tl.getX()) {
			x = tl.getX();
		}

		if (y < tl.getY()) {
			y = tl.getY();
		}

		if (x > br.getX()) {
			x = br.getX();
		}

		if (y > br.getY()) {
			y = br.getY();
		}
		return Point.valueOf(x, y);
	}

}
