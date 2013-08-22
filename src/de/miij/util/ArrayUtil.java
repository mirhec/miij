package de.miij.util;

import java.util.Enumeration;
import java.util.Hashtable;

public class ArrayUtil
{
	private static Character[] Order = new Character[]
	{
		new Character('0'), new Character('1'), new Character('2'),
		new Character('3'), new Character('4'), new Character('5'),
		new Character('6'), new Character('7'), new Character('8'),
		new Character('9'), new Character('A'), new Character('B'),
		new Character('C'), new Character('D'), new Character('E'),
		new Character('F'), new Character('G'), new Character('H'),
		new Character('I'), new Character('J'), new Character('K'),
		new Character('L'), new Character('M'), new Character('N'),
		new Character('O'), new Character('P'), new Character('Q'),
		new Character('R'), new Character('S'), new Character('T'),
		new Character('U'), new Character('V'), new Character('W'),
		new Character('X'), new Character('Y'), new Character('Z')
	};

	public ArrayUtil()
	{
		super();
	}

	public static Object[] insert(Object[] arr1, Object[] arr2)
	{
		Object[] ret = new Object[arr1.length + arr2.length];

		int i = 0;

		for (i = 0; i < arr1.length; i++)
			ret[ i] = arr1[ i];

		for (int x = 0; x < arr2.length; i++, x++)
			ret[ i] = arr2[ x];

		return ret;
	}

	/**
	 * Sortiert das Array um, so dass der &uuml;bergebene Index ab sofort 0 ist.
	 * <p/>
	 * @param arr
	 * @param index
	 * <p/>
	 * @return
	 */
	public static char[] setStartPos(char[] arr, int index)
	{
		char[] ret = new char[arr.length];

		for (int i = index, j = 0; j < arr.length; i = (i + 1) % arr.length, j++)
			ret[ j] = arr[ i];

		return ret;
	}

	/**
	 * Sortiert das Array um, so dass der &uuml;bergebene Index ab sofort 0 ist.
	 * <p/>
	 * @param arr
	 * @param index
	 * <p/>
	 * @return
	 */
	public static int[][] setStartPos(int[][] arr, int index)
	{
		int[][] ret = new int[arr.length][arr[ 0].length];

		for (int i = index, j = 0; j < arr.length; i = (i + 1) % arr.length, j++)
			for (int ii = 0; ii < arr[ i].length; ii++)
				ret[ j] = arr[ i];

		return ret;
	}

	public static Object[] getSubArray(Object[] arr, int start, int length)
	{
		Object[] ret = new Object[length];

		setItemsTo(ret, new Character('0'));

		for (int i = 0; i < length && i + start < arr.length; i++)
			ret[ i] = arr[ i + start];

		return ret;
	}

	public static Object[] insertSubArray(Object[] arr, Object[] insert, int start, int length)
	{
		for (int i = 0; i < length && i + start < arr.length; i++)
			arr[ i + start] = insert[ i];

		return arr;
	}

	public static void setItemsTo(Object[] arr, Object value)
	{
		for (int i = 0; i < arr.length; i++)
			arr[ i] = value;
	}

	/**
	 * Diese Methode liefert ein neues String[] zur&uuml;ck. Daf&uuml;r wird ein
	 * String (str) ben&ouml;tigt, in welchem Informationen bzw. Werte anhand
	 * des Trenneers (separator) gespeichert sind. Diese Methode kon- vertiert
	 * den String in ein Array.
	 * <p/>
	 * @param str
	 * @param separator
	 * <p/>
	 * @return
	 */
	public static String[] getArray(String str, String separator)
	{
		if (str == null)
			return new String[0];

		if (separator == null)
			return new String[]
			{
				str
			};

		String[] arr = new String[StringUtil.getOccurences(str, separator) + 1];

		for (int i = 0; i < arr.length; i++)
		{
			int index = str.indexOf(separator);
			if (index == -1)
			{
				arr[ i] = str;
				str = "";
			}
			else
			{
				arr[ i] = str.substring(0, index);
				str = str.substring(index + separator.length());
			}
		}

		return arr;
	}

	/**
	 * Diese Methode liefert einen String zur&uuml;ck. Daf&uuml;r wird ein
	 * String[] &ouml; ben&ouml;tigt, in welchem Informationen bzw. Werte anhand
	 * des Trenneers (separator) gespeichert sind. Diese Methode kon- vertiert
	 * das Array in einen String.
	 * <p/>
	 * @param arr
	 * @param separator
	 * <p/>
	 * @return
	 */
	public static String getString(Object[] arr, String separator)
	{
		if (arr == null)
			return "";

		String str = "";

		for (int i = 0; i < arr.length; i++)
		{
			str += arr[ i];

			if (separator != null && i + 1 < arr.length)
				str += separator;
		}

		return str;
	}

	/**
	 * Diese Methode liefert einen String zur&uuml;ck. Daf&uuml;r wird ein
	 * String[&ouml; ben&ouml;tigt, in welchem Informationen bzw. Werte anhand
	 * des Trenneers (separator) gespeichert sind. Diese Methode kon- vertiert
	 * das Array in einen String.
	 * <p/>
	 * @param arr
	 * @param separator
	 * <p/>
	 * @return
	 */
	public static String getString(int[] arr, String separator)
	{
		if (arr == null)
			return "";

		String str = "";

		for (int i = 0; i < arr.length; i++)
		{
			str += arr[ i];

			if (separator != null && i + 1 < arr.length)
				str += separator;
		}

		return str;
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft, ob das &uuml;bergebene Object
	 * (actElement) in dem &uuml;bergebenen Array vorkommt. Ist dies der Fall,
	 * so wird das n&auml;chste Element (bzw. das erste, wenn das aktuelle
	 * Element das letzte ist) zur&uuml;ck gegeben.
	 * <p/>
	 * @param arr
	 * @param actElement
	 * <p/>
	 * @return Liefert null zur&uuml;ck, wenn actElement nicht gefunden wurde.
	 */
	public static Object getNextElement(Object[] arr, Object actElement)
	{
		for (int i = 0; i < arr.length; i++)
			if (arr[ i].equals(actElement))
				if (i == arr.length - 2)
					return arr[ 0];
				else
					return arr[ i + 1];

		return null;
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft, ob das &uuml;bergebene Object
	 * (actElement) in dem &uuml;bergebenen Array vorkommt. Ist dies der Fall,
	 * so wird das vorherige Element (bzw. das letzte, wenn das aktuelle Element
	 * das erste ist) zur&uuml;ck gegeben.
	 * <p/>
	 * @param arr
	 * @param actElement
	 * <p/>
	 * @return Liefert null zur&uuml;ck, wenn actElement nicht gefunden wurde.
	 */
	public static Object getPreviousElement(Object[] arr, Object actElement)
	{
		for (int i = 0; i < arr.length; i++)
			if (arr[ i].equals(actElement))
				if (i == 0)
					return arr[ arr.length - 1];
				else
					return arr[ i - 1];

		return null;
	}

	/**
	 * Diese Methode liefert den Index des &uuml;bergebenen Elements
	 * zur&uuml;ck.
	 * <p/>
	 * @param arr
	 * @param element
	 * <p/>
	 * @return
	 */
	public static int getElementIndex(Object[] arr, Object element)
	{
		for (int i = 0; i < arr.length; i++)
			if (arr[ i].equals(element))
				return i;

		return -1;
	}

	/**
	 * Diese Methode sortiert das &uuml;bergebene Array nach dem BubbleSort-
	 * Algorithmus.
	 * <p/>
	 * @param arr
	 * <p/>
	 * @return
	 */
	public static int[] bubbleSort(int[] arr)
	{
		for (int i = 0; i < arr.length; i++)
			for (int j = i; j < arr.length; j++)
				if (arr[ j] < arr[ i])
				{
					arr[ i] = new Integer(arr[ j]).intValue();
					arr[ j] = new Integer(arr[ i]).intValue();
				}

		return arr;
	}

	/**
	 * Diese Methode sortiert das &uuml;bergebene Array nach dem BubbleSort-
	 * Algorithmus.
	 * <p/>
	 * @param arr
	 * <p/>
	 * @return
	 */
	public static long[] bubbleSort(long[] arr)
	{
		for (int i = 0; i < arr.length; i++)
			for (int j = i; j < arr.length; j++)
				if (arr[ j] < arr[ i])
				{
					arr[ i] = new Long(arr[ j]).longValue();
					arr[ j] = new Long(arr[ i]).longValue();
				}

		return arr;
	}

	/**
	 * Diese Methode sortiert das &uuml;bergebene Array nach dem BubbleSort-
	 * Algorithmus.
	 * <p/>
	 * @param arr
	 * <p/>
	 * @return
	 */
	public static Object[] bubbleSort(Object[] arr)
	{
		for (int i = 0; i < arr.length; i++)
			for (int j = i; j < arr.length; j++)
			{
				String s1 = arr[ i].toString().toUpperCase();
				String s2 = arr[ j].toString().toUpperCase();

				int index1 = -1;
				int index2 = -1;

				int pos = -1;

				while (index1 == index2 && pos < s1.length() - 1 && pos < s2.length() - 1)
				{
					pos++;
					index1 = ArrayUtil.getElementIndex(Order, new Character(s1.charAt(pos)));
					index2 = ArrayUtil.getElementIndex(Order, new Character(s2.charAt(pos)));
				}

				if (index1 > index2)
				{
					Object hlp = arr[ i];
					arr[ i] = arr[ j];
					arr[ j] = hlp;
				}
			}

		return arr;
	}

	/**
	 * Diese Methode sortiert das &uuml;bergebene Array nach dem BubbleSort-
	 * Algorithmus.
	 * <p/>
	 * @param arr
	 * <p/>
	 * @return
	 */
	public static String[] bubbleSort(String[] arr)
	{
		for (int i = 0; i < arr.length; i++)
			for (int j = i; j < arr.length; j++)
			{
				String s1 = arr[ i].toString().toUpperCase();
				String s2 = arr[ j].toString().toUpperCase();

				int index1 = -1;
				int index2 = -1;

				int pos = -1;

				while (index1 == index2 && pos < s1.length() - 1 && pos < s2.length() - 1)
				{
					pos++;
					index1 = ArrayUtil.getElementIndex(Order, new Character(s1.charAt(pos)));
					index2 = ArrayUtil.getElementIndex(Order, new Character(s2.charAt(pos)));
				}

				if (index1 > index2)
				{
					String hlp = arr[ i];
					arr[ i] = arr[ j];
					arr[ j] = hlp;
				}
			}

		return arr;
	}

	/**
	 * Diese Methode sortiert die &uuml;bergebene Hashtable im BubbleSort-Algo-
	 * rithmus.
	 * <p/>
	 * @param h
	 * @param keySort Ob nach den Schl&uuml;sseln, oder den Werten sortiert
	 *                werden soll
	 * <p/>
	 * @return
	 */
	public static Object[] bubbleSort(Hashtable h, boolean keySort)
	{
		Enumeration e = keySort ? h.keys() : h.elements();
		Object[] obj = new Object[h.size()];
		int i = 0;

		while (e.hasMoreElements())
			obj[ i++] = e.nextElement(); // i wird erst nachher ho&auml;hgez&auml;hlt

		return ArrayUtil.bubbleSort(obj);
	}

	public static void main(String[] args)
	{
		String s = "Inf1;;Inf2;;Inf3;;Inf4";
		System.out.println(s);
		String[] sarr = getArray(s, ";;");
		for (int i = 0; i < sarr.length; i++)
			System.out.println(sarr[ i]);
		s = getString(sarr, ";;");
		System.out.println(s);
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft, ob das &uuml;bergebene Objekt in dem
	 * &uuml;bergebenen Array existiert.
	 * <p/>
	 * @param arr
	 * @param obj
	 * <p/>
	 * @return
	 */
	public static boolean isInArray(Object[] arr, Object obj)
	{
		boolean ret = false;

		for (int i = 0; i < arr.length; i++)
			if ((arr[i] != null && obj != null && arr[i].equals(obj)) || arr[i] == null && obj == null)
				ret = true;

		return ret;
	}

	/**
	 * Diese Methode &uuml;berpr&uuml;ft, ob alle Werte des &uuml;bergebenen
	 * Arrays isIn in dem Array arr vorkommen.
	 * <p/>
	 * @param arr
	 * @param isIn
	 * <p/>
	 * @return
	 */
	public static boolean isInArray(Object[] arr, Object[] isIn)
	{
		boolean ret = true;

		for (int i = 0; i < isIn.length; i++)
			for (int j = 0; j < arr.length; j++)
				if (!isIn[ i].equals(arr[ j]))
					ret = false;

		return ret;
	}

	/**
	 * Diese Methode f&uuml;gt einem vorhandenen Array einen weiteren Eintrag an
	 * das Ende hinzu. Dabei wird nicht &uuml;berpr&uuml;ft, ob die Objekte vom
	 * Selben Datentyp sind.
	 * <p/>
	 * @param arr
	 * @param insert
	 * <p/>
	 * @return
	 */
	public static Object[] insert(Object[] arr, Object insert)
	{
		Object[] ret = new Object[arr.length + 1];

		for (int i = 0; i < arr.length; i++)
			ret[ i] = arr[ i];

		ret[ ret.length - 1] = insert;

		return ret;
	}

	/**
	 * Diese Methode f&uuml;gt einem vorhandenen Array einen weiteren Eintrag an
	 * das Ende hinzu. Dabei wird nicht &uuml;berpr&uuml;ft, ob die Objekte vom
	 * Selben Datentyp sind.
	 * <p/>
	 * @param arr
	 * @param insert
	 * <p/>
	 * @return
	 */
	public static Object[][] insert(Object[][] arr, Object insert, int pos2Dimension)
	{
		Object[][] ret = null;

		try
		{
			ret = new Object[arr.length + 1][arr[ 0].length > pos2Dimension + 1 ? arr[ 0].length : pos2Dimension + 1];
		}
		catch (ArrayIndexOutOfBoundsException ex)
		{
			ret = new Object[arr.length + 1][pos2Dimension + 1];
		}

		for (int i = 0; i < arr.length; i++)
			ret[ i] = arr[ i];

		ret[ ret.length - 1][ pos2Dimension] = insert;

		return ret;
	}

	public static Object[] replace(Object[] arr, Object original, Object replace)
	{
		int index = ArrayUtil.getElementIndex(arr, original);

		if (index > -1 && index < arr.length)
			arr[ index] = replace;

		return arr;
	}

	public static Object[] remove(Object[] arr, Object removeElement)
	{
		int index = ArrayUtil.getElementIndex(arr, removeElement);

		Object[] ret = new Object[arr.length - 1];

		for (int i = 0; i < arr.length; i++)
			if (i != index)
				if (i > index)
					ret[ i - 1] = arr[ i];

		return ret;
	}
	// public static void main( String[] args )
	// {
	// String s = "Inf1;;Inf2;;Inf3;;Inf4";
	// System.out.println( s );
	// String[] sarr = getArray( s , ";;" );
	// for( int i = 0; i < sarr.length; i++ )
	// {
	// System.out.println( sarr[ i ] );
	// }
	// s = getString( sarr , ";;" );
	// System.out.println( s );
	// }
}
