package com.company.NervManagementConsoleREST.model;

import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SIMULATION_PARTICIPANTS")
public class SimulationParticipant {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "simulationParticipantId")
	private Integer simulationParticipantId;
	
	@ManyToOne
	@JoinColumn(name = "simulationId")
	private Simulation simulation;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "memberId")
	private Member member;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
	
	
	
	public SimulationParticipant() {
		super();
	}
	
	

	public SimulationParticipant(Simulation simulation, User user, Member member, LocalDateTime startTime,
			LocalDateTime endTime) {
		super();
		this.simulation = simulation;
		this.user = user;
		this.member = member;
		this.startTime = startTime;
		this.endTime = endTime;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}
////////////////////////////////
	public Integer getSimulationParticipantId() {
		return simulationParticipantId;
	}


	public void setSimulationParticipantId(Integer simulationParticipantId) {
		this.simulationParticipantId = simulationParticipantId;
	}


	public Simulation getSimulation() {
		return simulation;
	}


	public void setSimulation(Simulation simulation) {
		this.simulation = simulation;
	}
	
	public LocalDateTime getStartTime() {
		return startTime;
	}


	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	
	public LocalDateTime getEndTime() {
		return endTime;
	}


	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	


	@Override
	public int hashCode() {
		return Objects.hash(endTime, member, simulation, simulationParticipantId, startTime, user);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimulationParticipant other = (SimulationParticipant) obj;
		return Objects.equals(endTime, other.endTime) && Objects.equals(member, other.member)
				&& Objects.equals(simulation, other.simulation)
				&& Objects.equals(simulationParticipantId, other.simulationParticipantId)
				&& Objects.equals(startTime, other.startTime) && Objects.equals(user, other.user);
	}


	@Override
	public String toString() {
		return "SimulationParticipant [simulationParticipantId=" + simulationParticipantId + ", startTime=" + startTime
				+ ", endTime=" + endTime + "]";
	}






	
	
	
}
