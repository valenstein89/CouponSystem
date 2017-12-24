(function () {

    var module = angular.module("adminApp");

    // http://stackoverflow.com/questions/41211875/angularjs-1-6-0-latest-now-routes-not-working
    module.config(['$locationProvider', function ($locationProvider) {
        $locationProvider.hashPrefix('');
    }]);

    // router config
    module.config(function ($stateProvider, $urlRouterProvider) {

        $stateProvider
        .state("getAllCompanies", {
            url: "/getAllCompanies",
            templateUrl: "get_all_companies.html",
            controller: "GetAllCompaniesController as getAllCompsCtrl"
        })
        .state("getCompany", {
            url: "/getCompany",
            templateUrl: "get_company.html",
            controller: "GetCompanyController as getCompCtrl"
        })
		.state("createCompany", {
            url: "/createCompany",
            templateUrl: "create_company.html",
            controller: "CreateCompanyController as createCompCtrl"
        })
        .state('getAllCustomers', {
            url: "/getAllCustomers",
            templateUrl: "get_all_customers.html",
            controller: "GetAllCustomersController as getAllCustCtrl"
        })
		.state('getCustomer', {
            url: "/getCustomer",
            templateUrl: "get_customer.html",
            controller: "GetCustomerController as getCustCtrl"
        })
        .state("createCustomer", {
            url: "/createCustomer",
            templateUrl: "create_customer.html",
            controller: "CreateCustomerController as createCustCtrl"
        });

        $urlRouterProvider.when("", "/getAllCompanies"); // first browsing postfix is empty --> route it to /main
        // $urlRouterProvider.otherwise('/404'); // when no switch case matches --> route to /404
    });

})();