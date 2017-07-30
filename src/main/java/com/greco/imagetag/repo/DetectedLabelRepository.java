package com.greco.imagetag.repo;


import com.greco.imagetag.model.DetectedLabel;
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

@Component
public class DetectedLabelRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    public int addDetectedLabel(DetectedLabel detectedLabel){
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                        PreparedStatement ps =
                                connection.prepareStatement("INSERT INTO labels(labelname) VALUES (?)", new String[] {"id"});
                        ps.setString(1, detectedLabel.getLabelName());
                        return ps;
                    }
                },
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Cacheable("label")
    public DetectedLabel findDetectedLabel(String detectedLabelName){
        return jdbcTemplate.queryForObject(
                "select id, labelname from labels where labelname = '" + detectedLabelName +"'",
                (rs, i) -> new DetectedLabel(rs.getInt("id"), rs.getString("labelname")));
    }

}
