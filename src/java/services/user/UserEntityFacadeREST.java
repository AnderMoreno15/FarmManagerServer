/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services.user;

import javax.ejb.EJB;
import javax.ws.rs.Path;

/**
 *
 * @author Ander
 */
@Path("userentity")
public class UserEntityFacadeREST {

    @EJB
    private IUserEjb userEjb;

    /**
     * Creates a new instance of UserEntityFacadeREST
     */
    public UserEntityFacadeREST() {
    }

//    @POST
//    @Consumes(MediaType.APPLICATION_XML)
//    public void createAnimalGroup(UserEntity user) {
//        try {
//            userEjb.setUser(user);
//        } catch (CreateException ex) {
//            throw new InternalServerErrorException(ex.getMessage());        
//        }
//    }
//    
//    @GET
//    @Path("search/{email}")
//    @Produces(MediaType.APPLICATION_XML)
//    public UserEntity getUser(@PathParam("email") String email) {
//        try{
//           return (UserEntity) userEjb.getUser(email);
//        } catch (ReadException ex) {
//            throw new InternalServerErrorException(ex.getMessage());
//        }
//    }
    
}
