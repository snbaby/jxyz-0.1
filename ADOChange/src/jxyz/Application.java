package jxyz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import jxyz.exchanger.BuildingHttpExchanger;
import jxyz.exchanger.CampusHttpExchanger;
import jxyz.exchanger.CustomDaliyExchanger;
import jxyz.exchanger.CustomExchanger;
import jxyz.exchanger.CustomHttpExchanger;
import jxyz.exchanger.CustomMonthExchanger;
import jxyz.exchanger.DeliveryDaliyExchanger;
import jxyz.exchanger.DeliveryMonthExchanger;
import jxyz.exchanger.DepartmentExchanger;
import jxyz.exchanger.DeptSaleDaliyExchanger;
import jxyz.exchanger.DeptSaleMonthExchanger;
import jxyz.exchanger.EmpDaliyExchanger;
import jxyz.exchanger.EmpExchanger;
import jxyz.exchanger.EmpInfoExchanger;
import jxyz.exchanger.EmpMonthExchanger;
import jxyz.exchanger.EnterpriseContractHttpExchanger;
import jxyz.exchanger.EnterpriseCustomHttpExchanger;
import jxyz.exchanger.EnterpriseCustomRelatioHttpExchanger;
import jxyz.exchanger.EnterpriseHttpExchanger;
import jxyz.exchanger.EnterpriseInstanceHttpExchanger;
import jxyz.exchanger.Exchanger;
import jxyz.exchanger.RegionDaliyExchanger;
import jxyz.exchanger.RegionMonthExchanger;
import jxyz.exchanger.ResourceExchanger;
import jxyz.exchanger.SandTableExchanger;
import jxyz.exchanger.SanderExchanger;
import jxyz.exchanger.UptownHttpExchanger;
import jxyz.utils.TimeUtil;

/**
 * 转换启动类
 *
 * @author xiaoxin
 *
 */
public class Application {

    public static Map<String, Object> GLOBAL_PARAM = new HashMap<String, Object>();

    public static final String CURR_YEAR = "curr_year";
    public static final String LAST_YEAR = "last_year";
    public static final String CURR_DAY = "curr_day";
    public static final String LAST_DAY = "last_day";
    public static final String CURR_MONTH = "curr_month";
    public static final String LAST_MONTH = "last_month";

    public static final String CURR_MONTH_FIRSTDAY = "curr_month_first_day";
    public static final String CURR_MONTH_LASTDAY = "curr_month_last_day";

    public static final String LAST_MONTH_FIRSTDAY = "last_month_first_day";
    public static final String LAST_MONTH_LASTDAY = "last_month_last_day";

    public static final String DEL_CURR_MONTH_START = "del_curr_month_start";
    public static final String DEL_CURR_MONTH_END = "del_curr_month_end";

    public static final String LAST_YEAR_SAME_DATE = "last_year_same_date";
    public static final String LAST_YEAR_SAME_MONTH_START = "last_year_same_month_start";
    public static final String LAST_YEAR_START = "last_year_start";
    public static final String CURR_YEAR_START = "curr_year_start";

    // 环境信息
    // public static final String DATABASE_IP = "161.117.39.189:3306";
    // public static final String DATABASE_USER = "jxyz";
    // public static final String DATABASE_PASS = "Huawei@1234";
    // public static final String DATABASE_DB = "jxyz2";

    private static final String DATABASE_IP = "127.0.0.1:3306";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "root@123";
    private static final String DATABASE_DB = "jxyz";

//	 public static final String DATABASE_IP =
//	 "rm-8vbif49m6k7l651e5fo.mysql.zhangbei.rds.aliyuncs.com:3306";
//	 public static final String DATABASE_USER = "root";
//	 public static final String DATABASE_PASS = "Liqin1988";
//	 public static final String DATABASE_DB = "jxyz_zy";

    // 问题记录
    // dwr_jxyz_department_d 和 t_grid_m 是如何同步的

    public static void main(String[] args) throws Exception {

        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Connection connection = DriverManager.getConnection(String.format(
                "jdbc:mysql://%s/%s?useSSL=false&allowPublicKeyRetrieval=true&useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&transformedBitIsBoolean=true&autoReconnect=true&failOverReadOnly=false&serverTimezone=Asia/Shanghai",
                DATABASE_IP, DATABASE_DB), DATABASE_USER, DATABASE_PASS);
        long startTime = System.currentTimeMillis();

        /*****自动算前一天****/
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
		c.add(Calendar.DATE, -1);
		calcByDate(c.getTime(), connection, 1);
        /*****自动算前一天****/

        /*****手动按天****/
//        String date = "2020-12-01";
//        Date targetDate = TimeUtil.stringToDate(date);
//        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
//        c.setTime(targetDate);
//
//    	for (int i = 0; i < 11; ++i) {
//			System.out.println("================计算日期【" + TimeUtil.translateDate(c.getTime()) + "】开始===========");
//			if (i==10){
//                calcByDate(c.getTime(), connection,1);
//            }else{
//                calcByDate(c.getTime(), connection,0);
//            }
//			System.out.println("================计算结束===========");
//			c.add(Calendar.DATE, 1);
//		}
        /*****手动按天****/
        System.out.println("=====总耗时" + (System.currentTimeMillis() - startTime) / 1000 + "===========");

    }

    private static void calcByDate(Date targetDate, Connection connection, Integer type) {
        // 设置全局变量
        // 计算当年
        Calendar cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        GLOBAL_PARAM.put(CURR_YEAR, TimeUtil.translateDate(cale.getTime(), "yyyy")); // 当前年
        cale.add(Calendar.YEAR, -1);
        GLOBAL_PARAM.put(LAST_YEAR, TimeUtil.translateDate(cale.getTime(), "yyyy")); // 去年
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.set(Calendar.HOUR_OF_DAY, 0);
        cale.set(Calendar.MINUTE, 0);
        cale.set(Calendar.SECOND, 0);
        GLOBAL_PARAM.put(LAST_DAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd HH:mm:ss")); // 当天凌晨0点
        cale.add(Calendar.DATE, 1);
        GLOBAL_PARAM.put(CURR_DAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd HH:mm:ss")); // 第二天凌晨0点

        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        GLOBAL_PARAM.put(CURR_MONTH, TimeUtil.translateDate(cale.getTime(), "yyyyMM")); // 本月
        GLOBAL_PARAM.put(LAST_MONTH, TimeUtil.translateDate(cale.getTime(), "yyyyMM")); // 上月
        // 计算本月第一天
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        GLOBAL_PARAM.put(CURR_MONTH_FIRSTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
        GLOBAL_PARAM.put(DEL_CURR_MONTH_START, TimeUtil.translateDate(cale.getTime(), "yyyyMM"));
        // 本月今天
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        GLOBAL_PARAM.put(CURR_MONTH_LASTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
        GLOBAL_PARAM.put(DEL_CURR_MONTH_END, TimeUtil.translateDate(cale.getTime(), "yyyyMM"));
        // 计算上月第一天
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.add(Calendar.MONTH, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        GLOBAL_PARAM.put(LAST_MONTH_FIRSTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
        
        // 计算上月同期
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.add(Calendar.MONTH, -1);
        GLOBAL_PARAM.put(LAST_MONTH_LASTDAY, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));

        // 计算去年同期
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.add(Calendar.YEAR, -1);
        GLOBAL_PARAM.put(LAST_YEAR_SAME_DATE, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
        // 计算去年同月1号
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.add(Calendar.YEAR, -1);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        GLOBAL_PARAM.put(LAST_YEAR_SAME_MONTH_START, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
        // 计算去年开始
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.add(Calendar.YEAR, -1);
        cale.set(Calendar.DAY_OF_YEAR, 1);
        GLOBAL_PARAM.put(LAST_YEAR_START, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));
        // 计算今年开始
        cale = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        cale.setTime(targetDate);
        cale.set(Calendar.DAY_OF_YEAR, 1);
        GLOBAL_PARAM.put(CURR_YEAR_START, TimeUtil.translateDate(cale.getTime(), "yyyy-MM-dd"));

//		System.out.println("===============开始转换" + TimeUtil.translateDate(targetDate) + "=============");

        for (String key : GLOBAL_PARAM.keySet()) {
            System.out.println("GLOBALPARAM[" + key + "]: " + GLOBAL_PARAM.get(key));
        }

        List<Exchanger> exchangerList = new ArrayList<Exchanger>();
        exchangerList.add(new BuildingHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new CampusHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new EnterpriseContractHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new EnterpriseCustomHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new EnterpriseCustomRelatioHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new EnterpriseHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new EnterpriseInstanceHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new UptownHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new CustomHttpExchanger());// 王建邮政地址接口
        exchangerList.add(new DepartmentExchanger());// 营业部同步
        exchangerList.add(new DeliveryDaliyExchanger());// 添加投递记录转换器
        exchangerList.add(new CustomExchanger()); // 添加客户转换器
        exchangerList.add(new DeptSaleDaliyExchanger()); // 添加部门销售收入转换器
        exchangerList.add(new EmpExchanger()); // 添加员工转换器
        exchangerList.add(new EmpDaliyExchanger()); // 添加员工日统计转换器
        exchangerList.add(new CustomDaliyExchanger()); // 添加客户日收入转换器
        exchangerList.add(new RegionDaliyExchanger()); // 添加区域销售日统计转换器
        exchangerList.add(new CustomMonthExchanger()); // 添加客户月统计转换器
        exchangerList.add(new DeliveryMonthExchanger()); // 添加投递月统计转换器
        exchangerList.add(new EmpMonthExchanger()); // 添加员工月统计转换器
        exchangerList.add(new RegionMonthExchanger()); // 添加区域销售月统计转换器
        exchangerList.add(new DeptSaleMonthExchanger()); // 添加部门销售月统计转换器
        if (type == 1) {
            exchangerList.add(new EmpInfoExchanger()); // 添加员工看板数据转换器
            exchangerList.add(new SandTableExchanger()); // 网格看板数据转换
            exchangerList.add(new SanderExchanger()); // 现费数据转换
            exchangerList.add(new ResourceExchanger()); // 添加资源转换器
        }

        try {
            long start = System.currentTimeMillis();
            for (Exchanger e : exchangerList) {
                System.out.println("============" + e.getClass().getName() + "===========");
                long time = System.currentTimeMillis();
                e.process(connection);
                System.out.println("============" + (System.currentTimeMillis() - time) / 1000 + "===========");
            }
            System.out.println("==========完成全部转换" + (System.currentTimeMillis() - start) / 1000 + "============");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
