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
 * WJW_BFHZB
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_BFHZB")
public class WjwBfhzb implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**UNIT_NO*/
	private String unitNo;
	
	/**UNIT_NAME*/
	private String unitName;
	
	/**BF_AMT*/
	private BigDecimal bfAmt;
	
	/**BF_DRUG_AMT*/
	private BigDecimal bfDrugAmt;
	
	/**BF_MEDC_AMT*/
	private BigDecimal bfMedcAmt;
	
	/**BF_TIM*/
	private String bfTim;
	
	/**OPER_NO*/
	private String operNo;
	
	/**OPER_TIME*/
	private String operTime;
	
	/**DETAIL*/
	private String detail;
	
	/**CONN_NO*/
	private String connNo;
	
	/**REMARK*/
	private String remark;
	
	/**NOTE1*/
	private String note1;
	
	/**NOTE2*/
	private String note2;
	
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
	
	/**CERT_NO*/
	private String certNo;
	
	/**XN_ACCTNO*/
	private String xnAcctno;
	
	/**XN_ACCTNAME*/
	private String xnAcctName;
	
	/**ZC_ACCTNO*/
	private String zcAcctno;
	
	/**ZC_ACCTNAME*/
	private String zcAcctname;
	
	/**1-申请，2-初审，3-复审，4-作废，5-完成 */
	private Integer status;
	
	/**FH_USER*/
	private String fhUser;
	
	/**FH_TIME*/
	private String fhTime;
	
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
	
	@Column(name="UNIT_NO", length=30, nullable=true)
	public String getUnitNo() {
		return unitNo;
	}
	
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	@Column(name="UNIT_NAME", length=200, nullable=true)
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Column(name="BF_AMT", length=21, nullable=true)
	public BigDecimal getBfAmt() {
		return bfAmt;
	}
	
	public void setBfAmt(BigDecimal bfAmt) {
		this.bfAmt = bfAmt;
	}
	
	@Column(name="BF_DRUG_AMT", length=21, nullable=true)
	public BigDecimal getBfDrugAmt() {
		return bfDrugAmt;
	}
	
	public void setBfDrugAmt(BigDecimal bfDrugAmt) {
		this.bfDrugAmt = bfDrugAmt;
	}
	
	@Column(name="BF_MEDC_AMT", length=21, nullable=true)
	public BigDecimal getBfMedcAmt() {
		return bfMedcAmt;
	}
	
	public void setBfMedcAmt(BigDecimal bfMedcAmt) {
		this.bfMedcAmt = bfMedcAmt;
	}
	
	@Column(name="BF_TIM", length=30, nullable=true)
	public String getBfTim() {
		return bfTim;
	}
	
	public void setBfTim(String bfTim) {
		this.bfTim = bfTim;
	}
	
	@Column(name="OPER_NO", length=20, nullable=true)
	public String getOperNo() {
		return operNo;
	}
	
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	
	@Column(name="OPER_TIME", length=30, nullable=true)
	public String getOperTime() {
		return operTime;
	}
	
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	
	@Column(name="DETAIL", length=100, nullable=true)
	public String getDetail() {
		return detail;
	}
	
	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	@Column(name="CONN_NO", length=32, nullable=true)
	public String getConnNo() {
		return connNo;
	}
	
	public void setConnNo(String connNo) {
		this.connNo = connNo;
	}
	
	@Column(name="REMARK", length=80, nullable=true)
	public String getRemark() {
		return remark;
	}
	
	public void setRemark(String remark) {
		this.remark = remark;
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

	@Column(name="CERT_NO", length=32, nullable=true)
	public String getCertNo() {
		return certNo;
	}
	
	public void setCertNo(String certNo) {
		this.certNo = certNo;
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
	@Column(name="STATUS", length=10, nullable=true)
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
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
}
