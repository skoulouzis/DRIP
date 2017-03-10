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
package nl.uva.sne.drip.api.service;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import nl.uva.sne.drip.api.dao.ClusterCredentialsDao;
import nl.uva.sne.drip.api.exception.NotFoundException;
import nl.uva.sne.drip.api.rpc.DRIPCaller;
import nl.uva.sne.drip.api.rpc.DeployerCaller;
import nl.uva.sne.drip.api.v1.rest.DeployController;
import nl.uva.sne.drip.commons.v1.types.CloudCredentials;
import nl.uva.sne.drip.commons.v1.types.ClusterCredentials;
import nl.uva.sne.drip.commons.v1.types.DeployParameter;
import nl.uva.sne.drip.commons.v1.types.LoginKey;
import nl.uva.sne.drip.commons.v1.types.Message;
import nl.uva.sne.drip.commons.v1.types.MessageParameter;
import nl.uva.sne.drip.commons.v1.types.ProvisionInfo;
import nl.uva.sne.drip.commons.v1.types.User;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author S. Koulouzis
 */
@Service
@PreAuthorize("isAuthenticated()")
public class DeployClusterService {

    @Autowired
    private ClusterCredentialsDao dao;

    @Value("${message.broker.host}")
    private String messageBrokerHost;

    @Autowired
    private CloudCredentialsService cloudCredentialsService;

    @Autowired
    private ProvisionService provisionService;

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ClusterCredentials findOne(String id) {
        return dao.findOne(id);
    }

    @PostFilter("(filterObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public List<ClusterCredentials> findAll() {
        return dao.findAll();
    }

    @PostAuthorize("(returnObject.owner == authentication.name) or (hasRole('ROLE_ADMIN'))")
    public ClusterCredentials delete(String id) {
        ClusterCredentials cred = dao.findOne(id);
        dao.delete(cred);
        return cred;
    }

    public ClusterCredentials save(ClusterCredentials clusterCred) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String owner = user.getUsername();
        clusterCred.setOwner(owner);
        return dao.save(clusterCred);
    }

    public ClusterCredentials deployCluster(String provisionID, String clusterType) {
        try (DRIPCaller deployer = new DeployerCaller(messageBrokerHost);) {
            Message deployerInvokationMessage = buildDeployerMessage(provisionID, clusterType.toLowerCase());

            Message response = (deployer.call(deployerInvokationMessage));
//            Message response = generateFakeResponse();
            List<MessageParameter> params = response.getParameters();
            ClusterCredentials clusterCred = new ClusterCredentials();
            for (MessageParameter p : params) {
                String name = p.getName();
                if (name.equals("credential")) {
                    String value = p.getValue();
                    clusterCred.setKey(value);
                    save(clusterCred);
                    return clusterCred;
                }
            }

        } catch (IOException | TimeoutException | JSONException | InterruptedException ex) {
            Logger.getLogger(DeployController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private Message buildDeployerMessage(String provisionID, String clusterType) {
        ProvisionInfo pro = provisionService.findOne(provisionID);
        if (pro == null) {
            throw new NotFoundException();
        }
        String cloudConfID = pro.getCloudcloudCredentialsID();
        CloudCredentials cCred = cloudCredentialsService.findOne(cloudConfID);
        List<LoginKey> loginKeys = cCred.getLoginKeys();
        List<DeployParameter> deployParams = pro.getDeployParameters();
        List<MessageParameter> parameters = new ArrayList<>();
        for (DeployParameter dp : deployParams) {
            String cName = dp.getCloudCertificateName();
            MessageParameter messageParameter = new MessageParameter();
            messageParameter.setName("credential");
            messageParameter.setEncoding("UTF-8");
            String key = null;
            for (LoginKey lk : loginKeys) {
                String lkName = lk.getName();
                if (lkName == null) {
                    lkName = lk.getAttributes().get("domain_name");
                }
                if (lkName.equals(cName)) {
                    key = lk.getKey();
                    break;
                }
            }
            messageParameter.setValue(key);
            Map<String, String> attributes = new HashMap<>();
            attributes.put("IP", dp.getIP());
            attributes.put("role", dp.getRole());
            attributes.put("user", dp.getUser());
            messageParameter.setAttributes(attributes);
            parameters.add(messageParameter);
        }
        MessageParameter clusterTypeParameter = new MessageParameter();
        clusterTypeParameter.setName("cluster");
        clusterTypeParameter.setEncoding("UTF-8");
        clusterTypeParameter.setValue(clusterType);
        parameters.add(clusterTypeParameter);
        Message deployInvokationMessage = new Message();

        deployInvokationMessage.setParameters(parameters);
        deployInvokationMessage.setCreationDate(System.currentTimeMillis());
        return deployInvokationMessage;
    }

    private Message generateFakeResponse() throws IOException, TimeoutException, InterruptedException, JSONException {
        String strResponse = "{'creationDate': 1488453891505, 'parameters': "
                + "[{'url': 'null', 'attributes': 'null', 'name': 'credential', "
                + "'value': 'apiVersion: v1\\nclusters:\\n- cluster:\\n    "
                + "certificate-authority-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tL"
                + "S0tCk1JSUN5RENDQWJDZ0F3SUJBZ0lCQURBTkJna3Foa2lHOXcwQkFRc0ZBRE"
                + "FWTVJNd0VRWURWUVFERXdwcmRXSmwKY201bGRHVnpNQjRYRFRFM01ETXdNakV"
                + "4TWpNek9Gb1hEVEkzTURJeU9ERXhNak16T0Zvd0ZURVRNQkVHQTFVRQpBeE1L"
                + "YTNWaVpYSnVaWFJsY3pDQ0FTSXdEUVlKS29aSWh2Y05BUUVCQlFBRGdnRVBBR"
                + "ENDQVFvQ2dnRUJBTGV4CitSS2p4SjR3WXJacTc5bHU1dExCc0JZbWtCTi9xZl"
                + "AzUXRUeXV6RW5nWmFYaHpQWnNYbGlGK3VLNVVROEFVNmYKdDdFNzVVQWVtYlR"
                + "MeXdjeDZqYmlYOXJuMEsxVnJBaktpdCsycGR1dHFZN1N0UDB5K2sxRXd4aWNR"
                + "aFZaeE8yNgpxbkM4QXNFL0Y3U2lBYXhVeVo2emVSVU1VTGZXMTk0UXQ1YS8xZ"
                + "nhkcERCUHZOOGlNOEVKUWk1RXJrbkN5S2piCm5Wa0lEU2VIQk9hUW5yYjBrM2"
                + "1OOStBdGNrM1pPNU1oRFJGajF3VWFZNFdKbmIzd01yZUhlWmJJL1Y5NWhjMFk"
                + "KREhlNGs2eW1MbFE4NmhmWjZyMDNQWTVGVC81eE55ZDRCN1p2YURqdW5zWXNB"
                + "NU11UWFIdUQwM3p3QkNxTlRNMQpyY3NRUkk1dVI0ejFYUVFheUVVQ0F3RUFBY"
                + "U1qTUNFd0RnWURWUjBQQVFIL0JBUURBZ0trTUE4R0ExVWRFd0VCCi93UUZNQU"
                + "1CQWY4d0RRWUpLb1pJaHZjTkFRRUxCUUFEZ2dFQkFLYmFzMlRIa010YnVjZkJ"
                + "lS3FUVGVDaGdMOEcKN0JqZmwxSGtqRWFYZUtsS1BTZ3NYYlNLdXNiUjNHTjdM"
                + "SGtZd0NQa0FXT1hoSmV6andxUVFTdzVyMUdVbXAvRgpFaHBKOTByM2l3NUJ3M"
                + "nJxOUdRNytOb1lQNENQdXYwd0N3V0wrWXd3ejhFSzhvVi9vdGNxdWxYVWUwUUZ"
                + "lL2txCkN3VGJvS2JBRElXd2xoSDdDd09ZOHo3SnRjREZ2WEtRYnRlTGNyUTY5"
                + "MFYyTUQxeTZyeUdmUUhsaWtsRjA2Sk8KMElXSVhDam1NYXppaG1hNGVHQTRHO"
                + "XZ6R2lMMVlyZGIxVDdYbFd3Zm5iOTg1QmVUWEZvV1JjMUo3RjFvQ3BaVwp2OTN3cko5L01uUzZxN3p2NkdNaUE0Rm5VanZiUDB4OHhWVXpsYjVvVG5HenFycSt2MXJLQklGSlFXdz0KLS0tLS1FTkQgQ0VSVElGSUNBVEUtLS0tLQo=\\n    server: https://52.201.84.116:6443\\n  name: kubernetes\\ncontexts:\\n- context:\\n    cluster: kubernetes\\n    user: admin\\n  name: admin@kubernetes\\n- context:\\n    cluster: kubernetes\\n    user: kubelet\\n  name: kubelet@kubernetes\\ncurrent-context: admin@kubernetes\\nkind: Config\\npreferences: {}\\nusers:\\n- name: admin\\n  user:\\n    client-certificate-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUM0ekNDQWN1Z0F3SUJBZ0lJWlR0eVhaRWNRUTh3RFFZSktvWklodmNOQVFFTEJRQXdGVEVUTUJFR0ExVUUKQXhNS2EzVmlaWEp1WlhSbGN6QWVGdzB4TnpBek1ESXhNVEl6TXpoYUZ3MHhPREF6TURJeE1USXpOREJhTUJzeApHVEFYQmdOVkJBTVRFR3QxWW1WeWJtVjBaWE10WVdSdGFXNHdnZ0VpTUEwR0NTcUdTSWIzRFFFQkFRVUFBNElCCkR3QXdnZ0VLQW9JQkFRRFFkOWdJK0liTVhDWlB6d244cGZuK1Vxd0RldXRKZjY5YWFCNFI4ZjZsSHl0dVRyNTkKZWJoZm43Uk5Od0UyTjZmeUdpOXpMdXdrcllpdU1zK3MvcTlDNVBmUXZiWTNaR2xTQk1KWXhTTGx6eFZ0UW1xdApKb1djWTBucUgwa2xjVnlZaFNKaEtKU25MdGx5UHBNZ0lkSWZBM1VDNXY4RVVzTzd0bFU5eGFZc0dkdDRmUDloCllxdDRZQndjOFY1VjgzNm5nTlFmNDdKZXJlMXR4RDRNV3JvM2pOZnp2NE9MVS9qTjlxdTdLWGpXZGZmenJiblIKQ2l3b1lhQ094aEV4Z3pvYkQvT01XZm1EcktRZlJZV3E2aUlWSmgvL0FoZi9KVGR6K3NoV2NndG96Q21KZDFXWgoyaEJnMTIwNE8zNTFLemVvZnp4MjJZc012NDdjVldIVjZ1QWhBZ01CQUFHak1UQXZNQTRHQTFVZER3RUIvd1FFCkF3SUZvREFkQmdOVkhTVUVGakFVQmdnckJnRUZCUWNEQVFZSUt3WUJCUVVIQXdJd0RRWUpLb1pJaHZjTkFRRUwKQlFBRGdnRUJBTE41VzJVT05RT3psaFNpenJsdWVQRGkySWlEczhnWWhPdTQwWndVSXE5eG9GREpFWE5xUXpvbQpRbjU3Y2l1OGhKSTZSV1FkWTNHNkZRTTV2ZG5meHRHVkRmUEs4Yk03SzFvZUFxSkNURm5xV1RKMEdSbnplSkhvCk5FemNIazZZYkxlVlhjenVJTHIxaWx1dGhscTJHQTlVT3VSc3JTNy9yTU1lYi9tUGd3Z0NWZ1cvd0piOEZoS2wKdkFDMmExS3lycUVlNGFxYWJyV283UndHMTRzSHJoN3RuTko0c1k2ZVJIajE1RGRYT1BUSko0NHJXc1pCd3N3awprNS84WnF4WlR5WjhySXljbnZPU01sbmJMeXZudmN6dytHUEgwQ2tuT2VhMUk5SU9KL3BtMXlOdTJvTElKN1ZNCm5pdlZNY282cEVpVE5Bd3UwbFF5N1FzSUNiaFAwa3M9Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K\\n    client-key-data: LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFcEFJQkFBS0NBUUVBMEhmWUNQaUd6RndtVDg4Si9LWDUvbEtzQTNyclNYK3ZXbWdlRWZIK3BSOHJiazYrCmZYbTRYNSswVFRjQk5qZW44aG92Y3k3c0pLMklyakxQclA2dlF1VDMwTDIyTjJScFVnVENXTVVpNWM4VmJVSnEKclNhRm5HTko2aDlKSlhGY21JVWlZU2lVcHk3WmNqNlRJQ0hTSHdOMUF1Yi9CRkxEdTdaVlBjV21MQm5iZUh6LwpZV0tyZUdBY0hQRmVWZk4rcDREVUgrT3lYcTN0YmNRK0RGcTZONHpYODcrRGkxUDR6ZmFydXlsNDFuWDM4NjI1CjBRb3NLR0dnanNZUk1ZTTZHdy96akZuNWc2eWtIMFdGcXVvaUZTWWYvd0lYL3lVM2MvcklWbklMYU13cGlYZFYKbWRvUVlOZHRPRHQrZFNzM3FIODhkdG1MREwrTzNGVmgxZXJnSVFJREFRQUJBb0lCQVFDQzNQaHNpVFoxU3ROeAovaEhTMjQ0WExHWjJ1TTdDa0xqUEU5VHNodkRIVHMzQXJRVy9WbzE2MlpJaGRvbjRNdS9tZHJHYXFMWHNRRk13CnB3emZGL0dGR0o3SUZvOHdMb1ErekdZRHU1eXdqbEp4QitSWFZYdENOTUhOaWw0c2R3RWRiVldya0FIaEFQUFgKVjVpYjd4OHNBTXVQU3RxREtFRmVzSkxKK0N6Y09TeGxFTXlGYnRQVkZpOGlTRkpCQjFUV0RZa25BWXU0L3plNQpNeDBvVWFzZFlGdkdvV3grblpISEh0bVQwVWhQdUY5Rm5wamtlZkdpZ3FPdlZ3NlpSMWZiN0lPMTBxd1BaQ2NsCklvQjJHSE1lUFZWRzFOKytPRUlJWkM4eWY2bjJNejYrYXZyWFpVeE9IV2ZBQTYrZUhhODNXT1l1Ym5RK0ZkVG4KK25KNVFIUUJBb0dCQU5lU2VlekYrdjlMQnlBTmVhYjVDSFJxNVp3OHZrWm1aOHp4QVU1c25uRnJDSnpNeWJtbQo1cUxQWUNLdXJITW9ZaUJyT1k2UTVUTlViNjVNQWgwYTdWRjJFWjhHZlBoRmNEMEZsMjlhVjRmSUtRTFI3RTFJClFUemdjTHJkbVVhbUVDZE9oc1BMTWMvTkFUa1dqVEw5SVRFRytUUktFMk8yb0hZUWt6RmVYYWNSQW9HQkFQZVEKVG1HK3piRDdSNjhybjFCYmNKRUpKSVVZT0podSs5K3JhZW1hbllYcFN4bVV2c0xNV0ZObXpSZGJVdlRNUS95VgpMNmVDb202MUFSMnV2NjlqVmc2b003a0tvOUhEakh3bVZzK01COXJacXUyYjd6N0NsdEQ2NVcyN2RneXUxdHdjCnd2Wk5BRXJQcWNNazY0MkswczlxeWRNUC94eVVqeUxFQytaclZrZ1JBb0dBTWRtQ2JsbGQ4SGsySFpoZ0lGZUkKTi91Y095UGswRHpRRmhsSk53eHhHME1vQzdKamw1WGIwWnhvMjd4T3pwWnhFcFFaRERtL2RyNDQwVEpzYnJTUwpXbnNXR2hNVEJRcHhxUHJKQ1F2NEg4TU9rTU5naElLSDYzN3ZNcGNaNGJkM0ZzL1c1Q0h4MDk3UlFKVlJjNUFMCmc5M2I5TmFJWTArUkRaeEpuV24zK0RFQ2dZQVRncmI2c3h2bzM5VGxkRTJvK3B2amdsSHpmMVl1aFVpcWR3cncKMHpPbzh4cnRHREQwajBCQ3ltNU5jZkFXeE9aeXlKc3AyM3pMdHM0d3RhMzd0NXQzcWoveFRHcGV4TWVVblR6bwpQSVA5OW51bklFdjVxVUpUbEFKeTVIeElRWnREVzZ6dDFtRzRnNmRBYnU5MWgwUlgvbldMZ2M0OFJXVUdlMmJvCnNQcnEwUUtCZ1FERW9BK0pqUUZ6Q3gxNFhhRGh4RkNYRjdXZnpsei9xejMzb0ZPdWxFTkdTZ2pPV2RMR2VyaWEKeGFqclZaT3dLTjRNd1NMNXEyYWJIczZtdC9KTjRONDlIazZqL0lnRTZKbFhMeHZ2TmxuK0kyYTB1RU5BVFVEKwp5elQ5dEQ4K3JaZ2hJR2oyd0dmekN3VCswUHFwNGM4WnFnbVg0a0hucE9oUkNObUlQVEZKSGc9PQotLS0tLUVORCBSU0EgUFJJVkFURSBLRVktLS0tLQo=\\n- name: kubelet\\n  user:\\n    client-certificate-data: LS0tLS1CRUdJTiBDRVJUSUZJQ0FURS0tLS0tCk1JSUM0ekNDQWN1Z0F3SUJBZ0lJTkFhdmdhbkFSL2N3RFFZSktvWklodmNOQVFFTEJRQXdGVEVUTUJFR0ExVUUKQXhNS2EzVmlaWEp1WlhSbGN6QWVGdzB4TnpBek1ESXhNVEl6TXpoYUZ3MHhPREF6TURJeE1USXpOREJhTUJzeApHVEFYQmdOVkJBTVRFR3QxWW1WeWJtVjBaWE10WVdSdGFXNHdnZ0VpTUEwR0NTcUdTSWIzRFFFQkFRVUFBNElCCkR3QXdnZ0VLQW9JQkFRRGZja0tOYkdWSmgzUlVSaFJhQWY5WUVmV1B3OWpkQmZETHlSdDJXWlZZeFY4ci91bEkKeWVWdzRPeHc0bmR1NXNBTE84b1c4NGM0Sm92cUJaN0ZLK0hkMkpLTzRsWVRCdmhPcEpCS0tCbUhLamgrRFZkegpjQ0NKS0FOUXlNLzZhcS9JTjZyZ0J4NXoreVJyblVhd1Jad0ZHZ3NLQzF5dURDMzNodTFhWmNFelNmc3JibC92CkcvNGVLWnhrSUV6Qzc3N0VnRHBvQTJWR2pCNUIwd001dzlpU2ZCVFQvNHFBc3psM2MwMEIrVnBBeFRQV3plK0sKZmNEeVJZQ3lEWmJmKytHdTRkVzRxaUUrcUVBYUxaK2R2MjUvNG5WWGM1b0xycVZnNEM1bWpPU1A5THkxVUJGMwoyQXMxSXVvNVdBU2lCY1JmQ2ZYaGpYUlVreG1IWVBVaVRRRXhBZ01CQUFHak1UQXZNQTRHQTFVZER3RUIvd1FFCkF3SUZvREFkQmdOVkhTVUVGakFVQmdnckJnRUZCUWNEQVFZSUt3WUJCUVVIQXdJd0RRWUpLb1pJaHZjTkFRRUwKQlFBRGdnRUJBRCtUSGZUVEZkOVpoMEhuQklOWkxmdXBiWXFDQ0NQQUJ4YStFMXY3Z011Q1dwUFBRL1VqWlpsZApsU1ZIL2dqd1FVb3hGMmtKY2ZOVHJTNHRJSTFhMEIyZVByRzNIdVNaQ0MxYVVLeUFhL2NXcFFiVDZaR1lyV3Q4CkJ6bWFVaFUvTVVhNXU4ckF4VlorYm5iZmtnTzdjaStPcDl1Z1dmMlpPS2E1YldPZ3VCMVNhNFJ5ZkJpRk5JRnIKZndEbXZDam51UloxbDU0N1FVNm5Pd2lFenVqQ1RNMytjYlBoejk0K054cFJKSFYyVGlVL3ZMeXRGZmNtdTFrRgpsWURiZkUwSUptK0xiL2wyTEhGSnQ1d1d0emEyZit2RHEzaGV6R1o4THRBeEM4eGgzelpvR0FScUh0bjV5V0k4ClpLOWFIcjE4cUVadHZyVlhWYzBseC9tbTB4N2ZrTGs9Ci0tLS0tRU5EIENFUlRJRklDQVRFLS0tLS0K\\n    client-key-data: LS0tLS1CRUdJTiBSU0EgUFJJVkFURSBLRVktLS0tLQpNSUlFb3dJQkFBS0NBUUVBMzNKQ2pXeGxTWWQwVkVZVVdnSC9XQkgxajhQWTNRWHd5OGtiZGxtVldNVmZLLzdwClNNbmxjT0RzY09KM2J1YkFDenZLRnZPSE9DYUw2Z1dleFN2aDNkaVNqdUpXRXdiNFRxU1FTaWdaaHlvNGZnMVgKYzNBZ2lTZ0RVTWpQK21xdnlEZXE0QWNlYy9za2E1MUdzRVdjQlJvTENndGNyZ3d0OTRidFdtWEJNMG43SzI1Zgo3eHYrSGltY1pDQk13dSsreElBNmFBTmxSb3dlUWRNRE9jUFlrbndVMC8rS2dMTTVkM05OQWZsYVFNVXoxczN2CmluM0E4a1dBc2cyVzMvdmhydUhWdUtvaFBxaEFHaTJmbmI5dWYrSjFWM09hQzY2bFlPQXVab3prai9TOHRWQVIKZDlnTE5TTHFPVmdFb2dYRVh3bjE0WTEwVkpNWmgyRDFJazBCTVFJREFRQUJBb0lCQUc2bW9IcDBRVHhHVGI4bAo3UklkaHh0UDJYRFdKV0JlZnI0TDJ3T2luU2lXcE9pZWxWdXNUMmkwbWFIVkpCdlJQU3pTOE9Lb3VqOStKeTgrCmxUOUMyZGtJVkp0WlRoS2lFdml1MlowL2VsVEsvYXErWko0UDJxelJHY001am5TTnZIckR4bVNtWWRoQmQrbXEKdXNTejArMWExamlsSThJMDJkYUlCS1lOV2IxUmFvQ1JKWHFEaFZ6cDNkcE9XQmJVaE9RU2poTmZ2d2p2NjE0WAo4bndlRFNvQmVRSG0vKzU3Q2FwYU1xakpKKzVoSS9rbjNtbVhER2hKMyt1MTVPT2VjSjRORnU4SzY5aWNaQkROCklTbGFMcG40SVB5bk0vdUhBaHNyUTIveWxtdVdlNjl3NVFHTkVGY2JlWHhTL1FvUU91TkFBTnhqUit5MXlhRVIKZWtYMGdya0NnWUVBNXZscWZTSEdOZjhrTk00SVhzZCsvVU5DbkhJa0xlSUcwN0xxd1lDRUw0ektySGpKN0tuZwpJeWtXbHNyWDUzRlJGMXhnb3FTeGtYSkczbVFnbWtlL2FjQ0RMa0t4SThIaWVvbGlSUDk0TFN2REZlbGlOUTRSCnBiYjkwWm5GSHBsU1hMK3diZlRRY1o5RC9kTGxkbkVHR2pOTEJvZzJjZm1MY2prNjBvQ0tpc3NDZ1lFQTk2Z0sKSGY4NkxLUysvVkVPTFVSV1hWSkxqOTkwbFB6QTFBQUxLak5xUndFZzcrMEhVSlphLzJ0d1VFY1kvSjV4dmZYNApyN0U4VXBkbmZ5QjEweE5iMkc0Y20zaEsrTkpEQTNJMThDZ215Y2FLbUl5ZitYeHVIV0p3Vyt3MDBMaXNrbTJVCnYrZktwRU81eUJuTmxzZjNlQ29kSmo4NnJEZTlxUnpmQ1lsVStITUNnWUVBc01xS0V0dHVMdGhxNnl3a3hmOG4KR1UySlI5d3FDeUN0bnc2Y2w1MzZEN2RSVG5CcUJvY0VjNFU0c0JUMDYwY1dEYTU4blNFNGRSNm1WZ1VIMjI4OQplUUdNcHpVUEIxS1pPcmU3aXhxTDd1OUhOSmRkdjBSVWg2WW9ZMjJ5cnJnbWphMS9xcm92YmppOHZ5VFUxREt3CnZBODB4dnd0eUZGOHhlSStQdTk2bTc4Q2dZQnlteGpUZU9ENjkrSktaOUFmc3BnazU0aUFNL0JIWWcvNkMyc2YKUkZ2U0wvdUdEREp1WUtZTHZUUmtjZ0ptSlF5TjA5NFpyL015WlU1SHprVzRRQ1VvcXd3aUJqRHJwS0hPWG5RSApodzBzSXV6UVc0Tk1lUUNjS0luR2dEQ1F4bVZWL2Mvd1h0dXYySWVJeWlnUnRzZ1dtNmFScElFS1lXRXJlV2pkCmlPZ0JjUUtCZ0ZZeTJYMmRubHVnUUI5TDhDWVRYMDZ2Q3ZNM3BsOWIwZXN3MFcxTzljVGJEbmViYXRUdW1lUy8KMEtpUHh5b2tBS1pCM3NDTUl3ZC9ZbTdtbEdjRU9aNEhCLzJCWURHaTdUWFdyTjkwRmVGZFlPVVRXWEpQaHNwQwpQRXVET2hGSHBMZTN6Smxyck9ySG1Ebm5aOFdtWU4rdGZzNnVuVHVsSXZPQWFKd01xVlZ4Ci0tLS0tRU5EIFJTQSBQUklWQVRFIEtFWS0tLS0tCg==\\n', "
                + "'encoding': 'UTF-8'}]}";

        strResponse = strResponse.replaceAll("'null'", "null").replaceAll("\'", "\"").replaceAll(" ", "");

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);

        return mapper.readValue(strResponse, Message.class);
    }
}
