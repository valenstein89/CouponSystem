var module = angular.module("companyApp");

module.service("CompanySrvc", CompanySrvcCtor);

function CompanySrvcCtor($http)
{
	this.getAllCoupons = function(){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon");		
	}
	
	this.getCoupon = function(id){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/' + id);
	}
	
	this.updateCoupon = function(coupon){
		return $http.put("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon", coupon);
	}
	
	this.deleteCoupon = function(id){
		return $http.delete("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/" + id);
	}
	
	this.createCoupon = function(coupon){
		return $http.post("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon", coupon);
	}
	
	this.getCouponsByType = function(type){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/type/" + type);
	}
	
	this.getCouponsByPrice = function(price){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/price/" + price);
	}
	
	this.getCouponsByDate = function(date){
		return $http.get("http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/date/" + date);
	}
}