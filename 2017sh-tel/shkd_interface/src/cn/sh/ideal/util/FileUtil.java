package cn.sh.ideal.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;

/**
 * 文件工具类
 * 
 * @author gilbert
 * 
 */
public class FileUtil {
	private static Logger log = Logger.getLogger(FileUtil.class);
	private static File file = null;

	/**
	 * 创建文件对象
	 * 
	 * @author Benson
	 * @param filePath
	 *            文件路径
	 * @return 布尔值 [true:存在 | false:不存在]
	 */
	private static void createFileObject(String filePath) {
		if (null == file) {
			file = new File(filePath);
		}
	}

	/**
	 * 判断文件或路径是否存在
	 * 
	 * @author Benson
	 * @param filePath
	 *            文件路径
	 * @return 布尔值 [true:存在 | false:不存在]
	 */
	public static boolean isExis(String filePath) {
		createFileObject(filePath);
		return file.exists();
	}

	/**
	 * 创建目录
	 * 
	 * @author Benson
	 * @param filePath
	 *            文件路径
	 * @return 布尔值 [true:成功 | false:失败]
	 */
	public static boolean mkdirs(String filePath) {
		createFileObject(filePath);
		return file.mkdirs();
	}

	/**
	 * 判断是否是文件
	 * 
	 * @author Benson
	 * @param filePath
	 *            文件路径
	 * @return 布尔值 [true:是 | false:不是]
	 */
	public static boolean isFile(String filePath) {
		createFileObject(filePath);
		return file.isFile();
	}

	/**
	 * 判断是否是目录
	 * 
	 * @author Benson
	 * @param filePath
	 *            文件路径
	 * @return 布尔值 [true:是 | false:不是]
	 */
	public static boolean isDirectory(String filePath) {
		createFileObject(filePath);
		return file.isDirectory();
	}

	/**
	 * 删除文件
	 * 
	 * @author fengqiang
	 * @modifier Benson [2015-03-12 11:59]
	 * @param filePath
	 * @return [true:成功 | false:失败]
	 */
	public boolean deleteFile(String filePath) {
		boolean flag = false;
		if (isExis(filePath) && isFile(filePath)) { // 路径为文件且不为空则进行删除
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * 删除文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @author gilbert
	 * @version 1.0,2015年7月27日
	 */
	public boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				if (!temp.delete()) {

				}
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]); // 先删除文件夹里面的文件
				if (temp.delete()) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/**
	 * 向指定目录写文件
	 * 
	 * @author Benson
	 * @param fin
	 *            文件输入流
	 * @param filePath
	 *            要输出的文件路径及文件名称
	 * @throws FileNotFoundException
	 *             ,IOException
	 */
	public static void wirteFile(FileInputStream fin, String filePath)
			throws FileNotFoundException, IOException {
		FileOutputStream fou = null;
		try {
			fou = new FileOutputStream(filePath);
			byte[] b = new byte[1024 * 100];
			int len = 0;
			while ((len = fin.read(b)) != -1) {
				fou.write(b, 0, len);
			}
		} catch (Exception e) {
			log.error("" + e);
		} finally {
			if (fou != null) {
				fou.close();
			}
		}

	}

	/**
	 * 创建目录
	 * 
	 * @author humanhua
	 * @param filePath
	 *            文件路径
	 * @return 布尔值 [true:成功 | false:失败]
	 */
	public static boolean createCatalog(String filePath) {
		file = new File(filePath);
		return file.mkdirs();
	}

	/**
	 * 删除文件
	 * 
	 * @author humanhua
	 * @modifier [2015-03-25 11:59]
	 * @param filePath
	 * @return [true:成功 | false:失败]
	 */
	public static boolean deletefilePath(String filePath) {
		boolean flag = false;
		file = new File(filePath);
		if (isExis(filePath) && isFile(filePath)) { // 路径为文件且不为空则进行删除
			flag = file.delete();
		}
		return flag;
	}

	/**
	 * 判断文件或路径是否存在
	 * 
	 * @author humanhua
	 * @param filePath
	 * 文件路径
	 * @return 布尔值 [true:存在 | false:不存在]
	 */
	public static boolean isFileExis(String filePath) {
		file = new File(filePath);
		return file.exists();
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPath
	 *            String 原文件路径 如：c:/fqf.txt
	 * @param newPath
	 *            String 复制后路径 如：f:/fqf.txt
	 * @return boolean
	 * @throws IOException
	 */
	public static void copyFile(String oldPath, String newPath)
			throws IOException {
		InputStream inStream = null;
		try {
			int bytesum = 0;
			int byteread = 0;
			File newfile = new File(newPath);
			if (newfile.exists()) {// 判断 如果新的文件已经存在，删掉
				if (newfile.delete()) {

				}
			}
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // 文件存在时
				inStream = new FileInputStream(oldPath); // 读入原文件
				try(FileOutputStream fs = new FileOutputStream(newPath)){
					byte[] buffer = new byte[1444];
					while ((byteread = inStream.read(buffer)) != -1) {
						bytesum += byteread; // 字节数 文件大小
						fs.write(buffer, 0, byteread);
					}
					inStream.close();
					fs.close();
				}catch (Exception e) {
					log.error(e);
				}
			}
		} catch (Exception e) {
			log.error(e);
		} finally {
			if (inStream != null) {
				inStream.close();
			}
		}
	}
}