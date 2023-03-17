package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.ProblemSaveRequestDto;
import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemRepository;
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
    private final RestTemplate restTemplate;

    @Override
    public List<ProblemSortResponseDTO> sort1(String condition){
        switch (condition) {
            case "좋아요":
                return problemRepository.findProblemByLikesIs();
            case "조회수":
                problemRepository.finaAll();
            case "내가 푼 문제":
                problemRepository.finaAll();
            case "해시태그":
        }
        return problemRepository.finaAll(condition);
    }
    public String
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
                .tag(request.getTag())
                .build();

        return problemRepository.save(problem).getProblemId();
    }
}
