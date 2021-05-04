package org.medmota.ipldasbord.repository;

import java.util.List;

import org.medmota.ipldasbord.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Long>{
	
	List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName, String team2, Pageable pageable);
	
	default List<Match> findLatestMatchesByTeam(String team, int count){
		return getByTeam1OrTeam2OrderByDateDesc(team, team, PageRequest.of(0, count));
	}

}
