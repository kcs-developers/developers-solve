//package com.developers.solve.service;
//
//import com.developers.solve.problem.entity.Problem;
//import com.developers.solve.problem.repository.AttachedRepository;
//import com.developers.solve.problem.repository.ProblemQueryDsl;
//import com.developers.solve.problem.repository.ProblemRepository;
//import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
//import com.developers.solve.problem.requestDTO.ProblemUpdateRequestDto;
//import com.developers.solve.problem.requestDTO.SaveAttachedDto;
//import com.developers.solve.problem.requestDTO.UpdatedAttachedDto;
//import com.developers.solve.problem.responseDTO.*;
//import com.developers.solve.problem.service.ProblemService;
//import com.developers.solve.problem.service.ProblemServiceImpl;
//import com.developers.solve.solution.dto.SolutionRequest;
//import com.developers.solve.solution.dto.SolutionResponse;
//import com.developers.solve.solution.entity.Solution;
//import com.developers.solve.solution.repository.SolutionRepository;
//import com.developers.solve.solution.service.SolutionService;
//import com.developers.solve.solution.service.SolutionServiceImp;
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.cache.CacheManager;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.web.client.RestTemplate;
//import org.testng.Assert;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//import static org.mockito.ArgumentMatchers.any;
//
//
//import java.util.*;
//
//
//@ExtendWith(MockitoExtension.class)
//public class ProblemServiceTest {
//
//    @InjectMocks
//    private SolutionServiceImp solutionService;
//    @InjectMocks
//    private ProblemServiceImpl problemService;
//    @Mock
//    ProblemRepository problemRepository;
//    @Mock
//    SolutionRepository solutionRepository;
//    @Mock
//    AttachedRepository attachedRepository;
//
//    @Nested
//    @DisplayName("문제 생성")
//    class CreateProblem{
//        private String type;
//        private String writer;
//        private String title;
//        private String content;
//        private String answer;
//        private String level;
//        private String hashtag;
//        @BeforeEach
//        void setup(){
//            type = "answer";
//            writer = "Taeho";
//            title = "Project";
//            content = "Test CODE 끝내고 싶어요";
//            answer = "빨리끝내고 React 시작해야함";
//            level = "gold";
//            hashtag = "CS,BackEnd";
//
//        }
//        @Nested
//        @DisplayName("정상 케이스")
//        class SuccessCase {
//            @Test
//            @DisplayName("새로운 문제 작성")
//            void createProblemSuccessCase(){
//                List<String> answerCandidate = Arrays.asList("1", "2", "3", "4");
//
//                ProblemSaveRequestDto problemSaveRequestDto = ProblemSaveRequestDto.builder()
//                        .type(type)
//                        .level(level)
//                        .title(title)
//                        .writer(writer)
//                        .answer(answer)
//                        .content(content)
//                        .tag(hashtag)
//                        .AnswerCandidate(answerCandidate)
//                        .content(content)
//                        .build();
//                Problem problem = Problem.builder()
//                        .title(title)
//                        .writer(writer)
//                        .content(content)
//                        .answer(answer)
//                        .answerCandidate(String.valueOf(answerCandidate))
//                        .type(type)
//                        .level(level)
//                        .hashtag(hashtag)
//                        .build();
//
//                when(problemRepository.save(any(Problem.class))).thenReturn(problem);
//                ProblemSaveResponseDto result = problemService.save(problemSaveRequestDto);
//                assertThat(result.getData().getWriter()).isEqualTo("Taeho");
//            }
//        }
//        @Nested
//        @DisplayName("실패한 케이스")
//        class FailCase{
//            @Test
//            @DisplayName("반환된 게시물이 Null일 경우")
//            void FailCase1(){
//                ProblemSaveRequestDto problemSaveRequestDto = ProblemSaveRequestDto.builder()
//                        .type(type)
//                        .level(level)
//                        .title(title)
//                        .writer(writer)
//                        .answer(answer)
//                        .content(content)
//                        .tag(hashtag)
//                        .content(content)
//                        .build();
//                when(problemRepository.save(any(Problem.class))).thenReturn(null);
//                ProblemSaveResponseDto result = problemService.save(problemSaveRequestDto);
//                assertThat(result.getData()).isNull();
//            }
//        }
//    }
//    @Nested
//    @DisplayName("문제 수정")
//    class UpdateProblem{
//        private String type;
//        private String title;
//        private String content;
//        private String answer;
//        private String level;
//        private String hashtag;
//        private Long problemId;
//        private String writer;
//
//        @Nested
//        @DisplayName("정상 케이스")
//        class SuccessCase{
//            @BeforeEach
//            void setup(){
//                writer = "Taeho";
//                type = "choice";
//                title = "테스트 테스트 테스트";
//                problemId = 750L;
//                level = "silver";
//                answer = "이게 맞겠지";
//                content = "아 바꿔바꿔";
//                hashtag = "CS,BackEnd,Cloud";
//            }
//            @Test
//            @DisplayName("기존 문제 수정")
//            void updatedProblem(){
//                Problem problem = Problem.builder().
//                        writer(writer).
//                        title(title).
//                        problemId(problemId).
//                        content(content).
//                        answer(answer).
//                        type(type).
//                        level(level).
//                        hashtag(hashtag)
//                        .build();
//                when(problemRepository.save(any(Problem.class))).thenReturn(problem);
//                when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
//                ProblemUpdateRequestDto problemUpdateRequestDto = ProblemUpdateRequestDto.builder().
//                        problemId(750L).
//                        writer("Taeho").
//                        title("바뀐거").
//                        content("바뀐내용").
//                        type("answer").
//                        build();
//                problemService.update(problemUpdateRequestDto);
//                Optional<Problem> problem1 = problemRepository.findById(problemUpdateRequestDto.getProblemId());
//                assertThat(problem1.get().getTitle()).isEqualTo("바뀐거");
//            }
//        }
//        @Nested
//        @DisplayName("비정상 케이스")
//        class FailCase{
//            @BeforeEach
//            void setup(){
//                writer = "Taeho";
//                type = "choice";
//                title = "테스트 테스트 테스트";
//                problemId = 999L;
//                level = "silver";
//                answer = "이게 맞겠지";
//                content = "아 바꿔바꿔";
//                hashtag = "CS,BackEnd,Cloud";
//            }
//            @Test
//            @DisplayName("비정상 케이스 업데이트")
//            void updatedFailCase(){
//                Problem problem = Problem.builder().
//                        writer(writer).
//                        title(title).
//                        problemId(problemId).
//                        content(content).
//                        answer(answer).
//                        type(type).
//                        level(level).
//                        hashtag(hashtag)
//                        .build();
////                when(problemRepository.save(problem)).thenReturn(problem);
//                // when or thenReturn의 메소드가 반환하는 객체를 쓰지않으면 Mockito는 불필요한 테스트 코드로 인지하고 Stubbing Strict Error를 반환함.
//                problemRepository.save(problem);
//
//                ProblemUpdateRequestDto problemUpdateRequestDto = ProblemUpdateRequestDto.builder().
//                        problemId(9L).
//                        writer("Taeho").
//                        title("바뀐거").
//                        content("바뀐내용").
//                        type("answer").
//                        build();
//
//                 when(problemRepository.findById(problemUpdateRequestDto.getProblemId())).thenThrow(new NullPointerException(String.format("해당하는 문제 아이디가 없습니다.")));
////              problemService.update(problemUpdateRequestDto);
//////                System.out.println(x);
//                Exception exception = assertThrows(NullPointerException.class, () -> {problemService.update(problemUpdateRequestDto);});
//                assertThat(exception.getMessage()).isEqualTo(String.format("해당하는 문제 아이디가 없습니다."));
//            }
//        }
//    }
//
//    @Nested
//    @DisplayName("문제 삭제")
//    class DeleteProblem{
//        private Long problemId;
//        private String writer;
//        private String title;
//        private String content;
//
//        @BeforeEach
//        void setUp(){
//            problemId = 10L;
//            writer = "Taeho";
//            title = "삭제";
//            content = "이승기";
//;
//        }
//        @Test
//        @DisplayName("삭제 성공 케이스")
//        void DeleteSuccess(){
//            Problem problem = Problem.builder().
//                    writer(writer).
//                    title(title).
//                    problemId(problemId).
//                    content(content)
//                    .build();
////            when(problemRepository.save(any(Problem.class))).thenReturn(problem);
//            problemRepository.save(problem);
//            when(problemRepository.findById(problemId)).thenReturn(Optional.of(problem));
//
//            String result = problemService.deleteProblem(problemId);
//            assertThat(result).isEqualTo("Complete Delete");
//        }
//        @Nested
//        @DisplayName("비정상 케이스")
//        class FailCase{
//            private Long problemId;
//            private String writer;
//            private String title;
//            private String content;
//
//            @BeforeEach
//            void setUp(){
//                problemId = 10L;
//                writer = "Taeho";
//                title = "삭제";
//                content = "이승기";
//            }
//            @Test
//            @DisplayName("삭제 실패 케이스")
//            void DeleteFailCase(){
//                Problem problem = Problem.builder().
//                        writer(writer).
//                        title(title).
//                        problemId(problemId).
//                        content(content)
//                        .build();
//                problemRepository.save(problem);
//                Long FailData = 12L;
//
//                when(problemRepository.findById(FailData)).thenThrow(new NullPointerException(String.format("해당하는 문제 아이디가 없습니다.")));
//                Exception exception = assertThrows(NullPointerException.class, () -> {problemService.deleteProblem(FailData);});
//                assertThat(exception.getMessage()).isEqualTo(String.format("해당하는 문제 아이디가 없습니다."));
//
//
//            }
//        }
//    }
//    @Nested
//    @DisplayName("문제 상세 조회")
//    class DetailProblem{
//        private String type;
//        private String writer;
//        private String title;
//        private String content;
//        private String answer;
//        private String level;
//        private String hashtag;
//
//        @BeforeEach
//        void setUp(){
//            type = "answer";
//            writer = "Taeho";
//            title = "Project";
//            content = "Test CODE 끝내고 싶어요";
//            answer = "빨리끝내고 React 시작해야함";
//            level = "gold";
//            hashtag = "CS,BackEnd";
//        }
//        @Test
//        @DisplayName("상세 조회 성공 케이스")
//        void DetailProblemSuccess(){
//            Problem problem = Problem.builder().
//                    title(title).
//                    writer(writer).
//                    content(content).
//                    answer(answer).
//                    type(type).
//                    level(level).
//                    hashtag(hashtag)
//                    .build();
//        }
//
//
//    }
//
//
//
//    @Test
//    @DisplayName("Default(created_time) Test")
//    public SortResponseDTO DefaultTest() {
//        List<ProblemSortResponseDTO> response = problemService.NotIncludeSolvedSort(null, null, null, null, null, null, null, null, null);
//        SortResponseDTO sortResponseDTO = SortResponseDTO.builder().
//                msg("성공적으로 분류 하였습니다.").
//                status("code 200 Success").
//                data(response).
//                build();
//        return sortResponseDTO;
//    }
//
//    @Test
//    @DisplayName("LikesOrder Test")
//    public void OrderLikesTest() {
//        List response = problemService.NotIncludeSolvedSort("likes", null, null, null, null, null, null, null, null);
//        System.out.println(response);
//    }
//
//    @Test
//    @DisplayName("ViewsOrder Test")
//    public void OrderViewsTest() {
//        List<ProblemSortResponseDTO> response = problemService.NotIncludeSolvedSort("views", null, null, null, null, null, null, null, null);
//        System.out.println(response);
//    }
//    @Test
//    @DisplayName("Update License Check")
//    public void UpdateLicense(){
//        ProblemDetailResponseDto problemDetailResponseDto = problemService.problemDetail(2L,"lango");
//        System.out.println(problemDetailResponseDto);
//    }
//
//    @Test
//    @DisplayName("OrderAndType")
//    public void OrderAndType() {
//        List response = problemService.NotIncludeSolvedSort("views", "answer", null, null, null, null, null, null, null);
//        System.out.println(response);
//    }
//
//    @Test
//    @DisplayName("OrderAndTypeLevel")
//    public void OrderAndLevelType() {
//        List response = problemService.NotIncludeSolvedSort("views", null, "bronze", null, null, null, null, null, null);
//        System.out.println(response);
//    }
//    @Test
//    @DisplayName("OrderAndTypeLevelHash")
//    public void OrderAndLevelTypeHash(){
//        List response = problemService.NotIncludeSolvedSort("views", null, "bronze", null, null, null, null, null, null);
//        System.out.println(response);
//    }
//    @Test
//    @DisplayName("OrderAndTypeLevelSolved")
//    public void OrderAndLevelTypeSolved() {
//        List response = problemService.IncludeSolvedSort("createdTime", null, null, "True", null, null, null, null, "Taeho");
//        System.out.println(response);
//    }
//    @Test
//    @DisplayName("OrderAndTypeLevelSolvedHash")
//    public void OrderAndTypeLevelSolvedHash(){
//        List response = problemService.IncludeSolvedSort("createdTime", "answer", "bronze", "True","CS", null,null, null, "Taeho");
//        System.out.println(response);
//    }
//    @Test
//    @DisplayName("UploadAttached")
//    public void UploadAttached(){
//        List<String> pathname = new ArrayList<>();
//        pathname.add("hello.jpg");
//        pathname.add("hi.jpg");
//        SaveAttachedDto saveAttachedDto = SaveAttachedDto.builder().pathname(pathname).problemId(10L).build();
//        String result = problemService.uploadAttached(saveAttachedDto);
//        System.out.println(result);
//    }
//    @Test
//    @DisplayName("UpdateAttached")
//    public void UpdateAttached(){
//        List<String> pathname = new ArrayList<>();
//        pathname.add("hello.jpg");
//        pathname.add("adam.jpg");
//        UpdatedAttachedDto updatedAttachedDto = UpdatedAttachedDto.builder().pathname(pathname).problemId(10L).build();
//        String result = problemService.UpdateAttached(updatedAttachedDto);
//        System.out.println(result);
//    }
//    @DisplayName("문제 등록")
//    @Test
//    public void testSave(){
//        //given
//
////        String url = "http://localhost:9000/api/member/1";
////        Map<String,String> res = restTemplate.getForObject(url, Map.class);
////        String restResult = res.get("memberName");
////        System.out.println(restResult);
//
//        ProblemSaveRequestDto requestDto = ProblemSaveRequestDto.builder()
//                .type("CHOICE")
//                .writer("Taeho")
//                .title("Spring Boot의 장/단점 중 잘못된 것은 무엇일까요?")
//                .content("Spring Boot의 장/단점 중 잘못된 것은 무엇인지 4가지 중에 골라주세요.")
//                .answer("1")
//                .level("bronze")
//                .build();
//
//        //when
//        ProblemSaveResponseDto responseDto = problemService.save(requestDto);
//        Optional<Problem> result = problemRepository.findById(responseDto.getData().getProblemId());
//
//        //then
//        Assertions.assertEquals(responseDto.getData().getAnswer(), "1");
//        Assertions.assertTrue(result.isPresent());
//    }
//
//
//    @DisplayName("문제 상세 정보 조회 - 문제 푼 사용자")
//    @Test
//    public void problemDetailSolved() {
//        // given
////        String url = "http://localhost:9000/api/member/1";
////        Map<String,String> res = restTemplate.getForObject(url, Map.class);
////        String restResult = res.get("memberName");
//
//        ProblemSaveRequestDto saveRequestDto = ProblemSaveRequestDto.builder()
//                .writer("Taeho")
//                .title("test")
//                .content("Test111")
//                .type("CHOICE")
//                .level("silver")
//                .answer("2")
//                .build();
//
//
//        ProblemSaveResponseDto problemSaveResponseDto = problemService.save(saveRequestDto);
//
//        SolutionRequest request = SolutionRequest.builder()
//                .problemId(problemSaveResponseDto.getData().getProblemId())
//                .solver(saveRequestDto.getWriter())
//                .id(1L)
//                .build();
//        // when
//        SolutionResponse solutionResponse = solutionService.save(request);
//
//        ProblemDetailResponseDto responseDto = problemService.problemDetail(problemSaveResponseDto.getData().getProblemId(), "lango");
//
//        System.out.println("상세조회:" + responseDto);
//        System.out.println("답:" + solutionRepository.findById(responseDto.getData().getProblemId()));
//
//
//        // then
//        Optional<Solution> solution = solutionRepository.findById(solutionResponse.getSolutionId());
//        Assertions.assertNotNull(solution.get());
//        Assertions.assertTrue(responseDto.getData().getSolved());
//    }
//
//    @DisplayName("문제 상세 정보 조회 - 문제 안푼 사용자")
//    @Test
//    public void problemDetailNotSolved() {
//        // given
////        String url = "http://localhost:9000/api/member/1";
////        Map<String,String> res = restTemplate.getForObject(url, Map.class);
////        String restResult = res.get("memberName");
//
//        ProblemSaveRequestDto saveRequestDto = ProblemSaveRequestDto.builder()
//                .writer("Taeho")
//                .title("test")
//                .content("Test111")
//                .type("CHOICE")
//                .level("silver")
//                .answer("2")
//                .build();
//
//        ProblemSaveResponseDto problemSaveResponseDto = problemService.save(saveRequestDto);
//        SolutionRequest request = SolutionRequest.builder()
//                .problemId(problemSaveResponseDto.getData().getProblemId())
//                .solver("saveRequest")
//                .id(1L)
//                .build();
//        // when
//
//        SolutionResponse solutionResponse = solutionService.save(request);
//
//        ProblemDetailResponseDto responseDto = problemService.problemDetail(problemSaveResponseDto.getData().getProblemId(), "test123");
//
//        // then
//        Assertions.assertFalse(responseDto.getData().getSolved());
//        System.out.println(responseDto.getData().getSolved());
//    }
//
//
//
//    @DisplayName("문제 수정")
//    @Test
//    @Transactional
//    public void testUpdate(){
//        //given
//        ProblemUpdateRequestDto requestDto = ProblemUpdateRequestDto.builder()
//                .problemId(2L)
//                .writer("wjdduq")
//                .answer("3")
//                .content("goodgood")
//                .level("gold")
//                .title("13")
//                .hashTag("goog,goof")
//                .type("answer")
//                .build();
//        //when
//        ProblemUpdateResponseDto responseDto = problemService.update(requestDto);
//        //then
//        System.out.println(responseDto);
////        Assertions.assertEquals(responseDto.getData().getAnswer(), "3");
////        Assertions.assertEquals(responseDto.getData().getLevel(), "gold");
//    }
//
//    @DisplayName("문제 삭제")
//    @Test
//    @Transactional
//    public void testDeleteProblem() {
//        // given
//        Long problemId = 1L;
//        Problem target = problemRepository.findById(problemId)
//                .orElseThrow(() -> new IllegalArgumentException("삭제 대상이 없다."));
//
//        // when
//        String deletedProblemId = problemService.deleteProblem(target.getProblemId());
//
//        // then
//        System.out.println(deletedProblemId);
//    }
//
//    @DisplayName("문제 Like검색")
//    @Test
//    void problemLike(){
//        //given
//        String param= "spring boot";
//
//        //when
//        List<ProblemSortResponseDTO> problemList = problemService.getListWithSearch(param);
//
//        //then
//        System.out.println(param+"으로 찾은 문제 개수"+problemList.size());
//        System.out.println(param+"으로 찾은 문제 조회"+problemList);
//
//    }
//
////    @Test
////    @DisplayName("OrderAndTypeLevelHashTag")
////    public void OrderAndTypeLevelHashTag() {
////
////    }
//}
////    @Test
////    public void pdfdf(){
////        List<ProblemSortResponseDTO> list = problemService.FirstSortProblem("likes", null, null,null, null, null,100L,null, "lango");
////        System.out.println("+++++LIST: "+ i++ + list);
//        //캐시에서 결과가져오기
////        Cache cache = cacheManager.getCache("FirstSortProblem");
////        List<ProblemSortResponseDTO> list1 = cache.get();
////    @Test
////    public void tes1(){
////        List<ProblemSortResponseDTO> list = problemService.NotFirstSortProblem("likes,types", 10L);
////        System.out.println("List:" + list);
////    }
////    @Test
////    public void RedisTest() {
////        problemService.addViewCntToRedis(1L);
////    }
//
//
