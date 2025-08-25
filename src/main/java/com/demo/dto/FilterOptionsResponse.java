package com.demo.dto;

import java.util.List;

/**
 * 过滤选项DTO
 * 包含所有可用的过滤选项
 */
public class FilterOptionsResponse {
    
    private List<String> bodySites;
    private List<String> phenotypes;
    private List<String> sequencingTypes;
    private List<String> platforms;
    private List<String> countries;
    private List<String> continents;
    private List<String> sexes;
    private List<String> ageGroups;
    
    public FilterOptionsResponse() {}
    
    public FilterOptionsResponse(List<String> bodySites, List<String> phenotypes, List<String> sequencingTypes,
                                List<String> platforms, List<String> countries, List<String> continents,
                                List<String> sexes, List<String> ageGroups) {
        this.bodySites = bodySites;
        this.phenotypes = phenotypes;
        this.sequencingTypes = sequencingTypes;
        this.platforms = platforms;
        this.countries = countries;
        this.continents = continents;
        this.sexes = sexes;
        this.ageGroups = ageGroups;
    }
    
    // Getters and Setters
    public List<String> getBodySites() { return bodySites; }
    public void setBodySites(List<String> bodySites) { this.bodySites = bodySites; }
    
    public List<String> getPhenotypes() { return phenotypes; }
    public void setPhenotypes(List<String> phenotypes) { this.phenotypes = phenotypes; }
    
    public List<String> getSequencingTypes() { return sequencingTypes; }
    public void setSequencingTypes(List<String> sequencingTypes) { this.sequencingTypes = sequencingTypes; }
    
    public List<String> getPlatforms() { return platforms; }
    public void setPlatforms(List<String> platforms) { this.platforms = platforms; }
    
    public List<String> getCountries() { return countries; }
    public void setCountries(List<String> countries) { this.countries = countries; }
    
    public List<String> getContinents() { return continents; }
    public void setContinents(List<String> continents) { this.continents = continents; }
    
    public List<String> getSexes() { return sexes; }
    public void setSexes(List<String> sexes) { this.sexes = sexes; }
    
    public List<String> getAgeGroups() { return ageGroups; }
    public void setAgeGroups(List<String> ageGroups) { this.ageGroups = ageGroups; }
}