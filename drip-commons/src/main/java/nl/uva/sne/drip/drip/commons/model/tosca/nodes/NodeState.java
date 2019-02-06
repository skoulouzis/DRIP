/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.uva.sne.drip.drip.commons.model.tosca.nodes;

/**
 *
 * @author S. Koulouzis
 */
//http://docs.oasis-open.org/tosca/TOSCA-Simple-Profile-YAML/v1.0/os/TOSCA-Simple-Profile-YAML-v1.0-os.html#DEFN_TOSCA_VALUES_STATE
public enum NodeState {
    INITIAL,
    CREATING,
    CREATED,
    CONFIGURING,
    CONFIGURED,
    STARTING,
    STARTED,
    STOPPING,
    DELETING,
    ERROR;
}
