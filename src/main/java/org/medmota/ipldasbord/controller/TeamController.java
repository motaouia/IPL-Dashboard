package org.medmota.ipldasbord.controller;

import java.util.List;

import org.medmota.ipldasbord.model.Match;
import org.medmota.ipldasbord.model.Team;
import org.medmota.ipldasbord.repository.MatchRepository;
import org.medmota.ipldasbord.repository.TeamRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TeamController {
	 private static final Logger log = LoggerFactory.getLogger(TeamController.class);
	
	private TeamRepository teamRepository;
	private MatchRepository matchRepository;

	public TeamController(TeamRepository teamRepository, 
						  MatchRepository matchRepository) {
		this.teamRepository = teamRepository;
		this.matchRepository = matchRepository;
	}

	//EXP : http://localhost:8080/team/Royal Challengers Bangalore
	@GetMapping("/team/{teamName}")
	public Team getTeam(@PathVariable String teamName) {
		Team team = teamRepository.findTeamByName(teamName);
		List<Match> matchs = matchRepository.findLatestMatchesByTeam(teamName, 4);
		team.setListLastMatchs(matchs);
		return team;
	}

}