package de.miij.ui.folderchooser;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;
import sun.awt.shell.*;
import java.awt.*;
import javax.swing.filechooser.FileView;

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
public class FolderTreeModel implements TreeModel
{
   private FileFilter showFilter;
   private FileView fv;
   private EventListenerList listenerList = new EventListenerList();
   private ArrayList<ShellFolder> racines;
   private Map<ShellFolder,ArrayList<ShellFolder>> cache;

   public FolderTreeModel(FileFilter showFilter, FileView fv, Map<ShellFolder,ArrayList<ShellFolder>> cache) {
      this.showFilter=showFilter;
      this.fv=fv;
      racines = new ArrayList<ShellFolder>();
      Set<ShellFolder> set = cache.keySet();
      for(ShellFolder sf:set){
         if (sf.getParentFile()==null){
            racines.add(sf);
         }
      }
      this.cache=cache;
   }

   public void setFileView(FileView fv){
      this.fv=fv;
   }

   /**
    * Returns the root of the tree.  Returns <code>null</code>
    * only if the tree has no nodes.
    *
    * @return  the root of the tree
    */
   public Object getRoot(){
       return this;
   }


   /**
    * Returns the child of <code>parent</code> at index <code>index</code>
    * in the parent's
    * child array.  <code>parent</code> must be a node previously obtained
    * from this data source. This should not return <code>null</code>
    * if <code>index</code>
    * is a valid index for <code>parent</code> (that is <code>index >= 0 &&
    * index < getChildCount(parent</code>)).
    *
    * @param   parent  a node in the tree, obtained from this data source
    * @return  the child of <code>parent</code> at index <code>index</code>
    */
   public Object getChild(Object parent, int index){
      if (parent==this){
         return racines.get(index);
      } else {
         ShellFolder fic =(ShellFolder)parent;
         return listerFils(fic).get(index);
      }
   }


   /**
    * Returns the number of children of <code>parent</code>.
    * Returns 0 if the node
    * is a leaf or if it has no children.  <code>parent</code> must be a node
    * previously obtained from this data source.
    *
    * @param   parent  a node in the tree, obtained from this data source
    * @return  the number of children of the node <code>parent</code>
    */
   public int getChildCount(Object parent){
       if (parent==this){
          return racines.size();
       } else {
          ShellFolder fic =(ShellFolder)parent;
          return listerFils(fic).size();
       }
   }


   /**
    * Returns <code>true</code> if <code>node</code> is a leaf.
    * It is possible for this method to return <code>false</code>
    * even if <code>node</code> has no children.
    * A directory in a filesystem, for example,
    * may contain no files; the node representing
    * the directory is not a leaf, but it also has no children.
    *
    * @param   node  a node in the tree, obtained from this data source
    * @return  true if <code>node</code> is a leaf
    */
   public boolean isLeaf(Object parent){
      boolean retour = false;
      if (parent!=this){
         Boolean b = fv.isTraversable((ShellFolder)parent);
         if (b!=null){
            retour = !b;
         }
      }
      return retour;
   }

   /**
     * Messaged when the user has altered the value for the item identified
     * by <code>path</code> to <code>newValue</code>.
     * If <code>newValue</code> signifies a truly new value
     * the model should post a <code>treeNodesChanged</code> event.
     *
     * @param path path to the node that the user has altered
     * @param newValue the new value from the TreeCellEditor
     */
   public void valueForPathChanged(TreePath path, Object newValue){
      ShellFolder sf = (ShellFolder)path.getLastPathComponent();
      File newFolder = new File(sf.getParentFile(),(String)newValue);
      boolean b = sf.renameTo(newFolder);
      if (b){
         try {
            ArrayList<ShellFolder> brother = cache.get(ShellFolder.getShellFolder(sf.getParentFile()));
            int index = brother.indexOf(sf);
            brother.set(index, ShellFolder.getShellFolder(newFolder));
            fireTreeStructureChanged(this, path.getParentPath(),null,null);
         } catch(FileNotFoundException ex) { // impossible
         }
      }
   }

   /**
    * Returns the index of child in parent.  If either <code>parent</code>
    * or <code>child</code> is <code>null</code>, returns -1.
    * If either <code>parent</code> or <code>child</code> don't
    * belong to this tree model, returns -1.
    *
    * @param parent a note in the tree, obtained from this data source
    * @param child the node we are interested in
    * @return the index of the child in the parent, or -1 if either
    *    <code>child</code> or <code>parent</code> are <code>null</code>
    *    or don't belong to this tree model
    */
   public int getIndexOfChild(Object parent, Object child){
      ArrayList<ShellFolder> fils;
      if (parent==this){
         fils = racines;
      } else {
         ShellFolder fic =(ShellFolder)parent;
         fils = listerFils(fic);
      }
      for(int i=0;i<fils.size();i++){
         if (child==fils.get(i)){
            return i;
         }
      }
      return -1;
   }

//
//  Change Events
//

   /**
    * Adds a listener for the TreeModelEvent posted after the tree changes.
    *
    * @see     #removeTreeModelListener
    * @param   l       the listener to add
    */
   public void addTreeModelListener(TreeModelListener l) {
       listenerList.add(TreeModelListener.class, l);
   }

   /**
    * Removes a listener previously added with <B>addTreeModelListener()</B>.
    *
    * @see     #addTreeModelListener
    * @param   l       the listener to remove
    */
   public void removeTreeModelListener(TreeModelListener l) {
       listenerList.remove(TreeModelListener.class, l);
   }

   /**
     * Invoke this method after you've changed how node is to be
     * represented in the tree.
     */
    /**
     * Notifies all listeners that have registered interest for
     * notification on this event type.  The event instance
     * is lazily created using the parameters passed into
     * the fire method.
     *
     * @param source the node where the tree model has changed
     * @param path the path to the root node
     * @param childIndices the indices of the affected elements
     * @param children the affected elements
     * @see EventListenerList
     */
    protected void fireTreeStructureChanged(Object source, TreePath path,
                                        int[] childIndices,
                                        Object[] children) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        TreeModelEvent e = null;
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length-2; i>=0; i-=2) {
            if (listeners[i]==TreeModelListener.class) {
                // Lazily create the event:
                if (e == null)
                    e = new TreeModelEvent(source, path,
                                           childIndices, children);
                ((TreeModelListener)listeners[i+1]).treeStructureChanged(e);
            }
        }
    }


   /**
    * Return the child of a folder.
    * Use the internal cache.
    * @param f ShellFolder
    * @return ArrayList
    */
   private ArrayList<ShellFolder> listerFils(ShellFolder f){
   ArrayList<ShellFolder>retour = cache.get(f);
      if (retour == null||retour.size()==0){
         retour = convert(f.listFiles(showFilter));
         cache.put(f,retour);
      }
      return retour;
   }

   public void elementModifie(Component c, Object obj){
       TreePath tp = getTreePath(obj).getParentPath();
       int indexFils = getIndexOfChild(tp.getLastPathComponent(),obj);
       fireTreeNodesChanged(c,tp,new int[]{indexFils},null);
   }


   /**
    * Notifies all listeners that have registered interest for
    * notification on this event type.  The event instance
    * is lazily created using the parameters passed into
    * the fire method.
    *
    * @param source the node being changed
    * @param path the path to the root node
    * @param childIndices the indices of the changed elements
    * @param children the changed elements
    * @see EventListenerList
    */
   protected void fireTreeNodesChanged(Object source, TreePath path,
                                       int[] childIndices,
                                       Object[] children) {
       // Guaranteed to return a non-null array
       Object[] listeners = listenerList.getListenerList();
       TreeModelEvent e = null;
       // Process the listeners last to first, notifying
       // those that are interested in this event
       for (int i = listeners.length-2; i>=0; i-=2) {
           if (listeners[i]==TreeModelListener.class) {
               // Lazily create the event:
               if (e == null)
                   e = new TreeModelEvent(source, path,
                                          childIndices, children);
               ((TreeModelListener)listeners[i+1]).treeNodesChanged(e);
           }
       }
   }

   /**
    * Return the child of a folder.
    * Use the internal cache.
    * @param f ShellFolder
    * @return ArrayList
    */
   public void addFils(Component c, TreePath p, ShellFolder pere, ShellFolder fils){
      ArrayList<ShellFolder>retour = cache.get(pere);
      if (retour == null||retour.size()==0){
         retour = convert(pere.listFiles(showFilter));
         cache.put(pere,retour);
      }
      retour.add(fils);
      fireTreeStructureChanged(c,p,null,null);
   }


   /**
    * Convert File[] into ArrayList<ShellFolder>
    * @param fichiers File[]
    * @return ArrayList
    */
   public static ArrayList<ShellFolder>convert(File[] fichiers){
      ArrayList list = new ArrayList();
      if (fichiers!=null){
         for(File fic:fichiers){
            try{
               list.add(ShellFolder.getShellFolder(fic));
            }catch (FileNotFoundException ex){
            }
         }
      }
      return list;
   }

   public void clear(){
      racines.clear();
      cache.clear();
   }

   /**
    * Pour un objet donn� retourne le TreePath correspondant
    * @param recherche Object
    * @return TreePath
    */
   public TreePath getTreePath(Object recherche){
      ShellFolder sf = (ShellFolder)recherche;
      LinkedList ll = new LinkedList();
      ll.addFirst(sf);
      File f;
      while((f = sf.getParentFile())!=null){
         try
         {
            sf = ShellFolder.getShellFolder(f);
            ll.addFirst(sf);
         } catch(FileNotFoundException ex){} // Impossible
      }
      ll.addFirst(this);
      return ll==null?null:new TreePath(ll.toArray());
   }

}
