package de.miij.ui.folderchooser;

import de.miij.layout.FlexConstraint;
import de.miij.layout.FlexLayout;
import de.miij.ui.comp.DecoratedDialog;
import sun.awt.shell.ShellFolder;
import sun.swing.FilePane;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.filechooser.FileView;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * <p>Titre : JFolderChooseer</p>
 * <p/>
 * <p>Description : A replacement for JFileChooser to select Directory</p>
 * <p/>
 * <p>Copyright : Copyright (c) 2006</p>
 * <p/>
 * <p>Soci�t� : PSafix.org</p>
 *
 * @author Michel PRUNET
 * @version 1.0
 */
public class JFolderChooser extends JPanel implements TreeSelectionListener {
	public final static int APPROVE_OPTION = JFileChooser.APPROVE_OPTION;
	public final static int CANCEL_OPTION = JFileChooser.CANCEL_OPTION;
	public final static int ERROR_OPTION = JFileChooser.ERROR_OPTION;
	public final static int SAVE_DIALOG = JFileChooser.SAVE_DIALOG;
	public final static int OPEN_DIALOG = JFileChooser.OPEN_DIALOG;

	private FolderTreeModel model;
	private FileFilter showFilter = new DirectoryFileFilter();
	private FileFilter acceptFilter;
	private boolean acceptFilterOnly = false;
	private FolderJTree jTree1 = new FolderJTree();
	private FileView fileView = new SystemFileView();
	private FolderTreeCellRenderer renderer = new FolderTreeCellRenderer(fileView);
	private FolderTreeCellEditor editor = new FolderTreeCellEditor(fileView, jTree1, renderer);
	private JScrollPane scroll = new JScrollPane(jTree1);
	private GridBagLayout gridBagLayout1 = new GridBagLayout();
	private String newFolderErrorText;
	private String newFolderErrorSeparator;

	/**
	 * Property
	 */
	private String title;
	private JDialog dialog;
	private int returnValue;
	private int dialogType;
	private ShellFolder currentFolder;
	private JButton bNewFolder = new JButton();
	private JButton bYes = new JButton();
	private JButton bCancel = new JButton();

	public JFolderChooser() {
		try {
			jbInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		this.setLayout(gridBagLayout1);
		bYes.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bYes_actionPerformed(e);
			}
		});
		bCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bCancel_actionPerformed(e);
			}
		});
		this.add(scroll, new GridBagConstraints(0, 0, 3, 1, 1.0, 1.0
				, GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(2, 2, 2, 2), 0, 0));
		this.add(bCancel, new GridBagConstraints(2, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 2, 0), 0, 0));
		this.add(bNewFolder, new GridBagConstraints(0, 1, 1, 1, 1.0, 0.0
				, GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 2, 2, 10), 0, 0)); //      File[] fic = File.listRoots();//FileSystemView.getFileSystemView().getRoots();
		this.add(bYes, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
				, GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 0, 2, 2), 0, 0));
		File[] fic = (File[]) ShellFolder.get("fileChooserShortcutPanelFolders");
		jTree1.setCellRenderer(renderer);
		jTree1.setCellEditor(editor);
		jTree1.setRootVisible(false);
		bNewFolder.addActionListener(new NewFolderAction());
		jTree1.setEditable(true);
		bYes.setEnabled(false);
		jTree1.addTreeSelectionListener(this);
	}

	/**
	 * Called whenever the value of the selection changes.
	 *
	 * @param e the event that characterizes the change.
	 */
	public void valueChanged(TreeSelectionEvent e) {
		TreePath tp = jTree1.getSelectionPath();
		boolean ok = tp != null && tp.getLastPathComponent() != null;
		if (ok) {
			ShellFolder f = (ShellFolder) tp.getLastPathComponent();
			if (!accept(f)) {
				ok = false;
			}
		}
		bYes.setEnabled(ok);
	}

	/**
	 * This method is called when a file is selected in the tree
	 *
	 * @param sf ShellFolder
	 * @return boolean
	 */
	protected boolean accept(ShellFolder sf) {
		boolean b;
		b = (acceptFilter == null) ? true : acceptFilter.accept(sf);
		if (!acceptFilterOnly) {
//         System.out.println(sf.getName());
//         System.out.println("sf.isFileSystem()="+sf.isFileSystem()+" sf.isDirectory()="+sf.isDirectory()+" sf.exists()="+sf.exists()+" sf.canRead()="+sf.canRead()+" sf.canWrite()="+sf.canWrite());
			b = sf.isFileSystem() && sf.isDirectory() && sf.exists() && sf.canRead();
//         if (dialogType==OPEN_DIALOG){
//            b = sf.isFileSystem()&&sf.isDirectory()&&sf.exists()&&sf.canRead();
//         } else if (dialogType==SAVE_DIALOG){
//            b = sf.isFileSystem()&&sf.isDirectory()&&sf.exists()&&sf.canRead()&&sf.canWrite();
//         }
		}
		return b;
	}

	/**
	 * Set the accept filter. The file is selectable only if the filter return true
	 *
	 * @param acceptFilter     FileFilter
	 * @param acceptFilterOnly boolean To disable the default behaviour
	 */
	public void setAcceptFilter(FileFilter acceptFilter, boolean acceptFilterOnly) {
		this.acceptFilter = acceptFilter;
		this.acceptFilterOnly = acceptFilterOnly;
	}

	/**
	 * Set the accept filter. The file is selectable only if the filter return true
	 *
	 * @param acceptFilter FileFilter
	 */
	public void setAcceptFilter(FileFilter acceptFilter) {
		setAcceptFilter(acceptFilter, false);
	}

	protected class NewFolderAction extends AbstractAction {
		protected NewFolderAction() {
			super(FilePane.ACTION_NEW_FOLDER);
		}

		public void actionPerformed(ActionEvent e) {
			FileSystemView fsv = FileSystemView.getFileSystemView();
//         File currentDirectory = fc.getCurrentDirectory();

			TreePath tp = jTree1.getSelectionPath();
			if (tp != null && tp.getLastPathComponent() != null) {
				ShellFolder newFolder = null;
				currentFolder = (ShellFolder) tp.getLastPathComponent();
				try {
					newFolder = ShellFolder.getShellFolder(fsv.createNewFolder(currentFolder));
				} catch (IOException exc) {
					JOptionPane.showMessageDialog(JFolderChooser.this,
							newFolderErrorText + newFolderErrorSeparator,
							newFolderErrorText, JOptionPane.ERROR_MESSAGE);
					return;
				}
				model.addFils(JFolderChooser.this, tp, currentFolder, newFolder);
				jTree1.setSelectionPathLater(tp.pathByAddingChild(newFolder), true);

			}
		}
	}


	/**
	 * Build system file tree
	 *
	 * @return HashMap
	 */
	private HashMap<ShellFolder, ArrayList<ShellFolder>> generateMap() {
		HashMap<ShellFolder, ArrayList<ShellFolder>> map = new HashMap<ShellFolder, ArrayList<ShellFolder>>();
		for (File f : (File[]) ShellFolder.get("fileChooserComboBoxFolders")) {
			generateTreeForAFile(map, f);
		}
		if (currentFolder == null) {
			try {
				currentFolder = ShellFolder.getShellFolder(FileSystemView.getFileSystemView().getDefaultDirectory());
			} catch (FileNotFoundException ex) {
			}
		}

		ShellFolder sf = (ShellFolder) currentFolder;
		if (sf != null) {
			fillCurrentDirectory(map, sf);
		}

		return map;
	}

	private void fillCurrentDirectory(HashMap<ShellFolder, ArrayList<ShellFolder>> map, ShellFolder sf) {
		boolean trouve = false;
		ShellFolder pere = null;
		try {
			File file = sf.getParentFile();
			if (file != null) {
				pere = ShellFolder.getShellFolder(sf.getParentFile());
			}
		} catch (FileNotFoundException ex) {
		}
		if (pere != null) {
			ArrayList<ShellFolder> freres = map.get(pere);
			if (freres == null || freres.size() == 0) {
				freres = FolderTreeModel.convert(pere.listFiles(showFilter));
				map.put(pere, freres);
			} else {
				trouve = true;
			}
			if (!freres.contains(sf)) {
				freres.add(sf);
			}
			if (!trouve) {
				fillCurrentDirectory(map, pere);
			}
		}

	}

	private void generateTreeForAFile(HashMap<ShellFolder, ArrayList<ShellFolder>> map, File f) {
		try {
			ShellFolder courant = ShellFolder.getShellFolder(f);
			if (courant.getParentFile() != null) {
				ShellFolder pere = ShellFolder.getShellFolder(courant.getParentFile());
				ArrayList<ShellFolder> freres = map.get(courant.getParentFile());
				if (freres == null) {
					freres = FolderTreeModel.convert(pere.listFiles(showFilter));
					map.put(pere, freres);
				}
				if (!freres.contains(courant)) {
					freres.add(courant);
				}
			}

			ArrayList<ShellFolder> fils = map.get(courant);
			if (fils == null) {
				fils = new ArrayList<ShellFolder>();
				map.put(courant, fils);
			}
		} catch (FileNotFoundException ex) {
		}
	}

	/**
	 * This method must be called to open the dialog
	 * With this option all the folder are selectable except if a file Filter is defined
	 *
	 * @param parent Component
	 * @return int
	 */
	public int showOpenDialog(Component parent) {
		dialogType = OPEN_DIALOG;
		return showDialog(parent);
	}

	/**
	 * This method must be called to open the dialog
	 * With this option only writable folders are selectable except if a file Filter is defined
	 *
	 * @param parent Component
	 * @return int
	 */
	public int showSaveDialog(Component parent) {
		dialogType = SAVE_DIALOG;
		return showDialog(parent);
	}

	/**
	 * Pops a custom directory chooser dialog
	 * Example
	 * <pre>
	 *    JFolderChooser chooser = new JFolderChooser(null);
	 *    chooser.showDialog(parentFrame, null);
	 * </pre>
	 * <p/>
	 * <p/>
	 * <p/>
	 * The <code>parent</code> argument determines two things:
	 * the frame on which the open dialog depends and
	 * the component whose position the look and feel
	 * should consider when placing the dialog.  If the parent
	 * is a <code>Frame</code> object (such as a <code>JFrame</code>)
	 * then the dialog depends on the frame and
	 * the look and feel positions the dialog
	 * relative to the frame (for example, centered over the frame).
	 * If the parent is a component, then the dialog
	 * depends on the frame containing the component,
	 * and is positioned relative to the component
	 * (for example, centered over the component).
	 * If the parent is <code>null</code>, then the dialog depends on
	 * no visible window, and it's placed in a
	 * look-and-feel-dependent position
	 * such as the center of the screen.
	 *
	 * @param parent            the parent component of the dialog;
	 *                          can be <code>null</code>
	 * @return the return state of the file chooser on popdown:
	 * <ul>
	 * <li>CANCEL_OPTION
	 * <li>APPROVE_OPTION
	 * <li>ERROR_OPTION if an error occurs or the
	 * dialog is dismissed
	 * </ul>
	 * @throws HeadlessException if GraphicsEnvironment.isHeadless()
	 *                           returns true.
	 * @see java.awt.GraphicsEnvironment#isHeadless
	 */
	private int showDialog(Component parent) throws HeadlessException {
		i18n();
		dialog = createDialog(parent);
		dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				returnValue = CANCEL_OPTION;
			}
		});
		returnValue = ERROR_OPTION;

		rescanCurrentDirectory();
		if (currentFolder != null) {
			TreePath path = model.getTreePath(currentFolder);
			jTree1.expandPath(path);
			jTree1.setSelectionPathLater(path, false);
		}

		dialog.setVisible(true);
		dialog.dispose();
		model.clear();
		dialog = null;
		return returnValue;
	}

	/**
	 * Tells the UI to rescan its files list from the current directory.
	 */
	public void rescanCurrentDirectory() {
		if (model != null) {
			model.clear();
		}
		model = new FolderTreeModel(showFilter, fileView, generateMap());
		jTree1.setModel(model);
	}

	public void setFileView(FileView fv) {
		this.fileView = fv;
		renderer.setFileView(fv);
		editor.setFileView(fv);
		if (model != null) {
			model.setFileView(fv);
		}
	}

	private Color DialogBackgroundColor;

	public void setDialogBackground(Color c) {
		DialogBackgroundColor = c;
	}

	/**
	 * Creates and returns a new <code>JDialog</code> wrapping
	 * <code>this</code> centered on the <code>parent</code>
	 * in the <code>parent</code>'s frame.
	 * This method can be overriden to further manipulate the dialog,
	 * to disable resizing, set the location, etc. Example:
	 * <pre>
	 *     class MyFileChooser extends JFolderChooser {
	 *         protected JDialog createDialog(Component parent) throws HeadlessException {
	 *             JDialog dialog = super.createDialog(parent);
	 *             dialog.setLocation(300, 200);
	 *             dialog.setResizable(false);
	 *             return dialog;
	 *         }
	 *     }
	 * </pre>
	 *
	 * @param parent the parent component of the dialog;
	 *               can be <code>null</code>
	 * @return a new <code>JDialog</code> containing this instance
	 * @throws HeadlessException if GraphicsEnvironment.isHeadless()
	 *                           returns true.
	 * @see java.awt.GraphicsEnvironment#isHeadless
	 */
	protected JDialog createDialog(Component parent) throws HeadlessException {
		DecoratedDialog dialog;
		Window window = getWindowForComponent(parent);
		if (window instanceof Frame) {
			dialog = new DecoratedDialog((Frame) window, false);
		} else {
			dialog = new DecoratedDialog((Dialog) window, false);
		}
		dialog.setTitle(title);
		dialog.setModal(true);

		dialog.setTitleBarBackground(DialogBackgroundColor);
		dialog.setBackground(DialogBackgroundColor);
		setBackground(DialogBackgroundColor);
		jTree1.setBackground(DialogBackgroundColor);
		bCancel.setBackground(DialogBackgroundColor);
		bYes.setBackground(DialogBackgroundColor);
		bNewFolder.setBackground(DialogBackgroundColor);
		scroll.setBackground(DialogBackgroundColor);
		dialog.getContentPane().setBackground(DialogBackgroundColor);
		ActionListener escListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				bCancel_actionPerformed(e);
			}
		};
		ActionListener enterListener = new
				ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						bYes_actionPerformed(e);
					}
				};
		dialog.getRootPane().registerKeyboardAction(escListener, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
		dialog.getRootPane().registerKeyboardAction(enterListener, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);

//		dialog.setComponentOrientation(this.getComponentOrientation());

//		Container contentPane = dialog.getContentPane();
		JPanel pnl = new JPanel();
		pnl.setLayout(new BorderLayout());
		pnl.add(this, BorderLayout.CENTER);
		dialog.setLayout(new FlexLayout());
		dialog.add(pnl, new FlexConstraint().left(0).top(0).bottom(0).right(0));

//		if (JDialog.isDefaultLookAndFeelDecorated()) {
//			boolean supportsWindowDecorations =
//					UIManager.getLookAndFeel().getSupportsWindowDecorations();
//			if (supportsWindowDecorations) {
//				dialog.getRootPane().setWindowDecorationStyle(JRootPane.FILE_CHOOSER_DIALOG);
//			}
//		}
//		dialog.pack();
		dialog.setSize(350, 500);
		dialog.setLocationRelativeTo(parent);

		return dialog;
	}


	/**
	 * Returns the specified component's toplevel <code>Frame</code> or
	 * <code>Dialog</code>.
	 *
	 * @param parentComponent the <code>Component</code> to check for a
	 *                        <code>Frame</code> or <code>Dialog</code>
	 * @return the <code>Frame</code> or <code>Dialog</code> that
	 * contains the component, or the default
	 * frame if the component is <code>null</code>,
	 * or does not have a valid
	 * <code>Frame</code> or <code>Dialog</code> parent
	 * @throws HeadlessException if
	 *                           <code>GraphicsEnvironment.isHeadless</code> returns
	 *                           <code>true</code>
	 * @see java.awt.GraphicsEnvironment#isHeadless
	 */
	static Window getWindowForComponent(Component parentComponent)
			throws HeadlessException {
		if (parentComponent == null)
			return JOptionPane.getRootFrame();
		if (parentComponent instanceof Frame || parentComponent instanceof Dialog)
			return (Window) parentComponent;
		return getWindowForComponent(parentComponent.getParent());
	}

	public void setTitle(String t) {
		title = t;
	}

	/**
	 * Set the filter which select the File Displayed
	 *
	 * @param showFilter FileFilter
	 */
	public void setShowFilter(FileFilter showFilter) {
		this.showFilter = showFilter;
	}


	/**
	 * Set the current directory
	 *
	 * @param dir File
	 */
	public void setCurrentDirectory(File dir) {
		try {
			currentFolder = ShellFolder.getShellFolder(dir);
		} catch (FileNotFoundException ex) {
		}
	}

	private void i18n() {
		Locale loc = Locale.getDefault();
		if (title == null) {
			title = UIManager.getString("FileChooser.openDialogTitleText", loc);
		}
		bCancel.setText(UIManager.getString("FileChooser.cancelButtonText", loc));
		bCancel.setToolTipText(UIManager.getString("FileChooser.cancelButtonToolTipText", loc));
		bYes.setText(UIManager.getString("FileChooser.openButtonText", loc));
		bYes.setToolTipText(UIManager.getString("FileChooser.openButtonToolTipText", loc));
		bNewFolder.setText(UIManager.getString("FileChooser.directoryDescriptionText", loc));
		newFolderErrorText = UIManager.getString("FileChooser.newFolderErrorText", loc);
		newFolderErrorSeparator = UIManager.getString("FileChooser.newFolderErrorSeparator", loc);
	}


	/**
	 * Called when bYes is pressed
	 *
	 * @param e ActionEvent
	 */
	public void bYes_actionPerformed(ActionEvent e) {
		TreePath tp = jTree1.getSelectionPath();
		if (tp != null && tp.getLastPathComponent() != null) {
			ShellFolder newFolder = null;
			currentFolder = (ShellFolder) tp.getLastPathComponent();
			returnValue = APPROVE_OPTION;
			dialog.setVisible(false);
		} else {
			bYes.setEnabled(false);
		}
	}

	/**
	 * Called when bCancel is pressed
	 *
	 * @param e ActionEvent
	 */
	public void bCancel_actionPerformed(ActionEvent e) {
		returnValue = CANCEL_OPTION;
		dialog.setVisible(false);
	}

	/**
	 * Return the selected file.
	 * You must check return value.
	 */
	public File getSelectedFile() {
		return currentFolder;
	}

	/**
	 * Set the current directory
	 *
	 * @param fichier File
	 */
	public void setSelectedFile(File fichier) {
		setCurrentDirectory(fichier);
	}


	public static void main(String[] args) {
//      try
//      {
//         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//      } catch(Exception ex)
//      {
//      }
		JFolderChooser ch = new JFolderChooser();
		ch.setCurrentDirectory(new File("/C:/"));
		int i = ch.showSaveDialog(null);
		System.out.println(i);
		System.out.println(ch.getSelectedFile());
	}
}

