import { IQualification } from 'app/shared/model/qualification.model';
import { IUserRole } from 'app/shared/model/user-role.model';

export const enum Department {
  CSE = 'CSE',
  ME = 'ME',
  ECE = 'ECE',
  EEE = 'EEE',
  CE = 'CE',
  OTHER = 'OTHER'
}

export const enum Semester {
  S1 = 'S1',
  S2 = 'S2',
  S3 = 'S3',
  S4 = 'S4',
  S5 = 'S5',
  S6 = 'S6',
  S7 = 'S7',
  S8 = 'S8',
  NA = 'NA'
}

export interface IUserDomain {
  id?: number;
  firstName?: string;
  lastName?: string;
  email?: string;
  password?: string;
  department?: Department;
  semester?: Semester;
  contactNumber?: number;
  addressId?: number;
  qualifications?: IQualification[];
  roles?: IUserRole[];
}

export const defaultValue: Readonly<IUserDomain> = {};
