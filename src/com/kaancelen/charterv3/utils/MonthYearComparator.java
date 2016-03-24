package com.kaancelen.charterv3.utils;

import java.io.Serializable;
import java.util.Comparator;

public class MonthYearComparator implements Comparator<Object>, Serializable{

	private static final long serialVersionUID = 3043478774421250458L;

	@Override
	public int compare(Object arg0, Object arg1) {
		String[] monthYear1 = Util.replaceTurkishChars(((String) arg0)).split("-");
		String[] monthYear2 = Util.replaceTurkishChars(((String) arg1)).split("-");
		
		String month1 = monthYear1[0];
		String month2 = monthYear2[0];
		int year1 = Integer.valueOf(monthYear1[1]);
		int year2 = Integer.valueOf(monthYear2[1]);
		
		if(month1.compareTo(month2) == 0){
			if(year1 > year2){
				return 1;
			}
			if(year2 > year1){
				return -1;
			}
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
