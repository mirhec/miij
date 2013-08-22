package de.miij.util;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Diese Klasse stellte statische Methoden zur Verf&uuml;gung, welche das
 * dynamische Laden von (zur Laufzeit nicht bekannten) Klassen m&ouml;glich
 * machen. Es ist a&ouml;ch m&ouml;glich, andere EXEs zu starten.
 * <p/>
 * @author Mirhec
 */
public class FileLoaderUtil
{
	/**
	 * Diese Methode l&auml;dt das &uuml;bergebene JAR-File und liefert das
	 * &ouml;* dazugeh&ouml;rende Klassen-Objekt zur&uuml;ck.
	 * <p/>
	 * @param path
	 * <p/>
	 * @throws Exception
	 * @return
	 */
	public static Class loadExternalClass(String path) throws Exception
	{
		try
		{
			// --- 3.1 * Versuchen die Klasse (Dateiname ohne Endung) zu laden
			File f = new File(path);
			String filename = f.getName();
			if (f.isFile())
				if (filename.substring(filename.length() - 3).equals("jar")) // Nur *.jar-Files
				{
					Class clazz = new URLClassLoader(new URL[]
					{
						f.toURL()
					}).loadClass(filename.substring(0, filename.length() - 4));

					return clazz;
				}
		}
		catch (Exception ex)
		{
			throw new Exception("Die Klasse '" + path + "' konnte nicht geladen werden!");
		}

		return null;
	}
}
