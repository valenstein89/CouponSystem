var module = angular.module("customerApp");

module.service("CustomerSrvc", CustomerSrvcCtor);

function CustomerSrvcCtor($http)
{
	this.getAllCoupons = function(){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/customer/coupon");		
    }
    
    this.purchaseCoupon = function(id){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/customer/coupon/" + id);
//		return $http({ url: "http://localhost:8080/CouponSystemWebAPI/webapi/customer/coupon", method: "POST", params: id});
//		$http({
//		    method: 'POST',
//		    url: 'http://localhost:8080/CouponSystemWebAPI/webapi/customer/coupon',
//		    data: id,
//		    headers: {
//		        'Content-Type': 'application/json'
//		    }})
	}
	
	this.getCouponsByType = function(type){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/customer/coupon/type/" + type);
	}
	
	this.getCouponsByPrice = function(price){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/customer/coupon/price/" + price);
	}
	
}