package com.developers.solve.controller;

import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.requestDTO.SaveAttachedDto;
import com.developers.solve.problem.requestDTO.UpdatedAttachedDto;
import com.developers.solve.problem.responseDTO.*;
import com.developers.solve.problem.service.ProblemService;
import com.developers.solve.solution.dto.SolutionRequest;
import com.developers.solve.solution.service.SolutionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
//@WebMvcTest(ProblemController.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
//@WebMvcTest(DevelopersSolveApplication.class)
public class ProblemControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProblemService problemService;
    @MockBean
    private SolutionService solutionService;

    ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void testS3update() throws Exception {
        // given
        List<String> pathList = Arrays.asList("path1", "path2", "path3");
        Long problemId = 1L;
        UpdatedAttachedDto updatedAttachedDto = UpdatedAttachedDto.builder()
                .pathname(pathList)
                .problemId(problemId)
                .build();
        String expectedResponse = "Success Updated Attached File";
        given(problemService.UpdateAttached(updatedAttachedDto)).willReturn(expectedResponse);

        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/s3/update") //perform메소드는 인자로 RequestBuilder를 인터페이스를 받는다.
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(updatedAttachedDto)))
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("s3/update",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .andExpect(MockMvcResultMatchers.status().isOk());
//                .andExpect(content().string(equalTo(expectedResponse))); //ResultMatcher -> RequestMatcher
        //MockMvcMatcher는 Http응답메시지를 검증할수 잇는 기능들을 제공하고
        //ResultActions는 서버에서 처리한 Http응답 메시지의 각속송에 접근할수 잇는 메서드제공

        // then
        verify(problemService, times(1)).UpdateAttached(updatedAttachedDto);
    }


    @Test
    public void s3Upload() throws Exception{
        //given
        List<String> pathList = Arrays.asList("path1", "path2", "path3");
        Long problemId = 1L;
        SaveAttachedDto saveAttachedDto = SaveAttachedDto.builder()
                .pathname(pathList)
                .problemId(problemId)
                .build();

        String expectedResponse = "Success Updated Attached File";
        given(problemService.uploadAttached(saveAttachedDto)).willReturn(expectedResponse);

        //when
        //요청을 전송하는 역할을 합니다.
        //결과로 ResultActions 객체를 받으며, ResultActions 객체는 리턴 값을 검증하고 확인할 수 있는 andExcpect() 메소드를 제공해줍니다.
        mockMvc.perform(post("/api/s3/upload") //perform메소드는 인자로 RequestBuilder를 인터페이스를 받는다.
                        .contentType(MediaType.APPLICATION_JSON)
                        //ObjectMapper 클래스의 writeValueAsString, writeValueAsBytes는
                        // Java 오브젝트로 부터 JSON을 만들고 이를 문자열 혹은 Byte 배열로 반환한다.
                        .content(new ObjectMapper().writeValueAsString(saveAttachedDto)))
                //MockMvcMatcher는 Http응답메시지를 검증할수 잇는 기능들을 제공하고
                //ResultActions는 서버에서 처리한 Http응답 메시지의 각속송에 접근할수 잇는 메서드제공
                .andDo(MockMvcResultHandlers.print())
                .andDo(document("s3/uplooad",
                        Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
                        Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .andExpect(status().isOk());
        //then
        //verify: 객체 호출이 제대로 이루어졌는지 확인
        verify(problemService, times(1)).uploadAttached(saveAttachedDto);
    }

    @Test
    public void testSearchProblem() throws Exception {
        //given
        String search = "spring";
        List<String> hash = Arrays.asList("Cs", "java", "good");
        given(problemService.getListWithSearch(search)).willReturn(List.of(ProblemSortResponseDTO.builder()
                .problemId(1L)
                .type("type")
                .writer("writer")
                .title("title")
                .content("content")
                .answer("answer")
                .level("level")
                .tag("tag")
                .views(0L)
                .likes(0L)
                .createdTime(LocalDateTime.now())
                .hashTag(hash)
                .build()));
        //when
        ResultActions resultActions = mockMvc.perform(get("/api/problem").param("search", search))
                .andExpect(status().isOk())
                .andDo(document("problem/likeSearch"
                        , relaxedQueryParameters(parameterWithName("search").description("search result")
                        )));
        //then
        verify(problemService, times(1)).getListWithSearch("spring");
    }
    @Test
    public void testDeleteProblem() throws Exception{
        //given
        Long problemId = 10L;

        String expected = "Complete Delete";
        given(problemService.deleteProblem(problemId)).willReturn(expected);

        //when
        mockMvc.perform(delete("/api/problem/{problemId}",problemId
                        ,Preprocessors.preprocessRequest(Preprocessors.prettyPrint())
                        ,Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .andExpect(status().isOk())
                .andDo(document("problem/deleteProblem", pathParameters(
                        parameterWithName("problemId").description("Delete problemID")
                )));

        //then
        verify(problemService,times(1)).deleteProblem(problemId);
    }

    @Test
    public void testUpdateProblem() throws Exception {
        //given
        List <String> tag = Arrays.asList("ttag","test");
        List <String> answer = Arrays.asList("test1","test2","test3");
        ProblemUpdateRequestDto problemUpdateRequestDto = ProblemUpdateRequestDto.builder()
                .problemId(5L)
                .level("silver")
                .answer("yeop")
                .content("what is this")
                .title("updated title")
                .answerCandidate(answer)
                .writer("yeop")
                .type("choice")
                .hashTag(tag.toString())
                .build();

        given(problemService.update(problemUpdateRequestDto)).willReturn(ProblemUpdateResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("수정이 완료되었습니다.")
                .build());


        //when
        mockMvc.perform(patch("/api/problem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(problemUpdateRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(document("problem/updateProblem",
                        requestFields(
                                fieldWithPath("problemId").description("ProblemId of problem"),
                                fieldWithPath("title").description("Title of the problem"),
                                fieldWithPath("writer").description("Writer of the problem"),
                                fieldWithPath("answer").description("Answer of the problem"),
                                fieldWithPath("content").description("Content of the problem"),
                                fieldWithPath("type").description("Type of the problem"),
                                fieldWithPath("hashTag").description("Hashtags for the problem"),
                                fieldWithPath("level").description("level for the problem"),
                                fieldWithPath("answerCandidate").description("answer candidate for the problem")
                        )
                ));
        //then
        verify(problemService,times(1)).update(problemUpdateRequestDto);

    }  // https://m.boostcourse.org/web326/lecture/59389 참고해서 수정

    @Test
    public void testDetailProblem() throws Exception{

        Long problemId = 2L;
        String member = "lango";

        ProblemDetailResponseDto expectedResponse = ProblemDetailResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 조회 완료하였습니다.")
                .data(ProblemDetailDto.builder()
                        .problemId(problemId)
                        .type("CHOICE")
                        .writer("writer")
                        .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
                        .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
                        .answer("1")
                        .level("bronze")
                        .hashTag("")
                        .views(0L)
                        .likes(0L)
                        .solved(true)
                        .build())
                .build();

        given(problemService.problemDetail(problemId,member))
                .willReturn(expectedResponse);

        mockMvc.perform(get("/api/problem/{problemId}/{member}",problemId, member
                        ,Preprocessors.preprocessRequest(Preprocessors.prettyPrint())
                        ,Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
                .andExpect(status().isOk())
                .andDo(document("problem/detailProblem", pathParameters(
                        parameterWithName("problemId").description("Detail ProblemID"),
                        parameterWithName("member").description("Detail Member")
                )));

        verify(problemService,times(1)).problemDetail(problemId,member);


    }

    @Test
    public void registerProblem() throws Exception{

        ProblemSaveRequestDto saveRequestDto = ProblemSaveRequestDto.builder()
                .writer("yeop")
                .title("test")
                .content("Test111")
                .type("CHOICE")
                .level("silver")
                .answer("2")
                .tag("CS,TypeScript")
                .views(0L) // views 필드 추가
                .likes(0L) // likes 필드 추가
                .build();


        ProblemSaveResponseDto responseDto = ProblemSaveResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 문제를 생성하였습니다.")
                .build();

        given(problemService.save(saveRequestDto)).willReturn(responseDto);


        mockMvc.perform(post("/api/problem")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveRequestDto)))
                .andExpect(status().isOk())
                .andDo(document("problem/saveProblem"));

        //then
        verify(problemService,times(1)).save(saveRequestDto);
    }


//    @Test
//    void problemLikesadd() throws Exception{
//        Long problemId = 3L;
//
////        given(problemService.addLikesCntToRedis(problemId)).willReturn()
//
//        mockMvc.perform(get("/api/problem/likes/{problemId}",problemId
//                        ,Preprocessors.preprocessRequest(Preprocessors.prettyPrint())
//                        ,Preprocessors.preprocessResponse(Preprocessors.prettyPrint())))
//                .andExpect(status().isOk())
//                .andDo(document("problem/likesAdd",
//                        pathParameters(parameterWithName("problemId").description("좋아요 증가")))
//                );
//    }

    @Test //조건 검색 테스트
    public void sortProblem_shouldReturnSortedProblems() throws Exception {
        // Mock service response
        List<ProblemSortResponseDTO> mockResult = new ArrayList<>();
        List<String> hashtag = Arrays.asList("good","CS","kakao");
        mockResult.add(ProblemSortResponseDTO.builder()
                .problemId(1L)
                .type("type1")
                .writer("John Doe")
                .title("Title1")
                .content("Content1")
                .answer("Answer1")
                .level("level1")
                .tag("tag1")
                .views(10L)
                .likes(5L)
                .createdTime(LocalDateTime.now())
                .hashTag(hashtag)
                .build());
        mockResult.add(ProblemSortResponseDTO.builder()
                .problemId(2L)
                .type("type2")
                .writer("Jane Smith")
                .title("Title2")
                .content("Content2")
                .answer("Answer2")
                .level("level2")
                .tag("tag2")
                .views(20L)
                .likes(15L)
                .createdTime(LocalDateTime.now().minusDays(1))
                .hashTag(hashtag)
                .build());
        given(problemService.CreatedTimeSortList()).willReturn(mockResult);

        // Send request to controller
        MvcResult mvcResult = mockMvc.perform(get("/api/problem/list")
                        .param("order", "createdTime")
                        .param("writer", "John Doe"))
                .andExpect(status().isOk())
                .andDo(document("problem/search"))
                .andReturn();

        // Verify response
        String responseContent = mvcResult.getResponse().getContentAsString();
        SortResponseDTO responseDto = new ObjectMapper().readValue(responseContent, SortResponseDTO.class);
//        assertThat(responseDto.getData(), Matchers.equalTo(mockResult));
    }
    @Test
    public void solutionTest1() throws Exception {
        SolutionRequest request = SolutionRequest.builder()
                .solver("lango")
                .problemId(43L)
                .build();

        mockMvc.perform(post("/api/solution")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(document("problem/saveSolution",
                        requestFields(
                                fieldWithPath("id").description("문제푼 회원 ID"),
                                fieldWithPath("solver").description("문제푼 회원 이름"),
                                fieldWithPath("problemId").description("푼 문제 ID")
                        )
                ))
                .andReturn();
    }


}



