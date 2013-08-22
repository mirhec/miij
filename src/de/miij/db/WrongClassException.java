package de.miij.db;

public class WrongClassException extends Exception
{
	private static final long serialVersionUID = 2443600061525695078L;

	public String getMessage()
	{
		return "Die Datei beinhaltet nicht die erwartete Klasse!";
	}
}