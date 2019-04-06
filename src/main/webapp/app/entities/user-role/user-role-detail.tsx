import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './user-role.reducer';
import { IUserRole } from 'app/shared/model/user-role.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUserRoleDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class UserRoleDetail extends React.Component<IUserRoleDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { userRoleEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            UserRole [<b>{userRoleEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="role">Role</span>
            </dt>
            <dd>{userRoleEntity.role}</dd>
          </dl>
          <Button tag={Link} to="/entity/user-role" replace color="info">
            <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/user-role/${userRoleEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ userRole }: IRootState) => ({
  userRoleEntity: userRole.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(UserRoleDetail);
