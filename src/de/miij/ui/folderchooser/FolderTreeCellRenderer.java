package de.miij.ui.folderchooser;

import javax.swing.tree.*;
import javax.swing.JTree;
import java.awt.Component;
import java.io.*;

import sun.awt.shell.*;

import javax.swing.filechooser.*;


/**
 *
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
public class FolderTreeCellRenderer extends DefaultTreeCellRenderer
{
   private FileView fv;

   public FolderTreeCellRenderer(FileView fv)
   {
      this.fv=fv;
   }

   public Component getTreeCellRendererComponent(JTree tree, Object value,
                   boolean sel,
                   boolean expanded,
                   boolean leaf, int row,
                   boolean hasFocus) {
      super.getTreeCellRendererComponent(tree,value,sel,expanded,leaf,row,hasFocus);
      if (value instanceof ShellFolder){
         ShellFolder f = (ShellFolder)value;
         setIcon(fv.getIcon((File)value));
         setText(fv.getName(f));
      }
	  if(!sel)
	    setOpaque(true);
	  else
	    setOpaque(false);
      return this;
   }

   public void setFileView(FileView fv){
      this.fv=fv;
   }

}
