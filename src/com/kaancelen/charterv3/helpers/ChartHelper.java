package com.kaancelen.charterv3.helpers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.kaancelen.charterv3.models.JobRecord;
import com.kaancelen.charterv3.utils.Constants;
import com.kaancelen.charterv3.utils.LabelComparator;
import com.kaancelen.charterv3.utils.StringComparator;

public class ChartHelper implements Serializable{
	
	private static final long serialVersionUID = 339845787886029908L;
	
	public static final int PERSONEL_PERFORMANCE = 1;
	public static final int DEPARTMENT_PERFORMANCE = 2;

	public static BarChartModel drawPerformans(List<JobRecord> jobRecords, int type) {
		BarChartModel barChartModel = new BarChartModel();
		String title = null;
		
		switch (type) {
		case PERSONEL_PERFORMANCE:
			title = "ÇALIŞAN PERFORMANS";
			barChartModel.setLegendPosition("ne");
			barChartModel.addSeries(ChartSeriesCalculator.PersonelReport(jobRecords));
			barChartModel.addSeries(ChartSeriesCalculator.PersonelCek(jobRecords));
			barChartModel.addSeries(ChartSeriesCalculator.PersonelBanka(jobRecords));
			break;
		case DEPARTMENT_PERFORMANCE:
			title = "BÖLÜM PERFORMANS";
			barChartModel.setStacked(true);
			barChartModel.addSeries(ChartSeriesCalculator.DepartmentReport(jobRecords, ChartSeriesCalculator.RAPOR));//Rapor
			barChartModel.addSeries(ChartSeriesCalculator.DepartmentReport(jobRecords, ChartSeriesCalculator.CEK));//Çek
			barChartModel.addSeries(ChartSeriesCalculator.DepartmentReport(jobRecords, ChartSeriesCalculator.BANKA));//banka
			barChartModel.addSeries(ChartSeriesCalculator.DepartmentReport(jobRecords, ChartSeriesCalculator.TOPLAM));//Toplam
			break;
		}
		
		barChartModel.setTitle(title);
		return barChartModel;
	}
	
	public static LineChartModel drawPerformansMonthly(List<JobRecord> jobRecords){
		LineChartModel lineChartModel = new LineChartModel();
		lineChartModel.setTitle("AYLIK PERFORMANS");
		lineChartModel.setLegendPosition("ne");
		//lineChartModel.setStacked(true);
		lineChartModel.getAxes().put(AxisType.X, new CategoryAxis());
		Axis yAxis = lineChartModel.getAxis(AxisType.Y);
		yAxis.setMin(0);
		
		lineChartModel.addSeries(ChartSeriesCalculator.MonthlyReport(jobRecords, ChartSeriesCalculator.RAPOR));//Rapor
		lineChartModel.addSeries(ChartSeriesCalculator.MonthlyReport(jobRecords, ChartSeriesCalculator.CEK));//Çek
		lineChartModel.addSeries(ChartSeriesCalculator.MonthlyReport(jobRecords, ChartSeriesCalculator.BANKA));//banka
		lineChartModel.addSeries(ChartSeriesCalculator.MonthlyReport(jobRecords, ChartSeriesCalculator.TOPLAM));//Toplam
		
		return lineChartModel;
	}
	
	public static void checkPersonelChart(BarChartModel model, List<String> personelList){
		for (ChartSeries chartSeries : model.getSeries()) {
			for (String personel : personelList) {
				if(!chartSeries.getData().containsKey(personel))
					chartSeries.getData().put(personel, 0);
			}
		}
	}
	
	public static void checkMonths(LineChartModel model, List<String> monthList){
		for (ChartSeries chartSeries : model.getSeries()) {
			for (String month : monthList) {
				if(!chartSeries.getData().containsKey(month))
					chartSeries.getData().put(month, 0);
			}
		}
	}
	
	public static List<Map<Object, Number>> calculatePersonelData(BarChartModel personelChart){
		List<Map<Object, Number>> personelData = new ArrayList<Map<Object, Number>>();
		Map<Object, Number> report = personelChart.getSeries().get(0).getData();
		Map<Object, Number> cek = personelChart.getSeries().get(1).getData();
		Map<Object, Number> banka = personelChart.getSeries().get(2).getData();
		Map<Object, Number> total = new TreeMap<>(new StringComparator());
		
		for (Entry<Object, Number> entry : personelChart.getSeries().get(0).getData().entrySet()){
			total.put(entry.getKey(), (Integer)report.get(entry.getKey()) + (Integer)banka.get(entry.getKey()) + (Integer)cek.get(entry.getKey()));
		}
		
		personelData.add(report);//report
		personelData.add(cek);//çek
		personelData.add(banka);//banka
		personelData.add(total);//total
		
		return personelData;
	}
	
	public static List<Map<Object, Number>> calculateDepartmentData(BarChartModel departmentChart){
		List<Map<Object, Number>> departmentData = new ArrayList<Map<Object, Number>>();
		Map<Object, Number> report = departmentChart.getSeries().get(0).getData();//rapor
		Map<Object, Number> cek = departmentChart.getSeries().get(1).getData();//çek
		Map<Object, Number> banka = departmentChart.getSeries().get(2).getData();//banka
		Map<Object, Number> total = departmentChart.getSeries().get(3).getData();//toplam
		Map<Object, Number> row = new TreeMap<Object, Number>(new LabelComparator());
		
		
		row.put(Constants.DEPARTMENT_LABELS[0], report.get(Constants.DEPARTMENT_LABELS[0]));//Rapor
		row.put(Constants.DEPARTMENT_LABELS[1], report.get(Constants.DEPARTMENT_LABELS[1]));//Olumlu rapor
		row.put(Constants.DEPARTMENT_LABELS[2], report.get(Constants.DEPARTMENT_LABELS[2]));//Olumsuz rapor
		row.put(Constants.DEPARTMENT_LABELS[3], cek.get(Constants.DEPARTMENT_LABELS[3]));//çek
		row.put(Constants.DEPARTMENT_LABELS[4], cek.get(Constants.DEPARTMENT_LABELS[4]));//Olumlu çek
		row.put(Constants.DEPARTMENT_LABELS[5], cek.get(Constants.DEPARTMENT_LABELS[5]));//Olumsuz çek
		row.put(Constants.DEPARTMENT_LABELS[6], banka.get(Constants.DEPARTMENT_LABELS[6]));//banka
		row.put(Constants.DEPARTMENT_LABELS[7], total.get(Constants.DEPARTMENT_LABELS[7]));//Toplam
		
		departmentData.add(row);
		
		return departmentData;
	}
	
	public static List<Map<Object, Number>> calculateMonthsData(LineChartModel monthlyChart){
		List<Map<Object, Number>> monthlyData = new ArrayList<Map<Object,Number>>();
		for(int i=0; i<4; i++){
			monthlyData.add(monthlyChart.getSeries().get(i).getData());
		}
		
		return monthlyData;
	}
}
