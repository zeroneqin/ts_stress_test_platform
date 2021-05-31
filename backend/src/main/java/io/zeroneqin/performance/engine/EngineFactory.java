package io.zeroneqin.performance.engine;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.zeroneqin.Application;
import io.zeroneqin.base.domain.FileContent;
import io.zeroneqin.base.domain.FileMetadata;
import io.zeroneqin.base.domain.LoadTestWithBLOBs;
import io.zeroneqin.base.domain.TestResourcePool;
import io.zeroneqin.commons.constants.FileType;
import io.zeroneqin.commons.constants.ResourcePoolTypeEnum;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.utils.LogUtil;
import io.zeroneqin.i18n.Translator;
import io.zeroneqin.performance.engine.docker.DockerTestEngine;
import io.zeroneqin.performance.parse.EngineSourceParser;
import io.zeroneqin.performance.parse.EngineSourceParserFactory;
import io.zeroneqin.service.FileService;
import io.zeroneqin.service.KubernetesTestEngine;
import io.zeroneqin.service.TestResourcePoolService;
import org.apache.commons.beanutils.ConstructorUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.reflections8.Reflections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EngineFactory {
    private static FileService fileService;
    private static TestResourcePoolService testResourcePoolService;
    private static Class<? extends KubernetesTestEngine> kubernetesTestEngineClass;

    static {
        Reflections reflections = new Reflections(Application.class.getPackage().getName());
        Set<Class<? extends KubernetesTestEngine>> implClass = reflections.getSubTypesOf(KubernetesTestEngine.class);
        for (Class<? extends KubernetesTestEngine> aClass : implClass) {
            kubernetesTestEngineClass = aClass;
            // 第一个
            break;
        }
    }

    public static Engine createEngine(LoadTestWithBLOBs loadTest) {
        String resourcePoolId = loadTest.getTestResourcePoolId();
        if (StringUtils.isBlank(resourcePoolId)) {
            MSException.throwException(Translator.get("test_resource_pool_id_is_null"));
        }
        int resourcePoolSlaveNum = loadTest.getTestResourcePoolSlaveNum();
        if (resourcePoolSlaveNum==0) {
            MSException.throwException(Translator.get("test_resource_pool_slave_is_0"));
        }

        TestResourcePool resourcePool = testResourcePoolService.getResourcePool(resourcePoolId);
        if (resourcePool == null) {
            MSException.throwException(Translator.get("test_resource_pool_id_is_null"));
        }

        final ResourcePoolTypeEnum type = ResourcePoolTypeEnum.valueOf(resourcePool.getType());

        if (type == ResourcePoolTypeEnum.NODE) {
            return new DockerTestEngine(loadTest);
        }
        if (type == ResourcePoolTypeEnum.K8S) {
            try {
                return ConstructorUtils.invokeConstructor(kubernetesTestEngineClass, loadTest);
            } catch (Exception e) {
                LogUtil.error(e);
                return null;
            }
        }
        return null;
    }

    public static EngineContext createContext(LoadTestWithBLOBs loadTest, String resourceId, double ratio, long startTime, String reportId, int resourceIndex) {
        final List<FileMetadata> fileMetadataList = fileService.getFileMetadataByTestId(loadTest.getId());
        if (org.springframework.util.CollectionUtils.isEmpty(fileMetadataList)) {
            MSException.throwException(Translator.get("run_load_test_file_not_found") + loadTest.getId());
        }
        FileMetadata jmxFile = fileMetadataList.stream().filter(f -> StringUtils.equalsIgnoreCase(f.getType(), FileType.JMX.name()))
                .findFirst().orElseGet(() -> {
                    throw new RuntimeException(Translator.get("run_load_test_file_not_found") + loadTest.getId());
                });
        LogUtil.info("eeeeee");
        List<FileMetadata> csvFiles = fileMetadataList.stream().filter(f -> StringUtils.equalsIgnoreCase(f.getType(), FileType.CSV.name())).collect(Collectors.toList());
        List<FileMetadata> jarFiles = fileMetadataList.stream().filter(f -> StringUtils.equalsIgnoreCase(f.getType(), FileType.JAR.name())).collect(Collectors.toList());
        List<FileMetadata> testFiles = fileMetadataList.stream().filter(f -> (!StringUtils.equalsIgnoreCase(f.getType(), FileType.JAR.name()) && !StringUtils.equalsIgnoreCase(f.getType(), FileType.CSV.name()) && !StringUtils.equalsIgnoreCase(f.getType(), FileType.JMX.name()))).collect(Collectors.toList());
        final FileContent fileContent = fileService.getFileContent(jmxFile.getId());
        LogUtil.info("ffffff");
        if (fileContent == null) {
            MSException.throwException(Translator.get("run_load_test_file_content_not_found") + loadTest.getId());
        }

        final EngineContext engineContext = new EngineContext();
        engineContext.setTestId(loadTest.getId());
        engineContext.setTestName(loadTest.getName());
        engineContext.setNamespace(loadTest.getProjectId());
        engineContext.setFileType(jmxFile.getType());
        engineContext.setResourcePoolId(loadTest.getTestResourcePoolId());
        engineContext.setStartTime(startTime);
        engineContext.setReportId(reportId);
        engineContext.setResourceIndex(resourceIndex);

        if (StringUtils.isNotEmpty(loadTest.getLoadConfiguration())) {
            final JSONArray jsonArray = JSONObject.parseArray(loadTest.getLoadConfiguration());

            for (int i = 0; i < jsonArray.size(); i++) {
                if (jsonArray.get(i) instanceof Map) {
                    JSONObject o = jsonArray.getJSONObject(i);
                    String key = o.getString("key");
                    if ("TargetLevel".equals(key)) {
                        engineContext.addProperty(key, Math.round(((Integer) o.get("value")) * ratio));
                    } else {
                        engineContext.addProperty(key, o.get("value"));
                    }
                }
                if (jsonArray.get(i) instanceof List) {
                    JSONArray o = jsonArray.getJSONArray(i);
                    for (int j = 0; j < o.size(); j++) {
                        JSONObject b = o.getJSONObject(j);
                        String key = b.getString("key");
                        Object values = engineContext.getProperty(key);
                        if (values == null) {
                            values = new ArrayList<>();
                        }
                        if (values instanceof List) {
                            Object value = b.get("value");
                            if ("TargetLevel".equals(key)) {
                                value = Math.round(((Integer) b.get("value")) * ratio);
                            }
                            ((List<Object>) values).add(value);
                            engineContext.addProperty(key, values);
                        }
                    }
                }
            }
        }
        /*
        {"timeout":10,"statusCode":["302","301"],"params":[{"name":"param1","enable":true,"value":"0","edit":false}],"domains":[{"domain":"baidu.com","enable":true,"ip":"127.0.0.1","edit":false}]}
         */
        if (StringUtils.isNotEmpty(loadTest.getAdvancedConfiguration())) {
            JSONObject advancedConfiguration = JSONObject.parseObject(loadTest.getAdvancedConfiguration());
            engineContext.addProperties(advancedConfiguration);
        }
        LogUtil.info("gggggg");
        final EngineSourceParser engineSourceParser = EngineSourceParserFactory.createEngineSourceParser(engineContext.getFileType());

        if (engineSourceParser == null) {
            MSException.throwException("File type unknown");
        }

        try (ByteArrayInputStream source = new ByteArrayInputStream(fileContent.getFile())) {
            String content = engineSourceParser.parse(engineContext, source);
            engineContext.setContent(content);
        } catch (MSException e) {
            LogUtil.error(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            LogUtil.error(e.getMessage(), e);
            MSException.throwException(e);
        }
        LogUtil.info("hhhhhh");
        if (CollectionUtils.isNotEmpty(csvFiles)) {
            Map<String, String> data = new HashMap<>();
            csvFiles.forEach(cf -> {
                FileContent csvContent = fileService.getFileContent(cf.getId());
                data.put(cf.getName(), new String(csvContent.getFile()));
            });
            engineContext.setTestData(data);
        }
        LogUtil.info("iiiiii");
        if (CollectionUtils.isNotEmpty(jarFiles)) {
            Map<String, byte[]> data = new HashMap<>();
            jarFiles.forEach(jf -> {
                FileContent content = fileService.getFileContent(jf.getId());
                data.put(jf.getName(), content.getFile());
            });
            engineContext.setTestJars(data);
        }

        LogUtil.info("jjjjjj");

        if (CollectionUtils.isNotEmpty(testFiles)) {
            Map<String, byte[]> data = new HashMap<>();
            testFiles.forEach(jf -> {
                FileContent content = fileService.getFileContent(jf.getId());
                data.put(jf.getName(), content.getFile());
            });
            engineContext.setTestFiles(data);
        }

        engineContext.setRatio(ratio);

        return engineContext;
    }


    @Resource
    private void setFileService(FileService fileService) {
        EngineFactory.fileService = fileService;
    }

    @Resource
    public void setTestResourcePoolService(TestResourcePoolService testResourcePoolService) {
        EngineFactory.testResourcePoolService = testResourcePoolService;
    }
}
