package com.demo.dto;

/**
 * 数据查询请求DTO
 * 用于接收前端的过滤条件
 */
public class SampleQueryRequest {
    
    // 分页参数
    private int page = 0;
    private int size = 20;
    private String sortBy = "id";
    private String sortDir = "asc";
    
    // 过滤条件
    private String bodySite;
    private String phenotype;
    private String sequencingType;
    private String platform;
    private String country;
    private String continent;
    private String sex;
    private String ageGroup;

    // Getters and Setters
    public int getPage() { return page; }
    public void setPage(int page) { this.page = page; }
    
    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }
    
    public String getSortBy() { return sortBy; }
    public void setSortBy(String sortBy) { this.sortBy = sortBy; }
    
    public String getSortDir() { return sortDir; }
    public void setSortDir(String sortDir) { this.sortDir = sortDir; }
    
    public String getBodySite() { return bodySite; }
    public void setBodySite(String bodySite) { this.bodySite = bodySite; }
    
    public String getPhenotype() { return phenotype; }
    public void setPhenotype(String phenotype) { this.phenotype = phenotype; }
    
    public String getSequencingType() { return sequencingType; }
    public void setSequencingType(String sequencingType) { this.sequencingType = sequencingType; }
    
    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }
    
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    
    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }
    
    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }
    
    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }
    
    @Override
    public String toString() {
        return "SampleQueryRequest{" +
                "page=" + page +
                ", size=" + size +
                ", sortBy='" + sortBy + '\'' +
                ", sortDir='" + sortDir + '\'' +
                ", bodySite='" + bodySite + '\'' +
                ", phenotype='" + phenotype + '\'' +
                ", sequencingType='" + sequencingType + '\'' +
                ", platform='" + platform + '\'' +
                ", country='" + country + '\'' +
                ", continent='" + continent + '\'' +
                ", sex='" + sex + '\'' +
                ", ageGroup='" + ageGroup + '\'' +
                '}';
    }
}