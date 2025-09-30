package br.edu.acad.ifma.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SegmentoTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Segmento getSegmentoSample1() {
        return new Segmento().id(1L).nome("nome1").descricao("descricao1").canalEnvio("canalEnvio1").agendamentoId(1L);
    }

    public static Segmento getSegmentoSample2() {
        return new Segmento().id(2L).nome("nome2").descricao("descricao2").canalEnvio("canalEnvio2").agendamentoId(2L);
    }

    public static Segmento getSegmentoRandomSampleGenerator() {
        return new Segmento()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .descricao(UUID.randomUUID().toString())
            .canalEnvio(UUID.randomUUID().toString())
            .agendamentoId(longCount.incrementAndGet());
    }
}
