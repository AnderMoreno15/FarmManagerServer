/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author Aitziber
 * @author Ander
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(services.animal.AnimalREST.class);
        resources.add(services.animalGroup.AnimalGroupFacadeREST.class);
        resources.add(services.consume.ConsumesFacadeREST.class);
        resources.add(services.manager.ManagerFacadeREST.class);
        resources.add(services.product.ProductEntityFacadeREST.class);
        resources.add(services.provider.ProviderEntityFacadeREST.class);
        resources.add(services.species.SpeciesREST.class);
        resources.add(services.user.UserEntityFacadeREST.class);
    }
    
}
