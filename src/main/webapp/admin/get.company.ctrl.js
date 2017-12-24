(function (){
    
    var module = angular.module("adminApp");

    module.controller("GetCompanyController", GetCompanyControllerCtor);

    // Ctor method
    function GetCompanyControllerCtor(AdminSrvc){
        this.showTable = false;
        this.showEdit = false;

        this.editMessage = '';
        this.deleteMessage = '';

        this.company = {};
        var self = this;
        
        this.getCompany = function(){
            var promiseGet = AdminSrvc.getCompany(self.id);
            promiseGet.then(
                function (resp){
                    self.company = resp.data;
                    if (self.company === ''){
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
        
        this.updateCompany = function(){
            
            var promiseUpdate = AdminSrvc.updateCompany(self.company);
            promiseUpdate.then(
                function (resp){
                    self.editMessage = resp.data;
                },
                function (err){
                    self.editMessage = resp.data;
                }
            )
        }
        
        this.deleteCompany = function(){
            var promiseDelete = AdminSrvc.deleteCompany(self.id);
            promiseDelete.then(
                function (resp){
                    self.deleteMessage = resp.data;
                },
                function (err){
                    self.deleteMessage = resp.data;                }
            )
        }
    }
})();