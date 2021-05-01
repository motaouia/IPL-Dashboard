package org.medmota.ipldasbord.data;

import java.time.LocalDate;

import org.medmota.ipldasbord.model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match>{
	 private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

	@Override
	public Match process(final MatchInput matchInput) throws Exception {
		final Match transformedMatch = new Match();
		
		transformedMatch.setId(Long.parseLong(matchInput.getId()));
		transformedMatch.setCity(matchInput.getCity());
		transformedMatch.setDate(LocalDate.parse(matchInput.getDate()));
		transformedMatch.setPlayerOfMatch(matchInput.getPlayer_of_match());
		transformedMatch.setVenue(matchInput.getVenue());
		
		//Set team1, team2 based on Innings Order
		String firstInningsTeam, secondInningsTeam;
		
		if("bat".equals(matchInput.getToss_decision())) {
			firstInningsTeam = matchInput.getToss_winner();
			secondInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam2() : matchInput.getTeam1();
		}else {
			secondInningsTeam = matchInput.getToss_winner();
			firstInningsTeam = matchInput.getToss_winner().equals(matchInput.getTeam1()) ? matchInput.getTeam1() : matchInput.getTeam2();
		}
		
		transformedMatch.setTeam1(firstInningsTeam);
		transformedMatch.setTeam2(secondInningsTeam);
		
		transformedMatch.setTossWinner(matchInput.getToss_winner());
		transformedMatch.setTossDecision(matchInput.getToss_decision());
		transformedMatch.setResult(matchInput.getResult());
		transformedMatch.setMatchWinner(matchInput.getWinner());
		transformedMatch.setResultMargin(matchInput.getResult_margin());
		transformedMatch.setUmpire1(matchInput.getUmpire1());
		transformedMatch.setUmpire2(matchInput.getUmpire2());
		
		//log.info("Converting (" + matchInput + ") into (" + transformedMatch + ")");
		return transformedMatch;
	}
	 
}