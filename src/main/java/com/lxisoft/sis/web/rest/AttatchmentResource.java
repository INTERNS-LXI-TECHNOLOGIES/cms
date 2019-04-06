package com.lxisoft.sis.web.rest;
import com.lxisoft.sis.service.AttatchmentService;
import com.lxisoft.sis.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sis.web.rest.util.HeaderUtil;
import com.lxisoft.sis.web.rest.util.PaginationUtil;
import com.lxisoft.sis.service.dto.AttatchmentDTO;
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
 * REST controller for managing Attatchment.
 */
@RestController
@RequestMapping("/api")
public class AttatchmentResource {

    private final Logger log = LoggerFactory.getLogger(AttatchmentResource.class);

    private static final String ENTITY_NAME = "attatchment";

    private final AttatchmentService attatchmentService;

    public AttatchmentResource(AttatchmentService attatchmentService) {
        this.attatchmentService = attatchmentService;
    }

    /**
     * POST  /attatchments : Create a new attatchment.
     *
     * @param attatchmentDTO the attatchmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new attatchmentDTO, or with status 400 (Bad Request) if the attatchment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/attatchments")
    public ResponseEntity<AttatchmentDTO> createAttatchment(@RequestBody AttatchmentDTO attatchmentDTO) throws URISyntaxException {
        log.debug("REST request to save Attatchment : {}", attatchmentDTO);
        if (attatchmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new attatchment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AttatchmentDTO result = attatchmentService.save(attatchmentDTO);
        return ResponseEntity.created(new URI("/api/attatchments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /attatchments : Updates an existing attatchment.
     *
     * @param attatchmentDTO the attatchmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated attatchmentDTO,
     * or with status 400 (Bad Request) if the attatchmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the attatchmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/attatchments")
    public ResponseEntity<AttatchmentDTO> updateAttatchment(@RequestBody AttatchmentDTO attatchmentDTO) throws URISyntaxException {
        log.debug("REST request to update Attatchment : {}", attatchmentDTO);
        if (attatchmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AttatchmentDTO result = attatchmentService.save(attatchmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, attatchmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /attatchments : get all the attatchments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of attatchments in body
     */
    @GetMapping("/attatchments")
    public ResponseEntity<List<AttatchmentDTO>> getAllAttatchments(Pageable pageable) {
        log.debug("REST request to get a page of Attatchments");
        Page<AttatchmentDTO> page = attatchmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/attatchments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /attatchments/:id : get the "id" attatchment.
     *
     * @param id the id of the attatchmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the attatchmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/attatchments/{id}")
    public ResponseEntity<AttatchmentDTO> getAttatchment(@PathVariable Long id) {
        log.debug("REST request to get Attatchment : {}", id);
        Optional<AttatchmentDTO> attatchmentDTO = attatchmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(attatchmentDTO);
    }

    /**
     * DELETE  /attatchments/:id : delete the "id" attatchment.
     *
     * @param id the id of the attatchmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/attatchments/{id}")
    public ResponseEntity<Void> deleteAttatchment(@PathVariable Long id) {
        log.debug("REST request to delete Attatchment : {}", id);
        attatchmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
