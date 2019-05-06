package com.untech.mcrcb.web.model;

public class WjwCertNum {
	/**id*/
	private Long id;
	
	/**cert_no*/
	private String certNo;
	
	/**time*/
	private String time;
	
	/**user_no*/
	private String userNo;
	
	/**user_name*/
	private String userName;
	
	/**unit_no*/
	private String unitNo;
	
	/**unit_name*/
	private String unitName;
	
	/**conn_no*/
	private Integer connNo;
	
	/**note*/
	private String note;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCertNo() {
		return certNo;
	}

	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getConnNo() {
		return connNo;
	}

	public void setConnNo(Integer connNo) {
		this.connNo = connNo;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "WjwCertNum [id=" + id + ", certNo=" + certNo + ", time=" + time
				+ ", userNo=" + userNo + ", userName=" + userName + ", unitNo="
				+ unitNo + ", unitName=" + unitName + ", connNo=" + connNo
				+ ", note=" + note + "]";
	}
}
