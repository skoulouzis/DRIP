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
package nl.uva.sne.drip.drip.commons.data.v1.external.ansible;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *This class represents a sysbench CPU Benchmark for a specific VM.
 * @author S. Koulouzis
 */
@Document
public class SysbenchCPUBenchmark extends BenchmarkResult {

    private String sysbenchVersion;
    private int numberOfThreads;
    private double executionTime;
    private int totalNumberOfEvents;
    private double avgEventsPerThread;
    private double stddevEventsPerThread;

    private double avgExecTimePerThread;
    private double stddevExecTimePerThread;

    private double minExecutionTimePerRequest;
    private double avgExecutionTimePerRequest;
    private double maxExecutionTimePerRequest;
    private double approx95Percentile;
    private String ansibleOutputID;

    /**
     * @return the sysbenchVersion
     */
    public String getSysbenchVersion() {
        return sysbenchVersion;
    }

    /**
     * @param sysbenchVersion the sysbenchVersion to set
     */
    public void setSysbenchVersion(String sysbenchVersion) {
        this.sysbenchVersion = sysbenchVersion;
    }

    /**
     * @return the numberOfThreads
     */
    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    /**
     * @param numberOfThreads the numberOfThreads to set
     */
    public void setNumberOfThreads(int numberOfThreads) {
        this.numberOfThreads = numberOfThreads;
    }

    /**
     * @return the totalNumberOfEvents
     */
    public int getTotalNumberOfEvents() {
        return totalNumberOfEvents;
    }

    /**
     * @param totalNumberOfEvents the totalNumberOfEvents to set
     */
    public void setTotalNumberOfEvents(int totalNumberOfEvents) {
        this.totalNumberOfEvents = totalNumberOfEvents;
    }

    /**
     * @return the executionTime
     */
    public double getExecutionTime() {
        return executionTime;
    }

    /**
     * @param executionTime the executionTime to set
     */
    public void setExecutionTime(double executionTime) {
        this.executionTime = executionTime;
    }

    /**
     * @return the avgEventsPerThread
     */
    public double getAvgEventsPerThread() {
        return avgEventsPerThread;
    }

    /**
     * @param avgEventsPerThread the avgEventsPerThread to set
     */
    public void setAvgEventsPerThread(double avgEventsPerThread) {
        this.avgEventsPerThread = avgEventsPerThread;
    }

    /**
     * @return the stddevEventsPerThread
     */
    public double getStddevEventsPerThread() {
        return stddevEventsPerThread;
    }

    /**
     * @param stddevEventsPerThread the stddevEventsPerThread to set
     */
    public void setStddevEventsPerThread(double stddevEventsPerThread) {
        this.stddevEventsPerThread = stddevEventsPerThread;
    }

    /**
     * @return the avgExecTimePerThread
     */
    public double getAvgExecTimePerThread() {
        return avgExecTimePerThread;
    }

    /**
     * @param avgExecTimePerThread the avgExecTimePerThread to set
     */
    public void setAvgExecTimePerThread(double avgExecTimePerThread) {
        this.avgExecTimePerThread = avgExecTimePerThread;
    }

    /**
     * @return the stddevExecTimePerThread
     */
    public double getStddevExecTimePerThread() {
        return stddevExecTimePerThread;
    }

    /**
     * @param stddevExecTimePerThread the stddevExecTimePerThread to set
     */
    public void setStddevExecTimePerThread(double stddevExecTimePerThread) {
        this.stddevExecTimePerThread = stddevExecTimePerThread;
    }

    /**
     * @return the minExecutionTimePerRequest
     */
    public double getMinExecutionTimePerRequest() {
        return minExecutionTimePerRequest;
    }

    /**
     * @param minExecutionTimePerRequest the minExecutionTimePerRequest to set
     */
    public void setMinExecutionTimePerRequest(double minExecutionTimePerRequest) {
        this.minExecutionTimePerRequest = minExecutionTimePerRequest;
    }

    /**
     * @return the avgExecutionTimePerRequest
     */
    public double getAvgExecutionTimePerRequest() {
        return avgExecutionTimePerRequest;
    }

    /**
     * @param avgExecutionTimePerRequest the avgExecutionTimePerRequest to set
     */
    public void setAvgExecutionTimePerRequest(double avgExecutionTimePerRequest) {
        this.avgExecutionTimePerRequest = avgExecutionTimePerRequest;
    }

    /**
     * @return the maxExecutionTimePerRequest
     */
    public double getMaxExecutionTimePerRequest() {
        return maxExecutionTimePerRequest;
    }

    /**
     * @param maxExecutionTimePerRequest the maxExecutionTimePerRequest to set
     */
    public void setMaxExecutionTimePerRequest(double maxExecutionTimePerRequest) {
        this.maxExecutionTimePerRequest = maxExecutionTimePerRequest;
    }

    /**
     * @return the approx95Percentile
     */
    public double getApprox95Percentile() {
        return approx95Percentile;
    }

    /**
     * @param approx95Percentile the approx95Percentile to set
     */
    public void setApprox95Percentile(double approx95Percentile) {
        this.approx95Percentile = approx95Percentile;
    }

    public void setAnsibleOutputID(String ansibleOutputID) {
        this.ansibleOutputID = ansibleOutputID;
    }

    /**
     * @return the ansibleOutputID
     */
    public String getAnsibleOutputID() {
        return ansibleOutputID;
    }

}
