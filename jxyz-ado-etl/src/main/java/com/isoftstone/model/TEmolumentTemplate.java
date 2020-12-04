package com.isoftstone.model;

import java.math.BigDecimal;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;


/**
 * The persistent class for the t_emolument_template database table.
 * 
 */
@Entity
@Table(name="t_emolument_template")
public class TEmolumentTemplate extends ActiveRecord<TEmolumentTemplate>  {

	@Id
	private int id;

	@Column(name="basic_salary")
	private BigDecimal basicSalary;

	@Column(name="city_code")
	private String cityCode;

	private String code;

	private String name;

	public TEmolumentTemplate() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getBasicSalary() {
		return this.basicSalary;
	}

	public void setBasicSalary(BigDecimal basicSalary) {
		this.basicSalary = basicSalary;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}