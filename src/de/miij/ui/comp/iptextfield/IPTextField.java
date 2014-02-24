package de.miij.ui.comp.iptextfield;

/**
 * Created by Mirko Hecky on 29.01.14.
 */
/**
 * diplays an  version text field
 */

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class IPTextField extends GridBagPanel {


	private static final long serialVersionUID = 1L;

	/**
	 * a text field for each byte
	 */
	private JTextField[] textFields;

	/**
	 * dots between text fields
	 */
	private JLabel[] dotsLabels;


	/**
	 * used to calculate enable/disable color; never shown
	 */
	private static JTextField sampleTextField = new JTextField();

	/**
	 * listen to changes in the byte fields
	 */
	private MyDocumentListener documentListener;

	/**
	 * list of key listeners
	 */
	private List<KeyListener> keyListenersList;

	/**
	 * List of Focus Adapter that select all data in JTextFiled during action
	 * */
	private List<FocusAdapter> focusAdapterList;

	/**
	 * list of key listeners
	 */
	private List<FocusListener> focusListenersList;

	private int maxHeight = 0;

	public IPTextField() {
		this(4);
	}

	/**
	 * @param byteCount
	 *            number of bytes to display
	 */
	private IPTextField(int byteCount) {
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(textFields.length > 0 && textFields[0] != null) textFields[0].requestFocus();
			}
		});

		textFields = new JTextField[byteCount];
		for (int i = 0; i < textFields.length; i++) {
			textFields[i] = new JTextField(3);
			textFields[i].setOpaque(false);
		}

		//layout
		//constraints.insets = new Insets(0, 0, 0, 0);

		List<JLabel> dotsLabelsList = new ArrayList<JLabel>();

		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.setHorizontalAlignment(JTextField.CENTER);
			Document document = DocumentsFactory.createIntDocument(255);
			textField.setDocument(document);

			if (i < textFields.length-1) {
				add(textField, i * 2, 0, 1, 1);
				if (textField.getPreferredSize().height > maxHeight)
					maxHeight = textField.getPreferredSize().height;
				JLabel label = new JLabel(".");



				add(label, (i * 2) + 1, 0, 1, 1);
				if (label.getPreferredSize().height > maxHeight)
					maxHeight = label.getPreferredSize().height;
				dotsLabelsList.add(label);
			} else
				add(textField, i * 2, 0, 1, 1);

		}

		//dotsLabels = new JLabel[dotsLabelsList.size()];
		dotsLabels = new JLabel[dotsLabelsList.size()];


		dotsLabels = dotsLabelsList.toArray(dotsLabels);

		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.setBorder(BorderFactory.createEmptyBorder());
		}

		//init
		Color backgroundColor = UIManager.getColor("TextField.background");
		setBackground(backgroundColor);
		Border border = UIManager.getBorder("TextField.border");
		setBorder(border);

		//register listeners
		for (int i = 1; i < textFields.length; i++) {
			JTextField field = textFields[i];
			field.addKeyListener(new BackKeyAdapter());
		}

		documentListener = new MyDocumentListener();
		for (int i = 0; i < textFields.length - 1; i++) {
			JTextField field = textFields[i];
			field.getDocument().addDocumentListener(documentListener);
			field.addKeyListener(new ForwardKeyAdapter());
		}

		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.addKeyListener(new MyKeyListener());
		}

		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.addFocusListener(new MyFocusAdapter());
		}

//		for (int i = 0; i < textFields.length; i++) {
//			JTextField textField = textFields[i];
//			textField.addFocusListener(new MyFocusAdapter());
//		}

		keyListenersList = new ArrayList<KeyListener>();
		focusListenersList = new ArrayList<FocusListener>();
		focusAdapterList = new ArrayList<FocusAdapter>();
	}

	public synchronized void addKeyListener(KeyListener l) {
		super.addKeyListener(l);
		keyListenersList.add(l);
	}

	public synchronized void addFocusListener(FocusListener l) {
		super.addFocusListener(l);
		if (focusListenersList != null)
			focusListenersList.add(l);
	}

	public synchronized void removeKeyListener(KeyListener l) {
		super.removeKeyListener(l);
		if (focusListenersList != null)
			keyListenersList.remove(l);
	}

	public synchronized void removeFocusListener(FocusListener l) {
		super.removeFocusListener(l);
		keyListenersList.remove(l);
	}

	public void setEnabled(boolean b) {
		super.setEnabled(b);
		sampleTextField.setEnabled(b);
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.setEnabled(b);
		}

		for (int i = 0; i < dotsLabels.length; i++) {
			JLabel dotsLabel = dotsLabels[i];
			dotsLabel.setEnabled(b);
		}

		setBackground(sampleTextField.getBackground());
		setForeground(sampleTextField.getForeground());
		setBorder(sampleTextField.getBorder());

	}

	public void requestFocus() {
		super.requestFocus();
		textFields[0].requestFocus();
	}

	public void setEditable(boolean b) {
		sampleTextField.setEditable(b);
		setBackground(sampleTextField.getBackground());
		setForeground(sampleTextField.getForeground());
		setBorder(sampleTextField.getBorder());
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.setEditable(b);
		}

		for (int i = 0; i < dotsLabels.length; i++) {
			JLabel dotsLabel = dotsLabels[i];


			dotsLabel.setForeground(sampleTextField.getForeground());
		}
	}

	public boolean isFieldEmpty() {
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			String sCell = textField.getText().trim();
			if (!(sCell.equals("")))
				return false;
		}
		return true;
	}




	public Dimension getPreferredSize() {
		if (super.getPreferredSize().height > maxHeight)
			maxHeight = super.getPreferredSize().height;
		return new Dimension(super.getPreferredSize().width, maxHeight);
	}





	/**
	 * clears current text in text fiekd
	 */
	private void reset() {
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.getDocument().removeDocumentListener(documentListener);
			textField.setText("");
			textField.getDocument().addDocumentListener(documentListener);
		}
	}



	public static void main(String[] args) {

		JFrame frame = new JFrame("test");
		IPTextField ipTextField = new IPTextField();
		ipTextField.setText("9.1.23.1479");
		frame.getContentPane().setLayout(new FlowLayout());
		frame.getContentPane().add(ipTextField);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}


	public void setText(String version) {
		if (version == null || "".equals(version) || "null".equals(version))
			reset();
		else {
			setVer(version.split("[.]"));
		}
	}


	private void setVer(String[] ver) {
		if (ver == null) {
			reset();
			return;
		}

		Enumeration<String> enumeration =  Collections.enumeration(Arrays.asList(ver));
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			String s = (String) enumeration.nextElement();
			textField.getDocument().removeDocumentListener(documentListener);
			textField.setText(s);
			textField.getDocument().addDocumentListener(documentListener);
		}
	}

	public void setToolTipText(String toolTipText) {
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];
			textField.setToolTipText(toolTipText);
		}
	}



	private class MyDocumentListener implements DocumentListener {


		@Override
		public void insertUpdate(DocumentEvent e) {
			Document document = e.getDocument();
			try {
				JTextField textField = (JTextField) FocusManager.getCurrentManager().getFocusOwner();

				String s = document.getText(0, document.getLength());

				if (s.length() >= textField.getColumns()){ // && textField.getCaretPosition() == 2) {
					textField.transferFocus();
				}

			} catch (BadLocationException e1) {
				e1.printStackTrace();
				return;
			}

		}

		public void removeUpdate(DocumentEvent e) {
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			//          Document document = e.getDocument();
			//          try {
			//              Component component = FocusManager.getCurrentManager().getFocusOwner();
			//              String s = document.getText(0, document.getLength());
			//
			//              // get selected integer
			//              int valueInt = Integer.parseInt(s);
			//
			//              if (valueInt > 25) {
			//                  component.transferFocus();
			//              }
			//
			//          } catch (BadLocationException e1) {
			//              e1.printStackTrace();
			//              return;
			//          }
		}
	}

	private class BackKeyAdapter extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			JTextField textField = (JTextField) e.getComponent();
			if (textField.getCaretPosition() == 0
					&& KeyEvent.VK_LEFT == e.getKeyCode()
					&& e.getModifiers() == 0)
				textField.transferFocusBackward();
			if (textField.getCaretPosition() == 0
					&& KeyEvent.VK_BACK_SPACE == e.getKeyCode()
					&& e.getModifiers() == 0) {
				textField.transferFocusBackward();
			}
		}
	}

	private class ForwardKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			JTextField textField = (JTextField) e.getComponent();

			if (KeyEvent.VK_RIGHT == e.getKeyCode() && e.getModifiers() == 0) {
				int length = textField.getText().length();
				int caretPosition = textField.getCaretPosition();

				if (caretPosition == length) {
					textField.transferFocus();
					e.consume();
				}
			} else if (e.getKeyChar() == '.' &&
					textField.getText().trim().length() != 0) {
				textField.setText(textField.getText().trim());
				textField.transferFocus();
				e.consume();
			}
		}
	}

	/**
	 * @return current text in ip text field
	 */
	public String getText()  {
		StringBuffer buffer = new StringBuffer();
		String ipResult;
		for (int i = 0; i < textFields.length; i++) {
			JTextField textField = textFields[i];

			if(textField.getText().trim().equals("")){
				return "";
			}

			buffer.append(Integer.parseInt(textField.getText()));
			if (i < textFields.length - 1){
				buffer.append('.');
			}
		}
		ipResult = buffer.toString();

		return ipResult;
	}

	/**
	 * general purpose key listener
	 */
	private class MyKeyListener implements KeyListener {
		public void keyPressed(KeyEvent e) {
			for (int i = 0; i < keyListenersList.size(); i++) {
				KeyListener keyListener = keyListenersList.get(i);
				keyListener.keyPressed(new KeyEvent(IPTextField.this,
						e.getID(), e.getWhen(), e.getModifiers(), e
						.getKeyCode(), e.getKeyChar(), e
						.getKeyLocation()));
			}
		}

		public void keyReleased(KeyEvent e) {
			for (int i = 0; i < keyListenersList.size(); i++) {
				KeyListener keyListener = keyListenersList.get(i);
				keyListener.keyReleased(new KeyEvent(IPTextField.this, e
						.getID(), e.getWhen(), e.getModifiers(),
						e.getKeyCode(), e.getKeyChar(), e.getKeyLocation()));
			}
		}

		public void keyTyped(KeyEvent e) {
			for (int i = 0; i < keyListenersList.size(); i++) {
				KeyListener keyListener = keyListenersList.get(i);
				keyListener.keyTyped(new KeyEvent(IPTextField.this, e.getID(),
						e.getWhen(), e.getModifiers(), e.getKeyCode(), e
						.getKeyChar(), e.getKeyLocation()));
			}
		}
	}

	private class MyFocusAdapter extends FocusAdapter {

		public void focusGained(FocusEvent e) {
			for (int i = 0; i < focusListenersList.size(); i++) {
				FocusListener focusListener = focusListenersList.get(i);
				focusListener.focusGained(new FocusEvent(
						IPTextField.this,
						e.getID(),
						e.isTemporary(),
						e.getOppositeComponent()
				));
			}

			if(e.getComponent() instanceof javax.swing.JTextField){
				highlightText((JTextField)e.getSource());
			}
		}

		public void focusLost(FocusEvent e) {
			for (int i = 0; i < focusListenersList.size(); i++) {
				FocusListener focusListener = focusListenersList.get(i);
				focusListener.focusLost(new FocusEvent(
						IPTextField.this,
						e.getID(),
						e.isTemporary(),
						e.getOppositeComponent()
				));
			}
		}

		public void highlightText(javax.swing.JTextField ctr){
			//ctr.setSelectionColor(Color.BLUE);
			//ctr.setSelectedTextColor(Color.WHITE);
			ctr.setSelectionStart(0);
			ctr.setSelectionEnd(ctr.getText().length());
			System.out.println(ctr.getText());

		}
	}


}