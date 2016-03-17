package com.kaancelen.charterv3.utils;

import java.io.Serializable;
import java.util.Comparator;

public class LabelComparator implements Comparator<Object>, Serializable{

	private static final long serialVersionUID = 3866795477630120248L;

	@Override
	public int compare(Object o1, Object o2) {
		String label1 = (String)o1;
		String label2 = (String)o2;
		
		int index1 = 4;
		int index2 = 4;
		for (int i=0; i < Constants.DEPARTMENT_LABELS.length; i++) {
			String label = Constants.DEPARTMENT_LABELS[i];
			if(label1.compareTo(label) == 0){
				index1 = i;
			}
			if(label2.compareTo(label) == 0){
				index2 = i;
			}
		}
		
		return index1 - index2;
	}

}