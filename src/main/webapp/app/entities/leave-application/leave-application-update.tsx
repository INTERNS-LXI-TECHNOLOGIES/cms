import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IUserDomain } from 'app/shared/model/user-domain.model';
import { getEntities as getUserDomains } from 'app/entities/user-domain/user-domain.reducer';
import { getEntity, updateEntity, createEntity, reset } from './leave-application.reducer';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILeaveApplicationUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface ILeaveApplicationUpdateState {
  isNew: boolean;
  appliedById: string;
}

export class LeaveApplicationUpdate extends React.Component<ILeaveApplicationUpdateProps, ILeaveApplicationUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      appliedById: '0',
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

    this.props.getUserDomains();
  }

  saveEntity = (event, errors, values) => {
    values.fromDate = convertDateTimeToServer(values.fromDate);
    values.toDate = convertDateTimeToServer(values.toDate);

    if (errors.length === 0) {
      const { leaveApplicationEntity } = this.props;
      const entity = {
        ...leaveApplicationEntity,
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
    this.props.history.push('/entity/leave-application');
  };

  render() {
    const { leaveApplicationEntity, userDomains, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.leaveApplication.home.createOrEditLabel">Create or edit a LeaveApplication</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : leaveApplicationEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="leave-application-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="reasonLabel" for="reason">
                    Reason
                  </Label>
                  <AvField id="leave-application-reason" type="text" name="reason" />
                </AvGroup>
                <AvGroup>
                  <Label id="fromDateLabel" for="fromDate">
                    From Date
                  </Label>
                  <AvInput
                    id="leave-application-fromDate"
                    type="datetime-local"
                    className="form-control"
                    name="fromDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.leaveApplicationEntity.fromDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="toDateLabel" for="toDate">
                    To Date
                  </Label>
                  <AvInput
                    id="leave-application-toDate"
                    type="datetime-local"
                    className="form-control"
                    name="toDate"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.leaveApplicationEntity.toDate)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label for="appliedBy.id">Applied By</Label>
                  <AvInput id="leave-application-appliedBy" type="select" className="form-control" name="appliedById">
                    <option value="" key="0" />
                    {userDomains
                      ? userDomains.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/leave-application" replace color="info">
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
  userDomains: storeState.userDomain.entities,
  leaveApplicationEntity: storeState.leaveApplication.entity,
  loading: storeState.leaveApplication.loading,
  updating: storeState.leaveApplication.updating,
  updateSuccess: storeState.leaveApplication.updateSuccess
});

const mapDispatchToProps = {
  getUserDomains,
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
)(LeaveApplicationUpdate);
