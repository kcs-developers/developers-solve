package com.developers.solve.service;

import com.developers.solve.problem.responseDTO.ProblemSortResponseDTO;
import com.developers.solve.problem.responseDTO.SortResponseDTO;
import com.developers.solve.problem.service.ProblemService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.testng.Assert;

import java.util.List;

@SpringBootTest
public class ProblemServiceTest {

    @Autowired
    ProblemService problemService;
    @Autowired
    RedisTemplate redisTemplate;

    @Test
    @DisplayName("Default(created_time) Test")
    public void DefaultTest(){

        List response = problemService.NotIncludeSolvedSort("createdTime",null,null,null,null,null,null,null,null);
        System.out.println(response);

    }
    @Test
    @DisplayName("LikesOrder Test")
    public void OrderLikesTest(){
        List response = problemService.NotIncludeSolvedSort("likes",null,null,null,null,null,null,null,null);
        System.out.println(response);
    }
    @Test
    @DisplayName("ViewsOrder Test")
    public void OrderViewsTest(){
        List response = problemService.NotIncludeSolvedSort("views",null,null,null,null,null,null,null,null);
        System.out.println(response);
    }
    @Test
    @DisplayName("OrderAndType")
    public void OrderAndType(){
        List response = problemService.NotIncludeSolvedSort("views","choice",null,null,null,null,null,null,null);
        System.out.println(response);
    }
    @Test
    @DisplayName("OrderAndTypeLevel")
    public void OrderAndLevelType(){
        List response = problemService.NotIncludeSolvedSort("views",null,"bronze",null,null,null,null,null,null);
        System.out.println(response);
    }
    @Test
    @DisplayName("OrderAndTypeLevelSolved")
    public void OrderAndLevelTypeSolved(){
            List response = problemService.IncludeSolvedSort("createdTime",null,null,"True",null,null,null,null,"Taeho");
            System.out.println(response);
        }
    }
//    @Test
//    public void pdfdf(){
//        List<ProblemSortResponseDTO> list = problemService.FirstSortProblem("likes", null, null,null, null, null,100L,null, "lango");
//        System.out.println("+++++LIST: "+ i++ + list);
        //캐시에서 결과가져오기
//        Cache cache = cacheManager.getCache("FirstSortProblem");
//        List<ProblemSortResponseDTO> list1 = cache.get();
//    @Test
//    public void tes1(){
//        List<ProblemSortResponseDTO> list = problemService.NotFirstSortProblem("likes,types", 10L);
//        System.out.println("List:" + list);
//    }
//    @Test
//    public void RedisTest() {
//        problemService.addViewCntToRedis(1L);
//    }


