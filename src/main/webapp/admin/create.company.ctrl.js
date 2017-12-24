(function(){

	var module = angular.module("adminApp");

	module.controller("CreateCompanyController", CreateCompanyControllerCtor);

	function CreateCompanyControllerCtor(AdminSrvc){

        this.message = '';

        var self = this;

		this.createCompany = function(){			
			if (this.company == undefined)
			{
				alert('Please fill in required fields!')
				return
			}

			// call create method
			var promise = AdminSrvc.createCompany(this.company)

			promise.then(
				function (resp){
//					self.message = 'Company Successfully created!';
					self.message = resp.data;
				},
				function (err){
//					self.message = 'Company was NOT created!';
					self.message = resp.data;
				}
			)
		}
	}
})();