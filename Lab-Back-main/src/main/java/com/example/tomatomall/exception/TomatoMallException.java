package com.example.tomatomall.exception;

public class TomatoMallException extends RuntimeException {
    public TomatoMallException(String message) {
        super(message);
    }
    public static TomatoMallException usernameOrPasswordError(){
        return new TomatoMallException("用户不存在/用户密码错误");
    }
    public static TomatoMallException usernameExist(){
        return new TomatoMallException("用户名已存在");
    }
    public static TomatoMallException needLogin(){
        return new TomatoMallException("用户未登录");
    }
    public static TomatoMallException usernameNotExist(){
        return new TomatoMallException("用户名不存在");
    }
    public static TomatoMallException fileUploadFail() { return new TomatoMallException("文件上传失败");}
    public static TomatoMallException storeNotExists() { return new TomatoMallException("商店不存在");}
    public static TomatoMallException productNotExists() { return new TomatoMallException("商品不存在");}
    public static TomatoMallException productNameAlreadyExists() { return new TomatoMallException("商品名称已存在");}
    public static TomatoMallException stockNotEnough() { return new TomatoMallException("商品库存不足");}
    public static TomatoMallException cartItemNotExists() { return new TomatoMallException("购物车商品不存在");}
    public static TomatoMallException orderNotExists() { return new TomatoMallException("订单不存在");}
    public static TomatoMallException promotionNameExists() { return new TomatoMallException("促销活动已存在");}
    public static TomatoMallException promotionNotExists() { return new TomatoMallException("促销活动不存在");}

    public static TomatoMallException categoryAlreadyExists() {return new TomatoMallException("该分类已存在");}

    public static TomatoMallException categoryNotExists() {return new TomatoMallException("该分类不存在");}

    public static TomatoMallException categoryProductRelationAlreadyExists() {return new TomatoMallException("该商品已存在此分类中");}

    public static TomatoMallException advertisementNotExists() {return new TomatoMallException("广告不存在");}
}
