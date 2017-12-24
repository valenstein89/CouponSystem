(function () {
	
	var module = angular.module("companyApp");
	
	module.controller("CreateCouponCtrl", CreateCouponCtrlCtor);
	
	function CreateCouponCtrlCtor(CompanySrvc){

		this.createCoupon = function(){

			this.message = '';
			
			if (this.coupon == undefined)
			{
				alert('Please fill in required fields!')
				return
			}
			
			// extract strings from date objects
			this.coupon.startDate = this.coupon.startDateRaw.toISOString().split("T")[0];
			this.coupon.endDate = this.coupon.endDateRaw.toISOString().split("T")[0];
			// call create method
			var promise = CompanySrvc.createCoupon(this.coupon)

			var self = this

			promise.then(
				function (resp){
					// if successful, show success message
					self.message = resp.data;
				},
				function (err){
					// in failed, show failure message
					self.message = resp.data;
				}
			)

		}
		
	}
	
})();