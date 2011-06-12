package backend;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Util {
	/**
	 * Sort a java.util.Map by its values, returning a ordered Map
	 * 
	 * This function takes any map and converts it into a Map that is sorted by
	 * the values. The order is predictable, as we are using LinkedHashMap for
	 * the ordering.
	 * 
	 * @param map
	 *            Input map that should be sorted by value
	 * @return A copy of the input map, sorted by values
	 **/
	public static <K, V> Map<K, V> sortMapByValue(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@SuppressWarnings("unchecked")
			// Suppress "Unchecked cast" for Comparable<V>
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return ((Comparable<V>) (o1.getValue())).compareTo(o2
						.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (int i = list.size() - 1; i > -1; i--) {
			Map.Entry<K, V> entry = list.get(i);
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}

	public static <K, V> Map<K, V> sortMapByValueInvers(Map<K, V> map) {
		List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			@SuppressWarnings("unchecked")
			// Suppress "Unchecked cast" for Comparable<V>
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return ((Comparable<V>) (o1.getValue())).compareTo(o2
						.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (int i = 0; i < list.size(); i++) {
			Map.Entry<K, V> entry = list.get(i);
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
}
