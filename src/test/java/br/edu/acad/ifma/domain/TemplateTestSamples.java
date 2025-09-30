package br.edu.acad.ifma.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class TemplateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Template getTemplateSample1() {
        return new Template()
            .id(1L)
            .nome("nome1")
            .tituloMensagem("tituloMensagem1")
            .corpoMensagem("corpoMensagem1")
            .tipoMensagem("tipoMensagem1");
    }

    public static Template getTemplateSample2() {
        return new Template()
            .id(2L)
            .nome("nome2")
            .tituloMensagem("tituloMensagem2")
            .corpoMensagem("corpoMensagem2")
            .tipoMensagem("tipoMensagem2");
    }

    public static Template getTemplateRandomSampleGenerator() {
        return new Template()
            .id(longCount.incrementAndGet())
            .nome(UUID.randomUUID().toString())
            .tituloMensagem(UUID.randomUUID().toString())
            .corpoMensagem(UUID.randomUUID().toString())
            .tipoMensagem(UUID.randomUUID().toString());
    }
}
