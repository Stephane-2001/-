package com.leeyen.crowd.test;

import com.leeyen.crowd.util.CrowdUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class StringTest {

    @Test
    public void testMd5(){
        String source = "123456";
        String encoded = CrowdUtil.md5(source);
        System.out.println(encoded);
    }

}
