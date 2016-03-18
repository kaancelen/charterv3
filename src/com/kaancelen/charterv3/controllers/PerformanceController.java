package com.kaancelen.charterv3.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;

import com.kaancelen.charterv3.helpers.ChartHelper;
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
	
	private boolean isPersonelChartDrawed;
	private boolean isDepartmentChartDrawed;
	private boolean isMonthlyChartDrawed;
	private boolean isCompareChartDrawed;
	
	private List<Map<Object, Number>> personelData;
	private List<Map<Object, Number>> departmentData; 
	private List<Map<Object, Number>> monthlyData;
	
	private ExcelFile firstExcel;
	private ExcelFile secondExcel;

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
			
			Util.showMessage(FacesMessage.SEVERITY_INFO, Messages.SUCCESS, Messages.FILE_UPLOAD_SUCCESS);
			
		} catch (IOException e) {
			Util.showMessage(FacesMessage.SEVERITY_FATAL, Messages.ERROR, e.getLocalizedMessage());
			System.err.println("PerformanceController : " + e.getLocalizedMessage());
		}
	}
	
	public void drawReport(ActionEvent actionEvent) {
		System.out.println("PerformanceController#drawReport");
		
		if(excelFileList.isEmpty()){
			Util.showMessage(FacesMessage.SEVERITY_ERROR, Messages.ERROR, Messages.NEED_1_FILE);
		}else{
			if(!(isPersonelChartDrawed || isDepartmentChartDrawed || isMonthlyChartDrawed)){
				//get excel file
				firstExcel = excelFileList.get(0);
				//draw performance
				personelChart = ChartHelper.drawPerformans(firstExcel.getJobRecordList(), ChartHelper.PERSONEL_PERFORMANCE);
				ChartHelper.checkPersonelChart(personelChart, firstExcel.getPersonelList());
				personelData = ChartHelper.calculatePersonelData(personelChart);
				isPersonelChartDrawed = true;
				
				//draw department
				departmentChart = ChartHelper.drawPerformans(firstExcel.getJobRecordList(), ChartHelper.DEPARTMENT_PERFORMANCE);
				departmentData = ChartHelper.calculateDepartmentData(departmentChart);
				isDepartmentChartDrawed = true;
				
				//draw monthly
				monthlyChart = ChartHelper.drawPerformansMonthly(firstExcel.getJobRecordList());
				ChartHelper.checkMonths(monthlyChart, firstExcel.getMonthList());
				monthlyData = ChartHelper.calculateMonthsData(monthlyChart);
				isMonthlyChartDrawed = true;
			}
		}
    }
	
	public void drawCompareReport(ActionEvent actionEvent) {
		System.out.println("PerformanceController#drawCompareReport");
		
		if(excelFileList.size() != 2){
			Util.showMessage(FacesMessage.SEVERITY_ERROR, Messages.ERROR, Messages.NEED_2_FILE);
			return;
		}
		if(!isCompareChartDrawed){
			//get excel file
			firstExcel = excelFileList.get(0);	//new excel
			secondExcel = excelFileList.get(1);	//old excel
			//draw compare
			compareChart = ChartHelper.drawCompareMonthly(firstExcel.getJobRecordList(), secondExcel.getJobRecordList(), firstExcel.getMonthList());
			isCompareChartDrawed = true;
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

	public boolean isPersonelChartDrawed() {
		return isPersonelChartDrawed;
	}

	public void setPersonelChartDrawed(boolean isPersonelChartDrawed) {
		this.isPersonelChartDrawed = isPersonelChartDrawed;
	}

	public boolean isDepartmentChartDrawed() {
		return isDepartmentChartDrawed;
	}

	public void setDepartmentChartDrawed(boolean isDepartmentChartDrawed) {
		this.isDepartmentChartDrawed = isDepartmentChartDrawed;
	}

	public boolean isMonthlyChartDrawed() {
		return isMonthlyChartDrawed;
	}

	public void setMonthlyChartDrawed(boolean isMonthlyChartDrawed) {
		this.isMonthlyChartDrawed = isMonthlyChartDrawed;
	}

	public boolean isCompareChartDrawed() {
		return isCompareChartDrawed;
	}

	public void setCompareChartDrawed(boolean isCompareChartDrawed) {
		this.isCompareChartDrawed = isCompareChartDrawed;
	}

	public List<Map<Object, Number>> getPersonelData() {
		return personelData;
	}

	public void setPersonelData(List<Map<Object, Number>> personelData) {
		this.personelData = personelData;
	}

	public List<Map<Object, Number>> getDepartmentData() {
		return departmentData;
	}

	public void setDepartmentData(List<Map<Object, Number>> departmentData) {
		this.departmentData = departmentData;
	}

	public List<Map<Object, Number>> getMonthlyData() {
		return monthlyData;
	}

	public void setMonthlyData(List<Map<Object, Number>> monthlyData) {
		this.monthlyData = monthlyData;
	}

	public ExcelFile getFirstExcel() {
		return firstExcel;
	}

	public void setFirstExcel(ExcelFile firstExcel) {
		this.firstExcel = firstExcel;
	}

	public ExcelFile getSecondExcel() {
		return secondExcel;
	}

	public void setSecondExcel(ExcelFile secondExcel) {
		this.secondExcel = secondExcel;
	}
	
	public String[] getDepartmentColumns(){
		return Constants.DEPARTMENT_LABELS;
	}
}
