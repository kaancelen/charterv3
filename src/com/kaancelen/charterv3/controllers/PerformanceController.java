package com.kaancelen.charterv3.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.kaancelen.charterv3.models.ExcelFile;
import com.kaancelen.charterv3.utils.Constants;
import com.kaancelen.charterv3.utils.Messages;
import com.kaancelen.charterv3.utils.Util;

@ManagedBean
@ViewScoped
public class PerformanceController implements Serializable{
	
	private static final long serialVersionUID = -3052039928610205832L;
	
	private List<ExcelFile> excelFileList;
	
	private String[] monthList;
	private List<String> selectedMonths;
	
	private BarChartModel personelChart;
	private BarChartModel departmentChart;
	private LineChartModel monthlyChart;
	private BarChartModel compareChart;

	@PostConstruct
	public void init(){
		System.out.println("PerformanceController#init");
		excelFileList = new ArrayList<ExcelFile>();
		monthList = Constants.MONTH_ARRAY;
		resetCharts();
	}
	
	@PreDestroy
	public void destroy(){
		System.out.println("PerformanceController#destroy");
	}
	
	private void resetCharts(){
		BarChartModel model = new BarChartModel();
        model.addSeries(new ChartSeries("PREVENT NULL POINTER EXCEPTION"));
        
        personelChart = departmentChart = compareChart = model;//PREVENET NULL POINTER EXCEPTION
        monthlyChart = new LineChartModel();//PREVENET NULL POINTER EXCEPTION
	}
	
	public void handleFileUpload(FileUploadEvent event){
		System.out.println("PerformanceController#handleFileUpload");
		
		ExcelFile excelFile = new ExcelFile();
		excelFile.setName(event.getFile().getFileName());
		excelFile.setPath(Constants.ROOT_PATH + excelFile.getSafeFileName(event.getFile().getFileName()));
		excelFile.setUploadDate(new Date());
		
		try {
			
			excelFile.saveFile(event.getFile().getInputstream());
			excelFile.loadData();
			excelFileList.add(excelFile);
			
			Util.showMessage(Messages.SUCCESS, Messages.FILE_UPLOAD_SUCCESS);
			
		} catch (IOException e) {
			Util.showMessage(Messages.ERROR, e.getLocalizedMessage());
			System.err.println("PerformanceController : " + e.getLocalizedMessage());
		}
	}
	
	public void drawReport(ActionEvent actionEvent) {
		System.out.println("PerformanceController#drawReport");
		
		for (String month : selectedMonths) {
			System.out.println(month);
		}
    }
	
	public void drawCompareReport(ActionEvent actionEvent) {
		System.out.println("PerformanceController#drawCompareReport");
		
		for (String month : selectedMonths) {
			System.out.println(month);
		}
    }
	
/**************************************************GETTER SETTER***************************************************/
	public List<ExcelFile> getExcelFileList() {
		return excelFileList;
	}

	public void setExcelFileList(List<ExcelFile> excelFileList) {
		this.excelFileList = excelFileList;
	}

	public List<String> getSelectedMonths() {
		return selectedMonths;
	}

	public void setSelectedMonths(List<String> selectedMonths) {
		this.selectedMonths = selectedMonths;
	}

	public String[] getMonthList() {
		return monthList;
	}

	public void setMonthList(String[] monthList) {
		this.monthList = monthList;
	}

	public BarChartModel getPersonelChart() {
		return personelChart;
	}

	public void setPersonelChart(BarChartModel personelChart) {
		this.personelChart = personelChart;
	}

	public BarChartModel getDepartmentChart() {
		return departmentChart;
	}

	public void setDepartmentChart(BarChartModel departmentChart) {
		this.departmentChart = departmentChart;
	}

	public LineChartModel getMonthlyChart() {
		return monthlyChart;
	}

	public void setMonthlyChart(LineChartModel monthlyChart) {
		this.monthlyChart = monthlyChart;
	}

	public BarChartModel getCompareChart() {
		return compareChart;
	}

	public void setCompareChart(BarChartModel compareChart) {
		this.compareChart = compareChart;
	}
	
}
