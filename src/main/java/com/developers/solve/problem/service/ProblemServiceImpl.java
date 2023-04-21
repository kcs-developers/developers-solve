package com.developers.solve.problem.service;

import com.developers.solve.problem.Exception.AccessException;
import com.developers.solve.problem.Exception.NullpointException;
import com.developers.solve.problem.entity.Attached;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.AttachedRepository;
import com.developers.solve.problem.repository.ProblemQueryDsl;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.problem.requestDTO.SaveAttachedDto;
import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.requestDTO.UpdatedAttachedDto;
import com.developers.solve.problem.responseDTO.*;
import com.developers.solve.solution.repository.SolutionRepository;
import com.querydsl.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemQueryDsl problemRepositoryImp;
    private final SolutionRepository solutionRepository;
    private final RedisTemplate<String, Object> redisTemplate;
    private final AttachedRepository attachedRepository;


    @Transactional
    @Override
    public void addViewCntToRedis(Long problemId) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = "problemId::" + problemId;
        String hashkey = "views";
        if (hashOperations.get(key, hashkey) == null) {
            hashOperations.put(key, hashkey, problemRepository.getViewsCnt(problemId));
            hashOperations.increment(key, hashkey, 1L);
            System.out.println(hashOperations.get(key, hashkey));
        } else {
            hashOperations.increment(key, hashkey, 1L);
            System.out.println(hashOperations.get(key, hashkey));
        }
    }

    @Transactional
    @Override
    public void addLikesCntToRedis(Long problemId) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = "problemId::" + problemId;
        String hashkey = "likes";
        if (hashOperations.get(key, hashkey) == null) {
            hashOperations.put(key, hashkey, problemRepository.getLikesCnt(problemId));
            hashOperations.increment(key, hashkey, 1L);
            System.out.println(hashOperations.get(key, hashkey));
        } else {
            hashOperations.increment(key, hashkey, 1L);
            System.out.println(hashOperations.get(key, hashkey));
        }
    }

    @Scheduled(fixedDelay = 1000L * 100L)
    @Transactional
    @Override
    public void deleteViewCntToRedis() {
        String hashkey = "views";
        Set<String> Rediskey = redisTemplate.keys("problemId*");
        Iterator<String> it = Rediskey.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long problemId = Long.parseLong(data.split("::")[1]);
            if (redisTemplate.opsForHash().get(data, hashkey) == null) {
                continue;
            }
            Long viewCnt = Long.parseLong((String.valueOf(redisTemplate.opsForHash().get(data, hashkey))));
            problemRepositoryImp.addViewCntFromRedis(problemId, viewCnt);
            redisTemplate.opsForHash().delete(data, hashkey);
        }
        System.out.println("views update complete");
    }

    @Scheduled(fixedDelay = 1000L * 60L)
    @Transactional
    @Override
    public void deleteLikesCntToRedis() {
        String hashkey = "likes";
        Set<String> RedisKey = redisTemplate.keys("problemId*");
        Iterator<String> it = RedisKey.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long problemId = Long.parseLong(data.split("::")[1]);
            if (redisTemplate.opsForHash().get(data, hashkey) == null) {
                continue;
            }
            Long likesCnt = Long.parseLong((String.valueOf(redisTemplate.opsForHash().get(data, hashkey))));
            problemRepositoryImp.addLikesCntFromRedis(problemId, likesCnt);
            redisTemplate.opsForHash().delete(data, hashkey);
        }
        System.out.println("likes update complete");
    }

    @Cacheable(cacheNames = "CreatedTimeSort")
    @Override
    public List<ProblemSortResponseDTO> CreatedTimeSortList() {
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;

        result = problemRepository.CreatedTimeSort();
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;

    }

    @CacheEvict(cacheNames = "CreatedTimeSort")
    @Override
    public void CreatedTimeSortCacheEvict() {
    }

    @Scheduled(fixedDelay = 1000L * 80L)
    @Override
    public void CreatedCacheUpdate() {
        CreatedTimeSortCacheEvict();
        CreatedTimeSortList();
    }
//    @Cacheable(cacheNames = "SubCreatTimeSort")
//    @Override
//    public void
//    @Cacheable(cacheNames = "ViewsSort")
//    @Override
//    public List<ProblemSortResponseDTO> ViewsSort(Long views){
//        List<Problem> result = null;
//        List<ProblemSortResponseDTO> problemSortResponseDTO;
//        result = problemRepository.ViewsSort(views);
//        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
//        return problemSortResponseDTO;
//    }

    @Override
    public List<ProblemSortResponseDTO> NotIncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer) {
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;
            result = problemRepositoryImp.getProblemSortedByNotSolved(order, types, level, hashtag, likes, views, createdTime);
        if (result == null){
            return Collections.emptyList();
        }
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;
    }

    @Override
    public List<ProblemSortResponseDTO> IncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer) {
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;
        if (!StringUtils.isNullOrEmpty(solved) && StringUtils.isNullOrEmpty(hashtag)) {
            result = problemRepositoryImp.getProblemSortedBySolved(order, types, level, hashtag, likes, views, createdTime, writer);
        }
        if (result == null){
            return Collections.emptyList();
        }
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;
    }

    @Override
    public ProblemSaveResponseDto save(ProblemSaveRequestDto request) {
        System.out.println(request.getAnswerCandidate());
        /*
         * 문제 등록시 작성자의 정보를 알아야 하기 때문에 사용자 관리 서비스에 API 요청이 필요하다.
         * 이 때, 사용자 서비스에 작성자 유효성 검사 API 요청하여 요청에 대한 응답으로 작성자의 닉네임을 반환받는다.
         * 현재는 개발 초기 단계이니 임의의 값을 writer에 삽입하여 테스트하고 이후 사용자 관리 서비스가 개발되면 RestTemplate을 사용하자.
         * */
//        String url = "http://192.168.99.13:9000/api/member/" + request.getId();
//        Map<String, String> res = restTemplate.getForObject(url, Map.class);
//        String result = res.get("memberName");
        String answerCandidate = "";
        if (request.getAnswerCandidate().isEmpty()){
            answerCandidate = "";
        } else{
        answerCandidate = request.getAnswerCandidate().stream().map(n -> String.valueOf(n)).collect(Collectors.joining(","));}
        Problem problem = Problem.builder()
                .type(request.getType())
                .writer(request.getWriter())
                .title(request.getTitle())
                .content(request.getContent())
                .likes(request.getLikes())
                .views(request.getViews())
                .answer(request.getAnswer())
                .level(request.getLevel())
                .answerCandidate(answerCandidate)
                .hashtag(request.getHashTag())
                .pathname(request.getPathname())
                .build();

        Optional<Long> problem2 = Optional.of(problemRepository.save(problem).getProblemId());
        problem2.orElseThrow(NullpointException::new);
        problemRepository.save(problem);

        return ProblemSaveResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 문제를 생성하였습니다.")
                .build();
    }

    @Override
    public ProblemDetailResponseDto problemDetail(Long problemId, String member) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(NullpointException::new);
        Boolean solved = solutionRepository.existsBySolverAndProblemIdProblemId(member, problemId);
        String Candidate = problemRepository.ListAnswerCandidate(problemId);
        List<String> answerCandidate;

        if (Candidate != null) {
            answerCandidate = Arrays.stream(Candidate.split(",")).toList();
        } else {
            answerCandidate = new ArrayList<>();
        }

        System.out.println("solved:" + solved);
        ProblemDetailDto dto = ProblemDetailDto.builder()
                .problemId(problem.getProblemId())
                .type(problem.getType())
                .writer(problem.getWriter())
                .title(problem.getTitle())
                .content(problem.getContent())
                .hashTag(problem.getHashtag())
                .answer(problem.getAnswer())
                .level(problem.getLevel())
                .likes(problem.getLikes())
                .views(problem.getViews())
                .answerCandidate(answerCandidate)
                .solved(solved)
                .pathname(problem.getPathname())
                .build();

        return ProblemDetailResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 조회 완료하였습니다.")
                .data(dto)
                .build();
    }

    //문제 수정
    @Transactional
    @Override
    public ProblemUpdateResponseDto update(ProblemUpdateRequestDto updateRequestDto) {
        //가장 먼저 수정권한이 있는지 확인한다.
        problemRepository.UpdateLicense(updateRequestDto.getProblemId(), updateRequestDto.getWriter()).orElseThrow(AccessException::new);
            //데이터 존재여부확인
            Problem problem = problemRepository.findById(updateRequestDto.getProblemId()).orElseThrow(NullPointerException::new);
                problem.updateProblem(updateRequestDto);
                problemRepository.save(problem);
                return ProblemUpdateResponseDto.builder()
                        .code(String.valueOf(HttpStatus.OK))
                        .msg("수정이 완료되었습니다.")
                        .build();
    }

    @Override
    @Transactional
    public String deleteProblem(Long problemId) {
        //해당 문제가 존재하는지 조회
        problemRepository.findById(problemId).orElseThrow(NullpointException::new);
        //데이터가 존재한다면 삭제
            problemRepository.deleteById(problemId);
        //문제가 지워짐 동시에 첨부 파일도 삭제 쿼리 실행
            attachedRepository.DeleteAttachedFile(problemId);
            String result = "Complete Delete";

        return result;
    }

    @Override
    public List<ProblemSortResponseDTO> getListWithSearch(String search) {
        // 검색 조건에 해당하는 문제가 없어 Null일 때 예외처리
        Optional<List> dto = Optional.of(problemRepository.findByTitleContaining(search.replaceAll("\\s+", " ")));
        dto.orElseThrow(NullpointException::new);
        //Null이 아닐때 실행되는 쿼리
        List<ProblemSortResponseDTO> dtoList = problemRepository.findByTitleContaining(search.replaceAll("\\s+", " "))
                .stream().map(this::EntityToDto).toList();
        return dtoList;
    }

    @Transactional
    @Override
    public String uploadAttached(SaveAttachedDto saveAttachedDto) {
        List<String> pathList = saveAttachedDto.getPathname();
        for (int i = 0; i < pathList.size(); i++) {
            String result = pathList.get(i);
            Attached attachedDto1 = Attached.builder()
                    .pathName(result)
                    .problemId(saveAttachedDto.getProblemId())
                    .build();
            attachedRepository.save(attachedDto1);
        }
        String response = "Attached Updated Success";
        return response;
    }

    @Transactional
    @Override
    public String UpdateAttached(UpdatedAttachedDto updatedAttachedDto) {
        String response = "";
        List<String> pathNames = attachedRepository.findByPathName(updatedAttachedDto.getProblemId()); //PathName다 찾아오기
        List<String> pathList = updatedAttachedDto.getPathname(); //pathName입력값
        if (pathList.isEmpty()) { //값이 비게 입력되면 삭제
            attachedRepository.DeleteAttachedFile(updatedAttachedDto.getProblemId());
            response = "Success Updated Attached File";
            return response;
        } else {
            for (int i = 0; i < pathList.size(); i++) {
                boolean isExists = false; //초기값 false설정
                for(String path: pathNames){
                    if(pathList.get(i).equals(path)){
                        isExists = true; //true이면 나가기 -> 실행하지 않는다.
                        break;
                    }
                }
                if(!isExists){ //존재하지 않으면
                    String result = pathList.get(i);
                    Attached attached1 = Attached.builder()
                            .pathName(result)
                            .problemId(updatedAttachedDto.getProblemId())
                            .build();
                    attachedRepository.save(attached1); // 값을 삽입한다.
                }
            }
            response = "Success Updated Attached File";
            return response;
        }
    }
}
