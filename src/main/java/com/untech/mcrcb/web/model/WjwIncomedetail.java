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
 * WJW_INCOMEDETAIL
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_INCOMEDETAIL")
public class WjwIncomedetail implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**UNIT_NO*/
	private String unitNo;
	
	/**AMOUNT*/
	private BigDecimal amount;
	
	/**UNIT_NAME*/
	private String unitName;
	
	/**OUT_ACCNO*/
	private String outAccno;
	
	/**OUT_ACCNAME*/
	private String outAccname;
	
	/**OUT_BANK*/
	private String outBank;
	
	/**IN_ACCNO*/
	private String inAccno;
	
	/**IN_NAME*/
	private String inName;
	
	/**IN_BANK*/
	private String inBank;
	
	/**ITEM1*/
	private String item1;
	
	/**ITEM2*/
	private String item2;
	
	/**ITEM3*/
	private String item3;
	
	/**ITEM1_DW*/
	private String item1Dw;
	
	/**ITEM2_DW*/
	private String item2Dw;
	
	/**ITEM3_DW*/
	private String item3Dw;
	
	/**ITEM1_NUM*/
	private Integer item1Num;
	
	/**ITEM2_NUM*/
	private Integer item2Num;
	
	/**ITEM3_NUM*/
	private Integer item3Num;
	
	/**ITEM1_ST*/
	private String item1St;
	
	/**ITEM2_ST*/
	private String item2St;
	
	/**ITEM3_ST*/
	private String item3St;
	
	/**ITEM1_AMT*/
	private BigDecimal item1Amt;
	
	/**ITEM2_AMT*/
	private BigDecimal item2Amt;
	
	/**ITEM3_AMT*/
	private BigDecimal item3Amt;
	
	/**ITEM1_CODE*/
	private String item1Code;
	
	/**ITEM2_CODE*/
	private String item2Code;
	
	/**ITEM3_CODE*/
	private String item3Code;
	
	/**人民币  156*/
	private String currency;
	
	/**1-申请，2-初审，3-复审，4-作废，5-完成*/
	private Integer status;
	
	/**CERT_NO*/
	private String certNo;
	
	/**YT*/
	private String yt;
	
	/**INC_TIME*/
	private String incTime;
	
	/**OPER_NO*/
	private String operNo;
	
	/**OPER_NAME*/
	private String operName;
	
	/**FH_USER*/
	private String fhUser;
	
	/**FH_TIME*/
	private String fhTime;
	
	/**NOTE1*/
	private String note1;
	
	/**NOTE2*/
	private String note2;
	
	/**XN_ACCTNO*/
	private String xnAcctno;
	
	/**XN_ACCTNAME*/
	private String xnAcctName;
	
	/**DRUG_AMT*/
	private BigDecimal drugAmt;
	
	/**MEDICAL_AMT*/
	private BigDecimal medicalAmt;
	
	/**OTHER_AMT*/
	private BigDecimal otherAmt;
	
	/**ZC_ACCTNO*/
	private String zcAcctno;
	
	/**ZC_ACCTNAME*/
	private String zcAcctname;
	
	@Id
    @GeneratedValue(generator = "tableGenerator")     
    @GenericGenerator(name = "tableGenerator", strategy="com.unteck.common.dao.key.SequenceGenerator")//SequenceGeneratorInt SequenceGenerator
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
	
	@Column(name="OUT_ACCNO", length=32, nullable=true)
	public String getOutAccno() {
		return outAccno;
	}
	
	public void setOutAccno(String outAccno) {
		this.outAccno = outAccno;
	}
	
	@Column(name="OUT_ACCNAME", length=200, nullable=true)
	public String getOutAccname() {
		return outAccname;
	}
	
	public void setOutAccname(String outAccname) {
		this.outAccname = outAccname;
	}
	
	@Column(name="OUT_BANK", length=200, nullable=true)
	public String getOutBank() {
		return outBank;
	}
	
	public void setOutBank(String outBank) {
		this.outBank = outBank;
	}
	
	@Column(name="IN_ACCNO", length=32, nullable=true)
	public String getInAccno() {
		return inAccno;
	}
	
	public void setInAccno(String inAccno) {
		this.inAccno = inAccno;
	}
	
	@Column(name="IN_NAME", length=200, nullable=true)
	public String getInName() {
		return inName;
	}
	
	public void setInName(String inName) {
		this.inName = inName;
	}
	
	@Column(name="IN_BANK", length=200, nullable=true)
	public String getInBank() {
		return inBank;
	}
	
	public void setInBank(String inBank) {
		this.inBank = inBank;
	}
	
	@Column(name="ITEM1", length=60, nullable=true)
	public String getItem1() {
		return item1;
	}
	
	public void setItem1(String item1) {
		this.item1 = item1;
	}
	
	@Column(name="ITEM2", length=60, nullable=true)
	public String getItem2() {
		return item2;
	}
	
	public void setItem2(String item2) {
		this.item2 = item2;
	}
	
	@Column(name="ITEM3", length=60, nullable=true)
	public String getItem3() {
		return item3;
	}
	
	public void setItem3(String item3) {
		this.item3 = item3;
	}
	
	@Column(name="ITEM1_DW", length=30, nullable=true)
	public String getItem1Dw() {
		return item1Dw;
	}
	
	public void setItem1Dw(String item1Dw) {
		this.item1Dw = item1Dw;
	}
	
	@Column(name="ITEM2_DW", length=30, nullable=true)
	public String getItem2Dw() {
		return item2Dw;
	}
	
	public void setItem2Dw(String item2Dw) {
		this.item2Dw = item2Dw;
	}
	
	@Column(name="ITEM3_DW", length=30, nullable=true)
	public String getItem3Dw() {
		return item3Dw;
	}
	
	public void setItem3Dw(String item3Dw) {
		this.item3Dw = item3Dw;
	}
	
	@Column(name="ITEM1_NUM", length=10, nullable=true)
	public Integer getItem1Num() {
		return item1Num;
	}
	
	public void setItem1Num(Integer item1Num) {
		this.item1Num = item1Num;
	}
	
	@Column(name="ITEM2_NUM", length=10, nullable=true)
	public Integer getItem2Num() {
		return item2Num;
	}
	
	public void setItem2Num(Integer item2Num) {
		this.item2Num = item2Num;
	}
	
	@Column(name="ITEM3_NUM", length=10, nullable=true)
	public Integer getItem3Num() {
		return item3Num;
	}
	
	public void setItem3Num(Integer item3Num) {
		this.item3Num = item3Num;
	}
	
	@Column(name="ITEM1_ST", length=60, nullable=true)
	public String getItem1St() {
		return item1St;
	}
	
	public void setItem1St(String item1St) {
		this.item1St = item1St;
	}
	
	@Column(name="ITEM2_ST", length=60, nullable=true)
	public String getItem2St() {
		return item2St;
	}
	
	public void setItem2St(String item2St) {
		this.item2St = item2St;
	}
	
	@Column(name="ITEM3_ST", length=60, nullable=true)
	public String getItem3St() {
		return item3St;
	}
	
	public void setItem3St(String item3St) {
		this.item3St = item3St;
	}
	
	@Column(name="ITEM1_AMT", length=21, nullable=true)
	public BigDecimal getItem1Amt() {
		return item1Amt;
	}
	
	public void setItem1Amt(BigDecimal item1Amt) {
		this.item1Amt = item1Amt;
	}
	
	@Column(name="ITEM2_AMT", length=21, nullable=true)
	public BigDecimal getItem2Amt() {
		return item2Amt;
	}
	
	public void setItem2Amt(BigDecimal item2Amt) {
		this.item2Amt = item2Amt;
	}
	
	@Column(name="ITEM3_AMT", length=21, nullable=true)
	public BigDecimal getItem3Amt() {
		return item3Amt;
	}
	
	public void setItem3Amt(BigDecimal item3Amt) {
		this.item3Amt = item3Amt;
	}
	
	@Column(name="ITEM1_CODE", length=30, nullable=true)
	public String getItem1Code() {
		return item1Code;
	}
	
	public void setItem1Code(String item1Code) {
		this.item1Code = item1Code;
	}
	
	@Column(name="ITEM2_CODE", length=30, nullable=true)
	public String getItem2Code() {
		return item2Code;
	}
	
	public void setItem2Code(String item2Code) {
		this.item2Code = item2Code;
	}
	
	@Column(name="ITEM3_CODE", length=30, nullable=true)
	public String getItem3Code() {
		return item3Code;
	}
	
	public void setItem3Code(String item3Code) {
		this.item3Code = item3Code;
	}
	
	@Column(name="CURRENCY", length=16, nullable=true)
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name="STATUS", length=10, nullable=true)
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="CERT_NO", length=32, nullable=true)
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	
	@Column(name="YT", length=100, nullable=true)
	public String getYt() {
		return yt;
	}
	
	public void setYt(String yt) {
		this.yt = yt;
	}
	
	@Column(name="INC_TIME", length=30, nullable=true)
	public String getIncTime() {
		return incTime;
	}
	
	public void setIncTime(String incTime) {
		this.incTime = incTime;
	}
	
	@Column(name="OPER_NO", length=30, nullable=true)
	public String getOperNo() {
		return operNo;
	}
	
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	
	@Column(name="OPER_NAME", length=60, nullable=true)
	public String getOperName() {
		return operName;
	}
	
	public void setOperName(String operName) {
		this.operName = operName;
	}
	
	@Column(name="FH_USER", length=20, nullable=true)
	public String getFhUser() {
		return fhUser;
	}
	
	public void setFhUser(String fhUser) {
		this.fhUser = fhUser;
	}
	
	@Column(name="FH_TIME", length=30, nullable=true)
	public String getFhTime() {
		return fhTime;
	}
	
	public void setFhTime(String fhTime) {
		this.fhTime = fhTime;
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
	
	@Column(name="DRUG_AMT", length=21, nullable=true)
	public BigDecimal getDrugAmt() {
		return drugAmt;
	}
	
	public void setDrugAmt(BigDecimal drugAmt) {
		this.drugAmt = drugAmt;
	}
	
	@Column(name="MEDICAL_AMT", length=21, nullable=true)
	public BigDecimal getMedicalAmt() {
		return medicalAmt;
	}
	
	public void setMedicalAmt(BigDecimal medicalAmt) {
		this.medicalAmt = medicalAmt;
	}
	
	
	@Column(name="OTHER_AMT", length=21, nullable=true)
	public BigDecimal getOtherAmt() {
		return otherAmt;
	}

	public void setOtherAmt(BigDecimal otherAmt) {
		this.otherAmt = otherAmt;
	}

	public void setXnAcctName(String xnAcctName) {
		this.xnAcctName = xnAcctName;
	}
	
}
