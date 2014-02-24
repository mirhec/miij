package de.miij.ui.folderchooser;

import java.io.File;
import javax.swing.*;
import java.util.*;
import java.awt.GridBagLayout;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.filechooser.*;

/**
 * <p>Titre : JFolderChooseer</p>
 *
 * <p>Description : A replacement for JFileChooser to select Directory</p>
 *
 * <p>Copyright : Copyright (c) 2006</p>
 *
 * <p>Soci�t� : PSafix.org</p>
 *
 * @author Michel PRUNET
 * @version 1.0
 */
public class DemoJFolderChooser extends JFrame
{
   private JComboBox jComboBox1 = new JComboBox();
   private GridBagLayout gridBagLayout1 = new GridBagLayout();
   private JLabel lab = new JLabel();
   private JButton bOk = new JButton();
   private JLabel labFV = new JLabel();
   private JComboBox jComboBox2 = new JComboBox();

   public DemoJFolderChooser(){
      try
      {
         jbInit();
      } catch(Exception ex)
      {
         ex.printStackTrace();
      }
   }

   public void init(FileView fv){
      JFolderChooser ch = new JFolderChooser();
      ch.setFileView(fv);
      ch.setCurrentDirectory(new File("/C:/"));
      int i = ch.showOpenDialog(this);
   }

   private static class LookAndFeelName{
      private UIManager.LookAndFeelInfo lf;

      public LookAndFeelName(UIManager.LookAndFeelInfo lf){
         this.lf=lf;
      }

      public String toString(){
         return lf.getName();
      }

      public UIManager.LookAndFeelInfo getLookAndFeel(){
         return lf;
      }
   }

   private void jbInit() throws Exception
   {
      this.getContentPane().setLayout(gridBagLayout1);
      lab.setText("Look And Feel : ");
      bOk.setText("Show JFolderChooser");
      bOk.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            bOk_actionPerformed(e);
         }
      });
      labFV.setText("FileView : ");
      this.getContentPane().add(lab, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0, 10, 0, 0), 0, 0));
      this.getContentPane().add(bOk, new GridBagConstraints(1, 2, 1, 1, 0.0, 0.0
              , GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 0, 10, 10), 0, 0));
      this.getContentPane().add(jComboBox1, new GridBagConstraints(1, 0, 1, 1, 1.0, 0.0
              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(10, 0, 0, 10), 0, 0));
      this.getContentPane().add(jComboBox2, new GridBagConstraints(1, 1, 1, 1, 0.0, 0.0
              , GridBagConstraints.WEST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      this.getContentPane().add(labFV, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0
              , GridBagConstraints.EAST, GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
      UIManager.LookAndFeelInfo[] fs = UIManager.getInstalledLookAndFeels();
      setTitle("Choose your look and feel");
      LookAndFeelName[] lfn = new LookAndFeelName[fs.length];
      for (int i=0;i<fs.length;i++){
         lfn[i] = new LookAndFeelName(fs[i]);
      }
      jComboBox1.setModel(new DefaultComboBoxModel(lfn));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      jComboBox2.setModel(new DefaultComboBoxModel(new String[]{"Default : SystemFileView","LookAndFeelFileView"}));
   }
   public void bOk_actionPerformed(ActionEvent e)
   {
      try
      {
         LookAndFeelName lookAndFeel = (LookAndFeelName) jComboBox1.getSelectedItem();
         UIManager.setLookAndFeel(lookAndFeel.getLookAndFeel().getClassName());
         SwingUtilities.updateComponentTreeUI(this);

         if (jComboBox2.getSelectedItem().equals("LookAndFeelFileView")){
            init(new LookAndFeelFileView());
         } else {
            init(new SystemFileView());
         }
      } catch(Exception ex)
      {
         ex.printStackTrace();
      }
   }


   public static void main(String[] args)
   {
      Locale.setDefault(Locale.ENGLISH);
      DemoJFolderChooser f = new DemoJFolderChooser();
      f.pack();
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      Dimension dc = f.getSize();
      f.setBounds((int)((d.getWidth()/2)-(dc.getWidth()/2)),
                  (int)((d.getHeight()/2)-(dc.getHeight()/2)),
                  (int)dc.getWidth(),(int)dc.getHeight());

      f.setVisible(true);
//      f.init();
   }

}
