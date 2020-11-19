package com.isoftstone.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.compress.archivers.sevenz.SevenZArchiveEntry;
import org.apache.commons.compress.archivers.sevenz.SevenZFile;

public class Utils {
	public static final String separator = File.separator;
	public static final String surfix = ".7z";

	public static void traverFile(File file, List<File> fileList) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File tempFile : files) {
					traverFile(tempFile, fileList);
				}
			}
		} else {
			fileList.add(file);
		}
	}

	public static InputStream getInputHttpsStreamByGet(String url) {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(url).openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(5000);
			conn.setRequestMethod("GET");
			if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inputStream = conn.getInputStream();
				return inputStream;
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			// TODO Auto-generated method stub
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			// TODO Auto-generated method stub
			return null;
		}
	} };

	/**
	 * zip解压
	 * 
	 * @param inputFile   待解压文件名
	 * @param destDirPath 解压路径
	 */
	public static void Uncompress(File srcFile, String destDirPath) throws Exception {
		// 判断源文件是否存在
		if (!srcFile.exists()) {
			throw new Exception(srcFile.getPath() + "所指文件不存在");
		}
		// 开始解压
		@SuppressWarnings("resource")
		SevenZFile zIn = new SevenZFile(srcFile);
		SevenZArchiveEntry entry = null;
		File file = null;
		while ((entry = zIn.getNextEntry()) != null) {
			if (!entry.isDirectory()) {
				file = new File(destDirPath, entry.getName());
				if (!file.exists()) {
					new File(file.getParent()).mkdirs();// 创建此文件的上级目录
				}
				OutputStream out = new FileOutputStream(file);
				BufferedOutputStream bos = new BufferedOutputStream(out);
				int len = -1;
				byte[] buf = new byte[1024];
				while ((len = zIn.read(buf)) != -1) {
					bos.write(buf, 0, len);
				}
				// 关流顺序，先打开的后关闭
				bos.close();
				out.close();
			}
		}
	}

}
