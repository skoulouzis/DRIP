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
package nl.uva.sne.drip.commons.v0.types;

import com.webcohesion.enunciate.metadata.DocumentationExample;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author S. Koulouzis
 */
@XmlRootElement
public class Configure {

    /**
     * Not used. It's there for backwards compatibility.
     */
    @DocumentationExample("user")
    public String user;
    /**
     * Not used. It's there for backwards compatibility.
     */
    @DocumentationExample("123")
    public String pwd;

    /**
     * The key id for the cloud provider.
     *
     */
    @DocumentationExample("AKIAITY3K5ZUQ6M7YBSQ")
    public String keyid;

    /**
     * The key for the cloud provider.
     */
    @DocumentationExample("6J7uo99ifrff45126Gsy5vgb3bmrtwY6hBxtYt9y")
    public String key;

    @XmlElement(name = "loginKey")
    public List<LoginKey0> loginKey;

    /**
     * The binary keystore (user.jks) for the cloud provider encoded in base64
     */
    @DocumentationExample("/u3+7QAAAAIAAAABAWuf2AAACvDCCArgwDgYKKwYBBEBAQUABIIC\\npKlNUfpKLNCy0h8P4L5XeECwwIN/tezeaOVsjvzLsXeiBsB/luYv9gttoeKFbcJb/IDpZr\\nfpL1tjF8Sed6g38kNoWwnVVoNZ1ILpITjESL6FkZW+gGAPS+XHp8l52/2DjeECQlx+38GlFUftWP\\nn3QH9bDYD+7sRtm4P0YWnekX67ltQHMV+Cyrg0HAFm+GUzJJoxu64MccURLBMw0If9gCmTOuVoRj\\nprPukU+cMY8torQ3+WmzC0S6U10HjQ2AkJ80HwM6CcS82qflHIBfQ/o7ep76rCDN22widcLGOihU\\nbJiNQtNdF6KRCJk0emVIvRHDHctsmmt2ao2Qx6ub2mA/eUZtlphHMVN5VQtSxdd87tKrH9FvaOe3\\nGX/irmBBopS2mfFee4LPn3FT/F5bl9YunygX3sWLGNsCl8G15hzgSSVn4As37czUulj8hks/Qpvu\\nqoVpegs2+m5mXbSMmif+cUPqyJZ8UL5slIGZMKQ7MJG1XVG6vFIHk3cS+L+NpDd2j8DZqBP5FXIO\\nCt/IxZxR2ZfPHHmIbOhwdnaznOoyAEGDzFTXnyTClGTDTW/6zxOe/ynNPlnBQaNqVJYqPtFG4AVs\\nhrSNuvYjm1xsVDROQI5LYF/nryU4VvqMTo2sjVP2g6Qw7E5ENWiYUAl2W+bk4H6WGDTdQgLm2J65\\ngdZpQTQtubzWi8Fxrptqul5eq96l0xH15XQ9lBKV0J8PEMa1jKOW/9s2U++hAtKTixssVntaNyUY\\n9dnXNYHnQRKXDrQr/izerNEhvlVcz6foWDurtjAIjxafyEBbXJ5TAyT4rxmkPxDg88LPXKCzpf1x\\n3WwD8MBUNBC9nfB37rHxEl7StvnK3IXBHmEksg7X6xhmUsrsQo8mfP05XDmqt+lsiYoAAAACAAVY\\nLjUwOQAAAz8wggM7MIICpKADAgECAgJFBDANBgkqhkiG9w0BAQUFADCBiTEUMBIGA1UEChMLY2gu\\nZ2VuaS5uZXQxEjAQBgNVBAsTCWF1dGhvcml0eTELMAkGA1UECxMCbWExLTArBgNVBAMTJDMxYzBm\\nMDlmLTk1ZjctNDUxMC1hMzBiLWQ5M2RmMmJkMDJjOTEhMB8GCSqGSIb3DQEJARYSY2gtYWRtaW5z\\nQGdlbmkubmV0MB4XDTE3MDEyNTE0NTY0OVoXDTE4MDEyNTE0NTY0OVowUjEtMCsGA1UEAxMkYmY4\\nYThhNTQtN2FjOS00NTEzLWIyZTItYjJjN2U2YWUwMDVkMSEwHwYJKoZIhvcNAQkBFhJzLmtvdWxv\\ndXppc0B1dmEubmwwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBANmwnBJETSZSQm67FxQHL0s6\\n84SYlYh6ltgi8DfwRu/wMA8sL5PsuupNvgKJCVHHZzPVwIrUy8JxpFrX3BItaDX+VPR+6sBJqUan\\nBeb3vojU6CyEZzwCAOhn9OxB8ZFyoMD6SwsykPBOeXhGKpWsOoBwQDx8i6xpto1AP3KY8DpxAgMB\\nAAGjgecwgeQwHQYDVR0OBBYEFNTBvaP5OnG0h8exiS8Ob0ZvfF8FMD4GA1UdIwQ3MDWAFHGlguYe\\n8bTQK4umhY/oGl1ifTGuoRqkGDAWMRQwEgYDVQQDEwtjaC5nZW5pLm5ldIIBAzAJBgNVHRMEAjAA\\nMHgGA1UdEQRxMG+BEnMua291bG91emlzQHV2YS5ubIYqdXJuOnB1YmxpY2lkOklETitjaC5nZW5p\\nLm5ldCt1c2VyK3Nrb3Vsb3V6hi11cm46dXVpZDpiZjhhOGE1NC03YWM5LTQ1MTMtYjJlMi1iMmM3\\nZTZhZTAwNWQwDQYJKoZIhvcNAQEFBQADgYEAP/9ihMCaalsPRBMDozgX2Wd6eFCNYuDIMFTeNkKa\\nkWJp156oQ4iAmbtP2R9r4W02gjEQRtUxvBhYI2Rp32wl1ZLdpSwnuZAE1H89dIHTct48VkiI1Zp9\\n5BtV4olBUAf2K2hd46pH3ObUAYZvWQGoT+oOlfcacAaEUNp01afC8voABVguNTA5AAADUzCCA08w\\nggK4oAMCAQICAQMwDQYJKoZIhvcNAQEFBQAwFjEUMBIGA1UEAxMLY2guZ2VuaS5uZXQwHhcNMTMw\\nNTIwMTMxODI3WhcNMTgwNTE5MTMxODI3WjCBiTEUMBIGA1UEChMLY2guZ2VuaS5uZXQxEjAQBgNV\\nBAsTCWF1dGhvcml0eTELMAkGA1UECxMCbWExLTArBgNVBAMTJDMxYzBmMDlmLTk1ZjctNDUxMC1h\\nMzBiLWQ5M2RmMmJkMDJjOTEhMB8GCSqGSIb3DQEJARYSY2gtYWRtaW5zQGdlbmkubmV0MIGfMA0G\\nCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC4LG9Xl2veo5+Zy1fDi47ajinYu+mv8QYDya2Hf8lPolQy\\nlnGFFQRMx/JsbG1BBQ86FqSeUWtvANhY1nRlrozutOBeg9EEfhX+JXvVdhEKQ4/ANliC0V1UR3GO\\nJHtQaj/6qoG/q01bWP7EJgf+ZPfTdCgCkJ89v8oth1m3UjCxcQIDAQABo4IBNzCCATMwHQYDVR0O\\nBBYEFHGlguYe8bTQK4umhY/oGl1ifTGuMEYGA1UdIwQ/MD2AFEU22V1Y/0L1KwlqbnkdPIMEy5hR\\noRqkGDAWMRQwEgYDVQQDEwtjaC5nZW5pLm5ldIIJAJvH3dZoB97bMEcGCCsGAQUFBwEBBDswOTA3\\nBhRpg8yTgKiYzKjHvbGngICqrteKG4YfaHR0cHM6Ly9jaC5nZW5pLm5ldC9jYWluZm8uaHRtbDBz\\nBgNVHREEbDBqgRJjaC1hZG1pbnNAZ2VuaS5uZXSGKXVybjpwdWJsaWNpZDpJRE4rY2guZ2VuaS5u\\nZXQrYXV0aG9yaXR5K21hhil1dWlkOjMxYzBmMDlmLTk1ZjctNDUxMC1hMzBiLWQ5M2RmMmJkMDJj\\nOTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBQUAA4GBAHgrtROjbfUT6HQCZCl1XuAEZQPse3/x\\nT2smC3LzAH4UUj3gEBq27VvxbqzazdBQCSeBEl2RUd+KoDzmhG5vBbAyHt8UE6s3P7Yx\\ngoNYTld0JHB5wq3XFRaaGbeVgo2AuK9S/Q3whzMTRW21a58tLP5zwKGzX3oyQQUT5J\\ncwv0z5NmJdAfk8Y=")
    public String geniKey;
    
    /**
     * Key alias in key store
     */
    @DocumentationExample("exogeni")
    public String geniKeyAlias;

    @XmlElement(name = "loginPubKey")
    public List<LoginKey0> loginPubKey;

    public String geniKeyPass;
    @XmlElement(name = "loginPriKey")
    public List<LoginKey0> loginPriKey;

}
