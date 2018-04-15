package no.bouvet.sandvika.activityboard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

import no.bouvet.sandvika.activityboard.domain.Athlete;

@RepositoryRestResource(collectionResourceRel = "athlete", path = "athlete")
public interface AthleteRepository extends MongoRepository<Athlete, Integer>
{
    Athlete findById(int id);

    void deleteById(int id);

    Athlete findOneByLastNameAndFirstName(String lastName, String firstName);

    Athlete findOneByToken(String token);

    List<Athlete> findAllByBadgesIsNotNull();

    List<Athlete> findAllByTokenIsNotNull();
}
