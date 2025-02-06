/*
 * Paquete que contiene los servicios REST para gestionar los grupos de animales en el sistema de gestión de granja.
 * <p>
 * Este paquete expone una serie de servicios a través de la clase {@link AnimalGroupFacadeREST} que permiten realizar operaciones 
 * CRUD sobre los grupos de animales. Las operaciones incluyen la creación, actualización, eliminación y recuperación de grupos de animales,
 * tanto de manera general como filtrada por administrador.
 * </p>
 * <p>
 * Los servicios se comunican con la capa de negocio a través de la interfaz {@link IAnimalGroupEjb} y manejan excepciones específicas
 * que pueden ocurrir durante las operaciones de lectura, escritura, actualización y eliminación.
 * </p>
 * 
 * @author Ander
 */
package services.animalGroup;
