import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IAddress } from 'app/shared/model/address.model';
import { getEntities as getAddresses } from 'app/entities/address/address.reducer';
import { IUserRole } from 'app/shared/model/user-role.model';
import { getEntities as getUserRoles } from 'app/entities/user-role/user-role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './user-domain.reducer';
import { IUserDomain } from 'app/shared/model/user-domain.model';
// tslint:disable-next-line:no-unused-variable
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IUserDomainUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IUserDomainUpdateState {
  isNew: boolean;
  idsroles: any[];
  addressId: string;
}

export class UserDomainUpdate extends React.Component<IUserDomainUpdateProps, IUserDomainUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idsroles: [],
      addressId: '0',
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

    this.props.getAddresses();
    this.props.getUserRoles();
  }

  saveEntity = (event, errors, values) => {
    values.dob = convertDateTimeToServer(values.dob);

    if (errors.length === 0) {
      const { userDomainEntity } = this.props;
      const entity = {
        ...userDomainEntity,
        ...values,
        roles: mapIdList(values.roles)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/entity/user-domain');
  };

  render() {
    const { userDomainEntity, addresses, userRoles, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="smartInformationSystemApp.userDomain.home.createOrEditLabel">Create or edit a UserDomain</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : userDomainEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="id">ID</Label>
                    <AvInput id="user-domain-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="regNumLabel" for="regNum">
                    Reg Num
                  </Label>
                  <AvField
                    id="user-domain-regNum"
                    type="text"
                    name="regNum"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="firstNameLabel" for="firstName">
                    First Name
                  </Label>
                  <AvField id="user-domain-firstName" type="text" name="firstName" />
                </AvGroup>
                <AvGroup>
                  <Label id="lastNameLabel" for="lastName">
                    Last Name
                  </Label>
                  <AvField id="user-domain-lastName" type="text" name="lastName" />
                </AvGroup>
                <AvGroup>
                  <Label id="dobLabel" for="dob">
                    Dob
                  </Label>
                  <AvInput
                    id="user-domain-dob"
                    type="datetime-local"
                    className="form-control"
                    name="dob"
                    placeholder={'YYYY-MM-DD HH:mm'}
                    value={isNew ? null : convertDateTimeFromServer(this.props.userDomainEntity.dob)}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="emailLabel" for="email">
                    Email
                  </Label>
                  <AvField
                    id="user-domain-email"
                    type="text"
                    name="email"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="passwordLabel" for="password">
                    Password
                  </Label>
                  <AvField
                    id="user-domain-password"
                    type="text"
                    name="password"
                    validate={{
                      required: { value: true, errorMessage: 'This field is required.' }
                    }}
                  />
                </AvGroup>
                <AvGroup>
                  <Label id="departmentLabel">Department</Label>
                  <AvInput
                    id="user-domain-department"
                    type="select"
                    className="form-control"
                    name="department"
                    value={(!isNew && userDomainEntity.department) || 'CSE'}
                  >
                    <option value="CSE">CSE</option>
                    <option value="ME">ME</option>
                    <option value="ECE">ECE</option>
                    <option value="EEE">EEE</option>
                    <option value="CE">CE</option>
                    <option value="OTHER">OTHER</option>
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label id="semesterLabel">Semester</Label>
                  <AvInput
                    id="user-domain-semester"
                    type="select"
                    className="form-control"
                    name="semester"
                    value={(!isNew && userDomainEntity.semester) || 'S1'}
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
                  <Label id="contactNumberLabel" for="contactNumber">
                    Contact Number
                  </Label>
                  <AvField id="user-domain-contactNumber" type="string" className="form-control" name="contactNumber" />
                </AvGroup>
                <AvGroup>
                  <Label id="activatedLabel" check>
                    <AvInput id="user-domain-activated" type="checkbox" className="form-control" name="activated" />
                    Activated
                  </Label>
                </AvGroup>
                <AvGroup>
                  <Label for="address.id">Address</Label>
                  <AvInput id="user-domain-address" type="select" className="form-control" name="addressId">
                    <option value="" key="0" />
                    {addresses
                      ? addresses.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <AvGroup>
                  <Label for="userRoles">Roles</Label>
                  <AvInput
                    id="user-domain-roles"
                    type="select"
                    multiple
                    className="form-control"
                    name="roles"
                    value={userDomainEntity.roles && userDomainEntity.roles.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {userRoles
                      ? userRoles.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.id}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/entity/user-domain" replace color="info">
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
  addresses: storeState.address.entities,
  userRoles: storeState.userRole.entities,
  userDomainEntity: storeState.userDomain.entity,
  loading: storeState.userDomain.loading,
  updating: storeState.userDomain.updating,
  updateSuccess: storeState.userDomain.updateSuccess
});

const mapDispatchToProps = {
  getAddresses,
  getUserRoles,
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
)(UserDomainUpdate);
