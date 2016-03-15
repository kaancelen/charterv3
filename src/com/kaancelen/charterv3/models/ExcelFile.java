package com.kaancelen.charterv3.models;

import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.kaancelen.charterv3.utils.Constants;
import com.kaancelen.charterv3.utils.CustomHelper;
import com.kaancelen.charterv3.utils.Util;

public class ExcelFile implements Serializable{

	private static final long serialVersionUID = 2794471268251049556L;
	private String name;
	private String path;
	private String uploadDate;
	private List<JobRecord> jobRecordList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<JobRecord> getJobRecordList() {
		return jobRecordList;
	}
	
	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	
	public void setUploadDate(Date uploadDate) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.HOUR_FORMAT);
		this.uploadDate = dateFormat.format(uploadDate);
	}

	public String getSafeFileName(String originalFileName){
		return UUID.randomUUID().toString().substring(16) + "." + Util.getFileExtension(originalFileName);
	}
	
	public void saveFile(InputStream inputStream){
		Util.copyFile(path, inputStream);
	}
	
	public void loadData(){
		jobRecordList = CustomHelper.getJobRecordsFromExcelFile(path);
	}
}
