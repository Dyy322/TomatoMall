package com.example.tomatomall.controller;

import com.example.tomatomall.service.AdvertisementService;
import com.example.tomatomall.vo.AdvertisementVO;
import com.example.tomatomall.vo.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/advertisements")
public class AdvertisementController {

    @Autowired
    AdvertisementService advertisementService;

    @GetMapping
    public Response<List<AdvertisementVO>> getAdvertisements() {
        return Response.buildSuccess(advertisementService.getAllAdvertisements());
    }

    @GetMapping("/{id}")
    public Response<AdvertisementVO> getAdvertisement(@PathVariable Integer id) {
        return Response.buildSuccess(advertisementService.getAdvertisementById(id));
    }

    @PutMapping
    public Response<String> updateAdvertisement(@RequestBody AdvertisementVO advertisementVO) {
        if(advertisementService.updateAdvertisement(advertisementVO)){
            return Response.buildSuccess("更新成功");
        }
        return Response.buildFailure("商品不存在","400");
    }

    @PostMapping
    public Response<AdvertisementVO> createAdvertisement(@RequestBody AdvertisementVO advertisementVO) {
        return Response.buildSuccess(advertisementService.createAdvertisement(advertisementVO));
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteAdvertisement(@PathVariable Integer id) {
        if(advertisementService.deleteAdvertisement(id)){
            return Response.buildSuccess("删除成功");
        }
        return Response.buildFailure("广告不存在","400");
    }
}
