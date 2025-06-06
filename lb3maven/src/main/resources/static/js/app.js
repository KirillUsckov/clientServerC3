angular.module('propertyApp', [])
    .controller('PropertyController', function($scope, $http, $window) {
        $scope.properties = [];
        $scope.fallbackImg = 'https://via.placeholder.com/100';

        $scope.load = function() {
            $http.get('/api/v1/property').then(function(res) {
                $scope.properties = res.data.properties;
            });
        };

        $scope.delete = function(id) {
            if (confirm("Удалить объект?")) {
                $http.delete('/api/v1/property/' + id).then($scope.load);
            }
        };

        $scope.goToCreate = function() {
            $window.location.href = 'create.html';
        };

        $scope.load();
    })
    .controller('CreateController', function($scope, $http, $window) {
        $scope.form = {};

        $scope.submit = function() {
            const formData = new FormData();
            formData.append('data', new Blob([JSON.stringify($scope.form)], { type: 'application/json' }));

            const files = document.getElementById('file-upload').files;
            if (files.length > 1) return alert("Не больше 1 фото");

            formData.append('photo', files[0]);


            $http.post('/api/v1/property', formData, {
                headers: { 'Content-Type': undefined }
            }).then(() => {
                alert('Успешно!');
                $window.location.href = 'main.html';
            }, () => {
                alert('Ошибка при создании');
            });
        };
    });

