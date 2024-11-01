package com.javarush.jira.profile.internal.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.AuthUser;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.profile.ProfileTo;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static com.javarush.jira.common.util.JsonUtil.writeValue;
import static com.javarush.jira.login.internal.web.UserTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileRestController.REST_URL;
import static com.javarush.jira.profile.internal.web.ProfileTestData.*;
import static com.javarush.jira.profile.internal.web.ProfileTestData.USER_PROFILE_TO;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@Transactional
class ProfileRestControllerTest extends AbstractControllerTest {

    @Mock
    private ProfileRestController profileController;
    @Autowired
    private UserRepository userRepository;


    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(PROFILE_TO_MATCHER.contentJson(USER_PROFILE_TO));

    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getVerify()  {
        AuthUser authUser = new AuthUser(userRepository.getById(1L));
        ProfileTo profileTo = profileController.get(authUser);
        Mockito.doAnswer(invocationOnMock -> profileTo)
                        .when(profileController).get(authUser);

        Mockito.verify(profileController, times(1)).get(authUser);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getInvalidTo() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(ProfileTestData.getInvalidTo())))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUpdatedProfileTo() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andExpect(status().isNoContent());

        PROFILE_TO_MATCHER.assertMatch(profileTo1, getUpdatedTo());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getUpdatedProfile() throws Exception {
        perform(MockMvcRequestBuilders.put(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(getUpdatedTo())))
                .andExpect(status().isNoContent());

        PROFILE_MATCHER.assertMatch(ProfileTestData.getNew(), ProfileTestData.getUpdated(99L));
    }





}