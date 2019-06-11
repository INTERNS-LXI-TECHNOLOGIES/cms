import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './day-of-week.reducer';
import { IDayOfWeek } from 'app/shared/model/day-of-week.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IDayOfWeekProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IDayOfWeekState = IPaginationBaseState;

export class DayOfWeek extends React.Component<IDayOfWeekProps, IDayOfWeekState> {
  state: IDayOfWeekState = {
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
    const { dayOfWeekList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="day-of-week-heading">
          Day Of Weeks
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Day Of Week
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub1')}>
                  Sub 1 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub2')}>
                  Sub 2 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub3')}>
                  Sub 3 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub4')}>
                  Sub 4 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub5')}>
                  Sub 5 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub6')}>
                  Sub 6 <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('sub7')}>
                  Sub 7 <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  Time Table <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {dayOfWeekList.map((dayOfWeek, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${dayOfWeek.id}`} color="link" size="sm">
                      {dayOfWeek.id}
                    </Button>
                  </td>
                  <td>{dayOfWeek.sub1}</td>
                  <td>{dayOfWeek.sub2}</td>
                  <td>{dayOfWeek.sub3}</td>
                  <td>{dayOfWeek.sub4}</td>
                  <td>{dayOfWeek.sub5}</td>
                  <td>{dayOfWeek.sub6}</td>
                  <td>{dayOfWeek.sub7}</td>
                  <td>{dayOfWeek.timeTableId ? <Link to={`time-table/${dayOfWeek.timeTableId}`}>{dayOfWeek.timeTableId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${dayOfWeek.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dayOfWeek.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${dayOfWeek.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ dayOfWeek }: IRootState) => ({
  dayOfWeekList: dayOfWeek.entities,
  totalItems: dayOfWeek.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(DayOfWeek);
