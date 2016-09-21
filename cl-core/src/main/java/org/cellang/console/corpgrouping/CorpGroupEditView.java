package org.cellang.console.corpgrouping;

import java.awt.Dimension;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import org.cellang.collector.EnvUtil;
import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.AbstractView;
import org.cellang.core.entity.CorpGroupEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;

public class CorpGroupEditView extends AbstractView {
	Box panel;
	TextArea groupType;
	TextArea groupDate;
	OperationContext oc;

	public CorpGroupEditView(OperationContext oc) {
		super("CorpGroupEdit");
		this.oc = oc;
		this.panel = new Box(BoxLayout.Y_AXIS);
		this.panel.setPreferredSize(new Dimension(100, 50));
		groupType = new TextArea();
		groupDate = new TextArea();
		this.panel.add(groupType);
		this.panel.add(groupDate);
		JButton button = new JButton("Submit");
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				doSubmit();
			}
		});
		this.panel.add(button);
		this.panel.add(Box.createVerticalGlue());

		this.setViewportView(this.panel);

	}

	protected void doSubmit() {
		String type = this.groupType.getText();
		if (type.trim().length() == 0) {
			throw new RuntimeException("type is empty.");
		}
		String id = "2015." + type;
		CorpGroupEntity e = new CorpGroupEntity();
		e.setId(id);
		e.setGroupDate("20151231");// EnvUtil.newDateOfYearLastDay(2015));
		e.setGroupId(id);
		e.setGroupType(type);

		this.oc.getEntityService().execute(new EntityOp<Object>() {

			@Override
			public Object execute(EntitySession es) {
				es.save(e);
				return null;
			}
		});
	}

}
