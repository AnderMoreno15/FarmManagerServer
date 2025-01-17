/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import entities.Species;
import exceptions.ReadException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Aitziber
 */
@Local
public interface ISpeciesFacade {
    public List<Species> getAllSpecies() throws ReadException;
}
