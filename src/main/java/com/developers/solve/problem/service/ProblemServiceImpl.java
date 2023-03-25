package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.dto.SolutionSaveRequetDto;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.problem.repository.ProblemQueryDsl;
import com.developers.solve.solution.entity.Solution;
import com.developers.solve.solution.repository.SolutionRepository;
import com.querydsl.core.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.*;

@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemQueryDsl problemRepositoryImp;
    private final RestTemplate restTemplate;
    private final SolutionRepository solutionRepository;

//    private final CacheManager cacheManager;

//    @Override
//    public List<Problem> searchProblem(String condition){
//        List<Problem> result = null;
//        if(condition.contains(",")) {
//            List<String> tags = List.of(condition.split(","));
//            result = problemRepository.findByTags(tags);
//        }
//        else {
//            switch (condition) {
//                case "likes": case "views": case "times":
//                    result = problemRepository.findByConditionOrderByDesc(condition);
//                    break;
//                // 난이도(하, 중, 상) 별 검색
//                case "bronze": case "silver": case "gold":
//                    result = problemRepository.findByProblemLikeLevel(condition);
//                    break;
//                //유형(객관식, 주관식) 별 검색
//                case "choice": case "answer":
//                    result = problemRepository.findByProblmeLikeType(condition);
//                    // 푼 문제들
//                case "solved":
//                    result = problemRepository.findByProblemInSolution(condition);
//                    break;
//                // 안 푼 문제들
//                case "noneSolved":
//                    result = problemRepository.findByProblemNotInSolution(condition);
//                    break;
//                default:
//                    break;
//            }
//        }
//        return null;
//    }
//    @Override
//    public List<Problem> getProblemBySolved(String condition){
//        Long condition1 = Long.parseLong(condition);
//        return problemRepository.getProblemBySolved().
//};
    //Page<Entity> 에서 Page<DTO>변환은 단순 map을 통해서 바꿀수 있다???
    //condition = 0&likes&solved
    //condition = 1&likes&soleved&type

//    @Cacheable(cacheNames = "FirstSortProblem")
    @Override
    public List<ProblemSortResponseDTO> FirstSortProblem(String order,String types,String level,String solved,Long problemId){
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;
            if(!StringUtils.isNullOrEmpty(solved)){
                    result = this.problemRepositoryImp.getProblemSortedBySolved(order,types,level,problemId);
            } else{
                    result = this.problemRepositoryImp.getProblemSortedByLevel(order,types,level,problemId);
            }
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;
    }
//    @Cacheable(cacheNames = "NotFirstSortProblem")
//    @Override
//    public List<ProblemSortResponseDTO> NotFirstSortProblem(String order,String types,String level,String solved,Long problemId) {
////        List<ProblemSortResponseDTO> cacheResults = null;
//        List<Problem> result = null;
//        List<String> sort = List.of(condition.split(","));
//        List<ProblemSortResponseDTO> problemSortResponseDTO;
//        int i = sort.size();
//        if (sort.get(0).equals("1")) {
//            if (sort.get(i - 1).equals("solved")) {
////                cacheResults = cacheManager.getCache("FirstSortProblem").get("id", List.class);
////                if (cacheResults.size() < 5) {
////                    cacheResult = this.problemRepositoryImp.getProblemSortedBySolved(condition, problemId);
////                    cacheResults = cacheResult.stream().map(this::EntityToDto).collect(toList());
//
//                }
//            } else {
////                cacheResults = cacheManager.getCache("FirstSortProblem").get(sort.get(i - 1), List.class);
////                if (cacheResults.size() < 5) {
////                    cacheResult = this.problemRepositoryImp.getProblemSortedBySolved(condition, problemId);
////                    cacheResults = cacheResult.stream().map(this::EntityToDto).collect(toList());
//
//                }
//            }
//        } else {
//            if (sort.get(i - 1).equals("solved")) {
//                cacheResults = cacheManager.getCache("NotFirstSortProblem").get(sort.get(i - 1), List.class);
//                if (cacheResults.size() < 5) {
//                    cacheResult = this.problemRepositoryImp.getProblemSortedBySolved(condition, problemId);
//                    cacheResults = cacheResult.stream().map(this::EntityToDto).collect(toList());
//                }
//            } else {
//                cacheResults = cacheManager.getCache("NotFirstSortProblem").get(sort.get(i - 1), List.class);
//                if (cacheResults.size() < 5) {
//                    cacheResult = this.problemRepositoryImp.getProblemSortedBySolved(condition, problemId);
//                    cacheResults = cacheResult.stream().map(this::EntityToDto).collect(toList());
//                    //레포에서 들고 온거는 Entity to DTO 후 넘겨줘야함!!
//                }
//            }
//        }
//        problemSortResponseDTO = new ArrayList<>(cacheResults);
//        return problemSortResponseDTO;
//    }
    @Override
    public Long save(ProblemSaveRequestDto request) {
        /*
        * 문제 등록시 작성자의 정보를 알아야 하기 때문에 사용자 관리 서비스에 API 요청이 필요하다.
        * 이 때, 사용자 서비스에 작성자 유효성 검사 API 요청하여 요청에 대한 응답으로 작성자의 닉네임을 반환받는다.
        * 현재는 개발 초기 단계이니 임의의 값을 writer에 삽입하여 테스트하고 이후 사용자 관리 서비스가 개발되면 RestTemplate을 사용하자.
        * */
//        String url = "http://localhost:9000/member/" + request.getWriterId();
//        ResponseEntity<Map> res = restTemplate.exchange(url, HttpMethod.GET, null, Map.class);

        Problem problem = Problem.builder()
                .type(request.getType())
                .writer("writer")
//                .writer((String) res.getBody().get("nickname"))
                .title(request.getTitle())
                .content(request.getContent())
                .answer(request.getAnswer())
                .level(request.getLevel())
                .build();

        return problemRepository.save(problem).getProblemId();
    }

    @Override
    public Long saveSolution(SolutionSaveRequetDto request) {
        Problem problem = Problem.builder().problemId(request.getProblemId())
                .build();
        Solution solution = Solution.builder().
                userId(request.getUserId()).problemId(problem).build();
        return solutionRepository.save(solution).getSolutionId();
    }

//    @Override
//    public Long register(ProblemSaveRequestDto dto) {
//        Problem entity = dtoToEntity(dto);
//        problemRepository.save(entity);
//        return entity.getProblemId();
//    }

}
