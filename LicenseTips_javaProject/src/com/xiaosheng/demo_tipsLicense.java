package com.xiaosheng;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.TextArea;
import java.awt.event.MouseEvent;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xiaosheng.utils.POIUtil;

public class demo_tipsLicense {

	public static void main(String[] args) {
		
		TextArea taArea = new TextArea();
		
		System.out.println("-------效生声纹引擎许可期限提示-------");

		Cell cell = null;
		Cell cell2 = null;

		String fileToBeRead = "\\\\192.168.18.210\\xiaosheng\\公用\\声纹引擎许可期限文档.xlsx";

		Workbook workbook;

		StringBuffer sb = new StringBuffer();

		try {

			int indexOf = fileToBeRead.indexOf(".xlsx");

			if (fileToBeRead.indexOf(".xlsx") > -1) {
				workbook = new XSSFWorkbook(new FileInputStream(fileToBeRead));
			} else {
				workbook = new HSSFWorkbook(new FileInputStream(fileToBeRead));
			}

			Sheet sheet = workbook.getSheet("Sheet1"); // 创建对工作表的引用
			int rows = sheet.getPhysicalNumberOfRows();// 获取表格的行数
			int columns = 0;
			for (int r = 0; r < rows; r++) { // 循环遍历表格的行
				if (r == 0) {
					// 在第一行标题行计算出列宽度,因为数据行中可能会有空值
					columns = sheet.getRow(r).getLastCellNum();// 列
					continue;
				}

				Row row = sheet.getRow(r); // 获取单元格中指定的行对象
				if (row != null) {
					// int cells = row.getPhysicalNumberOfCells();// 获取一行中的单元格数
					// int cells = row.getLastCellNum();// 获取一行中最后单元格的编号（从1开始）
					cell = row.getCell((short) 0);//项目名称读取
					cell2 = row.getCell((short) 2);//到期日期读取

				}				
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				//System.out.println(dateFormat.format(date));

				DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				Date d1 = df.parse(POIUtil.getCellValue(cell2));
				Date d2 = df.parse(df.format(new Date()));
				//System.out.println(d2);
				long diff = d1.getTime() - d2.getTime();// 这样得到的差值是微秒级别
				long days = diff / (1000 * 60 * 60 * 24);//得到剩余天数

				if (days == 60 || days == 59 || days == 61) {
					System.out.println(POIUtil.getCellValue(cell)+"，剩余两个月");
				}
				
				if (days == 30 || days == 29 || days == 31) {
					System.out.println(POIUtil.getCellValue(cell)+"，剩余一个月");
				}
				
				if (days < 8 && days > 0) {
					System.out.println(POIUtil.getCellValue(cell)+"，剩余天数：" + days);
				}
				
				//System.out.println(POIUtil.getCellValue(cell)+"，剩余天数：-->" + days);

				
				
				if (days == 60 || days == 59 || days == 61) {
					//sb.append(cell).append(",许可期限剩余 两个月。");
					taArea.append(cell.toString()+",许可期限剩余 两个月。"+ "\r\n");
				}
				
				if (days == 30 || days == 29 || days == 31) {
					//sb.append(cell).append(",许可期限剩余 一个月。");
					taArea.append(cell.toString()+",许可期限剩余 一个月。"+ "\r\n");
				}

				if (days < 8 && days > 0) {
					//sb.append(cell).append(",许可期限剩余：").append(days + "天。");
					taArea.append(cell.toString()+",许可期限剩余："+days+"天。"+ "\r\n");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			
				JFrame jFrame = new JFrame();

				JPanel jPanel = new JPanel();	
				
				taArea.setBackground(Color.white);
				taArea.setSize(90, 20);
				taArea.setRows(17);
				taArea.setColumns(68);
				taArea.setEditable(false);
				
				jPanel.add(taArea);
				jFrame.add(jPanel);
				//jFrame.add(taArea);

				jFrame.setLayout(new FlowLayout(1));
				jFrame.setTitle("引擎许可期限提醒");
				jFrame.setSize(550, 360);
				jFrame.setVisible(true);
				jFrame.setDefaultCloseOperation(3);
				jFrame.setLocationRelativeTo(null);

				// System.out.println(sb);
			
		} catch (HeadlessException e) {

			e.printStackTrace();
		}

	}

	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
