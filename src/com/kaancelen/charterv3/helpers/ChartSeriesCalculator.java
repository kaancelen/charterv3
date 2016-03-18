package com.kaancelen.charterv3.helpers;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartSeries;

import com.kaancelen.charterv3.models.JobRecord;
import com.kaancelen.charterv3.utils.Constants;
import com.kaancelen.charterv3.utils.LabelComparator;
import com.kaancelen.charterv3.utils.MonthComparator;
import com.kaancelen.charterv3.utils.StringComparator;

public class ChartSeriesCalculator implements Serializable {

	private static final long serialVersionUID = -5487042240791691821L;
	
	public static final int TOPLAM = 1;
	public static final int RAPOR = 2;
	public static final int CEK = 3;
	public static final int BANKA = 4;

	/**
	 * @param jobRecords
	 * @return
	 */
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
	
	/**
	 * @param jobRecords
	 * @return
	 */
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
	
	/**
	 * @param jobRecords
	 * @return
	 */
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
	
	/**
	 * @param jobRecords
	 * @param type
	 * @return
	 */
	public static ChartSeries DepartmentReport(List<JobRecord> jobRecords, int type) {
		Map<Object, Number> departmentMap = new TreeMap<Object, Number>(new LabelComparator());
		departmentMap.put(Constants.DEPARTMENT_LABELS[0], 0);//toplam
		departmentMap.put(Constants.DEPARTMENT_LABELS[1], 0);//rapor
		departmentMap.put(Constants.DEPARTMENT_LABELS[2], 0);//olumlu rapor
		departmentMap.put(Constants.DEPARTMENT_LABELS[3], 0);//olumsuz rapor
		departmentMap.put(Constants.DEPARTMENT_LABELS[4], 0);//çek
		departmentMap.put(Constants.DEPARTMENT_LABELS[5], 0);//olumlu çek
		departmentMap.put(Constants.DEPARTMENT_LABELS[6], 0);//olumsuz çek
		departmentMap.put(Constants.DEPARTMENT_LABELS[7], 0);//banka
		
		for (JobRecord jobRecord : jobRecords) {
			switch (type) {
			case TOPLAM:
				departmentMap.put(Constants.DEPARTMENT_LABELS[7], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[7]));
				break;
			case RAPOR:
				if(jobRecord.getType().contains(Constants.rapor)){	//Toplam rapor
					departmentMap.put(Constants.DEPARTMENT_LABELS[0], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[0]));
					if(!jobRecord.getResult().contains(Constants.olumsuz)){ //olumlu rapor
						departmentMap.put(Constants.DEPARTMENT_LABELS[1], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[1]));
					}
					if(jobRecord.getResult().contains(Constants.olumsuz)){ //olumsuz rapor
						departmentMap.put(Constants.DEPARTMENT_LABELS[2], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[2]));
					}
				}
				break;
			case CEK:
				if(jobRecord.getType().contains(Constants.cek)){	//Toplam �ek
					departmentMap.put(Constants.DEPARTMENT_LABELS[3], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[3]));
					if(!jobRecord.getResult().contains(Constants.olumsuz)){ //olumlu �ek
						departmentMap.put(Constants.DEPARTMENT_LABELS[4], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[4]));
					}
					if(jobRecord.getResult().contains(Constants.olumsuz)){ //olumsuz �ek
						departmentMap.put(Constants.DEPARTMENT_LABELS[5], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[5]));
					}
				}
				break;
			case BANKA:
				if(jobRecord.getType().contains(Constants.banka)){
					departmentMap.put(Constants.DEPARTMENT_LABELS[6], 1 + (Integer)departmentMap.get(Constants.DEPARTMENT_LABELS[6]));
				}
				break;
			}
		}
		
		ChartSeries chartSeries = new ChartSeries();
		chartSeries.setData(departmentMap);
		return chartSeries;
	}
	
	/**
	 * @param jobRecords
	 * @param type
	 * @return
	 */
	public static LineChartSeries MonthlyReport(List<JobRecord> jobRecords, int type){
		Map<Object, Number> monthlyMap = new TreeMap<Object, Number>(new MonthComparator());
		String label = "";
		Integer oldValue = null;
		
		for (JobRecord jobRecord : jobRecords) {
			switch (type) {
			case TOPLAM:
				oldValue = (Integer) monthlyMap.get(jobRecord.getMonth());
				monthlyMap.put(jobRecord.getMonth(), 1 + (oldValue==null?0:oldValue));
				break;
			case RAPOR:
				if(jobRecord.getType() != null && jobRecord.getType().contains(Constants.rapor)){
					oldValue = (Integer) monthlyMap.get(jobRecord.getMonth());
					monthlyMap.put(jobRecord.getMonth(), 1 + (oldValue==null?0:oldValue));
				}
				break;
			case CEK:
				if(jobRecord.getType() != null && jobRecord.getType().contains(Constants.cek)){
					oldValue = (Integer) monthlyMap.get(jobRecord.getMonth());
					monthlyMap.put(jobRecord.getMonth(), 1 + (oldValue==null?0:oldValue));
				}
				break;
			case BANKA:
				if(jobRecord.getType() != null && jobRecord.getType().contains(Constants.banka)){
					oldValue = (Integer) monthlyMap.get(jobRecord.getMonth());
					monthlyMap.put(jobRecord.getMonth(), 1 + (oldValue==null?0:oldValue));
				}
				break;
			}
		}
		
		switch (type) {
			case TOPLAM: label="Toplam";break;
			case RAPOR: label="Rapor Sayısı";break;
			case CEK: label="Çek Sayısı";break;
			case BANKA: label="Banka Sayısı";break;
		}
		
		LineChartSeries lineChartSeries = new LineChartSeries(label);
		lineChartSeries.setData(monthlyMap);
		return lineChartSeries;
	}

	/**
	 * @param firstJobRecors, new records 2016
	 * @param secondJobRecords, old records 2015
	 * @param drawedMonth
	 * @param selectedMonths
	 * @return
	 */
	public static ChartSeries CompareReport(List<JobRecord> firstJobRecors, List<JobRecord> secondJobRecords, String drawedMonth, List<String> selectedMonths) {
		Map<Object, Number> compareMap = new TreeMap<Object, Number>();
		for (String month : selectedMonths) {
			compareMap.put(month + "-2016", 0);
			compareMap.put(month + "-2015", 0);
		}
		
		//new 2016 records
		for (JobRecord newJobRecord : firstJobRecors) {
			if(newJobRecord.getMonth().compareTo(drawedMonth) == 0){
				compareMap.put(drawedMonth + "-2016", 1 + (Integer)compareMap.get(drawedMonth + "-2016"));
			}
		}
		
		//old 2015 records
		for (JobRecord oldJobRecord : secondJobRecords) {
			if(oldJobRecord.getMonth().compareTo(drawedMonth) == 0){
				compareMap.put(drawedMonth + "-2015", 1 + (Integer)compareMap.get(drawedMonth + "-2015"));
			}
		}
		
		ChartSeries chartSeries = new ChartSeries();
		chartSeries.setData(compareMap);
		return chartSeries; 
	}
}
