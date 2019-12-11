package nl.uva.sne.drip.model.cloud.storm;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * DCMetaInfo
 */
@Validated
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-11T15:13:55.016Z")

public class DCMetaInfo {

    @JsonProperty("domain")
    private String domain = null;

    @JsonProperty("endpoint")
    private String endpoint = null;

    @JsonProperty("country")
    private String country = null;

    @JsonProperty("longitude")
    private String longitude = null;

    @JsonProperty("latitude")
    private String latitude = null;

    @JsonProperty("availability")
    private String availability = null;

    @JsonProperty("VMMetaInfo")
    private List<VMMetaInfo> vmMetaInfo = null;

    @JsonProperty("extraInfo")
    @Valid
    private Map<String, Object> extraInfo = null;

    public DCMetaInfo domain(String domain) {
        this.domain = domain;
        return this;
    }

    /**
     * Get domain
     *
     * @return domain
  *
     */
    @ApiModelProperty(value = "")

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public DCMetaInfo endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    /**
     * Get endpoint
     *
     * @return endpoint
  *
     */
    @ApiModelProperty(value = "")

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public DCMetaInfo country(String country) {
        this.country = country;
        return this;
    }

    /**
     * Get country
     *
     * @return country
  *
     */
    @ApiModelProperty(value = "")

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public DCMetaInfo longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    /**
     * Get longitude
     *
     * @return longitude
  *
     */
    @ApiModelProperty(value = "")

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public DCMetaInfo latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    /**
     * Get latitude
     *
     * @return latitude
  *
     */
    @ApiModelProperty(value = "")

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public DCMetaInfo availability(String availability) {
        this.availability = availability;
        return this;
    }

    /**
     * Get availability
     *
     * @return availability
  *
     */
    @ApiModelProperty(value = "")

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public DCMetaInfo vmMetaInfo(List<VMMetaInfo> vmMetaInfo) {
        this.vmMetaInfo = vmMetaInfo;
        return this;
    }

    /**
     * Get vmMetaInfo
     *
     * @return vmMetaInfo
  *
     */
    @ApiModelProperty(value = "")

    @Valid

    public List<VMMetaInfo> getVmMetaInfo() {
        return vmMetaInfo;
    }

    public void setVmMetaInfo(List<VMMetaInfo> vmMetaInfo) {
        this.vmMetaInfo = vmMetaInfo;
    }

    public DCMetaInfo extraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
        return this;
    }

    public DCMetaInfo putExtraInfoItem(String key, Object extraInfoItem) {
        if (this.extraInfo == null) {
            this.extraInfo = new HashMap<String, Object>();
        }
        this.extraInfo.put(key, extraInfoItem);
        return this;
    }

    /**
     * Get extraInfo
     *
     * @return extraInfo
  *
     */
    @ApiModelProperty(value = "")

    public Map<String, Object> getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Map<String, Object> extraInfo) {
        this.extraInfo = extraInfo;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DCMetaInfo dcMetaInfo = (DCMetaInfo) o;
        return Objects.equals(this.domain, dcMetaInfo.domain)
                && Objects.equals(this.endpoint, dcMetaInfo.endpoint)
                && Objects.equals(this.country, dcMetaInfo.country)
                && Objects.equals(this.longitude, dcMetaInfo.longitude)
                && Objects.equals(this.latitude, dcMetaInfo.latitude)
                && Objects.equals(this.availability, dcMetaInfo.availability)
                && Objects.equals(this.vmMetaInfo, dcMetaInfo.vmMetaInfo)
                && Objects.equals(this.extraInfo, dcMetaInfo.extraInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domain, endpoint, country, longitude, latitude, availability, vmMetaInfo, extraInfo);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class DCMetaInfo {\n");

        sb.append("    domain: ").append(toIndentedString(domain)).append("\n");
        sb.append("    endpoint: ").append(toIndentedString(endpoint)).append("\n");
        sb.append("    country: ").append(toIndentedString(country)).append("\n");
        sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
        sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
        sb.append("    availability: ").append(toIndentedString(availability)).append("\n");
        sb.append("    vmMetaInfo: ").append(toIndentedString(vmMetaInfo)).append("\n");
        sb.append("    extraInfo: ").append(toIndentedString(extraInfo)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
