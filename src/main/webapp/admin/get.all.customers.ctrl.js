(function(){
    
    var module = angular.module('adminApp');

    module.controller('GetAllCustomersController', GetAllCustomersControllerCtor);

    function GetAllCustomersControllerCtor(AdminSrvc){
        
        this.customers = [];

        this.showTable = false;
        this.message = '';
            
        
        var self = this;

        var promise = AdminSrvc.getAllCustomers();
        promise.then(
            function(success){
                if(success.data[0] !== undefined){
                    self.customers = success.data;
                    self.showTable = true;
                    self.message = '';
                } else {
                    self.showTable = false;
                    self.message = 'No customers found';
                }
            },
            function(fail){
                self.showTable = false;
                self.message = resp.data;
            }
        )
    }    
})()