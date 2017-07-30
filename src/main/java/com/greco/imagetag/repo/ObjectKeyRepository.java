package com.greco.imagetag.repo;


import com.greco.imagetag.model.ObjectKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Component
public class ObjectKeyRepository  {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public int addObjectKey(ObjectKey objectKey){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO images(bucket, objectkey, objectkeysha1) VALUES (?,?,?)", new String[] {"id"});
                        ps.setString(1, objectKey.getBucket());
                        ps.setString(2, objectKey.getObjectKeyName());
                        ps.setString(3, objectKey.getObjectKeySha1());
                        return ps;
                    }
                },
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Cacheable("objectkeys")
    public List<ObjectKey> findAll() {

        List<ObjectKey> result = jdbcTemplate.query(
                "SELECT id,bucket, objectkey FROM images",
                (rs, rowNum) -> new ObjectKey(rs.getInt("id"), rs.getString("bucket"), rs.getString("objectkey"))
        );

        return result;

    }

    @Cacheable("objectkey")
    public ObjectKey findObjectKey(String bucket, String objectKey){
        return jdbcTemplate.queryForObject(
                "select id, bucket, objectkey from images where bucket = '" + bucket + "' and objectkey = '" + objectKey +"'",
                (rs, i) -> new ObjectKey(rs.getInt("id"), rs.getString("bucket"), rs.getString("objectkey")));
    }


}
