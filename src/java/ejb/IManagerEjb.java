/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ander
 */
@Local
public interface IManagerEjb {
    
    public void setManager(Manager manager) throws CreateException;
    
    public List<Manager> getManagers() throws ReadException;

    public Manager getManager(String email, String password) throws ReadException;
    
}
