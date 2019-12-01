package org.pentiumfallen.fiftheditionactionbroker.repositories;

import org.pentiumfallen.fiftheditionactionbroker.models.Characters;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CharactersRepository extends MongoRepository<Characters, String> {
	Characters findBy_id(ObjectId _id);
}