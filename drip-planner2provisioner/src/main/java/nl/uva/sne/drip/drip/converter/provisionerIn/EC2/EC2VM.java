package nl.uva.sne.drip.drip.converter.provisionerIn.EC2;

import nl.uva.sne.drip.drip.converter.provisionerIn.VM;


public class EC2VM extends VM{

	//The unit is GigaByte and must be a positive integer. 
	//This field is only valid when this sub-topology is from EC2. 
	//The default size is 8.
	//The disk size of node in the ExoGENI is fixed.
	public String diskSize;
		
	//if IOPS < 1000, the disk type will be 'gp2'. if IOPS > 1000, the disk type will be 'io1'
	//default amount is 0.
	public String IOPS;
	
	//The following fields are used for deleting the sub-topology. 
	//Only the field of instanceId can be written to the response file.
	//All the fields should be written to the control file, which is not returned to user.
	public String vpcId;
	
	public String subnetId;
	
	public String securityGroupId;
	
	public String instanceId;
	
	public String volumeId;
	
	public String routeTableId;
	
	public String internetGatewayId;
	
	
	//This is the actual private address in EC2 subnet, 
	public String actualPrivateAddress;
	

}
