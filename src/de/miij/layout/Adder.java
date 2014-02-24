package de.miij.layout;

import de.miij.ui.comp.flex.FlexComponent;
import de.miij.util.M;

import java.awt.*;

/**
 * Created by Mirko Hecky on 15.01.14.
 */
public class Adder {
	private FlexComponent[] components;
	private Container container;
	private int gap = 5;
	private int startX = 5;
	private int startTop = 5;
	private int width = 100;
	private int height = 25;

	public Adder(Container addTo, Component... c) {
		components = new FlexComponent[c.length];
		for(int i = 0; i < c.length; ++i)
			components[i] = new FlexComponent(c[i]).width(width).height(height);
		container = addTo;
//		if(container.getLayout() instanceof FlexLayout == false)
//			container.setLayout(new FlexLayout());
	}

	public void inOneRow() {
		inOneRow(gap);
	}

	public Adder at(int x, int y) {
		startX = x; startTop = y;
		return this;
	}

	public Adder at(int x) {
		startX = x;
		return this;
	}

	public Adder at(float y) {
		startTop = (int) y;
		return this;
	}

	public void inOneRow(int gap) {
		Component last = null;
		for(FlexComponent c : components) {
			if(last == null)
				container.add(c.FlexComponent, new FlexConstraint().left(startX).top(startTop).width(c.FlexWidth).height(c.FlexHeight));
			else
				container.add(c.FlexComponent, new FlexConstraint().left(last, M.RIGHT, gap).top(startTop).width(c.FlexWidth).height(c.FlexHeight));
			last = c.FlexComponent;
		}
	}

	public void inMultipleRows(int... componentCountPerRow) {
		inMultipleRows(gap, componentCountPerRow);
	}

	public void inMultipleRows(float verticalGap, int... componentCountPerRow) {
		inMultipleRows(gap, verticalGap, componentCountPerRow);
	}

	public void inMultipleRows(float horizontalGap, float verticalGap, int... componentCountPerRow) {
		Component last = null;
		Component lastRowComponent = null;
		int idx = 0;
		for(int row = 0; row < componentCountPerRow.length; ++row) {
			int addToXPos = 0;
			for(int col = 0; col < componentCountPerRow[row]; ++col) {
				FlexComponent c = components[idx];
				if(c.FlexComponent != null) {
					if(row == 0) {
						if(last == null)
							container.add(c.FlexComponent, new FlexConstraint().left(startX).top(startTop).width(c.FlexWidth).height(c.FlexHeight));
						else
							container.add(c.FlexComponent, new FlexConstraint().left(last, M.RIGHT, (int)horizontalGap + addToXPos).top(startTop).width(c.FlexWidth).height(c.FlexHeight));
					} else {
						if(last == null)
							container.add(c.FlexComponent, new FlexConstraint().left(startX).top(lastRowComponent, M.BOTTOM, (int)verticalGap).width(components[col].FlexWidth).height(c.FlexHeight));
						else
							container.add(c.FlexComponent, new FlexConstraint().left(last, M.RIGHT, (int)horizontalGap + addToXPos).top(lastRowComponent, M.BOTTOM, (int)verticalGap).width(components[col].FlexWidth).height(c.FlexHeight));
					}
					addToXPos = 0;
					if(col == componentCountPerRow[row] - 1) lastRowComponent = c.FlexComponent;
				} else {
					addToXPos += c.FlexWidth + (int) horizontalGap;
				}
				last = c.FlexComponent;
				idx++;
			}
			last = null;
		}
		int col = 0;
		int addToXPos = 0;
		for(; idx < components.length; ++idx) {
			FlexComponent c = components[idx];
			if(c.FlexComponent != null) {
				if(last == null)
					container.add(c.FlexComponent, new FlexConstraint().left(startX).top(lastRowComponent, M.BOTTOM, (int)verticalGap).width(components[col].FlexWidth).height(c.FlexHeight));
				else
					container.add(c.FlexComponent, new FlexConstraint().left(last, M.RIGHT, (int)horizontalGap + addToXPos).top(lastRowComponent, M.BOTTOM, (int)verticalGap).width(components[col].FlexWidth).height(c.FlexHeight));
				last = c.FlexComponent;
				addToXPos = 0;
			} else {
				addToXPos += components[col].FlexWidth + (int) horizontalGap;
			}
			col++;
		}
	}

	public Adder widths(int... widths) {
		int lastWidth = widths[0];
		for(int i = 0; i < components.length; ++i) {
			components[i].width(lastWidth);
			if(i < widths.length - 1)
				lastWidth = widths[i+1];
		}
		return this;
	}

	public Adder heights(int... heights) {
		int lastHeight = heights[0];
		for(int i = 0; i < components.length; ++i) {
			components[i].height(lastHeight);
			if(i < heights.length - 1)
				lastHeight = heights[i+1];
		}
		return this;
	}
}