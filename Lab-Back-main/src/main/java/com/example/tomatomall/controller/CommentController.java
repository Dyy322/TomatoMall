package com.example.tomatomall.controller;

import com.example.tomatomall.po.Comment;
import com.example.tomatomall.repository.CommentRepository;
import com.example.tomatomall.service.CommentService;
import com.example.tomatomall.vo.CommentVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {


    @Autowired
    CommentService commentService;
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/{productId}")
    Response<List<CommentVO>> getComments(@PathVariable Integer productId) {
        return Response.buildSuccess(commentService.findByProductId(productId));
    }

    @PostMapping
    Response<String> addComment(@RequestBody CommentVO commentVO) {
        commentRepository.save(commentVO.toPO());
        return Response.buildSuccess("评论成功！");
    }
}
