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
import { getEntity, updateEntity, createEntity, reset } from './user-role.reducer';
import { IUserRole } from 'app/shared/model/user-role.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserRoleUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserRoleUpdateState {
  isNew: boolean;
  userIdId: string;
}

export class UserRoleUpdate extends React.Component<IUserRoleUpdateProps, IUserRoleUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      userIdId: '0',
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
    if (errors.length === 0) {
      const { userRoleEntity } = this.props;
      const entity = {
        ...userRoleEntity,
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
    this.props.history.push('/entity/user-role');
  };

  render() {
    const { userRoleEntity, userDomains, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartAcademicSystemApp.userRole.home.createOrEditLabel">Create or edit a UserRole</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userRoleEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="user-role-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="roleLabel">Role</Label>
                  <AvInput
                    id="user-role-role"
                    type="select"
                    className="form-control"
                    name="role"
                    value={(!isNew && userRoleEntity.role) || 'STUDENT'}
                  >
                    <option value="STUDENT">STUDENT</option>
                    <option value="FACULTY">FACULTY</option>
                    <option value="ADMIN">ADMIN</option>
                    <option value="PLACEMENT_OFFICER">PLACEMENT_OFFICER</option>
                    <option value="HOD">HOD</option>
                    <option value="TUTOR">TUTOR</option>
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-role" replace color="info">
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
  userRoleEntity: storeState.userRole.entity,
  loading: storeState.userRole.loading,
  updating: storeState.userRole.updating,
  updateSuccess: storeState.userRole.updateSuccess
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
)(UserRoleUpdate);
