package no.bouvet.sandvika.activityboard.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import no.bouvet.sandvika.activityboard.domain.Badge;

@RepositoryRestResource(collectionResourceRel = "badge", path = "badge")
public interface BadgeRepository extends MongoRepository<Badge, String>
{
    Badge findByName(String name);

    void deleteByName(String name);

}