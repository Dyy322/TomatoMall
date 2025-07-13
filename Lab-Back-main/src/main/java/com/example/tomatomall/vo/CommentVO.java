package com.example.tomatomall.vo;

import com.example.tomatomall.po.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class CommentVO {

    private Integer commentId;
    private String userName;
    private Integer productId;
    private Integer score;
    private String content;

    public Comment toPO(){
        Comment comment = new Comment();
        comment.setCommentId(commentId);
        comment.setUserName(userName);
        comment.setProductId(productId);
        comment.setScore(score);
        comment.setContent(content);
        return comment;
    }
}
