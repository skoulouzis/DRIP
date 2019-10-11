package nl.uva.sne.drip.api;

import org.springframework.core.io.Resource;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import nl.uva.sne.drip.dao.ToscaTemplateDAO;
import nl.uva.sne.drip.model.ToscaTemplate;
import nl.uva.sne.drip.service.ToscaTemplateService;
import org.springframework.beans.factory.annotation.Autowired;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-10-10T17:15:46.465Z")

@Controller
public class ToscaTemplateApiController implements ToscaTemplateApi {

    private static final Logger log = LoggerFactory.getLogger(ToscaTemplateApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private ToscaTemplateService toscaTemplateService;

    @org.springframework.beans.factory.annotation.Autowired
    public ToscaTemplateApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<String> deleteToscaTemplateByID(@ApiParam(value = "ID of topolog template to return", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                toscaTemplateService.deleteByID(id);
                return new ResponseEntity<>(objectMapper.readValue("\"\"", String.class), HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> getToscaTemplateByID(@ApiParam(value = "ID of topolog template to return", required = true) @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("text/plain")) {
            try {
                String ymlStr = toscaTemplateService.findByID(id);
                return new ResponseEntity<>(ymlStr, HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type ", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateToscaTemplateByID(@ApiParam(
            value = "ID of topolog template to return", required = true)
            @PathVariable("id") String id, @ApiParam(value = "file detail")
            @Valid @RequestPart("file") MultipartFile file) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("text/plain")) {
            try {
                id = toscaTemplateService.updateToscaTemplateByID(id, file);
                return new ResponseEntity<>(id, HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type ", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ApiException ex) {
                return ApiException.handleExceptions(ex);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> uploadToscaTemplate(@ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile file) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("*/*")) {
            try {
                String id = toscaTemplateService.saveFile(file);
                return new ResponseEntity<>(id, HttpStatus.OK);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (ApiException ex) {
                return ApiException.handleExceptions(ex);
            }
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<List<String>> getToscaTemplateIDs() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            List<String> ids = toscaTemplateService.getAllIds();
            return new ResponseEntity<>(ids, HttpStatus.NOT_IMPLEMENTED);
        }

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
