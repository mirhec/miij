package de.miij.ui.folderchooser;

import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.File;

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
public class LookAndFeelFileView extends FileView
{
   FileView fv;
   public LookAndFeelFileView()
   {
      JFileChooser chooser = new JFileChooser();
      fv =chooser.getUI().getFileView(chooser);
   }

   /**
    * The name of the file. Normally this would be simply
    * <code>f.getName()</code>.
    */
   public String getName(File f) {
      return fv.getName(f);
   };

   /**
    * A human readable description of the file. For example,
    * a file named <i>jag.jpg</i> might have a description that read:
    * "A JPEG image file of James Gosling's face".
    */
   public String getDescription(File f) {
      return fv.getDescription(f);
   }

   /**
    * A human readable description of the type of the file. For
    * example, a <code>jpg</code> file might have a type description of:
    * "A JPEG Compressed Image File"
    */
   public String getTypeDescription(File f) {
      return fv.getTypeDescription(f);
   }

   /**
    * The icon that represents this file in the <code>JFileChooser</code>.
    */
   public Icon getIcon(File f) {
      return fv.getIcon(f);
   }

   /**
    * Whether the directory is traversable or not. This might be
    * useful, for example, if you want a directory to represent
    * a compound document and don't want the user to descend into it.
    */
   public Boolean isTraversable(File f) {
      return fv.isTraversable(f);
   }


}
