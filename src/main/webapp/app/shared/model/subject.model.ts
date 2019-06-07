import { IStudyMaterial } from 'app/shared/model/study-material.model';

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

export const enum Department {
  CSE = 'CSE',
  ME = 'ME',
  ECE = 'ECE',
  EEE = 'EEE',
  CE = 'CE',
  OTHER = 'OTHER'
}

export interface ISubject {
  id?: number;
  subjectCode?: string;
  semester?: Semester;
  department?: Department;
  materials?: IStudyMaterial[];
}

export const defaultValue: Readonly<ISubject> = {};
