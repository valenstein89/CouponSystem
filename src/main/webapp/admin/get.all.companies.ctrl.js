(function(){

    var module = angular.module('adminApp');

    module.controller('GetAllCompaniesController', GetAllCompaniesControllerCtor);

    function GetAllCompaniesControllerCtor(AdminSrvc){
        
        this.companies = [];

        this.showTable = false;
        this.message = '';
         
        
        var self = this;

        var promise = AdminSrvc.getAllCompanies();
        promise.then(
            function(success){
                if(success.data[0] !== undefined){
                    self.companies = success.data;
                    self.showTable = true;
                    self.message = '';
                } else {
                    self.showTable = false;
                    self.message = 'No comapnies found';
                }
            },
            function(fail){
                self.showTable = false;
                self.message = resp.data;
            }
        )

    }

})()