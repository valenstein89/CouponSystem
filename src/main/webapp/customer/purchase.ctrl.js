(function (){
    var module = angular.module('customerApp');

    module.controller('PurchaseCouponController', PurchaseCouponControllerCtor);

    function PurchaseCouponControllerCtor(CustomerSrvc){
        this.message = '';
        var self = this;

        this.purchase = function(){
            console.log(self.id);
            var promise = CustomerSrvc.purchaseCoupon(self.id);
            promise.then(
                function(success){
                	self.message = resp.data;
                },
                function(fail){
                	self.message = resp.data;
                }
            )
        }
    }
})()