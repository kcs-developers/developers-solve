package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.solution.repository.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final SolutionRepository solutionRepository;
    private final RestTemplate restTemplate;

    @Override
    public List<Problem> searchProblem(String condition){
        List<Problem> result = null;
        if(condition.contains(",")) {
            List<String> tags = List.of(condition.split(","));
            result = problemRepository.findByTags(tags);
        }
        else {
            switch (condition) {
                case "likes": case "views": case "times":
                    result = problemRepository.findByConditionOrderByDesc(condition);
                    break;
                // 난이도(하, 중, 상) 별 검색
                case "bronze": case "silver": case "gold":
                    result = problemRepository.findByProblemLikeLevel(condition);
                    break;
                //유형(객관식, 주관식) 별 검색
                case "choice": case "answer":
                    result = problemRepository.findByProblmeLikeType(condition);
                // 푼 문제들
                case "solved":
                    result = problemRepository.findByProblemInSolution(condition);
                    break;
                // 안 푼 문제들
                case "noneSolved":
                    result = problemRepository.findByProblemNotInSolution(condition);
                    break;
                default:
                    break;
            }
        }
        return null;
    }
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
}
