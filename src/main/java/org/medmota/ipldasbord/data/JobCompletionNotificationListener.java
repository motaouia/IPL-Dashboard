package org.medmota.ipldasbord.data;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;

import org.medmota.ipldasbord.model.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

	private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

	//We gonna Use EntityManger instead of JdbcTemplate
	//private final JdbcTemplate jdbcTemplate;
	
	private EntityManager em;
	

	public EntityManager getEm() {
		return em;
	}

	@Autowired
	/*public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}*/
	
	public JobCompletionNotificationListener(EntityManager em) {
		this.em = em;
	}

	@Override
	@Transactional
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			log.info("!!! JOB FINISHED! Time to verify the results");

			// ==> Uncomment to check if we process the csv file correctlly
			/*jdbcTemplate.query("SELECT team1, team2, date FROM match",
					(rs, row) -> " Team 1: " + rs.getString(1) + " Team 2: " + rs.getString(2) + " Date: " + rs.getString(3))
					.forEach(myStr -> {
						System.out.println("@@##=*=##@@");
						System.out.println(myStr);
						});
			*/
			
			Map<String, Team> dataTeam = new HashMap<String, Team>();
			//Create JPQL query to fetch all Team1  And Team 2 
			 em.createQuery("select distinct m.team1, count(*) from Match m group by m.team1", Object[].class)
				.getResultList()
				.stream()
				.map(e -> new Team((String)e[0], (long)e[1]))
				.forEach(team -> dataTeam.put(team.getName(), team));
			em.createQuery("select distinct m.team2 , count(*) from Match m group by m.team2", Object[].class)
			  .getResultList()
			  .stream()
			  .forEach(e -> {
				  Team team = dataTeam.get((String)e[0]);
				  team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
			  });
			
			//Now we gonna set the value of totalwins
			//very easy we ganna just use the csv file data directly 
			em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
			.getResultList()
			.stream()
			.forEach(e -> {
			   Team team = dataTeam.get((String)e[0]);
			   //to avoid the case where matchWinner is 'NA' => review the csv File
			   if(team != null) {
			   team.setTotalWins((long)e[1]);
			   }
			  });
			
			//final step persist
			dataTeam.values().forEach(team -> {
				em.persist(team);
				});
		}
	}

}