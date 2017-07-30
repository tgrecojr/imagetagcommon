package com.greco.imagetag.bus;

import com.greco.imagetag.model.DetectedLabel;
import com.greco.imagetag.model.ObjectKey;
import com.greco.imagetag.repo.DetectedLabelRepository;
import com.greco.imagetag.repo.ObjectKeyLabelRepository;
import com.greco.imagetag.repo.ObjectKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ObjectKeyManager {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private DetectedLabelRepository detectedLabelRepository;

    @Autowired
    private ObjectKeyRepository objectKeyRepository;

    @Autowired
    private ObjectKeyLabelRepository objectKeyLabelRepository;

    @Transactional
    public void saveObjectKeyAndLabels(ObjectKey objectKey){
        int objectKeyId;
        try{
            objectKeyId = objectKeyRepository.addObjectKey(objectKey);
        }catch(DuplicateKeyException dkeObjectKey){
            logger.info("Object Key already exists.  Will only process changes in confidence");
            ObjectKey foundObjectKey = objectKeyRepository.findObjectKey(objectKey.getBucket(),objectKey.getObjectKeyName());
            objectKeyId = foundObjectKey.getId();
            logger.info("Found id " + objectKeyId + " for bucket " + objectKey.getBucket() + " and key " + objectKey.getObjectKeyName());
        }
        int finalObjectKeyId = objectKeyId;
        objectKey.getDetectedLabels().forEach(label->{
            int detectedLabelId;
            try{
                detectedLabelId = detectedLabelRepository.addDetectedLabel(label);
            }catch(DuplicateKeyException dkeLabel){
                logger.info("Label " + label.getLabelName() + " already exists.  Retrieving primary key");
                DetectedLabel dl = detectedLabelRepository.findDetectedLabel(label.getLabelName());
                logger.info("Retrieved primary key of " + dl.getId() + " for label " + dl.getLabelName());
                detectedLabelId = dl.getId();
            }

            try{
                objectKeyLabelRepository.addLabelAndConfidenceForObjectKey(finalObjectKeyId,detectedLabelId,label.getConfidence());
            }catch(DuplicateKeyException dkeLabelAndObject){
                logger.info("Image and Label combination exists.  Updating Confidence Only");
                objectKeyLabelRepository.updateConfidence(finalObjectKeyId,detectedLabelId,label.getConfidence());
            }
        });

    }
}
