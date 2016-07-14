package org.cellang;

import java.awt.Dimension;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.cellang.console.chart.LineChart;
import org.cellang.console.view.CompoundGrowModel;

public class Tmp5 {
	private static void createAndShowGui() {

		LineChart mainPanel = new LineChart(new CompoundGrowModel(BigDecimal.ONE, new BigDecimal(0.1), 50));
		mainPanel.setPreferredSize(new Dimension(800, 600));
		JFrame frame = new JFrame("DrawGraph");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(mainPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGui();
			}
		});
	}
}