package cn.sh.ideal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * @author xinxinfan
 * @date 2013-08-26 读取excel内容
 * 
 */
public class ParseExcelUtil {
	private static Logger log = Logger.getLogger(ParseExcelUtil.class);

	public List<List<Object>> readExcel(File file, int readStratRow,
			boolean isExcel2003, boolean isExcel2007)
			throws FileNotFoundException, IOException {
		List<List<Object>> excelList = new ArrayList<List<Object>>();
		Workbook workbook = null;
		DecimalFormat df = new DecimalFormat("0");
		if (isExcel2003) {
			workbook = createWorkBook2003(new FileInputStream(file));
		}
		if (isExcel2007) {
			workbook = createWorkBook2007(new FileInputStream(file));
		}
		Sheet sheet = null;
		if (workbook != null) {
			sheet = workbook.getSheetAt(0);
		}
		if (sheet != null) {

			// excel每一行数据组成的list
			List<Object> rowList2 = new ArrayList<Object>();
			Row row2 = sheet.getRow(1);// 第二行 取key
			int x = row2.getLastCellNum();
			for (int i = 0; i < x; i++) {
				Cell cell2 = row2.getCell((short) i);
				if (null != cell2
						|| !ToolString.isNull(ExcelUtil.readCell(cell2).trim())) {
					rowList2.add(ExcelUtil.readCell(cell2));
				}
			}
			excelList.add(rowList2);

			int lastRowNum = sheet.getLastRowNum();
			if (readStratRow <= lastRowNum) {
				// 获取标题行
				Row titleRow = sheet.getRow(readStratRow - 1);
				// 获取标题行的列数
				int lastCellNum = titleRow.getLastCellNum();
				// 循环读取excel的行
				for (int i = readStratRow; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (row != null) {
						List<Object> rowList = new ArrayList<Object>();
						// 循环读取该行的列
						for (int j = 0; j < lastCellNum; j++) {
							// 获取单元格
							Cell cell = row.getCell(j);
							if (cell != null) {
								// String stringCellValue =
								// cell.getStringCellValue();
								String stringCellValue = "";
								switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_STRING:
									stringCellValue = cell.getStringCellValue();
									break;
								case HSSFCell.CELL_TYPE_NUMERIC:
									stringCellValue = df.format(cell
											.getNumericCellValue());
									break;
								case HSSFCell.CELL_TYPE_FORMULA:
									cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									stringCellValue = String.valueOf(cell
											.getNumericCellValue());
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									stringCellValue = "";
									break;
								case HSSFCell.CELL_TYPE_BOOLEAN:
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									break;
								default:
									stringCellValue = "";
									break;
								}
								rowList.add(stringCellValue);
							}
						}
						excelList.add(rowList);
					}
				}
			}
		}
		return excelList;
	}

	/**
	 * 读取excel
	 * 
	 * @param inputStream
	 * @param readStratRow
	 * @param isExcel2003
	 * @param isExcel2007
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @author gilbert
	 * @version 1.0,2015年3月12日
	 */
	public List<List<Object>> readExcel(InputStream inputStream,
			int readStratRow, boolean isExcel2003, boolean isExcel2007)
			throws FileNotFoundException, IOException {
		List<List<Object>> excelList = new ArrayList<List<Object>>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		DecimalFormat df = new DecimalFormat("0");
		Workbook workbook = null;
		if (isExcel2003) {
			workbook = createWorkBook2003(inputStream);
		}
		if (isExcel2007) {
			workbook = createWorkBook2007(inputStream);
		}
		Sheet sheet = null;
		if (workbook != null) {
			sheet = workbook.getSheetAt(0);
		}
		if (sheet != null) {
			int lastRowNum = sheet.getLastRowNum();

			// excel每一行数据组成的list
			List<Object> rowList2 = new ArrayList<Object>();

			if (readStratRow <= lastRowNum) {
				// 获取标题行
				Row titleRow = sheet.getRow(readStratRow - 1);
				// 获取标题行的列数
				int lastCellNum = titleRow.getLastCellNum();
				// 循环读取excel的行
				for (int i = readStratRow; i <= lastRowNum; i++) {
					Row row = sheet.getRow(i);
					if (row != null) {
						List<Object> rowList = new ArrayList<Object>();

						boolean b = checkAllEmpty(row, lastCellNum);
						if (!b) {
							continue;
						}

						// 循环读取该行的列
						for (int j = 0; j < lastCellNum; j++) {

							// 获取单元格
							Cell cell = row.getCell(j);

							String stringCellValue = "";
							if (cell != null) {
								switch (cell.getCellType()) {
								case HSSFCell.CELL_TYPE_STRING:
									stringCellValue = cell.getStringCellValue()
											.trim();
									break;
								case HSSFCell.CELL_TYPE_NUMERIC:
									if (HSSFDateUtil.isCellDateFormatted(cell)) { // 时间格式
										Date theDate = cell.getDateCellValue();
										stringCellValue = sdf.format(theDate);
									} else {
										stringCellValue = df.format(cell
												.getNumericCellValue());
									}
									break;
								case HSSFCell.CELL_TYPE_FORMULA:
									// cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
									// stringCellValue =
									// String.valueOf(cell.getNumericCellValue());
									stringCellValue = "FORMULA"; // 公式列，默认值为formula，数据校验时，认为此数据格式不正确
									break;
								case HSSFCell.CELL_TYPE_BLANK:
									stringCellValue = "";
									break;
								case HSSFCell.CELL_TYPE_BOOLEAN:
									break;
								case HSSFCell.CELL_TYPE_ERROR:
									break;
								default:
									stringCellValue = "";
									break;
								}
								rowList.add(stringCellValue.trim());
							} else {
								rowList.add(stringCellValue);
							}
						}
						excelList.add(rowList);
					}
				}
			}
		}
		return excelList;
	}

	// 判断row的一行是否全为空
	private boolean checkAllEmpty(Row row, int lastCellNum) {
		boolean b = false;

		for (int j = 0; j < lastCellNum; j++) {
			// 获取单元格
			Cell cell = row.getCell(j);
			if (!ToolString.isNull(cell)) {
				if (StringUtils.isNotBlank(cell.toString())) {
					b = true;
					return b;
				}
			}

		}

		return b;
	}

	private Workbook createWorkBook2003(InputStream is) throws IOException {
		try {
			return new HSSFWorkbook(is);
		} catch (Exception e) {
			log.error("" + e);
		}
		return null;
	}

	private Workbook createWorkBook2007(InputStream is) throws IOException {
		try {
			return new XSSFWorkbook(is);
		} catch (Exception e) {
			log.error("" + e);
		}
		return null;
	}
}
