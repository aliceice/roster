package de.aice.roster.ratpack.util;

import java.util.function.BinaryOperator;

/**
 * String utils.
 *
 * @author Selena Lowell (selena.lowell@unknown.com)
 * @version $Id$
 */
public final class StringUtils {

	public static final String EMPTY = "";

	private StringUtils() { }

	/**
	 * Create concatenation function with given separator.
	 *
	 * @param separator separator to use
	 * @return concatenation function
	 */
	public static BinaryOperator<String> concat(final String separator) {
		return (s1, s2) -> s1 + separator + s2;
	}
}
