package com.sulikdan.ERDMS.repositories.mongo;

import com.sulikdan.ERDMS.entities.Doc;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;
import java.util.function.Predicate;

/**
 * Created by Daniel Šulik on 12-Aug-20
 *
 * <p>Class DocMongoRepository is to contain @MongoReposiory and QuerydslPredicateExecutor
 */
public interface DocMongoRepository
    extends MongoRepository<Doc, String>, QuerydslPredicateExecutor<Doc> {

//    List<Doc> findAll(List<Predicate> predicates);
//    List<Doc> findByRege
}