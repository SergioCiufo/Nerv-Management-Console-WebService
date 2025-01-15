package com.company.NervManagementConsoleREST.config;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

//NON STATICO MEGLIO PER I TEST
public class JpaUtil {
    private final String PERSISTENCE_UNIT_NAME = "NervManagementConsole";
    private EntityManagerFactory entityManagerFactory;

    public JpaUtil() {
        try {
            entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ExceptionInInitializerError(e);
        }
    }

    //metodo per ottenere EntityManagerFactory
    public EntityManagerFactory getEntityManagerFactory() {
        return entityManagerFactory;
    }

    //metodo per ottenere EntityManagerHandler e quindi fare tutto closeable
    public EntityManagerHandler getEntityManager() {
        return new EntityManagerHandler(entityManagerFactory.createEntityManager());
    }

    //metodo per chiudere EntityManagerFactory
    public void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
