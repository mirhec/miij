package de.miij.util;

import java.awt.Container;

public class UIUtil
{
	/**
	 * Diese Methode liefert alle Komponenten auf dem Container c zur&uuml;ck.
	 * <p/>
	 * @param c
	 * <p/>
	 * @return
	 * <p/>
	 * @deprecated Hierbei ist Vorsicht geboten, da die Komponenten mehrmals in
	 * dem Array vorkommen
	 */
	public static Object[] getRecursiveChildren(Container c)
	{
		Object[] comps = c.getComponents();

		for (int i = 0; i < comps.length; i++)
			if (comps[ i] instanceof Container)
				if (((Container) comps[ i]).getComponents().length > 0)
					comps = ArrayUtil.insert(comps, getRecursiveChildren((Container) comps[ i]));

		return comps;
	}
}
