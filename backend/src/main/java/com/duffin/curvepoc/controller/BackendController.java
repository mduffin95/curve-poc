package com.duffin.curvepoc.controller;

import com.duffin.curvepoc.Dto;
import com.duffin.curvepoc.Bond;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;

import static java.time.temporal.ChronoUnit.YEARS;

@EnableScheduling
@Controller
public class BackendController {

    private Map<Long, Bond> bonds;


    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    @Autowired
    private SimpMessagingTemplate template;

    private Random rand;

    @PostConstruct
    public void init() {
        rand = new Random();
        bonds = new HashMap<>();

        LocalDate now = LocalDate.now();
        LocalDate date = now;
        double yield = 0.0;
        for (int i = 0; i < 10; i++) {
            date = date
                    .plusYears(rand.nextInt(3))
                    .plusMonths(rand.nextInt(6));
            yield += rand.nextDouble() * 5.0 / YEARS.between(now, date);
            Bond bond = new Bond(i, date, yield);
            bonds.put(bond.id, bond);
        }

    }

    @SubscribeMapping("/init")
    public Dto initialLoad() {
        LOG.debug("Sending data");
        Dto dto = new Dto();

        dto.values.putAll(bonds);
        return dto;
    }

    @Scheduled(fixedRate = 5000)
    public void dataStream() throws Exception {
        LOG.debug("Sending data");
        Dto dto = new Dto();

        long i = rand.nextInt(10);
        double x = 0.2;
        Bond b = bonds.get(i);
        b.yield *= ((1.0 - x / 2.0) + rand.nextDouble() * x);

        dto.values.put(b.id, b);
        this.template.convertAndSend("/topic/data", dto);
    }
}
