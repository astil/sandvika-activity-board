package no.bouvet.sandvika.activityboard.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javastrava.api.v3.model.StravaActivity;
import no.bouvet.sandvika.activityboard.domain.Activity;

@RepositoryRestResource(collectionResourceRel = "activity", path = "activity")
public interface ActivityRepository extends MongoRepository<Activity, Integer>
{
    public List<Activity> findByAthleteLastName(String lastname);
    public List<Activity> findByStartDateLocalAfter(Date startDate);
    public List<Activity> findById(int id);
}
