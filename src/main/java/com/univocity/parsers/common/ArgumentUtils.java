/*******************************************************************************
 * Copyright 2014 uniVocity Software Pty Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package com.univocity.parsers.common;

import java.util.*;

/**
 * An utility class for validating inputs.
 *
 * @author uniVocity Software Pty Ltd - <a href="mailto:parsers@univocity.com">parsers@univocity.com</a>
 *
 */
public class ArgumentUtils {

	public static String[] EMPTY_STRING_ARRAY = new String[0];

	/**
	 * Throws an IllegalArgumentException if the given array is null or empty.
	 * @param argDescription the description of the elements
	 * @param args the elements to be validated.
	 * @param <T> Type of arguments to be validated
	 */
	public static <T> void notEmpty(String argDescription, T... args) {
		if (args == null) {
			throw new IllegalArgumentException(argDescription + " must not be null");
		}
		if (args.length == 0) {
			throw new IllegalArgumentException(argDescription + " must not be empty");
		}
	}

	/**
	 * Throws an IllegalArgumentException if the given array is null,empty, or contains null values
	 * @param argDescription the description of the elements
	 * @param args the elements to be validated.
	 * @param <T> Type of arguments to be validated
	 */
	public static <T> void noNulls(String argDescription, T... args) {
		notEmpty(argDescription, args);
		for (T arg : args) {
			if (arg == null) {
				if (args.length > 0) {
					throw new IllegalArgumentException(argDescription + " must not contain nulls");
				} else {
					throw new IllegalArgumentException(argDescription + " must not be null");
				}
			}
		}
	}

	/**
	 * Returns the index of an element in a given array.
	 * @param array the element array
	 * @param element the element to be looked for in the array.
	 *
	 * @return the index of the given element in the array, or -1 if the element could not be found.
	 */
	public static int indexOf(Object[] array, Object element) {
		if (array == null) {
			throw new NullPointerException("Null array");
		}
		if (element == null) {
			for (int i = 0; i < array.length; i++) {
				if (array[i] == null) {
					return i;
				}
			}
		} else {
			if (element instanceof String && array instanceof String[]) {
				for (int i = 0; i < array.length; i++) {
					String e = String.valueOf(array[i]);
					if (element.toString().equalsIgnoreCase(e)) {
						return i;
					}
				}
			}

			for (int i = 0; i < array.length; i++) {
				if (element.equals(array[i])) {
					return i;
				}
			}
		}
		return -1;
	}

	/**
	 * Searches for elements in a given array and returns the elements not found.
	 * @param array An array with elements
	 * @param elements the elements to be found
	 * @return the elements not found in the array.
	 */
	public static Object[] findMissingElements(Object[] array, Collection<?> elements) {
		return findMissingElements(array, elements.toArray());
	}

	/**
	 * Searches for elements in a given array and returns the elements not found.
	 * @param array An array with elements
	 * @param elements the elements to be found
	 * @return the elements not found in the array.
	 */
	public static Object[] findMissingElements(Object[] array, Object[] elements) {
		List<Object> out = new ArrayList<Object>();

		for (Object element : elements) {
			if (indexOf(array, element) == -1) {
				out.add(element);
			}
		}

		return out.toArray();
	}

	/**
	 * Normalizes the Strings in a given array by trimming all elements and converting them to lower case.
	 * @param strings a String array with elements to be normalized.
	 * @return the normalized version of the original string array.
	 */
	public static String[] normalize(String[] strings) {
		String[] out = new String[strings.length];

		for (int i = 0; i < strings.length; i++) {
			out[i] = normalize(strings[i]);
		}

		return out;
	}

	/**
	 * Normalizes a given String by trimming whitespaces and converting it to lower case.
	 * @param string a String to be normalized.
	 * @return the normalized version of the original String.
	 */
	public static String normalize(String string) {
		if (string == null) {
			return null;
		}
		return string.trim().toLowerCase();
	}

	/**
	 * Normalizes the Strings in a given array by trimming all elements and converting them to lower case.
	 * @param strings a String collection with elements to be normalized. The original contents of the collection will be modified.
	 */
	public static void normalize(Collection<String> strings) {
		HashSet<String> normalized = new HashSet<String>(strings.size());
		for (String string : strings) {
			normalized.add(string.trim().toLowerCase());
		}

		strings.clear();
		strings.addAll(normalized);
	}

}
