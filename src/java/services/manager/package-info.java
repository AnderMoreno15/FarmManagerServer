/**
 * Paquete que contiene los servicios REST para gestionar los administradores del sistema de gestión de granja.
 * <p>
 * Este paquete expone una serie de servicios a través de la clase {@link ManagerFacadeREST} que permiten realizar operaciones 
 * CRUD sobre los administradores. Las operaciones incluyen la creación, actualización, eliminación y recuperación de administradores,
 * tanto de manera general como mediante búsquedas filtradas por correo electrónico y contraseña.
 * </p>
 * <p>
 * Los servicios interactúan con la capa de negocio a través de la interfaz {@link IManagerEjb} y manejan excepciones específicas
 * relacionadas con la creación, lectura, actualización y autenticación de administradores.
 * </p>
 * 
 * @author Ander
 */
package services.manager;
