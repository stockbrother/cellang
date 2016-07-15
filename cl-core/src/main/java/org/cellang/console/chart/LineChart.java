package org.cellang.console.chart;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineChart<T> extends JPanel {
	private static final Logger LOG = LoggerFactory.getLogger(LineChart.class);
	private int padding = 25;

	private int leftLabelPadding = 150;

	private int bottomLabelPadding = 50;

	private Color gridColor = new Color(200, 200, 200, 200);
	private ColorGenerator cg = new ColorGenerator();
	private static final Stroke GRAPH_STROKE = new BasicStroke(1f);

	private int pointWidth = 4;

	private int numberYDivisions = 10;

	private ChartModel<T> model;

	private int xLabelRotate = 45;

	private DecimalFormat format = new DecimalFormat("#,##0.00");

	public LineChart(ChartModel<T> model) {
		this.model = model;
	}

	protected void drawString(Graphics2D g2, String string, int x, int y, Color color) {
		drawRotate(g2, string, x, y, 0, color);
	}

	protected void drawLine(Graphics2D g2, int x1, int y1, int x2, int y2, Color color) {
		g2.setColor(color);
		drawLine(g2, x1, y1, x2, y2);
	}

	protected void drawLine(Graphics2D g2, int x1, int y1, int x2, int y2) {
		g2.drawLine(x1, y1, x2, y2);
	}

	public void drawRotate(Graphics2D g2, String text, double x, double y, int angle, Color color) {
		g2.setColor(color);
		g2.translate((float) x, (float) y);
		g2.rotate(Math.toRadians(angle));
		g2.drawString(text, 0, 0);
		g2.rotate(-Math.toRadians(angle));
		g2.translate(-(float) x, -(float) y);
	}

	protected void paintSerial(ColorGenerator cg, Graphics2D g2, String sname, int size, double yrange, double ymax) {
		Color color = cg.next();

		double xScale = ((double) getWidth() - (2 * padding) - leftLabelPadding) / (size - 1);
		double yScale = ((double) getHeight() - 2 * padding - bottomLabelPadding) / yrange;

		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int x1 = (int) (i * xScale + padding + leftLabelPadding);
			T xValue = this.model.getXValue(i);
			BigDecimal yValue = this.model.getYValue(sname, xValue);
			if (yValue == null) {
				continue;
			}

			int y1 = (int) ((ymax - yValue.doubleValue()) * yScale + padding);

			graphPoints.add(new Point(x1, y1));
		}

		// draw line segment
		Stroke oldStroke = g2.getStroke();
		g2.setColor(color);
		//g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			drawLine(g2, x1, y1, x2, y2);
		}

		//g2.setStroke(oldStroke);
		// draw join point
		g2.setColor(color);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		double max = model.getDisplayMax().doubleValue();
		double min = model.getDisplayMin().doubleValue();
		double range = max - min;

		LOG.debug("paintComponent");

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int size = model.getWindowSize();

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + leftLabelPadding, padding, getWidth() - (2 * padding) - leftLabelPadding,
				getHeight() - 2 * padding - bottomLabelPadding);
		g2.setColor(Color.BLACK);

		// draw y axis and grid lines.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + leftLabelPadding;
			int x1 = pointWidth + padding + leftLabelPadding;
			int y0 = getHeight() - ((i * (getHeight() - padding * 2 - bottomLabelPadding)) / numberYDivisions + padding
					+ bottomLabelPadding);
			int y1 = y0;
			if (size > 0) {
				drawLine(g2, padding + leftLabelPadding + 1 + pointWidth, y0, getWidth() - padding, y1, gridColor);

				double iD = (double) i;
				BigDecimal yLabelValue = new BigDecimal(min + range * (iD / numberYDivisions));

				String yLabel = format.format(yLabelValue);

				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				// label Y label
				drawString(g2, yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3, Color.BLACK);
			}
			drawLine(g2, x0, y0, x1, y1);
		}

		// draw x axis label and grid lines.
		for (int i = 0; i < size; i++) {
			if (size > 1) {
				int x0 = i * (getWidth() - padding * 2 - leftLabelPadding) / (size - 1) + padding + leftLabelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - bottomLabelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((size / 20.0)) + 1)) == 0) {
					// skip some values.

					drawLine(g2, x0, getHeight() - padding - bottomLabelPadding - 1 - pointWidth, x1, padding,
							gridColor);
					T xValue = model.getXValue(i);
					String xLabel = model.getXDisplayValue(xValue);//
					if (xLabel == null) {
						xLabel = i + "";
					}
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					// X labels
					drawRotate(g2, xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3, xLabelRotate,
							Color.BLACK);
				}
				drawLine(g2, x0, y0, x1, y1);
			}
		}

		// draw x and y axe line
		drawLine(g2, padding + leftLabelPadding, getHeight() - padding - bottomLabelPadding, padding + leftLabelPadding,
				padding);
		drawLine(g2, padding + leftLabelPadding, getHeight() - padding - bottomLabelPadding, getWidth() - padding,
				getHeight() - padding - bottomLabelPadding);
		// draw serials
		List<String> snameL = model.getSerialNameList();
		cg.reset();
		for (String sname : snameL) {
			this.paintSerial(cg, g2, sname, size, range, max);
		}
	}

}
