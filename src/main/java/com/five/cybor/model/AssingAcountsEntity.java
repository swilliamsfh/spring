package com.five.cybor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="dataasig")
public class AssingAcountsEntity {

	@Id
	private String daacct;
	private int daunit;
	private int damount;
	private String dadays;
	private String daagency;
	private String dazip;
	private String dacity;
	private String daaddr1;
	private String daaddr2;
	private String danumsuc;
	private String danamesuc;
	private String userid;
	private String dasegment;
	
	public AssingAcountsEntity(String daacct, int daunit, int damount, String dadays, String daagency, String dazip,
			String dacity, String daaddr1, String daaddr2, String danumsuc, String danamesuc, String userid,
			String dasegment) {
		super();
		this.daacct = daacct;
		this.daunit = daunit;
		this.damount = damount;
		this.dadays = dadays;
		this.daagency = daagency;
		this.dazip = dazip;
		this.dacity = dacity;
		this.daaddr1 = daaddr1;
		this.daaddr2 = daaddr2;
		this.danumsuc = danumsuc;
		this.danamesuc = danamesuc;
		this.userid = userid;
		this.dasegment = dasegment;
	}

	public AssingAcountsEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getDaacct() {
		return daacct;
	}

	public void setDaacct(String daacct) {
		this.daacct = daacct;
	}

	public int getDaunit() {
		return daunit;
	}

	public void setDaunit(int daunit) {
		this.daunit = daunit;
	}

	public int getDamount() {
		return damount;
	}

	public void setDamount(int damount) {
		this.damount = damount;
	}

	public String getDadays() {
		return dadays;
	}

	public void setDadays(String dadays) {
		this.dadays = dadays;
	}

	public String getDaagency() {
		return daagency;
	}

	public void setDaagency(String daagency) {
		this.daagency = daagency;
	}

	public String getDazip() {
		return dazip;
	}

	public void setDazip(String dazip) {
		this.dazip = dazip;
	}

	public String getDacity() {
		return dacity;
	}

	public void setDacity(String dacity) {
		this.dacity = dacity;
	}

	public String getDaaddr1() {
		return daaddr1;
	}

	public void setDaaddr1(String daaddr1) {
		this.daaddr1 = daaddr1;
	}

	public String getDaaddr2() {
		return daaddr2;
	}

	public void setDaaddr2(String daaddr2) {
		this.daaddr2 = daaddr2;
	}

	public String getDanumsuc() {
		return danumsuc;
	}

	public void setDanumsuc(String danumsuc) {
		this.danumsuc = danumsuc;
	}

	public String getDanamesuc() {
		return danamesuc;
	}

	public void setDanamesuc(String danamesuc) {
		this.danamesuc = danamesuc;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDasegment() {
		return dasegment;
	}

	public void setDasegment(String dasegment) {
		this.dasegment = dasegment;
	}
	
	
	
	
}
