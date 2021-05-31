package io.zeroneqin.performance.engine.docker;

import com.alibaba.fastjson.JSON;
import io.zeroneqin.base.domain.LoadTestWithBLOBs;
import io.zeroneqin.base.domain.TestResource;
import io.zeroneqin.commons.constants.ResourceStatusEnum;
import io.zeroneqin.commons.exception.MSException;
import io.zeroneqin.commons.utils.CommonBeanFactory;
import io.zeroneqin.commons.utils.LogUtil;
import io.zeroneqin.config.JmeterProperties;
import io.zeroneqin.config.KafkaProperties;
import io.zeroneqin.controller.ResultHolder;
import io.zeroneqin.dto.BaseSystemConfigDTO;
import io.zeroneqin.dto.NodeDTO;
import io.zeroneqin.i18n.Translator;
import io.zeroneqin.performance.engine.AbstractEngine;
import io.zeroneqin.performance.engine.request.StartTestRequest;
import io.zeroneqin.service.SystemParameterService;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DockerTestEngine extends AbstractEngine {
    private static final String BASE_URL = "http://%s:%d";
    private RestTemplate restTemplate;
    private RestTemplate restTemplateWithTimeOut;

    public DockerTestEngine(LoadTestWithBLOBs loadTest) {
        this.init(loadTest);
    }

    @Override
    protected void init(LoadTestWithBLOBs loadTest) {
        super.init(loadTest);
        this.restTemplate = (RestTemplate) CommonBeanFactory.getBean("restTemplate");
        this.restTemplateWithTimeOut = (RestTemplate) CommonBeanFactory.getBean("restTemplateWithTimeOut");
    }

    @Override
    public void start() {
        Integer runningSumThreadNum = getRunningThreadNum();
        int totalThreadNum = resourceList.stream()
                .filter(r -> ResourceStatusEnum.VALID.name().equals(r.getStatus()))
                .map(r -> JSON.parseObject(r.getConfiguration(), NodeDTO.class).getMaxConcurrency())
                .reduce(Integer::sum)
                .orElse(0);
        if (threadNum > totalThreadNum - runningSumThreadNum) {
            MSException.throwException(Translator.get("max_thread_insufficient"));
        }
        LogUtil.debug("resourceList.size:"+resourceList.size());
        List<Integer> resourceRatio = resourceList.stream()
                .filter(r -> ResourceStatusEnum.VALID.name().equals(r.getStatus()))
                .map(r -> JSON.parseObject(r.getConfiguration(), NodeDTO.class).getMaxConcurrency())
                .collect(Collectors.toList());

        for (int i = 0, size = resourceList.size(); i < size; i++) {
            int ratio = resourceRatio.get(i);
//            double realThreadNum = ((double) ratio / totalThreadNum) * threadNum;
            runTest(resourceList.get(i), ((double) ratio / totalThreadNum), i);
        }

    }

    private void runTest(TestResource resource, double ratio, int resourceIndex) {

        String configuration = resource.getConfiguration();
        NodeDTO node = JSON.parseObject(configuration, NodeDTO.class);
        String nodeIp = node.getIp();
        Integer port = node.getPort();

        BaseSystemConfigDTO baseInfo = CommonBeanFactory.getBean(SystemParameterService.class).getBaseInfo();
        KafkaProperties kafkaProperties = CommonBeanFactory.getBean(KafkaProperties.class);
        JmeterProperties jmeterProperties = CommonBeanFactory.getBean(JmeterProperties.class);
        String metersphereUrl = "http://localhost:8081";
        if (baseInfo != null) {
            metersphereUrl = baseInfo.getUrl();
        }

        Map<String, String> env = new HashMap<>();
        env.put("RATIO", "" + ratio);
        env.put("RESOURCE_INDEX", "" + resourceIndex);
        env.put("METERSPHERE_URL", metersphereUrl);
        env.put("START_TIME", "" + this.getStartTime());
        env.put("TEST_ID", this.loadTest.getId());
        env.put("REPORT_ID", this.getReportId());
        env.put("BOOTSTRAP_SERVERS", kafkaProperties.getBootstrapServers());
        env.put("LOG_TOPIC", kafkaProperties.getLog().getTopic());
        env.put("RESOURCE_ID", resource.getId());
        env.put("THREAD_NUM", "" + threadNum);
        env.put("HEAP", jmeterProperties.getHeap());


        StartTestRequest startTestRequest = new StartTestRequest();
        startTestRequest.setImage(JMETER_IMAGE);
        startTestRequest.setEnv(env);

        String uri = String.format(BASE_URL + "/jmeter/container/start", nodeIp, port);
        ResultHolder result = restTemplate.postForObject(uri, startTestRequest, ResultHolder.class);
        if (result == null) {
            MSException.throwException(Translator.get("start_engine_fail"));
        }
        if (!result.isSuccess()) {
            MSException.throwException(result.getMessage());
        }
    }

    @Override
    public void stop() {
        String testId = loadTest.getId();
        this.resourceList.forEach(r -> {
            NodeDTO node = JSON.parseObject(r.getConfiguration(), NodeDTO.class);
            String ip = node.getIp();
            Integer port = node.getPort();

            String uri = String.format(BASE_URL + "/jmeter/container/stop/" + testId, ip, port);
            ResultHolder result = restTemplateWithTimeOut.getForObject(uri, ResultHolder.class);
            if (result == null) {
                MSException.throwException(Translator.get("container_delete_fail"));
            }
            if (!result.isSuccess()) {
                MSException.throwException(result.getMessage());
            }
        });
    }

    public static String getBaseUrl() {
        return BASE_URL;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }

    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public RestTemplate getRestTemplateWithTimeOut() {
        return restTemplateWithTimeOut;
    }

    public void setRestTemplateWithTimeOut(RestTemplate restTemplateWithTimeOut) {
        this.restTemplateWithTimeOut = restTemplateWithTimeOut;
    }


    public List<TestResource> getResourceList() {
        return resourceList;
    }

}
