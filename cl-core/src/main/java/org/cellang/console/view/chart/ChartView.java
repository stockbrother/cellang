package org.cellang.console.view.chart;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import org.cellang.console.chart.ChartModel;
import org.cellang.console.chart.LineChart;
import org.cellang.console.control.DataPageQuerable;
import org.cellang.console.control.Descriable;
import org.cellang.console.view.AbstractView;

public class ChartView<T> extends AbstractView {
	protected LineChart<T> chart;
	ChartModel<T> model;
	AbstractChartDataProvider<T> data;
	JPanel top;
	JPanel tools;

	public ChartView(String title, AbstractChartDataProvider<T> cd) {
		super(title);
		// cd.setView(this);//
		model = cd.getModel();
		this.data = cd;

		this.chart = new LineChart<T>(model);
		this.top = new JPanel(new BorderLayout());
		this.top.setBackground(Color.blue);//
		this.top.add(this.chart, BorderLayout.CENTER);
		this.tools = new JPanel();
		JButton//
		button = new JButton();
		button.setText("Clear");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearSerials();
			}
		});
		this.tools.add(button);

		button = new JButton();
		button.setText("ZoomDown");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				zoomDown();
			}
		});
		this.tools.add(button);
		
		button = new JButton();
		button.setText("ZoomReset");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				zoomReset();
			}
		});
		this.tools.add(button);
		
		this.top.add(this.tools, BorderLayout.NORTH);

		this.setViewportView(this.top);

		this.data.nextPage();
	}

	private void zoomDown() {
		LineChart.ZoomSetting zs = this.chart.getZoomSetting();
		float zy = zs.getZoomY();
		zy = zy * 2f;
		zs.setZoomY(zy);
		this.chart.setZoomSetting(zs);		
	}
	
	private void zoomReset() {
		LineChart.ZoomSetting zs = this.chart.getZoomSetting();		
		zs.setZoomY(1f);
		this.chart.setZoomSetting(zs);
	}

	private void clearSerials() {
		this.model.clearSerials();
		this.chart.updateUI();
	}

	@Override
	public <T> T getDelegate(Class<T> cls) {
		if (cls.equals(DataPageQuerable.class)) {
			return (T) this.data;
		} else if (cls.equals(Descriable.class)) {
			return (T) this.data;
		}
		return null;
	}

}
