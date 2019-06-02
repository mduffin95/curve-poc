package com.duffin.curvepoc.controller;

import com.duffin.curvepoc.Dto;
import com.duffin.curvepoc.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.util.Random;

@EnableScheduling
@Controller
public class BackendController {


    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private SimpMessagingTemplate template;

    private Random rand;

    public BackendController() {
        rand = new Random();
    }

    @Scheduled(fixedRate = 5000)
    public void dataStream() throws Exception {
        LOG.debug("Sending data");
        Dto dto = new Dto();

        for (String p: Point.points) {
            Point point = new Point(p, rand.nextInt(100));
            dto.values.add(point);
        }
        this.template.convertAndSend("/topic/data", dto);
    }
}
