/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ProviderEntity;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author InigoFreire
 */
@Local
public interface IProviderFacade {
    public List<ProviderEntity> getAllProviders() throws ReadException;
}
