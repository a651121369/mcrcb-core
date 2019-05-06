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
 * WJW_ACCOUNT
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_ACCOUNT")
public class WjwAccount implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**ACC_NO*/
	private String accNo;
	
	/**CUST_NAME*/
	private String custName;

	/**1对公，2对私*/
	private Integer accCode;
	/**1对公，2对私*/
	private Integer accType;
	
	/**UNIT_NO*/
	private String unitNo;
	
	/**UNIT_NAME*/
	private String unitName;
	
	/**AMOUNT*/
	private BigDecimal amount;
	
	/**ACC_NUM*/
	private BigDecimal accNum;
	
	/**1-正常，2-注销*/
	private Integer accStatus;
	
	/**ACC_PARENT*/
	private String accParent;
	
	/**CREATE_TIME*/
	private String createTime;
	
	/**CREATE_USER*/
	private String createUser;
	
	/**UPDATE_TIME*/
	private String updateTime;
	
	/**UPDATE_USER*/
	private String updateUser;
	
	/**LEVEL*/
	private Integer level;
	
	/**RATE*/
	private BigDecimal rate;
	
	/**BANK_UNIT*/
	private String bankUnit;
	
	/**BANK_NAME*/
	private String bankName;
	
	/**BANK_AMOUNT*/
	private BigDecimal bankAmount;
	
	/**UNK_COME*/
	private BigDecimal unkCome;
	
	/**INT_COME*/
	private BigDecimal intCome;
	
	/**156-人名币*/
	private String currency;
	
	/**1-真实帐户，2-虚拟账户*/
	private Integer accFld;
	
	/**1-活期，2-定期，3-协议*/
	private Integer accPro;
	
	/**1-计息，2-不计息*/
	private Integer intFlag;
	
	/**1-按月计息，2-按季度计息，3-按年计息*/
	private Integer intType;
	
	/**1-收入，2-支出*/
	private Integer inOrOut;
	
	/**DRUG_AMT*/
	private BigDecimal drugAmt;
	
	/**MEDICAL_AMT*/
	private BigDecimal medicalAmt;
	
	/**OTHER_AMT*/
	private BigDecimal otherAmt;
	
	/**NOTE1*/
	private String note1;
	
	/**NOTE2*/
	private String note2;
	
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
	
	@Column(name="ACC_NO", length=32, nullable=true)
	public String getAccNo() {
		return accNo;
	}
	
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
	@Column(name="CUST_NAME", length=200, nullable=true)
	public String getCustName() {
		return custName;
	}
	
	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name="ACC_TYPE", length=10, nullable=true)
	public Integer getAccType() {
		return accType;
	}

	public void setAccType(Integer accType) {
		this.accType = accType;
	}

	@Column(name="ACCOUNT_SYS_CODE", length=10, nullable=true)
	public Integer getAccCode() {
		return accCode;
	}

	public void setAccCode(Integer accCode) {
		this.accCode = accCode;
	}
	
	@Column(name="UNIT_NO", length=20, nullable=true)
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
	
	@Column(name="AMOUNT", length=21, nullable=true)
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name="ACC_NUM", length=21, nullable=true)
	public BigDecimal getAccNum() {
		return accNum;
	}
	
	public void setAccNum(BigDecimal accNum) {
		this.accNum = accNum;
	}
	
	@Column(name="ACC_STATUS", length=10, nullable=true)
	public Integer getAccStatus() {
		return accStatus;
	}
	
	public void setAccStatus(Integer accStatus) {
		this.accStatus = accStatus;
	}
	
	@Column(name="ACC_PARENT", length=32, nullable=true)
	public String getAccParent() {
		return accParent;
	}
	
	public void setAccParent(String accParent) {
		this.accParent = accParent;
	}
	
	@Column(name="CREATE_TIME", length=30, nullable=true)
	public String getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	@Column(name="CREATE_USER", length=20, nullable=true)
	public String getCreateUser() {
		return createUser;
	}
	
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	
	@Column(name="UPDATE_TIME", length=30, nullable=true)
	public String getUpdateTime() {
		return updateTime;
	}
	
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
	@Column(name="UPDATE_USER", length=20, nullable=true)
	public String getUpdateUser() {
		return updateUser;
	}
	
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	
	@Column(name="LEVEL", length=10, nullable=true)
	public Integer getLevel() {
		return level;
	}
	
	public void setLevel(Integer level) {
		this.level = level;
	}
	
	@Column(name="RATE", length=9, nullable=true)
	public BigDecimal getRate() {
		return rate;
	}
	
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
	@Column(name="BANK_UNIT", length=20, nullable=true)
	public String getBankUnit() {
		return bankUnit;
	}
	
	public void setBankUnit(String bankUnit) {
		this.bankUnit = bankUnit;
	}
	
	@Column(name="BANK_NAME", length=200, nullable=true)
	public String getBankName() {
		return bankName;
	}
	
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Column(name="BANK_AMOUNT", length=21, nullable=true)
	public BigDecimal getBankAmount() {
		return bankAmount;
	}
	
	public void setBankAmount(BigDecimal bankAmount) {
		this.bankAmount = bankAmount;
	}
	
	@Column(name="UNK_COME", length=21, nullable=true)
	public BigDecimal getUnkCome() {
		return unkCome;
	}
	
	public void setUnkCome(BigDecimal unkCome) {
		this.unkCome = unkCome;
	}
	
	@Column(name="INT_COME", length=21, nullable=true)
	public BigDecimal getIntCome() {
		return intCome;
	}
	
	public void setIntCome(BigDecimal intCome) {
		this.intCome = intCome;
	}
	
	@Column(name="CURRENCY", length=16, nullable=true)
	public String getCurrency() {
		return currency;
	}
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	@Column(name="ACC_FLD", length=10, nullable=true)
	public Integer getAccFld() {
		return accFld;
	}
	
	public void setAccFld(Integer accFld) {
		this.accFld = accFld;
	}
	
	@Column(name="ACC_PRO", length=10, nullable=true)
	public Integer getAccPro() {
		return accPro;
	}
	
	public void setAccPro(Integer accPro) {
		this.accPro = accPro;
	}
	
	@Column(name="INT_FLAG", length=10, nullable=true)
	public Integer getIntFlag() {
		return intFlag;
	}
	
	public void setIntFlag(Integer intFlag) {
		this.intFlag = intFlag;
	}
	
	@Column(name="INT_TYPE", length=10, nullable=true)
	public Integer getIntType() {
		return intType;
	}
	
	public void setIntType(Integer intType) {
		this.intType = intType;
	}
	
	@Column(name="IN_OR_OUT", length=10, nullable=true)
	public Integer getInOrOut() {
		return inOrOut;
	}
	
	public void setInOrOut(Integer inOrOut) {
		this.inOrOut = inOrOut;
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
	
}
