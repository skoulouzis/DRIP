package nl.uva.sne.drip.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import nl.uva.sne.drip.service.TopologTemplateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

@javax.annotation.Generated(value = "nl.uva.sne.drip.codegen.languages.SpringCodegen", date = "2019-10-09T14:00:37.436Z")

@Controller
public class TopologTemplateApiController implements TopologTemplateApi {

    private static final Logger log = LoggerFactory.getLogger(TopologTemplateApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Autowired
    private TopologTemplateService topologTemplateService;

    @org.springframework.beans.factory.annotation.Autowired
    public TopologTemplateApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> addTopologTemplate(@ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile file) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> getTopologTemplateById(@ApiParam(value = "ID of topolog template to return", required = true) @PathVariable("TopologTemplateId") Long topologTemplateId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> topologTemplate(@ApiParam(value = "ID of topolog template to return", required = true) @PathVariable("TopologTemplateId") Long topologTemplateId, @ApiParam(value = "") @RequestHeader(value = "api_key", required = false) String apiKey) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateTopologTemplate(@ApiParam(value = "ID of topolog template to return", required = true) @PathVariable("TopologTemplateId") Long topologTemplateId, @ApiParam(value = "file detail") @Valid @RequestPart("file") MultipartFile file) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
