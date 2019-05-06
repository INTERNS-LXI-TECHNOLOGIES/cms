import { IUserDomain } from 'app/shared/model/user-domain.model';

export const enum Role {
  STUDENT = 'STUDENT',
  FACULTY = 'FACULTY',
  ADMIN = 'ADMIN',
  PLACEMENT_OFFICER = 'PLACEMENT_OFFICER',
  HOD = 'HOD',
  TUTOR = 'TUTOR'
}

export interface IUserRole {
  id?: number;
  role?: Role;
  userIds?: IUserDomain[];
}

export const defaultValue: Readonly<IUserRole> = {};
