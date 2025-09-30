import { IAgendamento } from 'app/shared/model/notificationSystem/agendamento.model';

export interface ISegmento {
  id?: number;
  nome?: string;
  descricao?: string | null;
  canalEnvio?: string;
  agendamentoId?: number;
  agendamento?: IAgendamento | null;
}

export const defaultValue: Readonly<ISegmento> = {};
