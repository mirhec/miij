package de.miij.ui.dialog;

import de.miij.layout.FlexConstraint;
import de.miij.ui.comp.DecoratedDialog;
import de.miij.util.ValSetter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static de.miij.util.LangString.L;

/**
 * Created by Mirko Hecky on 07.03.14.
 */
public class MessageDialog {
	private static final Color BACK = new Color(89, 89, 89);

	public MessageDialog() {}

	private static DecoratedDialog prepare(Window owner, String title) {
		final DecoratedDialog d = new DecoratedDialog(owner, false);
		d.setTitle(" " + title);
		d.setSize(370, 125);
		d.setLocationRelativeTo(null);
		d.setModal(true);
		d.setTitleBarBackground(BACK);
		d.setBackground(BACK);
		d.getContentPane().setBackground(BACK);
		d.setTitleForeground(Color.WHITE);
		setBackgroundColors(d.getLayeredPane(), BACK);

		return d;
	}

	public static void showOk(Window owner, String title, JComponent content) {
		showOk(owner, title, content, null);
	}

	public static void showOk(Window owner, String title, JComponent content, Dimension dim) {
		final DecoratedDialog d = prepare(owner, title);

		d.add(content, new FlexConstraint().left(0).top(0).right(0).bottom(30));

		final JButton btnOk = new JButton(L("Ok"));
		btnOk.setBackground(BACK);
		d.add(btnOk, new FlexConstraint().center_h(0).width(100).height(25).bottom(0));

		// Add actions
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.dispose();
			}
		};
		btnOk.addActionListener(action);
		if(dim != null)
			d.setSize(dim);
		d.setLocationRelativeTo(null);
		d.setVisible(true);
	}

	public static int showYesNo(Window owner, String title, String message) {
		final DecoratedDialog d = prepare(owner, title);

		JLabel msg = new JLabel(message);
		d.add(msg, new FlexConstraint().left(0).top(0).right(0).bottom(40));

		final JButton btnYes = new JButton(L("Yes"));
		JButton btnNo = new JButton(L("No"));
		btnYes.setBackground(BACK);
		btnNo.setBackground(BACK);
		d.add(btnYes, new FlexConstraint().center_h(-105).width(100).height(25).bottom(0));
		d.add(btnNo, new FlexConstraint().center_h(105).width(100).height(25).bottom(0));

		// Add actions
		final ValSetter val = new ValSetter();
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				val.I = e.getSource().equals(btnYes) ? 1 : 0;
				d.dispose();
			}
		};
		btnYes.addActionListener(action);
		btnNo.addActionListener(action);

		d.setVisible(true);
		return val.I;
	}

	public static void showOk(Window owner, String title, String message) {
		final DecoratedDialog d = prepare(owner, title);

		JLabel msg = new JLabel(message);
		d.add(msg, new FlexConstraint().left(0).top(0).right(0).bottom(40));

		final JButton btnOk = new JButton(L("Ok"));
		btnOk.setBackground(BACK);
		d.add(btnOk, new FlexConstraint().center_h(0).width(100).height(25).bottom(0));

		// Add actions
		ActionListener action = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				d.dispose();
			}
		};
		btnOk.addActionListener(action);
		d.setVisible(true);
	}

	private static void setBackgroundColors(Component c, Color col) {
		if(c instanceof Container)
			for(Component c2 : ((Container)c).getComponents())
				setBackgroundColors(c2, col);
		if(c instanceof JLabel)
			((JLabel)c).setOpaque(true);
		else if(c instanceof JPanel)
			((JPanel)c).setOpaque(true);
		else if(c instanceof JLayeredPane)
			((JLayeredPane)c).setOpaque(true);
		c.setBackground(col);
	}
}
