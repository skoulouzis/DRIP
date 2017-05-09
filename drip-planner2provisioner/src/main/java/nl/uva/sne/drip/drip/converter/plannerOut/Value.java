package nl.uva.sne.drip.drip.converter.plannerOut;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "size",
    "name",
    "docker"
})
public class Value {

    @JsonProperty("size")
    private String size;
    @JsonProperty("name")
    private String name;
    @JsonProperty("docker")
    private String docker;

    @JsonProperty("size")
    public String getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(String size) {
        this.size = size;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("docker")
    public String getDocker() {
        return docker;
    }

    @JsonProperty("docker")
    public void setDocker(String docker) {
        this.docker = docker;
    }
}
