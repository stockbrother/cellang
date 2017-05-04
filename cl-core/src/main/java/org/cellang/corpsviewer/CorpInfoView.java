package org.cellang.corpsviewer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import org.cellang.core.entity.CorpInfoEntity;
import org.cellang.viewsframework.AbstractView;
import org.cellang.viewsframework.ops.OperationContext;

public class CorpInfoView extends AbstractView {

	Box box;
	Map<String, JLabel> valueLabelMap = new HashMap<>();

	public CorpInfoView(OperationContext oc) {
		super("CorpInfo", oc);

		this.box = new Box(BoxLayout.Y_AXIS);
		this.box.setPreferredSize(new Dimension(100, 50));
		this.setViewportView(this.box);

		this.addField("Id", "todo");
		this.addField("Name", "todo");

		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doSubmit();
			}
		});
		this.box.add(button);
		this.box.add(Box.createVerticalGlue());
	}

	public void refresh() {

	}

	private void addField(String key, Object defValue) {
		Box line = Box.createHorizontalBox();
		JLabel label = new JLabel(key);
		label.setMinimumSize(new Dimension(50, 20));
		line.add(label);
		JLabel valueL = new JLabel(String.valueOf(defValue));
		valueL.setPreferredSize(new Dimension(200, 20));
		valueL.setMaximumSize(new Dimension(300, 20));//
		line.add(valueL);
		line.add(Box.createHorizontalGlue());
		box.add(line);
		valueLabelMap.put(key, valueL);
	}

	private void doSubmit() {

	}

	public void update(String corpId) {
		CorpInfoEntity e = oc.getEntityService().getEntity(CorpInfoEntity.class, corpId);
		valueLabelMap.get("Id").setText(e.getId());
		valueLabelMap.get("Name").setText(e.getName());
	}

}
