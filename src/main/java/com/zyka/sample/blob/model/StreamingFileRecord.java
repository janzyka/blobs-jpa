package com.zyka.sample.blob.model;

import java.sql.Blob;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class StreamingFileRecord {

    @Id
    @GeneratedValue
    private long id;

    @NotNull
    private String name;

    @Lob
    @NotNull
    private Blob data;

    public StreamingFileRecord(String name, Blob data) {
        this.name = name;
        this.data = data;
    }
}
