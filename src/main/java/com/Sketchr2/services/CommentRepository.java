package com.Sketchr2.services;

import com.Sketchr2.entities.Comment;
import org.springframework.data.repository.CrudRepository;


import java.util.List;

/**
 * Created by ericweidman on 5/2/17.
 */
public interface CommentRepository  extends CrudRepository<Comment, Integer> {

    List<Comment> findAllByDrawingId(int id);

}
