(function (){

	var module = angular.module("companyApp");

	module.controller("GetCouponCtrl", GetCouponCtrlCtor);

	// Ctor method
	function GetCouponCtrlCtor(CompanySrvc){
        this.showCoupon = false;
        this.showEdit = false;
        this.editSuccess = false;
        this.editFail = false;
        this.deleteSuccess = false;
        this.deleteFail = false;
        this.getCouponMessage = '';
        this.coupon = {};
        var ctorSelf = this;
        
        this.getCoupon = function(){
            var promiseGet = CompanySrvc.getCoupon(ctorSelf.id);
            promiseGet.then(
                function (resp){
//                	ctorSelf.coupon = resp.data;
//                    if (ctorSelf.coupon === ''){
//                    	ctorSelf.showCoupon = false;
//                    } else {
//                    	ctorSelf.showCoupon = true;
//                    };
                	if (resp.data !== undefined){
                		ctorSelf.coupon = resp.data;
                		ctorSelf.showCoupon = true;
                		ctorSelf.getCouponMessage = '';
                	} else {
                		ctorSelf.showCoupon = false;
                		ctorSelf.getCouponMessage = 'Coupon does not exist!';
                	}
                },
                function (err){
                	ctorSelf.showCoupon = false;
                	ctorSelf.getCouponMessage = err.data;
                }
            )
        }
        
        this.updateCoupon = function(){
        	
        	ctorSelf.coupon.startDate = ctorSelf.coupon.startDateRaw.toISOString().split("T")[0];
    		ctorSelf.coupon.endDate = ctorSelf.coupon.endDateRaw.toISOString().split("T")[0];
        	
        	var promiseUpdate = CompanySrvc.updateCoupon(ctorSelf.coupon);
        	promiseUpdate.then(
                function (resp){
                	ctorSelf.editSuccess = true;
                },
                function (err){
                    ctorSelf.editFail = true;
                }
            )
        }
        
        this.deleteCoupon = function(){
        	var promiseDelete = CompanySrvc.deleteCoupon(ctorSelf.id);
        	promiseDelete.then(
                function (resp){
                	ctorSelf.deleteSuccess = true;
                },
                function (err){
                    ctorSelf.deleteFail = true;
                }
            )
        }
	}
})();