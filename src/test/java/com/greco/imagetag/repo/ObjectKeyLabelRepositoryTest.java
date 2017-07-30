package com.greco.imagetag.repo;

import com.greco.imagetag.model.DetectedLabel;
import com.greco.imagetag.model.ObjectKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@JdbcTest
@ComponentScan("com.greco.imagetag")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ObjectKeyLabelRepositoryTest {

    @Autowired
    private DetectedLabelRepository detectedLabelRepository;

    @Autowired
    private ObjectKeyRepository objectKeyRepository;

    @Autowired
    private ObjectKeyLabelRepository objectKeyLabelRepository;

    @Test
    public void addLabelAndConfidenceForObjectKey(){
        ObjectKey ok = new ObjectKey();
        ok.setObjectKeyName("TEST OBJECT KEY");
        ok.setBucket("TEST BUCKET");
        DetectedLabel dl = new DetectedLabel();
        dl.setLabelName("TEST LABEL");
        int labelId = detectedLabelRepository.addDetectedLabel(dl);
        int objectId = objectKeyRepository.addObjectKey(ok);
        float f = 88f;
        int returnedValue = objectKeyLabelRepository.addLabelAndConfidenceForObjectKey(objectId,labelId,f);
        assertThat(returnedValue).isEqualTo(1);
    }

    @Test
    public void updateConfidence(){
        ObjectKey ok = new ObjectKey();
        ok.setObjectKeyName("TEST OBJECT KEY");
        ok.setBucket("TEST BUCKET");
        DetectedLabel dl = new DetectedLabel();
        dl.setLabelName("TEST LABEL");
        int labelId = detectedLabelRepository.addDetectedLabel(dl);
        int objectId = objectKeyRepository.addObjectKey(ok);
        float f = 88f;
        int returnedValue = objectKeyLabelRepository.addLabelAndConfidenceForObjectKey(objectId,labelId,f);
        int updatedReturndValue = objectKeyLabelRepository.updateConfidence(objectId,labelId,99f);
        assertThat(updatedReturndValue).isEqualTo(1);
    }
}