package com.joe.core.bean;

import net.sf.cglib.core.NamingPolicy;
import net.sf.cglib.core.Predicate;

public class JoeNamingPolicy implements NamingPolicy {
	public JoeNamingPolicy() {
	}

	public static final JoeNamingPolicy INSTANCE = new JoeNamingPolicy();

	public String getClassName(String prefix, String source, Object key,
			Predicate names) {
		if (prefix == null) {
			prefix = "com.joe.core.vo";
		} else if (prefix.startsWith("java")) {
			prefix = "$" + prefix;
		}
		String base = prefix + "$$"
				+ source.substring(source.lastIndexOf('.') + 1) + getTag()
				+ "$$" + Integer.toHexString(key.hashCode());
		System.out.println(" =============== " + key + " =============== ");
		System.out.println(" =============== " + source + " =============== ");
		String attempt = base;
		int index = 2;
		while (names.evaluate(attempt))
			attempt = base + "_" + index++;
		return attempt;
	}

	protected String getTag() {
		return "ByCGLIB";
	}
}