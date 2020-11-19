package com.isoftstone.component;

import java.io.File;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EtlComponent {
	@Value("${jxyzadoetl.etl.download}")
	private String downloadBasePath;

	@Value("${jxyzadoetl.etl.unzip}")
	private String unzipBasePath;

	@Value("${jxyzadoetl.etl.url}")
	private String downloadBaseUrl;

	@Async
	public void asyncParse(String id) throws Exception {

		LocalDate startDate = LocalDate.now();
		DbContext ctx = DbContext.getGlobalDbContext();
		File downloadBaseFolder = new File(downloadBasePath);
		if (!(downloadBaseFolder.isDirectory() && downloadBaseFolder.exists())) {
			downloadBaseFolder.mkdir();
		}

		File unzipBaseFolder = new File(unzipBasePath);
		if (!(unzipBaseFolder.exists() && unzipBaseFolder.isDirectory())) {
			unzipBaseFolder.mkdir();
		}

		String downloadUrl = downloadBaseUrl + id;
		HttpsURLConnection.setDefaultHostnameVerifier(new NullHostNameVerifier());
		SSLContext sc = SSLContext.getInstance("TLS");
		sc.init(null, Utils.trustAllCerts, new SecureRandom());
		HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		String downloadFilePath = downloadBaseFolder + Utils.separator + id + Utils.surfix;

		File downloadFile = new File(downloadFilePath);
		FileUtils.copyInputStreamToFile(Utils.getInputHttpsStreamByGet(downloadUrl), downloadFile);

		String unzipFilePath = unzipBasePath + Utils.separator + id;
		File unzipFileFolder = new File(unzipFilePath);
		if (!(unzipFileFolder.exists() && unzipFileFolder.isDirectory())) {
			unzipFileFolder.mkdir();
		}
		Utils.Uncompress(downloadFile, unzipFilePath);

		log.info(">>开始执行扫描文件夹--{}--", unzipFilePath);
		List<File> fileList = new ArrayList<File>();
		File esbDirectory = new File(unzipFilePath);
		Utils.traverFile(esbDirectory, fileList);
		log.info(">>文件数量--{}--", fileList.size());
		System.out.println(ctx.isBatchEnabled());
		try {
			ctx.nBatchBegin();
			System.out.println(ctx.isBatchEnabled());
			for (int i = 0; i < fileList.size(); i++) {
				File file = fileList.get(i);
				List<String> lineList = FileUtils.readLines(file);
				for (int j = 0; j < lineList.size(); j++) {
					ctx.exe(lineList.get(j));
				}
			}
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}

		LocalDate endDate = LocalDate.now();
		System.out.println("start:" + startDate.toString());
		System.out.println("end:" + endDate.toString());
		log.info(">>>>>>>>结束执行>>>>>>>>>");
	}

	private class NullHostNameVerifier implements HostnameVerifier {
		/*
		 * (non-Javadoc)
		 * 
		 * @see javax.net.ssl.HostnameVerifier#verify(java.lang.String,
		 * javax.net.ssl.SSLSession)
		 */
		@Override
		public boolean verify(String arg0, SSLSession arg1) {
			// TODO Auto-generated method stub
			return true;
		}
	}
}
