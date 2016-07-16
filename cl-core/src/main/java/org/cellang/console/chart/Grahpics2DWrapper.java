package org.cellang.console.chart;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Grahpics2DWrapper {

	Graphics2D g2d;

	public Grahpics2DWrapper() {

	}

	public Graphics2D get() {
		return this.g2d;
	}

	public Grahpics2DWrapper set(Graphics2D g2d) {
		this.g2d = g2d;
		return this;
	}

	public Grahpics2DWrapper translate(int x, int y) {
		this.g2d.translate(x, y);//
		return this;
	}

	public void fillRect(int x, int y, int width, int height) {
		this.g2d.fillRect(x, y, width, height);
	}

	protected void drawString(String string, int x, int y, Color color) {
		drawString(string, x, y, color, 0);
	}

	protected void drawLine(int x1, int y1, int x2, int y2) {
		g2d.drawLine(x1, y1, x2, y2);
	}

	protected void drawLine(int x1, int y1, int x2, int y2, Color color) {
		Color old = g2d.getColor();
		g2d.setColor(color);

		g2d.drawLine(x1, y1, x2, y2);
		g2d.setColor(old);//
	}

	public void drawString(String text, int x, int y, Color color, int angle) {

		Color old = g2d.getColor();
		g2d.setColor(color);

		g2d.translate((float) x, (float) y);

		g2d.rotate(Math.toRadians(angle));
		g2d.drawString(text, 0, 0);
		g2d.rotate(-Math.toRadians(angle));

		g2d.translate(-(float) x, -(float) y);
		g2d.setColor(old);

	}

	public Stroke getStroke() {
		return this.g2d.getStroke();
	}

	public void setColor(Color color) {
		this.g2d.setColor(color);//
	}

	public void fillOval(int x, int y, int width, int height) {
		this.g2d.fillOval(x, y, width, height);
	}

}
