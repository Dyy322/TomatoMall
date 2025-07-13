package com.example.tomatomall.po;


import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.example.tomatomall.vo.CommentVO;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

@Getter
@Setter
@Entity
@Table(name = "comments")
@Data
@NoArgsConstructor
public class Comment {


    @Id
    // 自动生成一个commentId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_Id")
    private Integer commentId;

    @Column(name = "user_Name")
    private String userName;

    @Column(name = "product_Id")
    private Integer productId;

    @Column(name = "score")
    private Integer score;

    @Column(name = "content")
    private String content;

    public CommentVO toVO(){
        CommentVO commentVO = new CommentVO();
        commentVO.setCommentId(this.commentId);
        commentVO.setUserName(this.userName);
        commentVO.setProductId(this.productId);
        commentVO.setScore(this.score);
        commentVO.setContent(this.content);
        return commentVO;
    }
}
