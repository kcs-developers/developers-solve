package com.developers.solve.problem.service;

import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemQueryDsl;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.responseDTO.ProblemSortResponseDTO;
import com.developers.solve.solution.repository.SolutionRepository;
import com.querydsl.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemQueryDsl problemRepositoryImp;
    private final RestTemplate restTemplate;
    private final SolutionRepository solutionRepository;
    private final RedisTemplate<String,Object> redisTemplate;



    @Transactional
    @Override
    public void addViewCntToRedis(Long problemId) {
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = "problemId::" + problemId;
        String hashkey = "views";
        if (hashOperations.get(key, hashkey) == null) {
            hashOperations.put(key, hashkey,problemRepository.getViewsCnt(problemId));
            hashOperations.increment(key,hashkey,1L);
            System.out.println(hashOperations.get(key,hashkey));
        } else
        {hashOperations.increment(key,hashkey,1L);
            System.out.println(hashOperations.get(key, hashkey));}
    }
    @Transactional
    @Override
    public void addLikesCntToRedis(Long problemId){
        HashOperations<String, String, Object> hashOperations = redisTemplate.opsForHash();
        String key = "problemId::" + problemId;
        String hashkey = "likes";
        if(hashOperations.get(key,hashkey) == null){
            hashOperations.put(key,hashkey,problemRepository.getLikesCnt(problemId));
            hashOperations.increment(key,hashkey,1L);
            System.out.println(hashOperations.get(key,hashkey));
        }else
        {hashOperations.increment(key,hashkey,1L);
            System.out.println(hashOperations.get(key, hashkey));}
    }
    @Scheduled(fixedDelay = 1000L*80L)
    @Transactional
    @Override
    public void deleteViewCntToRedis(){
        String hashkey = "views";
        Set<String> Rediskey = redisTemplate.keys("problemId*");
        Iterator<String> it = Rediskey.iterator();
        while (it.hasNext()) {
            String data = it.next();
            Long problemId = Long.parseLong(data.split("::")[1]);
            if (redisTemplate.opsForHash().get(data, hashkey) == null){
                break;
            }
            Long viewCnt = Long.parseLong((String.valueOf(redisTemplate.opsForHash().get(data, hashkey))));
            problemRepositoryImp.addViewCntFromRedis(problemId, viewCnt);
            redisTemplate.opsForHash().delete(data, hashkey);
        }
        System.out.println("views update complete");
    }
    @Scheduled(fixedDelay = 1000L*60L)
    @Transactional
    @Override
    public void deleteLikesCntToRedis(){
        String hashkey = "likes";
        Set<String> RedisKey = redisTemplate.keys("problemId*");
        Iterator<String> it = RedisKey.iterator();
        while(it.hasNext()){
            String data = it.next();
            Long problemId = Long.parseLong(data.split("::")[1]);
            if (redisTemplate.opsForHash().get(data, hashkey) == null){
                break;
            }
            Long likesCnt = Long.parseLong((String.valueOf(redisTemplate.opsForHash().get(data, hashkey))));
            problemRepositoryImp.addLikesCntFromRedis(problemId, likesCnt);
            redisTemplate.opsForHash().delete(data, hashkey);
        }
        System.out.println("likes update complete");
    }
    @Override
    public List<ProblemSortResponseDTO> NotIncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer){
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;
        if(StringUtils.isNullOrEmpty(solved)){
          result = problemRepositoryImp.getProblemSortedByNotSolved(order,types,level,hashtag,likes,views,createdTime);}
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;
    }
    @Override
    public List<ProblemSortResponseDTO> IncludeSolvedSort(String order, String types, String level, String solved, String hashtag, Long views, Long likes, String createdTime, String writer){
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;
        if(!StringUtils.isNullOrEmpty(solved) && StringUtils.isNullOrEmpty(hashtag)) {
            result = this.problemRepositoryImp.getProblemSortedBySolved(order,types,level,hashtag,likes,views,createdTime,writer);
        }
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;
        }

    @Override
    public Long save(ProblemSaveRequestDto request) {
        /*
        * 문제 등록시 작성자의 정보를 알아야 하기 때문에 사용자 관리 서비스에 API 요청이 필요하다.
        * 이 때, 사용자 서비스에 작성자 유효성 검사 API 요청하여 요청에 대한 응답으로 작성자의 닉네임을 반환받는다.
        * 현재는 개발 초기 단계이니 임의의 값을 writer에 삽입하여 테스트하고 이후 사용자 관리 서비스가 개발되면 RestTemplate을 사용하자.
        * */
        String url = "http://192.168.99.13:8000/api/member/" + request.getId();
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String result = res.get("memberName");

        Problem problem = Problem.builder()
                .type(request.getType())
                .writer(result)
                .title(request.getTitle())
                .content(request.getContent())
                .answer(request.getAnswer())
                .level(request.getLevel())
                .hashtag(request.getTag())
                .build();

        return problemRepository.save(problem).getProblemId();
    }

}
