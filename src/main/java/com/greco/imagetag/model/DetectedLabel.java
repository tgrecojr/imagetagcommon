package com.greco.imagetag.model;




public class DetectedLabel {

    public DetectedLabel(){

    }

    public DetectedLabel(int id, String labelName,float confidence){
        setId(id);
        setConfidence(confidence);
        setLabelName(labelName);
    }

    public DetectedLabel(String labelName,float confidence){
        setConfidence(confidence);
        setLabelName(labelName);
    }

    public DetectedLabel(int id, String labelName){
        setId(id);
        setLabelName(labelName);
    }



    private int id;
    private String labelName;
    private float confidence;

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }



}