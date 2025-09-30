import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import {} from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './template.reducer';

export const TemplateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const templateEntity = useAppSelector(state => state.notificationsystem.template.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateDetailsHeading">Template</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{templateEntity.id}</dd>
          <dt>
            <span id="nome">Nome</span>
          </dt>
          <dd>{templateEntity.nome}</dd>
          <dt>
            <span id="tituloMensagem">Titulo Mensagem</span>
          </dt>
          <dd>{templateEntity.tituloMensagem}</dd>
          <dt>
            <span id="corpoMensagem">Corpo Mensagem</span>
          </dt>
          <dd>{templateEntity.corpoMensagem}</dd>
          <dt>
            <span id="tipoMensagem">Tipo Mensagem</span>
          </dt>
          <dd>{templateEntity.tipoMensagem}</dd>
        </dl>
        <Button tag={Link} to="/notificationsystem/template" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notificationsystem/template/${templateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemplateDetail;
