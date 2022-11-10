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

	@Column(name = "value")
	private int value = -1;

	@Column(name = "home_points_H")
	private int homePointsH = -1;

	@Column(name = "home_points_A")
	private int homePointsA = -1;

	@Column(name = "away_points_H")
	private int awayPointsH = -1;

	@Column(name = "away_points_A")
	private int awayPointsA = -1;

	@OneToOne(mappedBy = "result")
	private Match match;

	public Result() {
	}

	public Result(Long id, int value, int homePointsH, int homePointsA, int awayPointsH, int awayPointsA) {
		super();
		this.id = id;
		this.value = value;
		this.homePointsH = homePointsH;
		this.homePointsA = homePointsA;
		this.awayPointsH = awayPointsH;
		this.awayPointsA = awayPointsA;
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

	public int getHomePointsH() {
		return homePointsH;
	}

	public void setHomePointsH(int homePointsH) {
		this.homePointsH = homePointsH;
	}

	public int getHomePointsA() {
		return homePointsA;
	}

	public void setHomePointsA(int homePointsA) {
		this.homePointsA = homePointsA;
	}

	public int getAwayPointsH() {
		return awayPointsH;
	}

	public void setAwayPointsH(int awayPointsH) {
		this.awayPointsH = awayPointsH;
	}

	public int getAwayPointsA() {
		return awayPointsA;
	}

	public void setAwayPointsA(int awayPointsA) {
		this.awayPointsA = awayPointsA;
	}

	public String toString() {
		if (value == 2) {
			return "Home Win";
		} else if (value == 1) {
			return "Draw";
		} else if (value == 0) {
			return "Home Loss";
		} else {
			return "TBA";
		}
	}

	public boolean isValid() {
		if (this.awayPointsA >= 0 && this.awayPointsH >= 0 && this.homePointsA >= 0 && this.homePointsH >= 0) {
			if (this.awayPointsA == this.awayPointsH && this.homePointsA == this.homePointsH) {
				return true;
			}
		}
		return false;
	}

	public void reset() {
		this.awayPointsA = -1;
		this.awayPointsH = -1;
		this.homePointsA = -1;
		this.homePointsH = -1;
		this.value = -1;
	}

	public void updateValue() {
		if (this.isValid()) {
			if (this.homePointsA > this.awayPointsA) {
				this.value = 2;
			} else if (this.homePointsA < this.awayPointsA) {
				this.value = 1;
			} else if (this.homePointsA == this.awayPointsA) {
				this.value = 0;
			}
		}
	}
	
	public boolean hasInputConflict() {
		if (this.awayPointsA >= 0 && this.awayPointsH >= 0 && this.homePointsA >= 0 && this.homePointsH >= 0) {
			if (this.awayPointsA != this.awayPointsH || this.homePointsA != this.homePointsH) {
				return true;
			}
		}
		return false;
	}
}
