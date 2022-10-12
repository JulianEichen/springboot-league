package com.myprojects.SBleague.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "results")
public class Result {
	
	@Id
	@Column(name = "result_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="value")
	private int value = -1;
	
	@Column(name="verified_home")
	private boolean verifiedHome;
	
	@Column(name="verified_away")
	private boolean verifiedAway;

	@OneToOne(mappedBy="result")
	private Match match;
	
	public Result() {}
	
	public Result(Long id, int value, boolean verifiedHome, boolean verifiedAway) {
		super();
		this.id = id;
		this.value = value;
		this.verifiedHome = verifiedHome;
		this.verifiedAway = verifiedAway;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public boolean isVerifiedHome() {
		return verifiedHome;
	}
	public void setVerifiedHome(boolean verifiedHome) {
		this.verifiedHome = verifiedHome;
	}
	public boolean isVerifiedAway() {
		return verifiedAway;
	}
	public void setVerifiedAway(boolean verifiedAway) {
		this.verifiedAway = verifiedAway;
	}

	public String toString() {
		if(value == 2){ return "Home Win";
		}else if(value==1) { return "Draw";
		}else if(value==0) { return "Home Loss";
		}else { return "TBA";
		}
	}
	
}
