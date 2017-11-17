package com.zyka.sample.blob.repository;

import com.zyka.sample.blob.model.StreamingFileRecord;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreamingFileRepository extends CrudRepository<StreamingFileRecord, Long> {
}
