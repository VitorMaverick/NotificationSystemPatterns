import React from 'react';
import { Route } from 'react-router'; // eslint-disable-line

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import { ReducersMapObject, combineReducers } from '@reduxjs/toolkit';

import getStore from 'app/config/store';

import entitiesReducers from './reducers';

import Cliente from './notificationSystem/cliente';
import Segmento from './notificationSystem/segmento';
import Agendamento from './notificationSystem/agendamento';
import Template from './notificationSystem/template';
import MensagemEnviada from './notificationSystem/mensagem-enviada';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  const store = getStore();
  store.injectReducer('notificationsystem', combineReducers(entitiesReducers as ReducersMapObject));
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="/cliente/*" element={<Cliente />} />
        <Route path="/segmento/*" element={<Segmento />} />
        <Route path="/agendamento/*" element={<Agendamento />} />
        <Route path="/template/*" element={<Template />} />
        <Route path="/mensagem-enviada/*" element={<MensagemEnviada />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
