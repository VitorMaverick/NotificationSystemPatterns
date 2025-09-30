import React, { useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Col, Row } from 'reactstrap';
import { ValidatedField, ValidatedForm, isNumber } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities as getClientes } from 'app/entities/notificationSystem/cliente/cliente.reducer';
import { getEntities as getTemplates } from 'app/entities/notificationSystem/template/template.reducer';
import { createEntity, getEntity, reset, updateEntity } from './mensagem-enviada.reducer';

export const MensagemEnviadaUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const clientes = useAppSelector(state => state.notificationsystem.cliente.entities);
  const templates = useAppSelector(state => state.notificationsystem.template.entities);
  const mensagemEnviadaEntity = useAppSelector(state => state.notificationsystem.mensagemEnviada.entity);
  const loading = useAppSelector(state => state.notificationsystem.mensagemEnviada.loading);
  const updating = useAppSelector(state => state.notificationsystem.mensagemEnviada.updating);
  const updateSuccess = useAppSelector(state => state.notificationsystem.mensagemEnviada.updateSuccess);

  const handleClose = () => {
    navigate(`/notificationsystem/mensagem-enviada${location.search}`);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getClientes({}));
    dispatch(getTemplates({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    if (values.id !== undefined && typeof values.id !== 'number') {
      values.id = Number(values.id);
    }
    values.dataEnvio = convertDateTimeToServer(values.dataEnvio);
    if (values.clienteId !== undefined && typeof values.clienteId !== 'number') {
      values.clienteId = Number(values.clienteId);
    }
    if (values.templateId !== undefined && typeof values.templateId !== 'number') {
      values.templateId = Number(values.templateId);
    }

    const entity = {
      ...mensagemEnviadaEntity,
      ...values,
      cliente: clientes.find(it => it.id.toString() === values.cliente?.toString()),
      template: templates.find(it => it.id.toString() === values.template?.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          dataEnvio: displayDefaultDateTime(),
        }
      : {
          ...mensagemEnviadaEntity,
          dataEnvio: convertDateTimeFromServer(mensagemEnviadaEntity.dataEnvio),
          cliente: mensagemEnviadaEntity?.cliente?.id,
          template: mensagemEnviadaEntity?.template?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="notificationSystemApp.notificationSystemMensagemEnviada.home.createOrEditLabel"
            data-cy="MensagemEnviadaCreateUpdateHeading"
          >
            Criar ou editar Mensagem Enviada
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField name="id" required readOnly id="mensagem-enviada-id" label="Código" validate={{ required: true }} />
              ) : null}
              <ValidatedField
                label="Data Envio"
                id="mensagem-enviada-dataEnvio"
                name="dataEnvio"
                data-cy="dataEnvio"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                }}
              />
              <ValidatedField label="Status" id="mensagem-enviada-status" name="status" data-cy="status" type="text" />
              <ValidatedField
                label="Cliente Id"
                id="mensagem-enviada-clienteId"
                name="clienteId"
                data-cy="clienteId"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField
                label="Template Id"
                id="mensagem-enviada-templateId"
                name="templateId"
                data-cy="templateId"
                type="text"
                validate={{
                  required: { value: true, message: 'O campo é obrigatório.' },
                  validate: v => isNumber(v) || 'Este campo é do tipo numérico.',
                }}
              />
              <ValidatedField id="mensagem-enviada-cliente" name="cliente" data-cy="cliente" label="Cliente" type="select">
                <option value="" key="0" />
                {clientes
                  ? clientes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="mensagem-enviada-template" name="template" data-cy="template" label="Template" type="select">
                <option value="" key="0" />
                {templates
                  ? templates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/notificationsystem/mensagem-enviada"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Voltar</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Salvar
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MensagemEnviadaUpdate;
