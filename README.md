## DevTest As Code

This project provide simple annotations that can be use  in your Junit Test to deploy. This annotation help to embde your Virtual Service in your source code. By this way, make your application readiness to continous integration testing without any constrains
These anotations use DevTest Rest API and deploy your virtual services based on request/response pairs. 
with This annotation you could be integrate complexe test scenario from your junit tests.  
To run this project you should have DevTest Server up and running and pointing on it from DevTestVirtualServer annotation

## Code Example

	
	@DevTestVirtualServer(registryHost="localhost" , deployServiceToVse = "VSE")

	public class UserServiceTest {
		static final Log logger=LogFactory.getLog(UserServiceTest.class);
		
		@Rule
		public VirtualServicesRule rules = new VirtualServicesRule();
	
		@DevTestVirtualService(serviceName = "UserServiceTest-EJB3UserControlBean", 
			port = 9080, basePath = "/itkoExamples/EJB3UserControlBean",
			rrpairsFolder = "UserServiceTest/getListUser/EJB3UserControlBean", 
			requestDataProtocol = {@Protocol(ProtocolType.DPH_SOAP) })
		@Test
		public void getListUser() {
		
		User[] users = bankServices.getListUser();
		// Then
		printUsers(users);
		assertNotNull(users);
		assertEquals(9, users.length);
		
		User user=getUser("Admin", users);
		assertNotNull(user);
		
		assertEquals("Admin", user.getLname());

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
