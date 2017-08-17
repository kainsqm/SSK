package cn.sh.ideal.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellValue;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Benson
 * @version 1.0 , 2015-02-13
 * @see [cn.sh.ideal.system.util.ExcelUtil]
 * @category [Tools] Excel Input/Output operations
 */
public class ExcelUtil {
	/**
	 * 生成薪资结果数据excel -- 未完成测试
	 * @author Benson
	 * @param results 结果数据
	 * @param filePath 文件输出的目标路径
	 * @param targetName 指标名称[target1,target2,target3,target4,...]
	 * @param modelName 模板子同时也是excel文件名
	 * @return boolean [true:导出成功 , false:导出失败]
	 * @throws FileNotFoundException,IOException
	 */
	public static boolean exportResult(List<String[]> results, String filePath, String modelName, String targetName){
		boolean bool = true;
		String[] tableHeader = targetName.split(",");
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体
		HSSFSheet sheet = workbook.createSheet("sheet1"); // 创建一个sheet
		HSSFHeader header = sheet.getHeader();// 设置sheet的头
		row = sheet.createRow((short) 0);// 创建第1行
		for (int j = 0,x = tableHeader.length; j < x; j++) {
			cell = row.createCell((short) j);
			cell.setCellValue(tableHeader[j]);
		}
		for (int i = 0,y = results.size(); i < y; i++) {
			row = sheet.createRow((short) (i + 1));// 创建第i+1行
			cell = row.createCell((short) 0);
			cell.setCellValue(String.valueOf(i + 1));
			for (int j = 0,z = tableHeader.length; j < z; j++) {
				cell = row.createCell((short) (j));
				if (results.get(i)[j] != null) {
					cell.setCellValue((String) results.get(i)[j]);
				} else {
					cell.setCellValue("");
				}
			}
		}
		try {
			OutputStream baos = new FileOutputStream(filePath + "/" + modelName + ".xls");
			workbook.write(baos);
			baos.close();
		} catch (FileNotFoundException e) {
			bool = false;
		} catch (IOException e) {
			bool = false;
		}
		return bool;
	}

	/**
	 * 生成导入模板 [空的绩效或薪资模板]->由于目前使用的是静态模板，因此，本方法暂未启用
	 * @author Benson
	 * @param filePath 文件输出的目标路径
	 * @param targetName 指标名称[key1,key2,key3,key4...-target1,target2,target3,target4,...]
	 * @param modelName 模板子同时也是excel文件名
	 * @return boolean [true:导出成功 , false:导出失败]
	 * @throws Exception
	 */
	public static boolean exportTemp(String filePath, String modelName, String targetName){
		boolean bool = true;
		String[] keyHeader = targetName.split("-");
		String[] titleKey = null;//表头的keyName
		String[] titleName = null;//表头中文名称
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的样式
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont();
		font.setFontName("宋体");
		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		font.setFontHeightInPoints((short) 12);// 设置字体大小
		style.setFont(font);
		HSSFSheet sheet = workbook.createSheet(modelName); // 创建一个sheet
		titleKey = keyHeader[0].split(",");
		row = row = sheet.createRow((short) 0);// 创建第一行[起始行为0]
		cell = row.createCell((short) 0);// 创建第一列[起始列为0]
		cell.setCellStyle(style);
		cell.setCellValue(titleKey[0].substring(0, titleKey[0].indexOf("_")));
		titleName = keyHeader[1].split(",");
		//从第二行起建
		LoopA : for(int i = 1; i < 9;i++){
			row = sheet.createRow((short) i);// 创建行[起始行为0]
			for (int j = 0 ,y = titleKey.length; j < y; j++) {
				if(i == 1){
					cell = row.createCell((short) j);// 创建列[起始列为0]
					cell.setCellStyle(style);
					cell.setCellValue(titleKey[j]);
				}else if(i == 7){
					cell = row.createCell((short) j);// 创建列[起始列为0]
					cell.setCellStyle(style);
					cell.setCellValue(titleName[j]);
				}else{
					continue LoopA;
				}
			}
		}
		for(int i = 0; i < 7; i++){
			row = sheet.getRow(i);
			row.setZeroHeight(true);//设置第一行隐藏
		}
		try {
			OutputStream baos = new FileOutputStream(filePath + "/" + modelName + ".xls");
			workbook.write(baos);
			baos.close();
		} catch (FileNotFoundException e) {
			bool = false;
		} catch (IOException e) {
			bool = false;
		}
		return bool;
	}
	
	/**
	 * 读取绩效薪资数据Excel [支持2003,2007,2010,其它版本暂未测试过] -> 暂未使用
	 * @author Benson
	 * @param filePath 文件输出的目标路径
	 * @param excelErrorMsg 错误信息记录类
	 * @param allKey 记录所有标识key
	 * @return List<String[]> [存放所有excel数据]
	 */
	/*public static List<String[]> readDataExcel(String filePath, Map<String, String> allKey){
		List<String[]> slist = null;
		try {
			String type = ToolString.bytesToHexString(filePath);
			slist = null;
			if (type.contains("504B0304")) {// 2007,2010
				slist = readDataExcel2007(filePath, allKey);
			} else if (type.equalsIgnoreCase("D0CF11E0")) {// 2003
				slist = readDataExcel2003(filePath, allKey);
			}
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		} catch (Exception e) {
		}
		return slist;
	}*/

	/**
	 * 读取Office 2007,2010 excel -- 暂未使用
	 * @author Benson
	 * @param filePath 文件输出的目标路径
	 * @param excelErrorMsg 错误信息记录类
	 * @param allKey 记录所有标识key
	 * @return List<String[]> [存放所有excel数据]
	 * @throws FileNotFoundException,IOException
	 */
	private static List<String[]> readDataExcel2007(String filePath, Map<String, String> allKey) throws FileNotFoundException,IOException {
		List<String[]> slist = new ArrayList<String[]>();
		String[] excelValue = null;
		int fail = 0;// 记录错误行数
		String strMessage = "";// 记录错误行的信息
		FileInputStream finput = new FileInputStream(filePath);
		XSSFWorkbook xwb = new XSSFWorkbook(finput);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		XSSFCell cell = null;
		row = sheet.getRow(0);
		String cellValue = "";// 每个单元格的值
		// 读文标识部分,模板key
		row = sheet.getRow(0);// 第一行
		cell = row.getCell((short) row.getFirstCellNum());
		String tempKeyName = cell.getStringCellValue().trim();
		allKey.put(tempKeyName, tempKeyName);
		// 读文标识部分,指标key,
		row = sheet.getRow(1);// 第二行
		for (int j = row.getFirstCellNum(), x = row.getLastCellNum(); j < x; j++) {
			cell = row.getCell((short) j);
			allKey.put(readCell(cell), String.valueOf(j));
		}
		// 读数据部分
		int count = sheet.getFirstRowNum() + 8;// 从第二行开始读
		for (int i = count, y = sheet.getLastRowNum(); i <= y; i++) {
			try {
				row = sheet.getRow(i);
				int colNum = row.getPhysicalNumberOfCells();// 获取每行的总列数
				excelValue = new String[colNum];
				for (int j = row.getFirstCellNum(), z = row.getLastCellNum(); j < z; j++) {
					cell = row.getCell((short) j);
					if (cell == null) {
						excelValue[j] = cellValue;
						continue;
					}
					excelValue[j] = readCell(cell);
				}
			} catch (Exception ex) {
				fail++;
				strMessage += "EXCEL导入错误：" + "第" + i + "行\t" + ex.getMessage() + "\n";
				continue;
			}
			slist.add(excelValue);
		}
		return slist;
	}

	/**
	 * 读取Office 2003 excel -> 暂未使用
	 * @author Benson
	 * @param filePath 文件输出的目标路径
	 * @param excelErrorMsg 错误信息记录类
	 * @param allKey 记录所有标识key
	 * @return List<String[]> [存放所有excel数据]
	 * @throws FileNotFoundException,IOException
	 */
	private static List<String[]> readDataExcel2003(String filePath,Map<String, String> allKey)  throws FileNotFoundException,IOException {
		List<String[]> slist = new ArrayList<String[]>();
		String[] excelValue = null;
		int fail = 0;// 记录错误行数
		String strMessage = "";// 记录错误行的信息
		FileInputStream finput = new FileInputStream(filePath);
		POIFSFileSystem fs = new POIFSFileSystem(finput);
		HSSFWorkbook hwb = new HSSFWorkbook(fs);
		HSSFSheet sheet = hwb.getSheetAt(0);
		HSSFRow row = null;
		HSSFCell cell = null;
		String cellValue = "";// 每个单元格的值
		// 读文标识部分,模板key
		row = sheet.getRow(0);// 第一行
		cell = row.getCell((short) row.getFirstCellNum());
		String tempKeyName = cell.getStringCellValue().trim();
		allKey.put(tempKeyName, tempKeyName);
		// 读文标识部分,指标key,
		row = sheet.getRow(1);// 第二行
		for (int j = row.getFirstCellNum(), x = row.getLastCellNum(); j < x; j++) {
			cell = row.getCell((short) j);
			allKey.put(readCell(cell), String.valueOf(j));
		}
		// 读数据部分
		int count = sheet.getFirstRowNum() + 8;// 从第九行开始读
		for (int i = count, y = sheet.getLastRowNum(); i <= y; i++) {
			try {
				row = sheet.getRow(i);
				int colNum = row.getPhysicalNumberOfCells();// 获取每行的总列数
				excelValue = new String[colNum];
				for (int j = row.getFirstCellNum(), z = row.getLastCellNum(); j < z; j++) {
					cell = row.getCell((short) j);
					if (cell == null) {
						excelValue[j] = cellValue;
						continue;
					}
					excelValue[j] = readCell(cell);
				}
			} catch (Exception ex) {
				fail++;
				strMessage += "EXCEL导入错误：" + "第" + i + "行\t" + ex.getMessage() + "\n";
				continue;
			}
			slist.add(excelValue);
		}
		return slist;
	}
	
	/**
	 * 读取单元格数据
	 * @author Benson
	 * @param object 单元格对象
	 * @return String [单元格数据]
	 */
	public static String readCell(Object object) {
		// HSSFCell cell 2003
		// XSSFCell cell 2007以上
		String cellValue = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 格式化日期字符串
		DecimalFormat nf = new DecimalFormat("#.######");// 格式化数字
		if (object instanceof HSSFCell) {
			HSSFCell cell = (HSSFCell) object;
			switch (cell.getCellType()) {
				case HSSFCell.CELL_TYPE_STRING:
					cellValue = cell.getStringCellValue().trim();
					break;
				case HSSFCell.CELL_TYPE_NUMERIC:
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						cellValue = sdf
								.format(HSSFDateUtil.getJavaDate(cell
										.getNumericCellValue())).trim();
					} else {
						cellValue = nf.format(cell.getNumericCellValue()).trim();
						if (Double.valueOf(cellValue) != cell.getNumericCellValue()) {
							cellValue = String.valueOf(cell.getNumericCellValue());
						} else {
							cellValue = nf.format(cell.getNumericCellValue())
									.trim();
						}
					}
					break;
				case HSSFCell.CELL_TYPE_BLANK:
					cellValue = "";
					break;
				case HSSFCell.CELL_TYPE_FORMULA:
					cellValue = ""; // 公式列默认不读取
					break;
				default:
					cellValue = cell.toString().trim();
					break;
				}
		} else if (object instanceof XSSFCell) {
			XSSFCell cell = (XSSFCell) object;
			switch (cell.getCellType()) {
			case XSSFCell.CELL_TYPE_STRING:
				cellValue = cell.getStringCellValue().trim();
				break;
			case XSSFCell.CELL_TYPE_NUMERIC:
				if (HSSFDateUtil.isCellDateFormatted(cell)) {
					cellValue = sdf
							.format(HSSFDateUtil.getJavaDate(cell
									.getNumericCellValue())).trim();
				} else {
					cellValue = nf.format(cell.getNumericCellValue()).trim();
					if (Double.valueOf(cellValue) != cell.getNumericCellValue()) {
						cellValue = String.valueOf(cell.getNumericCellValue());
					} else {
						cellValue = nf.format(cell.getNumericCellValue())
								.trim();
					}
				}
				break;
			case XSSFCell.CELL_TYPE_BLANK:
				cellValue = "";
				break;
			case XSSFCell.CELL_TYPE_FORMULA:
				cellValue = cell.getStringCellValue();
				break;
			default:
				cellValue = cell.toString().trim();
				break;
			}
		}
		return cellValue;
	}
}