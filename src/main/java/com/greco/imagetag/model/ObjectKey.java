package com.greco.imagetag.model;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;

public class ObjectKey  {



    private int id;
    private String bucket;
    private String objectKeyName;
    private String objectKeySha1;
    private List<DetectedLabel> detectedLabels;

    public List<DetectedLabel> getDetectedLabels() {
        return detectedLabels;
    }

    public void setDetectedLabels(List<DetectedLabel> detectedLabels) {
        this.detectedLabels = detectedLabels;
    }



    public ObjectKey(String bucket,String objectKeyName){
        setObjectKeyName(objectKeyName);
        setBucket(bucket);
    }

    public ObjectKey(int id, String bucket,String objectKeyName){
        setId(id);
        setObjectKeyName(objectKeyName);
        setBucket(bucket);
    }

    public ObjectKey(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getObjectKeyName() {
        return objectKeyName;
    }

    public void setObjectKeyName(String objectKeyName) {
        objectKeySha1 = DigestUtils.sha1Hex(objectKeyName);
        this.objectKeyName = objectKeyName;
    }

    public String getObjectKeySha1() {
        return objectKeySha1;
    }








}