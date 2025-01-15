package com.company.NervManagementConsoleREST.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class UserTest {

    private User user = new User();

    @Test
    public void shouldInitializeUserFields_whenUsingParameterizedConstructor() {
        //parameters
        User user = new User(1, "name", "surname", "username", "password");
        List<Member> memberList = new ArrayList<Member>();
        
        //result
        assertEquals("name", user.getName());
        assertEquals("surname", user.getSurname());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void shouldSetAndGetFieldsCorrectly_whenUsingSettersAndGetters() {
        //parameters
        user.setName("name");
        user.setSurname("surname");
        user.setUsername("username");
        user.setPassword("password");

        //result
        assertEquals("name", user.getName());
        assertEquals("surname", user.getSurname());
        assertEquals("username", user.getUsername());
        assertEquals("password", user.getPassword());
    }

    @Test
    public void shouldConvertImageCorrectly_whenUsingBase64ConversionMethods() {
        //parameters
        String base64Image = "test";

        user.setImageFromBase64(base64Image);

        //test
        String imageBase64 = user.getImageAsBase64();

        //result
        assertNotNull(user.getImage());
        assertEquals(base64Image, imageBase64);
    }

    @Test
    public void shouldAddMemberToList_whenUsingSetMembers() {
        //parameters
        Member member = new Member();
        List<Member> members = new ArrayList<>();
        members.add(member);
        user.setMembers(members);

        //result
        assertEquals(1, user.getMembers().size());
        assertEquals(member, user.getMembers().get(0));
    }

    @Test
    public void shouldReturnTrueForEqualityAndMatchingHashCode_whenComparingSameUserObject() {
        //parameters
        User user = new User("name", "surname", "username", "password");
        user.setMembers(new ArrayList<>());

        //result
        assertTrue(user.equals(user));
        assertEquals(user.hashCode(), user.hashCode());
    }

    @Test
    public void shouldReturnCorrectStringRepresentation_whenUsingToString() {
        //parameters
        user = new User(1, "name", "surname", "username", "password");

        //test
        String expected = "User [idUser=1, name=name, surname=surname, username=username]";

        //result
        assertEquals(expected, user.toString());
    }
}