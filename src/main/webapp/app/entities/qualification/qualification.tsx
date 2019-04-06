import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { ICrudGetAllAction, getSortState, IPaginationBaseState, getPaginationItemsNumber, JhiPagination } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './qualification.reducer';
import { IQualification } from 'app/shared/model/qualification.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IQualificationProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export type IQualificationState = IPaginationBaseState;

export class Qualification extends React.Component<IQualificationProps, IQualificationState> {
  state: IQualificationState = {
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
    const { qualificationList, match, totalItems } = this.props;
    return (
      <div>
        <h2 id="qualification-heading">
          Qualifications
          <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
            <FontAwesomeIcon icon="plus" />
            &nbsp; Create new Qualification
          </Link>
        </h2>
        <div className="table-responsive">
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={this.sort('id')}>
                  ID <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('grade')}>
                  Grade <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('year')}>
                  Year <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('university')}>
                  University <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('marks')}>
                  Marks <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={this.sort('percentage')}>
                  Percentage <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  User Domain <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {qualificationList.map((qualification, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${qualification.id}`} color="link" size="sm">
                      {qualification.id}
                    </Button>
                  </td>
                  <td>{qualification.grade}</td>
                  <td>{qualification.year}</td>
                  <td>{qualification.university}</td>
                  <td>{qualification.marks}</td>
                  <td>{qualification.percentage}</td>
                  <td>
                    {qualification.userDomainId ? (
                      <Link to={`user-domain/${qualification.userDomainId}`}>{qualification.userDomainId}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${qualification.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${qualification.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${qualification.id}/delete`} color="danger" size="sm">
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

const mapStateToProps = ({ qualification }: IRootState) => ({
  qualificationList: qualification.entities,
  totalItems: qualification.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Qualification);
