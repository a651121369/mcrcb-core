package com.untech.mcrcb.web.model;


import javax.persistence.*;
import java.io.Serializable;


/**
 * WJW_INCOMEDETAIL
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_ACCOUNT_DAYEND")
public class WjwAccountDayEnd implements Serializable{
	
	/**ID*/
	private int id;

	private String payAccTrue;
	private String payAccnameTrue;
	private String payAccmoneyTrue;
	private String getAccTrue;
	private String getAccnameTrue;
	private String getAccmoneyTrue;
	private String payAccFal;
	private String payAccnameFal;
	private String payAccmoneyFal;
	private String getAccFal;
	private String getAccnameFal;
	private String getAccmoneyFal;
	private String statusDayend;
	private String dateDayend;
	private String note1Dayend;
	private String note2Dayend;
	private String note3Dayend;

//	private String PAY_ACC_TRUE;
//	private String PAY_ACCNAME_TRUE;
//	private String PAY_ACCMONEY_TRUE;
//	private String GET_ACC_TRUE;
//	private String GET_ACCNAME_TRUE;
//	private String GET_ACCMONEY_TRUE;
//	private String PAY_ACC_FAL;
//	private String PAY_ACCNAME_FAL;
//	private String PAY_ACCMONEY_FAL;
//	private String GET_ACC_FAL;
//	private String GET_ACCNAME_FAL;
//	private String GET_ACCMONEY_FAL;
//	private String STATUS_DAYEND;
//	private String DATE_DAYEND;
//	private String NOTE1_DAYEND;
//	private String NOTE2_DAYEND;
//	private String NOTE3_DAYEND;


	public void setId(int id) {
		this.id = id;
	}
	@Id
	@Column(name="ID", length=10, nullable=false)
	public int getId() {
		return id;
	}

	@Column(name="PAY_ACC_TRUE", length=32, nullable=true)
	public String getPayAccTrue() {
		return payAccTrue;
	}

	public void setPayAccTrue(String payAccTrue) {
		this.payAccTrue = payAccTrue;
	}
	@Column(name="PAY_ACCNAME_TRUE", length=200, nullable=true)
	public String getPayAccnameTrue() {
		return payAccnameTrue;
	}

	public void setPayAccnameTrue(String payAccnameTrue) {
		this.payAccnameTrue = payAccnameTrue;
	}
	@Column(name="PAY_ACCMONEY_TRUE", length=21, nullable=true)
	public String getPayAccmoneyTrue() {
		return payAccmoneyTrue;
	}

	public void setPayAccmoneyTrue(String payAccmoneyTrue) {
		this.payAccmoneyTrue = payAccmoneyTrue;
	}
	@Column(name="GET_ACC_TRUE", length=32, nullable=true)
	public String getGetAccTrue() {
		return getAccTrue;
	}

	public void setGetAccTrue(String getAccTrue) {
		this.getAccTrue = getAccTrue;
	}
	@Column(name="GET_ACCNAME_TRUE", length=200, nullable=true)
	public String getGetAccnameTrue() {
		return getAccnameTrue;
	}

	public void setGetAccnameTrue(String getAccnameTrue) {
		this.getAccnameTrue = getAccnameTrue;
	}
	@Column(name="GET_ACCMONEY_TRUE", length=21, nullable=true)
	public String getGetAccmoneyTrue() {
		return getAccmoneyTrue;
	}

	public void setGetAccmoneyTrue(String getAccmoneyTrue) {
		this.getAccmoneyTrue = getAccmoneyTrue;
	}
	@Column(name="PAY_ACC_FAL", length=32, nullable=true)
	public String getPayAccFal() {
		return payAccFal;
	}

	public void setPayAccFal(String payAccFal) {
		this.payAccFal = payAccFal;
	}
	@Column(name="PAY_ACCNAME_FAL", length=200, nullable=true)
	public String getPayAccnameFal() {
		return payAccnameFal;
	}

	public void setPayAccnameFal(String payAccnameFal) {
		this.payAccnameFal = payAccnameFal;
	}
	@Column(name="PAY_ACCMONEY_FAL", length=21, nullable=true)
	public String getPayAccmoneyFal() {
		return payAccmoneyFal;
	}

	public void setPayAccmoneyFal(String payAccmoneyFal) {
		this.payAccmoneyFal = payAccmoneyFal;
	}
	@Column(name="GET_ACC_FAL", length=32, nullable=true)
	public String getGetAccFal() {
		return getAccFal;
	}

	public void setGetAccFal(String getAccFal) {
		this.getAccFal = getAccFal;
	}
	@Column(name="GET_ACCNAME_FAL", length=200, nullable=true)
	public String getGetAccnameFal() {
		return getAccnameFal;
	}

	public void setGetAccnameFal(String getAccnameFal) {
		this.getAccnameFal = getAccnameFal;
	}
	@Column(name="GET_ACCMONEY_FAL", length=21, nullable=true)
	public String getGetAccmoneyFal() {
		return getAccmoneyFal;
	}

	public void setGetAccmoneyFal(String getAccmoneyFal) {
		this.getAccmoneyFal = getAccmoneyFal;
	}
	@Column(name="STATUS_DAYEND", length=60, nullable=true)
	public String getStatusDayend() {
		return statusDayend;
	}

	public void setStatusDayend(String statusDayend) {
		this.statusDayend = statusDayend;
	}
	@Column(name="DATE_DAYEND", length=60, nullable=true)
	public String getDateDayend() {
		return dateDayend;
	}

	public void setDateDayend(String dateDayend) {
		this.dateDayend = dateDayend;
	}
	@Column(name="NOTE1_DAYEND", length=60, nullable=true)
	public String getNote1Dayend() {
		return note1Dayend;
	}

	public void setNote1Dayend(String note1Dayend) {
		this.note1Dayend = note1Dayend;
	}
	@Column(name="NOTE2_DAYEND", length=60, nullable=true)
	public String getNote2Dayend() {
		return note2Dayend;
	}

	public void setNote2Dayend(String note2Dayend) {
		this.note2Dayend = note2Dayend;
	}
	@Column(name="NOTE3_DAYEND", length=60, nullable=true)
	public String getNote3Dayend() {
		return note3Dayend;
	}
	public void setNote3Dayend(String note3Dayend) {
		this.note3Dayend = note3Dayend;
	}
}
