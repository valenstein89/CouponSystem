var module = angular.module('adminApp');

module.service('AdminSrvc', AdminSrvcCtor);

function AdminSrvcCtor($http)
{
	this.getAllCompanies = function(){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/admin/companies');	
	}
	this.getCompany = function(id){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/admin/companies/' + id);
	}
	this.updateCompany = function(company){
		return $http.put('http://localhost:8080/CouponSystemWebAPI/webapi/admin/companies', company);
	}
	this.deleteCompany = function(id){
		return $http.delete('http://localhost:8080/CouponSystemWebAPI/webapi/admin/companies/' + id);
	}
	this.createCompany = function(company){
		return $http.post('http://localhost:8080/CouponSystemWebAPI/webapi/admin/companies', company);
	}
	
	
	this.getAllCustomers = function(){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/admin/customers');
	}
	this.getCustomer = function(id){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/admin/customers/' + id);
	}
	this.updateCustomer = function(customer){
		return $http.put('http://localhost:8080/CouponSystemWebAPI/webapi/admin/customers', customer);
	}
	this.deleteCustomer = function(id){
		return $http.delete('http://localhost:8080/CouponSystemWebAPI/webapi/admin/customers/' + id);
	}
	this.createCustomer = function(customer){
		return $http.post('http://localhost:8080/CouponSystemWebAPI/webapi/admin/customers', customer);
	}	
	
	
	
	
	
	this.getCouponsByType = function(type){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/type/' + type);
	}
	
	this.getCouponsByPrice = function(price){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/price/' + price);
	}
	
	this.getCouponsByDate = function(date){
		return $http.get('http://localhost:8080/CouponSystemWebAPI/webapi/company/coupon/date/' + date);
	}
}