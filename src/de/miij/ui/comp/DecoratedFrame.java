/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import de.miij.Miij;
import de.miij.layout.FlexConstraint;
import de.miij.layout.FlexLayout;
import de.miij.ui.comp.flex.FlexRecalculateListener;
import de.miij.util.M;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * @author Mirko Hecky
 */
public class DecoratedFrame extends MFrame {
	protected static final int W = 4;
	protected static final int TITLE_BAR_HEIGHT = 25;
	protected static final int MENU_BAR_HEIGHT = 25;
	protected static int TOOL_BAR_HEIGHT = 30;
	protected JLabel lblTitle;
	protected MPanel contentPanel = new MPanel();
	protected JLayeredPane resizePanel = new JLayeredPane();
	protected MPanel toolbarPanel = new MPanel();
	protected MPanel titlePanel = new MPanel();
	protected MPanel northPanel = new MPanel();
	protected JToolBar toolbar;
	private JLabel left, right, top, bottom, topleft, topright, bottomleft, bottomright;
	private ArrayList<JButton> toolbarButtons = new ArrayList<JButton>();
	private ArrayList<JLabel> titleLabels = new ArrayList<JLabel>();
	private boolean helpVisible = true;
	private int labelSpacing = 10;
	private JLabel lblIcon = new JLabel();
	private Color borderColor = M.DECORATED_BORDER_COLOR;
	private Color background = new Color(89, 89, 89);
	private Color titleBarBackground = new Color(59, 59, 59);
	private boolean canDoubleClickTitleBar = true;

	public DecoratedFrame() {
		initDecoratedFrame();
	}

	public DecoratedFrame(boolean helpVisible) {
		this.helpVisible = helpVisible;
		initDecoratedFrame();
	}

	public boolean canDoubleClickTitleBar() {
		return canDoubleClickTitleBar;
	}

	public void setCanDoubleClickTitleBar(boolean allow) {
		canDoubleClickTitleBar = allow;
	}

	public void setBorderColor(Color c) {
		resizePanel.setBorder(new LineBorder(c));
		northPanel.setBorder(new MatteBorder(1, 1, 0, 1, c));
		borderColor = c;
	}

	public void addToolbarButton(Icon i, final Connector c) {
		addToolbarButton(toolbarButtons.size(), i, c);
	}

	public void addToolbarButton(Icon i, Icon iHover, final Connector c) {
		addToolbarButton(toolbarButtons.size(), i, iHover, c);
	}

	public void addToolbarButton(int index, Icon i, final Connector c) {
		addToolbarButton(index, i, i, c);
	}

	public void addToolbarButton(int index, Icon i, Icon iHover, final Connector c) {
		addToolbarButton(index, i, iHover, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (c != null)
					c.action(e);
			}
		});
	}

	public void addToolbarButton(Icon i, ActionListener l) {
		addToolbarButton(toolbarButtons.size(), i, l);
	}

	public void addToolbarButton(Icon i, Icon iHover, ActionListener l) {
		addToolbarButton(toolbarButtons.size(), i, iHover, l);
	}

	public void addToolbarButton(int index, Icon i, ActionListener l) {
		addToolbarButton(index, i, i, l);
	}

	public void addToolbarButton(int index, Icon i, Icon iHover, ActionListener l) {
		if (index < 0) return;

		JButton btn = makeButton(i, iHover, false, false, false);
		btn.addActionListener(l);
		addToolbarButton(index, btn);
	}

	public void addToolbarButton(JButton btn) {
		addToolbarButton(toolbarButtons.size(), btn);
	}

	public void addToolbarButton(int index, JButton btn) {
		while (index > toolbarButtons.size())
			toolbarButtons.add(null);

		toolbarButtons.add(index, btn);
		updateToolbarButtons();
	}

	public void updateToolbarButtons() {
		toolbarPanel.removeAll();
		int index = 0;
		for(JButton btn : toolbarButtons)
			toolbarPanel.add(btn, new FlexConstraint().right(TITLE_BAR_HEIGHT * index++).top(0).bottom(0).width(TITLE_BAR_HEIGHT));
	}

	public JButton getToolbarButton(int index) {
		if (toolbarButtons.size() > index && index >= 0)
			return toolbarButtons.get(index);
		return null;
	}

	////////////////////////////////////////////////////////////////////////////

	public void addTitleLabel(String text) {
		addTitleLabel(titleLabels.size(), text);
	}

	public void addTitleLabel(int index, String text) {
		addTitleLabel(index, text, null);
	}

	public void addTitleLabel(int index, String text, Color foreground) {
		JLabel lbl = new JLabel(text);
		lbl.setForeground(foreground);
		lbl.setFont(new Font("Tahoma", Font.BOLD, 13));
		addTitleLabel(index, lbl);
	}

	public void addTitleLabel(JLabel label) {
		addTitleLabel(titleLabels.size(), label);
	}

	public void addTitleLabel(final int index, final JLabel label) {
		if (index < 0) return;

		while (index > titleLabels.size())
			titleLabels.add(null);

		titleLabels.add(index, label);
		titlePanel.add(label, new FlexConstraint().left(new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				int left = W + lblTitle.getWidth() + labelSpacing + lblIcon.getWidth();
				for (int i = 0; i < index; ++i) left += titleLabels.get(i).getWidth() + labelSpacing;
				return left;
			}
		}).top(0).bottom(0).width(new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				return label.getFontMetrics(label.getFont()).stringWidth(label.getText()) + label.getIconTextGap() + (label.getIcon() != null ? label.getIcon().getIconWidth() : 0);
			}
		}));
	}

	public JLabel getTitleLabel(int index) {
		if (index < 0 || index >= titleLabels.size()) return null;
		else return titleLabels.get(index);
	}

	////////////////////////////////////////////////////////////////////////////

	public int getLabelSpacing() {
		return labelSpacing;
	}

	public void setLabelSpacing(int spacing) {
		labelSpacing = spacing;
	}

	////////////////////////////////////////////////////////////////////////////

	public void setTitleBarBackground(Color c) {
		titleBarBackground = c;
		northPanel.setBackground(c);
		if (getJMenuBar() != null) {
			getJMenuBar().setOpaque(false);
			getJMenuBar().setBackground(c);
			for (int i = 0; i < getJMenuBar().getMenuCount(); ++i) {
				JMenu m = getJMenuBar().getMenu(i);
				setJMenuBackground(m, c);
			}
		}

		if (toolbar != null)
			toolbar.setOpaque(false);
	}

	public void setJMenuBackground(JMenu m, Color c) {
		if (m == null) return;

		for (int ii = 0; ii < m.getItemCount(); ++ii) {
			Component mi = m.getMenuComponent(ii);
			if (mi == null) continue;

			mi.setBackground(c);

			if(mi instanceof JMenu)
				setJMenuBackground((JMenu) mi, c);
			if(mi instanceof JMenuItem) {   // For JMenu and JMenuItem, cause JMenu inherits from JMenuItem
				((JMenuItem)mi).setOpaque(true);
				((JMenuItem)mi).setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			}
		}
		m.setOpaque(true);
		m.setBackground(c);
		m.getPopupMenu().setBorder(new LineBorder(borderColor, 2));
	}

	public void setJPopupMenuBackground(JPopupMenu m, Color c) {
		if (m == null) return;

		for (int ii = 0; ii < m.getSubElements().length; ++ii) {
			MenuElement mi = m.getSubElements()[ii];
			if (mi == null) continue;

			if(mi instanceof JMenu)
				setJMenuBackground((JMenu) mi, c);
			if(mi instanceof JMenuItem) {   // For JMenu and JMenuItem, cause JMenu inherits from JMenuItem
				((JMenuItem)mi).setBackground(c);
				((JMenuItem)mi).setOpaque(true);
				((JMenuItem)mi).setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
			}
		}
		m.setOpaque(true);
		m.setBackground(c);
		m.setBorder(new LineBorder(borderColor, 2));
	}

	@Override
	public void setBackground(Color back) {
		super.setBackground(back);
		background = back;
		getContentPane().setBackground(back);
	}

	////////////////////////////////////////////////////////////////////////////

	public JToolBar getJToolBar() {
		return this.toolbar;
	}

	public void setJToolBar(JToolBar toolbar) {
		this.toolbar = toolbar;

		if (toolbar != null) {
			TOOL_BAR_HEIGHT = toolbar.getHeight();
			northPanel.add(toolbar, new FlexConstraint().left(W).right(W).top(W + TITLE_BAR_HEIGHT + (this.getJMenuBar() != null ? MENU_BAR_HEIGHT : 0)).height(TOOL_BAR_HEIGHT));
		}
	}

	////////////////////////////////////////////////////////////////////////////

	protected JButton makeButton(final Icon i, final Icon iHover, final boolean close, final boolean minimize, final boolean maximize) {
		final JButton button = new JButton(i);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.setBorder(null);
		button.setBorderPainted(false);
		button.setIcon(i);
		button.setRolloverIcon(iHover);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (close)
					dispatchEvent(new WindowEvent(DecoratedFrame.this, WindowEvent.WINDOW_CLOSING));
				if (minimize) {
					DecoratedFrame.this.setState(JFrame.ICONIFIED);
					dispatchEvent(new WindowEvent(DecoratedFrame.this, WindowEvent.WINDOW_STATE_CHANGED));
					revalidate();
				}
				if (maximize) {
					DecoratedFrame.this.setExtendedState(DecoratedFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
					dispatchEvent(new WindowEvent(DecoratedFrame.this, WindowEvent.WINDOW_STATE_CHANGED));
					revalidate();
				}
			}
		});
		return button;
	}

	private JButton makeCloseButton() {
		final Icon i1 = WindowIcons.getCloseIcon(false);
		final Icon i2 = WindowIcons.getCloseIcon(true);
		return makeButton(i1, i2, true, false, false);
	}

	private JButton makeMaximizeButton() {
		final Icon i1 = WindowIcons.getMaximizeIcon(false);
		final Icon i2 = WindowIcons.getMaximizeIcon(true);
		return makeButton(i1, i2, false, false, true);
	}

	private JButton makeMinimizeButton() {
		final Icon i1 = WindowIcons.getMinimizeIcon(false);
		final Icon i2 = WindowIcons.getMinimizeIcon(true);
		return makeButton(i1, i2, false, true, false);
	}

	private void initDecoratedFrame() {
		setUndecorated(true);

		// Toolbar
		JButton btnClose = makeCloseButton();
		JButton btnMaximize = makeMaximizeButton();
		JButton btnMinimize = makeMinimizeButton();

//		toolbarPanel.add(btnClose, new FlexConstraint().right(0).top(0).width(TITLE_BAR_HEIGHT).height(TITLE_BAR_HEIGHT));
//		toolbarPanel.add(btnMaximize, new FlexConstraint().right(TITLE_BAR_HEIGHT).top(0).width(TITLE_BAR_HEIGHT).height(TITLE_BAR_HEIGHT));
//		toolbarPanel.add(btnMinimize, new FlexConstraint().right(TITLE_BAR_HEIGHT * 2).top(0).width(TITLE_BAR_HEIGHT).height(TITLE_BAR_HEIGHT));
		addToolbarButton(btnClose);
		addToolbarButton(btnMaximize);
		addToolbarButton(btnMinimize);
		if (helpVisible)
			addToolbarButton(WindowIcons.getQuestionIcon(false), WindowIcons.getQuestionIcon(true), new Connector(this, "help"));

		// Resize elements
		DecoratedFrame.ResizeWindowListener rwl = new DecoratedFrame.ResizeWindowListener(this);
		for (JLabel l : java.util.Arrays.asList(
				left = new JLabel(), right = new JLabel(),
				top = new JLabel(), bottom = new JLabel(),
				topleft = new JLabel(), topright = new JLabel(),
				bottomleft = new JLabel(), bottomright = new JLabel())) {
			l.addMouseListener(rwl);
			l.addMouseMotionListener(rwl);
		}

		Dimension d = new Dimension(W, 0);
		left.setPreferredSize(d);
		left.setMinimumSize(d);
		right.setPreferredSize(d);
		right.setMinimumSize(d);

		d = new Dimension(0, W);
		top.setPreferredSize(d);
		top.setMinimumSize(d);
		bottom.setPreferredSize(d);
		bottom.setMinimumSize(d);

		d = new Dimension(W, W);
		topleft.setPreferredSize(d);
		topleft.setMinimumSize(d);
		topright.setPreferredSize(d);
		topright.setMinimumSize(d);
		bottomleft.setPreferredSize(d);
		bottomleft.setMinimumSize(d);
		bottomright.setPreferredSize(d);
		bottomright.setMinimumSize(d);

		setResizeCurser(false);

		// Title
		DecoratedFrame.DragWindowListener dwl = new DecoratedFrame.DragWindowListener();
		titlePanel.addMouseListener(dwl);
		titlePanel.addMouseMotionListener(dwl);
		titlePanel.setOpaque(false);
		titlePanel.setBorder(BorderFactory.createEmptyBorder(0, W, W, 0));

		lblIcon.addMouseListener(dwl);
		lblIcon.addMouseMotionListener(dwl);

		lblTitle = new JLabel(Miij.getAppName(), JLabel.LEFT);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTitle.setVerticalTextPosition(JLabel.TOP);
		titlePanel.add(lblTitle, new FlexConstraint().left(new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				return W + lblIcon.getWidth();
			}
		}).bottom(0).top(0).width(new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				if (lblTitle.getText() == null) return 0;
				return lblTitle.getFontMetrics(lblTitle.getFont()).stringWidth(lblTitle.getText()) + lblTitle.getIconTextGap() + (lblTitle.getIcon() != null ? lblTitle.getIcon().getIconWidth() : 0);
			}
		}));
//		titlePanel.add(top, new FlexConstraint().left(0).top(0).right(toolbarPanel, M.LEFT, 0).height(W));

		// North Panel
//		JPanel northPanel = new JPanel(new FlexLayout());
		northPanel.add(top, new FlexConstraint().left(W).top(0).right(W).height(W));
		northPanel.add(topleft, new FlexConstraint().left(0).top(0).height(W).width(W));
		northPanel.add(titlePanel, new FlexConstraint().left(W).top(0).height(TITLE_BAR_HEIGHT).right(toolbarPanel, M.LEFT, 0));
		northPanel.add(topright, new FlexConstraint().right(0).top(0).height(W).width(W));
		northPanel.add(toolbarPanel, new FlexConstraint().right(W).top(0).height(TITLE_BAR_HEIGHT).width(new FlexRecalculateListener() {

			@Override
			public int recalculate() {
				return TITLE_BAR_HEIGHT * 3 + TITLE_BAR_HEIGHT * toolbarButtons.size();
			}
		}));
		northPanel.setBorder(new MatteBorder(1, 1, 0, 1, borderColor));

		// South Panel
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.add(bottomleft, BorderLayout.WEST);
		southPanel.add(bottom, BorderLayout.CENTER);
		southPanel.add(bottomright, BorderLayout.EAST);

		// Resize Panel (all together)
		FlexRecalculateListener topRecalculateListener = new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				return (getJMenuBar() == null ? TITLE_BAR_HEIGHT : TITLE_BAR_HEIGHT + MENU_BAR_HEIGHT) +
						(toolbar == null ? 0 : TOOL_BAR_HEIGHT);
			}
		};
		resizePanel.setLayout(new FlexLayout());
		resizePanel.add(left, new FlexConstraint().left(0).top(W).bottom(W).width(W));
		resizePanel.add(right, new FlexConstraint().right(0).top(W).bottom(W).width(W));
		resizePanel.add(northPanel, new FlexConstraint().left(0).top(0).right(0).height(topRecalculateListener));
		resizePanel.add(southPanel, new FlexConstraint().left(0).right(0).bottom(0).height(W));
		resizePanel.add(contentPanel, new FlexConstraint().left(W).top(topRecalculateListener).bottom(W).right(W));
		resizePanel.setBorder(new LineBorder(borderColor));

		northPanel.setOpaque(true);
		titlePanel.setOpaque(false);
		lblTitle.setOpaque(false);
		toolbarPanel.setOpaque(false);
		southPanel.setOpaque(false);
		resizePanel.setOpaque(false);

		northPanel.add(lblIcon, new FlexConstraint().left(0).height(new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				if (lblIcon.getIcon() == null) return 0;
				return lblIcon.getIcon().getIconHeight();
			}
		}).top(0).width(new FlexRecalculateListener() {
			@Override
			public int recalculate() {
				if (lblIcon.getIcon() == null) return 0;
				return lblIcon.getIcon().getIconWidth();
			}
		}));

		setLayeredPane(resizePanel);
		setTitleBarBackground(titleBarBackground);
		setBackground(background);
		setBorderColor(borderColor);
	}

	@Override
	public void setJMenuBar(JMenuBar menubar) {
		super.setJMenuBar(menubar);

		if (menubar != null) {
			northPanel.add(menubar, new FlexConstraint().left(W).right(W).top(W + TITLE_BAR_HEIGHT).height(MENU_BAR_HEIGHT));
			setTitleBarBackground(titleBarBackground);
		}
	}

	@Override
	public Container getContentPane() {
		if (contentPanel == null)
			return super.getContentPane();
		else
			return contentPanel;
	}

	private void setResizeCurser(boolean b) {
		if (b) {
			left.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			right.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			top.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			bottom.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			topleft.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			topright.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			bottomleft.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			bottomright.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
		} else {
			left.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			right.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			top.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			bottom.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			topleft.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			topright.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			bottomleft.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			bottomright.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
	}

	@Override
	public void setTitle(String title) {
		super.setTitle(title);
		lblTitle.setText(title);
	}

	public void setTitleForeground(Color foreground) {
		lblTitle.setForeground(foreground);
	}

	@Override
	public void setIconImage(Image image) {
		super.setIconImage(image);
	}

	public void setTitleImage(Image image) {
		if (image != null) lblIcon.setIcon(new ImageIcon(image));
	}

	class ResizeWindowListener extends MouseAdapter {
		private final JFrame frame;
		private Rectangle startSide = null;

		public ResizeWindowListener(JFrame frame) {
			this.frame = frame;
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getSource() == top && e.getClickCount() > 1 && DecoratedFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
				DecoratedFrame.this.setExtendedState(JFrame.NORMAL);
				dispatchEvent(new WindowEvent(DecoratedFrame.this, WindowEvent.WINDOW_STATE_CHANGED));
				revalidate();
				startSide = null;
			} else
				startSide = frame.getBounds();
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			if (DecoratedFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH)
				DecoratedFrame.this.setResizeCurser(false);
			else
				DecoratedFrame.this.setResizeCurser(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			DecoratedFrame.this.setResizeCurser(false);
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			if (startSide == null)
				return;
			if (DecoratedFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH)
				return;

			Rectangle tmp = (Rectangle) startSide.clone();
			Component c = e.getComponent();
			if (c == topleft) {
				tmp.y += e.getY();
				tmp.height -= e.getY();
				tmp.x += e.getX();
				tmp.width -= e.getX();
			} else if (c == top) {
				tmp.y += e.getY();
				tmp.height -= e.getY();
			} else if (c == topright) {
				tmp.y += e.getY();
				tmp.height -= e.getY();
				tmp.width += e.getX();
			} else if (c == left) {
				tmp.x += e.getX();
				tmp.width -= e.getX();
			} else if (c == right)
				tmp.width += e.getX();
			else if (c == bottomleft) {
				tmp.height += e.getY();
				tmp.x += e.getX();
				tmp.width -= e.getX();
			} else if (c == bottom)
				tmp.height += e.getY();
			else if (c == bottomright) {
				tmp.height += e.getY();
				tmp.width += e.getX();
			}

			if (frame.getMinimumSize().width < tmp.width && frame.getMinimumSize().height < tmp.height) {
				frame.setBounds(tmp);
				startSide = tmp;
			}
		}
	}

	class DragWindowListener extends MouseAdapter {
		private MouseEvent start;
		private Window window;
		private boolean doubleClicked = false;

		@Override
		public void mousePressed(MouseEvent me) {
			if (me.getClickCount() > 1 && canDoubleClickTitleBar) {
				doubleClicked = true;
				DecoratedFrame.this.setExtendedState(DecoratedFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH ? JFrame.NORMAL : JFrame.MAXIMIZED_BOTH);
				dispatchEvent(new WindowEvent(DecoratedFrame.this, WindowEvent.WINDOW_STATE_CHANGED));
				revalidate();
				me.consume();
			} else {
				if (window == null) {
					Object o = me.getSource();
					if (o instanceof Window)
						window = (Window) o;
					else if (o instanceof JComponent)
						window = SwingUtilities.windowForComponent(me.getComponent());
				}
				start = me;
			}
		}

		@Override
		public void mouseDragged(MouseEvent me) {
			if (!doubleClicked) {
				if (DecoratedFrame.this.getExtendedState() == JFrame.MAXIMIZED_BOTH)
					return;
				Point eventLocationOnScreen = me.getLocationOnScreen();
				if (window != null && (eventLocationOnScreen.x != start.getX() || eventLocationOnScreen.y != start.getY()))
					window.setLocation(eventLocationOnScreen.x - start.getX(),
							eventLocationOnScreen.y - start.getY());
			}
			doubleClicked = false;
		}
	}
}
