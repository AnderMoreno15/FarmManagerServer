/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author InigoFreire
 */
@Embeddable
public class ProductProviderId implements Serializable {

    private Long productId;
    private Long providerId;
}