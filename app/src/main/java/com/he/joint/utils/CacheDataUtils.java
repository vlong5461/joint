package com.he.joint.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CacheDataUtils {
	
	public static synchronized boolean saveCacheData(String cacheDataPath, Object data) {
		boolean success = false;
		File file = new File(cacheDataPath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		ObjectOutputStream out;
		try {
			out = new ObjectOutputStream(new FileOutputStream(cacheDataPath, false));
			out.writeObject(data);
			out.close();
			success = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return success;
	}
	
	public static synchronized Object getCacheData(String cacheDataPath) {
		Object data = null;
		File file = new File(cacheDataPath);
		if (file.exists()) {
			ObjectInputStream in = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(cacheDataPath);
				int length = fis.available();
				if (length > 0) {
					in = new ObjectInputStream(new FileInputStream(cacheDataPath));
				 	data = in.readObject();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (fis != null) {
					try {
						fis.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

		return data;
	}

}
