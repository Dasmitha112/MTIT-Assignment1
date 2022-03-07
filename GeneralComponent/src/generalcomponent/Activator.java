package generalcomponent;

import java.util.ArrayList;
import java.util.Scanner;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import servicevehiclepasspublisher.ServiceVehiclePass;
import servicevehiclepasspublisher.Student;

public class Activator implements BundleActivator {

	Scanner sc = new Scanner(System.in);
	Scanner sci = new Scanner(System.in);
	
	private static BundleContext context;
	private ServiceReference serviceReference;
	
	//private String ACK= "NO";
	private int mode = 0;
	
	ArrayList values = new ArrayList();

	public void start(BundleContext bundleContext) throws Exception {
		context = bundleContext;
		System.out.println("\n######################################  Welcome to SLIIT ######################################\n");
		
		//Define the objects
		ServiceVehiclePass serviceVehiclePass = null;

		try {
			// Create a service tracker to monitor Vehicle Pass Service.
			serviceReference = context.getServiceReference(ServiceVehiclePass.class.getName());
			serviceVehiclePass = (ServiceVehiclePass) context.getService(serviceReference);
							
		} catch (Exception e) {			
			System.out.println("Error Importing Modules");
		}
		
		while(true) {
			System.out.println("\n\t1 - Vehicle Pass Authentication" + "\n" +"\tEnter -1 to exit" + "\n");

			System.out.println("Enter the Service Mode :");
			try {
				mode = sci.nextInt();
			} catch (java.lang.NumberFormatException e) {
				System.out.println("Enter only a Number");
			}
			
			if(mode == 1){
				
				//Validation
				if(serviceVehiclePass == null) {
					System.out.println("\n Vehicle Pass Component Not found, Install the Component \n");
					break;
				}
				
				while(true) {
					//Vehicle Pass function
					System.out.println("\n###################  Welcome to SLIIT Vehicle Pass System  ###################\n");
					System.out.println("To begin,\nEnter your registration number, or to EXIT enter 'X': ");
					String ID = sc.nextLine();
					
					//Condition Block.
					if(ID.equals("X") || ID.equals("x")) {
						System.out.println("Exit...\n");
						break;
					} else if(ID != null || ID != "" || ID != " ") {
						values = serviceVehiclePass.publishVehiclePassService(ID);
						if(values.get(0) == "Unauthorized user!") {
							System.out.println("Do you want to register?(y/n) ");
				    		char choise = sc.nextLine().charAt(0); 

				    		if(choise == 'y') {
				    			System.out.println("\nEnter your name: ");
				    			String name = sc.nextLine();
				    			System.out.println("Enter your street: ");
				    			String street = sc.nextLine();
				    			System.out.println("Enter your age: ");
				    			Integer age = sci.nextInt();
				    			
				    			Student st = new Student(ID, name, street, age);
				    			
				    			System.out.println("\nUser successfully added!");
				    			
				    			System.out.println("\nID : "+ st.getID() + "\n" + "Name : "+ st.getName() +  "\n" + "Street : "+ st.getStreet() + "\n" + "Age : "+ st.getAge());
				    			System.out.println("\nThank you!\nHave a good day!!\n");
				    			
				    			
				    		} else {
				    			System.out.println("\nThank you!\nHave a good day!!\n");
				    			//break;
				    		}
				    			
						} else if(values.get(0) == "Authorized user!"){
							System.out.println("\nAuthorized user!");
							System.out.println("\nID : "+ values.get(1) + "\n" + "Name : "+ values.get(2) +  "\n" + "Street : "+ values.get(3) +  "\n" +  "Age : " + values.get(4));
			    			System.out.println("\nThank you!\nHave a good day!!\n");
			    			
						}
					}
					
					System.out.println("\nDo you want to continue? (YES/NO)");
					String ACK = sc.nextLine();
					
					if(ACK.equalsIgnoreCase("NO")) {
						break;
					}
				}	
				
			} else if(mode == -1) {
				break;
			} else {
				System.out.println("\nMode Error Enter the Correct Mode\n");
			}
		}
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
