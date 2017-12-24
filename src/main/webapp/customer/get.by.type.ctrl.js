 (function(){
    var module = angular.module("customerApp");

    module.controller("GetCouponsByTypeController", GetCouponsByTypeControllerCtor);

    //Ctor method
    function GetCouponsByTypeControllerCtor(CustomerSrvc){
        this.message = '';
        this.showCoupons = false;
        this.coupons = [];
        var self = this;

        this.getByType = function(){
            var promise = CustomerSrvc.getCouponsByType(self.type);

            promise.then(
                function(success){	

            		if(success.data[0] !== undefined){
                        self.message = '';
                        self.coupons = success.data;
                		self.showCoupons = true;

            		} else {
                		self.showCoupons = false;
                        self.message = 'No coupons available!';
                	}
                },
                function(fail){
                	self.message = resp.data;
            	}
            );
        };
    }
})()