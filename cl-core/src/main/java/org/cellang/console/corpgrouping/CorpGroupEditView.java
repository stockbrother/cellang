package org.cellang.console.corpgrouping;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.cellang.console.ops.OperationContext;
import org.cellang.console.view.AbstractView;
import org.cellang.core.entity.CorpGroupEntity;
import org.cellang.core.entity.EntityOp;
import org.cellang.core.entity.EntitySession;

public class CorpGroupEditView extends AbstractView {
	Box box;
	JTextField groupType;
	JTextField groupDate;
	OperationContext oc;

	public CorpGroupEditView(OperationContext oc) {
		super("CorpGroupEdit", oc);
		this.oc = oc;
		this.box = new Box(BoxLayout.Y_AXIS);
		this.box.setPreferredSize(new Dimension(100, 50));
		this.setViewportView(this.box);
		{

			Box line = Box.createHorizontalBox();
			JLabel label = new JLabel("Group Type");
			label.setMinimumSize(new Dimension(50, 20));
			line.add(label);
			groupType = new JTextField();
			groupType.setPreferredSize(new Dimension(200, 20));
			groupType.setMaximumSize(new Dimension(300, 20));//
			line.add(groupType);
			line.add(Box.createHorizontalGlue());
			box.add(line);
		}

		{

			Box line = Box.createHorizontalBox();
			JLabel label = new JLabel("Group Date");
			label.setMinimumSize(new Dimension(50, 20));
			line.add(label);
			groupDate = new JTextField();
			groupDate.setPreferredSize(new Dimension(200, 20));
			groupDate.setMaximumSize(new Dimension(300, 20));//
			line.add(groupDate);
			line.add(Box.createHorizontalGlue());
			box.add(line);
		}

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
