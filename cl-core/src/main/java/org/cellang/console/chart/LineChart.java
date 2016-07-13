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

public class LineChart extends JPanel {
	private static final Logger LOG = LoggerFactory.getLogger(LineChart.class);
	private int padding = 25;
	private int leftLabelPadding = 150;
	private int bottomLabelPadding = 50;
	private Color lineColor = new Color(44, 102, 230, 180);
	private Color pointColor = new Color(100, 100, 100, 180);
	private Color gridColor = new Color(200, 200, 200, 200);
	private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
	private int pointWidth = 4;
	private int numberYDivisions = 10;
	private ChartModel model;
	private DecimalFormat format = new DecimalFormat("#,##0.00");

	public LineChart(ChartModel model) {
		this.model = model;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		double max = model.getMax().doubleValue();
		double min = model.getMin().doubleValue();
		double range = max - min;
		LOG.info("paintComponent");

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int size = model.getCount();

		double xScale = ((double) getWidth() - (2 * padding) - leftLabelPadding) / (size - 1);
		double yScale = ((double) getHeight() - 2 * padding - bottomLabelPadding)
				/ (model.getMax().doubleValue() - model.getMin().doubleValue());

		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int x1 = (int) (i * xScale + padding + leftLabelPadding);
			BigDecimal yValue = model.getYValue(i);
			if (yValue == null) {
				continue;
			}

			int y1 = (int) ((model.getMax().doubleValue() - yValue.doubleValue()) * yScale + padding);

			graphPoints.add(new Point(x1, y1));
		}

		// draw white background
		g2.setColor(Color.WHITE);
		g2.fillRect(padding + leftLabelPadding, padding, getWidth() - (2 * padding) - leftLabelPadding,
				getHeight() - 2 * padding - bottomLabelPadding);
		g2.setColor(Color.BLACK);

		// create hatch marks and grid lines for y axis.
		for (int i = 0; i < numberYDivisions + 1; i++) {
			int x0 = padding + leftLabelPadding;
			int x1 = pointWidth + padding + leftLabelPadding;
			int y0 = getHeight()
					- ((i * (getHeight() - padding * 2 - bottomLabelPadding)) / numberYDivisions + padding + bottomLabelPadding);
			int y1 = y0;
			if (size > 0) {
				g2.setColor(gridColor);
				g2.drawLine(padding + leftLabelPadding + 1 + pointWidth, y0, getWidth() - padding, y1);
				g2.setColor(Color.BLACK);
				double iD = (double) i;
				BigDecimal yLabelValue = new BigDecimal(min + range * (iD / numberYDivisions));

				String yLabel = format.format(yLabelValue);

				FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth(yLabel);
				g2.drawString(yLabel, x0 - labelWidth - 5, y0 + (metrics.getHeight() / 2) - 3);
			}
			g2.drawLine(x0, y0, x1, y1);
		}

		// and for x axis
		for (int i = 0; i < size; i++) {
			if (size > 1) {
				int x0 = i * (getWidth() - padding * 2 - leftLabelPadding) / (size - 1) + padding + leftLabelPadding;
				int x1 = x0;
				int y0 = getHeight() - padding - bottomLabelPadding;
				int y1 = y0 - pointWidth;
				if ((i % ((int) ((size / 20.0)) + 1)) == 0) {
					g2.setColor(gridColor);
					g2.drawLine(x0, getHeight() - padding - bottomLabelPadding - 1 - pointWidth, x1, padding);
					g2.setColor(Color.BLACK);
					String xLabel = model.getXValue(i);//
					if (xLabel == null) {
						xLabel = i + "";
					}
					FontMetrics metrics = g2.getFontMetrics();
					int labelWidth = metrics.stringWidth(xLabel);
					// X labels
					g2.drawString(xLabel, x0 - labelWidth / 2, y0 + metrics.getHeight() + 3);
				}
				g2.drawLine(x0, y0, x1, y1);
			}
		}

		// create x and y axes
		g2.drawLine(padding + leftLabelPadding, getHeight() - padding - bottomLabelPadding, padding + leftLabelPadding, padding);
		g2.drawLine(padding + leftLabelPadding, getHeight() - padding - bottomLabelPadding, getWidth() - padding,
				getHeight() - padding - bottomLabelPadding);

		Stroke oldStroke = g2.getStroke();
		g2.setColor(lineColor);
		g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);
		}

		g2.setStroke(oldStroke);
		g2.setColor(pointColor);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
	}

}
