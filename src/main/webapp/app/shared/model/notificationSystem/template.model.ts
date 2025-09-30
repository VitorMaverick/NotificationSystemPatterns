export interface ITemplate {
  id?: number;
  nome?: string;
  tituloMensagem?: string;
  corpoMensagem?: string;
  tipoMensagem?: string | null;
}

export const defaultValue: Readonly<ITemplate> = {};
