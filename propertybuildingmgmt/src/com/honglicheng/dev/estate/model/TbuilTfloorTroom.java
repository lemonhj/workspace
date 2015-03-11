package com.honglicheng.dev.estate.model;

import java.util.List;

public class TbuilTfloorTroom {
	private TBuilding tbuil;
	private List<TFloor> tFloor;
	private List<TRoom>  bzc;
	private List<TRoom>  bnc;
	private List<TRoom>  tsc1;
	private List<TRoom>  tsc2;
	
	public TbuilTfloorTroom(){}
	public TbuilTfloorTroom(TBuilding tbuil,List<TFloor> tFloor,List<TRoom>  bzc,
			List<TRoom>  bnc,List<TRoom>  tsc1,List<TRoom>  tsc2){
		this.tbuil = tbuil;
		this.tFloor = tFloor;
		this.bzc = bzc;
		this.bnc = bnc;
		this.tsc1 = tsc1;
		this.tsc2 = tsc2;
	}
	public TBuilding getTbuil() {
		return tbuil;
	}
	public void setTbuil(TBuilding tbuil) {
		this.tbuil = tbuil;
	}
	public List<TFloor> gettFloor() {
		return tFloor;
	}
	public void settFloor(List<TFloor> tFloor) {
		this.tFloor = tFloor;
	}
	public List<TRoom> getBzc() {
		return bzc;
	}
	public void setBzc(List<TRoom> bzc) {
		this.bzc = bzc;
	}
	public List<TRoom> getBnc() {
		return bnc;
	}
	public void setBnc(List<TRoom> bnc) {
		this.bnc = bnc;
	}
	public List<TRoom> getTsc1() {
		return tsc1;
	}
	public void setTsc1(List<TRoom> tsc1) {
		this.tsc1 = tsc1;
	}
	public List<TRoom> getTsc2() {
		return tsc2;
	}
	public void setTsc2(List<TRoom> tsc2) {
		this.tsc2 = tsc2;
	}
}
