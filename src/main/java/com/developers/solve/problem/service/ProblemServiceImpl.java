package com.developers.solve.problem.service;

import com.developers.solve.problem.dto.requestDTO.ProblemUpdateRequestDto;
import com.developers.solve.problem.dto.responseDTO.*;
import com.developers.solve.problem.entity.Problem;
import com.developers.solve.problem.repository.ProblemQueryDsl;
import com.developers.solve.problem.repository.ProblemRepository;
import com.developers.solve.problem.dto.requestDTO.ProblemSaveRequestDto;
import com.developers.solve.solution.repository.SolutionRepository;
import com.querydsl.core.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
@Log4j2
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
    public ProblemSaveResponseDto save(ProblemSaveRequestDto problemSaveRequestDto){


        String url = "http://localhost:9000/api/member/"+problemSaveRequestDto.getId();
        Map<String,String> res = restTemplate.getForObject(url, Map.class);
        String restResult = res.get("memberName");

        Problem problem = Problem.builder()
                .type(problemSaveRequestDto.getType())
                .writer(restResult)
                .title(problemSaveRequestDto.getTitle())
                .content(problemSaveRequestDto.getContent())
                .answer(problemSaveRequestDto.getAnswer())
                .level(problemSaveRequestDto.getLevel())
                .hashTag(problemSaveRequestDto.getHashtag())
                .views(problemSaveRequestDto.getViews())
                .likes(problemSaveRequestDto.getLikes())
                .build();

        Problem result = problemRepository.save(problem);

        ProblemSaveDetailResponseDto saveDetailResponseDto = ProblemSaveDetailResponseDto.builder()
                .problemId(result.getProblemId())
                .type(result.getType())
                .writer(result.getWriter())
                .title(result.getTitle())
                .content(result.getContent())
                .hashtag(result.getHashTag())
                .answer(result.getAnswer())
                .level(result.getLevel())
                .views(result.getViews())
                .likes(result.getLikes())
                .build();

        return ProblemSaveResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 문제를 생성하였습니다.")
                .data(saveDetailResponseDto)
                .build();
    }


    //문제 조회
    @Override
    public ProblemDetailResponseDto problemDetail(Long problemId, String member) {
        Problem problem = problemRepository.findById(problemId).orElseThrow();
        Boolean solved = solutionRepository.existsBySolverAndProblemProblemId(member, problemId);
        System.out.println("solved:" + solved);
        ProblemDetailDto dto = ProblemDetailDto.builder()
                .problemId(problem.getProblemId())
                .type(problem.getType())
                .writer(problem.getWriter()) //writerId?
                .title(problem.getTitle())
                .content(problem.getContent())
                .hashTag(problem.getHashTag())
                .answer(problem.getAnswer())
                .level(problem.getLevel())
                .likes(problem.getLikes())
                .views(problem.getViews())
                .solved(solved)
                .build();

        return ProblemDetailResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 조회 완료하였습니다.")
                .data(dto)
                .build();

    }
    //문제 수정
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
    @jakarta.transaction.Transactional
    public ResponseEntity<Long> deleteProblem(Long problemId){
        //데이터 존재여부 확인하고 없으면 에러메시지 출력
        Optional<Problem> target = problemRepository.findById(problemId);
        log.info(target);
        //데이터가 존재한다면 삭제
        if(target.isPresent()){
            problemRepository.deleteById(target.get().getProblemId());
        }
        return ResponseEntity.status(HttpStatus.OK).body(problemId);
    }

    @Override
    public ProblemListResponseDto getListWithSearch(String param) {
        List<ProblemDetailDto> dtoList = problemRepository.findByTitleContaining(param.replaceAll("\\s+", " "))
                .stream().map(problem -> entityToSearchDTO(problem)).toList();
        return ProblemListResponseDto.builder()
                .code(String.valueOf(HttpStatus.OK))
                .msg("정상적으로 문자열 검색이 되었습니다.")
                .data(dtoList)
                .build();
    }
}
