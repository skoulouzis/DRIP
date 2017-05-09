package nl.uva.sne.drip.drip.converter.provisionerIn;

import java.util.ArrayList;



public abstract class SubTopology {
	
	
	
	
	
	//Indicate a subnet that several can be put in.
	public ArrayList<Subnet> subnets;
	
	//Indicate how two VMs can be connected.
	public ArrayList<SubConnection> connections;
	

}
