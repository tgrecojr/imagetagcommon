package com.greco.imagetag.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ObjectKeyLabelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int addLabelAndConfidenceForObjectKey(int objectKeyId, int labelId, float confidence){
        return jdbcTemplate.update("INSERT INTO imagelabels(imageid,labelid,confidence) VALUES (?,?,?)",
                objectKeyId,labelId,confidence);
    }

    public int updateConfidence(int objectKeyId, int labelId, float confidence){
        return jdbcTemplate.update("update imagelabels set confidence = ? where imageid = ? and labelid =?",
                confidence,objectKeyId,labelId);
    }
}
