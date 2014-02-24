package de.miij.ui.comp.iptextfield;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Mirko Hecky on 29.01.14.
 */
public class GridBagPanel extends JPanel {

	private static final long serialVersionUID = 1L;


	public static final Insets NO_INSETS = new Insets(0, 0, 0, 0);

	public GridBagConstraints constraints;
	private GridBagLayout layout;


	public GridBagPanel() {
		layout = new GridBagLayout();
		constraints = new GridBagConstraints();
		constraints.fill = GridBagConstraints.NONE;
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = NO_INSETS;
		setLayout(layout);
	}

	public void setHorizontalFill() {
		constraints.fill = GridBagConstraints.HORIZONTAL;
	}

	public void setNoneFill() {
		constraints.fill = GridBagConstraints.NONE;
	}





	public void add(Component component, int x, int y, int width, int
			height, int weightX, int weightY) {
		GridBagLayout gbl = (GridBagLayout) getLayout();

		gbl.setConstraints(component, constraints);

		add(component);
	}


	public void add(Component component, int x, int y, int width, int height) {
		add(component, x, y, width, height, 0, 0);
	}

	public void setBothFill() {
		constraints.fill = GridBagConstraints.BOTH;
	}

	public void setInsets(Insets insets) {
		constraints.insets = insets;

	}
}