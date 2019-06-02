package com.duffin.curvepoc.controller;

import com.duffin.curvepoc.Dto;
import com.duffin.curvepoc.Bond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.time.temporal.ChronoUnit.YEARS;

@EnableScheduling
@Controller
public class BackendController {

    private List<Bond> bonds;


    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private SimpMessagingTemplate template;

    private Random rand;

    @PostConstruct
    public void init() {
        rand = new Random();
        bonds = new ArrayList<>();

        LocalDate now = LocalDate.now();
        LocalDate date = now;
        double yield = 0.0;
        for (int i = 0; i < 10; i++ ) {
            date = date
                    .plusYears(rand.nextInt(3))
                    .plusMonths(rand.nextInt(6));
            yield += rand.nextDouble() * 5.0 / YEARS.between(now, date);
            Bond bond = new Bond(date, yield);
            bonds.add(bond);
        }

    }

    @Scheduled(fixedRate = 5000)
    public void dataStream() throws Exception {
        LOG.debug("Sending data");
        Dto dto = new Dto();

        int i = rand.nextInt(10);
        double x = 0.2;
        bonds.get(i).yield *= ((1.0 - x/2.0) + rand.nextDouble() * x);

        dto.values.addAll(bonds);
        this.template.convertAndSend("/topic/data", dto);
    }
}
