import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './mensagem-enviada.reducer';

export const MensagemEnviadaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const mensagemEnviadaEntity = useAppSelector(state => state.notificationsystem.mensagemEnviada.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="mensagemEnviadaDetailsHeading">Mensagem Enviada</h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">Código</span>
          </dt>
          <dd>{mensagemEnviadaEntity.id}</dd>
          <dt>
            <span id="dataEnvio">Data Envio</span>
          </dt>
          <dd>
            {mensagemEnviadaEntity.dataEnvio ? (
              <TextFormat value={mensagemEnviadaEntity.dataEnvio} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{mensagemEnviadaEntity.status}</dd>
          <dt>
            <span id="clienteId">Cliente Id</span>
          </dt>
          <dd>{mensagemEnviadaEntity.clienteId}</dd>
          <dt>
            <span id="templateId">Template Id</span>
          </dt>
          <dd>{mensagemEnviadaEntity.templateId}</dd>
          <dt>Cliente</dt>
          <dd>{mensagemEnviadaEntity.cliente ? mensagemEnviadaEntity.cliente.id : ''}</dd>
          <dt>Template</dt>
          <dd>{mensagemEnviadaEntity.template ? mensagemEnviadaEntity.template.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/notificationsystem/mensagem-enviada" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Voltar</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/notificationsystem/mensagem-enviada/${mensagemEnviadaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Editar</span>
        </Button>
      </Col>
    </Row>
  );
};

export default MensagemEnviadaDetail;
