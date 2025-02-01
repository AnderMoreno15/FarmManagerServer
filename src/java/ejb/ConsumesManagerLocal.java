/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.AnimalGroup;
import entities.Consumes;
import entities.ConsumesId;
import entities.ProductEntity;
import exceptions.CreateException;
import exceptions.DeleteException;
import exceptions.ReadException;
import exceptions.UpdateException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Pablo
 */
@Local
public interface ConsumesManagerLocal {

    public void createConsume(Consumes consume) throws CreateException;
    public void updateConsume(Consumes consume) throws UpdateException;
    public void deleteConsumeById(Long consumesId) throws DeleteException ;
    public List<Consumes> getAllConsumes() throws ReadException;
    public List<Consumes> findConsumesByProduct(String nameProduct) throws ReadException;
    public List<Consumes> findConsumesByAnimalGroup(String nameAnimalGroup) throws ReadException;
    public List<Consumes> getConsumesByDate(Date dateFrom, Date dateTo) throws ReadException;
    public List<Consumes> getConsumesByDateFrom(Date dateFrom) throws ReadException;
    public List<Consumes> getConsumesByDateTo(Date dateTo) throws ReadException;

    
}
    

