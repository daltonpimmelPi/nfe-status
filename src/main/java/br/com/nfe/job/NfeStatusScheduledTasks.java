package br.com.nfe.job;

import br.com.nfe.service.NfeStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class NfeStatusScheduledTasks {

    private final NfeStatusService nfeStatusService;

    private static final Logger log = LoggerFactory.getLogger(NfeStatusScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    public NfeStatusScheduledTasks(NfeStatusService nfeStatusService) {
        this.nfeStatusService = nfeStatusService;
    }

    @Scheduled(fixedRate = 300000)
    public void reportCurrentTime() {
        log.info("running {}", dateFormat.format(new Date()));
        nfeStatusService.process();
    }
}