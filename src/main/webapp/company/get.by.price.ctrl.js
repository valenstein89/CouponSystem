(function (){
    var module = angular.module('companyApp');

    module.controller('GetCouponsByPriceCtrl', GetCouponsByPriceCtrlCtor);

    function GetCouponsByPriceCtrlCtor(CompanySrvc){
        this.showCoupons = false;
        this.message = '';
        this.coupons = [];
        var self = this;

        this.getByPrice = function(){
            var promise = CompanySrvc.getCouponsByPrice(self.price);
            promise.then(
                function(success){
                    if(success.data[0] !== undefined){
                        self.coupons = success.data;
                        self.showCoupons = true;
                        self.message = '';

                    } else {
                        self.showCoupons = false;
                        self.message = 'No coupons found!';
                    }
                },
                function(fail){
                	self.message = resp.data;
                }
            )
        }
    }
})()