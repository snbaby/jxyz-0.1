package jxyz.model;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import jxyz.utils.Tools;

public class Grid {
	private String code;
	private String name;
	private String parent_code;
	private int level;
	private BigDecimal center_longitude;

	public Grid(ResultSet rs) throws Exception {
		ResultSetMetaData rsmd = rs.getMetaData();
		int size = rsmd.getColumnCount();
		for (int i = 0; i < size; i++) {
			String fname = rsmd.getColumnName(i + 1);
			if(!Tools.isExistFieldName(fname, this.getClass()))
			{
				continue;
			}
			Field f = this.getClass().getDeclaredField(fname);
			if(f != null)
			{
				f.set(this, rs.getObject(rsmd.getColumnLabel(i + 1)));
			}
			else
			{
				System.out.println("error： 未知字段: " + rs.getObject(rsmd.getColumnLabel(i + 1)));
			}
		}
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParent_code() {
		return parent_code;
	}

	public void setParent_code(String parent_code) {
		this.parent_code = parent_code;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public BigDecimal getCenter_longitude() {
		return center_longitude;
	}

	public void setCenter_longitude(BigDecimal center_longitude) {
		this.center_longitude = center_longitude;
	}

}
