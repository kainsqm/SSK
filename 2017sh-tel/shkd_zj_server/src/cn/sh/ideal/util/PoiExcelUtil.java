package cn.sh.ideal.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiExcelUtil {

	private static final String FONT_FAMILY = "微软雅黑"; // 字体

	private static final int HEADER_FONT_SIZE = 12; // 表头字体大小

	private static final int DATA_FONT_SIZE = 10; // 数据字体大小

	private static final boolean HEADER_FONT_BOLD = true; // 表头是否粗休

	private static final boolean DATA_FONT_BOLD = false; // 数据是否粗休

	private static final int COLUMN_WIDTH = 3500; // 列宽
	private static Logger log = Logger.getLogger(PoiExcelUtil.class);

	/**
	 * 导出excel
	 * 
	 * @param dataList
	 *            数据集合,集合map中的key为excel表头
	 * @param sheetName
	 *            sheet名称
	 * @param exportFileName
	 *            导出excel名称
	 * @author gilbert
	 * @version 1.0,2015年11月17日
	 */
	@SuppressWarnings("static-access")
	public static void poiCreateExcel2003(
			List<LinkedHashMap<Object, Object>> dataList, String sheetName,
			OutputStream outputStream) {
		try {
			Workbook poiWorkbook = new HSSFWorkbook();
			Sheet sheet = poiWorkbook.createSheet(sheetName);

			int rowSize = 0;
			Map<Object, Object> titleMap = null;
			if (dataList != null && !dataList.isEmpty()) {
				rowSize = dataList.size();
				titleMap = dataList.get(0);
			}

			CellStyle textCellStyle = getTextCellStyle(poiWorkbook);
			CellStyle dateCellStyle = getDateFormatCellStyle(poiWorkbook,
					"yyyy-MM-dd");
			CellStyle numberCellStyle = getNumberCellStyle(poiWorkbook);

			// excel表头
			Row headerRow = sheet.createRow(0);
			CellStyle headerStyle = poiWorkbook.createCellStyle();
			// CellStyle headerStyle = poiWorkbook.createCellStyle();
			Font font = poiWorkbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("微软雅黑");
			headerStyle.setFont(font);
			// 背景色
			headerStyle.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			headerStyle.setFillBackgroundColor(HSSFColor.PALE_BLUE.index);
			headerStyle.setBorderRight(CellStyle.BORDER_THIN);
			/*
			 * setCellStyle(headerRow, headerStyle, font, 11,
			 * HSSFCellStyle.ALIGN_CENTER, false); // 表头样式
			 */int varColumnNumber = 0;
			if (!titleMap.isEmpty()) {
				for (Map.Entry<Object, Object> entry : titleMap.entrySet()) {
					setColumnWidth(sheet, varColumnNumber, COLUMN_WIDTH);
					Cell cell = headerRow.createCell(varColumnNumber);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(headerStyle);
					cell.setCellValue(String.valueOf(entry.getKey()));

					varColumnNumber++;
				}
			}

			for (int i = 0; i < rowSize; i++) {
				Row row = sheet.createRow(i + 1); // 第二行写数据
				Font dataFont = poiWorkbook.createFont();
				setCellStyle(row, numberCellStyle, dataFont, DATA_FONT_SIZE,
						HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD); // 数字单元格样式
				setCellStyle(row, dateCellStyle, dataFont, DATA_FONT_SIZE,
						HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD); // 日期单元格样式
				setCellStyle(row, textCellStyle, dataFont, DATA_FONT_SIZE,
						HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD); // 文本单元格样式
				Map<Object, Object> dataMap = dataList.get(i);
				int varDataColumnNumber = 0;
				for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
					setColumnWidth(sheet, varDataColumnNumber, COLUMN_WIDTH);
					Cell cell = row.createCell(varDataColumnNumber);
					if (entry.getValue() instanceof Integer) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Integer) entry.getValue());
					} else if (entry.getValue() instanceof Double) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Double) entry.getValue());
					} else if (entry.getValue() instanceof Float) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Float) entry.getValue());
					} else if (entry.getValue() instanceof BigDecimal) {
						// cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						// cell.setCellStyle(numberCellStyle);
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						BigDecimal value = (BigDecimal) entry.getValue();
						cell.setCellValue(value.doubleValue());
					} else if (entry.getValue() instanceof Long) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Long) entry.getValue());
					} else if (entry.getValue() instanceof Date) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(dateCellStyle);
						cell.setCellValue((Date) entry.getValue());
					} else if (entry.getValue() instanceof String) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(textCellStyle);
						cell.setCellValue((String) entry.getValue());
					} else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(textCellStyle);
						cell.setCellValue("");
					}
					varDataColumnNumber++;
				}
			}
			poiWorkbook.write(outputStream);
		} catch (Exception e) {
			log.error("" + e);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

	@SuppressWarnings("static-access")
	public static void QcBusinessCreateExcel2003(
			List<LinkedHashMap<Object, Object>> dataList, String sheetName,
			OutputStream outputStream, String bh) {
		try {
			Workbook poiWorkbook = new HSSFWorkbook();
			Sheet sheet = poiWorkbook.createSheet(sheetName);

			int rowSize = 0;
			Map<Object, Object> titleMap = null;
			if (dataList != null && !dataList.isEmpty()) {
				rowSize = dataList.size();
				titleMap = dataList.get(0);
			}

			CellStyle textCellStyle = getTextCellStyle(poiWorkbook);
			CellStyle dateCellStyle = getDateFormatCellStyle(poiWorkbook,
					"yyyy-MM-dd");
			CellStyle numberCellStyle = getNumberCellStyle(poiWorkbook);

			// excel表头
			Row headerRow = sheet.createRow(0);
			CellStyle cellStyle = poiWorkbook.createCellStyle();
			// CellStyle headerStyle = poiWorkbook.createCellStyle();
			Font font = poiWorkbook.createFont();
			font.setColor(Font.COLOR_RED);
			font.setFontHeightInPoints((short) 10);
			font.setFontName("微软雅黑");
			/*
			 * setCellStyle(headerRow, textCellStyle, font, DATA_FONT_SIZE,
			 * HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD);
			 */
			int varColumnNumber2 = 0;
			// setColumnWidth(sheet, varColumnNumber2, 7000);
			cellStyle.setFont(font);
			// 背景色
			Cell cell2 = headerRow.createCell(varColumnNumber2);
			cell2.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue(bh);
			CellRangeAddress region = new CellRangeAddress(0, 0, 0, 3); // 参数都是从O开始
			sheet.addMergedRegion(region);

			Row headerRow2 = sheet.createRow(1);
			CellStyle cellStyle2 = poiWorkbook.createCellStyle();
			// CellStyle headerStyle = poiWorkbook.createCellStyle();
			Font font2 = poiWorkbook.createFont();
			font2.setFontHeightInPoints((short) 11);
			font2.setFontName("微软雅黑");
			setColumnWidth(sheet, varColumnNumber2, 3000);
			cellStyle2.setFont(font2);
			// 背景色
			cellStyle2.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
			cellStyle2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			cellStyle2.setFillBackgroundColor(HSSFColor.PALE_BLUE.index);
			/*
			 * setCellStyle(headerRow2, textCellStyle, font, HEADER_FONT_SIZE,
			 * HSSFCellStyle.ALIGN_CENTER, false); // 表头样式
			 */int varColumnNumber = 0;
			if (!titleMap.isEmpty()) {
				for (Map.Entry<Object, Object> entry : titleMap.entrySet()) {
					setColumnWidth(sheet, varColumnNumber, COLUMN_WIDTH);
					Cell cell = headerRow2.createCell(varColumnNumber);
					cell.setCellType(HSSFCell.CELL_TYPE_STRING);
					cell.setCellStyle(cellStyle2);
					cell.setCellValue(String.valueOf(entry.getKey()));
					varColumnNumber++;
				}
			}

			for (int i = 0; i < rowSize; i++) {
				Row row = sheet.createRow(i + 2); // 第二行写数据
				Font dataFont = poiWorkbook.createFont();
				setCellStyle(row, numberCellStyle, dataFont, DATA_FONT_SIZE,
						HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD); // 数字单元格样式
				setCellStyle(row, dateCellStyle, dataFont, DATA_FONT_SIZE,
						HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD); // 日期单元格样式
				setCellStyle(row, textCellStyle, dataFont, DATA_FONT_SIZE,
						HSSFCellStyle.ALIGN_LEFT, DATA_FONT_BOLD); // 文本单元格样式
				Map<Object, Object> dataMap = dataList.get(i);
				int varDataColumnNumber = 0;
				for (Map.Entry<Object, Object> entry : dataMap.entrySet()) {
					setColumnWidth(sheet, varDataColumnNumber, COLUMN_WIDTH);
					Cell cell = row.createCell(varDataColumnNumber);
					if (entry.getValue() instanceof Integer) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Integer) entry.getValue());
					} else if (entry.getValue() instanceof Double) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Double) entry.getValue());
					} else if (entry.getValue() instanceof Float) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Float) entry.getValue());
					} else if (entry.getValue() instanceof Long) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(numberCellStyle);
						cell.setCellValue((Long) entry.getValue());
					} else if (entry.getValue() instanceof Date) {
						cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
						cell.setCellStyle(dateCellStyle);
						cell.setCellValue((Date) entry.getValue());
					} else if (entry.getValue() instanceof String) {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(textCellStyle);
						cell.setCellValue((String) entry.getValue());
					} else {
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellStyle(textCellStyle);
						cell.setCellValue("");
					}
					varDataColumnNumber++;
				}
			}
			poiWorkbook.write(outputStream);
		} catch (Exception e) {
			log.error("" + e);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}

	
	
	/**
	 * 导出excel
	 * 获取导出模板，在填写数据
	 * @param dataList
	 *            数据集合,集合map中的key为excel表头
	 * @param sheetName
	 *            sheet名称
	 * @param exportFileName
	 *            导出excel名称
	 * @param rowindex
	 *             表示从第几行开始写数据  避免不同的模板表头行数不一样
	 * @author niewenqiang
	 * @version 1.0,2017年4月17日
	 */
	@SuppressWarnings("static-access")
	public static void poiCreateExcelByTemplate2003(
			List<String> liststr,
			OutputStream outputStream,String readpath,int rowindex) {
		try {
			InputStream myxls = new FileInputStream(readpath);
			//Workbook poiWorkbook = new XSSFWorkbook(myxls);
			Workbook poiWorkbook = new HSSFWorkbook(myxls);
			//第一个sheet
			Sheet sheet = poiWorkbook.getSheetAt(0);
			int rowSize = 0;
			if (liststr.size()>0) {
				rowSize = liststr.size();
			}
			CellStyle textCellStyle = getTextCellStyle(poiWorkbook);
			textCellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框   
			for (int i = 0; i < rowSize; i++) {
	            Row row = sheet.getRow(i + rowindex); // 第二行写数据
				String strrole = liststr.get(i); 
				Cell cell=row.getCell(1); //获取指定行中第二列的数据
				cell.setCellStyle(textCellStyle);
				cell.setCellValue((String) strrole);
				
			}
			poiWorkbook.write(outputStream);
		} catch (Exception e) {
			log.info("导出失败 error:"+e.getMessage());
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
			}
		}
	}
	
	
	
	/**
	 * 给数据添加边框
	 * 
	 * @param sheet
	 * @param workbook
	 * @param rowNum
	 * @param columnNum
	 * @author gilbert
	 * @version 1.0,2015年7月21日
	 */
	public static void addBorder(Sheet sheet, Workbook workbook, int rowNum,
			int columnNum) {
		CellStyle regionStyle = workbook.createCellStyle();
		DataFormat format = workbook.createDataFormat();
		regionStyle.setDataFormat(format.getFormat("@"));// 设置单元格格式为"文本"
		regionStyle.setBorderTop(HSSFCellStyle.BORDER_MEDIUM);
		regionStyle.setTopBorderColor(HSSFColor.YELLOW.index);
		regionStyle.setBorderBottom(HSSFCellStyle.BORDER_MEDIUM);
		regionStyle.setBottomBorderColor(HSSFColor.YELLOW.index);
		regionStyle.setBorderLeft(HSSFCellStyle.BORDER_MEDIUM);
		regionStyle.setLeftBorderColor(HSSFColor.YELLOW.index);
		regionStyle.setBorderRight(HSSFCellStyle.BORDER_MEDIUM);
		regionStyle.setRightBorderColor(HSSFColor.YELLOW.index);
		Row row = sheet.getRow(rowNum);
		Cell cell = row.getCell(columnNum);
		cell.setCellStyle(regionStyle);
	}

	/**
	 * 设置字体样式
	 * 
	 * @param cellStyle
	 * @param font
	 * @param fontSize
	 * @param bold
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static void setFont(CellStyle cellStyle, Font font, int fontSize,
			boolean bold) {
		font.setFontName(FONT_FAMILY); // 字体
		font.setBoldweight(bold ? HSSFFont.BOLDWEIGHT_BOLD
				: HSSFFont.BOLDWEIGHT_NORMAL); // 变粗
		font.setFontHeightInPoints((short) fontSize); // 字体大小
		font.setColor(Font.COLOR_NORMAL); // 字体颜色
		font.setItalic(false); // 是否为斜体字
		font.setUnderline(Font.U_NONE); // 下划线
		font.setStrikeout(false); // 是否添加删除线
		cellStyle.setFont(font);
	}

	/**
	 * 设置单元格边框和颜色 CellStyle.BORDER_DOUBLE 双边线 CellStyle.BORDER_THIN 细边线
	 * CellStyle.BORDER_MEDIUM 中等边线 CellStyle.BORDER_DASHED 虚线边线
	 * CellStyle.BORDER_HAIR 小圆点虚线边线 CellStyle.BORDER_THICK 粗边线
	 * 
	 * @param cellStyle
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static void setBorderStyle(CellStyle cellStyle) {
		cellStyle.setBorderTop(CellStyle.BORDER_THIN);
		cellStyle.setTopBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderRight(CellStyle.BORDER_THIN);
		cellStyle.setRightBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		cellStyle.setBottomBorderColor(HSSFColor.BLACK.index);
		cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		cellStyle.setLeftBorderColor(HSSFColor.BLACK.index);
	}

	/**
	 * 对齐方式
	 * 
	 * @param cellStyle
	 * @param align
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static void setAlignStyle(CellStyle cellStyle, short align) {
		cellStyle.setAlignment(align); // 对齐方式
		cellStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); // 垂直居中对齐
	}

	/**
	 * 设置行高
	 * 
	 * @param row
	 * @param height
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static void setRowHeight(Row row, int height) {
		row.setHeightInPoints((short) height);
	}

	/**
	 * 设置列宽度
	 * 
	 * @param sheet
	 * @param columnNumber
	 * @param width
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static void setColumnWidth(Sheet sheet, int columnNumber, int width) {
		sheet.setColumnWidth(columnNumber, width);
	}

	/**
	 * 设置背景填充
	 * 
	 * @param cellStyle
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static void setGround(CellStyle cellStyle) {
		cellStyle.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	}

	/**
	 * 设置数字格式
	 * 
	 * @param cellStyle
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static CellStyle getNumberCellStyle(Workbook poiWorkbook) {
		CellStyle cellStyle = poiWorkbook.createCellStyle();
		cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0")); // 数字格式
		return cellStyle;
	}

	/**
	 * 设置文本格式
	 * 
	 * @param poiWorkbook
	 * @param cellStyle
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static CellStyle getTextCellStyle(Workbook poiWorkbook) {
		CellStyle cellStyle = poiWorkbook.createCellStyle();
		cellStyle.setDataFormat(poiWorkbook.createDataFormat().getFormat("@"));// 设置单元格格式为"文本"
		return cellStyle;
	}

	/**
	 * 设置日期格式
	 * 
	 * @param poiWorkbook
	 * @param cellStyle
	 * @param format
	 * @author gilbert
	 * @version 1.0,2015年11月16日
	 */
	public static CellStyle getDateFormatCellStyle(Workbook poiWorkbook,
			String format) {
		CellStyle cellStyle = poiWorkbook.createCellStyle();
		cellStyle.setDataFormat(poiWorkbook.createDataFormat()
				.getFormat(format)); // 日期格式
		return cellStyle;
	}

	/**
	 * 设置cell样式
	 * 
	 * @param row
	 *            行对象
	 * @param cellStyle
	 *            单元格样式
	 * @param font
	 *            字体对象
	 * @param fontSize
	 *            字段大小
	 * @param align
	 *            对齐方式
	 * @param bold
	 *            是否粗体
	 * @author gilbert
	 * @version 1.0,2015年11月17日
	 */
	public static void setCellStyle(Row row, CellStyle cellStyle, Font font,
			int fontSize, int align, boolean bold) {
		setAlignStyle(cellStyle, (short) align);
		setBorderStyle(cellStyle);
		setFont(cellStyle, font, fontSize, bold);
		// setGround(headerStyle);
		setRowHeight(row, 16);
	}

	/**
	 * poi创建excel
	 * 
	 * @param objList
	 * @param map
	 * @param regionList
	 */
	@SuppressWarnings("static-access")
	public static void poiCreateExcel(List<List<Object>> objList,
			Map<Object, Object> map, List<Map<String, Integer>> regionList,
			List<Map<String, Integer>> cellGroundColorList) {
		try {
			String fileName = (String) map.get("fileName");
			String excelVersion = (String) map.get("excelVersion");
			String propTtitle = (String) map.get("propTtitle");
			String propBlackContent = (String) map.get("propBlackContent");
			String propRedContent = (String) map.get("propRedContent");
			String propSheetName = (String) map.get("propSheetName");
			String propColumnName = (String) map.get("propColumnName");

			ConfigProperties properties = new ConfigProperties();
			String title = properties.getProperty(propTtitle);
			String blackContent = properties.getProperty(propBlackContent)
					.replaceAll("#br#", "\n");
			String redContent = properties.getProperty(propRedContent)
					.replaceAll("#br#", "\n");
			String content = blackContent + "\n" + redContent;
			String sheetName = properties.getProperty(propSheetName);
			String[] columnNames = properties.getProperty(propColumnName)
					.split(",");

			Workbook poiWorkbook = null;
			if (excelVersion != null && excelVersion.equals("2003")) {
				poiWorkbook = new HSSFWorkbook(new FileInputStream(fileName));
			}

			if (excelVersion != null && excelVersion.equals("2007")) {
				poiWorkbook = new XSSFWorkbook(fileName);
			}

			int columnSize = 44;
			Sheet sheet = null;
			if (poiWorkbook != null) {
				sheet = poiWorkbook.getSheet(sheetName);
			}
			if (objList != null && !objList.isEmpty()) {
				List<Object> obj = objList.get(0);
				columnSize = obj.size();
			}
			CellStyle style = null;
			if (poiWorkbook != null) {
				style = poiWorkbook.createCellStyle();
			}
			if (style != null) {

				style.setAlignment(HSSFCellStyle.ALIGN_LEFT);// 水平左对齐;
				style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中对齐
				DataFormat format = poiWorkbook.createDataFormat();
				style.setDataFormat(format.getFormat("@"));// 设置单元格格式为"文本"
			}
			int rowSize = 0;
			if (objList != null) {
				rowSize = objList.size();
			}
			for (int i = 0; i < rowSize; i++) {
				List<Object> obj = null;
				if (objList != null) {
					obj = objList.get(i);
				}
				Row row = sheet.createRow(i + 4);
				if (obj != null) {
					for (int j = 0; j < obj.size(); j++) {
						String value = (String) obj.get(j);
						Cell cell = row.createCell(j);
						cell.setCellType(HSSFCell.CELL_TYPE_STRING);
						cell.setCellValue(value);
						cell.setCellStyle(style);
					}
				}
			}

			// 给单元格增加边框,便于定位错误
			// addRegionBorder(sheet, poiWorkbook, regionList);
			// 给单元格增加填充颜色，便于定位重复数据
			addCellGroundColor(sheet, poiWorkbook, regionList);
			FileOutputStream os = new FileOutputStream(fileName);
			poiWorkbook.write(os);
			os.flush();
			os.close();
		} catch (IOException e) {
			log.error("" + e);
		}
	}

	/**
	 * 给单元格增加填充颜色
	 * 
	 * @param sheet
	 * @param workbook
	 * @param cellGroundColorList
	 */
	public static void addCellGroundColor(Sheet sheet, Workbook workbook,
			List<Map<String, Integer>> regionList) {
		try {
			CellStyle colorStyle = workbook.createCellStyle();
			DataFormat format = workbook.createDataFormat();
			colorStyle.setDataFormat(format.getFormat("@"));// 设置单元格格式为"文本"
			colorStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // 设置背景颜色
			colorStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);// 设置填充模式
			for (Map<String, Integer> colorMap : regionList) {
				int rowNum = colorMap.get("rowNum");
				int columnNum = colorMap.get("columnNum");
				Row row = sheet.getRow(rowNum);
				Cell cell = row.getCell(columnNum);
				cell.setCellStyle(colorStyle);
			}
		} catch (Exception e) {
			log.error("" + e);
		}
	}
	
	
	
	
	
}
