package com.greco.imagetag.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ObjectKeyTest {

    private static final String SHA1_RESULT = "b415033a297ec455ec2bd96eff8529a9e4b19bcf";
    private static final String OBJECT_KEY_NAME = "TESTOBJECTKEY";

    @Test
    public void calculateSHA1() {

        ObjectKey ok = new ObjectKey();
        ok.setObjectKeyName(OBJECT_KEY_NAME);
        assertThat(SHA1_RESULT).isEqualTo(ok.getObjectKeySha1());
    }

}