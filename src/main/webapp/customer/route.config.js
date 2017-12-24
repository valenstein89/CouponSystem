(function () {

    var module = angular.module("customerApp");

    // http://stackoverflow.com/questions/41211875/angularjs-1-6-0-latest-now-routes-not-working
    module.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.hashPrefix('');
    }]);

    // router config
    module.config(function ($stateProvider, $urlRouterProvider) {

        $stateProvider
        .state("getAllCoupons", {
            url: "/getAllCoupons",
            templateUrl: "get_all_coupons.html",
            controller: "GetAllCouponsController as getAllCtrl"
        })
        .state("purchaseCoupon", {
            url: "/purchaseCoupon",
            templateUrl: "purchase_coupon.html",
            controller: "PurchaseCouponController as purchaseCtrl"
        })
		.state("getCouponsByType", {
            url: "/getCouponsByType",
            templateUrl: "get_coupons_by_type.html",
            controller: "GetCouponsByTypeController as getByTypeCtrl"
        })
        .state("getCouponsByPrice", {
            url: "/getCouponsByPrice",
            templateUrl: "get_coupons_by_price.html",
            controller: "GetCouponsByPriceController as getByPriceCtrl"
        })

        $urlRouterProvider.when("", "/getAllCoupons"); // first browsing postfix is empty --> route it to /main
        // $urlRouterProvider.otherwise('/404'); // when no switch case matches --> route to /404
    });

})();