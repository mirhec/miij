package de.miij.ui.folderchooser;

import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.*;
import java.awt.Component;
import javax.swing.filechooser.*;
import java.io.*;
import javax.swing.tree.*;

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
public class FolderTreeCellEditor extends DefaultTreeCellEditor
{
   private FileView vue;
   public FolderTreeCellEditor(FileView vue, JTree tree, DefaultTreeCellRenderer renderer){
      super(tree, renderer);
      this.vue=vue;
   }

   public void setFileView(FileView vue){
      this.vue=vue;
   }



   public Component getTreeCellEditorComponent(JTree tree, Object value,
               boolean isSelected, boolean expanded,
               boolean leaf, int row){

      return super.getTreeCellEditorComponent(tree,vue.getName((File)value),isSelected,expanded,leaf,row);
   }

}
