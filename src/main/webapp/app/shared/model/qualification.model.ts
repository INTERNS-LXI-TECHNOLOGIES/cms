export interface IQualification {
  id?: number;
  grade?: string;
  year?: number;
  university?: string;
  marks?: number;
  percentage?: number;
  userDomainId?: number;
}

export const defaultValue: Readonly<IQualification> = {};
