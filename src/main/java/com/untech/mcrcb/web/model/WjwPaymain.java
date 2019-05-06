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
 * WJW_PAYMAIN
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_PAYMAIN")
public class WjwPaymain implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**UNIT_NAME*/
	private String unitName;
	
	/**UNIT_NO*/
	private String unitNo;
	
	/**PAY_TYPE*/
	private String payType;
	
	/**SQ_TIME*/
	private String sqTime;
	
	/**1-申请，2-初审，3-复审，4-作废，5-完成*/
	private Integer status;
	
	/**DSP_USERNO*/
	private String dspUserno;
	
	/**CS_USERNO*/
	private String csUserno;
	
	/**FS_USERNO*/
	private String fsUserno;
	
	/**CS_TIME*/
	private String csTime;
	
	/**FS_TIME*/
	private String fsTime;
	
	/**CONN_NO*/
	private String connNo;
	
	/**DSP_USERNAME*/
	private String dspUsername;
	
	/**CS_USERNAME*/
	private String csUsername;
	
	/**FS_USERNAME*/
	private String fsUsername;
	
	/**SQ_USERNO*/
	private String sqUserno;
	
	/**SQ_USERNAME*/
	private String sqUsername;
	
	/**REMARK*/
	private String remark;
	
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
	
	@Column(name="UNIT_NAME", length=200, nullable=true)
	public String getUnitName() {
		return unitName;
	}
	
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Column(name="UNIT_NO", length=30, nullable=true)
	public String getUnitNo() {
		return unitNo;
	}
	
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	
	@Column(name="PAY_TYPE", length=60, nullable=true)
	public String getPayType() {
		return payType;
	}
	
	public void setPayType(String payType) {
		this.payType = payType;
	}
	
	@Column(name="SQ_TIME", length=32, nullable=true)
	public String getSqTime() {
		return sqTime;
	}
	
	public void setSqTime(String sqTime) {
		this.sqTime = sqTime;
	}
	
	@Column(name="STATUS", length=10, nullable=true)
	public Integer getStatus() {
		return status;
	}
	
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Column(name="DSP_USERNO", length=20, nullable=true)
	public String getDspUserno() {
		return dspUserno;
	}
	
	public void setDspUserno(String dspUserno) {
		this.dspUserno = dspUserno;
	}
	
	@Column(name="CS_USERNO", length=20, nullable=true)
	public String getCsUserno() {
		return csUserno;
	}
	
	public void setCsUserno(String csUserno) {
		this.csUserno = csUserno;
	}
	
	@Column(name="FS_USERNO", length=20, nullable=true)
	public String getFsUserno() {
		return fsUserno;
	}
	
	public void setFsUserno(String fsUserno) {
		this.fsUserno = fsUserno;
	}
	
	@Column(name="CS_TIME", length=30, nullable=true)
	public String getCsTime() {
		return csTime;
	}
	
	public void setCsTime(String csTime) {
		this.csTime = csTime;
	}
	
	@Column(name="FS_TIME", length=30, nullable=true)
	public String getFsTime() {
		return fsTime;
	}
	
	public void setFsTime(String fsTime) {
		this.fsTime = fsTime;
	}
	
	@Column(name="CONN_NO", length=32, nullable=true)
	public String getConnNo() {
		return connNo;
	}
	
	public void setConnNo(String connNo) {
		this.connNo = connNo;
	}
	
	@Column(name="DSP_USERNAME", length=30, nullable=true)
	public String getDspUsername() {
		return dspUsername;
	}
	
	public void setDspUsername(String dspUsername) {
		this.dspUsername = dspUsername;
	}
	
	@Column(name="CS_USERNAME", length=30, nullable=true)
	public String getCsUsername() {
		return csUsername;
	}
	
	public void setCsUsername(String csUsername) {
		this.csUsername = csUsername;
	}
	
	@Column(name="FS_USERNAME", length=30, nullable=true)
	public String getFsUsername() {
		return fsUsername;
	}
	
	public void setFsUsername(String fsUsername) {
		this.fsUsername = fsUsername;
	}
	
	@Column(name="SQ_USERNO", length=20, nullable=true)
	public String getSqUserno() {
		return sqUserno;
	}
	
	public void setSqUserno(String sqUserno) {
		this.sqUserno = sqUserno;
	}
	
	@Column(name="SQ_USERNAME", length=30, nullable=true)
	public String getSqUsername() {
		return sqUsername;
	}
	
	public void setSqUsername(String sqUsername) {
		this.sqUsername = sqUsername;
	}
	
	@Column(name="REMARK", length=60, nullable=true)
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
	
}
