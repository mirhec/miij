/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import de.miij.Miij;
import de.miij.layout.FlexConstraint;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.Field;
import java.util.ArrayList;
import javax.swing.ComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListDataListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;

/**
 *
 * @author Mirko Hecky
 */
public class MColorBox extends MComboBox
{
	public JPanel colorPanel = new JPanel();
	private ArrayList<Color> colors = new ArrayList<Color>();
	private int selectedColorIndex = 0;
	private int cols = 3;
	/**
	 * Called, if the user changes the selected color. The colorChanged.event.getSource()-
	 * Method returns the selected color object.
	 */
	public Connector colorChanged = null;

	public MColorBox()
	{
		super();
		init();
	}
	
	public MColorBox(int columns)
	{
		super();
		cols = columns;
		init();
	}

	private void addPopupMouseListener(MouseListener m)
	{
		try
		{
			Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
			popupInBasicComboBoxUI.setAccessible(true);
			BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(getUI());

			Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
			scrollerInBasicComboPopup.setAccessible(true);
			JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

			scroller.getViewport().getView().addMouseListener(m);
		}
		catch (NoSuchFieldException e)
		{
			Miij.handleSilent(e);
		} catch (IllegalAccessException e)
		{
			Miij.handleSilent(e);
		}
	}
	
	private void addPopupMouseMotionListener(MouseMotionListener m)
	{
		try
		{
			Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
			popupInBasicComboBoxUI.setAccessible(true);
			BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(getUI());

			Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
			scrollerInBasicComboPopup.setAccessible(true);
			JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

			scroller.getViewport().getView().addMouseMotionListener(m);
		}
		catch (NoSuchFieldException e)
		{
			Miij.handleSilent(e);
		} catch (IllegalAccessException e)
		{
			Miij.handleSilent(e);
		}
	}

	@Override
	public void addItem(Object c)
	{
		if(c instanceof Color)
			colors.add((Color) c);
		updateColorPanel();
	}

	private void updateColorPanel()
	{
		colorPanel.removeAll();
		colorPanel.setLayout(new GridLayout(0, cols, 0, 0));
		for (Color c : colors)
		{
			colorPanel.add(getLabel(c));
		}
	}

	private JLabel getLabel(final Color c)
	{
		JLabel lbl = new JLabel();
		lbl.setOpaque(true);
		lbl.setBackground(c);
		lbl.setPreferredSize(new Dimension(35, 35));
		return lbl;
	}

	private void init()
	{
		setRenderer(new MColorBoxRenderer());
		setModel(new MColorBoxModel());

		addPopupMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				int idxX = e.getPoint().x / (getWidth() / cols);
				int idxY = e.getPoint().y / 35;
				int idx = idxY * cols + idxX;
				if(idx >= 0 && idx < colors.size())
				{
					selectedColorIndex = idx;
					if(colorChanged != null)
					{
						e.setSource(colors.get(idx));
						colorChanged.action(e);
					}
				}
			}
		});
		addPopupMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e)
			{
				int idxX = e.getPoint().x / (getWidth() / cols);
				int idxY = e.getPoint().y / 35;
				int idx = idxY * cols + idxX;
				if(idx >= 0 && idx < colors.size())
				{
					updateColorPanel();
					Component c = colorPanel.getComponent(idx);
					if(c instanceof JLabel)
					{
						for(Component cc : colorPanel.getComponents())
							if(cc instanceof JLabel)
								((JLabel)cc).setBorder(null);
						
						JLabel lbl = ((JLabel)c);
						Color col = lbl.getBackground();
						int gray = 255 - (col.getRed() + col.getGreen() + col.getBlue()) / 3;
						gray = 255 - gray > gray ? 0 : 255;
						Color inverted = new Color(gray, gray, gray);
						lbl.setBorder(new LineBorder(inverted, 2));
						
						try
						{
							Field popupInBasicComboBoxUI = BasicComboBoxUI.class.getDeclaredField("popup");
							popupInBasicComboBoxUI.setAccessible(true);
							BasicComboPopup popup = (BasicComboPopup) popupInBasicComboBoxUI.get(getUI());

							Field scrollerInBasicComboPopup = BasicComboPopup.class.getDeclaredField("scroller");
							scrollerInBasicComboPopup.setAccessible(true);
							JScrollPane scroller = (JScrollPane) scrollerInBasicComboPopup.get(popup);

							scroller.getViewport().getView().repaint();
						}
						catch (Exception ex)
						{
							Miij.handleSilent(ex);
						}
					}
				}
			}
		});
	}
	
	public Color getSelectedColor()
	{
		return selectedColorIndex > 0 && selectedColorIndex < colors.size() ? colors.get(selectedColorIndex) : null;
	}
	
	@Override
	public int getSelectedIndex()
	{
		return selectedColorIndex > 0 && selectedColorIndex < colors.size() ? selectedColorIndex : -1;
	}
	
	public void setSelectedColorIndex(int idx)
	{
		selectedColorIndex = idx;
	}

	class MColorBoxModel implements ComboBoxModel
	{

		@Override
		public void setSelectedItem(Object o)
		{
			if (o instanceof Color)
			{
//				selectedColorIndex = colors.indexOf((Color) o);
			}
		}

		@Override
		public Object getSelectedItem()
		{
			if (selectedColorIndex >= 0 && colors.size() > selectedColorIndex)
			{
				JLabel lbl = new JLabel();
				lbl.setOpaque(true);
				lbl.setBackground(colors.get(selectedColorIndex));
				lbl.setPreferredSize(new Dimension(35, 35));
				return lbl;
			}
			return null;
		}

		@Override
		public int getSize()
		{
			return 1;
		}

		@Override
		public Object getElementAt(int i)
		{
			return i >= 0 && i < colors.size() ? colors.get(i) : null;
		}

		@Override
		public void addListDataListener(ListDataListener ll)
		{
		}

		@Override
		public void removeListDataListener(ListDataListener ll)
		{
		}
	}

	class MColorBoxRenderer implements ListCellRenderer
	{

		@Override
		public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
		{
			if (value instanceof Color)
			{
				return colorPanel;
			}
			else
			{
				if (colors.size() > selectedColorIndex && selectedColorIndex >= 0)
				{
					JLabel lbl = new JLabel();
					BufferedImage img = new BufferedImage(40, 32, BufferedImage.TYPE_INT_RGB);
					Graphics2D g = img.createGraphics();
					g.setColor(colors.get(selectedColorIndex));
					g.fillRect(0, 0, 40, 32);
					g.dispose();
					lbl.setIcon(new ImageIcon(img));
					return lbl;
				}
			}
			return null;
		}
	}

	public static void main(String[] args)
	{
		MFrame f = new MFrame("MColorBox Test");
		MColorBox b = new MColorBox();
		b.addItem(Color.RED);
		b.addItem(Color.GREEN);
		b.addItem(Color.BLUE);
		b.addItem(Color.YELLOW);
		b.addItem(Color.BLACK);
		b.addItem(Color.WHITE);
		f.getContentPane().add(b, new FlexConstraint().left(10).top(10).right(10).height(40));
		f.setSize(200, 100);
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}
}
