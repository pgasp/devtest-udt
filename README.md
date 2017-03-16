## DevTest As Code

This project provide simple annotations that can be use  in your Junit Test to deploy. This annotation help to embde your Virtual Service in your source code. By this way, make your application readiness to continous integration testing without any constrains
These anotations use DevTest Rest API and deploy your virtual services based on request/response pairs. 
with This annotation you could be integrate complexe test scenario from your junit tests.  
To run this project you should have DevTest Server up and running and pointing on it from DevTestVirtualServer annotation

## Code Example

{

@DevTestVirtualServer(deployServiceToVse = "VSE")
public class UserServiceTest {
	
	@Rule
	public VirtualServicesRule rules = new VirtualServicesRule();
	
	@DevTestVirtualService(serviceName = "UserServiceTest-EJB3UserControlBean", 
			port = 9080, basePath = "/itkoExamples/EJB3UserControlBean",
			rrpairsFolder = "UserServiceTest/getListUser/EJB3UserControlBean", 
			requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUser() {
		// Given

		// When
		User[] users = bankServices.getListUser();
		// Then
		printUsers(users);
		assertNotNull(users);
		assertEquals(9, users.length);
		
		User user=getUser("Admin", users);
		assertNotNull(user);
		
		assertEquals("Admin", user.getLname());

	}
	
	
	@DevTestVirtualService(serviceName = "UserServiceTest-EJB3UserControlBean", 
			port = 9080, basePath = "/itkoExamples/EJB3UserControlBean",
			rrpairsFolder = "UserServiceTest/getListUser/getListUser", 
			requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
	@Test
	public void getListUser2() {
		// Given

		// When
		User[] users = bankServices.getListUser();
		// Then
		printUsers(users);
		assertNotNull(users);
		assertEquals(1, users.length);
		
		User user=getUser("toto", users);
		assertNotNull(user);
		
		assertEquals("toto", user.getLname());

	}
	

	private void printUsers(User[] users) {
	for (User user : users) {
		logger.info(user.getFname() +" "+user.getLname() +" "+ user.getLogin());
	}
		
	}


	/**
	 * @param name
	 * @param users
	 * @return
	 */
	private User getUser(String name,User[] users ){
		
		User result= null;
		for (User user : users) {
			if(name.equals(user.getLname())){
				result=user;
			}
				
		}
		return result;
	}
}
	

## Motivation

TODO

## Installation

TODO


## Contributors

- Pascal Gasp *Sr Architect Devops @ CA Technologies*
- Vincent Mazot *Sr Consultant Devops @ CA Technologies*
- Olivier Laplace  *Sr Presales Devops @ CA Technologies*

## License

TODO
