package com.pandabear.recom.domain.document.repository;

import com.pandabear.recom.domain.document.entity.Document;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends CrudRepository<Document, String> {
}
