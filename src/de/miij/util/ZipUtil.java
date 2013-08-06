package de.miij.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{

	/**
	 * Diese Methode packt alle in dem Array filenames enthaltenen Dateien
	 * in das Verzeichnis zipFilename.
	 *
	 * @param zipFilename
	 * @param filenames
	 * @throws IOException
	 */
	public static void zip(String baseDir, String zipFilename, String[] filenames) throws IOException
	{
		zip(baseDir, zipFilename, filenames, filenames);
	}

	/**
	 * Diese Methode packt alle in dem Array filenames enthaltenen Dateien
	 * in das ZIP-Archiv zipFilename, und vergibt den Dateien dann den Namen,
	 * welcher in dem Array archFilenames gespeichert ist:
	 * z.B. filenames[ 0 ] 		 		 => "C:\Programme\programm.exe"
	 * 		   archFilenames[ 0 ] => "Programm.exe"
	 *
	 * @param zipFilename
	 * @param filenames
	 * @param archFilenames
	 * @throws IOException
	 */
	public static void zip(String baseDir, String zipFilename, String[] filenames, String[] archFilenames) throws IOException
	{

		ZipOutputStream zout = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipFilename)));

		byte[] data = new byte[512];
		int bc;
		for (int i = 0; i < filenames.length; i++)
		{
			try
			{
				InputStream fin = new BufferedInputStream(new FileInputStream(filenames[i]));

//		 		 		 ZipEntry entry = new ZipEntry( FileUtil.stripPath( archFilenames[ i ] ) );
				ZipEntry entry = new ZipEntry(archFilenames[i].substring(baseDir.length() + 1));
//		 		 		 System.out.println( baseDir + "|" + baseDir.length() + "|" + archFilenames[ i ].substring( baseDir.length() + 1 ) );
				zout.putNextEntry(entry);

				while ((bc = fin.read(data, 0, 512)) != -1)
				{
					zout.write(data, 0, bc);
				}
				zout.flush();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
				// Weitermachen
			}
		}

		zout.close();
	}

	/**
	 * Diese Methode entpackt alle Dateien eines Archivs, welche in der Collection
	 * filenames vorkommen.
	 *
	 * @param zipFilename - ZIP-File
	 * @param filenames
	 * @param outdir - Ausgabeverzeichnis
	 * @throws IOException
	 */
	public static void unzip(String zipFilename, Collection filenames, String outdir) throws IOException
	{
		unzip(zipFilename, (String[]) filenames.toArray(new String[filenames.size()]), outdir);
	}

	/**
	 * Diese Methode entpackt alle Dateien eines Archivs, welche in dem Array
	 * filenames vorkommen.
	 *
	 * @param zipFilename - ZIP-File
	 * @param filenames
	 * @param outdir - Ausgabeverzeichnis
	 * @throws IOException
	 */
	public static void unzip(String zipFilename, String[] filenames, String outdir) throws IOException
	{

		ZipFile zipFile = new ZipFile(zipFilename);
		Enumeration entries = zipFile.entries();

		L1:
		while (entries.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry) entries.nextElement();

			for (int i = 0; i < filenames.length; i++)
			{
				if (entry.getName().equals(filenames[i]))
				{
					byte[] buffer = new byte[1024];
					int len;

					InputStream zipin = zipFile.getInputStream(entry);
					BufferedOutputStream fileout = new BufferedOutputStream(new FileOutputStream(outdir + "\\" + filenames[i]));

					while ((len = zipin.read(buffer)) >= 0)
					{
						fileout.write(buffer, 0, len);
					}

					zipin.close();
					fileout.flush();
					fileout.close();

					continue L1;

				}
			}
		}
	}

	/**
	 * Diese Methode entpackt alle in dem Archiv zipFilename
	 * vorhandenen Dateien, und speichert diese im Ordner
	 * outdir.
	 *
	 * @param zipFilename
	 * @param outdir
	 * @throws IOException
	 */
	public static void unzip(String zipFilename, String outdir) throws IOException
	{
		ZipFile zipFile = new ZipFile(zipFilename);
		Enumeration entries = zipFile.entries();

		while (entries.hasMoreElements())
		{
			ZipEntry entry = (ZipEntry) entries.nextElement();
			byte[] buffer = new byte[1024];
			int len;




			InputStream zipin = zipFile.getInputStream(entry);
			new File(outdir + "\\" + entry.getName()).getParentFile().mkdirs();
			BufferedOutputStream fileout = new BufferedOutputStream(new FileOutputStream(outdir + "\\" + entry.getName()));

			while ((len = zipin.read(buffer)) >= 0)
			{
				fileout.write(buffer, 0, len);
			}

			zipin.close();
			fileout.flush();
			fileout.close();
		}
	}

	public static void main(String[] args)
	{
		for (int i = 0; i < args.length; i++)
		{
			if (args[i] != null && !args[i].equals(""))
			{
				if (new File(args[i]).exists())
				{
					if (args[i].substring(args[i].length() - 3).equals("zip"))
					{
						try
						{
							ZipUtil.unzip(args[i], args[i].substring(0, args[i].length() - 3));
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
					else
					{
						try
						{
							ZipUtil.zip(args[i], args[i] + ".zip", FileUtil.getAllFiles(new File(args[i])));
						}
						catch (Exception ex)
						{
							ex.printStackTrace();
						}
					}
				}
			}
		}
	}
}

class TestZIPUtil
{

	public static void main(String[] args)
	{
		File f = new File("H:/Daten/Eclipse/Suffi/org");
		try
		{
			if (f.exists())
			{
				File[] files = f.listFiles();
				String[] fileNames = new String[files.length];
				String[] zipNames = new String[files.length];

				for (int i = 0; i < files.length; i++)
				{
					fileNames[i] = files[i].getAbsolutePath();
					zipNames[i] = files[i].getName();
				}
				ZipUtil.zip(f.getAbsolutePath(), "Modules.zip", FileUtil.getAllFiles(f), FileUtil.getAllFiles(f));
			}
			else
			{
				System.out.println(f.getAbsolutePath().substring(0, f.getAbsolutePath().lastIndexOf("/")) + " existiert nicht!");
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
