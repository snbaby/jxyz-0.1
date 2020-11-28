package com.isoftstone.component;

import java.io.File;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.github.drinkjava2.jsqlbox.DB;
import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.util.HttpsPostUtil;
import com.isoftstone.util.Utils;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EtlComponent {
	@Value("${jxyzadoetl.etl.download}")
	private String downloadBasePath;

	@Value("${jxyzadoetl.etl.unzip}")
	private String unzipBasePath;
	
	@Value("${jxyzadoetl.etl.base}")
	private String basePath;

	@Value("${jxyzadoetl.etl.url}")
	private String downloadBaseUrl;
	@Value("${jxyzadoetl.etl.del_url}")
	private String delUrl;

	@Value("${jxyzadoetl.etl.get_token.user_name}")
	private String userName;
	@Value("${jxyzadoetl.etl.get_token.password}")
	private String password;
	@Value("${jxyzadoetl.etl.get_token.token_url}")
	private String tokenUrl;

	@Async
	public void asyncParse(String id,String tableName,String condition) throws Exception {
		String requestId = UUID.randomUUID().toString();
		log.info(">>开始解析文件--文件Id:{}--请求Id:{}", id, requestId);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		LocalDateTime startTime = LocalDateTime.now();
		DbContext ctx = DbContext.getGlobalDbContext();
		if (null == condition || condition.equals("")){
			condition = null;
		}
		ctx.exe("INSERT INTO t_etl_log(`id`,`file_id`,`table_name`,`condition`,`recive_time`,`create_time`)VALUES(?,?,?,?,?,?)",
				DB.param(requestId, id, tableName, condition, df.format(new Date()), df.format(new Date())));

		File baseFolder = new File(basePath);
		if (!(baseFolder.isDirectory() && baseFolder.exists())) {
			baseFolder.mkdir();
		}
		
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
		ctx.exe("UPDATE t_etl_log SET download_start_time = ? WHERE id = ?",
				DB.param(df.format(new Date()), requestId));
		FileUtils.copyInputStreamToFile(Utils.getInputHttpsStreamByGet(downloadUrl), downloadFile);
		ctx.exe("UPDATE t_etl_log SET download_end_time = ? WHERE id = ?",
				DB.param(df.format(new Date()), requestId));
		String unzipFilePath = unzipBasePath + Utils.separator + id;
		File unzipFileFolder = new File(unzipFilePath);
		if (!(unzipFileFolder.exists() && unzipFileFolder.isDirectory())) {
			unzipFileFolder.mkdir();
		}
		ctx.exe("UPDATE t_etl_log SET parse_start_time = ? WHERE id = ?",
				DB.param(df.format(new Date()), requestId));
		Utils.Uncompress(downloadFile, unzipFilePath);
		ctx.exe("UPDATE t_etl_log SET parse_end_time = ? WHERE id = ?",
				DB.param(df.format(new Date()), requestId));
		log.info(">>开始执行扫描文件夹--{}--", unzipFilePath);
		List<File> fileList = new ArrayList<>();
		File esbDirectory = new File(unzipFilePath);
		Utils.traverFile(esbDirectory, fileList);
		log.info(">>文件数量--{}--", fileList.size());
		try {
			ctx.exe("UPDATE t_etl_log SET insert_start_time = ? WHERE id = ?",
					DB.param(df.format(new Date()), requestId));
			int total = 0;
			ctx.nBatchBegin();
			System.out.println(ctx.isBatchEnabled());
			for (int i = 0; i < fileList.size(); i++) {
				File file = fileList.get(i);
				List<String> lineList = FileUtils.readLines(file, "UTF-8");
				total = total+lineList.size();
				for (int j = 0; j < lineList.size(); j++) {
					ctx.exe(lineList.get(j));
				}
			}
			if (!tableName.contains("sdi_jxyz_pkp_waybill_base_")){
				total = total-1;
			}
			ctx.exe("UPDATE t_etl_log SET insert_end_time = ?,total= ? WHERE id = ?",
					DB.param(df.format(new Date()),total, requestId));
		} finally {
			// TODO: handle finally clause
			ctx.nBatchEnd();
		}

		LocalDateTime endTime = LocalDateTime.now();
		log.info(">>接收解析文件--{}--startTime:{}--endTime:{}", id, startTime, endTime);

		// 删除本地文件
		Utils.delAllFile(esbDirectory);
		log.info(">>接收解析文件--{}--", id);
		// 删除下载路径中的文件
		// 获取token
		JSONObject tokenJson = new JSONObject();
		tokenJson.put("userName", userName);
		tokenJson.put("password", password);
		String token = HttpsPostUtil.post(tokenUrl, tokenJson.toJSONString(), null);
		HttpsPostUtil.deleteFile(delUrl + id, token);
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
