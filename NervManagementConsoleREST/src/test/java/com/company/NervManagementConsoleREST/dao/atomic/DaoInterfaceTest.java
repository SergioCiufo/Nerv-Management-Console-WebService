package com.company.NervManagementConsoleREST.dao.atomic;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.company.NervManagementConsoleREST.config.EntityManagerHandler;
import com.company.NervManagementConsoleREST.model.User;

public class DaoInterfaceTest {

    private class DaoImplementation implements DaoInterface<User> {
        // Implementazione vuota
    }

    private DaoInterface<User> daoInterface;
    private EntityManagerHandler entityManagerHandler;
    private EntityManager entityManager;

    @BeforeEach
    public void setup() {
        daoInterface = new DaoImplementation();
        entityManagerHandler = mock(EntityManagerHandler.class);
        entityManager = mock(EntityManager.class);
    }

    @Test
    public void shouldCreateUser_whenAllOk() throws SQLException {
        //parameter
        User user = new User();
        user.setUsername("username");

        //mock
        when(entityManagerHandler.getEntityManager()).thenReturn(entityManager); // Restituisci un mock di EntityManager

        //test
        daoInterface.create(user, entityManagerHandler);

        //result
        //nulla da verificare, è vuoto
    }

    @Test
    public void shouldRetrieveUsers_whenCalled() throws SQLException {
        //test
        List<User> users = daoInterface.retrieve(entityManagerHandler);

        //result
        assertNull(users);
    }

    @Test
    public void shouldUpdateUser_whenAllOk() throws SQLException {
        //parameter
        User user = new User();
        user.setUsername("username");

        //test
        daoInterface.update(user, entityManagerHandler);

        //result
        //nulla da verificare, è vuoto
    }

    @Test
    public void shouldDeleteUser_whenAllOk() throws SQLException {
        //parameter
        User user = new User();
        user.setUsername("username");

        //test
        daoInterface.delete(user, entityManagerHandler);

        //result
        //nulla da verificare, è vuoto
    }
}
