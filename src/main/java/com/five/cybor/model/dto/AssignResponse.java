package com.five.cybor.model.dto;

import com.five.cybor.model.AgentEntity;
import com.five.cybor.model.AssingAcountsEntity;

public class AssignResponse {

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
	
	public AssignResponse(String daacct, int daunit, int damount, String dadays, String daagency, String dazip,
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

public AssignResponse(AssingAcountsEntity entity) {
		
	this.daacct = entity.getDaacct();
	this.daunit = entity.getDaunit();
	this.damount = entity.getDamount();
	this.dadays = entity.getDadays();
	this.daagency = entity.getDaagency();
	this.dazip = entity.getDazip();
	this.dacity = entity.getDacity();
	this.daaddr1 = entity.getDaaddr1();
	this.daaddr2 = entity.getDaaddr2();
	this.danumsuc = entity.getDanumsuc();
	this.danamesuc = entity.getDanamesuc();
	this.userid = entity.getUserid();
	this.dasegment = entity.getDasegment();
		
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
