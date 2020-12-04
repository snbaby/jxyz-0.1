package com.isoftstone.jxyz.model;

import java.math.BigDecimal;

import com.github.drinkjava2.jdialects.annotation.jpa.Column;
import com.github.drinkjava2.jdialects.annotation.jpa.Entity;
import com.github.drinkjava2.jdialects.annotation.jpa.Id;
import com.github.drinkjava2.jdialects.annotation.jpa.Table;
import com.github.drinkjava2.jsqlbox.ActiveRecord;

/**
 * The persistent class for the t_emolument_rule database table.
 * 
 */
@Entity
@Table(name = "t_emolument_rule")
public class TEmolumentRule extends ActiveRecord<TEmolumentRule> {

	@Id
	private int id;

	private String code;

	@Column(name = "commission_rate")
	private BigDecimal commissionRate;

	@Column(name = "fixed_income")
	private BigDecimal fixedIncome;

	@Column(name = "is_discount")
	private String isDiscount;

	@Column(name = "is_loose_items")
	private String isLooseItems;

	@Column(name = "max_discount")
	private BigDecimal maxDiscount;

	@Column(name = "min_discount")
	private BigDecimal minDiscount;

	private String name;

	@Column(name = "template_code")
	private String templateCode;

	private String type;

	public TEmolumentRule() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getCommissionRate() {
		return this.commissionRate;
	}

	public void setCommissionRate(BigDecimal commissionRate) {
		this.commissionRate = commissionRate;
	}

	public BigDecimal getFixedIncome() {
		return this.fixedIncome;
	}

	public void setFixedIncome(BigDecimal fixedIncome) {
		this.fixedIncome = fixedIncome;
	}

	public String getIsDiscount() {
		return this.isDiscount;
	}

	public void setIsDiscount(String isDiscount) {
		this.isDiscount = isDiscount;
	}

	public String getIsLooseItems() {
		return this.isLooseItems;
	}

	public void setIsLooseItems(String isLooseItems) {
		this.isLooseItems = isLooseItems;
	}

	public BigDecimal getMaxDiscount() {
		return this.maxDiscount;
	}

	public void setMaxDiscount(BigDecimal maxDiscount) {
		this.maxDiscount = maxDiscount;
	}

	public BigDecimal getMinDiscount() {
		return this.minDiscount;
	}

	public void setMinDiscount(BigDecimal minDiscount) {
		this.minDiscount = minDiscount;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemplateCode() {
		return this.templateCode;
	}

	public void setTemplateCode(String templateCode) {
		this.templateCode = templateCode;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}