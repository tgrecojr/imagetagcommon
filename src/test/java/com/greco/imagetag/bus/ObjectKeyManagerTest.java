package com.greco.imagetag.bus;

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

import java.util.ArrayList;

@RunWith(SpringRunner.class)
@SpringBootTest
@JdbcTest
@ComponentScan("com.greco.imagetag")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ObjectKeyManagerTest {

    @Autowired
    ObjectKeyManager objectKeyManager;

    @Test
    public void saveObjectKeyAndLabels(){
        ObjectKey ok =new ObjectKey("bucket","keyname");
        ArrayList labels = new ArrayList();
        DetectedLabel dl1 = new DetectedLabel("labelname",75f);
        DetectedLabel dl2 = new DetectedLabel("labelname2",75f);
        labels.add(dl1);
        labels.add(dl2);
        ok.setDetectedLabels(labels);
        objectKeyManager.saveObjectKeyAndLabels(ok);

    }

    @Test
    public void saveObjectKeyWithDuplicateLabels(){
        ObjectKey ok =new ObjectKey("bucket","keyname");
        ArrayList labels = new ArrayList();
        DetectedLabel dl1 = new DetectedLabel("labelname",75f);
        DetectedLabel dl2 = new DetectedLabel("labelname",75f);
        labels.add(dl1);
        labels.add(dl2);
        ok.setDetectedLabels(labels);
        objectKeyManager.saveObjectKeyAndLabels(ok);

    }


}