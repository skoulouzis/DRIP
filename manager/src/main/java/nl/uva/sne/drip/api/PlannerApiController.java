package nl.uva.sne.drip.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import javax.servlet.http.HttpServletRequest;
import nl.uva.sne.drip.service.DRIPService;
import nl.uva.sne.drip.sure.tosca.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Controller
public class PlannerApiController implements PlannerApi {

    private static final Logger log = LoggerFactory.getLogger(PlannerApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @Value("${message.broker.queue.planner}")
    private String queueName;
    @Autowired
    private DRIPService dripService;

    @org.springframework.beans.factory.annotation.Autowired
    public PlannerApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    @Override
    public ResponseEntity<String> planToscaTemplateByID(@ApiParam(
            value = "ID of topolog template to plan", required = true)
            @PathVariable("id") String id) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("text/plain")) {
            try {
                dripService.setRequestQeueName(queueName);
                String planedYemplateId = dripService.plan(id);
                return new ResponseEntity<>(planedYemplateId, HttpStatus.OK);
            } catch (ApiException ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } catch (Exception ex) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
