package org.medmota.ipldasbord.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	private Long totalMatches;
	private Long totalWins;
	
	public Team() {
		super();
	}
	
	public Team(String name, Long totalMatches) {
		super();
		this.name = name;
		this.totalMatches = totalMatches;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getTotalMatches() {
		return totalMatches;
	}
	public void setTotalMatches(Long totalMatches) {
		this.totalMatches = totalMatches;
	}
	public Long getTotalWins() {
		return totalWins;
	}
	public void setTotalWins(Long totalWins) {
		this.totalWins = totalWins;
	}

	@Override
	public String toString() {
		return "Team [name=" + name + ", totalMatches=" + totalMatches + ", totalWins=" + totalWins + "]";
	}
	
}