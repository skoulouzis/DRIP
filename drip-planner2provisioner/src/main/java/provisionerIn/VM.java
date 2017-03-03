package provisionerIn;


import java.util.ArrayList;




public class VM {
	
	
	public String name;
	public String type;
	public String nodeType;
	public String OStype;
	
	//Currently, the SIDE subsystem uses this field for GUI.
	public String script;
	
	public String domain;
	
	
	public String installation;
	public String clusterType;
	
	
	
	//The role of this node in docker cluster. 
	//The possible value can only be "null", "slave" and "master". 
	//This is not case sensitive. 
	public String role;
	
	//The name of the docker in repository, which can be "null". 
	public String dockers;
	
	//Do not need to be the same with the node name any more.
	//The initial value should be "null", which means the public is not determined. 
	//When the status of the sub-topology is stopped, deleted or failed, this field should also be "null".
	public String public_address;
	
	public ArrayList<Eth> ethernet_port;
	

	
}
