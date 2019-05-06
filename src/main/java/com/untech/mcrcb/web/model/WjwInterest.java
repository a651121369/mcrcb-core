package com.untech.mcrcb.web.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@Entity
@Table(name="WJW_INTEREST")
public class WjwInterest implements Serializable{
		
	private Long id;
	private String unitNo;
	private String unitName;
	private String inAccno;
	private String inAccname;
	private String inBank;
	private String outAccno;
	private String outAccname;
	private String outBank;
	private BigDecimal amount;
	private String voucher;
	private Integer payWay;
	private Integer status;
	private Integer inOrOut;//收入/支出户，标记该笔利息是收入户还是支出户支出的利息
	private String payTime;
	private String operator;
	private String fhUser;
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
	@Column(name="UNIT_NO",length=20,nullable=true)
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	@Column(name="UNIT_NAME",length=200,nullable=true)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name="IN_ACCNO",length=32,nullable=true)
	public String getInAccno() {
		return inAccno;
	}
	public void setInAccno(String inAccno) {
		this.inAccno = inAccno;
	}
	@Column(name="IN_ACCNAME",length=200,nullable=true)
	public String getInAccname() {
		return inAccname;
	}
	public void setInAccname(String inAccname) {
		this.inAccname = inAccname;
	}
	@Column(name="IN_BANK",length=200,nullable=true)
	public String getInBank() {
		return inBank;
	}
	public void setInBank(String inBank) {
		this.inBank = inBank;
	}
	@Column(name="OUT_ACCNO",length=32,nullable=true)
	public String getOutAccno() {
		return outAccno;
	}
	public void setOutAccno(String outAccno) {
		this.outAccno = outAccno;
	}
	@Column(name="OUT_ACCNAME",length=200,nullable=true)
	public String getOutAccname() {
		return outAccname;
	}
	public void setOutAccname(String outAccname) {
		this.outAccname = outAccname;
	}
	@Column(name="OUT_BANK",length=200,nullable=true)
	public String getOutBank() {
		return outBank;
	}
	public void setOutBank(String outBank) {
		this.outBank = outBank;
	}
	@Column(name="AMOUNT",length=21, nullable=true)
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name="VOUCHER",length=32,nullable=true)	
	public String getVoucher() {
		return voucher;
	}
	public void setVoucher(String voucher) {
		this.voucher = voucher;
	}
	@Column(name="PAY_WAY",length=10, nullable=true)
	public Integer getPayWay() {
		return payWay;
	}
	public void setPayWay(Integer payWay) {
		this.payWay = payWay;
	}
	@Column(name="STATUS",length=10, nullable=true)
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	@Column(name="IN_OR_OUT",length=10, nullable=true)
	public Integer getInOrOut() {
		return inOrOut;
	}
	public void setInOrOut(Integer inOrOut) {
		this.inOrOut = inOrOut;
	}
	@Column(name="PAY_TIME",length=30, nullable=true)
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	@Column(name="OPERATOR",length=20, nullable=true)
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	@Column(name="FH_USER",length=20, nullable=true)
	public String getFhUser() {
		return fhUser;
	}
	public void setFhUser(String fhUser) {
		this.fhUser = fhUser;
	}
	@Column(name="FH_TIME",length=30, nullable=true)
	public String getFhTime() {
		return fhTime;
	}
	public void setFhTime(String fhTime) {
		this.fhTime = fhTime;
	}
	

}
