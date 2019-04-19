import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from './user-management';
// prettier-ignore
import userDomain, {
  UserDomainState
} from 'app/entities/user-domain/user-domain.reducer';
// prettier-ignore
import userRole, {
  UserRoleState
} from 'app/entities/user-role/user-role.reducer';
// prettier-ignore
import address, {
  AddressState
} from 'app/entities/address/address.reducer';
// prettier-ignore
import event, {
  EventState
} from 'app/entities/event/event.reducer';
// prettier-ignore
import qualification, {
  QualificationState
} from 'app/entities/qualification/qualification.reducer';
// prettier-ignore
import leaveApplication, {
  LeaveApplicationState
} from 'app/entities/leave-application/leave-application.reducer';
// prettier-ignore
import attatchment, {
  AttatchmentState
} from 'app/entities/attatchment/attatchment.reducer';
// prettier-ignore
import examSchedule, {
  ExamScheduleState
} from 'app/entities/exam-schedule/exam-schedule.reducer';
// prettier-ignore
import exam, {
  ExamState
} from 'app/entities/exam/exam.reducer';
// prettier-ignore
import studyMaterial, {
  StudyMaterialState
} from 'app/entities/study-material/study-material.reducer';
// prettier-ignore
import examHall, {
  ExamHallState
} from 'app/entities/exam-hall/exam-hall.reducer';
// prettier-ignore
import subject, {
  SubjectState
} from 'app/entities/subject/subject.reducer';
// prettier-ignore
import assignment, {
  AssignmentState
} from 'app/entities/assignment/assignment.reducer';
// prettier-ignore
import userDomain, {
  UserDomainState
} from 'app/entities/user-domain/user-domain.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly userDomain: UserDomainState;
  readonly userRole: UserRoleState;
  readonly address: AddressState;
  readonly event: EventState;
  readonly qualification: QualificationState;
  readonly leaveApplication: LeaveApplicationState;
  readonly attatchment: AttatchmentState;
  readonly examSchedule: ExamScheduleState;
  readonly exam: ExamState;
  readonly studyMaterial: StudyMaterialState;
  readonly examHall: ExamHallState;
  readonly subject: SubjectState;
  readonly assignment: AssignmentState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  userDomain,
  userRole,
  address,
  event,
  qualification,
  leaveApplication,
  attatchment,
  examSchedule,
  exam,
  studyMaterial,
  examHall,
  subject,
  assignment,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar
});

export default rootReducer;
