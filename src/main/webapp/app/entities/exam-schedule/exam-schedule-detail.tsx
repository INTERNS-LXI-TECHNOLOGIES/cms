import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './exam-schedule.reducer';
import { IExamSchedule } from 'app/shared/model/exam-schedule.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExamScheduleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ExamScheduleDetail extends React.Component<IExamScheduleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { examScheduleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            ExamSchedule [<b>{examScheduleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="title">Title</span>
            </dt>
            <dd>{examScheduleEntity.title}</dd>
            <dt>
              <span id="startDate">Start Date</span>
            </dt>
            <dd>
              <TextFormat value={examScheduleEntity.startDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="endDate">End Date</span>
            </dt>
            <dd>
              <TextFormat value={examScheduleEntity.endDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="active">Active</span>
            </dt>
            <dd>{examScheduleEntity.active ? 'true' : 'false'}</dd>
            <dt>
              <span id="department">Department</span>
            </dt>
            <dd>{examScheduleEntity.department}</dd>
            <dt>
              <span id="semester">Semester</span>
            </dt>
            <dd>{examScheduleEntity.semester}</dd>
          </dl>
          <Button tag={Link} to="/entity/exam-schedule" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/exam-schedule/${examScheduleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ examSchedule }: IRootState) => ({
  examScheduleEntity: examSchedule.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExamScheduleDetail);
