import { ISegmento } from 'app/shared/model/notificationSystem/segmento.model';

export interface ICliente {
  id?: number;
  nome?: string;
  email?: string;
  telefone?: string | null;
  segmentoId?: number;
  segmento?: ISegmento | null;
}

export const defaultValue: Readonly<ICliente> = {};
