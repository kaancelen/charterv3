package com.kaancelen.charterv3.utils;

import java.io.Serializable;

public class Constants implements Serializable{

	private static final long serialVersionUID = 6475708594988786078L;
	
	public static final String HOUR_FORMAT = "HH:mm";
	public static final String ROOT_PATH = "C:\\CHARTER_FILES\\";
	public static final String[] MONTH_ARRAY = {"OCAK", "SUBAT", "MART", "NISAN", "MAYIS", "HAZIRAN", "TEMMUZ", "AGUSTOS", "EYLUL", "EKIM", "KASIM", "ARALIK"};
	public static final String[] DEPARTMENT_LABELS = {"Rapor", "Rapor/Olumlu", "Rapor/Olumsuz", "Çek", "Çek/Olumlu", "Çek/Olumsuz", "Banka", "Toplam"};
	
	public static final String cek = "çek";
	public static final String rapor = "rapor";
	public static final String banka = "banka";
	public static final String olumlu = "olumlu";
	public static final String olumsuz = "olumsuz";
}
