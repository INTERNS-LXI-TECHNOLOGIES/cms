export interface IStudyMaterial {
  id?: number;
  title?: string;
  module?: number;
  fileContentType?: string;
  file?: any;
  subjectId?: number;
}

export const defaultValue: Readonly<IStudyMaterial> = {};
