package de.miij.util;

import java.util.Hashtable;
import java.util.Locale;

/**
 * Created by Mirko Hecky on 06.03.14.
 */
public class LangString {
	private static Hashtable<String, String> de;
	private static Hashtable<String, Hashtable<String, String>> langs;

	/**
	 * Returns en in the translation of the current location.
	 *
	 * @param en
	 * @return
	 */
	public static String L(String en) {
		String lang = Locale.getDefault().getCountry();
		if(langs == null) init();
		if(langs.get(lang) == null) langs.put(lang, new Hashtable<String, String>());

		if(langs.get(lang).containsKey(en)) {
			return langs.get(lang).get(en);
		} else {
			return en;
		}
	}

	private static void init() {
		langs = new Hashtable<>();
		add("de", "Yes", "Ja");
		add("de", "No", "Nein");
		add("de", "Ok", "Ok");
	}

	public static void add(String lang, String key, String val) {
		if(langs == null) init();
		if(langs.get(lang) == null) langs.put(lang, new Hashtable<String, String>());
		langs.get(lang).put(key, val);
	}
}
