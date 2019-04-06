import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './exam.reducer';
import { IExam } from 'app/shared/model/exam.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExamDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class ExamDetail extends React.Component<IExamDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { examEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            Exam [<b>{examEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="examDate">Exam Date</span>
            </dt>
            <dd>
              <TextFormat value={examEntity.examDate} type="date" format={APP_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="startingTime">Starting Time</span>
            </dt>
            <dd>{examEntity.startingTime}</dd>
            <dt>
              <span id="duration">Duration</span>
            </dt>
            <dd>{examEntity.duration}</dd>
            <dt>Exam Schedule</dt>
            <dd>{examEntity.examScheduleId ? examEntity.examScheduleId : ''}</dd>
            <dt>Subject</dt>
            <dd>{examEntity.subjectId ? examEntity.subjectId : ''}</dd>
            <dt>Halls</dt>
            <dd>
              {examEntity.halls
                ? examEntity.halls.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === examEntity.halls.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/exam" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/exam/${examEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ exam }: IRootState) => ({
  examEntity: exam.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExamDetail);
