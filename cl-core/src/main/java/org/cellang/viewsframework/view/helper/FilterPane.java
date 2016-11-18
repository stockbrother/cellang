package org.cellang.viewsframework.view.helper;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.cellang.viewsframework.control.Filterable;

public class FilterPane extends Box {

	Filterable filterable;

	public FilterPane(Filterable fil) {
		super(BoxLayout.Y_AXIS);
		this.filterable = fil;
		String[] cols = fil.getFilterableColumnList();

		for (String key : cols) {
			Box line = new Box(BoxLayout.X_AXIS);
			line.add(Box.createHorizontalGlue());
			this.add(line);
			JLabel label = new JLabel(key);
			label.setMinimumSize(new Dimension(50, 20));
			line.add(label);

			JTextField text = new JTextField();
			text.setPreferredSize(new Dimension(200, 20));
			text.setMaximumSize(new Dimension(300, 20));//
			line.add(text);
			text.getDocument().addDocumentListener(new DocumentListener() {

				@Override
				public void insertUpdate(DocumentEvent e) {
					String value = text.getText();
					if (value.trim().length() == 0) {
						filterable.unsetLike(key);
					} else {
						filterable.setLike(key, value);
					}
				}

				@Override
				public void removeUpdate(DocumentEvent e) {
					String value = text.getText();
					if (value.trim().length() == 0) {
						filterable.unsetLike(key);
					} else {
						filterable.setLike(key, value);
					}
				}

				@Override
				public void changedUpdate(DocumentEvent e) {
					

				}
			});
			
		}
		//this.add(Box.createVerticalGlue());//

	}

}
