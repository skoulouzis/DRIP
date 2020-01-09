/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.Swagger2SpringBoot;
import nl.uva.sne.drip.commons.utils.Converter;
import nl.uva.sne.drip.configuration.MongoConfig;
import nl.uva.sne.drip.model.tosca.Credential;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {Swagger2SpringBoot.class})
public class ServiceTests {

    @Autowired
    ToscaTemplateService toscaTemplateService;

    private String toscaTemplateID;
    private String testApplicationExampleToscaContents;
    private static final String testApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_updated.yaml";
    private static final String testUpdatedApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_updated.yaml";
    private static final String testOutputApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_updated.yaml";
    private static final String testCredentialPath = ".." + File.separator + "fake_credentials" + File.separator + "test-geni.jks";

    @Autowired
    CredentialService credentialService;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;

    @BeforeClass
    public static void setUpClass() {
        try {
            _mongodExe = starter.prepare(new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net(MongoConfig.MONGO_TEST_HOST, MongoConfig.MONGO_TEST_PORT, Network.localhostIsIPv6()))
                    .build());
            _mongod = _mongodExe.start();

        } catch (IOException ex) {
            Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @AfterClass
    public static void tearDownClass() {
        _mongodExe.stop();

    }

    @Before
    public void setUp() {
        if (mockMvc == null) {
            DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
            mockMvc = builder.build();
        }

    }

    @After
    public void tearDown() {

    }

    @Test
    public void testPass() {
        Assert.assertTrue(true);
    }

    /**
     * Test of saveFile method, of class ToscaTemplateService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testToscaTemplateServiceSaveFile() throws Exception {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "saveFile");
        FileInputStream in = new FileInputStream(testApplicationExampleToscaFilePath);
        MultipartFile file = new MockMultipartFile("file", in);
        toscaTemplateID = toscaTemplateService.saveFile(file);
        Assert.assertNotNull(toscaTemplateID);
        testApplicationExampleToscaContents = toscaTemplateService.findByID(toscaTemplateID);
        Assert.assertNotNull(testApplicationExampleToscaContents);
    }

    /**
     * Test of updateToscaTemplateByID method, of class ToscaTemplateService.
     */
    @Test
    public void testToscaTemplateServiceUpdateToscaTemplateByID_String_MultipartFile() {
        FileInputStream in = null;
        try {
            Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "updateToscaTemplateByID");
            if (toscaTemplateID == null) {
                testToscaTemplateServiceSaveFile();
            }
            in = new FileInputStream(testUpdatedApplicationExampleToscaFilePath);
            MultipartFile file = new MockMultipartFile("file", in);
            String expResult = toscaTemplateID;
            String result = toscaTemplateService.updateToscaTemplateByID(toscaTemplateID, file);
            assertEquals(expResult, result);
            String updatedTemplate = toscaTemplateService.findByID(result);
            Assert.assertNotNull(updatedTemplate);
            Assert.assertNotEquals(result, testApplicationExampleToscaContents);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                fail(ex.getMessage());
                Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of updateToscaTemplateByID method, of class ToscaTemplateService.
     *
     * @throws java.io.FileNotFoundException
     */
    @Test
    public void testToscaTemplateServiceUpdateToscaTemplateByID_Exception_MultipartFile() throws FileNotFoundException, IOException {
        FileInputStream in = new FileInputStream(testUpdatedApplicationExampleToscaFilePath);
        MultipartFile file = new MockMultipartFile("file", in);
        try {
            toscaTemplateService.updateToscaTemplateByID("0", file);
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException)) {
                fail(ex.getMessage());
            }
        }
    }

    /**
     * Test of findByID method, of class ToscaTemplateService.
     */
    @Test
    public void testToscaTemplateServiceFindByID() {
        try {
            Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "findByID");
            if (toscaTemplateID == null) {
                testToscaTemplateServiceSaveFile();
            }
            String result = toscaTemplateService.findByID(toscaTemplateID);
            Assert.assertNotNull(result);
            assertEquals(testApplicationExampleToscaContents, result);
        } catch (JsonProcessingException ex) {
            fail(ex.getMessage());
            Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            fail(ex.getMessage());
            Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of deleteByID method, of class ToscaTemplateService.
     */
    @Test
    public void testToscaTemplateServiceDeleteByID() {
        try {
            Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "deleteByID");
            if (toscaTemplateID == null) {
                testToscaTemplateServiceSaveFile();
            }
            toscaTemplateService.deleteByID(toscaTemplateID);
            String id = toscaTemplateService.findByID(toscaTemplateID);
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException)) {
                fail(ex.getMessage());
                Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            try {
                testToscaTemplateServiceSaveFile();
            } catch (Exception ex) {
                fail(ex.getMessage());
                Logger.getLogger(ServiceTests.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of getAllIds method, of class ToscaTemplateService.
     * @throws java.lang.Exception
     */
    @Test
    public void testToscaTemplateServiceGetAllIds() throws Exception {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "getAllIds");
        testToscaTemplateServiceDeleteAll();
        int numOfINst = 3;
        for (int i = 1; i <= numOfINst; i++) {
            testToscaTemplateServiceSaveFile();
        }
        List<String> result = toscaTemplateService.getAllIds();
        assertEquals(numOfINst, result.size());
    }

    @Test
    public void testToscaTemplateServiceDeleteAll() {
        toscaTemplateService.deleteAll();
        int size = toscaTemplateService.getAllIds().size();
        assertEquals(0, size);
    }

    /**
     * Test of save method, of class CredentialService.
     */
    @Test
    public void testCredentialServiceSave() {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "save");
        saveCredential();
    }

    @Test
    public void testCredentialService() throws IOException, NoSuchAlgorithmException {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "testCredentialService");
        String keyStoreEncoded = Converter.encodeFileToBase64Binary(testCredentialPath);

        Credential credential = new Credential();
        credential.setCloudProviderName("ExoGENI");
        Map<String, String> keys = new HashMap<>();
        keys.put("keystore", keyStoreEncoded);
        credential.setKeys(keys);
        credential.setToken("1234");
        credential.setTokenType("password");
        credential.setUser("user");

        String keyStoreEncodedFromCredential = credential.getKeys().get("keystore");
        assertEquals(keyStoreEncoded, keyStoreEncodedFromCredential);

        String copyTestCredentialPath = ".." + File.separator + "fake_credentials" + File.separator + "copy_of_test-geni.jks";
        Converter.decodeBase64BToFile(keyStoreEncodedFromCredential, copyTestCredentialPath);

        String keystorFileChecksum = Converter.getFileMD5(testCredentialPath);
        String keystorFileCopyChecksum = Converter.getFileMD5(copyTestCredentialPath);

        assertEquals(keystorFileChecksum, keystorFileCopyChecksum);
    }

    public String saveCredential() {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "saveCredential");
        Credential document = new Credential();
        document.setCloudProviderName("exogeni");
        Map<String, String> keys = new HashMap<>();
        keys.put("keystore", "/qTlqams0Ppq2rnaOgL5am7ExGO2nMsOZYM61kiAnsvkOixUuoPy9r4d4OfhwQXXg3lZmeRITjNz4ps+hIDKuxodIQXgBtfMy9Kx8Syb9bIl/MQQls5hWyp9yHAl6vAampoxYu0170lceT1sds4OCz3tM9eF7/UoBQwXBPo94QhO1/vSbtICyVsm3Z2HeGKcBWobT3opZV2w30GqX/7OBmNeIG7RBMPuxLsUxJ9Alahi1zXOUjLkd2bmmVFREngmeubgCzPFxxCQQrZK6WratTzJKc1sRVNK5GJzTwi9BlcZSQSgprum9yVHUgQc6Ylmvdrkhn2g9SlluY2JAZyCZvHYaRBKE4o5bXBDumTy1YAPMNPTfpeeLz+YmH0GMfVwKkxtIBpjb045QseoIWcqxke60WWfJguaTqymXknmcqcLNz+UzUdfVfyurOy9X8xmTGCW5V4N");
        document.setKeys(keys);
        document.setToken("secret");
        document.setTokenType("password");

        return credentialService.save(document);
    }

    /**
     * Test of findByID method, of class CredentialService.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testCredentialServiceFindByID() throws Exception {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "findByID");
        String id = saveCredential();
        Credential result = credentialService.findByID(id);
        assertNotNull(result);
    }

    /**
     * Test of deleteByID method, of class CredentialService.
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    @Test
    public void testCredentialServiceDeleteByID() throws JsonProcessingException {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "deleteByID");
        String id = saveCredential();
        credentialService.deleteByID(id);
        try {
            Credential res = credentialService.findByID(id);
            assertNotNull(res);
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException)) {
                fail(ex.getMessage());
            }
        }
    }

    /**
     * Test of getAllIds method, of class CredentialService.
     */
    @Test
    public void testCredentialServiceGetAllIds() {
        Logger.getLogger(ServiceTests.class.getName()).log(Level.INFO, "getAllIds");
        testCredentialServiceDeleteAll();
        int numOfINst = 3;
        for (int i = 1; i <= numOfINst; i++) {
            saveCredential();
        }
        List<String> result = credentialService.getAllIds();
        assertEquals(numOfINst, result.size());
    }

    /**
     * Test of deleteAll method, of class CredentialService.
     */
    @Test
    public void testCredentialServiceDeleteAll() {
        credentialService.deleteAll();
        int size = credentialService.getAllIds().size();
        assertEquals(0, size);
    }
}
