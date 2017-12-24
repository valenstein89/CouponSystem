(function (){
	
	var module = angular.module("companyApp");
	
	module.controller("GetAllCouponsCtrl", GetAllCouponsCtrlCtor);
	
	// Ctor method
	function GetAllCouponsCtrlCtor(CompanySrvc){
		
		this.showTable = false;
		this.message = '';
		this.coupons = [];
		var self = this;
		
		var promiseGet = CompanySrvc.getAllCoupons();
		
		promiseGet.then(
			function (resp){
				if (resp.data[0] != undefined){
					self.coupons = resp.data;
					self.showTable = true;
					self.message = '';
				} else {
					self.showTable = false;
					self.message = 'No coupons found';
				}
			},
			
			function (err){
				self.showTable = false;
				self.message = resp.data;
			}
		)
	}
})();