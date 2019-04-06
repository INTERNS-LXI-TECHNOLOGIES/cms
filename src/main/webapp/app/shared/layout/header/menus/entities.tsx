import React from 'react';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from '../header-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name="Entities" id="entity-menu">
    <DropdownItem tag={Link} to="/entity/user-domain">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;User Domain
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/user-role">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;User Role
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/address">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Address
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/event">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Event
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/qualification">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Qualification
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/leave-application">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Leave Application
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/attatchment">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Attatchment
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/exam-schedule">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Exam Schedule
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/exam">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Exam
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/study-material">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Study Material
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/exam-hall">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Exam Hall
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/subject">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Subject
    </DropdownItem>
    <DropdownItem tag={Link} to="/entity/assignment">
      <FontAwesomeIcon icon="asterisk" fixedWidth />
      &nbsp;Assignment
    </DropdownItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
