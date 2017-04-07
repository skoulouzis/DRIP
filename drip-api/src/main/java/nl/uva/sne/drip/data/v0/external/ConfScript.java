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
package nl.uva.sne.drip.data.v0.external;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author S. Koulouzis
 */
@XmlRootElement
public class ConfScript extends Execute {

    /**
     * The script contents with new lines replace by '\n'
     */
    @DocumentationExample("#!/bin/bash\\n\\nlogstashScript=/root/runLogstash.sh\\nscreen -S logstash -dm /bin/bash \"$logstashScript\"\\n\\n\\nlogLevelPath=/root/\\nlogLevelFile=$logLevelPath/logstash_loglevel.csv\\nremotePath=/media/lobcder/skoulouz/\\nremoteLogPath=$remotePath/logs\\nlocalLogPath=/root/logs\\n\\nmkdir $localLogPath\\n\\n\\nfor logArchPath in $remoteLogPath/*.gz; do\\n  echo \"File -> $logArchPath\"\\n  if [ ! -f $logArchPath.lock ]; then\\n    touch $logArchPath.lock\\n    cp $logArchPath $localLogPath\\n    logArchName=$(basename $logArchPath)\\n    tar -xvf $localLogPath/$logArchName -C $localLogPath\\n    sleep 120\\n    modTime=-100\\n    while [ ! -f $logLevelFile ]\\n    do\\n      sleep 5\\n    done\\n    while [ $modTime -lt 120 ]\\n    do\\n      moddate=$(stat -c %Y $logLevelFile)\\n      moddate=${moddate%% *}\\n      now=$(date +%s)\\n      modTime=\"$(( $now - $moddate))\"\\n      echo $modTime\\n      sleep 5\\n    done\\n    rm $localLogPath/*.tar.gz\\n    rm $localLogPath/*.log\\n    modTime=-100\\n  fi\\ndone\\n    \\n\\nscreen -X -S logstash quit\\nsleep 5\\n\\nuid=`ifconfig | grep eth0 | awk '{print $NF}' | sed 's/://g'`\\n\\nfor csvFiles in $logLevelPath/*.csv; do\\n  echo $csvFiles $csvFiles$uid.csv\\n  mv $csvFiles $csvFiles$uid.csv\\n  cp $csvFiles$uid.csv $remotePath\\ndone\\n\\nkillall java \\n")
    public String script;
}
