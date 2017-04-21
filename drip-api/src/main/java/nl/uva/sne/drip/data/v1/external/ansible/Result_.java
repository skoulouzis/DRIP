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

/**
 *
 * @author S. Koulouzis
 */
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "_ansible_parsed",
    "changed",
    "_ansible_no_log",
    "cache_updated",
    "_ansible_item_result",
    "item",
    "invocation",
    "cache_update_time"
})
public class Result_ {

    @JsonProperty("_ansible_parsed")
    private Boolean ansibleParsed;
    @JsonProperty("changed")
    private Boolean changed;
    @JsonProperty("_ansible_no_log")
    private Boolean ansibleNoLog;
    @JsonProperty("cache_updated")
    private Boolean cacheUpdated;
    @JsonProperty("_ansible_item_result")
    private Boolean ansibleItemResult;
    @JsonProperty("item")
    private List<String> item = null;
    @JsonProperty("invocation")
    private Invocation invocation;
    @JsonProperty("cache_update_time")
    private Integer cacheUpdateTime;

    @JsonProperty("_ansible_parsed")
    public Boolean getAnsibleParsed() {
        return ansibleParsed;
    }

    @JsonProperty("_ansible_parsed")
    public void setAnsibleParsed(Boolean ansibleParsed) {
        this.ansibleParsed = ansibleParsed;
    }

    @JsonProperty("changed")
    public Boolean getChanged() {
        return changed;
    }

    @JsonProperty("changed")
    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    @JsonProperty("_ansible_no_log")
    public Boolean getAnsibleNoLog() {
        return ansibleNoLog;
    }

    @JsonProperty("_ansible_no_log")
    public void setAnsibleNoLog(Boolean ansibleNoLog) {
        this.ansibleNoLog = ansibleNoLog;
    }

    @JsonProperty("cache_updated")
    public Boolean getCacheUpdated() {
        return cacheUpdated;
    }

    @JsonProperty("cache_updated")
    public void setCacheUpdated(Boolean cacheUpdated) {
        this.cacheUpdated = cacheUpdated;
    }

    @JsonProperty("_ansible_item_result")
    public Boolean getAnsibleItemResult() {
        return ansibleItemResult;
    }

    @JsonProperty("_ansible_item_result")
    public void setAnsibleItemResult(Boolean ansibleItemResult) {
        this.ansibleItemResult = ansibleItemResult;
    }

    @JsonProperty("item")
    public List<String> getItem() {
        return item;
    }

    @JsonProperty("item")
    public void setItem(List<String> item) {
        this.item = item;
    }

    @JsonProperty("invocation")
    public Invocation getInvocation() {
        return invocation;
    }

    @JsonProperty("invocation")
    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    @JsonProperty("cache_update_time")
    public Integer getCacheUpdateTime() {
        return cacheUpdateTime;
    }

    @JsonProperty("cache_update_time")
    public void setCacheUpdateTime(Integer cacheUpdateTime) {
        this.cacheUpdateTime = cacheUpdateTime;
    }

}
