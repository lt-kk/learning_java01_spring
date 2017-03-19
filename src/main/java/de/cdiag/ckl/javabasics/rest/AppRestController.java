package de.cdiag.ckl.javabasics.rest;

import de.cdiag.ckl.javabasics.dao.CrudDao;
import de.cdiag.ckl.javabasics.entities.AppEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Created by CKL on 17.03.2017.
 */
@RestController
@RequestMapping("/rest/v1/app")
@RequiredArgsConstructor
public class AppRestController implements CrudController<AppEntity> {

	private final CrudDao<AppEntity> dao;

	public HttpEntity<List<AppEntity>> all() {
		List<AppEntity> apps = dao.all();
		return ResponseEntity.ok( apps );
	}

	@Override
	public HttpEntity<AppEntity> create( @RequestBody AppEntity entity ) {
		AppEntity app = dao.store( entity );
		return ResponseEntity.ok( app );
	}

	@Override
	public HttpEntity<AppEntity> get( @PathVariable("id") Long id ) {
		Optional<AppEntity> app = dao.get( id );
		return app.isPresent()
						 ? new ResponseEntity<>( app.get(), HttpStatus.OK )
						 : new ResponseEntity<>( HttpStatus.NOT_FOUND );
	}

	@Override
	public HttpEntity<AppEntity> update( @PathVariable("id") Long id, @RequestBody AppEntity entity ) {
		entity.setId( id );
		AppEntity app = dao.store( entity );
		return ResponseEntity.ok( app );
	}

	@Override
	@Transactional
	public HttpEntity<?> delete( @PathVariable("id") Long id ) {
		int affectedRows = dao.delete( id );
		return affectedRows > 0
						 ? new ResponseEntity<>( HttpStatus.OK )
						 : new ResponseEntity<>( HttpStatus.NOT_FOUND );
	}

}
