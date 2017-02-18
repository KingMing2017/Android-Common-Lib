package com.king.android.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;

public class FileUtils {

	public static long randomDownLoad(String filePath, byte[] arrByte,
			long pos, int length) {
		try {
			RandomAccessFile raf = new RandomAccessFile(filePath, "rwd");
			raf.seek(pos);
			raf.write(arrByte, 0, length);
			raf.close();
			raf = null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 0;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		} catch (IOException e) {
			e.printStackTrace();
			return pos;
		}
		return pos + length;
	}

	/**
	 * 删除文件或文件夹
	 * @param filePath
	 */
	public static void delFile(String filePath) {
		File file = new File(filePath);
		if(file.exists()){
			if(file.isDirectory()){
				File[] childs = file.listFiles();
				for(File item : childs){
					if(item.isFile()){
						item.delete();
					}else if(item.isDirectory()){
						delFile(item.getAbsolutePath());
					}
				}
				file.delete();
			}else if(file.isFile()){
				file.delete();
			}
		}
	}

	public static File createOrReplaceFile(String path){
		String status = Environment.getExternalStorageState();
		if (status.equalsIgnoreCase(Environment.MEDIA_MOUNTED_READ_ONLY)
				|| status.equalsIgnoreCase(Environment.MEDIA_BAD_REMOVAL)
				|| status.equalsIgnoreCase(Environment.MEDIA_UNMOUNTABLE)
				|| status.equalsIgnoreCase(Environment.MEDIA_UNMOUNTED)) {
			return null;
		}
		File file = new File(path);
		return createOrReplaceFile(file);
	}

	/**
	 * 创建文件
	 * @param file
	 * @return
	 */
	public static final File createOrReplaceFile(File file) {
		try {
			if (!file.getParentFile().exists()) {
				if (file.getParentFile().mkdirs()) {
					file.createNewFile();
				}
			} else {
				file.createNewFile();
			} 
		} catch (IOException e) {
			return null;
		}
		return file;
	}

	/**
	 * 创建目录
	 * @param dir
	 * @return
	 */
	public static final File createOrReplaceDir(File dir) {
		if (!dir.getParentFile().exists()) {
			if (dir.getParentFile().mkdirs()) {
				dir.mkdir();
			}
		} else {
			dir.mkdir();
		}
		return dir;
	}

	/**
	 * 创建目录
	 * @param path
	 * @return
	 */
	public static final File createOrReplaceDir(String path) {
		File dir = new File(path);
		if (!dir.getParentFile().exists()) {
			if (dir.getParentFile().mkdirs()) {
				dir.mkdir();
			}
		} else {
			dir.mkdir();
		}
		return dir;
	}

	public static final void write(File source, File target) {
		try {
			FileOutputStream out = new FileOutputStream(
					createOrReplaceFile(target));
			FileInputStream in = new FileInputStream(
					createOrReplaceFile(source));
			byte[] b = new byte[8192];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static final void write(InputStream in, File target) {
		try {
			FileOutputStream out = new FileOutputStream(
					createOrReplaceFile(target));
			byte[] b = new byte[8192];
			int len = 0;
			while ((len = in.read(b)) != -1) {
				out.write(b, 0, len);
			}
			out.flush();
			out.close();
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取文件
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static final String readSdFile(String fileName) throws Exception{
		String result = "";
		if (isFileExist(fileName)){
			File file = new File(fileName);
			FileInputStream fileInputStream = new FileInputStream(file);
			int length = fileInputStream.available();
			byte [] buffer = new byte[length];
			fileInputStream.read(buffer);
			result = EncodingUtils.getString(buffer,"UTF-8");
			fileInputStream.close();
		}
		return result;
	}

	/**
	 * 判断文件是否存在
	 * 
	 * @param path
	 * @return
	 */
	public static boolean isFileExist(final String path) {
		if (path == null) {
			return false;
		}
		try {
			File f = new File(path);
			if (!f.exists()) {
				return false;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return true;
	}

}
