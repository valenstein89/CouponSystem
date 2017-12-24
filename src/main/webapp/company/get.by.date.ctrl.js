(function (){
    var module = angular.module('companyApp');

    module.controller('GetCouponsByDateCtrl', GetCouponsByDateCtrlCtor);

    function GetCouponsByDateCtrlCtor(CompanySrvc){
    	this.message = '';
        this.showCoupons = false;
        this.coupons = [];
        var self = this;

        this.getByDate = function(){
        	var date = self.dateRaw.toISOString().split("T")[0];
            var promise = CompanySrvc.getCouponsByDate(date);
            promise.then(
                function(success){
                    if(success.data[0] !== undefined){
                        self.coupons = success.data;
                        self.showCoupons = true;
                        self.message = '';

                    } else {
                        self.showCoupons = false;
                        self.message = 'No coupons found';
                    }
                },
                function(fail){
                	self.message = resp.data;
                }
            )
        }
    }
})()