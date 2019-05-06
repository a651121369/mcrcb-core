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
 * WJW_BFDETAIL
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_BFDETAIL")
public class WjwBfdetail implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**UNIT_NO*/
	private String unitNo;
	
	/**AMOUNT*/
	private BigDecimal amount;
	
	/**UNIT_NAME*/
	private String unitName;
	
	/**BF_TIME*/
	private String bfTime;
	
	/**1-药品，2-医疗，3-其他*/
	private Integer bfItem;
	
	/**IN_BFQ_AMT*/
	private BigDecimal inBfqAmt;
	
	/**IN_BFH_AMT*/
	private BigDecimal inBfhAmt;
	
	/**OUT_BFQ_AMT*/
	private BigDecimal outBfqAmt;
	
	/**OUT_BFH_AMT*/
	private BigDecimal outBfhAmt;
	
	/**CONN_NO*/
	private String connNo;
	
	/**DRUG_BF_AMT*/
	private BigDecimal drugBfAmt;
	
	/**MEDC_BF_AMT*/
	private BigDecimal medcBfAmt;
	
	/**NOTE1*/
	private String note1;
	
	/**NOTE2*/
	private String note2;
	
	/**XN_ACCTNO*/
	private String xnAcctno;
	
	/**XN_ACCTNAME*/
	private String xnAcctName;
	
	/**ZC_ACCTNO*/
	private String zcAcctno;
	
	/**ZC_ACCTNAME*/
	private String zcAcctname;
	
	@Id
    @GeneratedValue(generator = "tableGenerator")     
    @GenericGenerator(name = "tableGenerator", strategy="com.unteck.common.dao.key.SequenceGenerator")
    @Column(name="ID", length=10, nullable=false)
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="UNIT_NO", length=20, nullable=true)
	public String getUnitNo() {
		return unitNo;
	}
	
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	@Column(name="AMOUNT", length=21, nullable=true)
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name="UNIT_NAME", length=200, nullable=true)
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Column(name="BF_TIME", length=32, nullable=true)
	public String getBfTime() {
		return bfTime;
	}
	
	public void setBfTime(String bfTime) {
		this.bfTime = bfTime;
	}
	
	@Column(name="BF_ITEM", length=10, nullable=true)
	public Integer getBfItem() {
		return bfItem;
	}
	
	public void setBfItem(Integer bfItem) {
		this.bfItem = bfItem;
	}
	
	@Column(name="IN_BFQ_AMT", length=21, nullable=true)
	public BigDecimal getInBfqAmt() {
		return inBfqAmt;
	}
	
	public void setInBfqAmt(BigDecimal inBfqAmt) {
		this.inBfqAmt = inBfqAmt;
	}
	
	@Column(name="IN_BFH_AMT", length=21, nullable=true)
	public BigDecimal getInBfhAmt() {
		return inBfhAmt;
	}
	
	public void setInBfhAmt(BigDecimal inBfhAmt) {
		this.inBfhAmt = inBfhAmt;
	}
	
	@Column(name="OUT_BFQ_AMT", length=21, nullable=true)
	public BigDecimal getOutBfqAmt() {
		return outBfqAmt;
	}
	
	public void setOutBfqAmt(BigDecimal outBfqAmt) {
		this.outBfqAmt = outBfqAmt;
	}
	
	@Column(name="OUT_BFH_AMT", length=21, nullable=true)
	public BigDecimal getOutBfhAmt() {
		return outBfhAmt;
	}
	
	public void setOutBfhAmt(BigDecimal outBfhAmt) {
		this.outBfhAmt = outBfhAmt;
	}
	
	@Column(name="CONN_NO", length=32, nullable=true)
	public String getConnNo() {
		return connNo;
	}
	
	public void setConnNo(String connNo) {
		this.connNo = connNo;
	}
	
	@Column(name="DRUG_BF_AMT", length=21, nullable=true)
	public BigDecimal getDrugBfAmt() {
		return drugBfAmt;
	}
	
	public void setDrugBfAmt(BigDecimal drugBfAmt) {
		this.drugBfAmt = drugBfAmt;
	}
	
	@Column(name="MEDC_BF_AMT", length=21, nullable=true)
	public BigDecimal getMedcBfAmt() {
		return medcBfAmt;
	}
	
	public void setMedcBfAmt(BigDecimal medcBfAmt) {
		this.medcBfAmt = medcBfAmt;
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
	@Column(name="XN_ACCTNO", length=32, nullable=true)
	public String getXnAcctno() {
		return xnAcctno;
	}

	public void setXnAcctno(String xnAcctno) {
		this.xnAcctno = xnAcctno;
	}
	@Column(name="XN_ACCTNAME", length=200, nullable=true)
	public String getXnAcctName() {
		return xnAcctName;
	}

	public void setXnAcctName(String xnAcctName) {
		this.xnAcctName = xnAcctName;
	}
	@Column(name="ZC_ACCTNO", length=32, nullable=true)
	public String getZcAcctno() {
		return zcAcctno;
	}

	public void setZcAcctno(String zcAcctno) {
		this.zcAcctno = zcAcctno;
	}
	@Column(name="ZC_ACCTNAME", length=200, nullable=true)
	public String getZcAcctname() {
		return zcAcctname;
	}
	
	public void setZcAcctname(String zcAcctname) {
		this.zcAcctname = zcAcctname;
	}
	
}
