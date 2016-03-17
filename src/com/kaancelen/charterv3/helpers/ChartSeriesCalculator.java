package com.kaancelen.charterv3.helpers;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.chart.ChartSeries;

import com.kaancelen.charterv3.models.JobRecord;
import com.kaancelen.charterv3.utils.Constants;
import com.kaancelen.charterv3.utils.StringComparator;

public class ChartSeriesCalculator {

	public static ChartSeries PersonelReport(List<JobRecord> jobRecords) {
		Map<Object, Number> personelReportMap = new TreeMap<Object, Number>(new StringComparator());
		
		for (JobRecord jobRecord : jobRecords) {
			//if it is not null and RAPOR than it is rapor
			if(jobRecord.getType().contains(Constants.rapor)){
				//if there are no records before it should be '0'
				Integer oldValue = (Integer) personelReportMap.get(jobRecord.getPersonel());
				personelReportMap.put(jobRecord.getPersonel(), 1 + (oldValue==null?0:oldValue));//increase counter 1 for this personel
			}
		}
		
		ChartSeries chartSeries = new ChartSeries("Rapor Sayısı");
		chartSeries.setData(personelReportMap);
		return chartSeries;
	}
	
	public static ChartSeries PersonelCek(List<JobRecord> jobRecords) {
		Map<Object, Number> personelCekMap = new TreeMap<Object, Number>(new StringComparator());
		
		for (JobRecord jobRecord : jobRecords) {
			//if desc contains 'memzu' then it is memzu record
			if(jobRecord.getType().contains(Constants.cek)){
				//if there are no records before it should be '0'
				Integer oldValue = (Integer) personelCekMap.get(jobRecord.getPersonel());
				personelCekMap.put(jobRecord.getPersonel(), 1 + (oldValue==null?0:oldValue));//increase counter 1 for this personel
			}
		}
		
		ChartSeries chartSeries = new ChartSeries("Çek sayısı");
		chartSeries.setData(personelCekMap);
		return chartSeries;
	}
	
	public static ChartSeries PersonelBanka(List<JobRecord> jobRecords) {
		Map<Object, Number> personelMemzucMap = new TreeMap<Object, Number>(new StringComparator());
		
		for (JobRecord jobRecord : jobRecords) {
			//if desc contains 'memzu' then it is memzu record
			if(jobRecord.getType().contains(Constants.banka)){
				//if there are no records before it should be '0'
				Integer oldValue = (Integer) personelMemzucMap.get(jobRecord.getPersonel());
				personelMemzucMap.put(jobRecord.getPersonel(), 1 + (oldValue==null?0:oldValue));//increase counter 1 for this personel
			}
		}
		
		ChartSeries chartSeries = new ChartSeries("Banka sayısı");
		chartSeries.setData(personelMemzucMap);
		return chartSeries;
	}
	
}
