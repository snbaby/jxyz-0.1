package com.isoftstone.jxyz.component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.jxyz.util.Utils;

@Component
public class JxyzComponent {
	private static String messageTable = "sdi_jxyz_pkp_trace_message_";
	private static String message704Table = "sdi_jxyz_pkp_trace_message_704_";
	private static SimpleDateFormat ymDf = new SimpleDateFormat("yyyyMM");
	
	@Scheduled(initialDelay = 2000, fixedDelay = 24*60*60*1000)
	public void createTable() {
		DbContext dbContext = DbContext.getGlobalDbContext();

		Calendar nextCalendar = Calendar.getInstance();
		nextCalendar.add(Calendar.MONTH, 1);

		Calendar thisCalendar = Calendar.getInstance();
		thisCalendar.add(Calendar.MONTH, 0);

		String thisMessageTable = messageTable + ymDf.format(thisCalendar.getTime());
		String nextMessageTable = messageTable + ymDf.format(nextCalendar.getTime());

		String thisMessage704Table = message704Table + ymDf.format(thisCalendar.getTime());
		String nextMessage704Table = message704Table + ymDf.format(nextCalendar.getTime());

		if (dbContext.qryLongValue(Utils.validateTable(thisMessageTable)) == 0) {
			dbContext.exe(Utils.createMessageTable(thisMessageTable));
		}

		if (dbContext.qryLongValue(Utils.validateTable(nextMessageTable)) == 0) {
			dbContext.exe(Utils.createMessageTable(nextMessageTable));
		}

		if (dbContext.qryLongValue(Utils.validateTable(thisMessage704Table)) == 0) {
			dbContext.exe(Utils.createMessageTable(thisMessage704Table));
		}

		if (dbContext.qryLongValue(Utils.validateTable(nextMessage704Table)) == 0) {
			dbContext.exe(Utils.createMessageTable(nextMessage704Table));
		}
	}

}
