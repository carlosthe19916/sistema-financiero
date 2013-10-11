package org.venturabank.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapToListClass {

	public static <T, S> List<Map.Entry<T, S>> mapToList(Map<T, S> map) {

		if (map == null) {
			return null;
		}

		List<Map.Entry<T, S>> list = new ArrayList<Map.Entry<T, S>>();
		list.addAll(map.entrySet());

		return list;
	}
}
