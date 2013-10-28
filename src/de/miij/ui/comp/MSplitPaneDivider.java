/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.miij.ui.comp;

import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JButton;
import javax.swing.JSplitPane;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicSplitPaneDivider;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import sun.swing.DefaultLookup;

/**
 *
 * @author Mirko Hecky
 */
public class MSplitPaneDivider extends BasicSplitPaneDivider
{
	private MSplitPaneUI mui;
	
    /**
     * Handles mouse dragging message to do the actual dragging.
     */
    protected DragController2 dragger;
    protected MouseHandler2 mouseHandler;
	private OneTouchState state;

	public MSplitPaneDivider(BasicSplitPaneUI ui)
	{
		super(ui);
		mui = (MSplitPaneUI) ui;
		setLayout(new DividerLayout2());
	}
	
	/**
	 * Returns the divider position for the normal state. To specify the
	 * divider position for normal state you have to set the preferred size for
	 * one of the components. If no preferred size is specified, the half width/
	 * height is returned.
	 * 
	 * @return 
	 */
	public int getNormalStatePosition()
	{
		Dimension size = splitPane.getSize();
		if(splitPane.getLeftComponent().getPreferredSize() != null)
			size = splitPane.getLeftComponent().getPreferredSize();
		else if(splitPane.getRightComponent().getPreferredSize() != null)
			size = splitPane.getRightComponent().getPreferredSize();
		return orientation == JSplitPane.HORIZONTAL_SPLIT ? size.width : size.height;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint (RenderingHints.KEY_ANTIALIASING,   RenderingHints.VALUE_ANTIALIAS_ON);
		
		// Draw border
		MSplitPane m = (MSplitPane) splitPane;
		if(m.getDividerBorderColor() != null)
		{
			g2d.setPaint(m.getDividerBorderColor());
			if(m.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
				g2d.drawRect(0, -1, getWidth() - 2, getHeight() + 1);
			else
				g2d.drawRect(-1, 0, getWidth() + 1, getHeight() - 2);
		}
		
		// Draw gripper/dragger
		if(m.getGripperColor() != null)
		{
			g2d.setPaint(m.getGripperColor());
			int r = orientation == JSplitPane.HORIZONTAL_SPLIT ? getWidth() / 2 : getHeight() / 2;
			int h = r * 3 * 2;
			for(int i = 0; i < 3; ++i)
				if(m.getOrientation() == JSplitPane.HORIZONTAL_SPLIT)
					g2d.fillOval(r - r/2, getHeight() / 2 - h/2 + i * r*2, r, r);
				else
					g2d.fillOval(getWidth() / 2 - h/2 + i * r*2, r - r / 2, r, r);
		}
	}

	/**
	 * Creates and return an instance of JButton that can be used to collapse
	 * the left component in the split pane.
	 */
	@Override
	protected JButton createLeftOneTouchButton()
	{
		final MSplitPane m = (MSplitPane) splitPane;

		JButton b = new JButton()
		{
			public void setBorder(Border b)
			{
			}

			public void paint(Graphics g)
			{
				if (splitPane != null)
				{
					int[] xs = new int[3];
					int[] ys = new int[3];
					int blockSize = ((MSplitPane)splitPane).getOneTouchButtonSize();
					blockSize -= 3;

					// ... then draw the arrow.
					g.setColor(m.getOneTouchButtonColor());
					if (orientation == JSplitPane.VERTICAL_SPLIT)
					{
						//blockSize = Math.min(getHeight(), oneTouchSize);
						xs[0] = blockSize;
						xs[1] = 0;
						xs[2] = blockSize << 1;
						ys[0] = 0;
						ys[1] = ys[2] = blockSize;
						g.drawPolygon(xs, ys, 3); // Little trick to make the
						// arrows of equal size
					}
					else
					{
//                        blockSize = Math.min(getWidth(), oneTouchSize);
						xs[0] = xs[2] = blockSize;
						xs[1] = 0;
						ys[0] = 0;
						ys[1] = blockSize;
						ys[2] = blockSize << 1;
					}
					g.fillPolygon(xs, ys, 3);
				}
			}

			// Don't want the button to participate in focus traversable.
			public boolean isFocusTraversable()
			{
				return false;
			}
		};
		b.setMinimumSize(new Dimension(((MSplitPane)splitPane).getOneTouchButtonSize() * orientation == JSplitPane.VERTICAL_SPLIT ? 2 : 1, ((MSplitPane)splitPane).getOneTouchButtonSize() * orientation == JSplitPane.VERTICAL_SPLIT ? 1 : 2));
		b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setRequestFocusEnabled(false);
		return b;
	}

	/**
	 * Creates and return an instance of JButton that can be used to collapse
	 * the right component in the split pane.
	 */
	@Override
	protected JButton createRightOneTouchButton()
	{
		final MSplitPane m = (MSplitPane) splitPane;

		JButton b = new JButton()
		{
			public void setBorder(Border border)
			{
			}

			public void paint(Graphics g)
			{
				if (splitPane != null)
				{
					int[] xs = new int[3];
					int[] ys = new int[3];
					int blockSize = ((MSplitPane)splitPane).getOneTouchButtonSize();
					blockSize -= 3;

					// ... then draw the arrow.
					g.setColor(m.getOneTouchButtonColor());
					if (orientation == JSplitPane.VERTICAL_SPLIT)
					{
//                        blockSize = Math.min(getHeight(), oneTouchSize);
						xs[2] = blockSize;
						xs[0] = blockSize << 1;
						xs[1] = 0;
						ys[2] = blockSize;
						ys[0] = ys[1] = 0;
//						g.drawPolygon(xs, ys, 3); // Little trick to make the
						// arrows of equal size
					}
					else
					{
//                        blockSize = Math.min(getWidth(), oneTouchSize);
						xs[0] = xs[2] = 0;
						xs[1] = blockSize;
						ys[0] = 0;
						ys[1] = blockSize;
						ys[2] = blockSize << 1;
					}
					g.fillPolygon(xs, ys, 3);
				}
			}

			// Don't want the button to participate in focus traversable.
			public boolean isFocusTraversable()
			{
				return false;
			}
		};
		b.setMinimumSize(new Dimension(((MSplitPane)splitPane).getOneTouchButtonSize() * orientation == JSplitPane.VERTICAL_SPLIT ? 2 : 1, ((MSplitPane)splitPane).getOneTouchButtonSize() * orientation == JSplitPane.VERTICAL_SPLIT ? 1 : 2));
		b.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		b.setFocusPainted(false);
		b.setBorderPainted(false);
		b.setRequestFocusEnabled(false);
		return b;
	}

	/**
	 * Messaged when the oneTouchExpandable value of the JSplitPane the receiver
	 * is contained in changes. Will create the <code>leftButton</code> and
	 * <code>rightButton</code> if they are null. invalidates the receiver as
	 * well.
	 */
	@Override
	protected void oneTouchExpandableChanged()
	{
		if (!DefaultLookup.getBoolean(splitPane, splitPaneUI,
									  "SplitPane.supportsOneTouchButtons", true))
			// Look and feel doesn't want to support one touch buttons, bail.
			return;
		if (splitPane.isOneTouchExpandable()
			&& leftButton == null
			&& rightButton == null)
		{
			/* Create the left button and add an action listener to
			 expand/collapse it. */
			leftButton = createLeftOneTouchButton();
			if (leftButton != null)
				leftButton.addActionListener(new OneTouchActionHandler(true));


			/* Create the right button and add an action listener to
			 expand/collapse it. */
			rightButton = createRightOneTouchButton();
			if (rightButton != null)
				rightButton.addActionListener(new OneTouchActionHandler(false));

			if (leftButton != null && rightButton != null)
			{
				add(leftButton);
				add(rightButton);
			}
		}
		revalidate();
	}

	public void setKeepHiddenPublic(boolean b)
	{
		setKeepHiddenPublic(b);
	}

    /**
     * Sets the SplitPaneUI that is using the receiver.
	 * @param newUI
     */
	@Override
    public void setBasicSplitPaneUI(BasicSplitPaneUI newUI) {
        if (splitPane != null) {
            splitPane.removePropertyChangeListener(this);
           if (mouseHandler != null) {
               splitPane.removeMouseListener(mouseHandler);
               splitPane.removeMouseMotionListener(mouseHandler);
               removeMouseListener(mouseHandler);
               removeMouseMotionListener(mouseHandler);
               mouseHandler = null;
           }
        }
        splitPaneUI = newUI;
        if (newUI != null) {
            splitPane = (MSplitPane) newUI.getSplitPane();
            if (splitPane != null) {
                if (mouseHandler == null) mouseHandler = new MouseHandler2();
                splitPane.addMouseListener(mouseHandler);
                splitPane.addMouseMotionListener(mouseHandler);
                addMouseListener(mouseHandler);
                addMouseMotionListener(mouseHandler);
                splitPane.addPropertyChangeListener(this);
                if (splitPane.isOneTouchExpandable()) {
                    oneTouchExpandableChanged();
                }
            }
        }
        else {
            splitPane = null;
        }
    }
	
	/**
     * MouseHandler is responsible for converting mouse events
     * (released, dragged...) into the appropriate DragController
     * methods.
     * <p>
     */
    protected class MouseHandler2 extends MouseAdapter
            implements MouseMotionListener
    {
        /**
         * Starts the dragging session by creating the appropriate instance
         * of DragController.
         */
        public void mousePressed(MouseEvent e) {
			boolean overDraggableArea = true;
			if(splitPane.isOneTouchExpandable())
			{
				if(orientation == JSplitPane.HORIZONTAL_SPLIT)
					overDraggableArea = !(e.getY() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
				else
					overDraggableArea = !(e.getX() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
			}
			
			if(!((MSplitPane)splitPane).isDraggable() || !overDraggableArea) return;
			
            if ((e.getSource() == MSplitPaneDivider.this ||
                 e.getSource() == splitPane) &&
                dragger == null &&splitPane.isEnabled()) {
                Component            newHiddenDivider = splitPaneUI.
                                     getNonContinuousLayoutDivider();

                if (hiddenDivider != newHiddenDivider) {
                    if (hiddenDivider != null) {
                        hiddenDivider.removeMouseListener(this);
                        hiddenDivider.removeMouseMotionListener(this);
                    }
                    hiddenDivider = newHiddenDivider;
                    if (hiddenDivider != null) {
                        hiddenDivider.addMouseMotionListener(this);
                        hiddenDivider.addMouseListener(this);
                    }
                }
                if (splitPane.getLeftComponent() != null &&
                    splitPane.getRightComponent() != null) {
                    if (orientation == JSplitPane.HORIZONTAL_SPLIT) {
                        dragger = new DragController2(e);
                    }
                    else {
                        dragger = new VerticalDragController2(e);
                    }
                    if (!dragger.isValid()) {
                        dragger = null;
                    }
                    else {
                        prepareForDragging();
                        dragger.continueDrag(e);
                    }
                }
                e.consume();
            }
        }


        /**
         * If dragger is not null it is messaged with completeDrag.
         */
        public void mouseReleased(MouseEvent e) {
			boolean overDraggableArea = true;
			if(splitPane.isOneTouchExpandable())
			{
				if(orientation == JSplitPane.HORIZONTAL_SPLIT)
					overDraggableArea = !(e.getY() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
				else
					overDraggableArea = !(e.getX() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
			}
			
			if(!((MSplitPane)splitPane).isDraggable() || !overDraggableArea) return;
			
            if (dragger != null) {
                if (e.getSource() == splitPane) {
                    dragger.completeDrag(e.getX(), e.getY());
                }
                else if (e.getSource() == MSplitPaneDivider.this) {
                    Point   ourLoc = getLocation();

                    dragger.completeDrag(e.getX() + ourLoc.x,
                                         e.getY() + ourLoc.y);
                }
                else if (e.getSource() == hiddenDivider) {
                    Point   hDividerLoc = hiddenDivider.getLocation();
                    int     ourX = e.getX() + hDividerLoc.x;
                    int     ourY = e.getY() + hDividerLoc.y;

                    dragger.completeDrag(ourX, ourY);
                }
                dragger = null;
                e.consume();
            }
        }


        //
        // MouseMotionListener
        //

        /**
         * If dragger is not null it is messaged with continueDrag.
         */
        public void mouseDragged(MouseEvent e) {
			boolean overDraggableArea = true;
			if(splitPane.isOneTouchExpandable())
			{
				if(orientation == JSplitPane.HORIZONTAL_SPLIT)
					overDraggableArea = !(e.getY() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
				else
					overDraggableArea = !(e.getX() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
			}
			
			if(!((MSplitPane)splitPane).isDraggable() || !overDraggableArea) return;
			
            if (dragger != null) {
                if (e.getSource() == splitPane) {
                    dragger.continueDrag(e.getX(), e.getY());
                }
                else if (e.getSource() == MSplitPaneDivider.this) {
                    Point   ourLoc = getLocation();

                    dragger.continueDrag(e.getX() + ourLoc.x,
                                         e.getY() + ourLoc.y);
                }
                else if (e.getSource() == hiddenDivider) {
                    Point   hDividerLoc = hiddenDivider.getLocation();
                    int     ourX = e.getX() + hDividerLoc.x;
                    int     ourY = e.getY() + hDividerLoc.y;

                    dragger.continueDrag(ourX, ourY);
                }
                e.consume();
            }
        }


        /**
         *  Resets the cursor based on the orientation.
         */
        public void mouseMoved(MouseEvent e) {
			mouseEntered(e);	// Do the same handling
        }

        /**
         * Invoked when the mouse enters a component.
         *
         * @param e MouseEvent describing the details of the enter event.
         * @since 1.5
         */
        public void mouseEntered(MouseEvent e) {
			boolean overDraggableArea = true;
			if(splitPane.isOneTouchExpandable())
			{
				if(orientation == JSplitPane.HORIZONTAL_SPLIT)
					overDraggableArea = !(e.getY() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
				else
					overDraggableArea = !(e.getX() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
			}
			
			if(!((MSplitPane)splitPane).isDraggable() || !overDraggableArea) {
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				return;
			} else {
				setCursor((orientation == JSplitPane.HORIZONTAL_SPLIT) ?
                  Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR) :
                  Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			}
			
            if (e.getSource() == MSplitPaneDivider.this) {
                setMouseOver(true);
            }
        }

        /**
         * Invoked when the mouse exits a component.
         *
         * @param e MouseEvent describing the details of the exit event.
         * @since 1.5
         */
        public void mouseExited(MouseEvent e) {
			boolean overDraggableArea = true;
			if(splitPane.isOneTouchExpandable())
			{
				if(orientation == JSplitPane.HORIZONTAL_SPLIT)
					overDraggableArea = !(e.getY() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
				else
					overDraggableArea = !(e.getX() < ((MSplitPane)splitPane).getOneTouchButtonSize() * 5);
			}
			
			if(!((MSplitPane)splitPane).isDraggable() || !overDraggableArea) return;
			
            if (e.getSource() == MSplitPaneDivider.this) {
                setMouseOver(false);
            }
        }
    }
	
	/**
     * Handles the events during a dragging session for a
     * HORIZONTAL_SPLIT oriented split pane. This continually
     * messages <code>dragDividerTo</code> and then when done messages
     * <code>finishDraggingTo</code>. When an instance is created it should be
     * messaged with <code>isValid</code> to insure that dragging can happen
     * (dragging won't be allowed if the two views can not be resized).
     * <p>
     * <strong>Warning:</strong>
     * Serialized objects of this class will not be compatible with
     * future Swing releases. The current serialization support is
     * appropriate for short term storage or RMI between applications running
     * the same version of Swing.  As of 1.4, support for long term storage
     * of all JavaBeans<sup><font size="-2">TM</font></sup>
     * has been added to the <code>java.beans</code> package.
     * Please see {@link java.beans.XMLEncoder}.
     */
    protected class DragController2
    {
        /**
         * Initial location of the divider.
         */
        int initialX;

        /**
         * Maximum and minimum positions to drag to.
         */
        int maxX, minX;

        /**
         * Initial location the mouse down happened at.
         */
        int offset;

        protected DragController2(MouseEvent e) {
            JSplitPane  splitPane = splitPaneUI.getSplitPane();
            Component   leftC = splitPane.getLeftComponent();
            Component   rightC = splitPane.getRightComponent();

            initialX = getLocation().x;
            if (e.getSource() == MSplitPaneDivider.this) {
                offset = e.getX();
            }
            else { // splitPane
                offset = e.getX() - initialX;
            }
            if (leftC == null || rightC == null || offset < -1 ||
                offset >= getSize().width) {
                // Don't allow dragging.
                maxX = -1;
            }
            else {
                Insets      insets = splitPane.getInsets();

                if (leftC.isVisible()) {
                    minX = leftC.getMinimumSize().width;
                    if (insets != null) {
                        minX += insets.left;
                    }
                }
                else {
                    minX = 0;
                }
                if (rightC.isVisible()) {
                    int right = (insets != null) ? insets.right : 0;
                    maxX = Math.max(0, splitPane.getSize().width -
                                    (getSize().width + right) -
                                    rightC.getMinimumSize().width);
                }
                else {
                    int right = (insets != null) ? insets.right : 0;
                    maxX = Math.max(0, splitPane.getSize().width -
                                    (getSize().width + right));
                }
                if (maxX < minX) minX = maxX = 0;
            }
        }


        /**
         * Returns true if the dragging session is valid.
         */
        protected boolean isValid() {
            return (maxX > 0);
        }


        /**
         * Returns the new position to put the divider at based on
         * the passed in MouseEvent.
         */
        protected int positionForMouseEvent(MouseEvent e) {
            int newX = (e.getSource() == MSplitPaneDivider.this) ?
                        (e.getX() + getLocation().x) : e.getX();

            newX = Math.min(maxX, Math.max(minX, newX - offset));
            return newX;
        }


        /**
         * Returns the x argument, since this is used for horizontal
         * splits.
         */
        protected int getNeededLocation(int x, int y) {
            int newX;

            newX = Math.min(maxX, Math.max(minX, x - offset));
            return newX;
        }


        protected void continueDrag(int newX, int newY) {
            dragDividerTo(getNeededLocation(newX, newY));
        }


        /**
         * Messages dragDividerTo with the new location for the mouse
         * event.
         */
        protected void continueDrag(MouseEvent e) {
            dragDividerTo(positionForMouseEvent(e));
        }


        protected void completeDrag(int x, int y) {
            finishDraggingTo(getNeededLocation(x, y));
        }


        /**
         * Messages finishDraggingTo with the new location for the mouse
         * event.
         */
        protected void completeDrag(MouseEvent e) {
            finishDraggingTo(positionForMouseEvent(e));
        }
    } // End of BasicSplitPaneDivider.DragController


    /**
     * Handles the events during a dragging session for a
     * VERTICAL_SPLIT oriented split pane. This continually
     * messages <code>dragDividerTo</code> and then when done messages
     * <code>finishDraggingTo</code>. When an instance is created it should be
     * messaged with <code>isValid</code> to insure that dragging can happen
     * (dragging won't be allowed if the two views can not be resized).
     */
    protected class VerticalDragController2 extends DragController2
    {
        /* DragControllers ivars are now in terms of y, not x. */
        protected VerticalDragController2(MouseEvent e) {
            super(e);
            JSplitPane splitPane = splitPaneUI.getSplitPane();
            Component  leftC = splitPane.getLeftComponent();
            Component  rightC = splitPane.getRightComponent();

            initialX = getLocation().y;
            if (e.getSource() == MSplitPaneDivider.this) {
                offset = e.getY();
            }
            else {
                offset = e.getY() - initialX;
            }
            if (leftC == null || rightC == null || offset < -1 ||
                offset > getSize().height) {
                // Don't allow dragging.
                maxX = -1;
            }
            else {
                Insets     insets = splitPane.getInsets();

                if (leftC.isVisible()) {
                    minX = leftC.getMinimumSize().height;
                    if (insets != null) {
                        minX += insets.top;
                    }
                }
                else {
                    minX = 0;
                }
                if (rightC.isVisible()) {
                    int    bottom = (insets != null) ? insets.bottom : 0;

                    maxX = Math.max(0, splitPane.getSize().height -
                                    (getSize().height + bottom) -
                                    rightC.getMinimumSize().height);
                }
                else {
                    int    bottom = (insets != null) ? insets.bottom : 0;

                    maxX = Math.max(0, splitPane.getSize().height -
                                    (getSize().height + bottom));
                }
                if (maxX < minX) minX = maxX = 0;
            }
        }


        /**
         * Returns the y argument, since this is used for vertical
         * splits.
         */
        protected int getNeededLocation(int x, int y) {
            int newY;

            newY = Math.min(maxX, Math.max(minX, y - offset));
            return newY;
        }


        /**
         * Returns the new position to put the divider at based on
         * the passed in MouseEvent.
         */
        protected int positionForMouseEvent(MouseEvent e) {
            int newY = (e.getSource() == MSplitPaneDivider.this) ?
                        (e.getY() + getLocation().y) : e.getY();


            newY = Math.min(maxX, Math.max(minX, newY - offset));
            return newY;
        }
    } // End of BasicSplitPaneDividier.VerticalDragController

	/**
	 * Listeners installed on the one touch expandable buttons.
	 */
	private class OneTouchActionHandler implements ActionListener
	{
		/**
		 * True indicates the resize should go the minimum (top or left) vs
		 * false which indicates the resize should go to the maximum.
		 */
		private boolean toMinimum;

		OneTouchActionHandler(boolean toMinimum)
		{
			this.toMinimum = toMinimum;
		}

		@Override
		public void actionPerformed(ActionEvent e)
		{
			Insets insets = splitPane.getInsets();
			int lastLoc = splitPane.getLastDividerLocation();
			int currentLoc = splitPaneUI.getDividerLocation(splitPane);
			int newLoc = currentLoc;
			
//			if(state == null)
//				state = OneTouchState.NORMAL;
//			
//			// If the splitter is not draggable, then you can only switch between
//			// 3 states ...
//			if(!((MSplitPane)splitPane).isDraggable())
//			{
//				if(toMinimum)
//				{
//					switch(state)
//					{
//						case MAXIMIZED:
//							newLoc = getNormalStatePosition();
//							state = OneTouchState.NORMAL;
//							break;
//						case MINIMIZED:
//							break;
//						case NORMAL:
//							if(orientation == JSplitPane.HORIZONTAL_SPLIT)
//								newLoc = insets.left;
//							else
//								newLoc = insets.top;
//							state = OneTouchState.MINIMIZED;
//							break;
//					}
//				}
//				else
//				{
//					switch(state)
//					{
//						case MAXIMIZED:
//							break;
//						case MINIMIZED:
//							newLoc = getNormalStatePosition();
//							state = OneTouchState.NORMAL;
//							break;
//						case NORMAL:
//							if(orientation == JSplitPane.HORIZONTAL_SPLIT)
//								newLoc = splitPane.getWidth() - getWidth() - insets.left;
//							else
//								newLoc = splitPane.getHeight() - getHeight() - insets.top;
//							state = OneTouchState.MAXIMIZED;
//							break;
//					}
//				}
//			}
//			else
			{
				state = OneTouchState.NORMAL;

				// We use the location from the UI directly, as the location the
				// JSplitPane itself maintains is not necessarly correct.
				if (toMinimum)	// from right to left or from bottom to top
				{
					if (orientation == JSplitPane.VERTICAL_SPLIT)
					{
						if (currentLoc >= (splitPane.getHeight() - insets.bottom - getHeight()) ||
							(splitPane.getRightComponent().getMinimumSize() != null && currentLoc >= (splitPane.getHeight() - insets.bottom - getHeight() - splitPane.getRightComponent().getMinimumSize().height)) ||
							(splitPane.getLeftComponent().getMaximumSize() != null && currentLoc >= splitPane.getLeftComponent().getMaximumSize().height))
						{
							int maxLoc = splitPane.getMaximumDividerLocation();
							newLoc = Math.min(lastLoc, maxLoc);
							if(newLoc == currentLoc && splitPane.getLeftComponent().getPreferredSize() != null)
							{
								int pref = splitPane.getLeftComponent().getPreferredSize().height;
								newLoc = pref;
							} else if(newLoc == currentLoc && splitPane.getRightComponent().getPreferredSize() != null)
							{
								int pref = splitPane.getRightComponent().getPreferredSize().height;
								newLoc = splitPane.getHeight() - pref;
							}
							
							if(newLoc == currentLoc)
							{
								newLoc = splitPane.getHeight() / 2;
							}
							mui.setKeepHidden(false);
						}
						else
						{
							newLoc = insets.top;
							mui.setKeepHidden(true);
							state = OneTouchState.MINIMIZED;
						}
					}
					else if (currentLoc >= (splitPane.getWidth() - insets.right - getWidth()) ||
							  (splitPane.getRightComponent().getMinimumSize() != null && currentLoc >= (splitPane.getWidth() - insets.right - getWidth() - splitPane.getRightComponent().getMinimumSize().width)) ||
							  (splitPane.getLeftComponent().getMaximumSize() != null && currentLoc >= splitPane.getLeftComponent().getMaximumSize().width))
					{
						int maxLoc = splitPane.getMaximumDividerLocation();
						newLoc = Math.min(lastLoc, maxLoc);
						if(newLoc == currentLoc && splitPane.getLeftComponent().getPreferredSize() != null)
						{
							int pref = splitPane.getLeftComponent().getPreferredSize().width;
							newLoc = pref;
						} else if(newLoc == currentLoc && splitPane.getRightComponent().getPreferredSize() != null)
						{
							int pref = splitPane.getRightComponent().getPreferredSize().width;
							newLoc = splitPane.getWidth() - pref;
						}
						
						if(newLoc == currentLoc)
						{
							newLoc = splitPane.getWidth() / 2;
						}
						mui.setKeepHidden(false);
					}
					else
					{
						newLoc = insets.left;
						mui.setKeepHidden(true);
						state = OneTouchState.MINIMIZED;
					}
				}
				// from left to right or from top to bottom
				else if (orientation == JSplitPane.VERTICAL_SPLIT)
				{
					if (currentLoc == insets.top ||
						(splitPane.getLeftComponent().getMinimumSize() != null && currentLoc == splitPane.getLeftComponent().getMinimumSize().height) ||
						(splitPane.getRightComponent().getMaximumSize() != null && currentLoc == splitPane.getHeight() - splitPane.getRightComponent().getMaximumSize().height - insets.bottom))
					{
						int maxLoc = splitPane.getMaximumDividerLocation();
						newLoc = Math.min(lastLoc, maxLoc);
						if(newLoc == currentLoc && splitPane.getRightComponent().getPreferredSize() != null)
						{
							int pref = splitPane.getRightComponent().getPreferredSize().height;
							newLoc = splitPane.getHeight() - pref;
						} else if(newLoc == currentLoc && splitPane.getLeftComponent().getPreferredSize() != null)
						{
							int pref = splitPane.getLeftComponent().getPreferredSize().height;
							newLoc = pref;
						}
						
						if(newLoc == currentLoc)
						{
							newLoc = splitPane.getHeight() / 2;
						}
						mui.setKeepHidden(false);
					}
					else
					{
						newLoc = splitPane.getHeight() - getHeight()
								 - insets.top;
						mui.setKeepHidden(true);
						state = OneTouchState.MAXIMIZED;
					}
				}
				else if (currentLoc == insets.left ||
						 (splitPane.getLeftComponent().getMinimumSize() != null && currentLoc == splitPane.getLeftComponent().getMinimumSize().width) ||
						 (splitPane.getRightComponent().getMaximumSize() != null && currentLoc == splitPane.getWidth() - splitPane.getRightComponent().getMaximumSize().width - insets.right))
				{
					int maxLoc = splitPane.getMaximumDividerLocation();
					newLoc = Math.min(lastLoc, maxLoc);
					if(newLoc == currentLoc && splitPane.getRightComponent().getPreferredSize() != null)
					{
						int pref = splitPane.getRightComponent().getPreferredSize().width;
						newLoc = splitPane.getWidth() - pref;
					} else if(newLoc == currentLoc && splitPane.getLeftComponent().getPreferredSize() != null)
					{
						int pref = splitPane.getLeftComponent().getPreferredSize().width;
						newLoc = pref;
					}
					
					if(newLoc == currentLoc)
					{
						newLoc = splitPane.getWidth() / 2;
					}
					mui.setKeepHidden(false);
				}
				else
				{
					newLoc = splitPane.getWidth() - getWidth() - insets.left;
					mui.setKeepHidden(true);
					state = OneTouchState.MAXIMIZED;
				}
			}
			
			if (currentLoc != newLoc)
			{
				splitPane.setDividerLocation(newLoc);
				// We do this in case the dividers notion of the location
				// differs from the real location.
				splitPane.setLastDividerLocation(currentLoc);
			}
			
			if(((MSplitPane)splitPane).getOneTouchListener() != null)
			{
				((MSplitPane)splitPane).getOneTouchListener().oneTouchButtonPressed(state);
			}
		}
	} // End of class BasicSplitPaneDivider.LeftActionListener


    /**
     * Used to layout a <code>BasicSplitPaneDivider</code>.
     * Layout for the divider
     * involves appropriately moving the left/right buttons around.
     * <p>
     */
    protected class DividerLayout2 implements LayoutManager
    {
        public void layoutContainer(Container c) {
			int oneTouchOffset = ((MSplitPane)splitPane).getOneTouchButtonSize() / 2;
			
            if (leftButton != null && rightButton != null &&
                c == MSplitPaneDivider.this) {
                if (splitPane.isOneTouchExpandable()) {
                    Insets insets = getInsets();

                    if (orientation == JSplitPane.VERTICAL_SPLIT) {
                        int extraX = (insets != null) ? insets.left : 0;
                        int blockSize = getHeight();

                        if (insets != null) {
                            blockSize -= (insets.top + insets.bottom);
                            blockSize = Math.max(blockSize, 0);
                        }
                        blockSize = Math.min(blockSize, ((MSplitPane)splitPane).getOneTouchButtonSize());

                        int y = (c.getSize().height - blockSize) / 2;

//                        if (!centerOneTouchButtons) {
                            y = (insets != null) ? insets.top : 0;
                            extraX = 0;
//                        }
                        leftButton.setBounds(extraX + oneTouchOffset, y,
                                             blockSize * 2, blockSize);
                        rightButton.setBounds(extraX + oneTouchOffset +
                                              ((MSplitPane)splitPane).getOneTouchButtonSize() * 2, y,
                                              blockSize * 2, blockSize);
                    }
                    else {
                        int extraY = (insets != null) ? insets.top : 0;
                        int blockSize = getWidth();

                        if (insets != null) {
                            blockSize -= (insets.left + insets.right);
                            blockSize = Math.max(blockSize, 0);
                        }
                        blockSize = Math.min(blockSize, ((MSplitPane)splitPane).getOneTouchButtonSize());

                        int x = (c.getSize().width - blockSize) / 2;

//                        if (!centerOneTouchButtons) {
                            x = (insets != null) ? insets.left : 0;
                            extraY = 0;
//                        }

                        leftButton.setBounds(x, extraY + oneTouchOffset,
                                             blockSize, blockSize * 2);
                        rightButton.setBounds(x, extraY + oneTouchOffset +
                                              ((MSplitPane)splitPane).getOneTouchButtonSize() * 2, blockSize,
                                              blockSize * 2);
                    }
                }
                else {
                    leftButton.setBounds(-5, -5, 1, 1);
                    rightButton.setBounds(-5, -5, 1, 1);
                }
            }
        }


        public Dimension minimumLayoutSize(Container c) {
            // NOTE: This isn't really used, refer to
            // BasicSplitPaneDivider.getPreferredSize for the reason.
            // I leave it in hopes of having this used at some point.
            if (c != MSplitPaneDivider.this || splitPane == null) {
                return new Dimension(0,0);
            }
            Dimension buttonMinSize = null;

            if (splitPane.isOneTouchExpandable() && leftButton != null) {
                buttonMinSize = leftButton.getMinimumSize();
            }

            Insets insets = getInsets();
            int width = getDividerSize();
            int height = width;

            if (orientation == JSplitPane.VERTICAL_SPLIT) {
                if (buttonMinSize != null) {
                    int size = buttonMinSize.height;
                    if (insets != null) {
                        size += insets.top + insets.bottom;
                    }
                    height = Math.max(height, size);
                }
                width = 1;
            }
            else {
                if (buttonMinSize != null) {
                    int size = buttonMinSize.width;
                    if (insets != null) {
                        size += insets.left + insets.right;
                    }
                    width = Math.max(width, size);
                }
                height = 1;
            }
            return new Dimension(width, height);
        }


        public Dimension preferredLayoutSize(Container c) {
            return minimumLayoutSize(c);
        }


        public void removeLayoutComponent(Component c) {}

        public void addLayoutComponent(String string, Component c) {}
    } // End of class BasicSplitPaneDivider.DividerLayout

}
