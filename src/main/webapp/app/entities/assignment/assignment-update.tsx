import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './assignment.reducer';
import { IAssignment } from 'app/shared/model/assignment.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAssignmentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IAssignmentUpdateState {
  isNew: boolean;
}

export class AssignmentUpdate extends React.Component<IAssignmentUpdateProps, IAssignmentUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
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
  }

  saveEntity = (event, errors, values) => {
    values.submissionDate = convertDateTimeToServer(values.submissionDate);

    if (errors.length === 0) {
      const { assignmentEntity } = this.props;
      const entity = {
        ...assignmentEntity,
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
    this.props.history.push('/entity/assignment');
  };

  render() {
    const { assignmentEntity, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartInformationSystemApp.assignment.home.createOrEditLabel">Create or edit a Assignment</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : assignmentEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="assignment-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="submissionDateLabel" for="submissionDate">
                    Submission Date
                  </Label>
                  <AvInput
                    id="assignment-submissionDate"
                    type="datetime-local"
                    className="form-control"
                    name="submissionDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.assignmentEntity.submissionDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="topicLabel" for="topic">
                    Topic
                  </Label>
                  <AvField id="assignment-topic" type="text" name="topic" />
                </AvGroup>
                <AvGroup>
                  <Label id="semesterLabel">Semester</Label>
                  <AvInput
                    id="assignment-semester"
                    type="select"
                    className="form-control"
                    name="semester"
                    value={(!isNew && assignmentEntity.semester) || 'S1'}
                  >
                    <option value="S1">S1</option>
                    <option value="S2">S2</option>
                    <option value="S3">S3</option>
                    <option value="S4">S4</option>
                    <option value="S5">S5</option>
                    <option value="S6">S6</option>
                    <option value="S7">S7</option>
                    <option value="S8">S8</option>
                    <option value="NA">NA</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="departmentLabel">Department</Label>
                  <AvInput
                    id="assignment-department"
                    type="select"
                    className="form-control"
                    name="department"
                    value={(!isNew && assignmentEntity.department) || 'CSE'}
                  >
                    <option value="CSE">CSE</option>
                    <option value="ME">ME</option>
                    <option value="ECE">ECE</option>
                    <option value="EEE">EEE</option>
                    <option value="CE">CE</option>
                    <option value="OTHER">OTHER</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/assignment" replace color="info">
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
  assignmentEntity: storeState.assignment.entity,
  loading: storeState.assignment.loading,
  updating: storeState.assignment.updating,
  updateSuccess: storeState.assignment.updateSuccess
});

const mapDispatchToProps = {
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
)(AssignmentUpdate);
