package br.edu.acad.ifma.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AgendamentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Agendamento getAgendamentoSample1() {
        return new Agendamento().id(1L).periodicidade("periodicidade1").horaEnvio("horaEnvio1");
    }

    public static Agendamento getAgendamentoSample2() {
        return new Agendamento().id(2L).periodicidade("periodicidade2").horaEnvio("horaEnvio2");
    }

    public static Agendamento getAgendamentoRandomSampleGenerator() {
        return new Agendamento()
            .id(longCount.incrementAndGet())
            .periodicidade(UUID.randomUUID().toString())
            .horaEnvio(UUID.randomUUID().toString());
    }
}
