package br.edu.acad.ifma.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MensagemEnviadaTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static MensagemEnviada getMensagemEnviadaSample1() {
        return new MensagemEnviada().id(1L).status("status1").clienteId(1L).templateId(1L);
    }

    public static MensagemEnviada getMensagemEnviadaSample2() {
        return new MensagemEnviada().id(2L).status("status2").clienteId(2L).templateId(2L);
    }

    public static MensagemEnviada getMensagemEnviadaRandomSampleGenerator() {
        return new MensagemEnviada()
            .id(longCount.incrementAndGet())
            .status(UUID.randomUUID().toString())
            .clienteId(longCount.incrementAndGet())
            .templateId(longCount.incrementAndGet());
    }
}
