package dev.fiko.movies.repository;

import dev.fiko.movies.entity.WatchList;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WatchlistRepository extends MongoRepository<WatchList, ObjectId> {
    WatchList findByUserId(String userId);
}