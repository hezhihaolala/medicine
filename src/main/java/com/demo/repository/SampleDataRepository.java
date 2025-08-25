package com.demo.repository;

import com.demo.entity.SampleData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 样本数据Repository接口
 * 提供数据查询和过滤功能
 */
@Repository
public interface SampleDataRepository extends JpaRepository<SampleData, Long> {

    /**
     * 根据多个条件进行过滤查询
     */
    @Query("SELECT s FROM SampleData s WHERE " +
           "(:bodySite IS NULL OR s.bodySite = :bodySite) AND " +
           "(:phenotype IS NULL OR s.phenotype = :phenotype) AND " +
           "(:sequencingType IS NULL OR s.sequencingType = :sequencingType) AND " +
           "(:platform IS NULL OR s.platform = :platform) AND " +
           "(:country IS NULL OR s.country = :country) AND " +
           "(:continent IS NULL OR s.continent = :continent) AND " +
           "(:sex IS NULL OR s.sex = :sex) AND " +
           "(:ageGroup IS NULL OR s.ageGroup = :ageGroup)")
    Page<SampleData> findByMultipleFilters(
            @Param("bodySite") String bodySite,
            @Param("phenotype") String phenotype,
            @Param("sequencingType") String sequencingType,
            @Param("platform") String platform,
            @Param("country") String country,
            @Param("continent") String continent,
            @Param("sex") String sex,
            @Param("ageGroup") String ageGroup,
            Pageable pageable
    );

    /**
     * 获取所有不同的Body Site值
     */
    @Query("SELECT DISTINCT s.bodySite FROM SampleData s WHERE s.bodySite IS NOT NULL ORDER BY s.bodySite")
    List<String> findDistinctBodySites();

    /**
     * 获取所有不同的Phenotype值
     */
    @Query("SELECT DISTINCT s.phenotype FROM SampleData s WHERE s.phenotype IS NOT NULL ORDER BY s.phenotype")
    List<String> findDistinctPhenotypes();

    /**
     * 获取所有不同的Sequencing Type值
     */
    @Query("SELECT DISTINCT s.sequencingType FROM SampleData s WHERE s.sequencingType IS NOT NULL ORDER BY s.sequencingType")
    List<String> findDistinctSequencingTypes();

    /**
     * 获取所有不同的Platform值
     */
    @Query("SELECT DISTINCT s.platform FROM SampleData s WHERE s.platform IS NOT NULL ORDER BY s.platform")
    List<String> findDistinctPlatforms();

    /**
     * 获取所有不同的Country值
     */
    @Query("SELECT DISTINCT s.country FROM SampleData s WHERE s.country IS NOT NULL ORDER BY s.country")
    List<String> findDistinctCountries();

    /**
     * 获取所有不同的Continent值
     */
    @Query("SELECT DISTINCT s.continent FROM SampleData s WHERE s.continent IS NOT NULL ORDER BY s.continent")
    List<String> findDistinctContinents();

    /**
     * 获取所有不同的Sex值
     */
    @Query("SELECT DISTINCT s.sex FROM SampleData s WHERE s.sex IS NOT NULL ORDER BY s.sex")
    List<String> findDistinctSexes();

    /**
     * 获取所有不同的Age Group值
     */
    @Query("SELECT DISTINCT s.ageGroup FROM SampleData s WHERE s.ageGroup IS NOT NULL ORDER BY s.ageGroup")
    List<String> findDistinctAgeGroups();
}