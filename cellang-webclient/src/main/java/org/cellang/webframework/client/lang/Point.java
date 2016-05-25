/**
 * All right is from Author of the file,to be explained in comming days.
 * Oct 5, 2012
 */
package org.cellang.webframework.client.lang;

/**
 * @author wu
 * 
 */
public class Point {

	private int x;

	private int y;

	/**
	 * @param absoluteTop
	 * @param absoluteLeft
	 */
	private Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public static Point valueOf(int x, int y) {
		return new Point(x, y);
	}

	public Point add(int x, int y) {
		return Point.valueOf(this.x + x, this.y + y);
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(point,x:" + x + ",y:" + y + ")";
	}

	@Override
	public boolean equals(Object obj) {
		if (null == obj || !(obj instanceof Point)) {
			return false;
		}
		Point p2 = (Point) obj;
		return this.x == p2.x && this.y == p2.y;
	}

	public Point minus(Point p2) {
		return Point.valueOf(this.x - p2.x, this.y - p2.y);
	}

	public Point plus(Point p2) {
		return Point.valueOf(this.x + p2.x, this.y + p2.y);
	}

	//first limit,x ,y allo great than 0;
	public boolean isFirstArea(boolean includeZero) {
		if (includeZero) {
			return this.x >= 0 && this.y >= 0;
		} else {
			return this.x > 0 && this.y > 0;
		}
	}

	public Point positive() {
		return Point.valueOf(Math.max(0, this.x), Math.max(0, this.y));
	}

}
