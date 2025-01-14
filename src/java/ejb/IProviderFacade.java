/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ProductEntity;
import entities.ProviderEntity;
import exceptions.CreateException;
import exceptions.ReadException;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author inifr
 */
@Local
public interface IProviderFacade {
    public void create(ProviderEntity provider) throws CreateException;
    public ProviderEntity getProviderById(Long id) throws ReadException;
}
