package nl.uva.sne.drip.drip.converter.provisionerIn;

import java.util.ArrayList;


public abstract class VM {


    public String name;
    public String type;
    public String nodeType;
    public String OStype;


    //Currently, the SIDE subsystem uses this field for GUI.
    //This script defines for individual VM.
    //It's important to keep in mind that this is only the script path.
    //The real content in the field of 'v_scriptString'
    public String script;


    //The role of this node in docker cluster. 
    //The possible value can only be "null", "slave" and "master". 
    //This is not case sensitive. 
    public String role;

//    //The name of the docker in repository, which can be "null". 
//    public String dockers;
    
//    ////Identify the type of the docker cluster used.
//    public String clusterType;

    //Do not need to be the same with the node name any more.
    //The initial value should be "null", which means the public is not determined. 
    //When the status of the sub-topology is stopped, deleted or failed, this field should also be "null".
    public String publicAddress;

    public ArrayList<Eth> ethernetPort;


}
