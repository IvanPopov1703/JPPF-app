package org.jppf.application.template;

import org.jppf.node.protocol.AbstractTask;

public class TaskSum extends AbstractTask<Integer> {

    private Integer num;

    public TaskSum(Integer num) {
        System.out.println("Init TaskSum");
        this.num = num;
    }

    @Override
    public void run() {
        System.out.println("Processing task sum");
        num = num * 2;
        setResult(num);
    }
}
