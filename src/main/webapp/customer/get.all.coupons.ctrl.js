(function (){
	
	var module = angular.module("customerApp");
	
	module.controller("GetAllCouponsController", GetAllCouponsControllerCtor);
	
	// Ctor method
	function GetAllCouponsControllerCtor(CustomerSrvc){
		this.showTable = false;
		this.coupons = [];
		this.message = '';
		var self = this;
		
		var promiseGet = CustomerSrvc.getAllCoupons();
		
		promiseGet.then(
			function (resp){
				if(resp.data[0] !== undefined){
					self.message = '';
					self.showTable = true;
					self.coupons = resp.data
				} else {
					self.showTable = false;
					self.message = 'No coupons available.';
				}
			},
			
			function (err){
				self.showTable = false;
				self.message = resp.data;
			}
		)
	}
})();