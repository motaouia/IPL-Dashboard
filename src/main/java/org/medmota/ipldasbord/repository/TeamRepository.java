package org.medmota.ipldasbord.repository;

import org.medmota.ipldasbord.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

	Team findTeamByName(String name);
}
