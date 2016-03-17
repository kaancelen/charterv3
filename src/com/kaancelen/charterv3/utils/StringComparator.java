package com.kaancelen.charterv3.utils;

import java.io.Serializable;
import java.util.Comparator;

public class StringComparator implements Comparator<Object>, Serializable{

	private static final long serialVersionUID = 7988989399796281800L;

	@Override
	public int compare(Object o1, Object o2) {
		String s1 = (String)o1;
		String s2 = (String)o2;
		return s1.compareTo(s2);
	}

}