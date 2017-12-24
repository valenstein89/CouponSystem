(function(){
    
        var module = angular.module("adminApp");
    
        module.controller("CreateCustomerController", CreateCustomerControllerCtor);
    
        function CreateCustomerControllerCtor(AdminSrvc){
    
            this.message = '';
    
            var self = this;
    
            this.createCustomer = function(){			
                if (this.customer == undefined)
                {
                    alert('Please fill in required fields!')
                    return
                }
    
                // call create method
                var promise = AdminSrvc.createCustomer(this.customer)
    
                promise.then(
                    function (resp){
//                        self.message = 'Customer Successfully created!';
                    	self.message = resp.data;
                    },
                    function (err){
//                        self.message = 'Customer was NOT created!';
                    	self.message = resp.data;
                    }
                )
            }
        }
    })();