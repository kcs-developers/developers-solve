package com.developers.solve.problem.service;

import com.developers.solve.problem.entity.Attached;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.AttachedRepository;
import com.developers.solve.problem.repository.ProblemQueryDsl;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.problem.requestDTO.AttachedDto;
import com.developers.solve.problem.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.problem.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.responseDTO.*;
import com.developers.solve.solution.repository.SolutionRepository;
import com.querydsl.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Service
public class ProblemServiceImpl implements ProblemService {
    private final ProblemRepository problemRepository;
    private final ProblemQueryDsl problemRepositoryImp;
    private final RestTemplate restTemplate;
    private final SolutionRepository solutionRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    private final AttachedRepository attachedRepository;



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
    @Cacheable(cacheNames = "CreatedTimeSort")
    @Override
    public List<ProblemSortResponseDTO> CreatedTimeSortList(){
        List<Problem> result = null;
        List<ProblemSortResponseDTO> problemSortResponseDTO;

            result = problemRepository.CreatedTimeSort();
        problemSortResponseDTO = result.stream().map(this::EntityToDto).collect(toList());
        return problemSortResponseDTO;

    }
    @CacheEvict(cacheNames = "CreatedTimeSort")
    @Override
    public void CreatedTimeSortCacheEvict(){
    }
    @Scheduled(fixedDelay = 1000L*80L)
    @Override
    public void CreatedCacheUpdate(){
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
    public ProblemSaveResponseDto save(ProblemSaveRequestDto request) {
        /*
         * 문제 등록시 작성자의 정보를 알아야 하기 때문에 사용자 관리 서비스에 API 요청이 필요하다.
         * 이 때, 사용자 서비스에 작성자 유효성 검사 API 요청하여 요청에 대한 응답으로 작성자의 닉네임을 반환받는다.
         * 현재는 개발 초기 단계이니 임의의 값을 writer에 삽입하여 테스트하고 이후 사용자 관리 서비스가 개발되면 RestTemplate을 사용하자.
         * */
        String url = "http://localhost:9000/api/member/" + request.getId();
        Map<String, String> res = restTemplate.getForObject(url, Map.class);
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
        Problem problem1 = problemRepository.save(problem);

        ProblemSaveDetailResponseDto saveDetailResponseDto = ProblemSaveDetailResponseDto.builder()
                .problemId(problem1.getProblemId())
                .type(problem1.getType())
                .writer(problem1.getWriter())
                .title(problem1.getTitle())
                .content(problem1.getContent())
                .hashtag(problem1.getHashtag())
                .answer(problem1.getAnswer())
                .level(problem1.getLevel())
                .views(problem1.getViews())
                .likes(problem1.getLikes())
                .build();

        return ProblemSaveResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 문제를 생성하였습니다.")
                .data(saveDetailResponseDto)
                .build();
    }
    @Override
    public ProblemDetailResponseDto problemDetail(Long problemId, String member){
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        Boolean solved = solutionRepository.existsBySolverAndProblemProblemId(member, problemId);
        List<String> pathname = attachedRepository.AttachedFile(problemId);
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
                .solved(solved)
                .pathname(pathname)
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
    public ProblemUpdateResponseDto update(ProblemUpdateRequestDto updateRequestDto){
        if(updateRequestDto.getProblemId() == null){
            return ProblemUpdateResponseDto.builder()
                    .code(String.valueOf(HttpStatus.BAD_REQUEST))
                    .msg("ProblemId가 없습니다.")
                    .build();
        }
        //데이터 존재여부확인
        Optional<Problem> problem = problemRepository.findById(updateRequestDto.getProblemId());
        if(problem.isPresent()){
            problem.get().updateProblem(updateRequestDto);
            Problem result = problemRepository.save(problem.get());
            return ProblemUpdateResponseDto.builder()
                    .code(String.valueOf(HttpStatus.OK))
                    .msg("수정이 완료되었습니다.")
                    .data(result)
                    .build();
        }else{
            return ProblemUpdateResponseDto.builder()
                    .code(String.valueOf(HttpStatus.BAD_REQUEST))
                    .msg("ProblemId가 없습니다!")
                    .build();
        }

    }
    @Override
    @Transactional
    public String deleteProblem(Long problemId){
       String result = null;
        Optional<Problem> target = problemRepository.findById(problemId);
        //데이터가 존재한다면 삭제
        if(target.isPresent()){
            problemRepository.deleteById(target.get().getProblemId());
            result = "Complete Delete";
        }
        return result;
    }
    @Override
    public List<ProblemSortResponseDTO> getListWithSearch(String search) {
        List<ProblemSortResponseDTO> dtoList = problemRepository.findByTitleContaining(search.replaceAll("\\s+", " "))
                .stream().map(this::EntityToDto).toList();
        return dtoList;
    }

    @Transactional
    @Override
    public String uploadAttached(AttachedDto attachedDto){
        List<String> pathList = attachedDto.getPathname();
        for (int i=0; i < pathList.size(); i++){
            String result = pathList.get(i);
            Attached attachedDto1 = Attached.builder()
                    .pathName(result)
                    .problemId(attachedDto.getProblemId())
                    .build();
            attachedRepository.save(attachedDto1);}
        String response = "Attached Updated Success";
        return response;
    }


}
