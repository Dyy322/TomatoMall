package com.example.tomatomall.po;

import com.example.tomatomall.vo.AdvertisementVO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(name = "advertisements")
@NoArgsConstructor
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "content", nullable = false)
    private String content;
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    public AdvertisementVO toVO(){
        AdvertisementVO advertisementVO = new AdvertisementVO();
        advertisementVO.setId(this.id);
        advertisementVO.setTitle(this.title);
        advertisementVO.setContent(this.content);
        advertisementVO.setImgUrl(this.imageUrl);
        return advertisementVO;
    }

}
