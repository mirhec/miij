package de.miij.ui.folderchooser;
import javax.swing.*;
import javax.swing.tree.TreePath;
import javax.swing.tree.*;

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
public class FolderJTree extends JTree
{
   private SelectionThread st = new SelectionThread();


   public FolderJTree()
   {
      getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
   }

   public void setSelectionPathLater(final TreePath path, boolean edit){
       st.setPath(path, edit);
       SwingUtilities.invokeLater(st);
   }

   private class SelectionThread implements Runnable{
       private TreePath path;
       private boolean edit;
       public void setPath(TreePath tp){
           path=tp;
           edit=false;
       }

       public void setPath(TreePath tp, boolean edit){
           path=tp;
           this.edit=edit;
       }

       public void run(){
           TreePath courant = path;
           if (courant!=null){
               setExpandedState(courant,false);
               setSelectionPath(courant);
               scrollPathToVisible(courant);
               if (edit){
                   startEditingAtPath(courant);
               }
               path = null;
           }
       }
   }

}
