package com.isoftstone.component;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.github.drinkjava2.jsqlbox.DbContext;
import com.isoftstone.service.SalaryAnalysisService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SalaryAnalysisComponent {
	@Autowired
	private SalaryAnalysisService salaryAnalysisService;

	@Scheduled(initialDelay = 2000, fixedDelay = 3600000)
	public void run3() throws InterruptedException {
		DbContext dbContext = DbContext.getGlobalDbContext();
		String ruleSql = "select a.*,b.code as ruleCode,b.name as ruleName,b.type,b.fixed_income,b.commission_rate,b.min_discount,b.max_discount,b.is_discount,b.is_loose_items from t_emolument_template a left join t_emolument_rule b on a.code = b.template_code";
		List<Map<String, Object>> ruleMapList = dbContext.qryMapList(ruleSql);

		String empSql = "select djed.ado_id,djed.emp_code,djed.emp_name,djed.emp_dept_code,djed.emp_dept_name,djed.emp_section_code,djed.emp_section_name,tgm.all_parent_code from dwr_jxyz_emp_d djed left join t_grid_m tgm on djed.emp_dept_code = tgm.code where djed.emp_status = '01' and djed.emp_section_code is not null";
		List<Map<String, Object>> empMapList = dbContext.qryMapList(empSql);

		String tGridMSql = "select code,name from t_grid_m where level < 4";
		List<Map<String, Object>> tGridMMapList = dbContext.qryMapList(tGridMSql);

		empMapList.forEach(empMap -> {
			try {
				salaryAnalysisService.salaryAnalysis(empMap, ruleMapList, tGridMMapList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				log.error("薪酬数据插入错误：{}",e.getMessage());
			}
		});
		
		log.error("over：------------------------------");
	}
}
