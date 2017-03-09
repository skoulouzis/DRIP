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
package nl.uva.sne.drip.commons.v1.types;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.Map;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * This class represents a login key. This key can be used to either login to a
 * VM created by the provisiner or by the VM to allow the user to login to the
 * VMs from the machine the keys correspond to.
 *
 * @author S. Koulouzis
 */
@Document
public class LoginKey extends OwnedObject {

    @Id
    private String id;

    private Map<String, String> attributes;

    private String key;

    private Type type;
    private String name;

    /**
     * The name of the key.
     *
     * @return
     */
    @DocumentationExample("id_dsa.pub")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    public static enum Type {
        PRIVATE,
        PUBLIC
    }

    /**
     * The type of key
     *
     * @return the type
     */
    @DocumentationExample("PRIVATE")
    public Type getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(Type type) {
        this.type = type;
    }

    /**
     * A general use key,value. In the case of the cloud credentials it's used
     * to specify the domain name for ec2
     *
     * @return the attributes
     */
    @DocumentationExample("domain_name:Virginia")
    public Map<String, String> getAttributes() {
        return attributes;
    }

    /**
     * @param attributes the attributes to set
     */
    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }

    /**
     * The contents of the key.
     *
     * @return the key
     */
    @DocumentationExample("-----BEGIN RSA PRIVATE KEY-----\\nMIIEogIBAAKCAQEAm6AALYxkJFNzD3bfVJ4+hMY5j0/kqM9CURLKXMlYuAysnvoG8wZKx9Bedefm\\neNSse4zTg798ZA2kDMZFIrwp1AseTwtj8DDu5fhG5DjyI3g6iJltS5zFQdMXneDlHXBX8cncSzNY\\nRx0NdjEMAe7YttvI8FNlxL0VnMFli/HB/ftzYMe5+AmkSROncVGHiwoiUpj+vtobCFOYtXsCf6ri\\nd4lgWA5wv6DZT/JKCYymiBqgSXu3ueFcEzw5SAukARWVjn1xccjZkokFfBbO/FpYY00TrUTBw9S6\\nD3iM+gj8RT6EKILOmhrt71D21S95WAWIT7h2YBsy1KAvMixhNf9VaQIDAQABAoIBAHhVYK3Xl3tr\\nN1Xm0ctJTQg3ijxhR2qsUBgGUokqezpdOoD2zbbOz7XvTYsX1GLr967U9pwxzUpELexexwiTvDgk\\nnLv8D7ui6qbRsmc4DSsWBRSophVIVFKQmftO8Xow6x+fuYJAYmsicM1KIYHBILtL+PSzV8anenWq\\nKQ3r0tfCiQhEzKEk4b1uT3SJWQyHE++JAhVkO7lIeb6S9Dg1jAaAeMnJ/NiMxTarpPRnxe6hsTsH\\ngG1iKWo+Skcl4SknOc+CMEfyDjG4FL7MGhKduahsO8vMUrgGsDD7EH3NiX/FweB8La6qpDYAwFpC\\nycrooyhiyzw8Wb5gGaYnmvr9l70CgYEAx74O8JleXaHpxEAmh4h7VbLmJ3mOylfBmOdzcHeedJQw\\nack2SAv65WBI9S9MEQ7J/vFuyw5HNk3C/mcWgzDQXSNIhHLvl/Z9sux/Qpm3SQWLzBxKV3dJ4r\\nwcAxzVA93+/L1Nee+VOKnlyRumvVa6+XLsLagpap2AVcTqlerMcCgYEAx3T2pXtqkCE9eU/ov22r\\npdaKjgHoGOUg1CMEfWi/Ch6sYIIRyrHz6dhy+yR1pXNgPbLWdrn8l88F3+IsmbaMupMgRmqwEC3G\\n9Y2FglGIVvRdZaagvRxLzRCcvcN4v6OYs9ST4o1xlv7Qxphld+0XDKv7VSCv/rASuK8BqlFL3E8C\\ngYArMXJRnRjG7qh6g9TRIjZphdI3XxX9s5Rt2D8iZvuhAhqmBZjzY4PR7kxYmO2+EpCjzNnEl0XW\\n/GHaWbiIjhnAykx4N9KP7gGom3O5lzwHUme1XnFKcO2wDjQwJbufRmba8iQF1srN577mF+Z7ha4V\\nJ1duCTzvWF1KFX6sk/uhKQKBgAcDFai7rgNjJ8YcCRKxyFcMM9LKPl6hr4XFtWKzTAQPEABUkkuN\\n9gVClsg9f+VRKRECOIf0Ae1UWeCFEwxUXp4wjfHrzkTDVztKvmbWdvSXorDwKrZ7SC7tZpVFSfly\\nxuuLjadpUZT9YFmbAfY1X5oSccOMYqORjRbxEB3svb4BAoGAGTgFuq9Zojh/KIqY8b4HpEfmh6CQ\\nhLVfD98Nqd6GDbxgvIM0v4mFXE92x2jn35Ia0JdFyh3B8Vkl7sqQZfxDFXI9O9pte2mxY9ICaY\\n55+X/SN1pd53BH+gaPZJy/R+Vpvs5MN48hoUKy5UKpoFeUWrS5QArjtvNCm4SGlXw=\\n-----END RSA PRIVATE KEY-----\\n")
    public String getKey() {
        return key;
    }

    /**
     * @param key the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

}
