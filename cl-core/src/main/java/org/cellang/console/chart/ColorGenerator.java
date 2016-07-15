package org.cellang.console.chart;

import java.awt.Color;

public class ColorGenerator {

	private Color[] colorArray = new Color[] { Color.BLUE, Color.RED, Color.GREEN, Color.CYAN, Color.gray, };

	private int nextIndex;

	public Color next() {

		int i = nextIndex % colorArray.length;
		int round = nextIndex / colorArray.length;
		Color rt = colorArray[i];
		nextIndex++;
		return rt;
	}

	public void reset() {
		nextIndex = 0;
	}
}
