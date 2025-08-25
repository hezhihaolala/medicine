package com.demo.entity;

import jakarta.persistence.*;

/**
 * 样本数据实体类
 * 对应CSV文件中的数据结构
 */
@Entity
@Table(name = "sample_data")
public class SampleData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "run_id")
    private String run;

    @Column(name = "project_id")
    private String projectId;

    @Column(name = "bio_sample")
    private String bioSample;

    @Column(name = "pmid")
    private String pmid;

    @Column(name = "sequencing_type")
    private String sequencingType;

    @Column(name = "library_layout")
    private String libraryLayout;

    @Column(name = "platform")
    private String platform;

    @Column(name = "model")
    private String model;

    @Column(name = "phenotype")
    private String phenotype;

    @Column(name = "disease_stage")
    private String diseaseStage;

    @Column(name = "complication", length = 1000)
    private String complication;

    @Column(name = "smoke")
    private String smoke;

    @Column(name = "recent_antibiotics_use")
    private String recentAntibioticsUse;

    @Column(name = "antibiotics_used", length = 500)
    private String antibioticsUsed;

    @Column(name = "body_site")
    private String bodySite;

    @Column(name = "body_site_raw", length = 500)
    private String bodySiteRaw;

    @Column(name = "sex")
    private String sex;

    @Column(name = "age", length = 500)
    private String age;

    @Column(name = "age_group")
    private String ageGroup;

    @Column(name = "bmi")
    private String bmi;

    @Column(name = "patient_id", length = 500)
    private String patientId;

    @Column(name = "time_point", length = 500)
    private String timePoint;

    @Column(name = "reads")
    private Long reads;

    @Column(name = "shannon")
    private Double shannon;

    @Column(name = "observed")
    private Integer observed;

    @Column(name = "chao1")
    private Double chao1;

    @Column(name = "country")
    private String country;

    @Column(name = "continent")
    private String continent;

    @Column(name = "location")
    private String location;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "age_start")
    private Double ageStart;

    @Column(name = "age_end")
    private Double ageEnd;

    // 构造函数
    public SampleData() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRun() { return run; }
    public void setRun(String run) { this.run = run; }

    public String getProjectId() { return projectId; }
    public void setProjectId(String projectId) { this.projectId = projectId; }

    public String getBioSample() { return bioSample; }
    public void setBioSample(String bioSample) { this.bioSample = bioSample; }

    public String getPmid() { return pmid; }
    public void setPmid(String pmid) { this.pmid = pmid; }

    public String getSequencingType() { return sequencingType; }
    public void setSequencingType(String sequencingType) { this.sequencingType = sequencingType; }

    public String getLibraryLayout() { return libraryLayout; }
    public void setLibraryLayout(String libraryLayout) { this.libraryLayout = libraryLayout; }

    public String getPlatform() { return platform; }
    public void setPlatform(String platform) { this.platform = platform; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public String getPhenotype() { return phenotype; }
    public void setPhenotype(String phenotype) { this.phenotype = phenotype; }

    public String getDiseaseStage() { return diseaseStage; }
    public void setDiseaseStage(String diseaseStage) { this.diseaseStage = diseaseStage; }

    public String getComplication() { return complication; }
    public void setComplication(String complication) { this.complication = complication; }

    public String getSmoke() { return smoke; }
    public void setSmoke(String smoke) { this.smoke = smoke; }

    public String getRecentAntibioticsUse() { return recentAntibioticsUse; }
    public void setRecentAntibioticsUse(String recentAntibioticsUse) { this.recentAntibioticsUse = recentAntibioticsUse; }

    public String getAntibioticsUsed() { return antibioticsUsed; }
    public void setAntibioticsUsed(String antibioticsUsed) { this.antibioticsUsed = antibioticsUsed; }

    public String getBodySite() { return bodySite; }
    public void setBodySite(String bodySite) { this.bodySite = bodySite; }

    public String getBodySiteRaw() { return bodySiteRaw; }
    public void setBodySiteRaw(String bodySiteRaw) { this.bodySiteRaw = bodySiteRaw; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getAgeGroup() { return ageGroup; }
    public void setAgeGroup(String ageGroup) { this.ageGroup = ageGroup; }

    public String getBmi() { return bmi; }
    public void setBmi(String bmi) { this.bmi = bmi; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getTimePoint() { return timePoint; }
    public void setTimePoint(String timePoint) { this.timePoint = timePoint; }

    public Long getReads() { return reads; }
    public void setReads(Long reads) { this.reads = reads; }

    public Double getShannon() { return shannon; }
    public void setShannon(Double shannon) { this.shannon = shannon; }

    public Integer getObserved() { return observed; }
    public void setObserved(Integer observed) { this.observed = observed; }

    public Double getChao1() { return chao1; }
    public void setChao1(Double chao1) { this.chao1 = chao1; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public String getContinent() { return continent; }
    public void setContinent(String continent) { this.continent = continent; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }

    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }

    public Double getAgeStart() { return ageStart; }
    public void setAgeStart(Double ageStart) { this.ageStart = ageStart; }

    public Double getAgeEnd() { return ageEnd; }
    public void setAgeEnd(Double ageEnd) { this.ageEnd = ageEnd; }
}