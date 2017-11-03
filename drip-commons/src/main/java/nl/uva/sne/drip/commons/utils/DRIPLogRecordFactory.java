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
package nl.uva.sne.drip.commons.utils;

import java.util.logging.LogRecord;
import nl.uva.sne.drip.drip.commons.data.v1.external.DRIPLogRecord;

/**
 *
 * @author S. Koulouzis
 */
public class DRIPLogRecordFactory {

    public static DRIPLogRecord create(LogRecord rec) {
        DRIPLogRecord dRec = new DRIPLogRecord();
        dRec.setLevel(rec.getLevel().getName());
        dRec.setLoggerName(rec.getLoggerName());
        dRec.setMessage(rec.getMessage());
        dRec.setMillis(dRec.getTimestamp());
        dRec.setSequenceNumber(rec.getSequenceNumber());
        dRec.setSourceClassName(rec.getSourceClassName());
        dRec.setSourceMethodName(rec.getSourceMethodName());
        return dRec;
    }
}
