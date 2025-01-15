package com.company.NervManagementConsoleREST.web.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import com.company.NervManagementConsoleREST.dao.service.MemberServiceDao;
import com.company.NervManagementConsoleREST.model.Member;
import com.company.NervManagementConsoleREST.service.LoginService;
import com.company.NervManagementConsoleREST.service.MemberService;

public class MemberServiceTest {
	private MemberService memberService;
	
	//oggeto mock
	private MemberServiceDao memberServiceDao = mock(MemberServiceDao.class);
	
	@BeforeEach
	public void setup() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		try (MockedStatic<Persistence> mockedPersistence = Mockito.mockStatic(Persistence.class)) {
			memberService  = new MemberService();
	    }
		
		Field f = memberService.getClass().getDeclaredField("memberServiceDao");
		f.setAccessible(true);
		f.set(memberService, memberServiceDao);
	}
	
	@Test
	public void shouldReturnListMember_whenAllOk() throws Exception{
		//parameters
		List<Member> memberList = new ArrayList<Member>();
		
		//mocks
		doReturn(memberList).when(memberServiceDao).retrieveMembers();
		
		//test
		List<Member> result = memberService.retrieveMembers();
		
		//results
		verify(memberServiceDao).retrieveMembers();
		assertNotNull(result); //che result non sia nullo (così il test può andare avanti)
        assertEquals(memberList, result); //che corrisponda alla lista mockata
	}
	
	@Test
	public void shouldReturnMember_whenAllOk() throws Exception{
		//parameters
		Member member = new Member();
		int idMember = 1;
		
		//mocks
		doReturn(member).when(memberServiceDao).retrieveByMemberId(idMember);
		
		//test
		Member result = memberService.retrieveByMemberId(idMember);
		
		//verify
		verify(memberServiceDao).retrieveByMemberId(idMember);
		assertNotNull(result);
		assertEquals(member, result);
	}
}
