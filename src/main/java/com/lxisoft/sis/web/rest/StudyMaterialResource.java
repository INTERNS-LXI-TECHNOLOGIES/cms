package com.lxisoft.sis.web.rest;
import com.lxisoft.sis.service.StudyMaterialService;
import com.lxisoft.sis.web.rest.errors.BadRequestAlertException;
import com.lxisoft.sis.web.rest.util.HeaderUtil;
import com.lxisoft.sis.web.rest.util.PaginationUtil;
import com.lxisoft.sis.service.dto.StudyMaterialDTO;
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
 * REST controller for managing StudyMaterial.
 */
@RestController
@RequestMapping("/api")
public class StudyMaterialResource {

    private final Logger log = LoggerFactory.getLogger(StudyMaterialResource.class);

    private static final String ENTITY_NAME = "studyMaterial";

    private final StudyMaterialService studyMaterialService;

    public StudyMaterialResource(StudyMaterialService studyMaterialService) {
        this.studyMaterialService = studyMaterialService;
    }

    /**
     * POST  /study-materials : Create a new studyMaterial.
     *
     * @param studyMaterialDTO the studyMaterialDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studyMaterialDTO, or with status 400 (Bad Request) if the studyMaterial has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/study-materials")
    public ResponseEntity<StudyMaterialDTO> createStudyMaterial(@RequestBody StudyMaterialDTO studyMaterialDTO) throws URISyntaxException {
        log.debug("REST request to save StudyMaterial : {}", studyMaterialDTO);
        if (studyMaterialDTO.getId() != null) {
            throw new BadRequestAlertException("A new studyMaterial cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudyMaterialDTO result = studyMaterialService.save(studyMaterialDTO);
        return ResponseEntity.created(new URI("/api/study-materials/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /study-materials : Updates an existing studyMaterial.
     *
     * @param studyMaterialDTO the studyMaterialDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studyMaterialDTO,
     * or with status 400 (Bad Request) if the studyMaterialDTO is not valid,
     * or with status 500 (Internal Server Error) if the studyMaterialDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/study-materials")
    public ResponseEntity<StudyMaterialDTO> updateStudyMaterial(@RequestBody StudyMaterialDTO studyMaterialDTO) throws URISyntaxException {
        log.debug("REST request to update StudyMaterial : {}", studyMaterialDTO);
        if (studyMaterialDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudyMaterialDTO result = studyMaterialService.save(studyMaterialDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studyMaterialDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /study-materials : get all the studyMaterials.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studyMaterials in body
     */
    @GetMapping("/study-materials")
    public ResponseEntity<List<StudyMaterialDTO>> getAllStudyMaterials(Pageable pageable) {
        log.debug("REST request to get a page of StudyMaterials");
        Page<StudyMaterialDTO> page = studyMaterialService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/study-materials");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /study-materials/:id : get the "id" studyMaterial.
     *
     * @param id the id of the studyMaterialDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studyMaterialDTO, or with status 404 (Not Found)
     */
    @GetMapping("/study-materials/{id}")
    public ResponseEntity<StudyMaterialDTO> getStudyMaterial(@PathVariable Long id) {
        log.debug("REST request to get StudyMaterial : {}", id);
        Optional<StudyMaterialDTO> studyMaterialDTO = studyMaterialService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studyMaterialDTO);
    }

    /**
     * DELETE  /study-materials/:id : delete the "id" studyMaterial.
     *
     * @param id the id of the studyMaterialDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/study-materials/{id}")
    public ResponseEntity<Void> deleteStudyMaterial(@PathVariable Long id) {
        log.debug("REST request to delete StudyMaterial : {}", id);
        studyMaterialService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
