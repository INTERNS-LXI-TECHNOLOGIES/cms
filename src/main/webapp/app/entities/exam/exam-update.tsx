import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IExamSchedule } from 'app/shared/model/exam-schedule.model';
import { getEntities as getExamSchedules } from 'app/entities/exam-schedule/exam-schedule.reducer';
import { ISubject } from 'app/shared/model/subject.model';
import { getEntities as getSubjects } from 'app/entities/subject/subject.reducer';
import { IExamHall } from 'app/shared/model/exam-hall.model';
import { getEntities as getExamHalls } from 'app/entities/exam-hall/exam-hall.reducer';
import { getEntity, updateEntity, createEntity, reset } from './exam.reducer';
import { IExam } from 'app/shared/model/exam.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExamUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IExamUpdateState {
  isNew: boolean;
  idshalls: any[];
  examScheduleId: string;
  subjectId: string;
}

export class ExamUpdate extends React.Component<IExamUpdateProps, IExamUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idshalls: [],
      examScheduleId: '0',
      subjectId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getExamSchedules();
    this.props.getSubjects();
    this.props.getExamHalls();
  }

  saveEntity = (event, errors, values) => {
    values.examDate = convertDateTimeToServer(values.examDate);

    if (errors.length === 0) {
      const { examEntity } = this.props;
      const entity = {
        ...examEntity,
        ...values,
        halls: mapIdList(values.halls)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/exam');
  };

  render() {
    const { examEntity, examSchedules, subjects, examHalls, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.exam.home.createOrEditLabel">Create or edit a Exam</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : examEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="exam-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="examDateLabel" for="examDate">
                    Exam Date
                  </Label>
                  <AvInput
                    id="exam-examDate"
                    type="datetime-local"
                    className="form-control"
                    name="examDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.examEntity.examDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="startingTimeLabel" for="startingTime">
                    Starting Time
                  </Label>
                  <AvField id="exam-startingTime" type="text" name="startingTime" />
                </AvGroup>
                <AvGroup>
                  <Label id="durationLabel" for="duration">
                    Duration
                  </Label>
                  <AvField id="exam-duration" type="text" name="duration" />
                </AvGroup>
                <AvGroup>
                  <Label for="examSchedule.id">Exam Schedule</Label>
                  <AvInput id="exam-examSchedule" type="select" className="form-control" name="examScheduleId">
                    <option value="" key="0" />
                    {examSchedules
                      ? examSchedules.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="subject.id">Subject</Label>
                  <AvInput id="exam-subject" type="select" className="form-control" name="subjectId">
                    <option value="" key="0" />
                    {subjects
                      ? subjects.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="examHalls">Halls</Label>
                  <AvInput
                    id="exam-halls"
                    type="select"
                    multiple
                    className="form-control"
                    name="halls"
                    value={examEntity.halls && examEntity.halls.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {examHalls
                      ? examHalls.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/exam" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  examSchedules: storeState.examSchedule.entities,
  subjects: storeState.subject.entities,
  examHalls: storeState.examHall.entities,
  examEntity: storeState.exam.entity,
  loading: storeState.exam.loading,
  updating: storeState.exam.updating,
  updateSuccess: storeState.exam.updateSuccess
});

const mapDispatchToProps = {
  getExamSchedules,
  getSubjects,
  getExamHalls,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExamUpdate);
