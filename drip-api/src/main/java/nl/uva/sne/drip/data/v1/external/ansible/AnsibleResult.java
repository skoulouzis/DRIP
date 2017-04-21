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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class AnsibleResult {

    @JsonProperty("_ansible_parsed")
    private Boolean ansibleParsed;
    @JsonProperty("changed")
    private Boolean changed;

    @JsonProperty("results")
    private List<Result_> results = null;

    @JsonProperty("_ansible_verbose_always")
    private Boolean ansible_verbose_always;

    @JsonProperty("end")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date end;
    @JsonProperty("_ansible_no_log")
    private Boolean ansibleNoLog;
    @JsonProperty("stdout")
    private String stdout;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("cmd")
    private List<String> cmd = null;
    @JsonProperty("rc")
    private Integer rc;
    @JsonProperty("start")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSSSS")
    private Date start;
    @JsonProperty("stderr")
    private String stderr;
    @JsonProperty("delta")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss.SSSSSS")
    private Date delta;
    @JsonProperty("invocation")
    private Invocation invocation;
    @JsonProperty("stdout_lines")
    private List<String> stdoutLines = null;

    @JsonProperty("warnings")
    private List<String> warnings = null;

    @JsonProperty("_ansible_parsed")
    public Boolean getAnsibleParsed() {
        return ansibleParsed;
    }

    @JsonProperty("_ansible_parsed")
    public void setAnsibleParsed(Boolean ansibleParsed) {
        this.ansibleParsed = ansibleParsed;
    }

    @JsonProperty("_ansible_verbose_always")
    public Boolean getAnsibleVerboseAlways() {
        return ansible_verbose_always;
    }

    @JsonProperty("_ansible_verbose_always")
    public void setAnsibleVerboseAlways(Boolean ansible_verbose_always) {
        this.ansible_verbose_always = ansible_verbose_always;
    }

    @JsonProperty("changed")
    public Boolean getChanged() {
        return changed;
    }

    @JsonProperty("changed")
    public void setChanged(Boolean changed) {
        this.changed = changed;
    }

    @JsonProperty("_ansible_verbose_always")
    public Boolean getAnsible_verbose_always() {
        return changed;
    }

    @JsonProperty("_ansible_verbose_always")
    public void setAnsible_verbose_always(Boolean _ansible_verbose_always) {
        this.ansible_verbose_always = _ansible_verbose_always;
    }

    @JsonProperty("end")
    public Date getEnd() {
        return end;
    }

    @JsonProperty("end")
    public void setEnd(Date end) {
        this.end = end;
    }

    @JsonProperty("_ansible_no_log")
    public Boolean getAnsibleNoLog() {
        return ansibleNoLog;
    }

    @JsonProperty("_ansible_no_log")
    public void setAnsibleNoLog(Boolean ansibleNoLog) {
        this.ansibleNoLog = ansibleNoLog;
    }

    @JsonProperty("stdout")
    public String getStdout() {
        return stdout;
    }

    @JsonProperty("stdout")
    public void setStdout(String stdout) {
        this.stdout = stdout;
    }

    @JsonProperty("msg")
    public String getMsg() {
        return msg;
    }

    @JsonProperty("msg")
    public void setMsg(String msg) {
        this.msg = msg;
    }

    @JsonProperty("cmd")
    public List<String> getCmd() {
        return cmd;
    }

    @JsonProperty("cmd")
    public void setCmd(List<String> cmd) {
        this.cmd = cmd;
    }

    @JsonProperty("rc")
    public Integer getRc() {
        return rc;
    }

    @JsonProperty("rc")
    public void setRc(Integer rc) {
        this.rc = rc;
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

    @JsonProperty("invocation")
    public Invocation getInvocation() {
        return invocation;
    }

    @JsonProperty("invocation")
    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    @JsonProperty("stdout_lines")
    public List<String> getStdoutLines() {
        return stdoutLines;
    }

    @JsonProperty("stdout_lines")
    public void setStdoutLines(List<String> stdoutLines) {
        this.stdoutLines = stdoutLines;
    }

    @JsonProperty("warnings")
    public List<String> getWarnings() {
        return warnings;
    }

    @JsonProperty("warnings")
    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    @JsonProperty("results")
    public List<Result_> getResults() {
        return results;
    }

    @JsonProperty("results")
    public void setResults(List<Result_> results) {
        this.results = results;
    }

}
