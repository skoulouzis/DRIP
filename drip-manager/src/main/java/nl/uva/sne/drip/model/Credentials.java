package nl.uva.sne.drip.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;

/**
 * Credentials
 */
@Validated
@JsonInclude(Include.NON_NULL)
public class Credentials {

    @JsonProperty("protocol")
    private String protocol = null;

    @JsonProperty("token_type")
    private String tokenType = null;

    @JsonProperty("token")
    private String token = null;

    @JsonProperty("keys")
    @Valid
    private Map<String, String> keys = null;

    @JsonProperty("user")
    private String user = null;

    @JsonProperty("cloud_provider_name")
    private String cloudProviderName = null;

    public Credentials protocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    /**
     * Get protocol
     *
     * @return protocol
  *
     */
    @ApiModelProperty(value = "")

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Credentials tokenType(String tokenType) {
        this.tokenType = tokenType;
        return this;
    }

    /**
     * Get tokenType
     *
     * @return tokenType
  *
     */
    @ApiModelProperty(value = "")

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public Credentials token(String token) {
        this.token = token;
        return this;
    }

    /**
     * Get token
     *
     * @return token
  *
     */
    @ApiModelProperty(value = "")

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Credentials keys(Map<String, String> keys) {
        this.keys = keys;
        return this;
    }

    public Credentials putKeysItem(String key, String keysItem) {
        if (this.keys == null) {
            this.keys = new HashMap<String, String>();
        }
        this.keys.put(key, keysItem);
        return this;
    }

    /**
     * Get keys
     *
     * @return keys
  *
     */
    @ApiModelProperty(value = "")

    public Map<String, String> getKeys() {
        return keys;
    }

    public void setKeys(Map<String, String> keys) {
        this.keys = keys;
    }

    public Credentials user(String user) {
        this.user = user;
        return this;
    }

    /**
     * Get user
     *
     * @return user
  *
     */
    @ApiModelProperty(value = "")

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Credentials cloudProviderName(String cloudProviderName) {
        this.cloudProviderName = cloudProviderName;
        return this;
    }

    /**
     * Get cloudProviderName
     *
     * @return cloudProviderName
  *
     */
    @ApiModelProperty(value = "")

    public String getCloudProviderName() {
        return cloudProviderName;
    }

    public void setCloudProviderName(String cloudProviderName) {
        this.cloudProviderName = cloudProviderName;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Credentials credentials = (Credentials) o;
        return Objects.equals(this.protocol, credentials.protocol)
                && Objects.equals(this.tokenType, credentials.tokenType)
                && Objects.equals(this.token, credentials.token)
                && Objects.equals(this.keys, credentials.keys)
                && Objects.equals(this.user, credentials.user)
                && Objects.equals(this.cloudProviderName, credentials.cloudProviderName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, tokenType, token, keys, user, cloudProviderName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Credentials {\n");

        sb.append("    protocol: ").append(toIndentedString(protocol)).append("\n");
        sb.append("    tokenType: ").append(toIndentedString(tokenType)).append("\n");
        sb.append("    token: ").append(toIndentedString(token)).append("\n");
        sb.append("    keys: ").append(toIndentedString(keys)).append("\n");
        sb.append("    user: ").append(toIndentedString(user)).append("\n");
        sb.append("    cloudProviderName: ").append(toIndentedString(cloudProviderName)).append("\n");
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