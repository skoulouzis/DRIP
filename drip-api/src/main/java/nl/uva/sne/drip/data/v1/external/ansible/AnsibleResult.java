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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import org.springframework.data.mongodb.core.index.Indexed;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnsibleResult {

    @JsonProperty("msg")
    private String msg;
    @JsonProperty("changed")
    private Boolean changed;

    @JsonProperty("end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date end;

    @JsonProperty("start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date start;

    @JsonProperty("delta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSSSS")
    private Date delta;

    
    @JsonProperty("stdout")
    private String stdout;

    @JsonProperty("stderr")
    private String stderr;

    @Indexed
    @JsonProperty("cmd")
    private List<String> cmd;

    @JsonProperty("stdout_lines")
    private List<String> stdout_lines;

    @JsonProperty("results")
    private List<AnsibleResult_> results = null;

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("changed")
    public Boolean getChanged() {
        return changed;
    }

    @JsonProperty("changed")
    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    @JsonProperty("results")
    public List<AnsibleResult_> getAnsibleResults() {
        return results;
    }

    @JsonProperty("results")
    public void setAnsibleResults(List<AnsibleResult_> results) {
        this.results = results;
    }

    @JsonProperty("end")
    public Date getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(Date end) {
        this.end = end;
    }

    @JsonProperty("stdout")
    public String getStdout() {
        return stdout;
    }

    @JsonProperty("stdout")
    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    @JsonProperty("cmd")
    public List<String> getCmd() {
        return cmd;
    }

    @JsonProperty("cmd")
    public void setCmd(List<String> cmd) {
        this.cmd = cmd;
    }

    @JsonProperty("start")
    public Date getStart() {
        return start;
    }

    @JsonProperty("start")
    public void setStart(Date start) {
        this.start = start;
    }

    @JsonProperty("stderr")
    public String getStderr() {
        return stderr;
    }

    @JsonProperty("stderr")
    public void setStderr(String stderr) {
        this.stderr = stderr;
    }

    @JsonProperty("delta")
    public Date getDelta() {
        return delta;
    }

    @JsonProperty("delta")
    public void setDelta(Date delta) {
        this.delta = delta;
    }

    @JsonProperty("stdout_lines")
    public List<String> getStdout_lines() {
        return stdout_lines;
    }

    @JsonProperty("stdout_lines")
    public void setStdout_lines(List<String> stdout_lines) {
        this.stdout_lines = stdout_lines;
    }

}
