package de.miij.ui.folderchooser;

import javax.swing.filechooser.*;
import javax.swing.Icon;
import java.io.File;
import javax.swing.UIManager;
import java.awt.Image;
import java.util.Map;
import javax.swing.ImageIcon;
import java.util.HashMap;
import sun.awt.shell.*;

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
public class SystemFileView extends FileView
{
   private Icon directoryIcon    = UIManager.getIcon("FileView.directoryIcon"); //NOI18N
   private Icon fileIcon         = UIManager.getIcon("FileView.fileIcon"); //NOI18N
   private Icon computerIcon     = UIManager.getIcon("FileView.computerIcon"); //NOI18N
   private Icon hardDriveIcon    = UIManager.getIcon("FileView.hardDriveIcon"); //NOI18N
   private Icon floppyDriveIcon  = UIManager.getIcon("FileView.floppyDriveIcon"); //NOI18N
   private Map<Image, ImageIcon> cacheImages = new HashMap<Image, ImageIcon>();


   public SystemFileView()
   {
   }

   /**
    * The name of the file. Normally this would be simply
    * <code>f.getName()</code>.
    */
   public String getName(File f) {
//      ShellFolder sf = (ShellFolder)f;
      String s = FileSystemView.getFileSystemView().getSystemDisplayName(f);
      if ("".equals(s)){
         s = f.getName();
      }
      return s;
   }

   /**
    * A human readable description of the file. For example,
    * a file named <i>jag.jpg</i> might have a description that read:
    * "A JPEG image file of James Gosling's face".
    */
   public String getDescription(File f) {
  return null;
   }

   /**
    * A human readable description of the type of the file. For
    * example, a <code>jpg</code> file might have a type description of:
    * "A JPEG Compressed Image File"
    */
   public String getTypeDescription(File f) {
      return FileSystemView.getFileSystemView().getSystemTypeDescription(f);
   }

   /**
    * The icon that represents this file in the <code>JFileChooser</code>.
    */
   public Icon getIcon(File f) {
      Icon retour = null;
      ShellFolder sf = (ShellFolder)f;
      java.awt.Image image = sf.getIcon(false);
      if(image == null)
      {
         retour=getDefaultIcon(sf);
      } else
      {
         ImageIcon i = cacheImages.get(image);
         if(i == null)
         {
            i = new ImageIcon(image);
            cacheImages.put(image, i);
         }
         retour = i;
      }
      return retour;
   }

   public Icon getDefaultIcon(File f) {
       Icon icon = fileIcon;
       if (f!=null){
          FileSystemView fsv = FileSystemView.getFileSystemView();
          if (fsv.isFloppyDrive(f)) {
             icon = floppyDriveIcon;
          } else if (fsv.isDrive(f)) {
             icon = hardDriveIcon;
          } else if (fsv.isComputerNode(f)) {
             icon = computerIcon;
          } else if (f.isDirectory()) {
             icon = directoryIcon;
          } else {
             icon = fileIcon;
          }
       }
       return icon;
   }

   public void clear(){
      cacheImages.clear();
   }

   /**
    * Whether the directory is traversable or not. This might be
    * useful, for example, if you want a directory to represent
    * a compound document and don't want the user to descend into it.
    */
   public Boolean isTraversable(File f) {
      return Boolean.TRUE;
   }

}
