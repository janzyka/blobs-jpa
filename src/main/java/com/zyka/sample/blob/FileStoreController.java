package com.zyka.sample.blob;

import com.zyka.sample.blob.helper.LobHelper;
import com.zyka.sample.blob.model.StreamingFileRecord;
import com.zyka.sample.blob.repository.StreamingFileRepository;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class FileStoreController {

    private final StreamingFileRepository streamingFileRepository;
    private final LobHelper lobCreator;

    @Autowired
    public FileStoreController(StreamingFileRepository streamingFileRepository, LobHelper lobCreator) {
        this.streamingFileRepository = streamingFileRepository;
        this.lobCreator = lobCreator;
    }

    @Transactional
    @RequestMapping(value = "/blobs", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> store(@RequestPart("file") MultipartFile multipartFile) throws IOException, SQLException, URISyntaxException {
        StreamingFileRecord streamingFileRecord = new StreamingFileRecord(multipartFile.getOriginalFilename(), lobCreator.createBlob(multipartFile.getInputStream(), multipartFile.getSize()));

        streamingFileRecord = streamingFileRepository.save(streamingFileRecord);

        return ResponseEntity.created(new URI("http://localhost:8080/blobs/" + streamingFileRecord.getId())).build();
    }

    @Transactional
    @RequestMapping(value = "/blobs/{id}", method = RequestMethod.GET)
    public void load(@PathVariable("id") long id, HttpServletResponse response) throws SQLException, IOException {
        StreamingFileRecord record = streamingFileRepository.findOne(id);

        response.addHeader("Content-Disposition", "attachment; filename=" + record.getName());
        IOUtils.copy(record.getData().getBinaryStream(), response.getOutputStream());
    }
}
