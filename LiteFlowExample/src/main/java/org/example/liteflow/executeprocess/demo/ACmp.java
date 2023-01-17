package org.example.liteflow.executeprocess.demo;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@Component("a")
public class ACmp extends NodeComponent {



    @Override
    public void process() {
        //do your business

        System.out.println("a");

        String requestData = (String) this.getRequestData();
        System.out.println("requestData："+ requestData);



    }
}
