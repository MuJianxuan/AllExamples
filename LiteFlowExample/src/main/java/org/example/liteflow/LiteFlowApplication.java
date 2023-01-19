package org.example.liteflow;

import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import com.yomahub.liteflow.slot.Slot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Optional;

/**
 * @author Rao
 * @Date 2023/01/16
 **/
@SpringBootApplication
public class LiteFlowApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(LiteFlowApplication.class, args);
        Optional.of(applicationContext.getBean(FlowExecutor.class)).ifPresent( flowExecutor -> {
            LiteflowResponse liteflowResponse = flowExecutor.execute2Resp("chain1", "arg");

//            flowExecutor.execute2Resp("")

        });

    }
}
