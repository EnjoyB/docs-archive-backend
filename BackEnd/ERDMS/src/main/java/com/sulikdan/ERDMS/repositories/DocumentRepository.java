package com.sulikdan.ERDMS.repositories;

import com.sulikdan.ERDMS.entities.AsyncApiState;
import com.sulikdan.ERDMS.entities.Document;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

// import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Class DocumentRepository is repo using CrudRepository to get access to DB, with all default CRUD
 * operations and more methods.
 *
 * @author Daniel Šulik
 * @version 1.0
 * @since 18-Jul-20
 */
// @Repository
public interface DocumentRepository extends CrudRepository<Document, String> {
//  TODO  check if save updates -- looks like its smart AF
  // public interface DocumentRepository extends JpaRepository<Document, String> {


}
