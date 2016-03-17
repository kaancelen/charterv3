package com.kaancelen.charterv3.helpers;

import java.io.Serializable;
import java.util.List;

import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.CategoryAxis;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.kaancelen.charterv3.models.JobRecord;

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
}
