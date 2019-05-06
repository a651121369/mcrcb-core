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
 * WJW_CHECKER
 * @author chenyong
 * @since  2015-11-04
 */
@Entity
@Table(name="WJW_CHECKER")
public class WjwChecker implements Serializable{
	
	/**ID*/
	private Long id;
	
	/**USER_ID*/
	private String userId;
	
	/**UNIT_ID*/
	private String unitId;
	
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
	
	@Column(name="USER_ID", length=20, nullable=true)
	public String getUserId() {
		return userId;
	}
	
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name="UNIT_ID", length=20, nullable=true)
	public String getUnitId() {
		return unitId;
	}
	
	public void setUnitId(String unitId) {
		this.unitId = unitId;
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
