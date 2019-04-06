package com.lxisoft.sas.web.rest;
import com.lxisoft.sas.service.LeaveApplicationService;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;
import com.lxisoft.sas.service.dto.LeaveApplicationDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing LeaveApplication.
 */
@RestController
@RequestMapping("/api")
public class LeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(LeaveApplicationResource.class);

    private static final String ENTITY_NAME = "leaveApplication";

    private final LeaveApplicationService leaveApplicationService;

    public LeaveApplicationResource(LeaveApplicationService leaveApplicationService) {
        this.leaveApplicationService = leaveApplicationService;
    }

    /**
     * POST  /leave-applications : Create a new leaveApplication.
     *
     * @param leaveApplicationDTO the leaveApplicationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaveApplicationDTO, or with status 400 (Bad Request) if the leaveApplication has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leave-applications")
    public ResponseEntity<LeaveApplicationDTO> createLeaveApplication(@RequestBody LeaveApplicationDTO leaveApplicationDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveApplicationDTO result = leaveApplicationService.save(leaveApplicationDTO);
        return ResponseEntity.created(new URI("/api/leave-applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leave-applications : Updates an existing leaveApplication.
     *
     * @param leaveApplicationDTO the leaveApplicationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaveApplicationDTO,
     * or with status 400 (Bad Request) if the leaveApplicationDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaveApplicationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leave-applications")
    public ResponseEntity<LeaveApplicationDTO> updateLeaveApplication(@RequestBody LeaveApplicationDTO leaveApplicationDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveApplication : {}", leaveApplicationDTO);
        if (leaveApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveApplicationDTO result = leaveApplicationService.save(leaveApplicationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leave-applications : get all the leaveApplications.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leaveApplications in body
     */
    @GetMapping("/leave-applications")
    public ResponseEntity<List<LeaveApplicationDTO>> getAllLeaveApplications(Pageable pageable) {
        log.debug("REST request to get a page of LeaveApplications");
        Page<LeaveApplicationDTO> page = leaveApplicationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leave-applications");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /leave-applications/:id : get the "id" leaveApplication.
     *
     * @param id the id of the leaveApplicationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaveApplicationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leave-applications/{id}")
    public ResponseEntity<LeaveApplicationDTO> getLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to get LeaveApplication : {}", id);
        Optional<LeaveApplicationDTO> leaveApplicationDTO = leaveApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveApplicationDTO);
    }

    /**
     * DELETE  /leave-applications/:id : delete the "id" leaveApplication.
     *
     * @param id the id of the leaveApplicationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-applications/{id}")
    public ResponseEntity<Void> deleteLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete LeaveApplication : {}", id);
        leaveApplicationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
