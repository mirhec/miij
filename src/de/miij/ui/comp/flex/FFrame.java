package de.miij.ui.comp.flex;

import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.UIManager;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import de.miij.layout.FlexConstraint;
import de.miij.layout.FlexLayout;
import de.miij.ui.comp.MButton;
import de.miij.ui.comp.MFrame;
import de.miij.ui.comp.MPanel;
import de.miij.util.M;
import java.awt.Color;
import javax.swing.JSplitPane;

/**
 * Mit diesem Fenster ist es m&ouml;glich, alle Komponenten flexibel anordnen zu
 * lassen, indem man beim Setzen der Komponenten die Methode <i><b>addComponent(
 * ... )</b></i> aufruft.
 * <p/>
 * @author Mirhec
 */
public class FFrame extends JFrame implements Flexable
{
	private static final long serialVersionUID = -2615853816282310538L;
//	private FPanel					ComponentPanel		= null;

	public FFrame()
	{
		super();
//		JPanel pnl			= new JPanel();
//		pnl.setLayout( new BorderLayout() );
//		this.setContentPane( pnl );
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

//		ComponentPanel		= new FPanel( false );
//		this.getContentPane().add( ComponentPanel , BorderLayout.CENTER );
		getContentPane().setLayout(new FlexLayout());

//		addComponentListener(new ComponentAdapter() {
//
//			@Override
//			public void componentResized(ComponentEvent e)
//			{
//				ComponentPanel.resizeFlexComponents();
//			}
//		});
	}

//	public FPanel getComponentPanel()
//	{
//		return ComponentPanel;
//	}
//	public void setComponentPanel( FPanel panel )
//	{
//		this.ComponentPanel				= panel;
//		setContentPane( ComponentPanel );
//	}
	public void setJToolBar(JToolBar tb, String position)
	{
		this.getContentPane().add(tb, position);
	}

	/**
	 * Diese Methode f&uuml;gt der ContentPane des FFrames eine neue flexible
	 * Komponente hinzu.
	 * <p/>
	 * @param comp
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 */
	public void addFlexibleComponent(FlexComponent flexComp)
	{
		// this.FlexComponents.add( flexComp );
//		this.getComponentPanel().addFlexibleComponent( flexComp );
		getContentPane().add(flexComp.FlexComponent, new FlexConstraint(flexComp));
	}

	public FlexComponent flex(Component c)
	{
		return new FlexComponent(c);
	}
}

class FlexComponentTest2
{
	public static void main(String[] args)
	{
		MFrame mf = new MFrame("test2");
		MPanel mp1 = new MPanel();
		MPanel mp2 = new MPanel();
		JSplitPane mp3 = new JSplitPane();
		MPanel mp4 = new MPanel();
		MPanel mp5 = new MPanel();
		MButton mb1 = new MButton("1");
		MButton mb2 = new MButton("2");
		MButton mb3 = new MButton("3");

		mp1.setBackground(Color.RED);
		mp2.setBackground(Color.BLUE);
		mp3.setBackground(Color.GREEN);
		mp4.setBackground(Color.YELLOW);
		mp5.setBackground(Color.CYAN);

		mf.flex(mp1).left(0).top(0).bottom(0).right(0).addAt(mf);
		mf.flex(mp2).left(10).top(10).bottom(10).right(10).addAt(mp1);
		mf.flex(mp3).right(10).top(10).bottom(10).left(10).addAt(mp2);
		mf.flex(mb1).left(10).right(10).top(10).height(20).addAt(mp5);
		mf.flex(mb2).left(10).right(10).top(40).height(20).addAt(mp5);
		mf.flex(mb3).left(10).right(10).top(70).height(20).addAt(mp5);

		mp3.setLeftComponent(mp4);
		mp3.setRightComponent(mp5);
		mp3.setDividerLocation(150);
		mp3.setDividerSize(0);

		mf.setSize(300, 200);
		mf.setVisible(true);
	}
}

class FlexibleComponentTest
{
	public static void main(String[] args)
	{
		try
		{
			UIManager.setLookAndFeel(new WindowsLookAndFeel());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}

		FFrame mf = new FFrame();
		mf.setTitle("FlexibleComponentTest");
		mf.setSize(500, 350);

		JLabel jl = new JLabel("Label");
		final JTextField jtf = new JTextField("30");
		JButton jb1 = new JButton("JB_1");
		JButton jb2 = new JButton("JB_2");

		FlexComponent flex = new FlexComponent(jl);
		flex.FlexX = 10;
		flex.FlexY = 10;
		flex.FlexWidth = 50;
		flex.FlexHeight = 20;
		mf.addFlexibleComponent(flex);

		flex = new FlexComponent(jtf);
		flex.FlexLeftComponent = new DockComponent(jl, M.RIGHT);
		flex.FlexLeftOffset = 10;
		flex.FlexRightComponent = new DockComponent(jb1, M.LEFT);
		flex.FlexRightOffset = -10;
		flex.FlexTopComponent = new DockComponent(jl, M.TOP);
		flex.FlexBottomComponent = new DockComponent(jl, M.BOTTOM);
		mf.addFlexibleComponent(flex);

		flex = new FlexComponent(jb1);
		flex.FlexRight = 100;
		flex.FlexRightOffset = -10;
		flex.FlexWidthListener = new FlexRecalculateListener()
		{
			public int recalculate()
			{
				int width = 30;

				try
				{
					width = Integer.parseInt(jtf.getText());
				}
				catch (Exception ex)
				{
					ex.printStackTrace();
				}

				return width;
			}
		};
		flex.FlexTopComponent = new DockComponent(jl, M.TOP);
		flex.FlexBottomComponent = new DockComponent(jl, M.BOTTOM);
		mf.addFlexibleComponent(flex);

		flex = new FlexComponent(jb2);
		flex.FlexX = 10;
		flex.FlexTopComponent = new DockComponent(jl, M.BOTTOM);
		flex.FlexTopOffset = 10;
		flex.FlexRight = 100;
		flex.FlexRightOffset = -10;
		flex.FlexBottom = 100;
		flex.FlexBottomOffset = -10;
		mf.addFlexibleComponent(flex);

		mf.setVisible(true);
		mf.setDefaultCloseOperation(FFrame.EXIT_ON_CLOSE);
	}
}