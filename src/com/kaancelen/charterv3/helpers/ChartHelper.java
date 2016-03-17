package com.kaancelen.charterv3.helpers;

import java.util.List;

import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;

import com.kaancelen.charterv3.models.JobRecord;

public class ChartHelper {
	
	public static final int PERSONEL_PERFORMANCE = 1;
	public static final int DEPARTMENT_PERFORMANCE = 2;

	public static BarChartModel drawPerformans(List<JobRecord> jobRecords, int type) {
		BarChartModel barChartModel = new BarChartModel();
		String title = null;
		
		switch (type) {
		case 1:
			title = "ÇALIŞAN PERFORMANS";
			barChartModel.setLegendPosition("ne");
			barChartModel.addSeries(ChartSeriesCalculator.PersonelReport(jobRecords));
			barChartModel.addSeries(ChartSeriesCalculator.PersonelCek(jobRecords));
			barChartModel.addSeries(ChartSeriesCalculator.PersonelBanka(jobRecords));
			break;
		}
		
		barChartModel.setTitle(title);
		return barChartModel;
	}
	
	public static void checkPersonelChart(BarChartModel model, List<String> personelList){
		for (ChartSeries chartSeries : model.getSeries()) {
			for (String personel : personelList) {
				if(!chartSeries.getData().containsKey(personel))
					chartSeries.getData().put(personel, 0);
			}
		}
	}
}
