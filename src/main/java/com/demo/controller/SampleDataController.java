package com.demo.controller;

import com.demo.dto.FilterOptionsResponse;
import com.demo.dto.PageResponse;
import com.demo.dto.SampleQueryRequest;
import com.demo.entity.SampleData;
import com.demo.service.SampleDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 样本数据API控制器
 * 提供RESTful API接口
 */
@RestController
@RequestMapping("/api/samples")
@CrossOrigin(origins = "*")
public class SampleDataController {

    private static final Logger log = LoggerFactory.getLogger(SampleDataController.class);
    
    private final SampleDataService sampleDataService;
    
    public SampleDataController(SampleDataService sampleDataService) {
        this.sampleDataService = sampleDataService;
    }

    /**
     * 获取样本数据 - 支持分页和过滤
     */
    @PostMapping("/search")
    public ResponseEntity<PageResponse<SampleData>> searchSamples(@RequestBody SampleQueryRequest request) {
        log.info("接收到样本数据查询请求: {}", request);
        
        PageResponse<SampleData> response = sampleDataService.getSampleData(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取样本数据 - GET方式，使用请求参数
     */
    @GetMapping
    public ResponseEntity<PageResponse<SampleData>> getSamples(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) String bodySite,
            @RequestParam(required = false) String phenotype,
            @RequestParam(required = false) String sequencingType,
            @RequestParam(required = false) String platform,
            @RequestParam(required = false) String country,
            @RequestParam(required = false) String continent,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String ageGroup) {
        
        SampleQueryRequest request = new SampleQueryRequest();
        request.setPage(page);
        request.setSize(size);
        request.setSortBy(sortBy);
        request.setSortDir(sortDir);
        request.setBodySite(bodySite);
        request.setPhenotype(phenotype);
        request.setSequencingType(sequencingType);
        request.setPlatform(platform);
        request.setCountry(country);
        request.setContinent(continent);
        request.setSex(sex);
        request.setAgeGroup(ageGroup);
        
        log.info("接收到样本数据GET查询请求: {}", request);
        
        PageResponse<SampleData> response = sampleDataService.getSampleData(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 获取所有可用的过滤选项
     */
    @GetMapping("/filter-options")
    public ResponseEntity<FilterOptionsResponse> getFilterOptions() {
        log.info("获取过滤选项");
        
        FilterOptionsResponse response = sampleDataService.getFilterOptions();
        return ResponseEntity.ok(response);
    }

    /**
     * 获取数据总数统计
     */
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalCount() {
        log.info("获取数据总数");
        
        long count = sampleDataService.getTotalCount();
        return ResponseEntity.ok(count);
    }
}