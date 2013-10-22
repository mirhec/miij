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
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

/**
 *
 * @author Mirko Hecky
 */
public class DecoratedDialog extends MDialog
{
	protected JLabel lblTitle;
	protected static final int W = 4;
	protected static final int TITLE_BAR_HEIGHT = 25;
	protected MPanel contentPanel = new MPanel();
	protected MPanel resizePanel  = new MPanel();
	protected MPanel toolbarPanel = new MPanel();
    private JLabel left, right, top, bottom, topleft, topright, bottomleft, bottomright;
	private ArrayList<JButton> toolbarButtons = new ArrayList<JButton>();
	private boolean helpVisible = true;
	
	public DecoratedDialog(Window owner)
	{
		super(owner);
		initDecoratedFrame();
	}
	public DecoratedDialog(Window owner, boolean helpVisible)
	{
		super(owner);
		this.helpVisible = helpVisible;
		initDecoratedFrame();
	}
	
	public void addToolbarButton(Icon i, final Connector c)
	{
		addToolbarButton(toolbarButtons.size(), i, c);
	}
	
	public void addToolbarButton(Icon i, Icon iHover, final Connector c)
	{
		addToolbarButton(toolbarButtons.size(), i, iHover, c);
	}
	
	public void addToolbarButton(int index, Icon i, final Connector c)
	{
		addToolbarButton(index, i, i, c);
	}
	
	public void addToolbarButton(int index, Icon i, Icon iHover, final Connector c)
	{
		addToolbarButton(index, i, iHover, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e)
			{
				if(c != null)
					c.action(e);
			}
		});
	}
	
	public void addToolbarButton(Icon i, ActionListener l)
	{
		addToolbarButton(toolbarButtons.size(), i, l);
	}
	
	public void addToolbarButton(Icon i, Icon iHover, ActionListener l)
	{
		addToolbarButton(toolbarButtons.size(), i, iHover, l);
	}
	
	public void addToolbarButton(int index, Icon i, ActionListener l)
	{
		addToolbarButton(index, i, i, l);
	}
	
	public void addToolbarButton(int index, Icon i, Icon iHover, ActionListener l)
	{
		JButton btn = makeButton(i, iHover, false);
		btn.addActionListener(l);
		toolbarButtons.add(index, btn);
		toolbarPanel.add(btn, new FlexConstraint().right(TITLE_BAR_HEIGHT + TITLE_BAR_HEIGHT * index).top(0).bottom(0).width(TITLE_BAR_HEIGHT));
	}
	
	public JButton getToolbarButton(int index)
	{
		if(toolbarButtons.size() > index && index >= 0)
			return toolbarButtons.get(index);
		return null;
	}

	private JButton makeButton(final Icon i, final Icon iHover, final boolean close)
	{
		final JButton button = new JButton(i);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorder(null);
		button.setBorderPainted(false);
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e)
			{
				button.setIcon(iHover);
			}
			@Override
			public void mouseExited(MouseEvent e)
			{
				button.setIcon(i);
			}
		});
        button.addActionListener(new ActionListener() {
            @Override public void actionPerformed(ActionEvent e) {
				if(close)
					dispatchEvent(new WindowEvent(DecoratedDialog.this, WindowEvent.WINDOW_CLOSING));
            }
        });
		return button;
	}
	
	private JButton makeCloseButton()
	{
		final Icon i1 = WindowIcons.getCloseIcon(false);
		final Icon i2 = WindowIcons.getCloseIcon(true);
		return makeButton(i1, i2, true);
	}
	
	private void initDecoratedFrame()
	{
		setUndecorated(true);
		
		JButton btnClose = makeCloseButton();
		
		JPanel title = new JPanel(new FlexLayout());
        DecoratedDialog.DragWindowListener dwl = new DecoratedDialog.DragWindowListener();
        title.addMouseListener(dwl);
        title.addMouseMotionListener(dwl);
        title.setOpaque(false);
        //title.setBackground(Color.ORANGE);
        title.setBorder(BorderFactory.createEmptyBorder(0,W,W,0));

		lblTitle = new JLabel(Miij.getAppName(), JLabel.LEFT);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTitle.setIcon(new ImageIcon(DecoratedDialog.class.getResource("/gfx/icon.png")));
        title.add(lblTitle, new FlexConstraint().left(0).right(toolbarPanel, M.LEFT, 0).height(TITLE_BAR_HEIGHT));
        toolbarPanel.add(btnClose, new FlexConstraint().right(0).top(0).width(TITLE_BAR_HEIGHT).height(TITLE_BAR_HEIGHT));
		if(helpVisible)
			addToolbarButton(WindowIcons.getQuestionIcon(false), WindowIcons.getQuestionIcon(true), new Connector(this, "help"));
		title.add(toolbarPanel, new FlexConstraint().right(0).top(0).height(TITLE_BAR_HEIGHT).width(new FlexRecalculateListener() {

			@Override
			public int recalculate()
			{
				return TITLE_BAR_HEIGHT + TITLE_BAR_HEIGHT * toolbarButtons.size();
			}
		}));
        //title.add(iconify, BorderLayout.WEST);

        DecoratedDialog.ResizeWindowListener rwl = new DecoratedDialog.ResizeWindowListener(this);
        for(JLabel l:java.util.Arrays.asList(
            left         = new JLabel(), right        = new JLabel(),
            top          = new JLabel(), bottom       = new JLabel(),
            topleft      = new JLabel(), topright     = new JLabel(),
            bottomleft   = new JLabel(), bottomright  = new JLabel())) {
//			l.addMouseListener(dcl);
            l.addMouseListener(rwl);
            l.addMouseMotionListener(rwl);
            //l.setOpaque(true);
            //l.setBackground(Color.RED);
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

        JPanel titlePanel = new JPanel(new BorderLayout(0,0));
        titlePanel.add(top,           BorderLayout.NORTH);
        titlePanel.add(title,         BorderLayout.CENTER);

        JPanel northPanel = new JPanel(new BorderLayout(0,0));
        northPanel.add(topleft,       BorderLayout.WEST);
        northPanel.add(titlePanel,    BorderLayout.CENTER);
        northPanel.add(topright,      BorderLayout.EAST);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(bottomleft,    BorderLayout.WEST);
        southPanel.add(bottom,        BorderLayout.CENTER);
        southPanel.add(bottomright,   BorderLayout.EAST);

        resizePanel.add(left,         new FlexConstraint().left(0).top(TITLE_BAR_HEIGHT).bottom(10).width(10));
        resizePanel.add(right,        new FlexConstraint().right(0).top(TITLE_BAR_HEIGHT).bottom(10).width(10));
        resizePanel.add(northPanel,   new FlexConstraint().left(0).top(0).right(0).height(TITLE_BAR_HEIGHT));
        resizePanel.add(southPanel,   new FlexConstraint().left(0).right(0).bottom(0).height(10));
        resizePanel.add(contentPanel, new FlexConstraint().left(10).top(TITLE_BAR_HEIGHT).bottom(10).right(10));

        titlePanel.setOpaque(false);
        northPanel.setOpaque(false);
        southPanel.setOpaque(false);

		resizePanel.setBorder(new LineBorder(Color.GRAY));
        setContentPane(resizePanel);
	}

	private void setResizeCurser(boolean b)
	{
		if(b)
		{
			left.setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
			right.setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
			top.setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			bottom.setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			topleft.setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			topright.setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			bottomleft.setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			bottomright.setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
		}
		else
		{
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
	public Container getContentPane()
	{
		if(contentPanel == null)
			return super.getContentPane();
		else
			return contentPanel;
	}
	
    class ResizeWindowListener extends MouseAdapter {
        private Rectangle startSide = null;
        private final JDialog frame;
        public ResizeWindowListener(JDialog frame) {
            this.frame = frame;
        }
        @Override public void mousePressed(MouseEvent e) {
            startSide = frame.getBounds();
        }
		
		@Override public void mouseEntered(MouseEvent e) {
			if(!DecoratedDialog.this.isResizable())
				DecoratedDialog.this.setResizeCurser(false);
			else
				DecoratedDialog.this.setResizeCurser(true);
		}
		
        @Override public void mouseDragged(MouseEvent e) {
            if(startSide==null) return;
			if(!DecoratedDialog.this.isResizable()) return;
			Rectangle tmp = (Rectangle) startSide.clone();
            Component c = e.getComponent();
            if(c==topleft) {
                tmp.y += e.getY();
                tmp.height -= e.getY();
                tmp.x += e.getX();
                tmp.width -= e.getX();
            }else if(c==top) {
                tmp.y += e.getY();
                tmp.height -= e.getY();
            }else if(c==topright) {
                tmp.y += e.getY();
                tmp.height -= e.getY();
                tmp.width += e.getX();
            }else if(c==left) {
                tmp.x += e.getX();
                tmp.width -= e.getX();
            }else if(c==right) {
                tmp.width += e.getX();
            }else if(c==bottomleft) {
                tmp.height += e.getY();
                tmp.x += e.getX();
                tmp.width -= e.getX();
            }else if(c==bottom) {
                tmp.height += e.getY();
            }else if(c==bottomright) {
                tmp.height += e.getY();
                tmp.width += e.getX();
            }
			
			if(frame.getMinimumSize().width < tmp.width && frame.getMinimumSize().height < tmp.height)
			{
				frame.setBounds(tmp);
				startSide = tmp;
			}
        }
    }
	
	class DragWindowListener extends MouseAdapter {
		private MouseEvent start;
		private Window window;
		@Override public void mousePressed(MouseEvent me) {
			if(window==null) {
				Object o = me.getSource();
				if(o instanceof Window) {
					window = (Window)o;
				}else if(o instanceof JComponent) {
					window = SwingUtilities.windowForComponent(me.getComponent());
				}
			}
			start = me;
		}
		@Override public void mouseDragged(MouseEvent me) {
			Point eventLocationOnScreen = me.getLocationOnScreen();
			if(window!=null && (eventLocationOnScreen.x != start.getX() || eventLocationOnScreen.y != start.getY())) {
				window.setLocation(eventLocationOnScreen.x - start.getX(),
								   eventLocationOnScreen.y - start.getY());
			}
		}
	}
	
	@Override
	public void setTitle(String title)
	{
		lblTitle.setText(title);
	}
}
