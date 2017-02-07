/*
 * Copyright 2017 S. Koulouzis.
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
package nl.uva.sne.drip.data;

import java.util.Date;

/**
 *
 * @author S. Koulouzis.
 */
public class Request implements IRequest {

    private String id;
    private Date creationDate;
    private Status status;

    @Override
    public void setID(String id) {
        this.id = id;
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public Date getCreationDate() {
        return this.creationDate;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status status) {
        status = this.status;
    }

}
