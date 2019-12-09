/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.provisioner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import nl.uva.sne.drip.commons.sure_tosca.client.ApiException;
import nl.uva.sne.drip.commons.utils.ToscaHelper;
import nl.uva.sne.drip.model.ToscaTemplate;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author alogo
 */
public class CloudStormServiceTest {

    private static ObjectMapper objectMapper;
    private static final String testUpdatedApplicationExampleToscaFilePath = ".." + File.separator + "TOSCA" + File.separator + "application_example_2_topologies.yaml";
    private static CloudStormService instance;
    private static ToscaTemplate toscaTemplate;

    public CloudStormServiceTest() {
    }

    @BeforeClass
    public static void setUpClass() throws FileNotFoundException, IOException, JsonProcessingException, ApiException {
        Properties prop = new Properties();
        String resourceName = "src/test/resources/application.properies";
        prop.load(new FileInputStream(resourceName));
        byte[] bytes = Files.readAllBytes(Paths.get(testUpdatedApplicationExampleToscaFilePath));
        String ymlStr = new String(bytes, "UTF-8");

        objectMapper = new ObjectMapper(new YAMLFactory().disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER));
        toscaTemplate = objectMapper.readValue(ymlStr, ToscaTemplate.class);

        instance = new CloudStormService(prop.getProperty("sure-tosca.base.path"), toscaTemplate);
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of execute method, of class CloudStormService.
     */
    @Test
    public void testExecute() throws Exception {
        System.out.println("execute");

        ToscaTemplate expResult = null;
        ToscaTemplate result = instance.execute();
    }

}
