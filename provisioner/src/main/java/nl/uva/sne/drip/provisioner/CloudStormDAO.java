/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.provisioner;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nl.uva.sne.drip.model.cloud.storm.CloudDB;
import nl.uva.sne.drip.model.cloud.storm.CloudsStormVM;
import nl.uva.sne.drip.model.cloud.storm.DB;
import nl.uva.sne.drip.model.cloud.storm.DBInfo;

/**
 *
 * @author S. Koulouzis
 */
class CloudStormDAO {

    private final String cloudStormDBPath;
    private final ObjectMapper objectMapper;

    public CloudStormDAO(String cloudStormDBPath) {
        this.cloudStormDBPath = cloudStormDBPath;
        this.objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }

    List<CloudsStormVM> findVmMetaInfoByProvider(CloudDB.CloudProviderEnum provider) throws IOException {

        DB db = objectMapper.readValue(new File(cloudStormDBPath + File.separator + "db.yml"), DB.class);
        List<CloudDB> cloudDBs = db.getCloudDBs();

        CloudDB targetCloudDB = null;
        for (CloudDB cloudDB : cloudDBs) {
            if (cloudDB.getCloudProvider().equals(provider)) {
                targetCloudDB = cloudDB;
                break;
            }
        }
        if (targetCloudDB != null) {

            List<CloudsStormVM> vMMetaInfos = new ArrayList<>();
            DBInfo dbInfo = objectMapper.readValue(new File(cloudStormDBPath + File.separator + targetCloudDB.getDbInfoFile()), nl.uva.sne.drip.model.cloud.storm.DBInfo.class);
            List< nl.uva.sne.drip.model.cloud.storm.CloudStormDCMetaInfo> metaInfos = dbInfo.getDcMetaInfo();
            for ( nl.uva.sne.drip.model.cloud.storm.CloudStormDCMetaInfo metaInfo : metaInfos) {
                vMMetaInfos.addAll(metaInfo.getVmMetaInfo());
            }
            return vMMetaInfos;

        }
        return null;

    }

}
