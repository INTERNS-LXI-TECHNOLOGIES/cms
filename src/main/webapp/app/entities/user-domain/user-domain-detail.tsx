import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-domain.reducer';
import { IUserDomain } from 'app/shared/model/user-domain.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserDomainDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserDomainDetail extends React.Component<IUserDomainDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userDomainEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            UserDomain [<b>{userDomainEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="firstName">First Name</span>
            </dt>
            <dd>{userDomainEntity.firstName}</dd>
            <dt>
              <span id="lastName">Last Name</span>
            </dt>
            <dd>{userDomainEntity.lastName}</dd>
            <dt>
              <span id="email">Email</span>
            </dt>
            <dd>{userDomainEntity.email}</dd>
            <dt>
              <span id="password">Password</span>
            </dt>
            <dd>{userDomainEntity.password}</dd>
            <dt>
              <span id="department">Department</span>
            </dt>
            <dd>{userDomainEntity.department}</dd>
            <dt>
              <span id="semester">Semester</span>
            </dt>
            <dd>{userDomainEntity.semester}</dd>
            <dt>
              <span id="contactNumber">Contact Number</span>
            </dt>
            <dd>{userDomainEntity.contactNumber}</dd>
            <dt>Address</dt>
            <dd>{userDomainEntity.addressId ? userDomainEntity.addressId : ''}</dd>
            <dt>Roles</dt>
            <dd>
              {userDomainEntity.roles
                ? userDomainEntity.roles.map((val, i) => (
                    <span key={val.id}>
                      <a>{val.id}</a>
                      {i === userDomainEntity.roles.length - 1 ? '' : ', '}
                    </span>
                  ))
                : null}
            </dd>
          </dl>
          <Button tag={Link} to="/entity/user-domain" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-domain/${userDomainEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userDomain }: IRootState) => ({
  userDomainEntity: userDomain.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserDomainDetail);
