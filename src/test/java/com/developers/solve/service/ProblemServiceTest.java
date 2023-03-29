package com.developers.solve.service;

import com.developers.solve.problem.dto.ProblemSortResponseDTO;
import com.developers.solve.problem.service.ProblemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.Pageable;

import java.util.List;

@SpringBootTest
public class ProblemServiceTest {

    int i = 0;
    @Autowired
    ProblemService problemService;
    @Autowired
    private CacheManager cacheManager;

    @Test
    public void pdfdf(){
        List<ProblemSortResponseDTO> list = problemService.FirstSortProblem(null, null, null,"solved", 100L, "Cloud");
        System.out.println("+++++LIST: "+ i++ + list);
        //캐시에서 결과가져오기
//        Cache cache = cacheManager.getCache("FirstSortProblem");
//        List<ProblemSortResponseDTO> list1 = cache.get();
    }
//    @Test
//    public void tes1(){
//        List<ProblemSortResponseDTO> list = problemService.NotFirstSortProblem("likes,types", 10L);
//        System.out.println("List:" + list);
//    }
    @Test
    public void RedisTest() {
        problemService.addViewCntToRedis(1L);
    }
}

