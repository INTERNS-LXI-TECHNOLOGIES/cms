export interface IAttatchment {
  id?: number;
  fileContentType?: string;
  file?: any;
  leaveApplicationId?: number;
}

export const defaultValue: Readonly<IAttatchment> = {};
