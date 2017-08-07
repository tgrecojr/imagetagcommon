package com.greco.imagetag.repo;

import com.greco.imagetag.model.DetectedLabel;
import com.greco.imagetag.model.ObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

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

    public List<ObjectKey> findObjectKeysForLabel(String bucket, String label){

        return jdbcTemplate.query(
                "select images.id as id,images.bucket as bucket ,images.objectkey as objectkey from images, imagelabels, labels where labels.id = imagelabels.labelid and images.id = imagelabels.imageid and images.bucket = '" + bucket + "' and labels.labelname = '"+ label +"' order by imagelabels.confidence",
                (rs, i) -> new ObjectKey(rs.getInt("id"), rs.getString("bucket"), rs.getString("objectkey")));


    }
}
