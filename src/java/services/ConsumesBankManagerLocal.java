/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.AnimalGroup;
import entities.Consumes;
import entities.Product;
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
public interface ConsumesBankManagerLocal {

    public void createConsume(Consumes consume) throws CreateException;
    public void updateConsume(Consumes consume) throws UpdateException;
    public void deleteConsume(Consumes consume) throws DeleteException;
    public List<Consumes> getAllConsumes() throws ReadException;
    public List<Consumes> findConsumesByProduct(Long productId) throws ReadException;
    public List<Consumes> findConsumesByAnimalGroup(Long animalGroupId) throws ReadException;
    public List<Consumes> getConsumesByDate(Date dateFrom, Date dateTo) throws ReadException;
    public List<Consumes> getConsumesByDateFrom(Date dateFrom) throws ReadException;
    public List<Consumes> getConsumesByDateTo(Date dateTo) throws ReadException;
}
    

