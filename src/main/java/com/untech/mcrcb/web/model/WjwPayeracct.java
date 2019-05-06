package com.untech.mcrcb.web.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;

import org.hibernate.annotations.GenericGenerator;



/**
 * WJW_PAYERACCT
 * @author liuyong
 * @since  2015-11-16
 */
@Entity
@Table(name="WJW_PAYERACCT")
public class WjwPayeracct implements Serializable{
	/**ID*/
	private Long id;
	
	/**ACCT_NO*/
	private String acctNo;
	
	/**ACCT_NAME*/
	private String acctName;
	
	/**ACCT_BANK*/
	private String acctBank;
	
	/**NOTE1*/
	private String note1;
	
	/**NOTE2*/
	private String note2;
	/**UNIT_NO*/
	private String unitNo;
	
	@Id
    @GeneratedValue(generator = "tableGenerator")     
    @GenericGenerator(name = "tableGenerator", strategy="com.unteck.common.dao.key.SequenceGenerator")
	@Column(name="ID", length=30, nullable=false)
	public Long getId() {
		return id;
	}
		
	public void setId(Long id) {
		this.id = id;
	}
	
    @Column(name="ACCT_NO", length=32, nullable=false)
	public String getAcctNo() {
		return acctNo;
	}
	
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	
	@Column(name="ACCT_NAME", length=200, nullable=true)
	public String getAcctName() {
		return acctName;
	}
	
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	
	@Column(name="ACCT_BANK", length=200, nullable=true)
	public String getAcctBank() {
		return acctBank;
	}
	
	public void setAcctBank(String acctBank) {
		this.acctBank = acctBank;
	}
	
	@Column(name="NOTE1", length=100, nullable=true)
	public String getNote1() {
		return note1;
	}
	
	public void setNote1(String note1) {
		this.note1 = note1;
	}
	
	@Column(name="NOTE2", length=100, nullable=true)
	public String getNote2() {
		return note2;
	}
	
	public void setNote2(String note2) {
		this.note2 = note2;
	}
	@Column(name="UNIT_NO",length=20,nullable=false)
	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
}
