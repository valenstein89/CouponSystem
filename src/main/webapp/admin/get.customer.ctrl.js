(function (){
    
    var module = angular.module("adminApp");

    module.controller("GetCustomerController", GetCustomerControllerCtor);

    // Ctor method
    function GetCustomerControllerCtor(AdminSrvc){
        this.showTable = false;
        this.showEdit = false;

        this.editMessage = '';
        this.deleteMessage = '';

        this.customer = {};
        var self = this;
        
        this.getCustomer = function(){
            var promiseGet = AdminSrvc.getCustomer(self.id);
            promiseGet.then(
                function (resp){
                    self.customer = resp.data;
                    if (self.customer === ''){
                        self.showTable = false;
                    } else {
                        self.showTable = true;
                    };
                },
                function (err){
                    self.showTable = false;
                    alert(err.data);
                }
            )
        }
        
        this.updateCustomer = function(){
            
            var promiseUpdate = AdminSrvc.updateCustomer(self.customer);
            promiseUpdate.then(
                function (resp){
                	self.editMessage = resp.data;
                },
                function (err){
                    self.editMessage = resp.data;
                }
            )
        }
        
        this.deleteCustomer = function(){
            var promiseDelete = AdminSrvc.deleteCustomer(self.id);
            promiseDelete.then(
                function (resp){
                    self.deleteMessage = resp.data;
                },
                function (err){
                    self.deleteMessage = resp.data;
                }
            )
        }
    }
})();