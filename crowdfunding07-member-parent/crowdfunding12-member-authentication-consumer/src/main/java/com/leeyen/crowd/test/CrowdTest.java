package com.leeyen.crowd.test;

import com.aliyun.api.gateway.demo.util.HttpUtils;
import com.leeyen.crowd.util.CrowdUtil;
import com.leeyen.crowd.util.ResultEntity;
import org.apache.http.HttpResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CrowdTest {

    private Logger logger = LoggerFactory.getLogger(CrowdTest.class);

    @Test
    public void testSendMessage() {
        String host = "https://dfsns.market.alicloudapi.com";

        String path = "/data/send_sms";

        String method = "POST";

        String appcode = "f7ce7b518cb3486a841bf5b57b21d221";

        String template_id = "TPL_0000";

        String phoneNum = "13871705950";

        ResultEntity<String> resultEntity = CrowdUtil.sendShortMessage(host, path, method, phoneNum, appcode, template_id);

        logger.info(resultEntity.toString());
    }
}
