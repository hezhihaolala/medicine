package com.demo.service;

import com.demo.entity.SampleData;
import com.demo.repository.SampleDataRepository;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * CSV数据导入服务
 * 负责从CSV文件导入数据到数据库
 */
@Service
public class CsvImportService implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CsvImportService.class);
    
    private final SampleDataRepository sampleDataRepository;
    
    public CsvImportService(SampleDataRepository sampleDataRepository) {
        this.sampleDataRepository = sampleDataRepository;
    }

    @Value("${app.csv.import-path}")
    private String csvFilePath;

    @Value("${app.csv.batch-size}")
    private int batchSize;

    @Override
    public void run(String... args) throws Exception {
        importCsvData();
    }

    /**
     * 导入CSV数据到数据库
     */
    @Transactional
    public void importCsvData() {
        try {
            log.info("开始导入CSV数据从文件: {}", csvFilePath);
            
            // 检查数据库中是否已有数据
            long existingCount = sampleDataRepository.count();
            if (existingCount > 0) {
                log.info("数据库中已存在 {} 条记录，跳过导入", existingCount);
                return;
            }

            List<SampleData> batch = new ArrayList<>();
            int totalProcessed = 0;

            try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
                String[] headers = reader.readNext(); // 读取表头
                log.info("CSV文件列数: {}", headers.length);

                String[] line;
                while ((line = reader.readNext()) != null) {
                    SampleData sampleData = parseCsvLine(line);
                    batch.add(sampleData);

                    if (batch.size() >= batchSize) {
                        sampleDataRepository.saveAll(batch);
                        totalProcessed += batch.size();
                        log.debug("已处理 {} 条记录", totalProcessed);
                        batch.clear();
                    }
                }

                // 保存剩余的数据
                if (!batch.isEmpty()) {
                    sampleDataRepository.saveAll(batch);
                    totalProcessed += batch.size();
                }

                log.info("CSV数据导入完成，共导入 {} 条记录", totalProcessed);

            } catch (IOException | CsvException e) {
                log.error("读取CSV文件时发生错误", e);
                throw new RuntimeException("CSV导入失败", e);
            }

        } catch (Exception e) {
            log.error("导入CSV数据时发生错误", e);
            throw new RuntimeException("CSV导入失败", e);
        }
    }

    /**
     * 解析CSV行数据为SampleData对象
     */
    private SampleData parseCsvLine(String[] line) {
        SampleData data = new SampleData();
        
        try {
            // 按CSV列顺序解析数据
            data.setRun(getStringValue(line, 0));
            data.setProjectId(getStringValue(line, 1));
            data.setBioSample(getStringValue(line, 2));
            data.setPmid(getStringValue(line, 3));
            data.setSequencingType(getStringValue(line, 4));
            data.setLibraryLayout(getStringValue(line, 5));
            data.setPlatform(getStringValue(line, 6));
            data.setModel(getStringValue(line, 7));
            data.setPhenotype(getStringValue(line, 8));
            data.setDiseaseStage(getStringValue(line, 9));
            data.setComplication(getStringValue(line, 10));
            data.setSmoke(getStringValue(line, 11));
            data.setRecentAntibioticsUse(getStringValue(line, 12));
            data.setAntibioticsUsed(getStringValue(line, 13));
            data.setBodySite(getStringValue(line, 14));
            data.setBodySiteRaw(getStringValue(line, 15));
            data.setSex(getStringValue(line, 16));
            data.setAge(getStringValue(line, 17));
            data.setAgeGroup(getStringValue(line, 18));
            data.setBmi(getStringValue(line, 19));
            data.setPatientId(getStringValue(line, 20));
            data.setTimePoint(getStringValue(line, 21));
            data.setReads(getLongValue(line, 22));
            data.setShannon(getDoubleValue(line, 23));
            data.setObserved(getIntegerValue(line, 24));
            data.setChao1(getDoubleValue(line, 25));
            data.setCountry(getStringValue(line, 26));
            data.setContinent(getStringValue(line, 27));
            data.setLocation(getStringValue(line, 28));
            data.setLatitude(getDoubleValue(line, 29));
            data.setLongitude(getDoubleValue(line, 30));
            data.setAgeStart(getDoubleValue(line, 31));
            data.setAgeEnd(getDoubleValue(line, 32));

        } catch (Exception e) {
            log.warn("解析CSV行时发生错误，使用默认值: {}", e.getMessage());
        }

        return data;
    }

    private String getStringValue(String[] line, int index) {
        if (index >= line.length || line[index] == null || line[index].trim().isEmpty() || "-".equals(line[index].trim())) {
            return null;
        }
        return line[index].trim();
    }

    private Long getLongValue(String[] line, int index) {
        try {
            String value = getStringValue(line, index);
            return value != null ? Long.parseLong(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double getDoubleValue(String[] line, int index) {
        try {
            String value = getStringValue(line, index);
            return value != null ? Double.parseDouble(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer getIntegerValue(String[] line, int index) {
        try {
            String value = getStringValue(line, index);
            return value != null ? Integer.parseInt(value) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}