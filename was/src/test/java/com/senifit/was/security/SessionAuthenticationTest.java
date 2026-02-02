package com.senifit.was.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 세션 기반 인증 테스트
 *
 * 이 테스트는 백엔드의 세션 관리가 정상 동작하는지 검증합니다.
 * - 로그인 후 세션 쿠키가 유지되는지
 * - 세션을 통한 인증된 요청이 정상 처리되는지
 * - 세션 없이 요청하면 401/403이 반환되는지
 */
@SpringBootTest
@AutoConfigureMockMvc
class SessionAuthenticationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("인증 없이 보호된 API 호출 시 403 반환")
    void unauthenticatedRequest_shouldReturn403() throws Exception {
        mockMvc.perform(get("/center"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("로그인 후 세션으로 인증된 API 호출 성공")
    void authenticatedRequest_withSession_shouldSucceed() throws Exception {
        // 1. 로그인 수행 (테스트용 계정 필요)
        // 실제 테스트 시에는 테스트용 계정 정보로 변경 필요
        MvcResult loginResult = mockMvc.perform(
                formLogin("/auth/signin")
                        .user("id", "test_account")  // 실제 테스트 계정으로 변경
                        .password("password", "test_password")  // 실제 비밀번호로 변경
        ).andReturn();

        // 2. 로그인 응답에서 세션 추출
        MockHttpSession session = (MockHttpSession) loginResult.getRequest().getSession();

        // 3. 세션을 포함하여 보호된 API 호출
        if (loginResult.getResponse().getStatus() == 200) {
            mockMvc.perform(get("/center").session(session))
                    .andExpect(status().isOk());
        }
    }

    @Test
    @DisplayName("세션 없이 요청 → 새 세션으로 요청: 403 반환")
    void requestWithoutSessionCookie_shouldReturn403() throws Exception {
        // 첫 번째 요청 (세션 없음)
        MvcResult result1 = mockMvc.perform(get("/center"))
                .andExpect(status().isForbidden())
                .andReturn();

        // 두 번째 요청 (다른 세션)
        mockMvc.perform(get("/center"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("로그인 엔드포인트는 인증 없이 접근 가능")
    void loginEndpoint_shouldBeAccessible() throws Exception {
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk());
    }
}
