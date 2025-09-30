import React from 'react';
import { Route } from 'react-router';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Agendamento from './agendamento';
import AgendamentoDetail from './agendamento-detail';
import AgendamentoUpdate from './agendamento-update';
import AgendamentoDeleteDialog from './agendamento-delete-dialog';

const AgendamentoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Agendamento />} />
    <Route path="new" element={<AgendamentoUpdate />} />
    <Route path=":id">
      <Route index element={<AgendamentoDetail />} />
      <Route path="edit" element={<AgendamentoUpdate />} />
      <Route path="delete" element={<AgendamentoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AgendamentoRoutes;
