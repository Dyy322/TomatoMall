package com.example.tomatomall.service;

import com.example.tomatomall.vo.AdvertisementVO;

import java.util.List;

public interface AdvertisementService {
    List<AdvertisementVO> getAllAdvertisements();

    AdvertisementVO getAdvertisementById(Integer id);

    AdvertisementVO createAdvertisement(AdvertisementVO advertisementVO);

    Boolean updateAdvertisement(AdvertisementVO advertisementVO);

    Boolean deleteAdvertisement(Integer id);
}
