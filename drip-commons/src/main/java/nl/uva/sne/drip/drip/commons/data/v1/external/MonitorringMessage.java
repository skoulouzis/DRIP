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
package nl.uva.sne.drip.drip.commons.data.v1.external;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.Date;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MonitorringMessage extends OwnedObject {

    private Date date;

    private Date time;

    private String metricMame;

    private String subid;

    private double value;

    private String messageType;

    /**
     * @return the metricMame
     */
    @DocumentationExample("cpuTotal")
    public String getMetricMame() {
        return metricMame;
    }

    /**
     * @param metricMame the metricMame to set
     */
    public void setMetricMame(String metricMame) {
        this.metricMame = metricMame;
    }

    /**
     * @return the subid
     */
    @DocumentationExample("1ccba0cc92174ce788695cfc0a027b57")
    public String getSubid() {
        return subid;
    }

    /**
     * @param subid the subid to set
     */
    public void setSubid(String subid) {
        this.subid = subid;
    }

    /**
     * @return the value
     */
    @DocumentationExample("91.0500021021063106")
    public double getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * @return the messageType
     */
    @DocumentationExample("critical")
    public String getMessageType() {
        return messageType;
    }

    /**
     * @param messageType the messageType to set
     */
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    /**
     * @return the date
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @DocumentationExample("2017-08-25")
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the time
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm:ss")
    @DocumentationExample("11:30:00")
    public Date getTime() {
        return time;
    }

    /**
     * @param time the time to set
     */
    public void setTime(Date time) {
        this.time = time;
    }

}
