import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './exam-schedule.reducer';
import { IExamSchedule } from 'app/shared/model/exam-schedule.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IExamScheduleProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IExamScheduleState = IPaginationBaseState;

export class ExamSchedule extends React.Component<IExamScheduleProps, IExamScheduleState> {
  state: IExamScheduleState = {
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
    const { examScheduleList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="exam-schedule-heading">
          Exam Schedules
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Exam Schedule
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('title')}>
                  Title <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('startDate')}>
                  Start Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('endDate')}>
                  End Date <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('active')}>
                  Active <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('department')}>
                  Department <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('semester')}>
                  Semester <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {examScheduleList.map((examSchedule, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${examSchedule.id}`} color="link" size="sm">
                      {examSchedule.id}
                    </Button>
                  </td>
                  <td>{examSchedule.title}</td>
                  <td>
                    <TextFormat type="date" value={examSchedule.startDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>
                    <TextFormat type="date" value={examSchedule.endDate} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{examSchedule.active ? 'true' : 'false'}</td>
                  <td>{examSchedule.department}</td>
                  <td>{examSchedule.semester}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${examSchedule.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${examSchedule.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${examSchedule.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ examSchedule }: IRootState) => ({
  examScheduleList: examSchedule.entities,
  totalItems: examSchedule.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ExamSchedule);
