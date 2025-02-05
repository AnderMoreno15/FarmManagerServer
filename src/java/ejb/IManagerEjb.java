/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Manager;
import exceptions.CreateException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ander
 * @author Aitziber
 */
@Local
public interface IManagerEjb {
    
    public void setManager(Manager manager) throws CreateException;
    
    public List<Manager> getManagers() throws ReadException;

    public List<Manager> getManager(String email, String password) throws ReadException;
    
    public Manager getManagerByEmail(String email) throws ReadException;
    
    public void updateManager(Manager manager) throws UpdateException;
    
    public void resetPassword(Manager manager) throws UpdateException;
    
    public Manager signIn(String email, String password) throws ReadException;
    
    public void signUp(Manager manager) throws CreateException;
    
}
