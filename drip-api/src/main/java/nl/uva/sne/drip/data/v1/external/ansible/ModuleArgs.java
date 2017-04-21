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
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "dpkg_options",
    "autoremove",
    "force",
    "name",
    "install_recommends",
    "package",
    "purge",
    "allow_unauthenticated",
    "state",
    "upgrade",
    "update_cache",
    "deb",
    "only_upgrade",
    "default_release",
    "cache_valid_time"
})
public class ModuleArgs {

    @JsonProperty("dpkg_options")
    private String dpkgOptions;
    @JsonProperty("autoremove")
    private Boolean autoremove;
    @JsonProperty("force")
    private Boolean force;
    @JsonProperty("name")
    private List<String> name = null;
    @JsonProperty("install_recommends")
    private Object installRecommends;
    @JsonProperty("package")
    private List<String> _package = null;
    @JsonProperty("purge")
    private Boolean purge;
    @JsonProperty("allow_unauthenticated")
    private Boolean allowUnauthenticated;
    @JsonProperty("state")
    private String state;
    @JsonProperty("upgrade")
    private Object upgrade;
    @JsonProperty("update_cache")
    private Boolean updateCache;
    @JsonProperty("deb")
    private Object deb;
    @JsonProperty("only_upgrade")
    private Boolean onlyUpgrade;
    @JsonProperty("default_release")
    private Object defaultRelease;
    @JsonProperty("cache_valid_time")
    private Integer cacheValidTime;

    @JsonProperty("dpkg_options")
    public String getDpkgOptions() {
        return dpkgOptions;
    }

    @JsonProperty("dpkg_options")
    public void setDpkgOptions(String dpkgOptions) {
        this.dpkgOptions = dpkgOptions;
    }

    @JsonProperty("autoremove")
    public Boolean getAutoremove() {
        return autoremove;
    }

    @JsonProperty("autoremove")
    public void setAutoremove(Boolean autoremove) {
        this.autoremove = autoremove;
    }

    @JsonProperty("force")
    public Boolean getForce() {
        return force;
    }

    @JsonProperty("force")
    public void setForce(Boolean force) {
        this.force = force;
    }

    @JsonProperty("name")
    public List<String> getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(List<String> name) {
        this.name = name;
    }

    @JsonProperty("install_recommends")
    public Object getInstallRecommends() {
        return installRecommends;
    }

    @JsonProperty("install_recommends")
    public void setInstallRecommends(Object installRecommends) {
        this.installRecommends = installRecommends;
    }

    @JsonProperty("package")
    public List<String> getPackage() {
        return _package;
    }

    @JsonProperty("package")
    public void setPackage(List<String> _package) {
        this._package = _package;
    }

    @JsonProperty("purge")
    public Boolean getPurge() {
        return purge;
    }

    @JsonProperty("purge")
    public void setPurge(Boolean purge) {
        this.purge = purge;
    }

    @JsonProperty("allow_unauthenticated")
    public Boolean getAllowUnauthenticated() {
        return allowUnauthenticated;
    }

    @JsonProperty("allow_unauthenticated")
    public void setAllowUnauthenticated(Boolean allowUnauthenticated) {
        this.allowUnauthenticated = allowUnauthenticated;
    }

    @JsonProperty("state")
    public String getState() {
        return state;
    }

    @JsonProperty("state")
    public void setState(String state) {
        this.state = state;
    }

    @JsonProperty("upgrade")
    public Object getUpgrade() {
        return upgrade;
    }

    @JsonProperty("upgrade")
    public void setUpgrade(Object upgrade) {
        this.upgrade = upgrade;
    }

    @JsonProperty("update_cache")
    public Boolean getUpdateCache() {
        return updateCache;
    }

    @JsonProperty("update_cache")
    public void setUpdateCache(Boolean updateCache) {
        this.updateCache = updateCache;
    }

    @JsonProperty("deb")
    public Object getDeb() {
        return deb;
    }

    @JsonProperty("deb")
    public void setDeb(Object deb) {
        this.deb = deb;
    }

    @JsonProperty("only_upgrade")
    public Boolean getOnlyUpgrade() {
        return onlyUpgrade;
    }

    @JsonProperty("only_upgrade")
    public void setOnlyUpgrade(Boolean onlyUpgrade) {
        this.onlyUpgrade = onlyUpgrade;
    }

    @JsonProperty("default_release")
    public Object getDefaultRelease() {
        return defaultRelease;
    }

    @JsonProperty("default_release")
    public void setDefaultRelease(Object defaultRelease) {
        this.defaultRelease = defaultRelease;
    }

    @JsonProperty("cache_valid_time")
    public Integer getCacheValidTime() {
        return cacheValidTime;
    }

    @JsonProperty("cache_valid_time")
    public void setCacheValidTime(Integer cacheValidTime) {
        this.cacheValidTime = cacheValidTime;
    }
}
