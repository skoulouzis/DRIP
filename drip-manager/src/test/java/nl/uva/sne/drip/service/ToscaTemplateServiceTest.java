/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.runtime.Network;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.Swagger2SpringBoot;
import nl.uva.sne.drip.api.ApiException;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
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
public class ToscaTemplateServiceTest {

    @Autowired
    ToscaTemplateService instance;

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    private static final MongodStarter starter = MongodStarter.getDefaultInstance();
    private static MongodExecutable _mongodExe;
    private static MongodProcess _mongod;
    private static final String testApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example.yaml";
    private static final String testUpdatedApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_updated.yaml";

    @BeforeClass
    public static void setUpClass() {
        try {
            _mongodExe = starter.prepare(new MongodConfigBuilder()
                    .version(Version.Main.PRODUCTION)
                    .net(new Net("localhost", 12345, Network.localhostIsIPv6()))
                    .build());
            _mongod = _mongodExe.start();

        } catch (IOException ex) {
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @AfterClass
    public static void tearDownClass() {
        _mongodExe.stop();

    }
    private String toscaTemplateID;
    private String testApplicationExampleToscaContents;

    @Before
    public void setUp() {
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(this.wac);
        mockMvc = builder.build();
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of saveFile method, of class ToscaTemplateService.
     */
    @Test
    public void testSaveFile() throws Exception {
        Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.INFO, "saveFile");
        FileInputStream in = new FileInputStream(testApplicationExampleToscaFilePath);
        MultipartFile file = new MockMultipartFile("file", in);
        toscaTemplateID = instance.saveFile(file);
        Assert.assertNotNull(toscaTemplateID);
        testApplicationExampleToscaContents = instance.findByID(toscaTemplateID);
        Assert.assertNotNull(testApplicationExampleToscaContents);
    }

    /**
     * Test of updateToscaTemplateByID method, of class ToscaTemplateService.
     */
    @Test
    public void testUpdateToscaTemplateByID_String_MultipartFile() {
        FileInputStream in = null;
        try {
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.INFO, "updateToscaTemplateByID");
            if (toscaTemplateID == null) {
                testSaveFile();
            }
            in = new FileInputStream(testUpdatedApplicationExampleToscaFilePath);
            MultipartFile file = new MockMultipartFile("file", in);
            String expResult = toscaTemplateID;
            String result = instance.updateToscaTemplateByID(toscaTemplateID, file);
            assertEquals(expResult, result);
            String updatedTemplate = instance.findByID(result);
            Assert.assertNotNull(updatedTemplate);
            Assert.assertNotEquals(result, testApplicationExampleToscaContents);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                fail(ex.getMessage());
                Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Test of updateToscaTemplateByID method, of class ToscaTemplateService.
     */
    @Test
    public void testUpdateToscaTemplateByID_Exception_MultipartFile() throws FileNotFoundException, IOException {
        FileInputStream in = null;

        in = new FileInputStream(testUpdatedApplicationExampleToscaFilePath);
        MultipartFile file = new MockMultipartFile("file", in);
        try {
            String result = instance.updateToscaTemplateByID("0", file);
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
    public void testFindByID() {
        try {
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.INFO, "findByID");
            if (toscaTemplateID == null) {
                testSaveFile();
            }
            String result = instance.findByID(toscaTemplateID);
            Assert.assertNotNull(result);
            assertEquals(testApplicationExampleToscaContents, result);
        } catch (JsonProcessingException ex) {
            fail(ex.getMessage());
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            fail(ex.getMessage());
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Test of deleteByID method, of class ToscaTemplateService.
     */
    @Test
    public void testDeleteByID() {
        try {
            Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.INFO, "deleteByID");
            if (toscaTemplateID == null) {
                testSaveFile();
            }
            instance.deleteByID(toscaTemplateID);
            String id = instance.findByID(toscaTemplateID);
        } catch (Exception ex) {
            if (!(ex instanceof NoSuchElementException)) {
                fail(ex.getMessage());
                Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }

        } finally {
            try {
                testSaveFile();
            } catch (Exception ex) {
                fail(ex.getMessage());
                Logger.getLogger(ToscaTemplateServiceTest.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
