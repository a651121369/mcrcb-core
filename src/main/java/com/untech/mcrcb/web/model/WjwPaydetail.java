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
 * WJW_PAYDETAIL
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_PAYDETAIL")
public class WjwPaydetail implements Serializable{
	
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
	
	/**PAY_TIME*/
	private String payTime;
	
	/**ZJ_FLD*/
	private String zjFld;
	
	/**1-现金，2-转账*/
	private Integer payWay;
	
	/**TOP_YSDW*/
	private String topYsdw;
	
	/**FOOT_YSDW*/
	private String footYsdw;
	
	/**ITME_YS*/
	private String itmeYs;
	
	/**FUNC_FL*/
	private String funcFl;
	
	/**ECNO_FL*/
	private String ecnoFl;
	
	/**ZB_DETAIL*/
	private String zbDetail;
	
	/**人民币  156*/
	private String currency;
	
	/**1-申请，2-初审，3-复审，4-作废，5-完成*/
	private Integer status;
	
	/**OPER_NO*/
	private String operNo;
	
	/**YT*/
	private String yt;
	
	/**1-医疗 ，2-药品，3-其他*/
	private Integer item;
	
	/**CONN_NO*/
	private String connNo;
	
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
	
	/**FH_USER*/
	private String fhUser;
	
	/**FH_TIME*/
	private String fhTime;
	
	private Integer backFlg;//退汇标志   1--退回未处理 2--已记账
	
	private String backVoucher; //退回凭证号
	
	/**PRINT_TIME*/
	private String printTime;
	
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
	
	@Column(name="ZC_ACCTNO", length=32, nullable=true)
	public String getZcAcctno() {
		return zcAcctno;
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
	
	@Column(name="PAY_TIME", length=30, nullable=true)
	public String getPayTime() {
		return payTime;
	}
	
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	
	@Column(name="ZJ_FLD", length=60, nullable=true)
	public String getZjFld() {
		return zjFld;
	}
	
	public void setZjFld(String zjFld) {
		this.zjFld = zjFld;
	}
	
	@Column(name="PAY_WAY", length=10, nullable=true)
	public Integer getPayWay() {
		return payWay;
	}
	
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	
	@Column(name="TOP_YSDW", length=30, nullable=true)
	public String getTopYsdw() {
		return topYsdw;
	}
	
	public void setTopYsdw(String topYsdw) {
		this.topYsdw = topYsdw;
	}
	
	@Column(name="FOOT_YSDW", length=30, nullable=true)
	public String getFootYsdw() {
		return footYsdw;
	}
	
	public void setFootYsdw(String footYsdw) {
		this.footYsdw = footYsdw;
	}
	
	@Column(name="ITME_YS", length=30, nullable=true)
	public String getItmeYs() {
		return itmeYs;
	}
	
	public void setItmeYs(String itmeYs) {
		this.itmeYs = itmeYs;
	}
	
	@Column(name="FUNC_FL", length=60, nullable=true)
	public String getFuncFl() {
		return funcFl;
	}
	
	public void setFuncFl(String funcFl) {
		this.funcFl = funcFl;
	}
	
	@Column(name="ECNO_FL", length=60, nullable=true)
	public String getEcnoFl() {
		return ecnoFl;
	}
	
	public void setEcnoFl(String ecnoFl) {
		this.ecnoFl = ecnoFl;
	}
	
	@Column(name="ZB_DETAIL", length=60, nullable=true)
	public String getZbDetail() {
		return zbDetail;
	}
	
	public void setZbDetail(String zbDetail) {
		this.zbDetail = zbDetail;
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
	
	@Column(name="OPER_NO", length=32, nullable=true)
	public String getOperNo() {
		return operNo;
	}
	
	public void setOperNo(String operNo) {
		this.operNo = operNo;
	}
	
	@Column(name="YT", length=100, nullable=true)
	public String getYt() {
		return yt;
	}
	
	public void setYt(String yt) {
		this.yt = yt;
	}
	
	@Column(name="ITEM", length=10, nullable=true)
	public Integer getItem() {
		return item;
	}
	
	public void setItem(Integer item) {
		this.item = item;
	}
	
	@Column(name="CONN_NO", length=32, nullable=true)
	public String getConnNo() {
		return connNo;
	}
	
	public void setConnNo(String connNo) {
		this.connNo = connNo;
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
	@Column(name="BACK_FLG",length=10, nullable=true)
	public Integer getBackFlg() {
		return backFlg;
	}

	public void setBackFlg(Integer backFlg) {
		this.backFlg = backFlg;
	}
	@Column(name="BACK_VOUCHER",length=32, nullable=true)
	public String getBackVoucher() {
		return backVoucher;
	}

	public void setBackVoucher(String backVoucher) {
		this.backVoucher = backVoucher;
	}
	
	@Column(name="PRINT_TIME", length=30, nullable=true)
	public String getPrintTime() {
		return printTime;
	}
	
	public void setPrintTime(String printTime) {
		this.printTime = printTime;
	}
	
	
}
