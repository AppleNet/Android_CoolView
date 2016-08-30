package com.llc.android_coolview.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.llc.android_coolview.kobe.bean.Station;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class OfficeUtils {

	public static List<Station> readExcel(InputStream filepath) {

		List<Station> stationList = new ArrayList<>();
		try {
			// 创建输入流，读取Excel
			InputStream is = filepath;
			// jxl提供的Workbook类
			Workbook wb = Workbook.getWorkbook(is);
			// Excel的页签数量
			int sheet_size = wb.getNumberOfSheets();
			for (int index = 0; index < sheet_size; index++) {
				// 每个页签创建一个Sheet对象
				Sheet sheet = wb.getSheet(index);
				// sheet.getRows()返回该页的总行数
				// 第一行列名称，不读取
				for (int i = 1; i < sheet.getRows(); i++) {
					if ((null != sheet.getCell(1, i).getContents()
							&& !sheet.getCell(1, i).getContents().equals(""))
							&& (null != sheet.getCell(2, i).getContents()
							&& !sheet.getCell(2, i).getContents()
							.equals(""))) {
						Station station=new Station();
						String name = sheet.getCell(0, i).getContents();
						station.setName(name);
						String pYin=sheet.getCell(2, i).getContents();
						station.setpYin(pYin);
						String sPyin=sheet.getCell(1, i).getContents();
						station.setSimplePyin(sPyin);
						stationList.add(station);
					}
				}
			}
			return stationList;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
