package com.lxisoft.sas.web.rest;
import com.lxisoft.sas.service.UserRoleService;
import com.lxisoft.sas.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sas.web.rest.util.HeaderUtil;
import com.lxisoft.sas.web.rest.util.PaginationUtil;
import com.lxisoft.sas.service.dto.UserRoleDTO;
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
 * REST controller for managing UserRole.
 */
@RestController
@RequestMapping("/api")
public class UserRoleResource {

    private final Logger log = LoggerFactory.getLogger(UserRoleResource.class);

    private static final String ENTITY_NAME = "userRole";

    private final UserRoleService userRoleService;

    public UserRoleResource(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    /**
     * POST  /user-roles : Create a new userRole.
     *
     * @param userRoleDTO the userRoleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new userRoleDTO, or with status 400 (Bad Request) if the userRole has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/user-roles")
    public ResponseEntity<UserRoleDTO> createUserRole(@RequestBody UserRoleDTO userRoleDTO) throws URISyntaxException {
        log.debug("REST request to save UserRole : {}", userRoleDTO);
        if (userRoleDTO.getId() != null) {
            throw new BadRequestAlertException("A new userRole cannot already have an ID", ENTITY_NAME, "idexists");
        }
        UserRoleDTO result = userRoleService.save(userRoleDTO);
        return ResponseEntity.created(new URI("/api/user-roles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /user-roles : Updates an existing userRole.
     *
     * @param userRoleDTO the userRoleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated userRoleDTO,
     * or with status 400 (Bad Request) if the userRoleDTO is not valid,
     * or with status 500 (Internal Server Error) if the userRoleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/user-roles")
    public ResponseEntity<UserRoleDTO> updateUserRole(@RequestBody UserRoleDTO userRoleDTO) throws URISyntaxException {
        log.debug("REST request to update UserRole : {}", userRoleDTO);
        if (userRoleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        UserRoleDTO result = userRoleService.save(userRoleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, userRoleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /user-roles : get all the userRoles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of userRoles in body
     */
    @GetMapping("/user-roles")
    public ResponseEntity<List<UserRoleDTO>> getAllUserRoles(Pageable pageable) {
        log.debug("REST request to get a page of UserRoles");
        Page<UserRoleDTO> page = userRoleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/user-roles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /user-roles/:id : get the "id" userRole.
     *
     * @param id the id of the userRoleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the userRoleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/user-roles/{id}")
    public ResponseEntity<UserRoleDTO> getUserRole(@PathVariable Long id) {
        log.debug("REST request to get UserRole : {}", id);
        Optional<UserRoleDTO> userRoleDTO = userRoleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(userRoleDTO);
    }

    /**
     * DELETE  /user-roles/:id : delete the "id" userRole.
     *
     * @param id the id of the userRoleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/user-roles/{id}")
    public ResponseEntity<Void> deleteUserRole(@PathVariable Long id) {
        log.debug("REST request to delete UserRole : {}", id);
        userRoleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
