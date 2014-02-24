package de.miij.ui.folderchooser;

import java.io.*;
import javax.swing.filechooser.FileSystemView;

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
public class DirectoryFileFilter implements FileFilter
{
   public DirectoryFileFilter()
   {
   }

   /**
    * Tests whether or not the specified abstract pathname should be included
    * in a pathname list.
    *
    * @param pathname The abstract pathname to be tested
    * @return <code>true</code> if and only if <code>pathname</code> should
    *   be included
    * @todo Impl�menter cette m�thode java.io.FileFilter
    */
   public boolean accept(File pathname)
   {
      return pathname.isDirectory() ||
              FileSystemView.getFileSystemView().isComputerNode(pathname) ||
              FileSystemView.getFileSystemView().isDrive(pathname) ||
//              FileSystemView.getFileSystemView().isFileSystem(pathname) ||
              FileSystemView.getFileSystemView().isFileSystemRoot(pathname) ||
              FileSystemView.getFileSystemView().isFloppyDrive(pathname);
   }
}
