package com.greco.imagetag.repo;

import com.greco.imagetag.model.ObjectKey;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
@JdbcTest
@ComponentScan("com.greco.imagetag")
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ObjectKeyRepositoryTest {

    @Autowired
    private ObjectKeyRepository objectKeyRepository;

    @Test
    public void addObjectKey() {

        ObjectKey ok = buildTestObjectKey();
        int returnedKey = objectKeyRepository.addObjectKey(ok);
        assertThat(returnedKey).isGreaterThan(0);
    }

    @Test
    public void findAll(){
        ObjectKey ok = buildTestObjectKey();
        objectKeyRepository.addObjectKey(ok);
        List okList = objectKeyRepository.findAll();
        assertThat(!okList.isEmpty());
    }

    @Test
    public void findObjectKey(){
        ObjectKey ok = buildTestObjectKey();
        objectKeyRepository.addObjectKey(ok);
        ObjectKey ok2 = objectKeyRepository.findObjectKey(ok.getBucket(),ok.getObjectKeyName());
        assertThat(ok2.getBucket()).isEqualToIgnoringCase(ok.getBucket());
    }

    private ObjectKey buildTestObjectKey(){
        ObjectKey ok = new ObjectKey();
        ok.setObjectKeyName("TEST OBJECT KEY");
        ok.setBucket("TEST BUCKET");
        return ok;
    }


}