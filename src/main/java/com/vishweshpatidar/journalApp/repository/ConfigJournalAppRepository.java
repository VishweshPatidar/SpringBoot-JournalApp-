package com.vishweshpatidar.journalApp.repository;

import com.vishweshpatidar.journalApp.entity.ConfigJournalAppEntity;
import com.vishweshpatidar.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigJournalAppRepository extends MongoRepository<ConfigJournalAppEntity, ObjectId> {

}
