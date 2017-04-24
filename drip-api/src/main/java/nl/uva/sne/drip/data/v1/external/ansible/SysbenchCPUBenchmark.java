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
package nl.uva.sne.drip.data.v1.external.ansible;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author S. Koulouzis
 */
@Document
public class SysbenchCPUBenchmark extends BenchmarkResult {

    private String sysbenchVersion;
    private int numberOfThreads;
    private int totalNumberOfEvents;
    private double executionTime;
    private int avgEventsPerThread;
    private int stddevEventsPerThread;

    private int avgExecTimePerThread;
    private int stddevExecTimePerThread;

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
    public int getAvgEventsPerThread() {
        return avgEventsPerThread;
    }

    /**
     * @param avgEventsPerThread the avgEventsPerThread to set
     */
    public void setAvgEventsPerThread(int avgEventsPerThread) {
        this.avgEventsPerThread = avgEventsPerThread;
    }

    /**
     * @return the stddevEventsPerThread
     */
    public int getStddevEventsPerThread() {
        return stddevEventsPerThread;
    }

    /**
     * @param stddevEventsPerThread the stddevEventsPerThread to set
     */
    public void setStddevEventsPerThread(int stddevEventsPerThread) {
        this.stddevEventsPerThread = stddevEventsPerThread;
    }

    /**
     * @return the avgExecTimePerThread
     */
    public int getAvgExecTimePerThread() {
        return avgExecTimePerThread;
    }

    /**
     * @param avgExecTimePerThread the avgExecTimePerThread to set
     */
    public void setAvgExecTimePerThread(int avgExecTimePerThread) {
        this.avgExecTimePerThread = avgExecTimePerThread;
    }

    /**
     * @return the stddevExecTimePerThread
     */
    public int getStddevExecTimePerThread() {
        return stddevExecTimePerThread;
    }

    /**
     * @param stddevExecTimePerThread the stddevExecTimePerThread to set
     */
    public void setStddevExecTimePerThread(int stddevExecTimePerThread) {
        this.stddevExecTimePerThread = stddevExecTimePerThread;
    }

}
