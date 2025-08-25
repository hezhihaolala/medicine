package com.demo.service;

import com.demo.dto.FilterOptionsResponse;
import com.demo.dto.PageResponse;
import com.demo.dto.SampleQueryRequest;
import com.demo.entity.SampleData;
import com.demo.repository.SampleDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * 样本数据业务服务类
 * 提供数据查询、过滤等业务功能
 */
@Service
public class SampleDataService {

    private static final Logger log = LoggerFactory.getLogger(SampleDataService.class);
    
    private final SampleDataRepository sampleDataRepository;
    
    public SampleDataService(SampleDataRepository sampleDataRepository) {
        this.sampleDataRepository = sampleDataRepository;
    }

    /**
     * 根据查询条件获取分页数据
     */
    public PageResponse<SampleData> getSampleData(SampleQueryRequest request) {
        log.info("查询样本数据: {}", request);

        // 创建分页和排序对象
        Sort sort = Sort.by(
            "desc".equalsIgnoreCase(request.getSortDir()) 
                ? Sort.Direction.DESC 
                : Sort.Direction.ASC,
            request.getSortBy()
        );
        
        Pageable pageable = PageRequest.of(request.getPage(), request.getSize(), sort);

        // 执行查询
        Page<SampleData> page = sampleDataRepository.findByMultipleFilters(
            request.getBodySite(),
            request.getPhenotype(),
            request.getSequencingType(),
            request.getPlatform(),
            request.getCountry(),
            request.getContinent(),
            request.getSex(),
            request.getAgeGroup(),
            pageable
        );

        // 转换为响应对象
        return new PageResponse<>(
            page.getContent(),
            page.getNumber(),
            page.getSize(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.isFirst(),
            page.isLast(),
            page.hasNext(),
            page.hasPrevious()
        );
    }

    /**
     * 获取所有可用的过滤选项
     */
    public FilterOptionsResponse getFilterOptions() {
        log.info("获取过滤选项");

        return new FilterOptionsResponse(
            sampleDataRepository.findDistinctBodySites(),
            sampleDataRepository.findDistinctPhenotypes(),
            sampleDataRepository.findDistinctSequencingTypes(),
            sampleDataRepository.findDistinctPlatforms(),
            sampleDataRepository.findDistinctCountries(),
            sampleDataRepository.findDistinctContinents(),
            sampleDataRepository.findDistinctSexes(),
            sampleDataRepository.findDistinctAgeGroups()
        );
    }

    /**
     * 获取数据统计信息
     */
    public long getTotalCount() {
        return sampleDataRepository.count();
    }
}