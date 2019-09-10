/*
 * Copyright 2017 S. Koulouzis, Wang Junchao, Huan Zhou, Yang Hu 
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nl.uva.sne.drip.api.v1.rest;

import com.webcohesion.enunciate.metadata.rs.ResponseCode;
import com.webcohesion.enunciate.metadata.rs.StatusCodes;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.service.BenchmarkResultService;
import nl.uva.sne.drip.api.service.UserService;
import nl.uva.sne.drip.drip.commons.data.v1.external.ansible.BenchmarkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * This controller is responsible for handling cloud benchmark tests like
 * sysbench
 *
 * @author S. Koulouzis
 */
@RestController
@RequestMapping("/user/v1.0/benchmark")
@Controller
@StatusCodes({
    @ResponseCode(code = 401, condition = "Bad credentials")
})
public class BenchmarkController {

    @Autowired
    private BenchmarkResultService benchmarkResultService;

    /**
     * Returns a BenchmarkResult
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 404, condition = "BenchmarkResult not found"),
        @ResponseCode(code = 200, condition = "BenchmarkResult exists")
    })
    public @ResponseBody
    BenchmarkResult get(@PathVariable("id") String id) {
        BenchmarkResult resp = benchmarkResultService.findOne(id);
        if (resp == null) {
            throw new NotFoundException();
        }
        return resp;
    }

    /**
     * Returns sysbench results only. Not supported yet
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful query")
    })
    public @ResponseBody
    List<BenchmarkResult> getBySysbench() {
        return benchmarkResultService.findBySysbench();
    }

    /**
     * Returns the ids of stored objects. Empty list if non stored
     *
     * @return
     */
    @RequestMapping(value = "/ids", method = RequestMethod.GET)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful query")
    })
    public @ResponseBody
    List<String> getIds() {
        List<BenchmarkResult> all = benchmarkResultService.findAll();
        List<String> ids = new ArrayList<>(all.size());
        for (BenchmarkResult pi : all) {
            ids.add(pi.getId());
        }
        return ids;
    }

    /**
     * Deletes object
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.USER, UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful delete"),
        @ResponseCode(code = 404, condition = "Non existing id")
    })
    public @ResponseBody
    String delete(@PathVariable("id") String id) {
        BenchmarkResult Key = benchmarkResultService.findOne(id);
        if (Key != null) {
            benchmarkResultService.delete(id);
            return "Deleted : " + id;
        }
        throw new NotFoundException();
    }

    /**
     * Deletes all entries. Use with caution !
     *
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.DELETE)
    @RolesAllowed({UserService.ADMIN})
    @StatusCodes({
        @ResponseCode(code = 200, condition = "Successful delete")
    })
    public @ResponseBody
    String deleteAll() {
        benchmarkResultService.deleteAll();
        return "Done";
    }
}
