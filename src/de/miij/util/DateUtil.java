package de.miij.util;

public class DateUtil
{
	public static int daysOfMonth(int monat, int jahr)
	{
		switch (monat)
		{
			case 1:
				return 31;
			case 2:
				return isSchaltjahr(jahr) ? 29 : 28;
			case 3:
				return 31;
			case 4:
				return 30;
			case 5:
				return 31;
			case 6:
				return 30;
			case 7:
				return 31;
			case 8:
				return 31;
			case 9:
				return 30;
			case 10:
				return 31;
			case 11:
				return 30;
			case 12:
				return 31;
			default:
				return -1;
		}
	}

	public static boolean isSchaltjahr(int jahr)
	{
		return jahr % 4 == 0 && jahr % 2000 != 0;
	}
}
