package com.example.tomatomall.service.serviceImpl;

import com.example.tomatomall.po.Comment;
import com.example.tomatomall.repository.CommentRepository;
import com.example.tomatomall.service.CommentService;
import com.example.tomatomall.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<CommentVO> findByProductId(Integer productId) {
        List<Comment> comments= commentRepository.findByProductId(productId);
        List<CommentVO> result=new ArrayList<>();
        for (Comment comment : comments) {
            result.add(comment.toVO());
        }
        return result;
    }
}
