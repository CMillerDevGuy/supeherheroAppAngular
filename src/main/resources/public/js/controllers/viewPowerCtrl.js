angular.module('mainModule').controller('viewPowerCtrl', ['$scope', 'powerFactory', 'powerTypeFactory', function($scope, powerFactory, powerTypeFactory){

	$scope.powerData = function(){
		powerFactory.getSinglePower().then(
				function(success){
					$scope.power = success.data;
				}
		)
	}
	
	$scope.powerData();
	
	$scope.model = function(){
		$scope.powerNameModel = $scope.power.powerName;
		$scope.powerDescriptionModel = $scope.power.powerDescription;
		$scope.powerType = $scope.power.powerType;
	}
	
	//$scope.updatedPower = {};
	
	$scope.updatePower = function(){
		$scope.updatedPower = {};
		$scope.updatedPower.id = $scope.power.id;
		$scope.updatedPower.powerName = $scope.powerNameModel;
		$scope.updatedPower.powerType = {};
		$scope.updatedPower.powerType = $scope.powerType;
		$scope.updatedPower.powerDescription = $scope.powerDescriptionModel;
		
		powerFactory.updatePower($scope.updatedPower).then(
				function(success){
					alert("woohoo!");
				},
				function(error){
					console.log(error);
				}
		)
		
	}
	
    $scope.powerType = {}; 
	//Multi-select/Dropdown settings
    $scope.powerTypeSettings = {externalIdProp: '', displayProp: 'powerType', idProp: 'id', smartButtonMaxItems: 1, selectionLimit: 1};
    
    
    //Populate the dropdowns and multiselects
    $scope.powerTypeData = powerTypeFactory.getPowerTypes().then(
        function(success){
            $scope.powerTypes = success.data;
            if($scope.powerTypes.length == 0){
                $scope.show = true;
                $scope.showOne = false;
            }
        },
        function(error){
            $scope.powerTypes = error;
        }
    )
}])