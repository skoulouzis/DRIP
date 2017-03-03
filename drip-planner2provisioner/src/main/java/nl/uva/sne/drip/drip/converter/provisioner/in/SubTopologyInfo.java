package nl.uva.sne.drip.drip.converter.provisioner.in;


import com.fasterxml.jackson.annotation.JsonIgnore;


public class SubTopologyInfo {
	//The name of the topology. It is also the file name of the low level description.
	public String topology;
	
	//Currently, only support "EC2" and "ExoGENI"/"GENI". This is not case sensitive.
	public String cloudProvider;

	
	//Point to the detailed description of the sub-topology.
	@JsonIgnore
	public SubTopology subTopology;
	
	
	
}
