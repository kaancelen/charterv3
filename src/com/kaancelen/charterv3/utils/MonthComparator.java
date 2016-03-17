package com.kaancelen.charterv3.utils;

import java.io.Serializable;
import java.util.Comparator;

public class MonthComparator implements Comparator<Object>, Serializable{

	private static final long serialVersionUID = 5481904919162981377L;

	@Override
	public int compare(Object arg0, Object arg1) {
		String month1 = Util.replaceTurkishChars(((String) arg0));
		String month2 = Util.replaceTurkishChars(((String) arg1));
		
		if(month1.compareTo(month2) == 0){
			return 0;
		}
		
		for (String month : Constants.MONTH_ARRAY) {
			if(month1.compareTo(month) == 0){
				return -1;
			}
			if(month2.compareTo(month) == 0){
				return 1;
			}
		}
		throw new IllegalArgumentException(month1 + " or " + month2 + " is not appripiote for this method");
	}

}