(function () {

    var module = angular.module("companyApp");

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
            controller: "GetAllCouponsCtrl as getAll"
        })
        .state("getCoupon", {
            url: "/getCoupon",
            templateUrl: "get_coupon.html",
            controller: "GetCouponCtrl as getCoup"
        })
		.state("getCouponsByType", {
            url: "/getCouponsByType",
            templateUrl: "get_coupons_by_type.html",
            controller: "GetCouponsByTypeCtrl as getByType"
        })
        .state("getCouponsByPrice", {
            url: "/getCouponsByPrice",
            templateUrl: "get_coupons_by_price.html",
            controller: "GetCouponsByPriceCtrl as getByPriceCtrl"
        })
		.state("getCouponsByDate", {
            url: "/getCouponsByDate",
            templateUrl: "get_coupons_by_date.html",
            controller: "GetCouponsByDateCtrl as getByDateCtrl"
        })
        .state("createCoupon", {
            url: "/createCoupon",
            templateUrl: "create_coupon.html",
            controller: "CreateCouponCtrl as createCoup"
        })
        .state("removeCoupon", {
            url: "/removeCoupon",
            templateUrl: "remove_coupon.html",
            controller: "RemoveCouponCtrl as removeCoupCtrl"
        });

        $urlRouterProvider.when("", "/getAllCoupons"); // first browsing postfix is empty --> route it to /main
        // $urlRouterProvider.otherwise('/404'); // when no switch case matches --> route to /404
    });

})();