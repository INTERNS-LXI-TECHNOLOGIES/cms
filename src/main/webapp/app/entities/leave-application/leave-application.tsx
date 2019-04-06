import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './leave-application.reducer';
import { ILeaveApplication } from 'app/shared/model/leave-application.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface ILeaveApplicationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type ILeaveApplicationState = IPaginationBaseState;

export class LeaveApplication extends React.Component<ILeaveApplicationProps, ILeaveApplicationState> {
  state: ILeaveApplicationState = {
    ...getSortState(this.props.location, ITEMS_PER_PAGE)
  };

  componentDidMount() {
    this.getEntities();
  }

  sort = prop => () => {
    this.setState(
      {
        order: this.state.order === 'asc' ? 'desc' : 'asc',
        sort: prop
      },
      () => this.sortEntities()
    );
  };

  sortEntities() {
    this.getEntities();
    this.props.history.push(`${this.props.location.pathname}?page=${this.state.activePage}&sort=${this.state.sort},${this.state.order}`);
  }

  handlePagination = activePage => this.setState({ activePage }, () => this.sortEntities());

  getEntities = () => {
    const { activePage, itemsPerPage, sort, order } = this.state;
    this.props.getEntities(activePage - 1, itemsPerPage, `${sort},${order}`);
  };

  render() {
    const { leaveApplicationList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="leave-application-heading">
          Leave Applications
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Leave Application
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('reason')}>
                  Reason <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('fromDate')}>
                  From Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('toDate')}>
                  To Date <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Applied By <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {leaveApplicationList.map((leaveApplication, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${leaveApplication.id}`} color="link" size="sm">
                      {leaveApplication.id}
                    </Button>
                  </td>
                  <td>{leaveApplication.reason}</td>
                  <td>
                    <TextFormat type="date" value={leaveApplication.fromDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={leaveApplication.toDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    {leaveApplication.appliedById ? (
                      <Link to={`user-domain/${leaveApplication.appliedById}`}>{leaveApplication.appliedById}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${leaveApplication.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${leaveApplication.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${leaveApplication.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
        <Row className="justify-content-center">
          <JhiPagination
            items={getPaginationItemsNumber(totalItems, this.state.itemsPerPage)}
            activePage={this.state.activePage}
            onSelect={this.handlePagination}
            maxButtons={5}
          />
        </Row>
      </div>
    );
  }
}

const mapStateToProps = ({ leaveApplication }: IRootState) => ({
  leaveApplicationList: leaveApplication.entities,
  totalItems: leaveApplication.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(LeaveApplication);
