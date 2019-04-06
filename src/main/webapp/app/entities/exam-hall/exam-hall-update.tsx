import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IExam } from 'app/shared/model/exam.model';
import { getEntities as getExams } from 'app/entities/exam/exam.reducer';
import { getEntity, updateEntity, createEntity, reset } from './exam-hall.reducer';
import { IExamHall } from 'app/shared/model/exam-hall.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExamHallUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IExamHallUpdateState {
  isNew: boolean;
  examsId: string;
}

export class ExamHallUpdate extends React.Component<IExamHallUpdateProps, IExamHallUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      examsId: '0',
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

    this.props.getExams();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { examHallEntity } = this.props;
      const entity = {
        ...examHallEntity,
        ...values
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/exam-hall');
  };

  render() {
    const { examHallEntity, exams, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.examHall.home.createOrEditLabel">Create or edit a ExamHall</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : examHallEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="exam-hall-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="hallNumberLabel" for="hallNumber">
                    Hall Number
                  </Label>
                  <AvField id="exam-hall-hallNumber" type="string" className="form-control" name="hallNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="batchLabel" for="batch">
                    Batch
                  </Label>
                  <AvField id="exam-hall-batch" type="text" name="batch" />
                </AvGroup>
                <AvGroup>
                  <Label id="rollNumFromLabel" for="rollNumFrom">
                    Roll Num From
                  </Label>
                  <AvField id="exam-hall-rollNumFrom" type="string" className="form-control" name="rollNumFrom" />
                </AvGroup>
                <AvGroup>
                  <Label id="rollNumToLabel" for="rollNumTo">
                    Roll Num To
                  </Label>
                  <AvField id="exam-hall-rollNumTo" type="string" className="form-control" name="rollNumTo" />
                </AvGroup>
                <AvGroup>
                  <Label id="invigialtorLabel" for="invigialtor">
                    Invigialtor
                  </Label>
                  <AvField id="exam-hall-invigialtor" type="text" name="invigialtor" />
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/exam-hall" replace color="info">
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
  exams: storeState.exam.entities,
  examHallEntity: storeState.examHall.entity,
  loading: storeState.examHall.loading,
  updating: storeState.examHall.updating,
  updateSuccess: storeState.examHall.updateSuccess
});

const mapDispatchToProps = {
  getExams,
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
)(ExamHallUpdate);
