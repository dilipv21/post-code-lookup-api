package au.com.postcode.postcodeapi.controller;

import au.com.postcode.postcodeapi.service.impl.PostCodeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PostCodeController.class)
@AutoConfigureRestDocs("build/generated-snippets")
public class PostCodeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostCodeService service;

    private RestDocumentationResultHandler document;

    @Before
    public void setup() throws Exception {

        document = MockMvcRestDocumentation.document("{method-name}",
                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()));
    }

    @Test
    public void retrieveSuburb_success_ok() throws Exception {
        given(service.getSuburbDetails(any(Integer.class))).willReturn("Melbourne");
        mockMvc.perform(get("/postcode/code/2000").contentType(MediaType.APPLICATION_JSON_UTF8).with(user("admin").password("pass")))
                .andExpect(status().isOk())
                .andDo(document);
    }


    @Test
    public void retrieveSuburb_success_authFailure() throws Exception {
        given(service.getSuburbDetails(any(Integer.class))).willReturn("2000");
        mockMvc.perform(get("/postcode/code/2000").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().is4xxClientError())
                .andDo(document);
    }


    @Test
    public void retrieveSuburb_success_badrequest() throws Exception {
        given(service.getSuburbDetails(any(Integer.class))).willReturn("STRING");
        mockMvc.perform(get("/postcode/code/string").contentType(MediaType.APPLICATION_JSON_UTF8).with(user("admin").password("pass")))
                .andExpect(status().is4xxClientError())
                .andDo(document);
    }


    //TODO: Same to be added to below endpoint.
    @Test
    public void retrievePostal() {
    }

    @Test
    public void saveSuburbDetails() {
    }
}