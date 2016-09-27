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

import org.cellang.console.chart.ChartModel.DataChangeListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LineChart<T> extends JPanel implements DataChangeListener{

	private static final Logger LOG = LoggerFactory.getLogger(LineChart.class);

	public static class ZoomSetting {
		float zoomY = 1;

		float zoomX = 1;

		float x = 0;

		float y = 0;

		public ZoomSetting setZoomY(float zy) {
			this.zoomY = zy;
			return this;
		}

		public float getZoomY() {
			return this.zoomY;
		}
	}

	private int paddingLabel = 3;
	private int padding = 25;

	private int bottomLabelPadding = 50;

	private Color gridColor = new Color(200, 200, 200, 200);
	private ColorGenerator cg = new ColorGenerator();
	private static final Stroke GRAPH_STROKE = new BasicStroke(1f);

	private int pointWidth = 4;

	private int numberYDivisions = 10;

	private ChartModel<T> model;

	private int xLabelRotateAngle = 15;

	private DecimalFormat format = new DecimalFormat("#,##0.00");

	XLabelRenderer<T> xLabelRenderer = new DefaultXLabelRenderer<T>();

	private int maxXLabelForDrawing = 10;

	private ZoomSetting zoomSetting = new ZoomSetting();

	public LineChart(ChartModel<T> model) {
		this.model = model;
		this.model.addDataChangeListener(this);
	}

	public ZoomSetting getZoomSetting() {
		return zoomSetting;
	}

	public void setZoomSetting(ZoomSetting zoomSetting) {
		this.zoomSetting = zoomSetting;
		this.updateUI();
	}

	public void setXLabelRenderer(XLabelRenderer<T> xLR) {
		this.xLabelRenderer = xLR;
	}

	protected void paintSerial(ColorGenerator cg, Grahpics2DWrapper g2, int viewPointWidth, int viewPointHeight,
			ChartSerial<T> ser, int size, double yrange, double ymax) {
		Color color = cg.next();
		int paintWidth = viewPointWidth;
		int paintHeight = (int) (this.zoomSetting.zoomY * viewPointHeight);

		double xScale = (double) paintWidth / (size - 1);
		double ystep = (double) paintHeight / yrange;
		int yoffset = -paintHeight + viewPointHeight;
		List<Point> graphPoints = new ArrayList<>();
		for (int i = 0; i < size; i++) {
			int x1 = (int) (i * xScale);
			T xValue = this.model.getXValue(i);
			BigDecimal yValue = this.model.getYValue(ser.getName(), xValue);
			if (yValue == null) {
				continue;
			}

			int y1 = (int) ((ymax - yValue.doubleValue()) * ystep) + yoffset;

			graphPoints.add(new Point(x1, y1));
		}

		// draw line segment
		Stroke oldStroke = g2.getStroke();
		g2.setColor(color);
		// g2.setStroke(GRAPH_STROKE);
		for (int i = 0; i < graphPoints.size() - 1; i++) {
			int x1 = graphPoints.get(i).x;
			int y1 = graphPoints.get(i).y;
			int x2 = graphPoints.get(i + 1).x;
			int y2 = graphPoints.get(i + 1).y;
			g2.drawLine(x1, y1, x2, y2);
		}

		// g2.setStroke(oldStroke);
		// draw join point
		g2.setColor(color);
		for (int i = 0; i < graphPoints.size(); i++) {
			int x = graphPoints.get(i).x - pointWidth / 2;
			int y = graphPoints.get(i).y - pointWidth / 2;
			int ovalW = pointWidth;
			int ovalH = pointWidth;
			g2.fillOval(x, y, ovalW, ovalH);
		}
		// draw Serial Name
		if (graphPoints.size() > 0) {

			Point last = graphPoints.get(graphPoints.size() - 1);
			int x = viewPointWidth;
			int y = last.y;
			g2.drawString(ser.getName(), x, y, color);

		}
	}

	protected int getRightLabelWidth() {
		return 150;
	}

	protected int getLeftLabelPadding() {
		return 150;
	}

	protected int getPaddingTop() {
		return this.padding;
	}

	protected int getPaddingBottom() {
		return this.padding + this.bottomLabelPadding;
	}

	protected int getPaddingRight() {
		return this.padding + this.getRightLabelWidth();
	}

	protected int getPaddingLeft() {
		return this.padding + this.getLeftLabelPadding();
	}

	private void drawXAxis(Grahpics2DWrapper g2, int viewPointWidth, int viewPointHeight, int xsize) {
		double xScale = (double) viewPointWidth / (xsize - 1);
		int drawXLabelStep = xsize / this.maxXLabelForDrawing;

		if (drawXLabelStep == 0) {
			drawXLabelStep = 1;
		}

		for (int i = 0; i < xsize; i++) {
			int xI = (int) (xScale * i);
			int y0 = viewPointHeight + paddingLabel;// TODO

			int y1 = viewPointHeight - pointWidth;
			int y2 = 0;
			if (i > 0) {

				// short black
				g2.drawLine(xI, y0, xI, y1, Color.BLACK);
				// long grid
				g2.drawLine(xI, y1, xI, y2, gridColor);
			}
			T xValue = this.model.getXValue(i);
			String xLabel = this.xLabelRenderer.toDisplayValue(xValue);

			FontMetrics metrics = g2.get().getFontMetrics();
			int labelWidth = metrics.stringWidth(xLabel);
			// label X label
			if (i % drawXLabelStep == 0) {
				g2.drawString(xLabel, xI, y0 + (metrics.getHeight() / 2) - 3, Color.BLACK, this.xLabelRotateAngle);
			}
		}

		g2.drawLine(0, viewPointHeight, viewPointWidth, viewPointHeight, Color.black);
	}

	private static double adjustStep(double stepD) {
		BigDecimal stepBD = new BigDecimal(stepD);
		int pow = String.valueOf(stepBD.unscaledValue()).length() - stepBD.scale();
		if (pow > 0) {
			stepBD = new BigDecimal(10).pow(pow);//
		} else {
			stepBD = new BigDecimal("0.1").pow(-pow);//
		}

		return stepBD.doubleValue();
	}

	private void drawYAxis(Grahpics2DWrapper g2, int viewPointWidth, int viewPointHeight, double minY, double maxY) {
		double range = (maxY - minY) / this.zoomSetting.zoomY;
		double yRangeStep = range / (double) numberYDivisions;
		yRangeStep = adjustStep(yRangeStep);
		double yViewStep = viewPointHeight * (yRangeStep / range);

		// draw y axis and grid lines.
		double yValue = minY;
		double yViewValue = 0f;
		while (yValue < maxY) {

			BigDecimal yLabelValue = new BigDecimal(yValue);

			int x0 = 0;
			int yI = (int) (viewPointHeight - yViewValue);

			int x1 = pointWidth;
			int x2 = viewPointWidth;

			// short black
			g2.drawLine(x0, yI, x1, yI, Color.BLACK);
			// long grid
			g2.drawLine(x1, yI, x2, yI, gridColor);

			String yLabel = format.format(yLabelValue);

			FontMetrics metrics = g2.get().getFontMetrics();
			int labelWidth = metrics.stringWidth(yLabel);
			// label Y label
			g2.drawString(yLabel, x0 - labelWidth - 5, yI + (metrics.getHeight() / 2) - 3, Color.BLACK);

			yValue = yValue + yRangeStep;
			yViewValue = yViewValue + yViewStep;
		}

		// Y axis
		g2.drawLine(0, viewPointHeight, 0, 0, Color.BLACK);
	}

	@Override

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		double max = model.getDisplayYMax().doubleValue();
		double min = model.getDisplayYMin().doubleValue();

		double range = max - min;

		LOG.debug("paintComponent");

		int paddingTop = this.getPaddingTop();
		int paddingLeft = this.getPaddingLeft();

		Grahpics2DWrapper g2 = new Grahpics2DWrapper().set((Graphics2D) g);

		g2.get().setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.translate(paddingLeft, paddingTop);

		int size = model.getWindowSize();

		int viewPointWidth = getWidth() - this.getPaddingLeft() - this.getPaddingRight();
		int viewPointHeight = getHeight() - this.getPaddingTop() - this.getPaddingBottom();
		// draw white background
		g2.get().setColor(Color.WHITE);
		g2.fillRect(0, 0, viewPointWidth, viewPointHeight);
		this.drawYAxis(g2, viewPointWidth, viewPointHeight, min, max);
		// X axis
		this.drawXAxis(g2, viewPointWidth, viewPointHeight, size);

		// draw serials
		List<ChartSerial<T>> snameL = model.getSerialList();
		cg.reset();
		for (ChartSerial<T> ser : snameL) {
			this.paintSerial(cg, g2, viewPointWidth, viewPointHeight, ser, size, range, max);
		}
		g2.translate(-paddingLeft, -paddingTop);
	}

	@Override
	public void dataChanged() {
		this.updateUI();
	}

}
