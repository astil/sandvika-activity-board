package no.bouvet.sandvika.activityboard.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import no.bouvet.sandvika.activityboard.domain.Badge;

@RepositoryRestResource(collectionResourceRel = "badge", path = "badge")
public interface BadgeRepository extends MongoRepository<Badge, String>
{
    Badge findByName(String name);

    Set<Badge> findBadgeByActivityTypeIn(List<String> activityTypes);

    void deleteByName(String name);

}