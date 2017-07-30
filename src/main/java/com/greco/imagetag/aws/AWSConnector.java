package com.greco.imagetag.aws;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.AmazonRekognitionClientBuilder;
import com.amazonaws.services.rekognition.model.*;
import com.amazonaws.services.rekognition.model.S3Object;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import com.greco.imagetag.model.DetectedLabel;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AWSConnector implements InitializingBean{

    @Value("${aws.profile}")
    private String amazonProfileName;
    @Value("${aws.region}")
    private String amazonRegionName;
    private AmazonS3 s3Client;
    private AmazonRekognition rekognitionClient;


    @Override
    public void afterPropertiesSet() throws Exception {
        AmazonS3Client.builder();
        s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(amazonRegionName)
                .withCredentials(new ProfileCredentialsProvider(amazonProfileName))
                .build();
        rekognitionClient = AmazonRekognitionClientBuilder
                .standard()
                .withRegion(amazonRegionName)
                .withCredentials(new ProfileCredentialsProvider(amazonProfileName))
                .build();

    }

    public ArrayList<String> getS3ObjectKeysForBucket(String bucket) {
        final ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucket);
        ListObjectsV2Result result;
        ArrayList<String> objectKeys = new ArrayList<String>();
        do {
            result = s3Client.listObjectsV2(req);
            for (S3ObjectSummary objectSummary : result.getObjectSummaries()) {
                objectKeys.add(objectSummary.getKey());
            }
            req.setContinuationToken(result.getNextContinuationToken());
        }
        while(result.isTruncated() == true );
        return objectKeys;
    }

    public List<DetectedLabel> getRekognitionLabels(String bucket, String objectKey, int maxLabels, float minConfidence){
        DetectLabelsRequest request = new DetectLabelsRequest()
                .withImage(new Image()
                        .withS3Object(new S3Object()
                                .withName(objectKey).withBucket(bucket)))
                .withMaxLabels(maxLabels)
                .withMinConfidence(minConfidence);
        DetectLabelsResult result = rekognitionClient.detectLabels(request);
        List <Label> labels = result.getLabels();
        ArrayList detectedLabels = new ArrayList();
        for (Label label : result.getLabels()) {
            DetectedLabel dl = new DetectedLabel(label.getName(),label.getConfidence());
            detectedLabels.add(dl);
        }
        return detectedLabels;

    }

    public void tagS3Object(String bucket, String objectKey, List<DetectedLabel> detectedLabels) {
        List<Tag> s3Tags = new ArrayList<>();
        for (DetectedLabel label : detectedLabels) {
            s3Tags.add(new Tag(label.getLabelName(), String.valueOf(label.getConfidence())));
        }
        s3Client.setObjectTagging(new SetObjectTaggingRequest(bucket, objectKey, new ObjectTagging(s3Tags)));
    }

}
