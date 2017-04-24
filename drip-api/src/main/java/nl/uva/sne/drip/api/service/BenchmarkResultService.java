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
package nl.uva.sne.drip.api.service;

import java.util.List;
import nl.uva.sne.drip.api.dao.BenchmarkResultDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.data.v1.external.User;
import nl.uva.sne.drip.data.v1.external.ansible.BenchmarkResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class BenchmarkResultService {

    @Autowired
    private BenchmarkResultDao benchmarkResultDao;

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public BenchmarkResult findOne(String id) {
        BenchmarkResult benchmarkResult = benchmarkResultDao.findOne(id);
        if (benchmarkResult == null) {
            throw new NotFoundException();
        }
        return benchmarkResult;
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<BenchmarkResult> findAll() {
        return benchmarkResultDao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public BenchmarkResult delete(String id) {
        BenchmarkResult benchmarkResult = benchmarkResultDao.findOne(id);
        if (benchmarkResult == null) {
            throw new NotFoundException();
        }
        benchmarkResultDao.delete(benchmarkResult);
        return benchmarkResult;
    }

    public BenchmarkResult save(BenchmarkResult benchmarkResult) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        benchmarkResult.setOwner(owner);
        return benchmarkResultDao.save(benchmarkResult);
    }

    @PostAuthorize("(hasRole('ROLE_ADMIN'))")
    public void deleteAll() {
        benchmarkResultDao.deleteAll();
    }

    public List<BenchmarkResult> findBySysbench() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.s
    }
}
