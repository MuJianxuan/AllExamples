package org.example.liteflow.executeprocess.demo;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@Component("b")
public class BCmp extends NodeComponent {

    @Override
    public void process() {
        //do your business
        System.out.println("b");
    }
}

