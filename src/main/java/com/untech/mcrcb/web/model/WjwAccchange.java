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
 * WJW_ACCCHANGE
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_ACCCHANGE")
public class WjwAccchange implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**UNIT_NO*/
	private String unitNo;
	
	/**UNIT_NAME*/
	private String unitName;
	
	/**ACC_NO*/
	private String accNo;
	
	/**ACC_NAME*/
	private String accName;
	
	/**DF_ACCNO*/
	private String dfAccno;
	
	/**DF_ACCNAME*/
	private String dfAccname;
	
	/**AMOUNT*/
	private BigDecimal amount;
	
	/**DRUG_AMT*/
	private BigDecimal drugAmt;
	
	/**MEDC_AMT*/
	private BigDecimal medcAmt;
	
	/**TRAN_AMT*/
	private BigDecimal tranAmt;
	
	/**TRAN_TIME*/
	private String tranTime;
	
	/**1-收入，2-支出*/
	private Integer inOrOut;
	
	/**OTHER_AMT*/
	private BigDecimal otherAmt;
	
	/**NOTE1*/
	private String note1;
	
	/**NOTE2*/
	private String note2;
	
	private String descstr;
	
	/**1 入账 2 清分 3 利息*/
	private Integer flag;
	
	private Integer tradeCount;//记录待清分入账笔数
	
	private Integer unkType;
	
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
	
	@Column(name="UNIT_NAME", length=200, nullable=true)
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Column(name="ACC_NO", length=32, nullable=true)
	public String getAccNo() {
		return accNo;
	}
	
	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}
	
	@Column(name="ACC_NAME", length=200, nullable=true)
	public String getAccName() {
		return accName;
	}
	
	public void setAccName(String accName) {
		this.accName = accName;
	}
	
	@Column(name="DF_ACCNO", length=32, nullable=true)
	public String getDfAccno() {
		return dfAccno;
	}
	
	public void setDfAccno(String dfAccno) {
		this.dfAccno = dfAccno;
	}
	
	@Column(name="DF_ACCNAME", length=200, nullable=true)
	public String getDfAccname() {
		return dfAccname;
	}
	
	public void setDfAccname(String dfAccname) {
		this.dfAccname = dfAccname;
	}
	
	@Column(name="AMOUNT", length=21, nullable=true)
	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	@Column(name="DRUG_AMT", length=21, nullable=true)
	public BigDecimal getDrugAmt() {
		return drugAmt;
	}
	
	public void setDrugAmt(BigDecimal drugAmt) {
		this.drugAmt = drugAmt;
	}
	
	@Column(name="MEDC_AMT", length=21, nullable=true)
	public BigDecimal getMedcAmt() {
		return medcAmt;
	}
	
	public void setMedcAmt(BigDecimal medcAmt) {
		this.medcAmt = medcAmt;
	}
	
	@Column(name="TRAN_AMT", length=21, nullable=true)
	public BigDecimal getTranAmt() {
		return tranAmt;
	}
	
	public void setTranAmt(BigDecimal tranAmt) {
		this.tranAmt = tranAmt;
	}
	
	@Column(name="TRAN_TIME", length=30, nullable=true)
	public String getTranTime() {
		return tranTime;
	}
	
	public void setTranTime(String tranTime) {
		this.tranTime = tranTime;
	}
	
	@Column(name="IN_OR_OUT", length=10, nullable=true)
	public Integer getInOrOut() {
		return inOrOut;
	}
	
	public void setInOrOut(Integer inOrOut) {
		this.inOrOut = inOrOut;
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

	@Column(name="DESCSTR", length=100, nullable=true)
	public String getDescstr() {
		return descstr;
	}

	public void setDescstr(String descstr) {
		this.descstr = descstr;
	}

	@Column(name="FLAG", length=10, nullable=true)
	public Integer getFlag() {
		return flag;
	}
	
	
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	@Column(name="TRADE_COUNT",length=10, nullable=true)
	public Integer getTradeCount() {
		return tradeCount;
	}

	public void setTradeCount(Integer tradeCount) {
		this.tradeCount = tradeCount;
	}
	@Column(name="UNK_TYPE",length=10, nullable=true)
	public Integer getUnkType() {
		return unkType;
	}

	public void setUnkType(Integer unkType) {
		this.unkType = unkType;
	}
	
	
	
}
