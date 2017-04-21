package nl.uva.sne.drip.data.v1.external.ansible;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "host",
    "result"
})
public class AnsibleOutput {

    @JsonProperty("host")
    private String host;
    @JsonProperty("result")
    private AnsibleResult result;

    @JsonProperty("host")
    public String getHost() {
        return host;
    }

    @JsonProperty("host")
    public void setHost(String host) {
        this.host = host;
    }

    @JsonProperty("result")
    public AnsibleResult getResult() {
        return result;
    }

    @JsonProperty("result")
    public void setResult(AnsibleResult result) {
        this.result = result;
    }

}
