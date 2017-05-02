package com.Sketchr2.services;

import com.Sketchr2.entities.Drawing;
import com.Sketchr2.entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by ericweidman on 5/2/17.
 */
public interface DrawingRepository extends CrudRepository<Drawing, Integer>{

List<Drawing> findAllByUserId (User id);

}
