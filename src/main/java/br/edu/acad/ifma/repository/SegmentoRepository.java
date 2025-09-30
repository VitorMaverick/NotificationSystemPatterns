package br.edu.acad.ifma.repository;

import br.edu.acad.ifma.domain.Segmento;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Segmento entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SegmentoRepository extends JpaRepository<Segmento, Long> {}
